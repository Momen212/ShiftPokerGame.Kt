package service

import entity.*
import kotlin.test.*


/**
 * This test class verifies that the game correctly identifies the winner(s)
 * based on the players' hands.
 * @property rootService used to initialize and control the game state.
 * @property gameService responsible for handling core game logic.
 * @property momen testplayer 1.
 * @property amro testplayer 2.
 * @property playerList list of players in the test game.
 * @property rounds number of rounds in the game.
 */
class FindWinnerTest {
    private lateinit var rootService: RootService
    private lateinit var gameService: GameService
    val momen = Player("Momen",
        mutableListOf(),
        mutableListOf())
    val amro = Player("Amro",
        mutableListOf(),
        mutableListOf())
    var playerList = mutableListOf(momen, amro)
    val rounds = 3

    /**
     * Initialize service to set up the test environment. This function is executed before every test.
     */
    @BeforeTest
    fun setUp() {
        rootService = RootService()
        gameService = GameService(rootService)
        gameService.startGame(playerList, rounds)
    }

    /**
     * Verifies that `findWinner` correctly identifies the winning player(s) based on hand
     * values royal flush is the best .
     */
    @Test
    fun `test if royal flush is the best`() {
        val game = rootService.mainGame?: throw IllegalStateException("No game currently running.")

        game.playerList[0].hiddenCards.clear()
        game.playerList[0].openCards.clear()
        game.playerList[1].hiddenCards.clear()
        game.playerList[1].openCards.clear()
        game.playerList[0].hiddenCards.addAll(listOf(
            Card(CardValue.THREE, CardSuit.HEARTS),
            Card(CardValue.THREE, CardSuit.SPADES)
        ))
        game.playerList[0].openCards.addAll(listOf(
            Card(CardValue.THREE, CardSuit.CLUBS),
            Card(CardValue.KING, CardSuit.HEARTS),
            Card(CardValue.KING, CardSuit.DIAMONDS)
        ))
        game.playerList[1].hiddenCards.addAll(listOf(
            Card(CardValue.ACE, CardSuit.HEARTS),
            Card(CardValue.JACK
                , CardSuit.HEARTS)
        ))
        game.playerList[1].openCards.addAll(listOf(
            Card(CardValue.KING, CardSuit.HEARTS),
            Card(CardValue.QUEEN, CardSuit.HEARTS),
            Card(CardValue.TEN, CardSuit.HEARTS)
        ))

        val winners = gameService.findWinners()

        assertTrue(game.playerList[1] in winners)
        assertTrue(game.playerList[0] != winners.first())
    }
/**
 * Verifies that `findWinner` correctly identifies the winning player(s) based on hand values
 * straight flush + full house.
 */
    @Test
    fun `test if straight flush is better than full house`() {
        val game = rootService.mainGame?: throw IllegalStateException("No game currently running.")

        game.playerList[0].hiddenCards.clear()
        game.playerList[0].openCards.clear()
        game.playerList[1].hiddenCards.clear()
        game.playerList[1].openCards.clear()
        game.playerList[0].hiddenCards.addAll(listOf(
            Card(CardValue.THREE, CardSuit.HEARTS),
            Card(CardValue.THREE, CardSuit.SPADES)
        ))
        game.playerList[0].openCards.addAll(listOf(
            Card(CardValue.THREE, CardSuit.CLUBS),
            Card(CardValue.KING, CardSuit.HEARTS),
            Card(CardValue.KING, CardSuit.DIAMONDS)
        ))
        game.playerList[1].hiddenCards.addAll(listOf(
            Card(CardValue.THREE, CardSuit.HEARTS),
            Card(CardValue.SEVEN
                , CardSuit.HEARTS)
        ))
        game.playerList[1].openCards.addAll(listOf(
            Card(CardValue.FIVE, CardSuit.HEARTS),
            Card(CardValue.SIX, CardSuit.HEARTS),
            Card(CardValue.FOUR, CardSuit.HEARTS)
        ))

        val winners = gameService.findWinners()

        assertTrue(game.playerList[1] in winners)
        assertTrue(game.playerList[0] != winners.first())
    }

    /**
     * Verifies that `findWinner` correctly identifies the winning player(s) based on hand values
     * full house + straight.
     */
    @Test
    fun `test if full house is better than straight`() {
        val game = rootService.mainGame?: throw IllegalStateException("No game currently running.")

        game.playerList[0].hiddenCards.clear()
        game.playerList[0].openCards.clear()
        game.playerList[1].hiddenCards.clear()
        game.playerList[1].openCards.clear()
        game.playerList[0].hiddenCards.addAll(listOf(
            Card(CardValue.THREE, CardSuit.HEARTS),
            Card(CardValue.THREE, CardSuit.SPADES)
        ))
        game.playerList[0].openCards.addAll(listOf(
            Card(CardValue.THREE, CardSuit.CLUBS),
            Card(CardValue.KING, CardSuit.HEARTS),
            Card(CardValue.KING, CardSuit.DIAMONDS)
        ))
        game.playerList[1].hiddenCards.addAll(listOf(
            Card(CardValue.THREE, CardSuit.HEARTS),
            Card(CardValue.SEVEN
                , CardSuit.HEARTS)
        ))
        game.playerList[1].openCards.addAll(listOf(
            Card(CardValue.FIVE, CardSuit.DIAMONDS),
            Card(CardValue.SIX, CardSuit.CLUBS),
            Card(CardValue.FOUR, CardSuit.HEARTS)
        ))

        val winners = gameService.findWinners()

        assertTrue(game.playerList[0] in winners)
        assertTrue(game.playerList[1] != winners.first())
    }

