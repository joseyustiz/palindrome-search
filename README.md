## Architecture and Patterns
* Layered Architecture
* Gateway (Enterprise Application Pattern)
* Builder

## Principles and Practices
* SOLID
* TDD

## Backend Technology
* Java 8
* Spring Boot
* Spring MVC
* Spring Data
* Lombok
* Groovy
* Spock
* Gradle
* MongoDB

## Frontend Technology
* TypeScript
* Bootstrap
* Angular

## Installation Process

**1. Building and packaging the Spring Boot application**

Run the following command in the root of the project:

```console
./gradlew bootBuildImage
```

It takes advantage of the latest available features of **Buildpacks** from Spring Boot 2.3.x, which creates the image **docker.io/library/walmart:0.0.1-SNAPSHOT**   

**2. Run the containers**
```console
docker-compose -f app.yml up -d
```
This commando will run the mongo database (walmart_products-db_1), the spring boot application (walmart_backend_1) and the angular application (walmart_frontend_1) 

**3. Import products data into mongo**
```console
docker exec walmart_products-db_1 bash -c 'bash /database/import.sh localhost'
```
NOTE: becase mongoimport requires mongod to be up and running, the import.sh script cannot be called in the Dockerfile.
  
**4. Open the application in the browser or execute HTTP GET to the endpoints**

Go to http://localhost:4200 or execute requests to the endpoint as explained at [Example requests](#example-requests). 

## Using full-text search instead of Regex search
This application uses full-text search technology of Mongo DB, which provides a better performance and scalability than Regex search. For more information, go to https://docs.mongodb.com/manual/text-search 

## Example requests

The implementation supports paging and sorting. The optional attributes are: **page**, **size** and **sort**. Sorting works only for all the fields, excepting percentageOfDiscount and priceMinusDiscount.

### Bad Request Search
```console
curl --location --request GET 'localhost:8080/products?phrase=-11'
```

```json
{
     "status": "BAD_REQUEST",
     "errors": [
         "phrase: contains character not allowed"
     ]
 }
```
 
The application allows characters from the spanish alphabet, numbers and spaces 

### Searcing by a Phrase Shorter Than 3 Character
```console
curl --location --request GET 'localhost:8080/products?phrase=ab'
```
```json
{
    "status": "BAD_REQUEST",
    "errors": [
        "phrase: invalid phrase"
    ]
}
```

### Searching by ID
```console
curl --location --request GET 'localhost:8080/products?phrase=1&page=1&size=5'
```
```json
{
    "content": [
        {
            "id": 1,
            "description": "rlñlw brhrka",
            "brand": "ooy eqrceli",
            "imageUrl": "www.lider.cl/catalogo/images/whiteLineIcon.svg",
            "price": 498724.0,
            "percentageOfDiscount": 0.0,
            "priceMinusDiscount": 498724.0
        }
    ],
    "pageable": "INSTANCE",
    "last": true,
    "totalPages": 1,
    "totalElements": 1,
    "first": true,
    "numberOfElements": 1,
    "sort": {
        "unsorted": true,
        "sorted": false,
        "empty": true
    },
    "size": 1,
    "number": 0,
    "empty": false
}
```
### Searching by Palindrome ID
```console
curl --location --request GET 'localhost:8080/products?phrase=11&page=1&size=5'
```
```json
{
    "content": [
        {
            "id": 11,
            "description": "fqfwt ikpxov",
            "brand": "iñmfdpd",
            "imageUrl": "www.lider.cl/catalogo/images/gamesIcon.svg",
            "price": 533752.0,
            "percentageOfDiscount": 50.0,
            "priceMinusDiscount": 266876.0
        }
    ],
    "pageable": "INSTANCE",
    "last": true,
    "totalPages": 1,
    "totalElements": 1,
    "first": true,
    "numberOfElements": 1,
    "sort": {
        "unsorted": true,
        "sorted": false,
        "empty": true
    },
    "size": 1,
    "number": 0,
    "empty": false
}
```
### Searching by Palindrome Phrase
```console
curl --location --request GET 'localhost:8080/products?phrase=saas&page=1&size=5&sort=price,asc'
```

