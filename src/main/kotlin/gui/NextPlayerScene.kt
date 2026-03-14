package gui

import service.*
import tools.aqua.bgw.components.layoutviews.Pane
import tools.aqua.bgw.components.uicomponents.Button
import tools.aqua.bgw.components.uicomponents.Label
import tools.aqua.bgw.components.uicomponents.UIComponent
import tools.aqua.bgw.core.Color
import tools.aqua.bgw.core.MenuScene
import tools.aqua.bgw.util.Font
import tools.aqua.bgw.visual.ColorVisual

/**
 * Scene that is shown between turns to indicate which player is next.
 * @property rootService containing game and player services.
 */
class NextPlayerScene(private val rootService: RootService) : MenuScene(width = 1920, height = 1080,
    background = ColorVisual(Color.WHITE)),Refreshable {

    /** Pane containing all menu components. */
    private val contentPane = Pane<UIComponent>(
        width = 500,
        height = 500,
        posX = 1920 / 2 - 500 / 2,
        posY = 1080 / 2 - 500 / 2,
        visual = ColorVisual(Color.BLACK)
    )


    /** Label that displays which player is currently taking their turn. */
    private val nextTurnLabel = Label(
        width = 400,
        height = 150,
        posX = 5,
        posY = 100,
        font = Font(45, Color.WHITE, "JetBrains Mono ExtraBold")
    )


    /** Button used to confirm and proceed to the next player's turn. */
    private val startButton = Button(
        text = "Weiter",
        width = 200,
        height = 75,
        posX = 150,
        posY = 390,
        font = Font(22, Color.WHITE, "JetBrains Mono ExtraBold"),
        visual = ColorVisual(Color(0x49585D))
    ).apply {
        onMouseClicked = {
            rootService.gameService.onAllRefreshables { refreshAfterConfirmation() }
        }
    }



    /**
     * Initializes the scene, and adds all UI components to the content pane.
     */
    init {
        background = ColorVisual(Color(12, 32, 39, 240))
        contentPane.addAll(nextTurnLabel, startButton)
        addComponents(contentPane)
    }

    /** Updates the label text after the active player has changed. */
    override fun refreshAfterNextTurn(){
        val game = rootService.mainGame ?: return
        nextTurnLabel.text =
            "${game.playerList[game.currentPlayerIndex].name} ist dran"
    }

}