package com.tictactoerest.tictactoegame;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tictactoerest.tictactoegame.model.gameclasses.GameREST;
import com.tictactoerest.tictactoegame.model.gameclasses.Gameplay;
import com.tictactoerest.tictactoegame.model.Step;
import com.tictactoerest.tictactoegame.repository.H2_JDBC;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.junit.jupiter.api.*;
import org.springframework.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class tictactoeREST {

    @BeforeAll
    static void init(){
        H2_JDBC.initDB();
    }

    @Test
    @Order(1)
    void gameNotCreated() throws IOException {
        HttpUriRequest request = new HttpGet("http://localhost:8080/gameplay/current-game/step?setMark=6");
        HttpResponse response = HttpClientBuilder.create().build().execute(request);
        String messageFromResponse = EntityUtils.toString(response.getEntity());
        assertEquals("Game is not started! Start new game.", messageFromResponse);
        assertEquals(response.getStatusLine().getStatusCode(), HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @Order(2)
    void newGame() throws IOException {
        HttpUriRequest request = new HttpGet("http://localhost:8080/gameplay/new-game?player1=Postman1&player2=Postman2");
        HttpResponse response = HttpClientBuilder.create().build().execute(request);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true);
        String jsonFromResponse = EntityUtils.toString(response.getEntity());
        GameREST test = mapper.readValue(jsonFromResponse, GameREST.class);
        assertAll(
                () -> assertNull(test.getWinner()),
                () -> assertEquals(0, test.getMap()[0][0]),
                () -> assertEquals(0, test.getMap()[0][1]),
                () -> assertEquals(0, test.getMap()[0][2]),
                () -> assertEquals(0, test.getMap()[1][0]),
                () -> assertEquals(0, test.getMap()[1][1]),
                () -> assertEquals(0, test.getMap()[1][2]),
                () -> assertEquals(0, test.getMap()[2][0]),
                () -> assertEquals(0, test.getMap()[2][1]),
                () -> assertEquals(0, test.getMap()[2][2]),
                () -> assertEquals(new ArrayList<Step>(), test.getSteps()),
                () -> assertEquals("Postman1", test.getPlayers()[0].getName()),
                () -> assertEquals("Postman2", test.getPlayers()[1].getName()),
                () -> assertEquals(9, test.getTurnsLeft()),
                () -> assertTrue(test.gameContinue)
        );
    }

    @Test
    @Order(3)
    void firstStep() throws IOException {
        HttpUriRequest request = new HttpGet("http://localhost:8080/gameplay/current-game/step?setMark=6");
        HttpResponse response = HttpClientBuilder.create().build().execute(request);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true);
        String jsonFromResponse = EntityUtils.toString(response.getEntity());
        GameREST test = mapper.readValue(jsonFromResponse, GameREST.class);
        assertAll(
                () -> assertNull(test.getWinner()),
                () -> assertEquals(0, test.getMap()[0][0]),
                () -> assertEquals(0, test.getMap()[0][1]),
                () -> assertEquals(0, test.getMap()[0][2]),
                () -> assertEquals(0, test.getMap()[1][0]),
                () -> assertEquals(0, test.getMap()[1][1]),
                () -> assertEquals(1, test.getMap()[1][2]),
                () -> assertEquals(0, test.getMap()[2][0]),
                () -> assertEquals(0, test.getMap()[2][1]),
                () -> assertEquals(0, test.getMap()[2][2]),

                () -> assertEquals(1, test.getSteps().get(0).getId()),
                () -> assertEquals(1, test.getSteps().get(0).getPlayerId()),
                () -> assertEquals(6, test.getSteps().get(0).getCellId()),

                () -> assertEquals("Postman1", test.getPlayers()[0].getName()),
                () -> assertEquals("Postman2", test.getPlayers()[1].getName()),
                () -> assertEquals(8, test.getTurnsLeft()),
                () -> assertTrue(test.gameContinue)
        );
    }

    @Test
    @Order(4)
    void doubleMark() throws IOException {
        HttpUriRequest request = new HttpGet("http://localhost:8080/gameplay/current-game/step?setMark=6");
        HttpResponse response = HttpClientBuilder.create().build().execute(request);
        String messageFromResponse = EntityUtils.toString(response.getEntity());
        assertEquals("Cell with id 6 marked already! Use another cell.", messageFromResponse);
        assertEquals(response.getStatusLine().getStatusCode(), HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @Order(5)
    void outOfMap() throws IOException {
        HttpUriRequest request = new HttpGet("http://localhost:8080/gameplay/current-game/step?setMark=99");
        HttpResponse response = HttpClientBuilder.create().build().execute(request);
        String messageFromResponse = EntityUtils.toString(response.getEntity());
        assertEquals("Cell with id 99 doesn't exist! Use cells with id from 1 to 9.", messageFromResponse);
        assertEquals(response.getStatusLine().getStatusCode(), HttpStatus.NOT_FOUND.value());
        request = new HttpGet("http://localhost:8080/gameplay/current-game/step?setMark=10");
        response = HttpClientBuilder.create().build().execute(request);
        messageFromResponse = EntityUtils.toString(response.getEntity());
        assertEquals("Cell with id 10 doesn't exist! Use cells with id from 1 to 9.", messageFromResponse);
        assertEquals(response.getStatusLine().getStatusCode(), HttpStatus.NOT_FOUND.value());
        request = new HttpGet("http://localhost:8080/gameplay/current-game/step?setMark=-17");
        response = HttpClientBuilder.create().build().execute(request);
        messageFromResponse = EntityUtils.toString(response.getEntity());
        assertEquals("Cell with id -17 doesn't exist! Use cells with id from 1 to 9.", messageFromResponse);
        assertEquals(response.getStatusLine().getStatusCode(), HttpStatus.NOT_FOUND.value());
        request = new HttpGet("http://localhost:8080/gameplay/current-game/step?setMark=0");
        response = HttpClientBuilder.create().build().execute(request);
        messageFromResponse = EntityUtils.toString(response.getEntity());
        assertEquals("Cell with id 0 doesn't exist! Use cells with id from 1 to 9.", messageFromResponse);
        assertEquals(response.getStatusLine().getStatusCode(), HttpStatus.NOT_FOUND.value());
    }

    @Test
    @Order(6)
    void secondStep() throws IOException {
        HttpUriRequest request = new HttpGet("http://localhost:8080/gameplay/current-game/step?setMark=5");
        HttpResponse response = HttpClientBuilder.create().build().execute(request);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true);
        String jsonFromResponse = EntityUtils.toString(response.getEntity());
        GameREST test = mapper.readValue(jsonFromResponse, GameREST.class);
        assertAll(
                () -> assertNull(test.getWinner()),
                () -> assertEquals(0, test.getMap()[0][0]),
                () -> assertEquals(0, test.getMap()[0][1]),
                () -> assertEquals(0, test.getMap()[0][2]),
                () -> assertEquals(0, test.getMap()[1][0]),
                () -> assertEquals(-1, test.getMap()[1][1]),
                () -> assertEquals(1, test.getMap()[1][2]),
                () -> assertEquals(0, test.getMap()[2][0]),
                () -> assertEquals(0, test.getMap()[2][1]),
                () -> assertEquals(0, test.getMap()[2][2]),

                () -> assertEquals(1, test.getSteps().get(0).getId()),
                () -> assertEquals(1, test.getSteps().get(0).getPlayerId()),
                () -> assertEquals(6, test.getSteps().get(0).getCellId()),

                () -> assertEquals("Postman1", test.getPlayers()[0].getName()),
                () -> assertEquals("Postman2", test.getPlayers()[1].getName()),
                () -> assertEquals(7, test.getTurnsLeft()),
                () -> assertTrue(test.gameContinue)
        );
    }

    @Test
    @Order(7)
    void fastP1win() throws IOException {
        HttpUriRequest request = new HttpGet("http://localhost:8080/gameplay/current-game/step?setMark=9");
        HttpResponse response = HttpClientBuilder.create().build().execute(request);
        assertEquals(response.getStatusLine().getStatusCode(), HttpStatus.OK.value());
        request = new HttpGet("http://localhost:8080/gameplay/current-game/step?setMark=8");
        response = HttpClientBuilder.create().build().execute(request);
        assertEquals(response.getStatusLine().getStatusCode(), HttpStatus.OK.value());
        request = new HttpGet("http://localhost:8080/gameplay/current-game/step?setMark=3");
        response = HttpClientBuilder.create().build().execute(request);
        assertEquals(response.getStatusLine().getStatusCode(), HttpStatus.OK.value());

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true);
        String jsonFromResponse = EntityUtils.toString(response.getEntity());
        GameREST test = mapper.readValue(jsonFromResponse, GameREST.class);
        assertAll(
                // - - X
                // - O X
                // - O X
                () -> assertEquals(0, test.getMap()[0][0]),
                () -> assertEquals(0, test.getMap()[0][1]),
                () -> assertEquals(1, test.getMap()[0][2]),
                () -> assertEquals(0, test.getMap()[1][0]),
                () -> assertEquals(-1, test.getMap()[1][1]),
                () -> assertEquals(1, test.getMap()[1][2]),
                () -> assertEquals(0, test.getMap()[2][0]),
                () -> assertEquals(-1, test.getMap()[2][1]),
                () -> assertEquals(1, test.getMap()[2][2]),

                () -> assertEquals(1, test.getSteps().get(0).getId()),
                () -> assertEquals(1, test.getSteps().get(0).getPlayerId()),
                () -> assertEquals(6, test.getSteps().get(0).getCellId()),

                () -> assertEquals(2, test.getSteps().get(1).getId()),
                () -> assertEquals(2, test.getSteps().get(1).getPlayerId()),
                () -> assertEquals(5, test.getSteps().get(1).getCellId()),

                () -> assertEquals(3, test.getSteps().get(2).getId()),
                () -> assertEquals(1, test.getSteps().get(2).getPlayerId()),
                () -> assertEquals(9, test.getSteps().get(2).getCellId()),

                () -> assertEquals(4, test.getSteps().get(3).getId()),
                () -> assertEquals(2, test.getSteps().get(3).getPlayerId()),
                () -> assertEquals(8, test.getSteps().get(3).getCellId()),

                () -> assertEquals(5, test.getSteps().get(4).getId()),
                () -> assertEquals(1, test.getSteps().get(4).getPlayerId()),
                () -> assertEquals(3, test.getSteps().get(4).getCellId()),

                () -> assertEquals("Postman1", test.getPlayers()[0].getName()),
                () -> assertEquals("Postman2", test.getPlayers()[1].getName()),
                () -> assertEquals(4, test.getTurnsLeft()),

                () -> assertEquals("Postman1", test.getWinner().getName()),
                () -> assertEquals(1, test.getWinner().getId()),
                () -> assertEquals("X", test.getWinner().getMark()),

                () -> assertFalse(test.gameContinue)
        );
    }

    @Test
    @Order(8)
    void markWhenGameEnded() throws IOException {
        HttpUriRequest request = new HttpGet("http://localhost:8080/gameplay/current-game/step?setMark=1");
        HttpResponse response = HttpClientBuilder.create().build().execute(request);
        String messageFromResponse = EntityUtils.toString(response.getEntity());
        assertEquals("Game is ended! Start new game.", messageFromResponse);
        assertEquals(response.getStatusLine().getStatusCode(), HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @Order(9)
    void fullGameP2Win() throws IOException{
        HttpUriRequest request = new HttpGet("http://localhost:8080/gameplay/new-game?player1=Postman1&player2=Postman2");
        HttpResponse response = HttpClientBuilder.create().build().execute(request);
        assertEquals(response.getStatusLine().getStatusCode(), HttpStatus.OK.value());
        request = new HttpGet("http://localhost:8080/gameplay/current-game/step?setMark=1");
        response = HttpClientBuilder.create().build().execute(request);
        assertEquals(response.getStatusLine().getStatusCode(), HttpStatus.OK.value());
        request = new HttpGet("http://localhost:8080/gameplay/current-game/step?setMark=2");
        response = HttpClientBuilder.create().build().execute(request);
        assertEquals(response.getStatusLine().getStatusCode(), HttpStatus.OK.value());
        request = new HttpGet("http://localhost:8080/gameplay/current-game/step?setMark=3");
        response = HttpClientBuilder.create().build().execute(request);
        assertEquals(response.getStatusLine().getStatusCode(), HttpStatus.OK.value());
        request = new HttpGet("http://localhost:8080/gameplay/current-game/step?setMark=5");
        response = HttpClientBuilder.create().build().execute(request);
        assertEquals(response.getStatusLine().getStatusCode(), HttpStatus.OK.value());
        request = new HttpGet("http://localhost:8080/gameplay/current-game/step?setMark=4");
        response = HttpClientBuilder.create().build().execute(request);
        assertEquals(response.getStatusLine().getStatusCode(), HttpStatus.OK.value());
        request = new HttpGet("http://localhost:8080/gameplay/current-game/step?setMark=8");
        response = HttpClientBuilder.create().build().execute(request);
        assertEquals(response.getStatusLine().getStatusCode(), HttpStatus.OK.value());
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true);
        String jsonFromResponse = EntityUtils.toString(response.getEntity());
        GameREST test = mapper.readValue(jsonFromResponse, GameREST.class);
        assertAll(
                // X O X
                // X O -
                // - O -
                () -> assertEquals(1, test.getMap()[0][0]),
                () -> assertEquals(-1, test.getMap()[0][1]),
                () -> assertEquals(1, test.getMap()[0][2]),
                () -> assertEquals(1, test.getMap()[1][0]),
                () -> assertEquals(-1, test.getMap()[1][1]),
                () -> assertEquals(0, test.getMap()[1][2]),
                () -> assertEquals(0, test.getMap()[2][0]),
                () -> assertEquals(-1, test.getMap()[2][1]),
                () -> assertEquals(0, test.getMap()[2][2]),

                () -> assertEquals(1, test.getSteps().get(0).getId()),
                () -> assertEquals(1, test.getSteps().get(0).getPlayerId()),
                () -> assertEquals(1, test.getSteps().get(0).getCellId()),

                () -> assertEquals(2, test.getSteps().get(1).getId()),
                () -> assertEquals(2, test.getSteps().get(1).getPlayerId()),
                () -> assertEquals(2, test.getSteps().get(1).getCellId()),

                () -> assertEquals(3, test.getSteps().get(2).getId()),
                () -> assertEquals(1, test.getSteps().get(2).getPlayerId()),
                () -> assertEquals(3, test.getSteps().get(2).getCellId()),

                () -> assertEquals(4, test.getSteps().get(3).getId()),
                () -> assertEquals(2, test.getSteps().get(3).getPlayerId()),
                () -> assertEquals(5, test.getSteps().get(3).getCellId()),

                () -> assertEquals(5, test.getSteps().get(4).getId()),
                () -> assertEquals(1, test.getSteps().get(4).getPlayerId()),
                () -> assertEquals(4, test.getSteps().get(4).getCellId()),

                () -> assertEquals(6, test.getSteps().get(5).getId()),
                () -> assertEquals(2, test.getSteps().get(5).getPlayerId()),
                () -> assertEquals(8, test.getSteps().get(5).getCellId()),

                () -> assertEquals("Postman1", test.getPlayers()[0].getName()),
                () -> assertEquals("Postman2", test.getPlayers()[1].getName()),
                () -> assertEquals(3, test.getTurnsLeft()),

                () -> assertEquals("Postman2", test.getWinner().getName()),
                () -> assertEquals(2, test.getWinner().getId()),
                () -> assertEquals("O", test.getWinner().getMark()),

                () -> assertFalse(test.gameContinue)
        );
    }

    @Test
    @Order(10)
    void fullGameDraw() throws IOException{
        HttpUriRequest request = new HttpGet("http://localhost:8080/gameplay/new-game?player1=Postman1&player2=Postman2");
        HttpResponse response = HttpClientBuilder.create().build().execute(request);
        assertEquals(response.getStatusLine().getStatusCode(), HttpStatus.OK.value());
        request = new HttpGet("http://localhost:8080/gameplay/current-game/step?setMark=1");
        response = HttpClientBuilder.create().build().execute(request);
        assertEquals(response.getStatusLine().getStatusCode(), HttpStatus.OK.value());
        request = new HttpGet("http://localhost:8080/gameplay/current-game/step?setMark=2");
        response = HttpClientBuilder.create().build().execute(request);
        assertEquals(response.getStatusLine().getStatusCode(), HttpStatus.OK.value());
        request = new HttpGet("http://localhost:8080/gameplay/current-game/step?setMark=3");
        response = HttpClientBuilder.create().build().execute(request);
        assertEquals(response.getStatusLine().getStatusCode(), HttpStatus.OK.value());
        request = new HttpGet("http://localhost:8080/gameplay/current-game/step?setMark=5");
        response = HttpClientBuilder.create().build().execute(request);
        assertEquals(response.getStatusLine().getStatusCode(), HttpStatus.OK.value());
        request = new HttpGet("http://localhost:8080/gameplay/current-game/step?setMark=4");
        response = HttpClientBuilder.create().build().execute(request);
        assertEquals(response.getStatusLine().getStatusCode(), HttpStatus.OK.value());
        request = new HttpGet("http://localhost:8080/gameplay/current-game/step?setMark=6");
        response = HttpClientBuilder.create().build().execute(request);
        assertEquals(response.getStatusLine().getStatusCode(), HttpStatus.OK.value());
        request = new HttpGet("http://localhost:8080/gameplay/current-game/step?setMark=8");
        response = HttpClientBuilder.create().build().execute(request);
        assertEquals(response.getStatusLine().getStatusCode(), HttpStatus.OK.value());
        request = new HttpGet("http://localhost:8080/gameplay/current-game/step?setMark=7");
        response = HttpClientBuilder.create().build().execute(request);
        assertEquals(response.getStatusLine().getStatusCode(), HttpStatus.OK.value());
        request = new HttpGet("http://localhost:8080/gameplay/current-game/step?setMark=9");
        response = HttpClientBuilder.create().build().execute(request);
        assertEquals(response.getStatusLine().getStatusCode(), HttpStatus.OK.value());
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true);
        String jsonFromResponse = EntityUtils.toString(response.getEntity());
        GameREST test = mapper.readValue(jsonFromResponse, GameREST.class);
        assertAll(
                // X O X
                // X O O
                // O X X
                () -> assertEquals(1, test.getMap()[0][0]),
                () -> assertEquals(-1, test.getMap()[0][1]),
                () -> assertEquals(1, test.getMap()[0][2]),
                () -> assertEquals(1, test.getMap()[1][0]),
                () -> assertEquals(-1, test.getMap()[1][1]),
                () -> assertEquals(-1, test.getMap()[1][2]),
                () -> assertEquals(-1, test.getMap()[2][0]),
                () -> assertEquals(1, test.getMap()[2][1]),
                () -> assertEquals(1, test.getMap()[2][2]),

                () -> assertEquals(1, test.getSteps().get(0).getId()),
                () -> assertEquals(1, test.getSteps().get(0).getPlayerId()),
                () -> assertEquals(1, test.getSteps().get(0).getCellId()),

                () -> assertEquals(2, test.getSteps().get(1).getId()),
                () -> assertEquals(2, test.getSteps().get(1).getPlayerId()),
                () -> assertEquals(2, test.getSteps().get(1).getCellId()),

                () -> assertEquals(3, test.getSteps().get(2).getId()),
                () -> assertEquals(1, test.getSteps().get(2).getPlayerId()),
                () -> assertEquals(3, test.getSteps().get(2).getCellId()),

                () -> assertEquals(4, test.getSteps().get(3).getId()),
                () -> assertEquals(2, test.getSteps().get(3).getPlayerId()),
                () -> assertEquals(5, test.getSteps().get(3).getCellId()),

                () -> assertEquals(5, test.getSteps().get(4).getId()),
                () -> assertEquals(1, test.getSteps().get(4).getPlayerId()),
                () -> assertEquals(4, test.getSteps().get(4).getCellId()),

                () -> assertEquals(6, test.getSteps().get(5).getId()),
                () -> assertEquals(2, test.getSteps().get(5).getPlayerId()),
                () -> assertEquals(6, test.getSteps().get(5).getCellId()),

                () -> assertEquals(7, test.getSteps().get(6).getId()),
                () -> assertEquals(1, test.getSteps().get(6).getPlayerId()),
                () -> assertEquals(8, test.getSteps().get(6).getCellId()),

                () -> assertEquals(8, test.getSteps().get(7).getId()),
                () -> assertEquals(2, test.getSteps().get(7).getPlayerId()),
                () -> assertEquals(7, test.getSteps().get(7).getCellId()),

                () -> assertEquals(9, test.getSteps().get(8).getId()),
                () -> assertEquals(1, test.getSteps().get(8).getPlayerId()),
                () -> assertEquals(9, test.getSteps().get(8).getCellId()),

                () -> assertEquals("Postman1", test.getPlayers()[0].getName()),
                () -> assertEquals("Postman2", test.getPlayers()[1].getName()),
                () -> assertEquals(0, test.getTurnsLeft()),

                () -> assertNull(test.getWinner()),
                () -> assertFalse(test.gameContinue)
        );
    }

    @Test
    void loadGame() throws IOException {
        HttpUriRequest request = new HttpGet("http://localhost:8080/gameplay/load/3");
        HttpResponse response = HttpClientBuilder.create().build().execute(request);
        assertEquals(response.getStatusLine().getStatusCode(), HttpStatus.OK.value());
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true);
        String jsonFromResponse = EntityUtils.toString(response.getEntity());
        Gameplay test = mapper.readValue(jsonFromResponse, Gameplay.class);
        assertAll(
                // X O X
                // X O O
                // O X X

                () -> assertEquals(1, test.getSteps().get(0).getId()),
                () -> assertEquals(1, test.getSteps().get(0).getPlayerId()),
                () -> assertEquals(1, test.getSteps().get(0).getCellId()),

                () -> assertEquals(2, test.getSteps().get(1).getId()),
                () -> assertEquals(2, test.getSteps().get(1).getPlayerId()),
                () -> assertEquals(2, test.getSteps().get(1).getCellId()),

                () -> assertEquals(3, test.getSteps().get(2).getId()),
                () -> assertEquals(1, test.getSteps().get(2).getPlayerId()),
                () -> assertEquals(3, test.getSteps().get(2).getCellId()),

                () -> assertEquals(4, test.getSteps().get(3).getId()),
                () -> assertEquals(2, test.getSteps().get(3).getPlayerId()),
                () -> assertEquals(5, test.getSteps().get(3).getCellId()),

                () -> assertEquals(5, test.getSteps().get(4).getId()),
                () -> assertEquals(1, test.getSteps().get(4).getPlayerId()),
                () -> assertEquals(4, test.getSteps().get(4).getCellId()),

                () -> assertEquals(6, test.getSteps().get(5).getId()),
                () -> assertEquals(2, test.getSteps().get(5).getPlayerId()),
                () -> assertEquals(6, test.getSteps().get(5).getCellId()),

                () -> assertEquals(7, test.getSteps().get(6).getId()),
                () -> assertEquals(1, test.getSteps().get(6).getPlayerId()),
                () -> assertEquals(8, test.getSteps().get(6).getCellId()),

                () -> assertEquals(8, test.getSteps().get(7).getId()),
                () -> assertEquals(2, test.getSteps().get(7).getPlayerId()),
                () -> assertEquals(7, test.getSteps().get(7).getCellId()),

                () -> assertEquals(9, test.getSteps().get(8).getId()),
                () -> assertEquals(1, test.getSteps().get(8).getPlayerId()),
                () -> assertEquals(9, test.getSteps().get(8).getCellId()),

                () -> assertEquals("Postman1", test.getPlayers()[0].getName()),
                () -> assertEquals("Postman2", test.getPlayers()[1].getName()),

                () -> assertEquals("\"Draw\"", test.getWinner())
        );
    }

    @Test
    void loadNotExistGame() throws IOException {
        HttpUriRequest request = new HttpGet("http://localhost:8080/gameplay/load/8888");
        HttpResponse response = HttpClientBuilder.create().build().execute(request);
        assertEquals(response.getStatusLine().getStatusCode(), HttpStatus.NOT_FOUND.value());
        String messageFromResponse = EntityUtils.toString(response.getEntity());
        assertEquals("Could not find logfile number 8888 to load!", messageFromResponse);
    }

    @Test
    void deleteNotExistGame() throws IOException {
        HttpUriRequest request = new HttpDelete("http://localhost:8080/gameplay/delete/8888");
        HttpResponse response = HttpClientBuilder.create().build().execute(request);
        assertEquals(response.getStatusLine().getStatusCode(), HttpStatus.NOT_FOUND.value());
    }
}