```json
{
    "content": [
        {
            "id": 1365,
            "description": "veat yicpskña",
            "brand": "saas",
            "imageUrl": "www.lider.cl/catalogo/images/gamesIcon.svg",
            "price": 139252.0,
            "percentageOfDiscount": 50.0,
            "priceMinusDiscount": 69626.0
        },
        {
            "id": 1826,
            "description": "bcth janwihml",
            "brand": "saas",
            "imageUrl": "www.lider.cl/catalogo/images/babyIcon.svg",
            "price": 569448.0,
            "percentageOfDiscount": 50.0,
            "priceMinusDiscount": 284724.0
        },
        {
            "id": 1353,
            "description": "aywoc ñonuge",
            "brand": "saas",
            "imageUrl": "www.lider.cl/catalogo/images/toysIcon.svg",
            "price": 581781.0,
            "percentageOfDiscount": 50.0,
            "priceMinusDiscount": 290890.5
        },
        {
            "id": 1631,
            "description": "doww inunojbs",
            "brand": "saas",
            "imageUrl": "www.lider.cl/catalogo/images/homeIcon.svg",
            "price": 589174.0,
            "percentageOfDiscount": 50.0,
            "priceMinusDiscount": 294587.0
        },
        {
            "id": 1716,
            "description": "rgvqme kvska",
            "brand": "saas",
            "imageUrl": "www.lider.cl/catalogo/images/bedRoomIcon.svg",
            "price": 891826.0,
            "percentageOfDiscount": 50.0,
            "priceMinusDiscount": 445913.0
        }
    ],
    "pageable": {
        "sort": {
            "sorted": true,
            "unsorted": false,
            "empty": false
        },
        "pageNumber": 0,
        "pageSize": 5,
        "offset": 0,
        "unpaged": false,
        "paged": true
    },
    "last": true,
    "totalPages": 1,
    "totalElements": 5,
    "numberOfElements": 5,
    "first": true,
    "sort": {
        "sorted": true,
        "unsorted": false,
        "empty": false
    },
    "size": 5,
    "number": 0,
    "empty": false
}
```
### Searching by a No-Palindrome Phrase
```console
curl --location --request GET 'localhost:8080/products?phrase=aywoc&page=1&size=2'
```

```json
{
    "content": [
        {
            "id": 2911,
            "description": "aywoc ñonuge",
            "brand": "ikd qmflpsq",
            "imageUrl": "www.lider.cl/catalogo/images/babyIcon.svg",
            "price": 486839.0,
            "percentageOfDiscount": 0.0,
            "priceMinusDiscount": 486839.0
        },
        {
            "id": 2705,
            "description": "aywoc ñonuge",
            "brand": "yeazgeejt",
            "imageUrl": "www.lider.cl/catalogo/images/whiteLineIcon.svg",
            "price": 21742.0,
            "percentageOfDiscount": 0.0,
            "priceMinusDiscount": 21742.0
        }
    ],
    "pageable": {
        "sort": {
            "sorted": false,
            "unsorted": true,
            "empty": true
        },
        "pageNumber": 0,
        "pageSize": 2,
        "offset": 0,
        "unpaged": false,
        "paged": true
    },
    "last": false,
    "totalPages": 5,
    "totalElements": 9,
    "numberOfElements": 2,
    "first": true,
    "sort": {
        "sorted": false,
        "unsorted": true,
        "empty": true
    },
    "size": 2,
    "number": 0,
    "empty": false
}
```
### Searching by a Phrase That Isn't Stored at the Brand or Description of the Product
```console
curl --location --request GET 'localhost:8080/products?phrase=abc'
```
```json
{
    "content": [],
    "pageable": {
        "sort": {
            "sorted": false,
            "unsorted": true,
            "empty": true
        },
        "pageNumber": 0,
        "pageSize": 20,
        "offset": 0,
        "unpaged": false,
        "paged": true
    },
    "last": true,
    "totalPages": 0,
    "totalElements": 0,
    "numberOfElements": 0,
    "first": true,
    "sort": {
        "sorted": false,
        "unsorted": true,
        "empty": true
    },
    "size": 20,
    "number": 0,
    "empty": true
}
```