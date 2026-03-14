package entity
/**
 * Enum to distinguish between the 10 possible handvalues in a ShiftPokerGame handvalues:
 * ROYAL_FLUSH, STRAIGHT_FLUSH, FOUR_OF_A_KIND, FULL_HOUSE, FLUSH, STRAIGHT, THREE_OF_A_KIND,
 * TWO_PAIR, ONE_PAIR or HIGH_CARD.
 *
 * The handvalues are ordered according to their most common ordering:
 * HIGH_CARD < ONE_PAIR < ... < STRAIGHT_FLUSH < ROYAL_FLUSH.
 */
enum class HandValue
{
    ROYAL_FLUSH,
    STRAIGHT_FLUSH,
    FOUR_OF_A_KIND,
    FULL_HOUSE,
    FLUSH,
    STRAIGHT,
    THREE_OF_A_KIND,
    TWO_PAIR,
    ONE_PAIR,
    HIGH_CARD,
    ;
}