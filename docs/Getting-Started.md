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

## 6. Use NMDEV in your own application

Create a directory containing the following `pom.xml` and save your valid
license as `nm.lic` in that directory. Keep the settings.xml server from step 2.

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>
  <groupId>example</groupId>
  <artifactId>my-nmdev-app</artifactId>
  <version>1.0.0</version>
  <properties>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    <maven.compiler.source>8</maven.compiler.source>
    <maven.compiler.target>8</maven.compiler.target>
  </properties>
  <repositories>
    <repository>
      <id>github-nmdev</id>
      <url>https://maven.pkg.github.com/nmltd/nmdev</url>
      <releases><enabled>true</enabled></releases>
      <snapshots><enabled>false</enabled></snapshots>
    </repository>
  </repositories>
  <dependencies>
    <dependency>
      <groupId>dev.nm</groupId>
      <artifactId>nmdev</artifactId>
      <version>1.2.6</version>
    </dependency>
  </dependencies>
  <build>
    <plugins>
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-compiler-plugin</artifactId>
        <version>3.14.1</version>
      </plugin>
      <plugin>
        <groupId>org.codehaus.mojo</groupId>
        <artifactId>exec-maven-plugin</artifactId>
        <version>3.5.0</version>
        <configuration><mainClass>example.FirstMatrix</mainClass></configuration>
      </plugin>
    </plugins>
  </build>
</project>
```

Save this as `src/main/java/example/FirstMatrix.java`:

```java
package example;

import dev.nm.algebra.linear.matrix.doubles.matrixtype.dense.DenseMatrix;

public final class FirstMatrix {
    public static void main(String[] args) {
        DenseMatrix a = new DenseMatrix(new double[][] {{1, 2}, {3, 4}});
        System.out.println("Rows = " + a.nRows() + ", columns = " + a.nCols());
        System.out.println("Entry (2, 1) = " + a.get(2, 1));
    }
}
```

From the directory containing `pom.xml` and `nm.lic`, run:

```bash
mvn -U clean compile exec:java
```

Expected output:

```text
Rows = 2, columns = 2
Entry (2, 1) = 3.0
```

NMDEV matrix indices start at 1. Java arrays still start at 0. The combined
customer JAR contains its runtime dependencies; this application does not need
the private parent POM, nmutils/nmlicense dependencies, or an obfuscation plugin.

## 7. Customer setup checklist

- Obtain package access for the GitHub account that owns your token. A token
  alone does not grant access to a package your account cannot read.
- Create a classic token with `read:packages`; authorize organization SSO when
  your organization requires it. Keep it in your local secret manager/environment.
- On macOS/Linux use `~/.m2/settings.xml`; on Windows use
  `%USERPROFILE%\\.m2\\settings.xml`. Create the directory/file if absent and
  merge the server entry without deleting other configured servers.
- Obtain a trial or commercial license through [NM Dev](https://nm.dev/products/nmdev).
  Request the modules used by your application and replace expired trials.
- Package authentication happens during Maven download. License validation
  happens when Java executes. Fix the failing stage independently.
- A 404 can also indicate inaccessible private packages. Check the exact
  `dev.nm:nmdev:1.2.6` coordinates and account permissions before changing code.
- Verify the first matrix example, then run the tutorials relevant to your task.
- When requesting help, include Java/Maven/OS versions, exact dependency version,
  working directory, command and error text. Remove tokens and license contents.

Public API reference: https://nmltd.github.io/nmdev/. Release notes:
https://github.com/nmltd/nmdev/releases/tag/v1.2.6 (repository access may be required).
