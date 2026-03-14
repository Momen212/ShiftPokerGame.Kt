package entity

/**
 * class Player represents a player in the game.
 * @property name the player's name.
 * @property openCards the player's face-up cards. This list is changeable,
 * as the face-up cards can change during the game.
 * @property hiddenCards the player's face-down cards.
 * These cards are not visible to other players and remain unchanged.
 * @property playerHandValue contains the calculated hand values of the player.
 * @property actionCount indicates how many actions did the player. By default, this value is 0.
 */
data class Player (val name: String,
              var openCards: MutableList<Card>,
              var hiddenCards: MutableList<Card>  ) {
    var playerHandValue: HandValue = HandValue.HIGH_CARD
    var actionCount: Int = 0
}