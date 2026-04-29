package com.demo.api.resource;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@QuarkusTest
class OwnerResourceTest {

    @Test
    void createOwner_shouldReturn201() {
        given()
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
                .body("id", notNullValue())
                .body("firstName", equalTo("John"))
                .body("lastName", equalTo("Doe"));
    }

    @Test
    void getOwnerById_shouldReturn200() {
        Long id = given()
                .contentType(ContentType.JSON)
                .body("""
                        {
                            "firstName": "Jane",
                            "lastName": "Smith",
                            "phone": "555-5678",
                            "address": "456 Oak Ave"
                        }
                        """)
                .when().post("/owners")
                .then()
                .statusCode(201)
                .extract().jsonPath().getLong("id");

        given()
                .when().get("/owners/" + id)
                .then()
                .statusCode(200)
                .body("firstName", equalTo("Jane"))
                .body("lastName", equalTo("Smith"));
    }

    @Test
    void getOwnerById_notFound_shouldReturn404() {
        given()
                .when().get("/owners/99999")
                .then()
                .statusCode(404);
    }

    @Test
    void updateOwner_shouldReturn200() {
        Long id = given()
                .contentType(ContentType.JSON)
                .body("""
                        {
                            "firstName": "Bob",
                            "lastName": "Brown",
                            "phone": "555-0000",
                            "address": "789 Pine Rd"
                        }
                        """)
                .when().post("/owners")
                .then()
                .statusCode(201)
                .extract().jsonPath().getLong("id");

        given()
                .contentType(ContentType.JSON)
                .body("""
                        {
                            "firstName": "Bobby",
                            "lastName": "Brown",
                            "phone": "555-0001",
                            "address": "789 Pine Rd"
                        }
                        """)
                .when().put("/owners/" + id)
                .then()
                .statusCode(200)
                .body("firstName", equalTo("Bobby"));
    }

    @Test
    void deleteOwner_shouldReturn204() {
        Long id = given()
                .contentType(ContentType.JSON)
                .body("""
                        {
                            "firstName": "Delete",
                            "lastName": "Me",
                            "phone": "555-9999",
                            "address": "000 Delete St"
                        }
                        """)
                .when().post("/owners")
                .then()
                .statusCode(201)
                .extract().jsonPath().getLong("id");

        given()
                .when().delete("/owners/" + id)
                .then()
                .statusCode(204);

        given()
                .when().get("/owners/" + id)
                .then()
                .statusCode(404);
    }
}
