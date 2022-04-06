package com.tictactoerest.tictactoegame;

import com.tictactoerest.tictactoegame.model.Player;
import com.tictactoerest.tictactoegame.model.Step;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static com.tictactoerest.tictactoegame.TicTacToe.gameConsole;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TictactoegameApplicationTests {

	@BeforeEach
	void init(){
		gameConsole.initialize();
		gameConsole.createGame("TEST1", "TEST2");
	}

	@Test
	void testCreated(){
		assertNotNull(gameConsole, "GameConsole not created");
	}

	@Test
	void playersAdded(){
		assertAll("Players",
				() -> assertEquals(1, gameConsole.getPlayers()[0].getId()),
				() -> assertEquals("TEST1", gameConsole.getPlayers()[0].getName()),
				() -> assertEquals("X", gameConsole.getPlayers()[0].getMark()),
				() -> assertEquals(2, gameConsole.getPlayers()[1].getId()),
				() -> assertEquals("TEST2", gameConsole.getPlayers()[1].getName()),
				() -> assertEquals("O", gameConsole.getPlayers()[1].getMark())
		);
	}

	@Test
	void testAddingStepsP1() {
		gameConsole.setMark(0);
		assertAll("Gameplay step 1",
				() -> assertEquals(1, gameConsole.getSteps().get(0).getId()),
				() -> assertEquals(1, gameConsole.getSteps().get(0).getPlayerId()),
				() -> assertEquals(1, gameConsole.getSteps().get(0).getCellId()),
				() -> assertEquals(1, gameConsole.getMap()[0][0]),
				() -> assertEquals(8, gameConsole.getTurnsLeft()),
				() -> assertTrue(gameConsole.gameContinue)
		);
	}

	@Test
	void testAddingStepsP2(){
		gameConsole.setMark(0);
		gameConsole.setMark(1);
		assertAll("Gameplay step 2",
				() -> assertEquals(2, gameConsole.getSteps().get(1).getId()),
				() -> assertEquals(2, gameConsole.getSteps().get(1).getPlayerId()),
				() -> assertEquals(2, gameConsole.getSteps().get(1).getCellId()),
				() -> assertEquals(-1, gameConsole.getMap()[0][1]),
				() -> assertEquals(7, gameConsole.getTurnsLeft()),
				() -> assertTrue(gameConsole.gameContinue)
		);
	}

	@Test
	void fastWinP1(){
		// X O -
		// X O -
		// X - -
		gameConsole.setMark(0);
		gameConsole.setMark(1);
		gameConsole.setMark(3);
		gameConsole.setMark(4);
		gameConsole.setMark(6);
		gameConsole.gameContinue = gameConsole.checkNoWin();
		ArrayList<Step> testStep = gameConsole.getSteps();
		Player winner = gameConsole.getWinner();
		assertAll("Gameplay won player #1",
				//Turn 1
				() -> assertEquals(1, testStep.get(0).getId()),
				() -> assertEquals(1, testStep.get(0).getPlayerId()),
				() -> assertEquals(1, testStep.get(0).getCellId()),
				() -> assertEquals(1, gameConsole.getMap()[0][0]),
				//Turn 2
				() -> assertEquals(2, testStep.get(1).getId()),
				() -> assertEquals(2, testStep.get(1).getPlayerId()),
				() -> assertEquals(2, testStep.get(1).getCellId()),
				() -> assertEquals(-1, gameConsole.getMap()[0][1]),
				//Turn 3
				() -> assertEquals(3, testStep.get(2).getId()),
				() -> assertEquals(1, testStep.get(2).getPlayerId()),
				() -> assertEquals(4, testStep.get(2).getCellId()),
				() -> assertEquals(1, gameConsole.getMap()[1][0]),
				//Turn 4
				() -> assertEquals(4, testStep.get(3).getId()),
				() -> assertEquals(2, testStep.get(3).getPlayerId()),
				() -> assertEquals(5, testStep.get(3).getCellId()),
				() -> assertEquals(-1, gameConsole.getMap()[1][1]),
				//Turn 5
				() -> assertEquals(5, testStep.get(4).getId()),
				() -> assertEquals(1, testStep.get(4).getPlayerId()),
				() -> assertEquals(7, testStep.get(4).getCellId()),
				() -> assertEquals(1, gameConsole.getMap()[2][0]),
				//End game
				() -> assertEquals(4, gameConsole.getTurnsLeft()),
				() -> assertFalse(gameConsole.gameContinue),
				() -> assertEquals("TEST1", winner.getName()),
				() -> assertEquals("X", winner.getMark()),
				() -> assertEquals(1, winner.getId())
		);
	}

	@Test
	void fastWinP2(){
		// X O -
		// X O -
		// - O X
		gameConsole.notPossibleTurn(0);
		gameConsole.notPossibleTurn(1);
		gameConsole.notPossibleTurn(3);
		gameConsole.notPossibleTurn(4);
		gameConsole.notPossibleTurn(8);
		gameConsole.notPossibleTurn(7);
		ArrayList<Step> testStep = gameConsole.getSteps();
		Player winner = gameConsole.getWinner();
		assertAll("Gameplay won player #2",
				//Turn 1
				() -> assertEquals(1, testStep.get(0).getId()),
				() -> assertEquals(1, testStep.get(0).getPlayerId()),
				() -> assertEquals(1, testStep.get(0).getCellId()),
				() -> assertEquals(1, gameConsole.getMap()[0][0]),

				//Turn 2
				() -> assertEquals(2, testStep.get(1).getId()),
				() -> assertEquals(2, testStep.get(1).getPlayerId()),
				() -> assertEquals(2, testStep.get(1).getCellId()),
				() -> assertEquals(-1, gameConsole.getMap()[0][1]),

				//Turn 3
				() -> assertEquals(3, testStep.get(2).getId()),
				() -> assertEquals(1, testStep.get(2).getPlayerId()),
				() -> assertEquals(4, testStep.get(2).getCellId()),
				() -> assertEquals(1, gameConsole.getMap()[1][0]),

				//Turn 4
				() -> assertEquals(4, testStep.get(3).getId()),
				() -> assertEquals(2, testStep.get(3).getPlayerId()),
				() -> assertEquals(5, testStep.get(3).getCellId()),
				() -> assertEquals(-1, gameConsole.getMap()[1][1]),

				//Turn 5
				() -> assertEquals(5, testStep.get(4).getId()),
				() -> assertEquals(1, testStep.get(4).getPlayerId()),
				() -> assertEquals(9, testStep.get(4).getCellId()),
				() -> assertEquals(1, gameConsole.getMap()[2][2]),

				//Turn 6
				() -> assertEquals(6, testStep.get(5).getId()),
				() -> assertEquals(2, testStep.get(5).getPlayerId()),
				() -> assertEquals(8, testStep.get(5).getCellId()),
				() -> assertEquals(-1, gameConsole.getMap()[2][1]),

				//End game
				() -> assertEquals(3, gameConsole.getTurnsLeft()),
				() -> assertFalse(gameConsole.gameContinue),
				() -> assertEquals("TEST2", winner.getName()),
				() -> assertEquals("O", winner.getMark()),
				() -> assertEquals(2, winner.getId())
		);
	}

	@Test
	void draw(){
		// X O X
		// X O X
		// O X O
		gameConsole.notPossibleTurn(0);
		gameConsole.notPossibleTurn(1);
		gameConsole.notPossibleTurn(2);
		gameConsole.notPossibleTurn(4);
		gameConsole.notPossibleTurn(3);
		gameConsole.notPossibleTurn(6);
		gameConsole.notPossibleTurn(5);
		gameConsole.notPossibleTurn(8);
		gameConsole.notPossibleTurn(7);
		ArrayList<Step> testStep = gameConsole.getSteps();
		Player winner = gameConsole.getWinner();
		assertAll("Gameplay draw",
				//Turn 1
				() -> assertEquals(1, testStep.get(0).getId()),
				() -> assertEquals(1, testStep.get(0).getPlayerId()),
				() -> assertEquals(1, testStep.get(0).getCellId()),
				() -> assertEquals(1, gameConsole.getMap()[0][0]),

				//Turn 2
				() -> assertEquals(2, testStep.get(1).getId()),
				() -> assertEquals(2, testStep.get(1).getPlayerId()),
				() -> assertEquals(2, testStep.get(1).getCellId()),
				() -> assertEquals(-1, gameConsole.getMap()[0][1]),

				//Turn 3
				() -> assertEquals(3, testStep.get(2).getId()),
				() -> assertEquals(1, testStep.get(2).getPlayerId()),
				() -> assertEquals(3, testStep.get(2).getCellId()),
				() -> assertEquals(1, gameConsole.getMap()[0][2]),

				//Turn 4
				() -> assertEquals(4, testStep.get(3).getId()),
				() -> assertEquals(2, testStep.get(3).getPlayerId()),
				() -> assertEquals(5, testStep.get(3).getCellId()),
				() -> assertEquals(-1, gameConsole.getMap()[1][1]),

				//Turn 5
				() -> assertEquals(5, testStep.get(4).getId()),
				() -> assertEquals(1, testStep.get(4).getPlayerId()),
				() -> assertEquals(4, testStep.get(4).getCellId()),
				() -> assertEquals(1, gameConsole.getMap()[1][0]),

				//Turn 6
				() -> assertEquals(6, testStep.get(5).getId()),
				() -> assertEquals(2, testStep.get(5).getPlayerId()),
				() -> assertEquals(7, testStep.get(5).getCellId()),
				() -> assertEquals(-1, gameConsole.getMap()[2][0]),

				//Turn 7
				() -> assertEquals(7, testStep.get(6).getId()),
				() -> assertEquals(1, testStep.get(6).getPlayerId()),
				() -> assertEquals(6, testStep.get(6).getCellId()),
				() -> assertEquals(1, gameConsole.getMap()[1][2]),

				//Turn 8
				() -> assertEquals(8, testStep.get(7).getId()),
				() -> assertEquals(2, testStep.get(7).getPlayerId()),
				() -> assertEquals(9, testStep.get(7).getCellId()),
				() -> assertEquals(-1, gameConsole.getMap()[2][2]),

				//Turn 9
				() -> assertEquals(9, testStep.get(8).getId()),
				() -> assertEquals(1, testStep.get(8).getPlayerId()),
				() -> assertEquals(8, testStep.get(8).getCellId()),
				() -> assertEquals(1, gameConsole.getMap()[2][1]),

				//End game
				() -> assertEquals(0, gameConsole.getTurnsLeft()),
				() -> assertFalse(gameConsole.gameContinue),
				() -> assertNull(winner)
		);
	}

	@Test
	void doubleMark(){
		assertFalse(gameConsole.notPossibleTurn(0));
		assertTrue(gameConsole.notPossibleTurn(0));
	}

	@Test
	void outOfMap(){
		assertTrue(gameConsole.notPossibleTurn(-1));
		assertTrue(gameConsole.notPossibleTurn(9));
		assertTrue(gameConsole.notPossibleTurn(-5));
		assertTrue(gameConsole.notPossibleTurn(520));
	}
}
