package utils.restapi;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import utils.config.Config;

import static io.restassured.RestAssured.given;

public class RestAPIUtils {


//   1 - way - private static RequestSpecification requestSpecification = new RequestSpecBuilder()
//            .setBaseUri(TrelloEndPoints.URL)
//            .setContentType(ContentType.JSON)
//            .build();
//
//    public static void main(String[] args) {
//        given().spec(requestSpecification);
//    }



    /**
     * TODO:
     * Create a method that returns RequestSpecification
     * with the following headers: AcceptType,ContentType
     *
     * @return RequestSpecification
     */
    public static RequestSpecification requestSpecification() {
//        RestAssured.given() - but then no need static import static io.restassured.RestAssured.given;
        return given()
                .baseUri(Config.getPropertiesValue("food_delivery_main_url"))
                .accept(ContentType.JSON)
                .and()
                .contentType(ContentType.JSON);
    }


}
