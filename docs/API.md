# API

## Overview

This API provides OAuth2 authentication, user account management and password management.

### Authentication

The API uses OAuth2 with JWT access tokens.

Supported grant types:

- `password`
- `refresh_token`
- `client_credentials`

## Response formats

### Successful response format

Successful responses return the endpoint-specific response directly without a common wrapper.

Endpoints that complete successfully without returning data respond with:

**204 No Content**

### Application error response format

Errors handled by the application's exception handler use the following format.

**error:** string
- Application error code
- See Application error codes

**description:** string
- Developer-facing description of the error

**code:** integer
- HTTP status code

**details:** object<string, string>
- Optional
- Contains additional error information
- Key: field or target associated with the error
- Value: error description for that field or target

### OAuth / Spring Security error response format

Authentication and OAuth2 errors handled directly by Spring Security use a separate format.

**error:** string
- OAuth2 or authentication error code

**error_description:** string
- Description of the authentication or OAuth2 error

## Application error codes

**INVALID_ARGUMENTS**
- HTTP 400 Bad Request
- One or more request values violate their validation constraints

**MESSAGE_NOT_READABLE**
- HTTP 400 Bad Request
- Request body could not be read or parsed

**UNAUTHENTICATED**
- HTTP 401 Unauthorized
- Authentication is required or has failed

**PERMISSION_DENIED**
- HTTP 403 Forbidden
- The authenticated caller is not permitted to perform the operation

**RESOURCE_NOT_FOUND**
- HTTP 404 Not Found
- Requested resource does not exist

**URI_NOT_FOUND**
- HTTP 404 Not Found
- Requested API URI does not exist

**METHOD_NOT_ALLOWED**
- HTTP 405 Method Not Allowed
- HTTP method is not supported by the requested endpoint

**ALREADY_EXISTS**
- HTTP 409 Conflict
- Resource conflicts with an existing resource

**UNSUPPORTED_MEDIA_TYPE**
- HTTP 415 Unsupported Media Type
- Request media type is not supported

**INTERNAL**
- HTTP 500 Internal Server Error
- Unexpected server error

**UNAVAILABLE**
- HTTP 503 Service Unavailable
- Required service or database is unavailable

# Endpoints

## Token

### POST /oauth/token — Get access token

Obtains an access token and refresh token using the OAuth2 password grant.

#### Authorization

Basic Auth

**Username:** string
- Required

**Password:** string
- Required

#### Body

x-www-form-urlencoded

**username:** string
- Required

**password:** string
- Required

**grant_type:** string
- Required
- Value: `password`

#### Successful response

**200 OK**

**access_token:** string

**token_type:** string

**refresh_token:** string

**expires_in:** integer

**scope:** string

#### Error response

**400 Bad Request**
- Invalid user credentials
- Missing required OAuth parameters
- Unsupported grant type

**401 Unauthorized**
- Invalid client credentials
- Missing client authentication

---

### POST /oauth/token — Refresh access token

Obtains a new access token using an OAuth2 refresh token.

#### Authorization

Basic Auth

**Username:** string
- Required

**Password:** string
- Required

#### Body

x-www-form-urlencoded

**refresh_token:** string
- Required

**grant_type:** string
- Required
- Value: `refresh_token`

#### Successful response

**200 OK**

**access_token:** string

**token_type:** string

**refresh_token:** string

**expires_in:** integer

**scope:** string

#### Error response

**401 Unauthorized**
- Invalid or malformed refresh token
- Expired refresh token
- Invalid client credentials
- Missing client authentication

**400 Bad Request**
- Missing required OAuth parameters
- Unsupported grant type

---

### POST /oauth/token — Get client access token

Obtains an access token using the OAuth2 client credentials grant.

#### Authorization

Basic Auth

**Username:** string
- Required

**Password:** string
- Required

#### Body

x-www-form-urlencoded

**grant_type:** string
- Required
- Value: `client_credentials`

#### Successful response

**200 OK**

**access_token:** string

**token_type:** string

**expires_in:** integer

**scope:** string

#### Error response

**401 Unauthorized**
- Invalid client credentials
- Missing client authentication

**400 Bad Request**
- Missing required OAuth parameters
- Unsupported grant type

---

### GET /.well-known/jwks.json — Get JWK set

Returns the JSON Web Key Set used to verify JWT signatures issued by the authorization server.

