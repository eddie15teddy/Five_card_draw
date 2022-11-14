/*
CLASS:      Game
AUTHOR:     Eddie Butkaliuk
REMARKS:    A class representing the game
            Runs the game
            Implements GameLogicable
*/

import java.util.LinkedList;
import java.util.List;

public class Game implements GameLogicable
{
    private int currentState;
    private int gameNumber;
    private Deck deck;
    private Player human;
    private Player CPU;


    //Constructor
    public Game()
    {
        currentState = 0; //starts at stage 0. Next stage is called when game begins
        gameNumber = 1;

        deck = new Deck();
        deck.shuffle();

        Hand handPlayer = new Hand();
        handPlayer.draw(deck, true);
        Hand handCPU = new Hand();
        handCPU.draw(deck, false);

        human = new Player(handPlayer, "Player1");

        //CPU = new CPUDumb(handCPU);
        CPU = new CPUSmart(handCPU);
    }

    //Implemented methods
    public Handable getCPUHand() {return CPU.getHand();}
    public Handable getHumanHand() {return human.getHand();}
    public boolean nextState(String[] messages)
    {
        LinkedList<Cardable> discarded;

        //go to next stage or start over
        if(currentState < MAX_GAME_STATES)
            currentState++;
        else
        {
            currentState = 1;
            gameNumber++;
        }

        if(currentState == 1)
        {

            human.resetPoints();
            CPU.resetPoints();

            human.getHand().showAllCards();

            messages[0] = "Beginning of Game " + gameNumber;
            messages[1] = human.getName()+ ", choose which cards to discard";
            messages[2] = "and click the proceed button.";
        }
        else if(currentState == 2)
        {
            discarded = human.getHand().discard();
            deck.returnToDeck(discarded);
            CPU.selectCards();

            messages[0] = human.getName() + " has discarded cards.";
            messages[1] = CPU.getName() + " is thinking...";

        }
        else if(currentState == 3)
        {
            discarded = CPU.getHand().discard();
            deck.returnToDeck(discarded);
            messages[0] = CPU.getName() + " has discarded cards.";
            messages[1] = "Each player will be delt the same number of cards they discarded";
        }
        else if(currentState == 4)
        {
            human.getHand().draw(deck, true);
            CPU.getHand().draw(deck, false);
            messages[0] = "Each player has been dealt new cards.";
            messages[1] = "Click Proceed to see the winner!";
        }
        else if(currentState == 5)
        {
            CPU.getHand().showAllCards();

            messages[0] = CPU.getName() + " has: " + CPU.getHand().evaluateHand();
            messages[1] = human.getName() + " has: " + human.getHand().evaluateHand();

            if(human.getHand().compareTo(CPU.getHand()) == 1)
            {
                messages[2] = human.getName() + " wins!!!";
                human.wins();
            }
            else if(human.getHand().compareTo(CPU.getHand()) == -1)
            {
                messages[2] = CPU.getName() + " wins!!!";
                CPU.wins();
            }
            else
                messages[2] =  "Its a tie";

            messages[3] = human.getName() + " has won " + human.getNumWins() + " games. " + CPU.getName() + " has won " + CPU.getNumWins() + " games.";
        }
        else
        {
            messages[0] = "Click Proceed to play a new game!";
            discarded =  human.getHand().returnCards();
            deck.returnToDeck(discarded);

            discarded =  CPU.getHand().returnCards();
            deck.returnToDeck(discarded);

            deck.shuffle();

            human.getHand().draw(deck, false);
            CPU.getHand().draw(deck, false);
        }


        return true;
    }

}//Game
