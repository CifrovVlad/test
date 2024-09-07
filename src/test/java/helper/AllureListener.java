package helper;

import io.qameta.allure.restassured.AllureRestAssured;

public class AllureListener {
    private static final AllureRestAssured FILTER = new AllureRestAssured();


    public static AllureRestAssured getAllureRestAssured() {
        FILTER.setRequestTemplate("http-request.ftl");
        FILTER.setResponseTemplate("http-response.ftl");
        return FILTER;
    }
}
