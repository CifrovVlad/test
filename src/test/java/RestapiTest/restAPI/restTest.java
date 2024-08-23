package RestapiTest.restAPI;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;

import java.time.Clock;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class restTest {
      private final static String URL = "https://reqres.in/";


      @Test
      public void getAvatarAndIdTest() {
          Specification.installSpecification(Specification.requestSpec(URL), Specification.response200());
          List<UserData> users = given()
                  .when()
                  .get( "api/users?page=2")
                  .then().log().all()
                  .extract().body().jsonPath().getList("data", UserData.class);

        users.forEach(x -> Assert.assertTrue(x.getAvatar().contains(x.getId().toString())));
       Assert.assertTrue(users.stream().allMatch(x -> x.getEmail().endsWith("@reqres.in")));
      }
       @Test
      public void post() {
    Specification.installSpecification(Specification.requestSpec(URL), Specification.response200());
         Integer id = 4;
         String token = "QpwL5tke4Pnpja7X4";
         Registry registry = new Registry("eve.holt@reqres.in", "pistol");
         SuccessBag successBag = given()
                 .body(registry)
                 .post("api/register")
                 .then().log().all()
                 .extract().as(SuccessBag.class);
    Assert.assertEquals(id, successBag.getId());
    Assert.assertEquals(token, successBag.getToken());
      }
@Test
      public void Year() {
          Specification.installSpecification(Specification.requestSpec(URL), Specification.response200());
          List<OldModel> list = given()
                  .when()
                  .get("/api/unknown")
                  .then().log().all()
                  .extract().body().jsonPath().getList("data", OldModel.class);

        List<Integer> years = list.stream().map(OldModel::getYear).collect(Collectors.toList());
        List<Integer> years2 = years.stream().sorted().collect(Collectors.toList());

        Assert.assertEquals(years2, years);
          System.out.println(years);
          System.out.println(years2);


      }
      @Test
      public void deleat() {
          Specification.installSpecification(Specification.requestSpec(URL), Specification.response204());
          given()
                  .when()
                  .delete("/api/users/2")
                  .then().log().all();
      }
      @Test
    public void TimeCurrent() {
          Specification.installSpecification(Specification.requestSpec(URL), Specification.response200());
          TimeModel timeModel = new TimeModel("morpheus", "zion resident");
          ResponceTime responceTime = given()
                  .body(timeModel)
                  .when()
                  .put("/api/users/2")
                  .then().log().all()
                  .extract().as(ResponceTime.class);
          System.out.println(Clock.systemUTC().instant().toString());
          String regix = "(.{5})$";
          String regix1 = "(.{8})$";
          String timeNow = Clock.systemUTC().instant().toString().replaceAll(regix1, "");
          System.out.println(timeNow);
          Assert.assertEquals(timeNow, responceTime.getUpdatedAt().replaceAll(regix, ""));

      }

      @Test
    public void notPojo () {
          Specification.installSpecification(Specification.requestSpec(URL), Specification.response200());
          Response response = given()
                  .when()
                  .get("api/users?page=2")
                  .then().log().all()
                  .body("page", equalTo(2))
                  .extract().response();

          JsonPath jsonPath = response.jsonPath();
         List<Integer> ids = jsonPath.getList("data.id");
         List<String> avatars = jsonPath.getList("data.avatar");
         List<String> emails = jsonPath.getList("data.email");

          for (int i = 0; i < avatars.size(); i++) {
              Assert.assertTrue(avatars.get(i).contains(ids.get(i).toString()));
          }
          Assert.assertTrue(emails.stream().allMatch(x -> x.endsWith("@reqres.in")));
      }

      @Test
    public void noPojoTest() {
          Specification.installSpecification(Specification.requestSpec(URL), Specification.response200());
          Map<String, String> map = new HashMap<>();
          map.put("email", "eve.holt@reqres.in");
          map.put("password", "pistol");
          given()
                  .body(map)
                  .when()
                  .post("api/register")
                  .then().log().all()
                  .body("id", equalTo(4))
                  .body("token", equalTo("QpwL5tke4Pnpja7X4"));

      }
      @Test
      public void noPojoYearTest() {
          Specification.installSpecification(Specification.requestSpec(URL), Specification.response200());
          Response response = given()
                  .when()
                  .get("/api/unknown")
                  .then().log().all()
                  .body("page", equalTo(1))
                  .extract().response();
          JsonPath jsonPath = response.jsonPath();
          List<Integer> years = jsonPath.getList("data.year");
          List<Integer> years2 = years.stream().sorted().collect(Collectors.toList());
          Assert.assertEquals(years2, years);
          System.out.println(years);
          System.out.println(years2);


      }
      @Test
    public void noPojoTimeTest() {
          Specification.installSpecification(Specification.requestSpec(URL), Specification.response200());
          Map<String, String> map = new HashMap<>();
          map.put("name", "morpheus");
          map.put("job", "zion resident");
          Response response = given()
                  .body(map)
                  .when()
                  .put("/api/users/2")
                  .then().log().all()
                  .extract().response();
          JsonPath jsonPath = response.jsonPath();
          String regix = "(.{5})$";
          String regix1 = "(.{8})$";
          String timeNow = Clock.systemUTC().instant().toString().replaceAll(regix1, "");
          System.out.println(timeNow);
          String time  = jsonPath.get("updatedAt").toString().replaceAll(regix, "");
          System.out.println(time);

          Assert.assertEquals(timeNow, time);


      }
      @Test
    public void noPojoErrorTest() {
          Specification.installSpecification(Specification.requestSpec(URL), Specification.response400());
          Map<String, String> map = new HashMap<>();
          map.put("email", "sydney@fife");
          given()
                  .body(map)
                  .when()
                  .post("api/register")
                  .then().log().all()
                  .body("error", equalTo("Missing password"));
      }
}
