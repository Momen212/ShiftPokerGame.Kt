package service

import entity.*
import kotlin.test.*

/**
 * This class verify that swapping nothing (pass) is performed correctly.
 * @property rootService used to initialize and control the game state.
 * @property momen testplayer 1.
 * @property amro testplayer 2.
 *
 */
class SwapNoneTest {
    private lateinit var rootService: RootService
    val momen = Player("Momen",
        mutableListOf(),
        mutableListOf())
    val amro = Player("Amro",
        mutableListOf(),
        mutableListOf())

    /**
     * Initialize service to set up the test environment. This function is executed before every test.
     */
    @BeforeTest
    fun setUp() {
        rootService = RootService()
        val playerList = mutableListOf(momen, amro)
        val rounds = 2
        rootService.gameService.startGame(playerList,rounds)
    }


    /**
     * Verifies that when a player nothing swaped (pass),
     * Adds the correct log entry
     * Increments the player's action counter
     */
    @Test
    fun `test swap nothing (pass)`() {

        val game = rootService.mainGame?: throw IllegalStateException("No game currently running.")
        val player = game.playerList[game.currentPlayerIndex]

        val oldActionCount = player.actionCount
        assert(("${game.playerList[game.currentPlayerIndex]
            .name} hat swapNone ausgeführt") !in game.logList)

        rootService.playerService.swapNone()

        assert(("${game.playerList[game.currentPlayerIndex]
            .name} hat swapNone ausgeführt") in game.logList)
        assert(player.actionCount == oldActionCount +1)
    }

}