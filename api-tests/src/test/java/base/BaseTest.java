package base;

import com.github.tomakehurst.wiremock.WireMockServer;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import utils.ConfigReader;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class BaseTest {

    protected static WireMockServer wireMockServer;

    @BeforeAll
    static void setup(){


        final String BASE_URL = ConfigReader.getProperty("base.url");
        System.out.println(">>> USING BASE_URL = " + BASE_URL);
        RestAssured.baseURI = BASE_URL;

        if (BASE_URL.contains("localhost")) {
            wireMockServer = new WireMockServer(8080);
            wireMockServer.start();
            configureFor("localhost", 8080);



            wireMockServer.stubFor(put(urlEqualTo("/pet"))
                    .withRequestBody(containing("\"id\":null"))
                    .willReturn(aResponse()
                            .withStatus(400)
                            .withHeader("Content-Type", "application/json")
                            .withBody("{\"message\":\"Invalid ID supplied\"}")));

            wireMockServer.stubFor(put(urlEqualTo("/pet"))
                    .withRequestBody(containing("\"id\":999999"))
                    .willReturn(aResponse()
                            .withStatus(404)
                            .withHeader("Content-Type", "application/json")
                            .withBody("{\"message\":\"Pet not found\"}")));
        }

        // RestAssured setup
        RestAssured.baseURI = BASE_URL;
        RestAssured.filters(
                new RequestLoggingFilter(),
                new ResponseLoggingFilter()
        );
    }




    @AfterAll
    static void tearDown() {
        if (wireMockServer != null) {
            wireMockServer.stop();
        }
    }
}
