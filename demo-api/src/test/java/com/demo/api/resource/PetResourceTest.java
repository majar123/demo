package com.demo.api.resource;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;

@QuarkusTest
class PetResourceTest {

    private Long ownerId;

    @BeforeEach
    void createOwner() {
        ownerId = given()
                .contentType(ContentType.JSON)
                .body("""
                        {
                            "firstName": "John",
                            "lastName": "Doe",
                            "phone": "555-1234",
                            "address": "123 Main St"
                        }
                        """)
                .when().post("/owners")
                .then()
                .statusCode(201)
                .extract().jsonPath().getLong("id");
    }

    @Test
    void createPet_shouldReturn201() {
        given()
                .contentType(ContentType.JSON)
                .body(String.format("""
                        {
                            "name": "Buddy",
                            "birthDate": "2020-03-15",
                            "type": "DOG",
                            "ownerId": %d
                        }
                        """, ownerId))
                .when().post("/pets")
                .then()
                .statusCode(201)
                .body("id", notNullValue())
                .body("name", equalTo("Buddy"))
                .body("type", equalTo("DOG"));
    }

    @Test
    void getPetById_shouldReturn200() {
        Long petId = given()
                .contentType(ContentType.JSON)
                .body(String.format("""
                        {
                            "name": "Whiskers",
                            "birthDate": "2019-07-22",
                            "type": "CAT",
                            "ownerId": %d
                        }
                        """, ownerId))
                .when().post("/pets")
                .then()
                .statusCode(201)
                .extract().jsonPath().getLong("id");

        given()
                .when().get("/pets/" + petId)
                .then()
                .statusCode(200)
                .body("name", equalTo("Whiskers"))
                .body("type", equalTo("CAT"));
    }

    @Test
    void getPetById_notFound_shouldReturn404() {
        given()
                .when().get("/pets/99999")
                .then()
                .statusCode(404);
    }

    @Test
    void getPetsByOwner_shouldReturn200() {
        given()
                .contentType(ContentType.JSON)
                .body(String.format("""
                        {
                            "name": "Tweety",
                            "birthDate": "2021-11-05",
                            "type": "BIRD",
                            "ownerId": %d
                        }
                        """, ownerId))
                .when().post("/pets")
                .then()
                .statusCode(201);

        given()
                .when().get("/pets/owner/" + ownerId)
                .then()
                .statusCode(200);
    }

    @Test
    void createPet_ownerNotFound_shouldReturn404() {
        given()
                .contentType(ContentType.JSON)
                .body("""
                        {
                            "name": "Ghost",
                            "birthDate": "2020-01-01",
                            "type": "DOG",
                            "ownerId": 99999
                        }
                        """)
                .when().post("/pets")
                .then()
                .statusCode(404);
    }

    @Test
    void deletePet_shouldReturn204() {
        Long petId = given()
                .contentType(ContentType.JSON)
                .body(String.format("""
                        {
                            "name": "DeleteMe",
                            "birthDate": "2020-01-01",
                            "type": "RABBIT",
                            "ownerId": %d
                        }
                        """, ownerId))
                .when().post("/pets")
                .then()
                .statusCode(201)
                .extract().jsonPath().getLong("id");

        given()
                .when().delete("/pets/" + petId)
                .then()
                .statusCode(204);

        given()
                .when().get("/pets/" + petId)
                .then()
                .statusCode(404);
    }
}
