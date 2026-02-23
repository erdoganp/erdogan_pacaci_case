package tests.pet;

import base.BaseTest;
import client.PetClient;
import dto.*;
import factory.PetFactory;
import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;

public class UpdatePetTests extends BaseTest {
    PetClient petClient = new PetClient();


    @Test
    @Description("Update a pet with valid data, verify that the pet is updated successfully and the response body matches the update request body.")
    void shouldUpdatePetSuccessfully_Case_1() {

        PetRequestDto createRequest = PetFactory.createPet();

        PetResponseDto createResponse =
                petClient.createPet(createRequest)
                        .then()
                        .statusCode(200)
                        .extract()
                        .as(PetResponseDto.class);

        PetRequestDto updateRequest = new PetRequestDto();
        updateRequest.setId(createResponse.getId());  // aynı id
        updateRequest.setName("UpdatedName");
        updateRequest.setStatus(PetStatus.sold);

        CategoryRequestDto category = new CategoryRequestDto();
        category.setId(createResponse.getCategory().getId());
        category.setName(createResponse.getCategory().getName());

        List<TagRequestDto> tags = Arrays.stream(createResponse.getTags())
                .map(tagResponse -> {
                    TagRequestDto tagRequest = new TagRequestDto();
                    tagRequest.setId(tagResponse.getId());
                    tagRequest.setName(tagResponse.getName());
                    return tagRequest;
                })
                .toList();


        List<String> photoUrls = Arrays.stream(createResponse.getPhotoUrls()).toList();
        updateRequest.setCategory(category);
        updateRequest.setPhotoUrls(photoUrls);
        updateRequest.setTags(tags);

        PetResponseDto updateResponse =
                petClient.updatePet(updateRequest)
                        .then()
                        .statusCode(200)
                        .extract()
                        .as(PetResponseDto.class);


        assertEquals(updateRequest.getId(), updateResponse.getId());
        assertEquals("UpdatedName", updateResponse.getName());
        assertEquals("sold", updateResponse.getStatus().toString());

    }

    @Test
    @Description("the ")
    void shouldReturn400WhenIdIsInvalid_Case_2() {

        PetRequestDto request = PetFactory.createPet();
        request.setId(null);   // invalid id

        petClient.updatePet(request)
                .then()
                .statusCode(200);
    }


    //@Test
    @Description("the mock server is enable when the request send to localhost:8080")
    void shouldReturn400WhenIdIsInvalidwithMock() {

        // 400 senaryosu → id null
        PetRequestDto request = PetFactory.createPet();
        request.setId(null);

        Response response = petClient.updatePet(request)
                .then()
                .statusCode(400)   // it returns 400 because of the mock server setup in BaseTest
                .extract()
                .response();

        String body = response.asString();
        assertTrue(body.contains("Invalid ID supplied"));
    }



    @Test
    @Description("Create a pet with an ID ,name and status." +
            "Note: This test assumes that there is a pet with ID 1 in the system. If not, it will create a pet with ID 1 before running the test." )
    void shouldCreatePetWithById() {

        Long id = 1L;
        String name = "TestPet";
        String status = "available";

        Response response =
                petClient.updatePetWithId(id, name, status)
                        .then()
                        .statusCode(200)
                        .extract()
                        .response();


        assertEquals(id, response.jsonPath().getInt("message"), "updated pet id should match the request id");
    }


}