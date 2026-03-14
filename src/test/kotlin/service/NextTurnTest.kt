package service

import entity.*
import kotlin.test.*


/**
 * This test class verifies that the actions are counted correctly.
 * @property rootService used to initialize and control the game state.
 * @property momen testplayer 1.
 * @property amro testplayer 2.
 * @property playerList list of players in the test game.
 * @property rounds number of rounds in the game.
 */
class NextTurnTest {
    private lateinit var rootService: RootService
    private lateinit var gameService: GameService
    val momen = Player("Momen",
        mutableListOf(),
        mutableListOf())
    val amro = Player("Amro",
        mutableListOf(),
        mutableListOf())
    var playerList = mutableListOf(momen, amro)
    var rounds = 3


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
     * Verifies that nextTurn advances the current player index correctly after two actions
     * then sets current player index to zero
     */
    @Test
    fun `test if nextTurn increments current player index when actions counts are 2`() {
        val game = rootService.mainGame?: throw IllegalStateException("No game currently running.")
        val player = game.playerList[game.currentPlayerIndex]
        player.actionCount=2
        gameService.nextTurn()

        assertEquals(1, game.currentPlayerIndex)
        assertEquals(0, player.actionCount)


    }


/**
 * Verifies that the game automatically ends when the round count reaches zero.
 */
    @Test
    fun `test if finish game called when rounds equal zero`() {
        val game = rootService.mainGame?: throw IllegalStateException("No game currently running.")

        game.roundCount=0

        gameService.nextTurn()

        assertNull(rootService.mainGame)

    }

}