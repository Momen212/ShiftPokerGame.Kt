package service

import entity.SchiebePokerGame

/**
 * The root service class is responsible for managing services and the entity layer reference.
 * This class acts as a central hub for every other service within the application.
 * @property playerService service responsible for managing player actions.
 * @property gameService service responsible for managing game logic.
 * @property mainGame the current SchiebePokerGame instance, or NULL if no game is running.
 */
class RootService{

    val playerService = PlayerActionService(this)
    val gameService = GameService(this)
    var mainGame : SchiebePokerGame? = null


    /**
     * Adds the provided [newRefreshable] to all services connected to this root service
     * @param newRefreshable The [Refreshable] to be added
     */
    fun addRefreshable(newRefreshable: Refreshable) {
        gameService.addRefreshable(newRefreshable)
        playerService.addRefreshable(newRefreshable)
    }


}