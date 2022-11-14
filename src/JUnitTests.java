/*
CLASS:      JUnitTests
AUTHOR:     Eddie Butkaliuk
REMARKS:    A class to thoroughly test the compareTo method in hand
*/

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class JUnitTests {

//Tests
    @Test
	public void test1()
	{
		Cardable[] cards1 = {new Card(2, Cardable.Suit.CLUB), new Card(2, Cardable.Suit.HEART), new Card(3, Cardable.Suit.CLUB), new Card(4, Cardable.Suit.CLUB), new Card(2, Cardable.Suit.DIAMOND)};
		TestableHand th1 = new Hand();
		th1.addCards(cards1);
		
		Cardable[] cards2 = {new Card(3, Cardable.Suit.HEART), new Card(4, Cardable.Suit.HEART), new Card(5, Cardable.Suit.HEART), new Card(6, Cardable.Suit.HEART), new Card(7, Cardable.Suit.DIAMOND)};
		TestableHand th2 = new Hand();
		th2.addCards(cards2);
		
		assertTrue(th1.compareTo(th2) < 0, "Straight beats Three of a kind.");
    }

	@Test
	public void testHighCard()
	{
		Cardable[] cards1 = getHighestHighCard();
		TestableHand h1 = new Hand();
		h1.addCards(cards1);

		Cardable[] cards2 = getLowestHighCard();
		TestableHand h2 = new Hand();
		h2.addCards(cards2);

		assertTrue(h1.compareTo(h2) > 0, "High card Ace beats high card 6");
	}

	@Test
	public void testHighCardTie()
	{
		Cardable[] cards2 = {new Card(14, Cardable.Suit.CLUB), new Card(13, Cardable.Suit.HEART), new Card(12, Cardable.Suit.CLUB), new Card(11, Cardable.Suit.CLUB), new Card(8, Cardable.Suit.DIAMOND)};

		TestableHand h1 = new Hand();
		h1.addCards(cards2);

		TestableHand h2 = new Hand();
		h2.addCards(cards2);

		assertEquals(0, h1.compareTo(h2), "High cards equal");
	}

	@Test
	public void testHighCardVSPair()
	{
		Cardable[] highCard = getHighestHighCard();

		TestableHand h1 = new Hand();
		h1.addCards(highCard);

		Cardable[] pair = {new Card(2, Cardable.Suit.CLUB), new Card(2, Cardable.Suit.HEART), new Card(4, Cardable.Suit.CLUB), new Card(5, Cardable.Suit.CLUB), new Card(7, Cardable.Suit.DIAMOND)};

		TestableHand h2 = new Hand();
		h2.addCards(pair);

		assertTrue(h2.compareTo(h1)>0,"Pair beats high card");
	}

	@Test
	public void testHighCardVS2Pair()
	{
		Cardable[] highCard = getHighestHighCard();
		TestableHand h1 = new Hand();
		h1.addCards(highCard);

		Cardable[] pair = getLowestTwoPair();

		TestableHand h2 = new Hand();
		h2.addCards(pair);

		assertTrue(h2.compareTo(h1)>0,"Two pair beats high card");
	}

	@Test
	public void testHighCardVSThreeKind()
	{
		Cardable[] highCard = getHighestHighCard();
		TestableHand h1 = new Hand();
		h1.addCards(highCard);

		Cardable[] pair = getLowestThreeOfKind();

		TestableHand h2 = new Hand();
		h2.addCards(pair);

		assertTrue(h2.compareTo(h1)>0,"Three of a kind pair beats high card");
	}
	@Test
	public void testHighCardVSStraight()
	{
		Cardable[] highCard = getHighestHighCard();
		TestableHand h1 = new Hand();
		h1.addCards(highCard);

		Cardable[] cards = getLowestStraight();

		TestableHand h2 = new Hand();
		h2.addCards(cards);

		assertTrue(h2.compareTo(h1)>0,"Straight beats high card");
	}
	@Test
	public void testHighCardVSFlush()
	{
		Cardable[] highCard = getHighestHighCard();
		TestableHand h1 = new Hand();
		h1.addCards(highCard);

		Cardable[] pair = getLowestFlush();

		TestableHand h2 = new Hand();
		h2.addCards(pair);

		assertTrue(h2.compareTo(h1)>0,"Flush beats high card");
	}
	@Test
	public void testHighCardVSFullHouse()
	{
		Cardable[] highCard = getHighestHighCard();
		TestableHand h1 = new Hand();
		h1.addCards(highCard);

		Cardable[] pair = getLowestFullHouse();

		TestableHand h2 = new Hand();
		h2.addCards(pair);

		assertTrue(h2.compareTo(h1)>0,"Flush beats high card");
	}
	@Test
	public void testHighCardVSFourKind()
	{
		Cardable[] highCard = getHighestHighCard();
		TestableHand h1 = new Hand();
		h1.addCards(highCard);

		Cardable[] pair = getLowestFourKind();

		TestableHand h2 = new Hand();
		h2.addCards(pair);

		assertTrue(h2.compareTo(h1)>0,"Flush beats high card");
	}
	@Test
	public void testHighCardVSStraightFlush()
	{
		Cardable[] highCard = getHighestHighCard();
		TestableHand h1 = new Hand();
		h1.addCards(highCard);

		Cardable[] pair = getLowestStraightFlush();

		TestableHand h2 = new Hand();
		h2.addCards(pair);

		assertTrue(h2.compareTo(h1)>0," Straight flush beats high card");
	}

	@Test
	public void testPair1()
	{
		Cardable[] pairL = getLowestPair();
		TestableHand h1 = new Hand();
		h1.addCards(pairL);

		Cardable[] pairH = getHighestPair();

		TestableHand h2 = new Hand();
		h2.addCards(pairH);

		assertTrue(h2.compareTo(h1)>0,"High pair beats low pair");
	}

	@Test
	public void testPairTie()
	{
		Cardable[] pairH1 = getHighestPair();
		TestableHand h1 = new Hand();
		h1.addCards(pairH1);

		Cardable[] pairH2 = getHighestPair();

		TestableHand h2 = new Hand();
		h2.addCards(pairH2);

		assertEquals(0, h2.compareTo(h1), "Pair tie");
	}
	@Test
	public void testPair2()
	{
		Cardable[] pairH1 = getHighestPair();
		TestableHand h1 = new Hand();
		h1.addCards(pairH1);

		Cardable[] pairH2 = {new Card(14, Cardable.Suit.DIAMOND), new Card(14, Cardable.Suit.SPADE), new Card(13, Cardable.Suit.CLUB), new Card(12, Cardable.Suit.CLUB), new Card(10, Cardable.Suit.CLUB)};;

		TestableHand h2 = new Hand();
		h2.addCards(pairH2);

		assertTrue(h2.compareTo(h1)<0,"High pair beats low pair. Only last card differs");
	}
	@Test
	public void testPairVSTwoPairs()
	{
		Cardable[] pairH1 = getHighestPair();
		TestableHand h1 = new Hand();
		h1.addCards(pairH1);

		Cardable[] pairH2 = getLowestTwoPair();

		TestableHand h2 = new Hand();
		h2.addCards(pairH2);

		assertTrue(h2.compareTo(h1)>0,"Two pair beats pair");
	}
	@Test
	public void testPairVSThreeKind()
	{
		Cardable[] pairH1 = getHighestPair();
		TestableHand h1 = new Hand();
		h1.addCards(pairH1);

		Cardable[] pairH2 = getLowestThreeOfKind();

		TestableHand h2 = new Hand();
		h2.addCards(pairH2);

		assertTrue(h2.compareTo(h1)>0,"Three of a kind beats pair");
	}
	@Test
	public void testPairVSStraight()
	{
		Cardable[] pairH1 = getHighestPair();
		TestableHand h1 = new Hand();
		h1.addCards(pairH1);

		Cardable[] pairH2 = getLowestStraight();

		TestableHand h2 = new Hand();
		h2.addCards(pairH2);

		assertTrue(h2.compareTo(h1)>0,"Straight beats pair");
	}
	@Test
	public void testPairVSFlush()
	{
		Cardable[] pairH1 = getHighestPair();
		TestableHand h1 = new Hand();
		h1.addCards(pairH1);

		Cardable[] pairH2 = getLowestFlush();

		TestableHand h2 = new Hand();
		h2.addCards(pairH2);

		assertTrue(h2.compareTo(h1)>0,"Flush beats pair");
	}
	@Test
	public void testPairVSFullHouse()
	{
		Cardable[] pairH1 = getHighestPair();
		TestableHand h1 = new Hand();
		h1.addCards(pairH1);

		Cardable[] pairH2 = getLowestFullHouse();

		TestableHand h2 = new Hand();
		h2.addCards(pairH2);

		assertTrue(h2.compareTo(h1)>0,"Full house beats pair.");
	}
	@Test
	public void testPairVSFourKind()
	{
		Cardable[] pairH1 = getHighestPair();
		TestableHand h1 = new Hand();
		h1.addCards(pairH1);

		Cardable[] pairH2 = getLowestFourKind();

		TestableHand h2 = new Hand();
		h2.addCards(pairH2);

		assertTrue(h2.compareTo(h1)>0,"Four of a kind beats pair.");
	}
	@Test
	public void testPairVSStraightFlush()
	{
		Cardable[] pairH1 = getHighestPair();
		TestableHand h1 = new Hand();
		h1.addCards(pairH1);

		Cardable[] cards2 = getLowestStraightFlush();

		TestableHand h2 = new Hand();
		h2.addCards(cards2);

		assertTrue(h2.compareTo(h1)>0,"Straight flush beats pair.");
	}
	@Test
	public void testTwoPair1()
	{
		Cardable[] cards1 = getLowestTwoPair();
		TestableHand h1 = new Hand();
		h1.addCards(cards1);

		Cardable[] cards2 = getHighestTwoPair();

		TestableHand h2 = new Hand();
		h2.addCards(cards2);

		assertTrue(h2.compareTo(h1)>0,"High two pair wins.");
	}
	@Test
	public void testTwoPair2()
	{
		Cardable[] cards1 = getLowestTwoPair();
		TestableHand h1 = new Hand();
		h1.addCards(cards1);

		Cardable[] cards2 = {new Card(2, Cardable.Suit.CLUB), new Card(2, Cardable.Suit.HEART), new Card(3, Cardable.Suit.CLUB), new Card(3, Cardable.Suit.CLUB), new Card(5, Cardable.Suit.DIAMOND)};

		TestableHand h2 = new Hand();
		h2.addCards(cards2);

		assertTrue(h2.compareTo(h1)>0,"Higher two pair wins. Only last card differs");
	}
	@Test
	public void testTwoPairTie()
	{
		Cardable[] pairH1 = getHighestTwoPair();
		TestableHand h1 = new Hand();
		h1.addCards(pairH1);

		Cardable[] pairH2 = getHighestTwoPair();

		TestableHand h2 = new Hand();
		h2.addCards(pairH2);

		assertEquals(0, h2.compareTo(h1), "Two pair tie");
	}
	@Test
	public void testTwoPairVSThreeKind()
	{
		Cardable[] pairH1 = getHighestTwoPair();
		TestableHand h1 = new Hand();
		h1.addCards(pairH1);

		Cardable[] pairH2 = getLowestThreeOfKind();

		TestableHand h2 = new Hand();
		h2.addCards(pairH2);

		assertTrue(h2.compareTo(h1)>0,"Three of a kind two beats pair");
	}
	@Test
	public void testTwoPairVSStraight()
	{
		Cardable[] pairH1 = getHighestTwoPair();
		TestableHand h1 = new Hand();
		h1.addCards(pairH1);

		Cardable[] pairH2 = getLowestStraight();

		TestableHand h2 = new Hand();
		h2.addCards(pairH2);

		assertTrue(h2.compareTo(h1)>0,"Straight beats two pair");
	}
	@Test
	public void testTwoPairVSFlush()
	{
		Cardable[] pairH1 = getHighestTwoPair();
		TestableHand h1 = new Hand();
		h1.addCards(pairH1);

		Cardable[] pairH2 = getLowestFlush();

		TestableHand h2 = new Hand();
		h2.addCards(pairH2);

		assertTrue(h2.compareTo(h1)>0,"Flush beats two pair");
	}
	@Test
	public void testTwoPairVSFullHouse()
	{
		Cardable[] pairH1 = getHighestTwoPair();
		TestableHand h1 = new Hand();
		h1.addCards(pairH1);

		Cardable[] pairH2 = getLowestFullHouse();

		TestableHand h2 = new Hand();
		h2.addCards(pairH2);

		assertTrue(h2.compareTo(h1)>0,"Full house beats two pair.");
	}
	@Test
	public void testTwoPairVSFourKind()
	{
		Cardable[] pairH1 = getHighestTwoPair();
		TestableHand h1 = new Hand();
		h1.addCards(pairH1);

		Cardable[] pairH2 = getLowestFourKind();

		TestableHand h2 = new Hand();
		h2.addCards(pairH2);

		assertTrue(h2.compareTo(h1)>0,"Four of a kind beats two pair.");
	}
	@Test
	public void testTwoPairVSStraightFlush()
	{
		Cardable[] pairH1 = getHighestTwoPair();
		TestableHand h1 = new Hand();
		h1.addCards(pairH1);

		Cardable[] cards2 = getLowestStraightFlush();

		TestableHand h2 = new Hand();
		h2.addCards(cards2);

		assertTrue(h2.compareTo(h1)>0,"Straight flush beats two pair.");
	}

	@Test
	public void testThreeKind1()
	{
		Cardable[] cards1 = getLowestThreeOfKind();
		TestableHand h1 = new Hand();
		h1.addCards(cards1);

		Cardable[] cards2 = getHighestThreeOfKind();

		TestableHand h2 = new Hand();
		h2.addCards(cards2);

		assertTrue(h2.compareTo(h1)>0,"High three of a kind wins.");
	}
	@Test
	public void testThreeKind2()
	{
		Cardable[] cards1 = getLowestThreeOfKind();
		TestableHand h1 = new Hand();
		h1.addCards(cards1);

		Cardable[] cards2 = {new Card(2, Cardable.Suit.CLUB), new Card(2, Cardable.Suit.HEART), new Card(2, Cardable.Suit.SPADE), new Card(3, Cardable.Suit.CLUB), new Card(5, Cardable.Suit.HEART)};

		TestableHand h2 = new Hand();
		h2.addCards(cards2);

		assertTrue(h2.compareTo(h1)>0,"Higher three of a kind wins. Only last card differs");
	}
	@Test
	public void testThreeKindTie()
	{
		Cardable[] pairH1 = getHighestThreeOfKind();
		TestableHand h1 = new Hand();
		h1.addCards(pairH1);

		Cardable[] pairH2 = getHighestThreeOfKind();

		TestableHand h2 = new Hand();
		h2.addCards(pairH2);

		assertEquals(0, h2.compareTo(h1), "Three of a kind tie");
	}
	@Test
	public void testThreeKindVSStraight()
	{
		Cardable[] pairH1 = getHighestThreeOfKind();
		TestableHand h1 = new Hand();
		h1.addCards(pairH1);

		Cardable[] pairH2 = getLowestStraight();

		TestableHand h2 = new Hand();
		h2.addCards(pairH2);

		assertTrue(h2.compareTo(h1)>0,"Straight beats three of a kind");
	}
	@Test
	public void testThreeKindVSFlush()
	{
		Cardable[] pairH1 = getHighestThreeOfKind();
		TestableHand h1 = new Hand();
		h1.addCards(pairH1);

		Cardable[] pairH2 = getLowestFlush();

		TestableHand h2 = new Hand();
		h2.addCards(pairH2);

		assertTrue(h2.compareTo(h1)>0,"Flush beats thee of a kind");
	}
	@Test
	public void testThreeKindVSFullHouse()
	{
		Cardable[] pairH1 = getHighestThreeOfKind();
		TestableHand h1 = new Hand();
		h1.addCards(pairH1);

		Cardable[] pairH2 = getLowestFullHouse();

		TestableHand h2 = new Hand();
		h2.addCards(pairH2);

		assertTrue(h2.compareTo(h1)>0,"Full house beats three of a kind");
	}
	@Test
	public void testThreeKindVSFourKind()
	{
		Cardable[] pairH1 = getHighestThreeOfKind();
		TestableHand h1 = new Hand();
		h1.addCards(pairH1);

		Cardable[] pairH2 = getLowestFourKind();

		TestableHand h2 = new Hand();
		h2.addCards(pairH2);

		assertTrue(h2.compareTo(h1)>0,"Four of a kind beats three of a kind");
	}
	@Test
	public void testThreeKindVSStraightFlush()
	{
		Cardable[] cards1 = getHighestThreeOfKind();
		TestableHand h1 = new Hand();
		h1.addCards(cards1);

		Cardable[] cards2 = getLowestStraightFlush();

		TestableHand h2 = new Hand();
		h2.addCards(cards2);

		assertTrue(h2.compareTo(h1)>0,"Straight flush beats three of a kind.");
	}


	@Test
	public void testStraight1()
	{
		Cardable[] cards1 = getLowestStraight();
		TestableHand h1 = new Hand();
		h1.addCards(cards1);

		Cardable[] cards2 = getHighestStraight();

		TestableHand h2 = new Hand();
		h2.addCards(cards2);

		assertTrue(h2.compareTo(h1)>0,"High straight wins.");
	}
	@Test
	public void testStraight2()
	{
		Cardable[] cards1 = getLowestStraight();
		TestableHand h1 = new Hand();
		h1.addCards(cards1);

		Cardable[] cards2 = {new Card(6, Cardable.Suit.CLUB), new Card(2, Cardable.Suit.HEART), new Card(3, Cardable.Suit.SPADE), new Card(4, Cardable.Suit.CLUB), new Card(5, Cardable.Suit.HEART)};

		TestableHand h2 = new Hand();
		h2.addCards(cards2);

		assertTrue(h2.compareTo(h1)>0,"Higher straight wins. Only one card differs");
	}
	@Test
	public void testStraightTie()
	{
		Cardable[] cards1 = getLowestStraight();
		TestableHand h1 = new Hand();
		h1.addCards(cards1);

		Cardable[] cards2 = getLowestStraight();

		TestableHand h2 = new Hand();
		h2.addCards(cards2);

		assertEquals(0, h2.compareTo(h1), "Straight tie");
	}
	@Test
	public void testStraightVSFlush()
	{
		Cardable[] cards1 = getLowestStraight();
		TestableHand h1 = new Hand();
		h1.addCards(cards1);

		Cardable[] cards2 = getLowestFlush();

		TestableHand h2 = new Hand();
		h2.addCards(cards2);

		assertTrue(h2.compareTo(h1)>0,"Flush beats straight");
	}
	@Test
	public void testStraightVSFullHouse()
	{
		Cardable[] cards1 = getLowestStraight();
		TestableHand h1 = new Hand();
		h1.addCards(cards1);

		Cardable[] cards2 = getLowestFullHouse();

		TestableHand h2 = new Hand();
		h2.addCards(cards2);

		assertTrue(h2.compareTo(h1)>0,"Full house beats straight");
	}
	@Test
	public void testStraightVSFourKind()
	{
		Cardable[] cards1 = getLowestStraight();
		TestableHand h1 = new Hand();
		h1.addCards(cards1);

		Cardable[] cards2 = getLowestFourKind();

		TestableHand h2 = new Hand();
		h2.addCards(cards2);

		assertTrue(h2.compareTo(h1)>0,"Four of a kind beats straight");
	}
	@Test
	public void testStraightVSStraightFlush()
	{
		Cardable[] cards1 = getLowestStraight();
		TestableHand h1 = new Hand();
		h1.addCards(cards1);

		Cardable[] cards2 = getLowestStraightFlush();

		TestableHand h2 = new Hand();
		h2.addCards(cards2);

		assertTrue(h2.compareTo(h1)>0,"Straight flush beats straight");
	}

	@Test
	public void testFlush1()
	{
		Cardable[] cards1 = getLowestFlush();
		TestableHand h1 = new Hand();
		h1.addCards(cards1);

		Cardable[] cards2 = getHighestFlush();

		TestableHand h2 = new Hand();
		h2.addCards(cards2);

		assertTrue(h2.compareTo(h1)>0,"High flush wins.");
	}
	@Test
	public void testFlush2()
	{
		Cardable[] cards1 = getLowestFlush();
		TestableHand h1 = new Hand();
		h1.addCards(cards1);

		Cardable[] cards2 = {new Card(2, Cardable.Suit.CLUB), new Card(3, Cardable.Suit.CLUB), new Card(4, Cardable.Suit.CLUB), new Card(6, Cardable.Suit.CLUB), new Card(7, Cardable.Suit.CLUB)};

		TestableHand h2 = new Hand();
		h2.addCards(cards2);

		assertTrue(h2.compareTo(h1)>0,"Higher straight wins. Only one card differs");
	}
	@Test
	public void testFlushTie()
	{
		Cardable[] cards1 = getLowestFlush();
		TestableHand h1 = new Hand();
		h1.addCards(cards1);

		Cardable[] cards2 = getLowestFlush();

		TestableHand h2 = new Hand();
		h2.addCards(cards2);

		assertEquals(0, h2.compareTo(h1), "Flush tie");
	}

	@Test
	public void testFlushVSFullHouse()
	{
		Cardable[] cards1 = getHighestFlush();
		TestableHand h1 = new Hand();
		h1.addCards(cards1);

		Cardable[] cards2 = getLowestFullHouse();

		TestableHand h2 = new Hand();
		h2.addCards(cards2);

		assertTrue(h2.compareTo(h1)>0,"Full house beats flush");
	}
	@Test
	public void testFlushVSFourKind()
	{
		Cardable[] cards1 = getHighestFlush();
		TestableHand h1 = new Hand();
		h1.addCards(cards1);

		Cardable[] cards2 = getLowestFourKind();

		TestableHand h2 = new Hand();
		h2.addCards(cards2);

		assertTrue(h2.compareTo(h1)>0,"Four of a kind beats flush");
	}
	@Test
	public void testFlushVSStraightFlush()
	{
		Cardable[] cards1 = getHighestFlush();
		TestableHand h1 = new Hand();
		h1.addCards(cards1);

		Cardable[] cards2 = getLowestStraightFlush();

		TestableHand h2 = new Hand();
		h2.addCards(cards2);

		assertTrue(h2.compareTo(h1)>0,"Straight flush beats flush");
	}

	@Test
	public void testFullHouse1()
	{
		Cardable[] cards1 = getLowestFullHouse();
		TestableHand h1 = new Hand();
		h1.addCards(cards1);

		Cardable[] cards2 = getHighestFullHouse();

		TestableHand h2 = new Hand();
		h2.addCards(cards2);

		assertTrue(h2.compareTo(h1)>0,"High full house wins");
	}
	@Test
	public void testFullHouse2()
	{
		Cardable[] cards1 = getLowestFullHouse();
		TestableHand h1 = new Hand();
		h1.addCards(cards1);

		Cardable[] cards2 = {new Card(2, Cardable.Suit.CLUB), new Card(2, Cardable.Suit.SPADE), new Card(2, Cardable.Suit.DIAMOND), new Card(4, Cardable.Suit.DIAMOND), new Card(4, Cardable.Suit.CLUB)};

		TestableHand h2 = new Hand();
		h2.addCards(cards2);

		assertTrue(h2.compareTo(h1)>0,"Higher full house wins. Only last two cards differ");
	}
	@Test
	public void testFullHouseTie()
	{
		Cardable[] cards1 = getLowestFullHouse();
		TestableHand h1 = new Hand();
		h1.addCards(cards1);

		Cardable[] cards2 = getLowestFullHouse();

		TestableHand h2 = new Hand();
		h2.addCards(cards2);

		assertEquals(0, h2.compareTo(h1), "Full house tie");
	}

	@Test
	public void testFullHouseVSFourKind()
	{
		Cardable[] cards1 = getHighestFullHouse();
		TestableHand h1 = new Hand();
		h1.addCards(cards1);

		Cardable[] cards2 = getLowestFourKind();

		TestableHand h2 = new Hand();
		h2.addCards(cards2);

		assertTrue(h2.compareTo(h1)>0,"Four of a kind beats full house");
	}
	@Test
	public void testFullHouseVSStraightFlush()
	{
		Cardable[] cards1 = getHighestFullHouse();
		TestableHand h1 = new Hand();
		h1.addCards(cards1);

		Cardable[] cards2 = getLowestStraightFlush();

		TestableHand h2 = new Hand();
		h2.addCards(cards2);

		assertTrue(h2.compareTo(h1)>0,"Straight flush beats full house");
	}
	@Test
	public void testFourKind1()
	{
		Cardable[] cards1 = getLowestFourKind();
		TestableHand h1 = new Hand();
		h1.addCards(cards1);

		Cardable[] cards2 = getHighestFourKind();

		TestableHand h2 = new Hand();
		h2.addCards(cards2);

		assertTrue(h2.compareTo(h1)>0,"High four of a kind wins");
	}
	@Test
	public void testFourKind2()
	{
		Cardable[] cards1 = getLowestFourKind();
		TestableHand h1 = new Hand();
		h1.addCards(cards1);

		Cardable[] cards2 = {new Card(2, Cardable.Suit.CLUB), new Card(2, Cardable.Suit.SPADE), new Card(2, Cardable.Suit.DIAMOND), new Card(2, Cardable.Suit.HEART), new Card(4, Cardable.Suit.CLUB)};

		TestableHand h2 = new Hand();
		h2.addCards(cards2);

		assertTrue(h2.compareTo(h1)>0,"Higher four of a kind wins. Only last card differs");
	}
	@Test
	public void testFourKindTie()
	{
		Cardable[] cards1 = getLowestFourKind();
		TestableHand h1 = new Hand();
		h1.addCards(cards1);

		Cardable[] cards2 = getLowestFourKind();

		TestableHand h2 = new Hand();
		h2.addCards(cards2);

		assertEquals(0, h2.compareTo(h1), "Four of a kind tie");
	}

	@Test
	public void testFourKindVSStraightFlush()
	{
		Cardable[] cards1 = getHighestFourKind();
		TestableHand h1 = new Hand();
		h1.addCards(cards1);

		Cardable[] cards2 = getLowestStraightFlush();

		TestableHand h2 = new Hand();
		h2.addCards(cards2);

		assertTrue(h2.compareTo(h1)>0,"Straight flush beats four of a kind");
	}

	@Test
	public void testStraightFlush1()
	{
		Cardable[] cards1 = getLowestStraightFlush();
		TestableHand h1 = new Hand();
		h1.addCards(cards1);

		Cardable[] cards2 = getHighestStraightFlush();

		TestableHand h2 = new Hand();
		h2.addCards(cards2);

		assertTrue(h2.compareTo(h1)>0,"High straight flush wins");
	}
	@Test
	public void testStraightFlush2()
	{
		Cardable[] cards1 = getLowestStraightFlush();
		TestableHand h1 = new Hand();
		h1.addCards(cards1);

		Cardable[] cards2 = {new Card(6, Cardable.Suit.SPADE), new Card(2, Cardable.Suit.SPADE), new Card(3, Cardable.Suit.SPADE), new Card(4, Cardable.Suit.SPADE), new Card(5, Cardable.Suit.SPADE)};

		TestableHand h2 = new Hand();
		h2.addCards(cards2);

		assertTrue(h2.compareTo(h1)>0,"Higher straight flush wins. Only one card differs");
	}
	@Test
	public void testStraightFlushTie()
	{
		Cardable[] cards1 = getLowestStraightFlush();
		TestableHand h1 = new Hand();
		h1.addCards(cards1);

		Cardable[] cards2 = getLowestStraightFlush();

		TestableHand h2 = new Hand();
		h2.addCards(cards2);

		assertEquals(0, h2.compareTo(h1), "Straight flush tie");
	}

	//Helper methods
	private Cardable[] getHighestHighCard()
	{
		return new Cardable[]{new Card(14, Cardable.Suit.CLUB), new Card(13, Cardable.Suit.HEART), new Card(12, Cardable.Suit.CLUB), new Card(11, Cardable.Suit.CLUB), new Card(9, Cardable.Suit.DIAMOND)};
	}
	private Cardable[] getLowestHighCard()
	{
		return new Cardable[]{new Card(7, Cardable.Suit.CLUB), new Card(2, Cardable.Suit.HEART), new Card(3, Cardable.Suit.CLUB), new Card(4, Cardable.Suit.CLUB), new Card(5, Cardable.Suit.DIAMOND)};
	}
	private Cardable[] getHighestTwoPair()
	{
		return new Cardable[]{new Card(14, Cardable.Suit.CLUB), new Card(14, Cardable.Suit.HEART), new Card(13, Cardable.Suit.CLUB), new Card(13, Cardable.Suit.CLUB), new Card(12, Cardable.Suit.DIAMOND)};
	}
	private Cardable[] getLowestPair()
	{
		return new Cardable[]{new Card(2, Cardable.Suit.CLUB), new Card(2, Cardable.Suit.HEART), new Card(3, Cardable.Suit.CLUB), new Card(4, Cardable.Suit.CLUB), new Card(5, Cardable.Suit.DIAMOND)};
	}
	private Cardable[] getHighestPair()
	{
		return new Cardable[]{new Card(14, Cardable.Suit.CLUB), new Card(14, Cardable.Suit.HEART), new Card(13, Cardable.Suit.CLUB), new Card(12, Cardable.Suit.CLUB), new Card(11, Cardable.Suit.DIAMOND)};
	}
	private Cardable[] getLowestTwoPair()
	{
		return new Cardable[]{new Card(2, Cardable.Suit.CLUB), new Card(2, Cardable.Suit.HEART), new Card(3, Cardable.Suit.CLUB), new Card(3, Cardable.Suit.CLUB), new Card(4, Cardable.Suit.DIAMOND)};
	}
	private Cardable[] getLowestThreeOfKind()
	{
		return new Cardable[]{new Card(2, Cardable.Suit.CLUB), new Card(2, Cardable.Suit.HEART), new Card(2, Cardable.Suit.SPADE), new Card(3, Cardable.Suit.CLUB), new Card(4, Cardable.Suit.HEART)};
	}
	private Cardable[] getHighestThreeOfKind()
	{
		return new Cardable[]{new Card(14, Cardable.Suit.CLUB), new Card(14, Cardable.Suit.HEART), new Card(14, Cardable.Suit.SPADE), new Card(13, Cardable.Suit.CLUB), new Card(12, Cardable.Suit.HEART)};
	}
	private Cardable[] getHighestStraight()
	{
		return new Cardable[]{new Card(14, Cardable.Suit.CLUB), new Card(13, Cardable.Suit.HEART), new Card(12, Cardable.Suit.SPADE), new Card(11, Cardable.Suit.CLUB), new Card(10, Cardable.Suit.HEART)};
	}
	private Cardable[] getLowestStraight()
	{
		return new Cardable[]{new Card(14, Cardable.Suit.CLUB), new Card(2, Cardable.Suit.HEART), new Card(3, Cardable.Suit.SPADE), new Card(4, Cardable.Suit.CLUB), new Card(5, Cardable.Suit.HEART)};
	}
	private Cardable[] getHighestFlush()
	{
		return new Cardable[]{new Card(14, Cardable.Suit.CLUB), new Card(13, Cardable.Suit.CLUB), new Card(12, Cardable.Suit.CLUB), new Card(11, Cardable.Suit.CLUB), new Card(9, Cardable.Suit.CLUB)};
	}
	private Cardable[] getLowestFlush()
	{
		return new Cardable[]{new Card(2, Cardable.Suit.CLUB), new Card(3, Cardable.Suit.CLUB), new Card(4, Cardable.Suit.CLUB), new Card(5, Cardable.Suit.CLUB), new Card(7, Cardable.Suit.CLUB)};
	}
	private Cardable[] getHighestFullHouse()
	{
		return new Cardable[]{new Card(14, Cardable.Suit.CLUB), new Card(14, Cardable.Suit.SPADE), new Card(14, Cardable.Suit.DIAMOND), new Card(13, Cardable.Suit.DIAMOND), new Card(13, Cardable.Suit.CLUB)};
	}
	private Cardable[] getLowestFullHouse()
	{
		return new Cardable[]{new Card(2, Cardable.Suit.CLUB), new Card(2, Cardable.Suit.SPADE), new Card(2, Cardable.Suit.DIAMOND), new Card(3, Cardable.Suit.DIAMOND), new Card(3, Cardable.Suit.CLUB)};
	}
	private Cardable[] getLowestFourKind()
	{
		return new Cardable[]{new Card(2, Cardable.Suit.CLUB), new Card(2, Cardable.Suit.SPADE), new Card(2, Cardable.Suit.DIAMOND), new Card(2, Cardable.Suit.HEART), new Card(3, Cardable.Suit.CLUB)};
	}
	private Cardable[] getHighestFourKind()
	{
		return new Cardable[]{new Card(14, Cardable.Suit.CLUB), new Card(14, Cardable.Suit.SPADE), new Card(14, Cardable.Suit.DIAMOND), new Card(14, Cardable.Suit.HEART), new Card(13, Cardable.Suit.CLUB)};
	}
	private Cardable[] getLowestStraightFlush()
	{
		return new Cardable[]{new Card(14, Cardable.Suit.SPADE), new Card(2, Cardable.Suit.SPADE), new Card(3, Cardable.Suit.SPADE), new Card(4, Cardable.Suit.SPADE), new Card(5, Cardable.Suit.SPADE)};
	}
	private Cardable[] getHighestStraightFlush()
	{
		return new Cardable[]{new Card(14, Cardable.Suit.SPADE), new Card(13, Cardable.Suit.SPADE), new Card(12, Cardable.Suit.SPADE), new Card(11, Cardable.Suit.SPADE), new Card(10, Cardable.Suit.SPADE)};
	}


}
