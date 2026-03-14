package service

import entity.*
import kotlin.test.*

/**
 * This class verify that swapping a face-up player card with a
 * card from the center is performed correctly.
 * @property rootService used to initialize and control the game state.
 * @property momen testplayer 1.
 * @property amro testplayer 2.
 *
 */
class SwapTest {
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
        val rounds = 4
        rootService.gameService.startGame(playerList,rounds)
    }


    /**
     * Verifies that swapping player selection card with middle selection card,
     * Exchanges the correct cards
     * Adds the correct log entry
     * Increments the player's action counter
     */
    @Test
    fun `test swap two cards if returns correct cards`() {

        val game = rootService.mainGame?: throw IllegalStateException("No game currently running.")
        val player = game.playerList[game.currentPlayerIndex]

        val oldPlayerCard = player.openCards[0]
        val oldMiddleCard = game.middleCards[2]
        val oldActionCount = player.actionCount

        assert(("${player.name} hat Swap zwischen $oldMiddleCard und $oldPlayerCard ausgeführt") !in game.logList)

        rootService.playerService.swap(0, 2)

        assertEquals(oldMiddleCard, player.openCards[0])
        assertEquals(oldPlayerCard, game.middleCards[2])
        assertNotEquals(oldMiddleCard, game.middleCards[2])
        assertNotEquals(oldPlayerCard, player.openCards[0])
        assert(("${player.name} hat Swap zwischen $oldMiddleCard und $oldPlayerCard ausgeführt") in game.logList)
        assert(player.actionCount == oldActionCount +1)
    }

    /**
     * Verifies that Swap throws an IllegalArgumentException when the provided indices are outside the valid range
     */
    @Test
    fun `test swap two cards if returns Illegalargumentexception when the parameter not in the valid range`() {



        assertFailsWith<IllegalArgumentException> { rootService.playerService.swap(4, 2)}
        assertFailsWith<IllegalArgumentException> { rootService.playerService.swap(4, 5)}
        assertFailsWith<IllegalArgumentException> { rootService.playerService.swap(2, 5)}

    }

}