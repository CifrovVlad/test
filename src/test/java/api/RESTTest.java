package api;

import api.Pojo.*;
import config.Specification;
import helper.AllureListener;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import java.time.Clock;
import java.util.*;

import static io.restassured.RestAssured.*;
import static org.hamcrest.core.IsEqual.equalTo;
@Tag("api")
public class RESTTest {
    private final static String URL = "https://reqres.in";
    private final static String URL2 = "https://httpbin.org";
    private final static String URL3 = "https://restful-booker.herokuapp.com";




    @Test
    public void getAllUsers() {
        Specification.installSpecification(Specification.requestSpec(URL), Specification.response200());
        List<UserData> users = given().filter(AllureListener.getAllureRestAssured()).when()
                .get("/api/users?page=2").then()
                .log().all().extract().body().
                jsonPath()
                .getList("data", UserData.class);

        users.forEach(x -> Assertions.assertTrue(x.getAvatar().contains(x.getId().toString())));
        Assertions.assertTrue(users.stream().allMatch(x -> x.getEmail().endsWith("@reqres.in")));
    }

    @Test
    public void getUser() {
        Specification.installSpecification(Specification.requestSpec(URL), Specification.response200());
        Response response = given().filter(AllureListener.getAllureRestAssured()).when().get("/api/users/2").then().log().all().extract().response();

        JsonPath jsonPath = response.jsonPath();
        Assertions.assertEquals(jsonPath.getString("data.id"), "2");

    }

    @Test
    public void getError404() {
        Specification.installSpecification(Specification.requestSpec(URL), Specification.response404());
        Response response = given().filter(AllureListener.getAllureRestAssured()).when().get("/api/users/23").then().log().all().extract().response();

    }

    @Test
    public void getListResourse() {
        Specification.installSpecification(Specification.requestSpec(URL), Specification.response200());
        List<ResourseApi> resourse = given().filter(AllureListener.getAllureRestAssured()).when().get("/api/unknown").then().log().all().extract().body().jsonPath().getList("data", ResourseApi.class);

        List<Integer> ids = resourse.stream().map(ResourseApi::getId).toList();
        Assertions.assertTrue(ids.contains(1));

    }

    @Test
    public void create() {
        Specification.installSpecification(Specification.requestSpec(URL), Specification.response201());
        Map<String, String> params = new HashMap<>();
        params.put("name", "morpheus");
        params.put("job", "leader");

        CreateUser createUser = given().filter(AllureListener.getAllureRestAssured()).body(params).when().post("/api/users").then().log().all().extract().as(CreateUser.class);

        Assertions.assertEquals("leader", createUser.getJob());
        String regix = "(.{5})$";
        String regix1 = "(.{8})$";
        String timeNow = Clock.systemUTC().instant().toString().replaceAll(regix1, "");
        System.out.println(timeNow);
        String timeResponce = createUser.getCreatedAt().replaceAll(regix, "");
        System.out.println(timeResponce);

        Assertions.assertEquals(timeNow, timeResponce);

    }

    @Test
    public void update() {
        Specification.installSpecification(Specification.requestSpec(URL), Specification.response200());
        Map<String, String> params = new HashMap<>();
        params.put("name", "morpheus");
        params.put("job", "zion resident");

        UpdeteUser updeteUser = given().filter(AllureListener.getAllureRestAssured()).body(params).when().put("/api/users/2").then().log().all().extract().as(UpdeteUser.class);

        Assertions.assertEquals("zion resident", updeteUser.getJob());
    }

    @Test
    public void delete() {
        Specification.installSpecification(Specification.requestSpec(URL), Specification.response204());
        given().when().delete("/api/users/2");
    }

    @Test
    public void ReqisterSuccessful() {
        Specification.installSpecification(Specification.requestSpec(URL), Specification.response200());
        Map<String, String> params = new HashMap<>();
        params.put("email", "eve.holt@reqres.in");
        params.put("password", "pistol");
        Response response = given().filter(AllureListener.getAllureRestAssured()).body(params).when().post("/api/register").then().log().all().body("id", equalTo(4)).body("token", equalTo("QpwL5tke4Pnpja7X4")).extract().response();
    }

