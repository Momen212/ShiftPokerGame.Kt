package service

import entity.*
import kotlin.test.*

/**
 * Test class to verify that all methods of the Refreshable interface
 * are correctly called by GameService and PlayerActionService.
 * @property rootService used to initialize and control the game state.
 * @property momen testplayer 1.
 * @property amro testplayer 2.
 * @property playerList list of players in the test game.
 * @property rounds number of rounds in the game.
 * @property refreshAfterStartGameCalled indicates whether refreshAfterStartGame() was called.
 * @property refreshAfterFinishGameCalled indicates whether refreshAfterFinishGame() was called.
 * @property refreshAfterNextTurnCalled indicates whether refreshAfterNextTurn() was called.
 * @property refreshAfterSwapCalled indicates whether refreshAfterSwap() was called.
 * @property refreshAfterSwapAllCalled indicates whether refreshAfterSwapAll() was called.
 * @property refreshAfterPushCardsCalled indicates whether refreshAfterPushCards() was called.
 */
class RefreshableTest : Refreshable {

    var refreshAfterStartGameCalled: Boolean = false
    var refreshAfterFinishGameCalled: Boolean = false
    var refreshAfterNextTurnCalled: Boolean = false
    var refreshAfterSwapCalled: Boolean = false
    var refreshAfterSwapAllCalled: Boolean = false
    var refreshAfterPushCardsCalled: Boolean = false
    private lateinit var rootService: RootService

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
        rootService.gameService.addRefreshable(this)
        rootService.playerService.addRefreshable(this)
        rootService.gameService.startGame(playerList, rounds)
    }

    /**
     * Resets all refresh flags to false.
     */
    fun reset(){
        refreshAfterStartGameCalled = false
        refreshAfterFinishGameCalled = false
        refreshAfterNextTurnCalled = false
        refreshAfterSwapCalled = false
        refreshAfterSwapAllCalled = false
        refreshAfterPushCardsCalled = false
    }

    /**
     * override function Called after a new game has been started to refresh the UI.
     */
    override fun refreshAfterStartGame() {
        refreshAfterStartGameCalled = true
    }


    /**
     * override function Called after the game has finished to refresh the UI.
     * @param winnerList List of players who won the game.
     */
    override fun refreshAfterFinishGame( winnerList: List<Player>) {
        refreshAfterFinishGameCalled = true
    }


    /**
     * override function Called after the turn has given to the next player to refresh the UI.
     */
    override fun refreshAfterNextTurn() {
        refreshAfterNextTurnCalled = true
    }


    /**
     * override function Called after a swap action for one card from the middle has done to refresh the UI.
     */
    override fun refreshAfterSwap() {
        refreshAfterSwapCalled = true
    }


    /**
     * override function Called after a swap action for all the middle cards has done to refresh the UI.
     */
    override fun refreshAfterSwapAll() {
        refreshAfterSwapAllCalled = true
    }


    /**
     * override function Called after a push action for the middle cards has done to refresh the UI.
     */
    override fun refreshAfterPushCards() {
        refreshAfterPushCardsCalled = true
    }


    /**
     * Tests whether all refresh methods are called correctly
     * after their corresponding service actions.
     */
    @Test
    fun `test if refreshables called`() {

        assertTrue(refreshAfterStartGameCalled)

        rootService.playerService.swap(0, 2)
        assertTrue(refreshAfterSwapCalled)
        reset()

        rootService.playerService.swapAll()
        assertTrue(refreshAfterSwapAllCalled)
        reset()

        rootService.playerService.pushCards(true)
        assertTrue(refreshAfterPushCardsCalled)
        reset()

        rootService.gameService.finishGame()
        assertTrue(refreshAfterFinishGameCalled)


    }

}