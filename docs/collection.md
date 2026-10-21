# Spring Boot API Boilerplate

Shared types, response formats, and object definitions for the Spring Boot API Boilerplate.

## Authentication

The API uses OAuth2 with JWT access tokens.

Supported grant types:

- `password`
- `refresh_token`
- `client_credentials`

Protected endpoints require a bearer access token.

## Type conventions

- `T[]` is an array of `T`.
- `Map<String, T>` is a JSON object with string keys and values of type `T`.
- `UUID` is a UUID string.
- In request tables, **Required** indicates whether a property must be supplied.
- Properties cannot be `null` unless their type includes `null`.

## Request objects

### CreateUserRequest

| Property    | Type   | Required | Constraints                                             |
|-------------|--------|----------|---------------------------------------------------------|
| `userName`  | String | Yes      | Between 4 and 24 characters. Cannot contain whitespace. |
| `firstName` | String | Yes      | Cannot be blank.                                        |
| `lastName`  | String | Yes      | Cannot be blank.                                        |
| `email`     | String | Yes      | Must be a valid email address.                          |
| `password`  | String | Yes      | Between 4 and 24 characters.                            |

### UpdateUserRequest

| Property    | Type   | Required | Constraints                                                   |
|-------------|--------|----------|---------------------------------------------------------------|
| `userName`  | String | Yes      | Between 4 and 24 characters. Cannot contain whitespace.       |
| `firstName` | String | Yes      | Cannot be blank.                                              |
| `lastName`  | String | Yes      | Cannot be blank.                                              |
| `email`     | String | Yes      | Must be a valid email address.                                |
| `password`  | String | Yes      | Between 4 and 24 characters. Must match the current password. |

The supplied password verifies the account and does not change the user's password.

### PasswordRequest

| Property   | Type   | Required | Constraints      |
|------------|--------|----------|------------------|
| `password` | String | Yes      | Cannot be blank. |

### ChangePasswordRequest

| Property          | Type   | Required | Constraints      |
|-------------------|--------|----------|------------------|
| `currentPassword` | String | Yes      | Cannot be blank. |
| `newPassword`     | String | Yes      | Cannot be blank. |

### ForgotPasswordRequest

| Property | Type   | Required | Constraints                                  |
|----------|--------|----------|----------------------------------------------|
| `email`  | String | No       | Must be a valid email address when supplied. |

### ResetPasswordRequest

| Property      | Type   | Required | Constraints           |
|---------------|--------|----------|-----------------------|
| `token`       | `UUID` | Yes      | Password reset token. |
| `newPassword` | String | Yes      | Cannot be blank.      |

## Response objects

### UserResponse

| Property    | Type    | Description       |
|-------------|---------|-------------------|
| `id`        | Integer | User ID.          |
| `userName`  | String  | Username.         |
| `firstName` | String  | First name.       |
| `lastName`  | String  | Last name.        |
| `email`     | String  | Email address.    |

The password is write-only and is not included in user responses.

### OAuthAccessTokenResponse

| Property       | Type    | Description                       |
|----------------|---------|-----------------------------------|
| `access_token` | String  | JWT access token.                 |
| `token_type`   | String  | OAuth2 token type.                |
| `expires_in`   | Integer | Access token lifetime in seconds. |
| `scope`        | String  | Granted OAuth2 scope.             |

### OAuthRefreshableTokenResponse

Includes all properties of [`OAuthAccessTokenResponse`](#oauthaccesstokenresponse), plus:

| Property        | Type   | Description           |
|-----------------|--------|-----------------------|
| `refresh_token` | String | OAuth2 refresh token. |

### JwkSetResponse

| Property | Type            | Description              |
|----------|-----------------|--------------------------|
| `keys`   | [`Jwk[]`](#jwk) | Available JSON Web Keys. |

### Jwk

| Property | Type   | Description                              |
|----------|--------|------------------------------------------|
| `kty`    | String | Key type. Value: `RSA`.                  |
| `e`      | String | RSA public exponent, Base64URL encoded.  |
| `n`      | String | RSA modulus, Base64URL encoded.          |

## Error response formats

### ErrorResponse

Application errors handled by the application's exception handler use this format.

| Property      | Type                            | Description                                                                                             |
|---------------|---------------------------------|---------------------------------------------------------------------------------------------------------|
| `error`       | [`ApiErrorCode`](#apierrorcode) | Application error code.                                                                                 |
| `description` | String                          | Developer-facing error description.                                                                     |
| `code`        | Integer                         | HTTP status code.                                                                                       |
| `details`     | `Map<String, String>` or `null` | Additional error information. The key identifies the field or target and the value describes the error. |

### OAuthErrorResponse

Authentication and OAuth2 errors handled directly by Spring Security use a separate format.

| Property            | Type   | Description                                        |
|---------------------|--------|----------------------------------------------------|
| `error`             | String | OAuth2 or authentication error code.               |
| `error_description` | String | Description of the OAuth2 or authentication error. |

### ApiErrorCode

String with one of the following values.

| Value                    | HTTP status                  | Meaning                                                             |
|--------------------------|------------------------------|---------------------------------------------------------------------|
| `INVALID_ARGUMENTS`      | `400 Bad Request`            | One or more request values violate their validation constraints.    |
| `MESSAGE_NOT_READABLE`   | `400 Bad Request`            | Request body could not be read or parsed.                           |
| `UNAUTHENTICATED`        | `401 Unauthorized`           | Authentication is required or has failed.                           |
| `PERMISSION_DENIED`      | `403 Forbidden`              | The authenticated caller is not permitted to perform the operation. |
| `RESOURCE_NOT_FOUND`     | `404 Not Found`              | Requested resource does not exist.                                  |
| `URI_NOT_FOUND`          | `404 Not Found`              | Requested API URI does not exist.                                   |
| `METHOD_NOT_ALLOWED`     | `405 Method Not Allowed`     | HTTP method is not supported by the requested endpoint.             |
| `ALREADY_EXISTS`         | `409 Conflict`               | Resource conflicts with an existing resource.                       |
| `UNSUPPORTED_MEDIA_TYPE` | `415 Unsupported Media Type` | Request media type is not supported.                                |
| `INTERNAL`               | `500 Internal Server Error`  | Unexpected server error.                                            |
| `UNAVAILABLE`            | `503 Service Unavailable`    | Required service or database is unavailable.                        |
