package br.com.xmrtecnologia.task;

import org.hamcrest.CoreMatchers;
import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class APITest {
    
    @BeforeClass
    public static void setup(){
        RestAssured.baseURI = "http://localhost:8001/tasks-backend";
    }

    @Test
    public void testGetTest() {
        RestAssured.given()
                .log().all()   // Logar a Requisição
            .when()
                .get("/todo")
            .then()
                .log().all()  // Logar e Resposta
        ;
    }

    @Test
    public void testPostTest() {
        RestAssured.given()
                .log().all()   // Logar a Requisição
                // Utilizadas as barras, pois eram aspas duplas dentro de aspas duplas
                .body(" {\"task\": \"Teste via API\", \"dueDate\": \"2050-04-03\"}")
                .contentType(ContentType.JSON)
            .when()
                .post("/todo")
            .then()
                .log().all()  // Logar e Resposta
        ;
    }

    @Test
    public void deveRetornarTarefasTest() {
        RestAssured.given()
            .when()
                .get("/todo")
            .then()
                .statusCode(200)
        ;
    }
        
    @Test
    public void deveAdicionarTarefaComSucessoTest() {
        RestAssured.given()
                // Utilizadas as barras, pois eram aspas duplas dentro de aspas duplas
                .body(" {\"task\": \"Teste via API\", \"dueDate\": \"2050-04-03\"}")  
                .contentType(ContentType.JSON)
            .when()
                .post("/todo")
            .then()
                .statusCode(201)
        ;

    }
    
    @Test
    public void naoDeveAdicionarTarefaInvalida() {
        RestAssured.given()
                // Utilizadas as barras, pois eram aspas duplas dentro de aspas duplas
                .body(" {\"task\": \"Teste via API\", \"dueDate\": \"2010-04-03\"}")  
                .contentType(ContentType.JSON)
            .when()
                .post("/todo")
            .then()
                .statusCode(400)
                .body("message", CoreMatchers.is("Due date must not be in past"))
        ;

    }
    
}