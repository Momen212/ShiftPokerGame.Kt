package service

import entity.Player

/**
 * This interface provides a mechanism for the service layer classes to communicate
 * (usually to the GUI classes) that certain changes have been made to the entity
 * layer, so that the user interface can be updated accordingly.
 *
 * Default (empty) implementations are provided for all methods, so that implementing
 * GUI classes only need to react to events relevant to them.
 *
 * @see AbstractRefreshingService
 */
interface Refreshable {

    /**
     * Called after a new game has been started to refresh the UI.
     */
    fun refreshAfterStartGame(){}

    /**
     * Called after the game has finished to refresh the UI.
     * @param winnerList List of players who won the game.
     */
    fun refreshAfterFinishGame(winnerList: List<Player>){}

    /**
    * Called after the turn has given to the next player to refresh the UI.
    */
    fun refreshAfterNextTurn(){}

    /**
     * Called after a swap action for one card from the middle has done to refresh the UI.
    */
    fun refreshAfterSwap(){}

     /**
     * Called after a swap action for all the middle cards has done to refresh the UI.
     */
    fun refreshAfterSwapAll(){}


     /**
     * Called after a push action for the middle cards has done to refresh the UI.
     */
    fun refreshAfterPushCards(){}


     /**
     * Called after the game has been restarted.
     */
    fun refreshAfterRestart(){}


    /**
     * Called after the next turn has been confirmed.
     */
    fun refreshAfterConfirmation(){}

}
