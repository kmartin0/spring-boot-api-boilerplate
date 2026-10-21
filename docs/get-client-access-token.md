# Get client access token

Obtains an access token using the OAuth2 client credentials grant.

## Request

| Detail           | Value                               |
|------------------|-------------------------------------|
| Method           | `POST`                              |
| URL              | `/oauth/token`                      |
| Authorization    | Basic Auth                          |
| Consumes         | `application/x-www-form-urlencoded` |
| Body             | Form parameters — required          |
| Path parameters  | None                                |
| Query parameters | None                                |

### Authorization

| Field    | Type   | Required | Description          |
|----------|--------|----------|----------------------|
| Username | String | Yes      | OAuth client ID.     |
| Password | String | Yes      | OAuth client secret. |

### Form parameters

| Parameter    | Type   | Required | Constraints                   |
|--------------|--------|----------|-------------------------------|
| `grant_type` | String | Yes      | Must be `client_credentials`. |

Object definitions are in the collection documentation.

## Success response

| Detail   | Value                      |
|----------|----------------------------|
| Status   | `200 OK`                   |
| Produces | `application/json`         |
| Body     | `OAuthAccessTokenResponse` |

## Error responses

Produces `application/json` with an `OAuthErrorResponse` body.

| Status             | Cause                                                                   |
|--------------------|-------------------------------------------------------------------------|
| `400 Bad Request`  | Required OAuth parameters are missing or the grant type is unsupported. |
| `401 Unauthorized` | Client credentials are invalid or client authentication is missing.     |
