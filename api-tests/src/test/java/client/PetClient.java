package client;


import dto.PetRequestDto;
import io.restassured.response.Response;
import io.restassured.http.ContentType;

import java.io.File;

import static io.restassured.RestAssured.given;

public class PetClient {

    private static final String PET_ENDPOINT = "/pet";

    public  Response createPet(PetRequestDto body) {
        return given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post(PET_ENDPOINT);
    }

    public Response getPetById(long id) {
        return given()
                .when()
                .get(PET_ENDPOINT + "/" + id);
    }

    public Response getPetFindByStatus(String status){

        return  given()
                .queryParam("status", status)
                .when()
                .get(PET_ENDPOINT + "/" +"findByStatus") ;

    }


    public Response uploadImage(Long petId, File file, String additionalMetadata) {

        return given()
                .pathParam("petId", petId)
                .multiPart("file", file)
                .multiPart("additionalMetadata", additionalMetadata)
                .when()
                .post(PET_ENDPOINT + "/"+"{petId}/uploadImage");
    }

    public Response updatePet(Object body) {
        return given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .put(PET_ENDPOINT);
    }

    public Response updatePetWithId(long id, String name, String status) {
        return given()
                .contentType(ContentType.URLENC)
                .formParam("name", name)
                .formParam("status", status)
                .when()
                .post(PET_ENDPOINT + "/" + id);
    }

    public Response deletePet(long id) {
        return given()
                .header("api_key", "special-key")
                .contentType(ContentType.JSON)
                .when()
                .delete(PET_ENDPOINT + "/" + id);
    }
}
