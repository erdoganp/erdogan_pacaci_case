package tests.pet;

import base.BaseTest;
import client.PetClient;
import dto.PetResponseDto;
import dto.PetStatus;
import io.qameta.allure.Description;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GetPetTest extends BaseTest {


    PetClient petClient = new PetClient();

    @Test
    @Description("Get pets by status, verify that the response contains only pets with the specified status and the response status code is 200.")
    void shouldGetPeyByStatusSuccessfully_Case_1() {

        PetStatus status = PetStatus.available;

        PetResponseDto[] pets = petClient.getPetFindByStatus(status.name())
                .then()
                .statusCode(200)
                .extract()
                .as(PetResponseDto[].class);


        assertTrue(pets.length > 0, "List of pets should not be empty");

        for (PetResponseDto pet : pets) {
            assertEquals(status.name(), pet.getStatus().name());
        }


    }

    @Test
    @Description("Get pet by id, verify that the response contains the pet with the specified id and the response status code is 200.")
    void shouldGetPetByIdSuccessfully_Case_2() {

        PetResponseDto pet = petClient.getPetById(1)
                .then()
                .statusCode(200)
                .extract()
                .as(PetResponseDto.class);

        assertEquals(1, pet.getId());

    }
}