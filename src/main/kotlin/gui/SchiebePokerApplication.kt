package gui

import entity.*
import service.*
import tools.aqua.bgw.core.BoardGameApplication

/**
 * Main application class for the SchiebePoker game.
 * Initializes all scenes and connects them with the RootService.
 * @property rootService containing game and player services.
 */
class SchiebePokerApplication(private val rootService: RootService = RootService())
    : BoardGameApplication("SchiebePoker Game"),Refreshable {
    private val menuScene: MenuScene = MenuScene(rootService)
    private val nextPlayerScene: NextPlayerScene = NextPlayerScene(rootService)
    private val gameScene: GameScene = GameScene(rootService)
    private val endScene: EndScene = EndScene(rootService)

/**
 * Initializes the application.
 * All scenes are registered as refreshables in the RootService
 * Initially, the game scene and menu scene are shown
 */
    init {
        rootService.addRefreshable(gameScene)
        rootService.addRefreshable(menuScene)
        rootService.addRefreshable(nextPlayerScene)
        rootService.addRefreshable(endScene)
        rootService.addRefreshable(this)

        this.showGameScene(gameScene)
        this.showMenuScene(menuScene)
    }

    /**
     * Called after a new game has been started.
     * Hides the menu scene with a short animation delay.
     */
    override fun refreshAfterStartGame() {
        hideMenuScene(500)
    }

    /**
     * Called after a player confirmed his turn.
     * Hides the menu scene with a short animation delay.
     */
    override fun refreshAfterConfirmation() {
        hideMenuScene(500)
    }

    override fun refreshAfterNextTurn(){
        showMenuScene(nextPlayerScene)

    }


    /**
     * Called after the game has finished.
     * Displays the end scene.
     * @param winnerList List of players who won the game.
     */
    override fun refreshAfterFinishGame(winnerList: List<Player>) {
        showMenuScene(endScene)
    }

    /**
     * Called after the game has been restarted.
     * Displays the main menu scene again.
     */
    override fun refreshAfterRestart() {
        showMenuScene(menuScene)
    }
}
