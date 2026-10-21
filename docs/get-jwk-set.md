# Get JWK set

Returns the JSON Web Key Set used to verify JWT signatures issued by the authorization server.

## Request

| Detail           | Value                     |
|------------------|---------------------------|
| Method           | `GET`                     |
| URL              | `/.well-known/jwks.json`  |
| Authorization    | Not required              |
| Consumes         | None                      |
| Body             | None                      |
| Path parameters  | None                      |
| Query parameters | None                      |

Object definitions are in the collection documentation.

## Success response

| Detail   | Value              |
|----------|--------------------|
| Status   | `200 OK`           |
| Produces | `application/json` |
| Body     | `JwkSetResponse`   |

## Error responses

No endpoint-specific client error response is defined.
