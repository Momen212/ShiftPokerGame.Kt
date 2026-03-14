package entity

/**
 * class SchiebePokerGame builds the fundamentals of the game.
 * @property currentPlayerIndex the index of the currently active player.
 * @property roundCount indicates which round the game is in.
 * @property middleCards provides three cards in the middle.
 * These cards can change during the course of the game.
 * @property discardStack  the discardStack for played or discarded cards.
 * This stack grows as the game progresses.
 * @property drawStack provides a drawStack from which players can draw cards.
 * @property playerList provides a list of current players.
 * @property logList provides a log for the Game.
 */
class SchiebePokerGame(var currentPlayerIndex: Int,
                       var roundCount: Int,
                       var middleCards: MutableList<Card>,
                       var discardStack: MutableList<Card>,
                       var drawStack: MutableList<Card>,
                       val playerList: MutableList<Player>) {
    var logList: MutableList<String> = mutableListOf()
}