    /**
     * Verifies that `findWinner` correctly identifies the winning player(s) based on hand values
     * four of a kind + full house.
     */
    @Test
    fun `test if four of a kind is better than full house is `() {
        val game = rootService.mainGame?: throw IllegalStateException("No game currently running.")

        game.playerList[0].hiddenCards.clear()
        game.playerList[0].openCards.clear()
        game.playerList[1].hiddenCards.clear()
        game.playerList[1].openCards.clear()
        game.playerList[0].hiddenCards.addAll(listOf(
            Card(CardValue.THREE, CardSuit.HEARTS),
            Card(CardValue.THREE, CardSuit.SPADES)
        ))
        game.playerList[0].openCards.addAll(listOf(
            Card(CardValue.THREE, CardSuit.CLUBS),
            Card(CardValue.KING, CardSuit.HEARTS),
            Card(CardValue.KING, CardSuit.DIAMONDS)
        ))
        game.playerList[1].hiddenCards.addAll(listOf(
            Card(CardValue.THREE, CardSuit.HEARTS),
            Card(CardValue.THREE, CardSuit.SPADES)
        ))
        game.playerList[1].openCards.addAll(listOf(
            Card(CardValue.THREE, CardSuit.CLUBS),
            Card(CardValue.THREE, CardSuit.HEARTS),
            Card(CardValue.KING, CardSuit.DIAMONDS)
        ))

        val winners = gameService.findWinners()

        assertTrue(game.playerList[1] in winners)
        assertTrue(game.playerList[0] != winners.first())
    }


    /**
     * Verifies that `findWinner` correctly identifies the winning player(s) based on hand values
     * flush + straight.
     */
    @Test
    fun `test if flush is better than straight`() {
        val game = rootService.mainGame?: throw IllegalStateException("No game currently running.")

        game.playerList[0].hiddenCards.clear()
        game.playerList[0].openCards.clear()
        game.playerList[1].hiddenCards.clear()
        game.playerList[1].openCards.clear()
        game.playerList[0].hiddenCards.addAll(listOf(
            Card(CardValue.THREE, CardSuit.HEARTS),
            Card(CardValue.TEN, CardSuit.HEARTS)
        ))
        game.playerList[0].openCards.addAll(listOf(
            Card(CardValue.THREE, CardSuit.HEARTS),
            Card(CardValue.JACK, CardSuit.HEARTS),
            Card(CardValue.KING, CardSuit.HEARTS)
        ))
        game.playerList[1].hiddenCards.addAll(listOf(
            Card(CardValue.THREE, CardSuit.HEARTS),
            Card(CardValue.SEVEN
                , CardSuit.HEARTS)
        ))
        game.playerList[1].openCards.addAll(listOf(
            Card(CardValue.FIVE, CardSuit.DIAMONDS),
            Card(CardValue.SIX, CardSuit.CLUBS),
            Card(CardValue.FOUR, CardSuit.HEARTS)
        ))

        val winners = gameService.findWinners()

        assertTrue(game.playerList[0] in winners)
        assertTrue(game.playerList[1] != winners.first())
    }

    /**
     * Verifies that `findWinner` correctly identifies the winning player(s) based on hand values
     * straight + three of a kind.
     */
    @Test
    fun `test if straight is better than three of a kind`() {
        val game = rootService.mainGame?: throw IllegalStateException("No game currently running.")

        game.playerList[0].hiddenCards.clear()
        game.playerList[0].openCards.clear()
        game.playerList[1].hiddenCards.clear()
        game.playerList[1].openCards.clear()
        game.playerList[0].hiddenCards.addAll(listOf(
            Card(CardValue.THREE, CardSuit.HEARTS),
            Card(CardValue.TEN, CardSuit.DIAMONDS)
        ))
        game.playerList[0].openCards.addAll(listOf(
            Card(CardValue.THREE, CardSuit.HEARTS),
            Card(CardValue.THREE, CardSuit.CLUBS),
            Card(CardValue.KING, CardSuit.HEARTS)
        ))
        game.playerList[1].hiddenCards.addAll(listOf(
            Card(CardValue.THREE, CardSuit.HEARTS),
            Card(CardValue.SEVEN
                , CardSuit.HEARTS)
        ))
        game.playerList[1].openCards.addAll(listOf(
            Card(CardValue.FIVE, CardSuit.DIAMONDS),
            Card(CardValue.SIX, CardSuit.CLUBS),
            Card(CardValue.FOUR, CardSuit.HEARTS)
        ))

        val winners = gameService.findWinners()

        assertTrue(game.playerList[1] in winners)
        assertTrue(game.playerList[0] != winners.first())
    }


