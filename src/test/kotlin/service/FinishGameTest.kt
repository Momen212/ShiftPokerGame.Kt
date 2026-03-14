package service

import entity.*
import kotlin.test.*


/**
 * This test class verifies that the current game is finished correctly.
 * @property rootService used to initialize and control the game state.
 * @property momen testplayer 1.
 * @property amro testplayer 2.
 * @property playerList list of players in the test game.
 * @property rounds number of rounds in the game.
 */
class FinishGameTest {
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
    }

/**
 * Verifies that calling finishGame ends the current game.
 */
    @Test
    fun `test if finishGame ends the game correctly`() {
        gameService.startGame(playerList, rounds)
        val game = rootService.mainGame?: throw IllegalStateException("No game currently running.")

        assertNotNull(game)

        gameService.finishGame()

        assertNull(rootService.mainGame)
    }
}