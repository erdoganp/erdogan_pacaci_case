package tests.pet;

import base.BaseTest;
import client.PetClient;
import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeletePetTests extends BaseTest {

    PetClient petClient = new PetClient();

    @Test
    @Description("Delete a pet by id, verify that the pet is deleted successfully and the response status code is 200.")
    void shouldDeletePetSuccessfully_Case_1() {

        int petId = 1;

      Response response=  petClient.deletePet(petId)
                .then()
                .statusCode(200)
                .extract()
                .response();

      assertEquals(petId, response.jsonPath().getInt("message"), "Deleted pet id should match the requested id");

    }

    //@Test
    @Description("Delete a non-existing pet by id, verify that the response status code is 404.")
    void shouldDeleteNonExistingPet_Case_2() {

        int nonExistingPetId = 9999;

        petClient.deletePet(nonExistingPetId)
                .then()
                .statusCode(404);
    }
}

