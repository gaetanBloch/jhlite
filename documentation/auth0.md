# OAuth 2.0 and OpenID Connect

OAuth is a stateful security mechanism, like HTTP Session. Spring Security provides excellent OAuth 2.0 and OIDC support, and this is leveraged by JHipster. If you’re not sure what OAuth and OpenID Connect (OIDC) are, please see [What the Heck is OAuth](https://developer.okta.com/blog/2017/06/21/what-the-heck-is-oauth)?

## Auth0

If you’d like to use Auth0 instead of Keycloak, follow the configuration steps below:

### Create an OIDC App using Auth0 Admin Dashboard

  - Create a free developer account at https://auth0.com/signup. After successful sign-up, your account shall be associated with a unique domain like `dev-xxx.us.auth0.com`
  - Create a new application of type `Regular Web Applications`. Switch to the `Settings` tab, and configure your application settings like:
    - Allowed Callback URLs: http://localhost:8080/login/oauth2/code/oidc, http://localhost:8080/swagger-ui/oauth2-redirect.html
    - Allowed Logout URLs: http://localhost:8080/
  - Navigate to **User Management > Roles** and create new roles named `ROLE_ADMIN`, and `ROLE_USER`.
  - Navigate to **User Management > Users** and create a new user account. Click on the **Role** tab to assign roles to the newly created user account.
  - Navigate to **Actions > Flows** and select **Login**. Create a new action named `Add Roles` and use the default trigger and runtime. Change the `onExecutePostLogin` handler to be as follows:

```javascript
exports.onExecutePostLogin = async (event, api) => {
  const namespace = 'https://www.jhipster.tech';
  if (event.authorization) {
    api.idToken.setCustomClaim('preferred_username', event.user.email);
    api.idToken.setCustomClaim(`${namespace}/roles`, event.authorization.roles);
    api.accessToken.setCustomClaim(`${namespace}/roles`, event.authorization.roles);
  }
}
```

  - Select **Deploy** and drag the `Add Roles` action to your Login flow.

If you’d like to have all these steps automated for you, add a 👍 to [issue #351](https://github.com/auth0/auth0-cli/issues/351) in the Auth0 CLI project.

### Configure JHipster Application to use Auth0 as OIDC Provider

In your application, update src/main/resources/config/application-auth0.yml to use your Auth0 settings. Hint: replace {yourAuth0Domain} with your org’s name (e.g., dev-123456.us.auth0.com).

```properties
application.security.oauth2.audience=account,api://default,https://{yourAuth0Domain}/api/v2/
security.oauth2.client.provider.oidc.issuer-uri=https://{yourAuth0Domain}/
security.oauth2.client.registration.oidc.client-id={client-id}
security.oauth2.client.registration.oidc.scope: openid,profile,email
```

Modify `auth0.sh` to use your Auth0 settings.

```bash
export SPRING_SECURITY_OAUTH2_CLIENT_REGISTRATION_OIDC_CLIENT_SECRET={client-secret}
```

If you have a doubt on the `issuer-uri` value, then, you can get the value from **Applications > {Your Application} > Settings > Advanced Settings > Endpoints > OpenID Configuration**. Remove `.well-known/openid-configuration` suffix since that will be added by the Spring Security.

You can use the default `Auth0 Management API` audience value from the **Applications > API > API Audience** field. You can also define your own custom API and use the identifier as the API audience.

Before running `Cypress` tests, specify `Auth0` user details by overriding the `CYPRESS_E2E_USERNAME` and `CYPRESS_E2E_PASSWORD` environment variables. Refer to [Cypress documentation](https://docs.cypress.io/guides/guides/environment-variables#Setting) for more details.

```bash
export CYPRESS_E2E_USERNAME=<your-username>
export CYPRESS_E2E_PASSWORD=<your-password>
```

Note: Auth0 requires a user to provide authorization consent on the first login. Consent flow is currently not handled in the Cypress test suite. To mitigate the issue, you can use a user account that has already granted consent to authorize application access via interactive login.

### Running locally

In order to run your application with OAuth 2.0 and Auth0:

```bash
source auth0.sh
SPRING_PROFILES_ACTIVE=auth0 ./mvnw
```

or

```bash
source auth0.sh
./mvnw -Dspring-boot.run.profiles=auth0
```
