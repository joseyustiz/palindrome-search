## Architecture and Patterns
* Layered Architecture
* Gateway (Enterprise Application Pattern)
* Builder

## Principles and Practices
* SOLID
* TDD

## Technology
* Java 8
* Spring Boot
* Spring MVC
* Spring Data
* Lombok
* Groovy
* Spock
* Gradle
* MongoDB

## Installation Process
1. Run the database container:

Follow the instructions at https://github.com/walmartdigital/products-db

2. Create a docker network to communicate the Spring Boot application with the database 

```console
docker network create --attachable api-net
```
 
3. Attach database container to the **api-net** network

```console
docker network connect api-net mongodb-local
```

**mongodb-local** is the _default_ name of the database container 

4. Building and packaging the Spring Boot application

Run the following command in the root of the project:

```console
./gradlew bootBuildImage
```

It takes advantage of the last available feature of **Buildpacks** from Spring Boot 2.3.x, which creates the image **docker.io/library/walmart:0.0.1-SNAPSHOT**   

5. Run the Spring Boot application

```console
docker run --rm --network api-net -e SPRING_DATA_MONGODB_USERNAME=productListUser -e SPRING_DATA_MONGODB_PASSWORD=productListPassword -e SPRING_DATA_MONGODB_HOST=mongodb-local -e SPRING_DATA_MONGODB_PORT=27017 -p 8080:8080 --name backend docker.io/library/walmart:0.0.1-SNAPSHOT
```

productListUser and productListPassword are the default credentials of the mongo database from https://github.com/walmartdigital/products-db 

## Use of full-text instead of Regex
This application uses full-text search of Mongo DB, which provides a better performance and scalability than Regex. For more information, go to https://docs.mongodb.com/manual/text-search 

## Example requests
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
 
The application allows letters from the spanish alphabet, numbers and spaces 

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
curl --location --request GET 'localhost:8080/products?phrase=1'
```
```json
[
    {
        "id": 1,
        "description": "rlñlw brhrka",
        "brand": "ooy eqrceli",
        "imageUrl": "www.lider.cl/catalogo/images/whiteLineIcon.svg",
        "price": 498724.0,
        "percentageOfDiscount": 0.0,
        "priceMinusDiscount": 498724.0
    }
]
```
### Searching by Palindrome ID
```console
curl --location --request GET 'localhost:8080/products?phrase=1'
```
```json
[
    {
        "id": 11,
        "description": "fqfwt ikpxov",
        "brand": "iñmfdpd",
        "imageUrl": "www.lider.cl/catalogo/images/gamesIcon.svg",
        "price": 533752.0,
        "percentageOfDiscount": 50.0,
        "priceMinusDiscount": 266876.0
    }
]
```
### Searching by Palindrome Phrase
```console
curl --location --request GET 'localhost:8080/products?phrase=saas'
```

```json
[
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
        "id": 1716,
        "description": "rgvqme kvska",
        "brand": "saas",
        "imageUrl": "www.lider.cl/catalogo/images/bedRoomIcon.svg",
        "price": 891826.0,
        "percentageOfDiscount": 50.0,
        "priceMinusDiscount": 445913.0
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
        "id": 1365,
        "description": "veat yicpskña",
        "brand": "saas",
        "imageUrl": "www.lider.cl/catalogo/images/gamesIcon.svg",
        "price": 139252.0,
        "percentageOfDiscount": 50.0,
        "priceMinusDiscount": 69626.0
    },
    {
        "id": 1353,
        "description": "aywoc ñonuge",
        "brand": "saas",
        "imageUrl": "www.lider.cl/catalogo/images/toysIcon.svg",
        "price": 581781.0,
        "percentageOfDiscount": 50.0,
        "priceMinusDiscount": 290890.5
    }
]
```
### Searching by a No-Palindrome Phrase
```console
curl --location --request GET 'localhost:8080/products?phrase=aywoc'
```

```json
[
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
    },
    {
        "id": 1789,
        "description": "aywoc ñonuge",
        "brand": "weñxoab",
        "imageUrl": "www.lider.cl/catalogo/images/whiteLineIcon.svg",
        "price": 874206.0,
        "percentageOfDiscount": 0.0,
        "priceMinusDiscount": 874206.0
    },
    {
        "id": 1381,
        "description": "aywoc ñonuge",
        "brand": "lkdznamm",
        "imageUrl": "www.lider.cl/catalogo/images/whiteLineIcon.svg",
        "price": 214450.0,
        "percentageOfDiscount": 0.0,
        "priceMinusDiscount": 214450.0
    },
    {
        "id": 1353,
        "description": "aywoc ñonuge",
        "brand": "saas",
        "imageUrl": "www.lider.cl/catalogo/images/toysIcon.svg",
        "price": 581781.0,
        "percentageOfDiscount": 0.0,
        "priceMinusDiscount": 581781.0
    },
    {
        "id": 1106,
        "description": "aywoc ñonuge",
        "brand": "owh xenvxen",
        "imageUrl": "www.lider.cl/catalogo/images/whiteLineIcon.svg",
        "price": 428488.0,
        "percentageOfDiscount": 0.0,
        "priceMinusDiscount": 428488.0
    },
    {
        "id": 909,
        "description": "aywoc ñonuge",
        "brand": "fumgxfd",
        "imageUrl": "www.lider.cl/catalogo/images/bedRoomIcon.svg",
        "price": 486693.0,
        "percentageOfDiscount": 0.0,
        "priceMinusDiscount": 486693.0
    },
    {
        "id": 863,
        "description": "aywoc ñonuge",
        "brand": "hgb dizñchz",
        "imageUrl": "www.lider.cl/catalogo/images/computerIcon.svg",
        "price": 110929.0,
        "percentageOfDiscount": 0.0,
        "priceMinusDiscount": 110929.0
    },
    {
        "id": 116,
        "description": "aywoc ñonuge",
        "brand": "jvvxqjq",
        "imageUrl": "www.lider.cl/catalogo/images/homeIcon.svg",
        "price": 106757.0,
        "percentageOfDiscount": 0.0,
        "priceMinusDiscount": 106757.0
    }
]
```
