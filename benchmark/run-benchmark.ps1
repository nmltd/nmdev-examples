param(
    [string]$NmdevJar,
    [string]$NmdevVersion = '1.2.10',
    [string]$OutputDirectory,
    [string]$CacheDirectory = (Join-Path $PSScriptRoot '.benchmark-cache'),
    [switch]$Smoke
)
Set-StrictMode -Version Latest
$ErrorActionPreference = 'Stop'
$applicationVersion = '1.3.0'
$cache = [IO.Path]::GetFullPath($CacheDirectory)
[IO.Directory]::CreateDirectory($cache) | Out-Null

function Get-VerifiedReleaseFile([string]$BaseUrl,[string]$Name) {
    $checksumFile = Join-Path $cache ($Name + '.SHA256SUMS')
    Invoke-WebRequest -UseBasicParsing -Uri "$BaseUrl/SHA256SUMS" -OutFile $checksumFile
    $lines = @(Get-Content -LiteralPath $checksumFile | Where-Object {
        $_ -match ('^[0-9a-fA-F]{64}\s+\*?' + [regex]::Escape($Name) + '$')
    })
    if ($lines.Count -ne 1) { throw "Release has no unique checksum for $Name" }
    $expected = ($lines[0] -split '\s+')[0]
    $path = Join-Path $cache $Name
    if (-not (Test-Path -LiteralPath $path) -or
        (Get-FileHash -LiteralPath $path -Algorithm SHA256).Hash -ne $expected) {
        $download = $path + '.download'
        Invoke-WebRequest -UseBasicParsing -Uri "$BaseUrl/$Name" -OutFile $download
        if ((Get-FileHash -LiteralPath $download -Algorithm SHA256).Hash -ne $expected) {
            Remove-Item -LiteralPath $download
            throw "Release checksum mismatch for $Name"
        }
        Move-Item -LiteralPath $download -Destination $path -Force
    }
    return $path
}

# Release inputs are downloaded once and reused; benchmark sources are never edited.
$application = Get-VerifiedReleaseFile "https://github.com/nmltd/nmdev-examples/releases/download/benchmark-v$applicationVersion" "nmdev-benchmark-$applicationVersion.jar"
if ([string]::IsNullOrWhiteSpace($NmdevJar)) {
    if ($NmdevVersion -notmatch '^\d+\.\d+\.\d+$') { throw 'NmdevVersion must be major.minor.patch.' }
    $NmdevJar = Get-VerifiedReleaseFile "https://github.com/nmltd/maven-packages/releases/download/nmdev-$NmdevVersion-validated" "nmdev-$NmdevVersion.jar"
    if ([string]::IsNullOrWhiteSpace($OutputDirectory)) { $OutputDirectory = "nmdev-$NmdevVersion-full" }
} else {
    $NmdevJar = (Resolve-Path -LiteralPath $NmdevJar).Path
    if ([string]::IsNullOrWhiteSpace($OutputDirectory)) {
        $hash = (Get-FileHash -LiteralPath $NmdevJar -Algorithm SHA256).Hash.ToLowerInvariant()
        $OutputDirectory = 'nmdev-' + $hash.Substring(0,12) + '-full'
    }
}
$arguments = @('-jar',$application,'--nmdev-jar',$NmdevJar,'--output',$OutputDirectory)
if ($Smoke) { $arguments += '--smoke' }
Write-Host "NMDEV input: $NmdevJar"
Write-Host "Results: $OutputDirectory"
& java @arguments
exit $LASTEXITCODE
