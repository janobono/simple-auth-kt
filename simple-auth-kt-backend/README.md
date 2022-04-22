# simple auth backend kt

Simple authentication backend, roles and users management api.

## build

```
docker build -t sk.janobono/simple-auth-backend .
```

## environment variables

| Name                | Default value                        |
|---------------------|--------------------------------------|
| PORT                | 8080                                 | 
| CONTEXT_PATH        | /api/backend                         |
| LOG_LEVEL           | debug                                | 
| DB_URL              | jdbc:postgresql://localhost:5432/app |  
| DB_USER             | app                                  | 
| DB_PASS             | app                                  | 
| APP_ISSUER          | simple-auth                          | 
| APP_JWT_EXPIRATION  | 7200                                 |
| APP_JWT_PRIVATE_KEY | *                                    | 
| APP_JWT_PUBLIC_KEY  | **                                   | 

- *, ** - generated with `sk.janobono.KeyGenerator`

## endpoints

Documentation is generated in [OpenApi](https://www.openapis.org/) 3.0 format, you will find it in
`./target/api-docs.yml` after build. You can use [swagger editor](https://editor.swagger.io/) to preview api.

For local dev run you can start database with:

```
docker-compose -f docker-compose-dev.yaml up -d
```

Import test user with:

```
docker cp ./test_user.sql simple-auth_db_1:/test_user.sql
docker exec -it simple-auth_db_1 bash
psql "dbname='app' user='app' password='app' host='localhost'" -f /test_user.sql
```

In project root directory.

### POST /api/backend/authenticate

```
curl --header "Content-Type: application/json" \
--request POST \
--data '{"username":"trevor.ochmonek.dev","password":"MelmacAlf+456"}' \
http://localhost:8080/api/backend/authenticate
```

result:

```json
{
  "bearer": "eyJ..."
}
```

### GET /api/backend/authorities

```
curl --header "Content-Type: application/json" \
--header "Authorization: Bearer REPLACE_ME_WITH_RIGHT_BEARER" \
--request GET \
http://localhost:8080/api/backend/authorities
```

```json
{
  "content": [
    {
      "id": 1,
      "name": "view-users"
    },
    {
      "id": 2,
      "name": "manage-users"
    }
  ],
  "pageable": {
    "sort": {
      "empty": true,
      "sorted": false,
      "unsorted": true
    },
    "offset": 0,
    "pageNumber": 0,
    "pageSize": 20,
    "paged": true,
    "unpaged": false
  },
  "last": true,
  "totalPages": 1,
  "totalElements": 2,
  "size": 20,
  "number": 0,
  "sort": {
    "empty": true,
    "sorted": false,
    "unsorted": true
  },
  "first": true,
  "numberOfElements": 2,
  "empty": false
}
```
