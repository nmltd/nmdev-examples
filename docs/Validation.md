# Validation

Validated on 2026-10-18 with Zulu OpenJDK 8u472 and the existing locally available combined NM Dev 1.2.6-SNAPSHOT JAR.

- All main sources compiled with `javac` against the combined binary, without private implementation source on the classpath.
- All six tutorial programs ran successfully from the repository root using a locally available trial license that is excluded from publication.
- JUnit 4.13.2: **7 tests passed** (original HelloWorld test plus six numerical tutorials).
- New tutorial Javadoc generated with `-Xdoclint:all` without warnings or errors.
- Relative links in the README and canonical tutorial documentation checked.

Binary SHA-256: `a3e2b2492e79530b8e12920b48f48bfc295786b138aa26300a16b53bafe1386c`.

The available validation binary's recorded source commit is `17af535be23bcf9316634c14712c044a967c713e`; tutorial provenance records the separately inspected test-source commit. This verifies the examples against that binary, not every future SNAPSHOT.

Maven is not installed in the execution environment, so Maven dependency resolution, the packaged manifest and `mvn clean verify` were not executed here. They remain the documented user workflow inherited from the starter. No CI run or live GitHub Wiki publication is claimed by these local checks.

