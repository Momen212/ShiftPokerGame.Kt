package service

import entity.*
import kotlin.test.*

/**
 * This class verify that pushing the cards in the middle to rhe left
 * or to the right with is performed correctly.
 * @property rootService used to initialize and control the game state.
 * @property momen testplayer 1.
 * @property amro testplayer 2.
 */
class PushCardsTest {
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
     * Verifies that pushing all middle cards to the left,
     * Moves the left-most middle card to the discard stack
     * Shifts remaining middle cards left
     * Draws a new card from the draw stack to the right-most middle card
     * Adds the correct log entry
     * Increments the player's action counter
     */
    @Test
    fun `test push all the middle cards to the left`() {

        val game = rootService.mainGame?: throw IllegalStateException("No game currently running.")
        val player = game.playerList[game.currentPlayerIndex]
        val left = true

        val firstCardDrawStack = game.drawStack[0]
        val firstCardMiddleCards = game.middleCards[0]
        val secondCardMiddleCards = game.middleCards[1]
        val lastCardMiddleCards = game.middleCards[2]
        val oldDrawStackSize = game.drawStack.size
        val oldDiscardStackSize = game.discardStack.size
        val oldActionCount = player.actionCount

        assert(("${game.playerList[game.currentPlayerIndex]
            .name} hat pushLeft ausgeführt") !in game.logList)

        rootService.playerService.pushCards(left)

        assertEquals(firstCardMiddleCards, game.discardStack[game.discardStack.size-1])
        assertEquals(secondCardMiddleCards, game.middleCards[0])
        assertEquals(lastCardMiddleCards, game.middleCards[1])
        assertEquals(firstCardDrawStack, game.middleCards[2])
        assertEquals(game.drawStack.size, oldDrawStackSize-1)
        assertEquals(game.discardStack.size, oldDiscardStackSize+1)
        assert(("${game.playerList[game.currentPlayerIndex]
            .name} hat pushLeft ausgeführt") in game.logList)
        assert(player.actionCount == oldActionCount +1)
    }


    /**
     * Verifies that pushing all middle cards to the right,
     * Moves the right-most middle card to the discard stack
     * Shifts remaining middle cards right
     * Draws a new card from the draw stack to the left-most middle card
     * Adds the correct log entry
     * Increments the player's action counter
     */
    @Test
    fun `test push all the middle cards to the right`() {

        val game = rootService.mainGame?: throw IllegalStateException("No game currently running.")
        val player = game.playerList[game.currentPlayerIndex]
        val left = false

        val firstCardDrawStack = game.drawStack[0]
        val firstCardMiddleCards = game.middleCards[0]
        val secondCardMiddleCards = game.middleCards[1]
        val lastCardMiddleCards = game.middleCards[2]
        val oldDrawStackSize = game.drawStack.size
        val oldDiscardStackSize = game.discardStack.size
        val oldActionCount = player.actionCount

        assert(("${game.playerList[game.currentPlayerIndex]
            .name} hat pushRight ausgeführt") !in game.logList)

        rootService.playerService.pushCards(left)

        assertEquals(lastCardMiddleCards, game.discardStack[game.discardStack.size-1])
        assertEquals(secondCardMiddleCards, game.middleCards[2])
        assertEquals(firstCardMiddleCards, game.middleCards[1])
        assertEquals(firstCardDrawStack, game.middleCards[0])
        assertEquals(game.drawStack.size, oldDrawStackSize-1)
        assertEquals(game.discardStack.size, oldDiscardStackSize+1)
        assert(("${game.playerList[game.currentPlayerIndex]
            .name} hat pushRight ausgeführt") in game.logList)
        assert(player.actionCount == oldActionCount +1)
    }


    /**
     * Verifies that pushing all middle cards to the left while the draw stack is empty,
     * Moves the left-most middle card to the discard stack
     * Shifts remaining middle cards left
     * Draws a new card from the draw stack to the right-most middle card
     * Adds the correct log entry
     * Increments the player's action counter
     */
    @Test
    fun `test push all the middle cards to the left when drawstack is empty`() {

        val game = rootService.mainGame?: throw IllegalStateException("No game currently running.")
        val player = game.playerList[game.currentPlayerIndex]
        val left = true

        val secondCardMiddleCards = game.middleCards[1]
        val lastCardMiddleCards = game.middleCards[2]
        val oldActionCount = player.actionCount
        game.discardStack.addAll(game.drawStack)
        game.drawStack.clear()

        assert(("${game.playerList[game.currentPlayerIndex]
            .name} hat pushLeft ausgeführt") !in game.logList)

        rootService.playerService.pushCards(left)

        assertEquals(secondCardMiddleCards, game.middleCards[0])
        assertEquals(lastCardMiddleCards, game.middleCards[1])
        // 52-13(playerhand1+playerhand2+middlecards) = 39 in drawstack
        assertEquals(game.drawStack.size, 39)
        //discardstack is empty
        assertEquals(game.discardStack.size, 0)
        assert(("${game.playerList[game.currentPlayerIndex]
            .name} hat pushLeft ausgeführt") in game.logList)
        assert(player.actionCount == oldActionCount +1)
    }

    /**
     * Verifies that pushing all middle cards to the left while the draw stack is empty,
     * Moves the left-most middle card to the discard stack
     * Shifts remaining middle cards left
     * Draws a new card from the draw stack to the right-most middle card
     * Adds the correct log entry
     * Increments the player's action counter
     */
    @Test
    fun `test push all the middle cards to the right when drawstack is empty`() {

        val game = rootService.mainGame?: throw IllegalStateException("No game currently running.")
        val player = game.playerList[game.currentPlayerIndex]
        val left = false

        val secondCardMiddleCards = game.middleCards[1]
        val firstCardMiddleCards = game.middleCards[0]
        val oldActionCount = player.actionCount
        game.discardStack.addAll(game.drawStack)
        game.drawStack.clear()


        assert(("${game.playerList[game.currentPlayerIndex]
            .name} hat pushRight ausgeführt") !in game.logList)

        rootService.playerService.pushCards(left)

        assertEquals(secondCardMiddleCards, game.middleCards[2])
        assertEquals(firstCardMiddleCards, game.middleCards[1])
        // 52-13(playerhand1+playerhand2+middlecards) = 39 in drawstack
        assertEquals(game.drawStack.size, 39)
        //discardstack is empty
        assertEquals(game.discardStack.size, 0)
        assert(("${game.playerList[game.currentPlayerIndex]
            .name} hat pushRight ausgeführt") in game.logList)
        assert(player.actionCount == oldActionCount +1)
    }

}