param([string]$RepositoryPath = (Get-Location).Path)
Set-StrictMode -Version Latest
$ErrorActionPreference = 'Stop'
$repo = (Resolve-Path -LiteralPath $RepositoryPath).Path
$lockPath = Join-Path $repo 'benchmark/dependencies.lock.tsv'
$originPath = Join-Path $repo 'benchmark/src/main/java/dev/nm/benchmark/OriginValidationMain.java'
$versionsPath = Join-Path $repo 'benchmark/versions.json'
$pins = [ordered]@{
    'commons-math4-core' = '0a694f3203cd94b0ea2804e0fd927c651a5120ceb8504d128cfb9e199783c188'
    'commons-math4-legacy' = 'c0d4bdedc85682630c20d7c751135ce958bc595e813647fbe7ee9038912e0337'
    'commons-math4-legacy-core' = 'c5bcc8d8f62f3797f5dc623fbfd4a2907383c513f91620a176868efb36aeb101'
    'commons-math4-legacy-exception' = 'eab5de858ddc35e55e9db0f2eff7621dafe36a92eb885aec2f9fe313ee55cb14'
}
foreach ($path in @($lockPath,$originPath,$versionsPath)) {
    if (-not (Test-Path -LiteralPath $path -PathType Leaf)) { throw "Missing source file: $path" }
}

# Prepare all edits before writing; retain every other library and the NMDEV pin.
$lock = [IO.File]::ReadAllText($lockPath)
foreach ($artifact in $pins.Keys) {
    $pattern = '(?m)^' + [regex]::Escape($artifact) + '-4\.0-(?:SNAPSHOT|beta1)\.jar\t[^\r\n]*'
    if ([regex]::Matches($lock,$pattern).Count -ne 1) { throw "Expected one lock entry for $artifact; no files changed." }
    $name = "$artifact-4.0-beta1.jar"
    $url = "https://repo.maven.apache.org/maven2/org/apache/commons/$artifact/4.0-beta1/$name"
    $line = $name + "`t" + $pins[$artifact] + "`t" + $url
    $lock = [regex]::Replace($lock,$pattern,$line)
}
$origin = [IO.File]::ReadAllText($originPath)
$originPattern = 'commons-math4-legacy-4\.0-(?:SNAPSHOT|beta1)\.jar'
if ([regex]::Matches($origin,$originPattern).Count -ne 2) { throw 'Expected two Commons origin checks; no files changed.' }
$origin = [regex]::Replace($origin,$originPattern,'commons-math4-legacy-4.0-beta1.jar')
$versions = [IO.File]::ReadAllText($versionsPath) | ConvertFrom-Json
foreach ($key in @('COMMONS','COMMONS_BLOCK')) {
    if ($null -eq $versions.PSObject.Properties[$key]) { throw "Missing version label: $key; no files changed." }
}
$versions.COMMONS = 'Commons Math 4.0-beta1 - Array2DRowRealMatrix'
$versions.COMMONS_BLOCK = 'Commons Math 4.0-beta1 - BlockRealMatrix'
$updates = [ordered]@{}
$updates[$lockPath] = $lock
$updates[$originPath] = $origin
$updates[$versionsPath] = ($versions | ConvertTo-Json -Depth 20) + [Environment]::NewLine

$backup = Join-Path $repo ('benchmark-commons-backup-' + [DateTime]::UtcNow.ToString('yyyyMMdd-HHmmss-fff'))
[IO.Directory]::CreateDirectory($backup) | Out-Null
foreach ($path in $updates.Keys) {
    Copy-Item -LiteralPath $path -Destination (Join-Path $backup (Split-Path -Leaf $path))
}
$utf8 = New-Object System.Text.UTF8Encoding($false)
foreach ($path in $updates.Keys) { [IO.File]::WriteAllText($path,[string]$updates[$path],$utf8) }
Write-Host 'Pinned all four Commons Math modules to released 4.0-beta1 artifacts with verified SHA-256 hashes.'
Write-Host "Source backups: $backup"
Write-Host 'NMDEV and all other dependency pins were preserved.'
Write-Host 'Commons differs from the previous snapshot comparison; the version labels now record this explicitly.'
Write-Host ''
Write-Host 'Build once, then use this NEW output directory to avoid stale snapshot JARs:'
Write-Host 'mvn -s benchmark/settings.xml -gs benchmark/settings.xml -f benchmark/pom.xml package'
Write-Host 'java -jar benchmark/target/nmdev-benchmark-1.2.0.jar --output nmdev-1.2.10-local-beta1'