#### Authorization

None

#### Body

None

#### Successful response

**200 OK**

**keys:** array
- Contains the available JSON Web Keys

Each key contains:

**kty:** string
- Key type
- Value: `RSA`

**e:** string
- RSA public exponent
- Base64URL encoded

**n:** string
- RSA modulus
- Base64URL encoded

#### Error response

No endpoint-specific client error response is defined.

## User

### POST /users — Register user

Creates a new user account.

#### Authorization

None

#### Body

JSON

**userName:** string
- Required
- Length: 4–24 characters
- Cannot contain whitespace

**firstName:** string
- Required

**lastName:** string
- Required

**email:** string
- Required
- Must be a valid email address

**password:** string
- Required
- Length: 4–24 characters

#### Successful response

**201 Created**

**id:** integer

**userName:** string

**firstName:** string

**lastName:** string

**email:** string

#### Error response

**400 Bad Request**
- One or more request fields do not satisfy their defined constraints

**409 Conflict**
- Email address is already in use
- Username is already in use

---

### GET /users — Get authenticated user

Returns the currently authenticated user.

#### Authorization

Bearer Token

**Token:** string
- Required
- Must be a valid access token

#### Body

None

#### Successful response

**200 OK**

**id:** integer

**userName:** string

**firstName:** string

**lastName:** string

**email:** string

#### Error response

**401 Unauthorized**
- Missing access token
- Invalid access token
- Expired access token
- Token does not resolve to an existing user

---

### PUT /users — Update user

Updates the currently authenticated user.

The supplied password is used to verify the account and does not change the user's password.

#### Authorization

Bearer Token

**Token:** string
- Required
- Must be a valid access token

#### Body

JSON

**userName:** string
- Required
- Length: 4–24 characters
- Cannot contain whitespace

**firstName:** string
- Required

**lastName:** string
- Required

**email:** string
- Required
- Must be a valid email address

**password:** string
- Required
- Length: 4–24 characters
- Must match the user's current password

#### Successful response

**200 OK**

**id:** integer

**userName:** string

**firstName:** string

**lastName:** string

**email:** string

#### Error response

**400 Bad Request**
- One or more request fields do not satisfy their defined constraints

**401 Unauthorized**
- Missing, invalid or expired access token
- Incorrect current password

**409 Conflict**
- Email address is already in use
- Username is already in use

---

### DELETE /users — Delete user

Deletes the currently authenticated user after verifying the user's password.

#### Authorization

Bearer Token

**Token:** string
- Required
- Must be a valid access token

#### Body

JSON

**password:** string
- Required
- Must not be blank
- Must match the user's current password

#### Successful response

**204 No Content**

#### Error response

**400 Bad Request**
- One or more request fields do not satisfy their defined constraints

**401 Unauthorized**
- Missing, invalid or expired access token
- Incorrect current password

## Password

### POST /users/change-password — Change password

Changes the password of the currently authenticated user.

#### Authorization

Bearer Token

**Token:** string
- Required
- Must be a valid access token

#### Body

JSON

**currentPassword:** string
- Required
- Must not be blank

**newPassword:** string
- Required
- Must not be blank

#### Successful response

**204 No Content**

#### Error response

**400 Bad Request**
- One or more request fields do not satisfy their defined constraints

**401 Unauthorized**
- Missing, invalid or expired access token
- Current password is incorrect

---

### POST /users/forgot-password — Forgot password

Creates a password reset token and sends a password reset email when the supplied email belongs to a user.

#### Authorization

None

#### Body

JSON

**email:** string
- Must be a valid email address

#### Successful response

**202 Accepted**
- Returned whether or not the email belongs to an existing user

#### Error response

**400 Bad Request**
- One or more request fields do not satisfy their defined constraints

---

### POST /users/reset-password — Reset password

Resets a user's password using a valid password reset token.

A reset token can only be used once and is removed after a successful password reset.

#### Authorization

None

#### Body

JSON

**token:** string
- Required
- UUID

**newPassword:** string
- Required
- Must not be blank

#### Successful response

**204 No Content**

#### Error response

**400 Bad Request**
- One or more request fields do not satisfy their defined constraints

**401 Unauthorized**
- Reset token does not exist
- Reset token has expired

**404 Not Found**
- User associated with the reset token no longer exists
