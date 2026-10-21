# Forgot password

Creates a password reset token and sends a password reset email when the supplied email belongs to a user.

The response does not reveal whether the email belongs to an existing user.

## Request

| Detail           | Value                              |
|------------------|------------------------------------|
| Method           | `POST`                             |
| URL              | `/users/forgot-password`           |
| Authorization    | Not required                       |
| Consumes         | `application/json`                 |
| Body             | `ForgotPasswordRequest` — required |
| Path parameters  | None                               |
| Query parameters | None                               |

Object definitions are in the collection documentation.

## Success response

| Detail   | Value          |
|----------|----------------|
| Status   | `202 Accepted` |
| Produces | None           |
| Body     | None           |

The same success status is returned whether or not the email belongs to an existing user.

## Error responses

Produces `application/json` with an `ErrorResponse` body.

| Status            | Type                | Cause                                                                            |
|-------------------|---------------------|----------------------------------------------------------------------------------|
| `400 Bad Request` | `INVALID_ARGUMENTS` | The request body fails validation. `details` identifies the affected properties. |
