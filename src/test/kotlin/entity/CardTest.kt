package entity

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

/**
 * Tests for class Card.
 * This test class verifies that:
 * A Card object is correctly created with a given value and suit.
 * @property testCard Variable of type card to test if value and suit are correctly given.
 */
class CardTest {

    val testCard: Card = Card(CardValue.TEN, CardSuit.HEARTS)
    /**
     * Tests card creation and equality.
     * Verifies that:
     * A card with the same value and suit is equal.
     * A card with different value and suit is not equal.
     */
    @Test
    fun createCardTest() {
        assertEquals(testCard,Card(CardValue.TEN, CardSuit.HEARTS))
        assertNotEquals(testCard,Card(CardValue.KING, CardSuit.CLUBS))
    }
}