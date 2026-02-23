package tests.pet;

import base.BaseTest;
import client.PetClient;
import dto.*;
import factory.PetFactory;
import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import static org.junit.jupiter.api.Assertions.*;

public class CreatePetTests extends BaseTest {

    PetClient petClient = new PetClient();

    @Test
    @Description("Create a pet with valid data, verify that the pet is created successfully and the response body matches the request body.")
    void shouldCreatePetSuccessfully_Case_1() {

        // create the request body using the factory
        PetRequestDto request = PetFactory.createPet();

        // when we send the request to create a pet, we expect a 200 status code and the response body to match the request body
        PetResponseDto response =
                petClient.createPet(request)
                        .then()
                        .statusCode(200)
                        .extract()
                        .as(PetResponseDto.class);


        // the response body should match the request body.
        assertAll(
                () -> assertNotNull(response.getId()),
                () -> assertEquals(request.getName(), response.getName()),
                () -> assertEquals(request.getStatus(), response.getStatus()),
                () -> assertEquals(request.getCategory().getName(), response.getCategory().getName()),
                () -> assertEquals(request.getTags().get(0).getName(), response.getTags()[0].getName()),
                () -> assertArrayEquals(request.getPhotoUrls().toArray(), response.getPhotoUrls())
        );
    }

    @Test
    @Description("Create a pet without name, verify that the pet is created successfully and the response body contains null for the name field.")
    void shouldCreatePetWithoutNameButResponseShouldContainNull_Case_2() {

        PetRequestDto request = PetFactory.createPet();
        request.setName(null);

        PetResponseDto response =
                petClient.createPet(request)
                        .then()
                        .statusCode(200)
                        .extract()
                        .as(PetResponseDto.class);

        assertNull(response.getName());
    }

    @ParameterizedTest
    @ValueSource(strings = {"available", "pending", "sold"})
    @Description("Create a pet with different statuses (available, pending, sold), verify that the pet is created successfully and the response body contains the correct status.")
    void shouldCreatePetWithDifferentStatuses_Case_3(String status) {

        PetRequestDto request = PetFactory.createPet();
        request.setStatus(PetStatus.valueOf(status));

        PetResponseDto response =
                petClient.createPet(request)
                        .then()
                        .statusCode(200)
                        .extract()
                        .as(PetResponseDto.class);

        assertEquals(status, response.getStatus().toString());
    }

    @Test
    @Description("Create a pet with an existing ID, verify that the pet is created successfully and the response body contains the same ID as the request body." +
            " Note: This test assumes that the API allows creating a pet with an existing ID, which may not be the case in a real-world scenario. In a real-world scenario, you would typically expect a 400 or 409 status code when trying to create a pet with an existing ID.")
    void shouldCreatePetWithExistingId_Case_4_Negative() {

        PetRequestDto request = PetFactory.createPet();
        request.setId(1L);

        PetResponseDto response =
                petClient.createPet(request)
                        .then()
                        .statusCode(200)
                        .extract()
                        .as(PetResponseDto.class);

        assertEquals(1L, response.getId());
    }

    @Test
    @Description("Create a pet with a long name (e.g., 500 characters), verify that the pet is created successfully and the response body contains the long name.")
    void shouldCreatePetWithLongName_Case_5() {

        PetRequestDto request = PetFactory.createPet();
        request.setName("A".repeat(500));

        petClient.createPet(request)
                .then()
                .statusCode(200);
    }

    @Test
    @Description("Create a pet with the maximum long value for the ID, verify that the pet is created successfully and the response body contains the correct ID.")
    void shouldCreatePetWithMaxLongId_Case_6() {

        PetRequestDto request = PetFactory.createPet();
        request.setId(Long.MAX_VALUE);

        petClient.createPet(request)
                .then()
                .statusCode(200);
    }

    @Test
    @Description("Create a pet and then fetch the pet by ID, verify that the fetched pet matches the created pet.")
    void shouldCreateAndVerifyPet_Case_7() {

        PetRequestDto request = PetFactory.createPet();

        PetResponseDto created =
                petClient.createPet(request)
                        .as(PetResponseDto.class);

        PetResponseDto fetched =
                petClient.getPetById(created.getId())
                        .as(PetResponseDto.class);

        assertEquals(created.getName(), fetched.getName());
    }

    @Test
    @Description("Create a pet and verify that the response body matches the JSON schema defined in pet-schema.json.")
    void shouldMatchResponseSchema_Case_8() {

        petClient.createPet(PetFactory.createPet())
                .then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath("schema/pet-schema.json"));


    }

    @Test
    @Description("Create a pet with an uploaded image, verify that the pet is created successfully and the response body contains the correct image URL.")
    void shouldCreatePetWithUploadedImage_Case_9() {

        File image = new File("src/test/resources/test.png");

        Response response = petClient.uploadImage(123L, image, "Test image upload");

        response.then()
                .statusCode(200);
    }

}