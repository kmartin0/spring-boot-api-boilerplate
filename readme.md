# Spring Boot API Boilerplate

Boilerplate project for building REST APIs with Spring Boot.

The project includes common API functionality such as user management, OAuth 2 authentication, persistence, validation, localization, email support and global exception handling.

## Features

- User registration and account management.
- Retrieve the authenticated user.
- Update and delete users.
- Change password.
- Forgot and reset password flows.
- OAuth 2 authentication and authorization using JWK-signed JWTs.
- Global exception handling with a unified error response format.
- Bean validation.
- Localization.
- Email support.
- Unit tests.
- MySQL persistence.
- MongoDB configuration available on a separate branch.
- Configurable HTTPS support.

## Technical Overview

- Spring Boot.
- Spring Security.
- OAuth 2.
- JWK-signed JWT access tokens.
- Spring Data JPA.
- MySQL.
- MongoDB variant.
- Bean Validation.
- Lombok.
- JaCoCo test coverage.
- Feature-based package structure.

## Documentation

- [Package Structure](#package-structure)
- [Getting Started](#getting-started)
- [Project Refactoring](#project-refactoring)
- [Security](#security)
    - [Authorization Server](#authorization-server)
    - [Resource Server](#resource-server)
- [Database](#database)
    - [MySQL](#mysql)
    - [MongoDB](#mongodb)
- [Email](#email)
- [Exception Handling](#exception-handling)
- [HTTPS](#https)
- [Additional Information](#additional-information)

## Package Structure

```text
boilerplateapi
├── config
├── exceptionhandler
│   ├── response
│   └── exception
├── features
│   ├── email
│   └── user
│       └── password
│           ├── change
│           ├── forgot
│           └── reset
├── security
│   ├── authorizationserver
│   │   ├── clientdetails
│   │   └── userdetails
│   └── resourceserver
├── utils
└── validators
```

## Getting Started

The required configuration changes are also marked with TODOs throughout the project.

The sections below describe the main changes required to configure the boilerplate for a new project.

## Project Refactoring

To replace the boilerplate naming with your own project naming, make the following changes.

### POM

Update:

- `groupId`
- `artifactId`
- `name`
- `description`

### Package and Module

- Refactor `com/boilerplate/boilerplateapi` to reflect the new `groupId` and `artifactId`.
- Rename `SpringBootApiBoilerplateApplication` to reflect the new application name.
- In IntelliJ, rename the module using:
  `Right click root folder > Refactor > Rename`
- Rename the Spring Boot run configuration using:
  `Edit Configurations > Spring Boot > SpringBootBoilerplateApplication > Name`
- Rename the project using:
  `File > Project Structure > Project Name`

## Security

Users are authenticated and authorized using OAuth 2 and JWT.

The security configuration consists of:

- Authorization server.
- Resource server.

### Authorization Server

The authorization server is responsible for creating and approving authentication and authorization data used by the resource server.

Before requesting user tokens, a client must be registered in the `ClientRepository`.

#### Get Access Token with User Credentials

The `Authorization` header uses Basic Authentication where:

```text
username = <CLIENT_ID>
password = <CLIENT_SECRET>
```

Example request:

```bash
curl --location --request POST 'http://localhost:8080/oauth/token' \
  --header 'Content-Type: application/x-www-form-urlencoded' \
  --header 'Authorization: Basic ZGFydHMtbWF0Y2hlci13ZWI6c2VjcmV0' \
  --data-urlencode 'username=<USER_EMAIL>' \
  --data-urlencode 'password=<USER_PASSWORD>' \
  --data-urlencode 'grant_type=password'
```

#### Get Access Token with Refresh Token

```bash
curl --location --request POST 'http://localhost:8080/oauth/token' \
  --header 'Content-Type: application/x-www-form-urlencoded' \
  --header 'Authorization: Basic ZGFydHMtbWF0Y2hlci13ZWI6c2VjcmV0' \
  --data-urlencode 'refresh_token=<REFRESH_TOKEN>' \
  --data-urlencode 'grant_type=refresh_token'
```

#### JWK Configuration

The authorization server supports JWT-encoded, JWK-signed authorization tokens and exposes a JWK Set URI.

A demo key pair is available at:

```text
resources/keystore/demo-dev-jwk.jks
```

It is configured in `application-dev.properties` for use when the `dev` profile is active.

To configure your own keystore containing a key pair:

##### 1. Create a Keystore

```bash
keytool -genkey \
  -keyalg RSA \
  -storetype PKCS12 \
  -keysize 2048 \
  -alias <KEYSTORE_ALIAS> \
  -keystore <KEYSTORE_FILE.jks> \
  -storepass <KEYSTORE_PASSWORD>
```

Optionally, view the public key:

```bash
keytool -list \
  -rfc \
  -alias <KEYSTORE_ALIAS> \
  -storepass <KEYSTORE_PASSWORD> \
  -keystore <KEYSTORE_FILE.jks>
```

Optionally, view both the public and private keys:

```bash
keytool -importkeystore \
  -srckeystore <KEYSTORE_FILE.jks> \
  -destkeystore <TEMP_P12.p12> \
  -deststoretype PKCS12

openssl pkcs12 -info -in <TEMP_P12.p12>
```

Production keystores should never be committed to a public repository. Add them to `.gitignore` and keep them private.

##### 2. Add the Keystore

Copy the generated `.jks` file into:

```text
src/main/resources/keystore
```

##### 3. Configure Environment Variables

```text
JWK_KEYSTORE_PATH=classpath:keystore/<KEYSTORE_FILE.jks>
JWK_KEYSTORE_PASS=<KEYSTORE_PASSWORD>
JWK_KEYSTORE_ALIAS=<KEYSTORE_ALIAS>
```

### Resource Server

The resource server does not require additional configuration to work out of the box.

If custom behavior is required, modify:

```text
security/resourceserver/ResourceServerSecurityConfig.java
```

## Database

The boilerplate repository provides two database variants:

- MySQL.
- MongoDB on the MongoDB boilerplate branch.

If a different database such as PostgreSQL is required, update the relevant models, repositories and application properties.

### Models

```text
features/user/User.java
features/user/password/reset/PasswordToken.java
security/authorizationserver/clientdetails/ClientDetails.java
```

### Repositories

```text
features/user/UserRepository.java
features/user/password/reset/PasswordTokenRepository.java
security/authorizationserver/clientdetails/ClientDetailsRepository.java
```

### Properties

```text
application.properties
```

## MySQL

The MySQL configuration uses:

```text
spring-boot-starter-data-jpa
```

Hibernate is not configured to create the database automatically.

You can either:

- Implement the required database mappings and enable:

```properties
spring.jpa.hibernate.ddl-auto=create
```

- Or use:

```text
resources/db/demo-db.sql
```

to create the database with demo data.

Configure the database connection using:

```text
DB_URI=<DATABASE_URI>
DB_USERNAME=<DATABASE_USERNAME>
DB_PASSWORD=<DATABASE_PASSWORD>
```

## MongoDB

The MongoDB configuration uses:

```text
spring-boot-starter-data-mongodb
```

MongoDB automatically creates collections when data is added.

If schema validation is required, load the provided database script in the Mongo shell.

For example:

```javascript
load("C:/path/to/project/src/main/resources/db/demo-db.js")
```

By default:

```properties
spring.data.mongodb.authentication-database=none
```

If the database requires authentication, configure this property and the corresponding credentials appropriately.

## Email

Configure email credentials using:

```text
EMAIL_USERNAME=<COMPANY_EMAIL_LOGIN>
EMAIL_PASSWORD=<COMPANY_EMAIL_PASSWORD>
```

The forgot-password functionality uses these credentials.

In:

```text
features/email/EmailServiceImpl.java
```

configure the following values in `sendForgotPasswordEmail`:

```text
mailFrom=<EMAIL_FROM_ADDRESS>
resetPasswordUrl=<FRONTEND_RESET_PASSWORD_URL>
```

You can also customize:

- The email subject using `helper.setSubject()`.
- The email template using `helper.setText()`.

## Exception Handling

Global exception handling is configured in:

```text
exceptionhandler/GlobalExceptionHandler.java
```

The API returns errors using the unified response structure defined in:

```text
exceptionhandler/response/ErrorResponse.java
```

Example validation error:

```json
{
  "error": "INVALID_ARGUMENTS",
  "description": "Invalid arguments have been supplied.",
  "code": 400,
  "details": {
    "userName": "Length must be between 4 and 24."
  }
}
```

Example URI error:

```json
{
  "error": "URI_NOT_FOUND",
  "description": "Uri with path /user not found.",
  "code": 404
}
```

## HTTPS

HTTPS is disabled by default.

To enable HTTPS using a self-signed certificate:

### 1. Create a Keystore

```bash
keytool -genkey \
  -keyalg RSA \
  -storetype PKCS12 \
  -keysize 2048 \
  -alias <KEYSTORE_ALIAS> \
  -keystore <KEYSTORE_FILE.jks> \
  -storepass <KEYSTORE_PASSWORD>
```

### 2. Add the Keystore

Copy the generated `.jks` file into the project resources.

### 3. Configure `application.properties`

```properties
server.ssl.enabled=true
server.ssl.key-store-type=JKS
server.ssl.key-store=classpath:<KEYSTORE.jks>
server.ssl.key-store-password=<KEYSTORE_PASSWORD>
server.ssl.key-alias=<KEYSTORE_ALIAS>
```

## Additional Information

- Endpoints are stored in `utils/Endpoints.java`.
- An example custom bean validator is available in `validator/nowhitespace`.
- JaCoCo is configured for test coverage. Generate a report using:

```bash
mvn clean verify
```

The generated report can be found at:

```text
target/site/jacoco/index.html
```

- The application supports English (`en`) and Dutch (`nl`) locales by default.
- Locale configuration is located in `config/LocaleConfig.java`.
- Validation messages are configured using `resources/messages.properties`.
- `util/MessageResolver` can be used to retrieve localized messages.
- Incoming requests are validated using the `@Validated` annotation.
- Validation groups allow the same bean to use different validation rules for different use cases, such as creating and updating a user.