package gorest.automation.api.test;

import gorest.automation.api.domain.User;
import org.apache.http.HttpStatus;
import org.junit.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class UserTest extends BaseTest {

    private static final String CREATE_USER_ENDPOINT = "/users";
    private static final String LIST_USER_SPECIFIC_ENDPOINT = "/users/{userId}";
    private static final String LIST_USER_ENDPOINT = "/comments";

    String token = "00e6592f3710b3f7e57f62158321dd1364eafaa932a55cf6486348d450114cb6";


    private int testReturnLimitPage(int page) {
        int limitUserPage = given().
                param("meta.pagination.page", page).
                when().
                get(LIST_USER_ENDPOINT).
                then().
                statusCode(HttpStatus.SC_OK).
                extract().
                path("meta.pagination.limit");
        return limitUserPage;
    }

    @Test
    public void testListLimitUser(){
        int limit = testReturnLimitPage(1);
        when().
            get(LIST_USER_ENDPOINT).
        then().
            statusCode(HttpStatus.SC_OK).
            body(
                 "data", is(notNullValue()),
                 "data.size()", is(limit),
                    "data.findAll{ it.created_at.startsWith('2021')}.size()", is(limit)
            );
    }

    @Test
    public void testCreateUserSuccessfully(){

        User user = new User("Pietro Nascimento Piva", "Male", "piva.pietro2029@email.com", "Active");
        given().
            header("Authorization", "Bearer " + token).
            body(user).
        when().
            post(CREATE_USER_ENDPOINT).
        then().
            body("code", is(HttpStatus.SC_CREATED)).
            body("data.name", is(user.getName())).
            body("data.email", is(user.getEmail()))
        ;
    }

    @Test
    public void testCreateUserWithoutEmail(){
        User user = new User();
        user.setName("Mariana F piva");
        user.setGender("Female");
        user.setStatus("Active");
        given().
            header("Authorization", "Bearer " + token).
            body(user).
        when().
            post(CREATE_USER_ENDPOINT).
        then().
            body("code", is(HttpStatus.SC_UNPROCESSABLE_ENTITY)).
            body("data[0].field", is("email")).
            body("data[0].message", is("can't be blank"))
        ;
    }

    @Test
    public void testListUserSpecific(){
        User user =
        given().
            pathParam("userId", 333).
        when().
            get(LIST_USER_SPECIFIC_ENDPOINT).
        then().
            statusCode(HttpStatus.SC_OK).
        extract().
            body().jsonPath().getObject("data", User.class);

        assertThat(user.getEmail(), containsString("@bogisich.com"));
        assertThat(user.getName(), is("Miss Gotum Nambeesan"));
        assertThat(user.getStatus(), is("Inactive"));
    }
}