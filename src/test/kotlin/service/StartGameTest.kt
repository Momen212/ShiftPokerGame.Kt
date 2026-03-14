package service

import entity.*
import kotlin.test.*


/**
 * This test class verifies that a new game is initialized correctly.
 * @property rootService used to initialize and control the game state.
 * @property momen testplayer 1.
 * @property amro testplayer 2.
 * @property playerList list of players in the test game.
 * @property rounds number of rounds in the game.
 */
class StartGameTest {
    private lateinit var rootService: RootService
    private lateinit var gameService: GameService
    val momen = Player("Momen",
        mutableListOf(),
        mutableListOf())
    val amro = Player("Amro",
        mutableListOf(),
        mutableListOf())
    val playerList = mutableListOf(momen, amro)
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
     * Verifies that startGame checks
     * Round count
     * Player list size
     * Number of middle cards
     * Player open and hidden cards
     * Non-empty draw stack and empty discard stack
     * Correct log entry
     */
    @Test
    fun `test if startGame initializes game correctly`() {
        val game = rootService.mainGame?: throw IllegalStateException("No game currently running.")

        assertEquals(rounds, game.roundCount)
        assertEquals(2, game.playerList.size)
        assertEquals(3, game.middleCards.size)
        assertEquals(3,game.playerList[0].openCards.size)
        assertEquals(3,game.playerList[1].openCards.size)
        assertEquals(2,game.playerList[0].hiddenCards.size)
        assertEquals(2,game.playerList[1].hiddenCards.size)
        assertTrue(game.drawStack.isNotEmpty())
        assertTrue(game.discardStack.isEmpty())
        assert(("Spiel gestartet.") in game.logList.first())
    }



    /**
     * Verifies that startGame throws IllegalArgumentException for invalid inputs.
     */
    @Test
    fun `test if startGame throws exception for invalid rounds or player list size`() {
        val fivePlayers = MutableList(5) { Player("P$it",
            mutableListOf(),
            mutableListOf()) }

        assertFailsWith<IllegalArgumentException> { rootService.gameService.startGame(playerList, 1) }
        assertFailsWith<IllegalArgumentException> { rootService.gameService.startGame(playerList, 8) }
        assertFailsWith<IllegalArgumentException> { rootService.gameService.startGame(mutableListOf(momen),rounds)}
        assertFailsWith<IllegalArgumentException> { rootService.gameService.startGame(fivePlayers, rounds)}
    }
}