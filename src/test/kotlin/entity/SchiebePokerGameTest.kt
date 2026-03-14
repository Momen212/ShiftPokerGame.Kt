package entity

import org.junit.jupiter.api.Assertions.*
import kotlin.collections.mutableListOf
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * Tests for class SchiebePokerGame .
 */
class SchiebePokerGameTest {

    /**
     * This function tests the correct initialization of a SchiebePokerGame.
     * @param testMiddleCards to test the three cards in the middle on Assert.
     * @param testDiscardStack to test Stack containing discarded or played cards on Assert.
     * @param testDrawStack to test Stack from which players draw cards on Assert.
     * @param testPlayerList to test the Length of the list of all participating players on Assert.
     * @param testGame to test the initialization of the SchiebePokerGame
     * @return none.
     */
    @Test
    fun testGameInitialization() {
        val testMiddleCards = mutableListOf<Card>()
        val testDiscardStack = mutableListOf<Card>()
        val testDrawStack = mutableListOf<Card>()
        val testPlayerList = mutableListOf<Player>(
            Player("Sam",
                mutableListOf<Card>(),
                mutableListOf<Card>()),
            Player("Tim",
                mutableListOf<Card>(),
                mutableListOf<Card>()))

        val testGame = SchiebePokerGame(
            currentPlayerIndex = 0,
            roundCount = 1,
            middleCards = testMiddleCards,
            discardStack = testDiscardStack,
            drawStack = testDrawStack,
            playerList = testPlayerList
        )

        assertEquals(0, testGame.currentPlayerIndex)
        assertEquals(1, testGame.roundCount)
        assertTrue(testGame.middleCards.isEmpty())
        assertTrue(testGame.drawStack.isEmpty())
        assertEquals(2, testGame.playerList.size)
        assertTrue(testGame.logList.isEmpty())
    }
}