# Starter CI package access

The public starter uses `dev.nm:nmdev-dist:1.2.6` from
https://maven.pkg.github.com/nmltd/maven-packages.

The workflow uses only this public repository's built-in `GITHUB_TOKEN`,
with `contents: read` and `packages: read`. No `NM_PACKAGES_TOKEN`,
`NM_REPOSITORIES_TOKEN` or private license secret is needed for this workflow.
It first confirms that its token cannot read the private NMDEV source POM,
then downloads the public package using an empty Maven cache, runs the starter
tests and eleven tutorials, and checks that licenses remain external to JARs.

For local Maven downloads, GitHub requires your own personal access token
(classic) with `read:packages`. Use your GitHub username and token in
`~/.m2/settings.xml` under server ID `github-nm-packages`; follow
[Getting started](Getting-Started.md). Private source repository access is not required.

The committed `nm.lic` is the approved public trial. Runtime trial validity and
module entitlements are separate from Maven authentication. Replace an expired
trial through NM Dev; never commit a private license.

To verify, run Actions → Verify public NMDEV starter and tutorials. A successful
run proves access from a public repository token that cannot read private NMDEV
source. It does not represent a manual test with a separate human GitHub account.

Official reference:
https://docs.github.com/en/packages/working-with-a-github-packages-registry/working-with-the-apache-maven-registry
