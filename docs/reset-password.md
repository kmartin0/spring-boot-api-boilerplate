# Reset password

Resets a user's password using a valid password reset token.

A reset token can only be used once and is removed after a successful password reset.

## Request

| Detail           | Value                             |
|------------------|-----------------------------------|
| Method           | `POST`                            |
| URL              | `/users/reset-password`           |
| Authorization    | Not required                      |
| Consumes         | `application/json`                |
| Body             | `ResetPasswordRequest` — required |
| Path parameters  | None                              |
| Query parameters | None                              |

Object definitions are in the collection documentation.

## Success response

| Detail   | Value            |
|----------|------------------|
| Status   | `204 No Content` |
| Produces | None             |
| Body     | None             |

## Error responses

Application errors use an `ErrorResponse` body. Invalid or expired reset tokens are handled through Spring Security and use an `OAuthErrorResponse` body.

| Status             | Response             | Type                 | Cause                                                                            |
|--------------------|----------------------|----------------------|----------------------------------------------------------------------------------|
| `400 Bad Request`  | `ErrorResponse`      | `INVALID_ARGUMENTS`  | The request body fails validation. `details` identifies the affected properties. |
| `401 Unauthorized` | `OAuthErrorResponse` | —                    | The reset token does not exist or has expired.                                   |
| `404 Not Found`    | `ErrorResponse`      | `RESOURCE_NOT_FOUND` | The user associated with the reset token no longer exists.                       |
