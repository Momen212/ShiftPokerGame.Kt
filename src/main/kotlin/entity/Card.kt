package entity

/**
 * class card uses cardvalue and cardsuit for create a card.
 * @property value provides a card value for a card.
 * @property suit provides a card suit for a card.
*/
data class Card(val value: CardValue, val suit: CardSuit)