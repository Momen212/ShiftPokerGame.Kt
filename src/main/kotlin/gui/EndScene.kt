package gui

import entity.*
import service.Refreshable
import service.RootService
import tools.aqua.bgw.components.layoutviews.Pane
import tools.aqua.bgw.components.uicomponents.UIComponent
import tools.aqua.bgw.core.Color
import tools.aqua.bgw.core.MenuScene
import tools.aqua.bgw.visual.ColorVisual
import tools.aqua.bgw.components.uicomponents.Button
import tools.aqua.bgw.components.uicomponents.Label
import tools.aqua.bgw.components.uicomponents.TextArea
import tools.aqua.bgw.core.Alignment
import tools.aqua.bgw.util.Font
import kotlin.system.exitProcess

/**
 * Scene shown at the end of the game.
 * Displays final results and allows the user to return to the menu.
 */
class EndScene(private val rootService: RootService)  : MenuScene(width = 1920, height = 1080,
    background = ColorVisual(Color.WHITE)),Refreshable {


    /** Reference to the current game. */
    val game = rootService.mainGame

    /** Pane containing all menu components. */
    private val contentPane = Pane<UIComponent>(
        width = 800,
        height = 900,
        posX = 1920 / 2 - 700 / 2,
        posY = 1080 / 2 - 900 / 2,
        visual = ColorVisual(Color.BLACK)
    )

    /** Label to display the title of the scene. */
    private val titleLabel = Label(
        text = "GEWINNER LISTE",
        width = 700,
        height = 100,
        posX = 0,
        posY = 30,
        alignment = Alignment.CENTER,
        font = Font(50, Color.WHITE, "JetBrains Mono ExtraBold")
    )

    /** Label to display the name of the winner. */
    private val winnerLabel = TextArea(
        text = "",
        width = 700,
        height = 600,
        posX = 50,
        posY = 150,
        font = Font(45, Color.BLACK, "JetBrains Mono ExtraBold"),
        visual = ColorVisual(Color.GREEN)
    )

    /**  Button to restart the game.*/
    private val restartButton = Button(
        text = "Neustart",
        width = 200,
        height = 60,
        posX = 100,
        posY = 800,
        font = Font(22, Color.WHITE, "JetBrains Mono ExtraBold"),
        visual = ColorVisual(Color(0x49585D))
    ).apply {
        // When the button is clicked, restart the game
        onMouseClicked = {
            // Access the onAllRefreshables method of the game service to call the refreshAfterGameRestart method
            rootService.gameService.onAllRefreshables { refreshAfterRestart() }
        }

    }

    /**  Button to end the Scene.*/
    private val exitButton = Button(
        text = "Beenden",
        width = 200,
        height = 60,
        posX = 400,
        posY = 800,
        font = Font(22, Color.WHITE, "JetBrains Mono ExtraBold"),
        visual = ColorVisual(Color(0x49585D))
    ).apply {
        // When the button is clicked, restart the game
        onMouseClicked = {
            exitProcess(0)
        }

    }


    /** Initialize the scene by setting the background color and adding all components to the content pane. */
    init {
        background = ColorVisual(Color(12, 32, 39, 240))
        contentPane.addAll(titleLabel, winnerLabel, restartButton, exitButton)
        addComponents(contentPane)
    }

    /**
     * The refreshAfterGameEnd method is called by the service layer after a game has ended.
     * It sets the list of the winners.
     * @param winnerList List of players who won the game.
     */
    override fun refreshAfterFinishGame(winnerList: List<Player>) {
        var rang = 0
        winnerLabel.apply {
            text = "Rangliste:\n\n"
            for (winner in winnerList) {
                val currentindex= winnerList.indexOf(winner)
                if (currentindex>0 && winner.playerHandValue == winnerList[currentindex-1].playerHandValue ) {

                    text += "${rang}. ${winner.name} mit ${winner.playerHandValue}\n"

                }
                else{
                    rang++
                text += "${rang}. ${winner.name} mit ${winner.playerHandValue}\n"

                }
            }

        }
    }
}
