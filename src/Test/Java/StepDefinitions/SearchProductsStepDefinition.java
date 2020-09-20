package StepDefinitions;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import javax.swing.text.AbstractDocument;
import java.util.HashMap;
import java.util.Map;
import static io.restassured.RestAssured.enableLoggingOfRequestAndResponseIfValidationFails;
import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;


public class SearchProductsStepDefinition {
    private static String sessionToken = "m00mdle0c8ykn1tc0tnaphty1vikusfe";
    private RequestSpecification request;
    private Response response;

    @Given("that the customer is on the Home page")
    public void that_the_customer_is_on_the_Home_page() {
        String baseUri = "https://magento.abox.co.za/rest/V1/";

        String loginPath = "integration/admin/token";
        String productCatalogPath = "products";
        String apiUserName = "training_api_user";
        String apiPassword = "PtkekYqgRZW8pCVN";
        String sessionToken = "";

        // get session token using rest assured

        sessionToken = given()
                .baseUri(baseUri)
                .basePath(loginPath)
                .queryParam("Username", apiUserName)
                .queryParam("Password", apiPassword)
                .when()
                .post()
                .then()
                .statusCode(200)
                .extract()
                .body().asString();

        System.out.println(sessionToken.toString());
    }


    @Given("enters a product name in the search field")
    public void enters_a_products_name_in_the_search_field() {
        String baseUri = "https://magento.abox.co.za/rest/V1/";
        Map<String, String> headers = new HashMap<>();

        headers.put("Content-Type", "application/json");

        headers.put("Authorization", sessionToken);

        request = given()
                .headers(headers)
                .baseUri(baseUri)
                .basePath("search")
                .queryParam("searchCriteria[requestName]", "quick_search_container")
                .queryParam("searchCriteria[filter_groups][0][filters][0][field]", "search_term")
                .queryParam("searchCriteria[filter_groups][0][filters][0][value]", "jackets");




    }


    @When("the customer clicks the search icon to search")
    public void the_customer_clicks_the_search_icon_to_search() {
        response = request.when().get();
    }

    @Then("the system should return a list of search results")
    public void the_WebPage_should_return_a_list_of_search_results() {
        String responseString = response
                .then()
                .assertThat()
                .statusCode(200)
                .and()
                .contentType(ContentType.JSON)
                .assertThat()
                .body("total_count", equalTo(9))
                .extract()
                .asString();

        System.out.println("Response String is: " + responseString);
    }

}
