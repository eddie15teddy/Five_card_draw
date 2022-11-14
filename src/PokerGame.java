//-----------------------------------------
// NAME		: Eddie Butkaliuk
// STUDENT NUMBER	: 007910587
// COURSE		: COMP 2150
// INSTRUCTOR	: your instructor
// ASSIGNMENT	: assignment #3
// QUESTION	: question #1
// 
// REMARKS: This is the class containing the main method that will run the game.
//
// In my project, ease of implementation was valued higher than running time
// This isn't a problem since a hand only has 5 cards
//-----------------------------------------

import java.util.LinkedList;

public class PokerGame
{
	public static void main(String[] args)
	{
		//Build a game logic, feed it into the PokerTableDisplay
		GameLogicable gl = new Game();  //Insert a call to the constructor of your class that implements GameLogicable
		PokerTableDisplay ptd = new PokerTableDisplay(gl);
	}
}
	