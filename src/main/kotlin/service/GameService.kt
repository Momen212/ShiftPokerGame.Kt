package service

import entity.Card
import entity.CardSuit
import entity.CardValue
import entity.HandValue
import entity.Player
import entity.SchiebePokerGame
import kotlin.random.Random

/**
 * Service class that manages the game logic for SchiebePoker.
 * @property rootService reference to the RootService, used to access the current game state.
 */
class GameService(private val rootService: RootService): AbstractRefreshingService()   {

    /**
     * Starts a new game with a list of players and number of rounds.
     * @param playerList list of players, who play in the game (2-4 players).
     * @param rounds number of rounds to play (2-7).
     * @throws IllegalArgumentException if the number of rounds or players is outside the valid range.
     */
    fun startGame(playerList: MutableList<Player>, rounds: Int) {
        require(rounds in 2..7) { "The number of rounds must be between 2 and 7" }
        require(playerList.size in 2..4) {"The number of players must be between 2 and 4" }

        val randomIndex = Random.nextInt(playerList.size)
        val rotated = playerList.drop(randomIndex) + playerList.take(randomIndex)
        playerList.clear()
        playerList.addAll(rotated)

        val cardsDeck = mutableListOf<Card>()
        for (suit in CardSuit.entries) {
            for (value in CardValue.entries) {
                cardsDeck.add(Card(value, suit))
            }
        }
        cardsDeck.shuffle()

        val players = mutableListOf<Player>()

        for (player in playerList) {
            val hiddenCards = mutableListOf(cardsDeck.removeAt(0),
                cardsDeck.removeAt(0))
            val openCards = mutableListOf(cardsDeck.removeAt(0),
                cardsDeck.removeAt(0), cardsDeck.removeAt(0))

            val playerEdited = Player(player.name, openCards, hiddenCards)
            players.add(playerEdited)
        }
        val middleCards = mutableListOf(cardsDeck.removeAt(0),
            cardsDeck.removeAt(0), cardsDeck.removeAt(0))

        val game = SchiebePokerGame(
            currentPlayerIndex = 0,
            roundCount = rounds,
            middleCards = middleCards,
            discardStack = mutableListOf(),
            drawStack = cardsDeck,
            playerList = players
        )
        rootService.mainGame = game
        game.logList.add("Spiel gestartet.")
        onAllRefreshables { refreshAfterStartGame() }
    }
    /**
     * Ends the current game and updates refreshable component with the winners.
     */
     fun finishGame() {
        val winners = findWinners()
        onAllRefreshables { refreshAfterFinishGame(winners) }
        rootService.mainGame = null
    }
    /**
     * Evaluates a player's hand and returns its hand value.
     * @param player the player whose hand is being evaluated.
     * @return HandValue The category of the hand .
     * @throws IllegalArgumentException if the hand does not contain exactly 5 cards.
     */
    private fun evaluationOfHand(player: Player): HandValue {
        var hand = player.hiddenCards + player.openCards
        if (hand.size != 5) { throw IllegalArgumentException("Hand must contain exactly 5 cards.") }

        val groupsByValues = hand.groupBy { it.value }
        val groupsSizes = groupsByValues.mapValues { it.value.size }

        // True, if four of a kind exist
        val fourOfAKind = groupsSizes.any { it.value == 4 }
        // True, if three of a kind exist
        val threeOfAKind = groupsSizes.any { it.value == 3 }
        //Number of groups that are pairs
        val pairs = groupsSizes.count { it.value == 2 }

        hand = hand.sortedBy { it.value.ordinal}

        val flush = hand.map { it.suit }.distinct().size == 1

        var straight = true
        for (i in 0 until hand.size - 1) {
            val current = hand[i].value.ordinal
            val next = hand[i + 1].value.ordinal

            if (next - current != 1) {
                //special case: A, 2, 3, 4, 5
                if (!(hand[i].value == CardValue.FIVE && hand[i + 1].value == CardValue.ACE)) {
                    straight = false
                    break
                }
            }
        }
        return when {
            flush && straight && hand[0].value == CardValue.ACE -> HandValue.ROYAL_FLUSH
            flush && straight  -> HandValue.STRAIGHT_FLUSH
            fourOfAKind ->HandValue.FOUR_OF_A_KIND
            threeOfAKind && pairs == 1 -> HandValue.FULL_HOUSE
            flush -> HandValue.FLUSH
            straight  -> HandValue.STRAIGHT
            threeOfAKind -> HandValue.THREE_OF_A_KIND
            pairs == 2 -> HandValue.TWO_PAIR
            pairs == 1 -> HandValue.ONE_PAIR
            else -> HandValue.HIGH_CARD
        }

    }

    /**
     * It finds the winner(s) of the current game based on hand values.
     * @return list of players who won the game. Sorted from strongest to weakest hand.
     */
    fun findWinners(): List<Player> {

        val game = rootService.mainGame ?: return mutableListOf()
        var strongValue = game.playerList[0].playerHandValue.ordinal

        for (player in game.playerList) {
            player.playerHandValue = evaluationOfHand(player)
            val handValue = player.playerHandValue.ordinal
            if (handValue< strongValue) {
                strongValue = handValue
            }
        }
        val sortedPlayers = game.playerList.sortedBy { it.playerHandValue.ordinal }

        val winners = mutableListOf<Player>()
        winners.addAll(sortedPlayers)

        val allHandvaluesDifferent = game.playerList
            .map { it.playerHandValue }
            .distinct()
            .size == game.playerList.size

        if (allHandvaluesDifferent && winners.size == 3) {
            winners.removeLast()
        }

        return winners
    }
    /**
     * Reshuffles the draw stack from the discard stack if the draw stack is empty.
     * @throws IllegalStateException if no game is currently running or
     * Draw stack is not empty or Discard stack is empty.
     */
    fun reshuffleDrawStack() {
        val game = rootService.mainGame ?: throw IllegalStateException("No game currently running.")
        require(game.drawStack.isEmpty()) { "Draw stack must be empty." }
        require(game.discardStack.isNotEmpty()) { "Discard Stack must not be empty." }
        if (game.drawStack.isEmpty()) {
            game.logList.add("Nachziehstapel ist leer. Ablagestapel wird gemischt.")
            game.drawStack.addAll(game.discardStack)
            game.discardStack.clear()
            game.drawStack.shuffle()
        }
    }
    /**
    * Moves the game to the next turn, updating player state and round count if needed.
    * @throws IllegalStateException if no game is currently running
    */
    fun nextTurn() {
        val game = rootService.mainGame ?: return
        val player = game.playerList[game.currentPlayerIndex]

        if (player.actionCount>=2) {
            player.actionCount = 0
            game.currentPlayerIndex = (game.currentPlayerIndex + 1) % game.playerList.size
            game.logList.add("${game.playerList[game.currentPlayerIndex].name} ist jetzt dran.")
            if (game.currentPlayerIndex == 0) {
                game.roundCount--
            }
            onAllRefreshables { refreshAfterNextTurn() }
        }

        if (game.roundCount <= 0) {
            finishGame()
        }
    }
}