    @Test
    public void ReqisterUnSuccessful() {
        Specification.installSpecification(Specification.requestSpec(URL), Specification.response400());
        Map<String, String> params = new HashMap<>();
        params.put("email", "sydney@fife");
        Response response = given().filter(AllureListener.getAllureRestAssured()).body(params).when().post("/api/register").then().log().all().body("error", equalTo("Missing password")).extract().response();
    }

    @Test
    public void LoginSuccessful() {
        Specification.installSpecification(Specification.requestSpec(URL), Specification.response200());
        Map<String, String> params = new HashMap<>();
        params.put("email", "eve.holt@reqres.in");
        params.put("password", "cityslicka");
        Response response = given().filter(AllureListener.getAllureRestAssured()).body(params).when().post("/api/login").then().log().all().body("token", equalTo("QpwL5tke4Pnpja7X4")).extract().response();
    }

    @Test
    public void LoginUnSuccessful() {
        Specification.installSpecification(Specification.requestSpec(URL), Specification.response400());
        Map<String, String> params = new HashMap<>();
        params.put("email", "peter@klaven");
        Response response = given().filter(AllureListener.getAllureRestAssured()).body(params).when().post("/api/login").then().log().all().body("error", equalTo("Missing password")).extract().response();
    }

    @Test
    public void getHttpBin() {
        Specification.installSpecification(Specification.requestSpec(URL2), Specification.response200());

        Response response = given().filter(AllureListener.getAllureRestAssured()).when().get("/get").then().log().all().extract().response();
    }

    @Test
    public void Auth() {
        Specification.installSpecification(Specification.requestSpec(URL3), Specification.response200());
        Map<String, String> params = new HashMap<>();
        params.put("username", "admin");
        params.put("password", "password123");
        Response response = given().filter(AllureListener.getAllureRestAssured()).body(params).when().post("/auth").then().log().all().extract().response();
    }

    @Test
    public void getBooksid() {
        Specification.installSpecification(Specification.requestSpec(URL3), Specification.response200());
        Response response = given().filter(AllureListener.getAllureRestAssured())
                .when().get("/booking").then().log().all().extract().response();
    }

    @Test
    public void getBooks() {
        Specification.installSpecification(Specification.requestSpec(URL3), Specification.response200());
        Map<String, String> params = new HashMap<>();
        params.put("checkin", "2018-01-01");
        params.put("checkout", "2019-01-01");
        BookDate bookDate = new BookDate("Jim", "Brown", 111, true, params, "Breakfast");

        Response response = given().filter(AllureListener.getAllureRestAssured())
                .body(bookDate).when().post("/booking").then().log().all().extract().response();
    }

    @Test
    public void getBooksUpdatePut() {
        Specification.installSpecification(Specification.requestSpec(URL3), Specification.response200());
        Map<String, String> params = new HashMap<>();
        params.put("checkin", "2018-01-01");
        params.put("checkout", "2019-01-01");
        BookDate bookDate = new BookDate("James", "Brown", 111, true, params, "Breakfast");

        Response response = given().filter(AllureListener.getAllureRestAssured())
                .auth().preemptive().basic("admin", "password123")
                .body(bookDate).when().put("/booking/273")
                .then().log().all().extract().response();
    }

    @Test
    public void getBooksUpdatePatch() {
        Specification.installSpecification(Specification.requestSpec(URL3), Specification.response200());
        Map<String, String> params = new HashMap<>();
        params.put("firstname", "James");
        params.put("lastname", "Brown");

        Response response = given().filter(AllureListener.getAllureRestAssured()).auth().preemptive()
                .basic("admin", "password123")
                .body(params).when()
                .patch("/booking/273").then().log().all().extract().response();
    }

    @Test
    public void deleteBook() {
        Specification.installSpecification(Specification.requestSpec(URL3), Specification.response201());

        given().filter(AllureListener.getAllureRestAssured())
                .auth().preemptive()
                .basic("admin", "password123")
                .when().delete("/booking/273");
    }



}