    /**
     * Verifies that `findWinner` correctly identifies the winning player(s) based on hand values
     * three of a kind + two pair.
     */
    @Test
    fun `test if three of a kind is better than two pair`() {
        val game = rootService.mainGame?: throw IllegalStateException("No game currently running.")

        game.playerList[0].hiddenCards.clear()
        game.playerList[0].openCards.clear()
        game.playerList[1].hiddenCards.clear()
        game.playerList[1].openCards.clear()
        game.playerList[0].hiddenCards.addAll(listOf(
            Card(CardValue.THREE, CardSuit.HEARTS),
            Card(CardValue.TEN, CardSuit.DIAMONDS)
        ))
        game.playerList[0].openCards.addAll(listOf(
            Card(CardValue.THREE, CardSuit.HEARTS),
            Card(CardValue.THREE, CardSuit.CLUBS),
            Card(CardValue.KING, CardSuit.HEARTS)
        ))
        game.playerList[1].hiddenCards.addAll(listOf(
            Card(CardValue.SEVEN, CardSuit.HEARTS),
            Card(CardValue.SEVEN
                , CardSuit.HEARTS)
        ))
        game.playerList[1].openCards.addAll(listOf(
            Card(CardValue.SIX, CardSuit.DIAMONDS),
            Card(CardValue.SIX, CardSuit.CLUBS),
            Card(CardValue.FOUR, CardSuit.HEARTS)
        ))

        val winners = gameService.findWinners()

        assertTrue(game.playerList[0] in winners)
        assertTrue(game.playerList[1] != winners.first())
    }

    /**
     * Verifies that `findWinner` correctly identifies the winning player(s) based on hand values
     * two pair + one pair.
     */
    @Test
    fun `test if two pair is better than one pair`() {
        val game = rootService.mainGame?: throw IllegalStateException("No game currently running.")

        game.playerList[0].hiddenCards.clear()
        game.playerList[0].openCards.clear()
        game.playerList[1].hiddenCards.clear()
        game.playerList[1].openCards.clear()
        game.playerList[0].hiddenCards.addAll(listOf(
            Card(CardValue.FOUR, CardSuit.HEARTS),
            Card(CardValue.TEN, CardSuit.DIAMONDS)
        ))
        game.playerList[0].openCards.addAll(listOf(
            Card(CardValue.THREE, CardSuit.HEARTS),
            Card(CardValue.THREE, CardSuit.CLUBS),
            Card(CardValue.KING, CardSuit.HEARTS)
        ))
        game.playerList[1].hiddenCards.addAll(listOf(
            Card(CardValue.SEVEN, CardSuit.HEARTS),
            Card(CardValue.SEVEN
                , CardSuit.HEARTS)
        ))
        game.playerList[1].openCards.addAll(listOf(
            Card(CardValue.SIX, CardSuit.DIAMONDS),
            Card(CardValue.SIX, CardSuit.CLUBS),
            Card(CardValue.FOUR, CardSuit.HEARTS)
        ))

        val winners = gameService.findWinners()

        assertTrue(game.playerList[1] in winners)
        assertTrue(game.playerList[0] != winners.first())
    }

    /**
     * Verifies that `findWinner` correctly identifies the winning player(s) based on hand values
     * one pair + high card.
     */
    @Test
    fun `test if one pair is better than high card`() {
        val game = rootService.mainGame?: throw IllegalStateException("No game currently running.")

        game.playerList[0].hiddenCards.clear()
        game.playerList[0].openCards.clear()
        game.playerList[1].hiddenCards.clear()
        game.playerList[1].openCards.clear()
        game.playerList[0].hiddenCards.addAll(listOf(
            Card(CardValue.FOUR, CardSuit.HEARTS),
            Card(CardValue.TEN, CardSuit.DIAMONDS)
        ))
        game.playerList[0].openCards.addAll(listOf(
            Card(CardValue.THREE, CardSuit.HEARTS),
            Card(CardValue.THREE, CardSuit.CLUBS),
            Card(CardValue.KING, CardSuit.HEARTS)
        ))
        game.playerList[1].hiddenCards.addAll(listOf(
            Card(CardValue.ACE, CardSuit.HEARTS),
            Card(CardValue.SEVEN
                , CardSuit.HEARTS)
        ))
        game.playerList[1].openCards.addAll(listOf(
            Card(CardValue.TEN, CardSuit.DIAMONDS),
            Card(CardValue.SIX, CardSuit.CLUBS),
            Card(CardValue.FOUR, CardSuit.HEARTS)
        ))

        val winners = gameService.findWinners()

        assertTrue(game.playerList[0] in winners)
        assertTrue(game.playerList[1] != winners.first())
    }

}