package entity

import org.junit.jupiter.api.Assertions.assertTrue
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * Tests for class Player .
 * @property testOpenCards this list is used to test whether cards can be added correctly
 * and whether the list is actually changeable.
 * @property testHiddenCards this remains empty and is only used to initialize the player.
 * @property testPlayer a test player named "Momen" is created,
 * who starts with an empty list of open and hidden cards.
 */
class PlayerTest {
    var testOpenCards = mutableListOf<Card>()
    var testHiddenCards = mutableListOf<Card>()
    var testPlayer = Player("Momen", testOpenCards, testHiddenCards)

    /**
     * testOpenCardsMutable: Tests if open cards can be added correctly and if the list of open cards is mutable.
     */
    @Test
    fun testOpenCardsMutable() {
        val testNewCard = Card(CardValue.THREE, CardSuit.HEARTS)

        testPlayer.openCards+=testNewCard
        assertTrue(testPlayer.openCards.contains(testNewCard))
        assertEquals(1, testPlayer.openCards.size)
    }

    /**
     * testActionCount:  Tests player's `actionCount` equality.
     */
    @Test
    fun testActionCount() {

        assertEquals(testPlayer.actionCount, 0)
    }
}