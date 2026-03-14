package service

import entity.*
import kotlin.test.*


/**
 * This test class verifies that the draw stack is reshuffled correctly when empty
 * and that the discard stack is moved into the draw stack.
 * @property rootService used to initialize and control the game state.
 * @property momen testplayer 1.
 * @property amro testplayer 2.
 * @property playerList list of players in the test game.
 * @property rounds number of rounds in the game.
 */
class ReshuffleDrawStackTest {
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
     * Verifies that when the draw stack is empty,
     * All cards from the discard stack are moved into the draw stack
     * The discard stack is cleared
     * The correct log entry is added
     */
    @Test
    fun `test if reshuffleDrawStack moves discardstack into drawstack when drawstack is empty`() {
        val game = rootService.mainGame?: throw IllegalStateException("No game currently running.")

        game.discardStack.addAll(game.drawStack)
        game.drawStack.clear()
        assert(("Nachziehstapel ist leer. Ablagestapel wird gemischt.") !in game.logList)

        gameService.reshuffleDrawStack()

        assertTrue(game.discardStack.isEmpty())
        assertTrue(game.drawStack.isNotEmpty())
        assert(("Nachziehstapel ist leer. Ablagestapel wird gemischt.") in game.logList)
    }


}