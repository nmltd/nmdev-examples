# Starter CI package access

The workflow **Verify public NMDEV starter and tutorials** first checks package
authentication, then uses an empty Maven cache to build/test the starter and run
all eleven tutorials, HelloWorld and Graph with the committed public trial.
It also checks that packaged JARs contain no .lic/.key files. It does not require
the private NM_LICENSE_BASE64 secret.

## Configure access once

Preferred: create a repository Actions secret named **NM_PACKAGES_TOKEN** using
a personal access token (classic) with **read:packages**. The token owner must
already have access to the private nmltd/nmdev package; authorize organization
SSO when required. Do not give this token write:packages or delete:packages.

Repository settings:
https://github.com/nmltd/nmdev-examples/settings/secrets/actions

If the token owner differs from the workflow actor, set the Actions variable
**NM_PACKAGES_USERNAME** to that owner's GitHub username.

If your existing **NM_REPOSITORIES_TOKEN** is an organization Actions secret with
package-read scope/access, you can instead edit its selected-repository policy
to include **nmdev-examples**. A policy allowing only private repositories does
not cover this public repository. Do not expose a broader token merely to avoid
creating the limited package-read token.

Credential precedence is NM_PACKAGES_TOKEN, then NM_REPOSITORIES_TOKEN, then the
repository GITHUB_TOKEN. The final fallback works only if GitHub grants that
repository token access to the package; permissions: packages: read alone does
not grant access to another private repository's package.

Secrets cannot be recovered from another repository's Settings page. Reuse a
known securely stored token, grant an existing organization secret to this
repository, or create a new token. Never put it in a POM, source file, issue,
chat message or workflow log.

## Verify

After configuring access, re-run the failed job on the PR. After merge, select
Actions → Verify public NMDEV starter and tutorials → Run workflow on main.
The first step must report a successful authenticated POM download before Maven
builds or runtime license checks. Fork PRs do not receive these credentials;
trusted same-repository PRs, main pushes and manual runs are supported.

HTTP 401/403/404 at the access check is a package credential/permission or
coordinate issue. It is not a failed numerical test or a rejected runtime license.
Once download succeeds, a license error concerns the bundled trial's expiry and
module coverage. Renew it through NM Dev when necessary.

Official references:
- https://docs.github.com/en/packages/working-with-a-github-packages-registry/working-with-the-apache-maven-registry
- https://docs.github.com/en/actions/security-for-github-actions/security-guides/using-secrets-in-github-actions
