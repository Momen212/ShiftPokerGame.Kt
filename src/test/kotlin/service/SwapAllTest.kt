package service

import entity.*
import kotlin.test.*

/**
 * This class verify that swapping all face-up player cards with all
 * cards from the center is performed correctly.
 * @property rootService used to initialize and control the game state.
 * @property momen testplayer 1.
 * @property amro testplayer 2.
 *
 */
class SwapAllTest {
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
        val rounds = 3
        rootService.gameService.startGame(playerList,rounds)
    }


    /**
     * Verifies that calling swapAll,
     * Exchanges all open cards of the current player with the middle cards
     * Adds the correct log entry
     * Increments the player's action counter
     */
    @Test
    fun `test swap all the cards if returns correct cards`() {

        val game = rootService.mainGame?: throw IllegalStateException("No game currently running.")
        val player = game.playerList[game.currentPlayerIndex]

        val copyListOfMiddleCards = game.middleCards.toMutableList()
        val copyListOfPlayerCards = player.openCards.toMutableList()
        val oldActionCount = player.actionCount
        assert(("${player.name} hat SwapALL ausgeführt") !in game.logList)
        rootService.playerService.swapAll()

        assertEquals(copyListOfMiddleCards[0], player.openCards[0])
        assertEquals(copyListOfMiddleCards[1], player.openCards[1])
        assertEquals(copyListOfMiddleCards[2], player.openCards[2])
        assertEquals(copyListOfPlayerCards[0], game.middleCards[0])
        assertEquals(copyListOfPlayerCards[1], game.middleCards[1])
        assertEquals(copyListOfPlayerCards[2], game.middleCards[2])
        assert(("${player.name} hat SwapALL ausgeführt") in game.logList)
        assert(player.actionCount == oldActionCount +1)
    }

}