import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

public class Searchjackets {

    private static String sessionToken = "jgo4aax6fr052djwrt8q8cbwecelgld7";
    public static String baseUri = "https://magento.abox.co.za/rest/V1/";
    public static void main(String[] args) {

        //Set HTTP Headers
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", sessionToken);

        RequestSpecification  request = given()
                .headers(headers)
                .baseUri(baseUri)
                .basePath("Search")
                .queryParam("searchCriteria[requestName]","quick_search_container")
                .queryParam("searchCriteria[filter_groups][0][filters][0][field]","search_term")
                .queryParam("searchCriteria[filter_groups][0][filters][0][value]","jackets");

        Response response = request.when().get();
        String responseString = response.then()
                .assertThat()
                .statusCode(200)
                .and()
                .contentType(ContentType.JSON)
                .assertThat()
                .body("total_count",equalTo(9))
                .extract()
                .body().asString();
        System.out.println("Response String is: " + responseString);
    }
}

