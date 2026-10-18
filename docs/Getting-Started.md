# Getting started

## 1. Prerequisites

Install a JDK (Java 8 or later), Maven and Git. Check `java -version` and `mvn -version`; Maven should use the intended JDK.

Clone the repository and enter its root:

```bash
git clone https://github.com/nmltd/nmdev-examples.git
cd nmdev-examples
```

## 2. Configure package authentication

The starter uses `dev.nm:nmdev:1.2.6` from `https://maven.pkg.github.com/nmltd/nmdev`. Follow the [GitHub Maven registry instructions](https://docs.github.com/en/packages/working-with-a-github-packages-registry/working-with-the-apache-maven-registry) and create a personal access token (classic) with `read:packages`. Your GitHub account and token must have access to this package; contact NM Dev if access is denied.

Merge this server entry into your local `~/.m2/settings.xml`. The server ID must match the repository ID in `pom.xml`:

```xml
<settings xmlns="http://maven.apache.org/SETTINGS/1.2.0">
  <servers>
    <server>
      <id>github-nmdev</id>
      <username>${env.GITHUB_USERNAME}</username>
      <password>${env.GITHUB_PACKAGES_TOKEN}</password>
    </server>
  </servers>
</settings>
```

Set both environment variables in your terminal using your normal secret-management method. Do not paste a token into source code, the POM, screenshots or an issue. A 401/403 during dependency resolution is a package-access problem, not a license error.

## 3. Keep the license in the working directory

Obtain a trial or commercial license from [NM Dev](https://nm.dev/products/nmdev) and save it as `nm.lic` in the repository root. The license itself is not included in this repository. The NM Dev runtime loads it as an external file from the current working directory. Run commands from the repository root. If the trial expires or lacks a required module, obtain an appropriate license from [NM Dev](https://nm.dev/products/nmdev).

The license is deliberately excluded from the application JAR. Do not commit your license. The ignore rules exclude `*.lic` files; keep them outside version control.

## 4. Build and run

```bash
mvn -U clean verify
mvn exec:java
mvn exec:java -Dexec.mainClass=dev.nm.examples.RunAllExamples
```

The default main class remains the starter's `dev.nm.HelloWorld`. The second command prints `A number of columns = 2`; the third runs the numerical tutorials. `mvn verify` executes the original test and each tutorial's numerical checks.

To select one tutorial:

```bash
mvn exec:java -Dexec.mainClass=dev.nm.examples.QuadraticProgrammingExample
```

Packaging copies runtime dependencies into `target/lib/`. You can also run without Maven after packaging:

```bash
java -jar target/nmdev-examples-1.0.0-SNAPSHOT.jar
java -cp "target/nmdev-examples-1.0.0-SNAPSHOT.jar:target/lib/*" dev.nm.examples.RunAllExamples
```

On Windows use `;` instead of `:` inside the Java classpath. Maven commands work with the same class names on both platforms.

## 5. Troubleshooting

| Symptom | Check |
|---|---|
| Package download returns 401/403 | Matching server ID, username, token and package access |
| License rejected | Working directory, trial validity and module entitlements |
| Main class not found | Run `mvn compile` and check the fully qualified class name |
| Numerical check fails | Record the exact resolved version, JVM and error; do not simply loosen the tolerance |

The dependency is pinned to stable 1.2.6. Record the resolved binary checksum when reporting numerical differences or reproducing a result.
