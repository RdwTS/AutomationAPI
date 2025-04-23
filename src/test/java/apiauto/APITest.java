package apiauto;

import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.json.JSONObject;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class APITest {

    @Test
    public void getUser() {
        //baseURL
        RestAssured.baseURI = "https://reqres.in/";
        // user perpage
        given().when().get("api/users/12")
                .then()
                .log().all()
                .assertThat().statusCode(200)
                .assertThat().body("data.id", Matchers.equalTo(12)); // validasi output
//                .assertThat().body("data.id",Matchers.hasSize(6));

    }

    @Test
    public void getListUser(){
        //baseURL
        RestAssured.baseURI = "https://reqres.in/";
        // user perpage
        given().when().get("api/users?page=1")
                .then()
                .log().all()
                .assertThat().statusCode(200)
                .assertThat().body("page", Matchers.equalTo(1)); // validasi output
//                .assertThat().body("data.id",Matchers.hasSize(6));
    }

    @Test
    public void createNewUser(){
        //baseURL
        RestAssured.baseURI = "https://reqres.in/";
        // parameter Json
        String nama      = "Jayjay";
        String pekerjaan = "student";

        JSONObject fieldJson = new JSONObject();
        fieldJson.put("name",nama       );
        fieldJson.put("job" ,pekerjaan  );
        //include header Json format
        given().log().all() // for print entire request to console
                .header("Content-Type","application/json")
                .header("Accept","application/json")
                .body(fieldJson.toString())
                .post("api/users")
                .then()
                .assertThat().statusCode(201)
                .assertThat().body("name", Matchers.equalTo(nama))
                .assertThat().body("job",Matchers.equalTo(pekerjaan))
                .assertThat().body("$",Matchers.hasKey("id"))
                .assertThat().body("$",Matchers.hasKey("createdAt"));
    }

}
