# Delete user

Deletes the currently authenticated user after verifying the user's password.

## Request

| Detail           | Value                        |
|------------------|------------------------------|
| Method           | `DELETE`                     |
| URL              | `/users`                     |
| Authorization    | Bearer Token                 |
| Consumes         | `application/json`           |
| Body             | `PasswordRequest` — required |
| Path parameters  | None                         |
| Query parameters | None                         |

The bearer token must be a valid access token.

Object definitions are in the collection documentation.

## Success response

| Detail   | Value            |
|----------|------------------|
| Status   | `204 No Content` |
| Produces | None             |
| Body     | None             |

## Error responses

Application validation errors use an `ErrorResponse` body. Authentication failures are handled by Spring Security and use an `OAuthErrorResponse` body.

| Status             | Response             | Type                | Cause                                                                                    |
|--------------------|----------------------|---------------------|------------------------------------------------------------------------------------------|
| `400 Bad Request`  | `ErrorResponse`      | `INVALID_ARGUMENTS` | The request body fails validation. `details` identifies the affected properties.         |
| `401 Unauthorized` | `OAuthErrorResponse` | —                   | The access token is missing, invalid, or expired, or the supplied password is incorrect. |
