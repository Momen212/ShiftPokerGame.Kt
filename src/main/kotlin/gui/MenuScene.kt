package gui

import entity.*
import service.*
import tools.aqua.bgw.components.layoutviews.Pane
import tools.aqua.bgw.components.uicomponents.Button
import tools.aqua.bgw.components.uicomponents.ComboBox
import tools.aqua.bgw.components.uicomponents.Label
import tools.aqua.bgw.components.uicomponents.TextField
import tools.aqua.bgw.components.uicomponents.UIComponent
import tools.aqua.bgw.core.Alignment
import tools.aqua.bgw.core.Color
import tools.aqua.bgw.core.MenuScene
import tools.aqua.bgw.util.Font
import tools.aqua.bgw.visual.ColorVisual



/**
 * Menu scene for the Schiebe Poker Game.
 * Provides a UI for,
 * Entering player names (2–4 players)
 * Selecting number of rounds
 * Starting the game
 * Adding/Removing players
 * @property rootService containing game and player services.
 */
class MenuScene(private val rootService: RootService) : MenuScene(width = 1920, height = 1080,
    background = ColorVisual(Color.WHITE)),Refreshable {

    /** Pane containing all menu components. */
    private val contentPane = Pane<UIComponent>(
        width = 700,
        height = 900,
        posX = 1920 / 2 - 700 / 2,
        posY = 1080 / 2 - 900 / 2,
        visual = ColorVisual(Color.BLACK)
    )

    /** Title label for the game. */
    private val titleLabel = Label(
        text = "Schiebe Poker Game",
        width = 700,
        height = 100,
        posX = 0,
        posY = 30,
        alignment = Alignment.CENTER,
        font = Font(45, Color.WHITE, "JetBrains Mono ExtraBold")
    )

    /** Label for player input field. */
    private val textFieldFirstLabel = Label(
        text = "Player-1",
        width = 600,
        height = 75,
        posX = 20,
        posY = 140,
        alignment = Alignment.TOP_LEFT,
        font = Font(30, Color.WHITE, "JetBrains Mono ExtraBold")
    )

    /** Label for player input field. */
    private val textFieldSecondLabel = Label(
        text = "Player-2",
        width = 600,
        height = 75,
        posX = 20,
        posY = 240,
        alignment = Alignment.TOP_LEFT,
        font = Font(30, Color.WHITE, "JetBrains Mono ExtraBold")
    )

    /** TextField for entering player name. */
    private val playerFirstInput = TextField(
        prompt = "Name",
        width = 400,
        height = 75,
        posX = 180,
        posY = 130,
        font = Font(26, Color.WHITE, "JetBrains Mono ExtraBold"),
        visual = ColorVisual(Color(0x49585D)),
    )

    /** TextField for entering player name. */
    private val playerSecondInput = TextField(
        prompt = "Name",
        width = 400,
        height = 75,
        posX = 180,
        posY = 230,
        font = Font(26, Color.WHITE, "JetBrains Mono ExtraBold"),
        visual = ColorVisual(Color(0x49585D)),
    )

    /** Button to remove the last player input field. */
    private val removePlayer = Button(
        text = "-",
        width = 75,
        height = 75,
        posX = 40,
        posY = 540,
        font = Font(50, Color.WHITE, "JetBrains Mono ExtraBold"),
        visual = ColorVisual(Color(0x49585D))
    ).apply {
        onMouseClicked = {
            if (playerInputs.size > 2) {
                val lastInput = playerInputs.removeLast()
                val lastLabel = playerLabels.removeLast()

                contentPane.remove(lastInput)
                contentPane.remove(lastLabel)
            }
        }
    }

    /** Button to add a new player input field. */
    private val addPlayer = Button(
        text = "+",
        width = 75,
        height = 75,
        posX = 565,
        posY = 540,
        font = Font(50, Color.WHITE, "JetBrains Mono ExtraBold"),
        visual = ColorVisual(Color(0x49585D))
    ).apply {
        onMouseClicked = {
            if (playerInputs.size < 4) {
                val textFieldLabel = Label(
                    text = "Player-${playerInputs.size + 1}",
                    width = 600,
                    height = 75,
                    posX = 20,
                    posY = 140 + playerInputs.size * 100,
                    alignment = Alignment.TOP_LEFT,
                    font = Font(30, Color.WHITE, "JetBrains Mono ExtraBold")
                )
                val playerDefaultInput = TextField(
                    prompt = "Name",
                    width = 400,
                    height = 75,
                    posX = 180,
                    posY = 130 + playerInputs.size * 100,
                    font = Font(26, Color.WHITE, "JetBrains Mono ExtraBold"),
                    visual = ColorVisual(Color(0x49585D)),
                )

                playerLabels.add(textFieldLabel)
                playerInputs.add(playerDefaultInput)

                contentPane.add(textFieldLabel)
                contentPane.add(playerDefaultInput)

            }
        }
    }

    /** ComboBox for the number of rounds. */
    val roundsBoxInput = ComboBox<Int>(
        posX = 450,
        posY = 650,
        width = 90,
        height = 90,
        visual = ColorVisual(Color(0x49585D)),
        prompt = "",
        font =Font(50, Color.WHITE)
    ).apply {
        items = mutableListOf(2, 3, 4, 5, 6, 7)
    }

    /** Label for the rounds input field. */
    private val roundsLabel = Label(
        text = "Anzahl der Runden:",
        width = 400,
        height = 75,
        posX = 20,
        posY = 660,
        alignment = Alignment.TOP_LEFT,
        font = Font(45, Color.WHITE, "JetBrains Mono ExtraBold")
    )

    /** Lists storing player input fields and their labels. */
    private val playerInputs = mutableListOf(playerFirstInput, playerSecondInput)
    private val playerLabels = mutableListOf(textFieldFirstLabel, textFieldSecondLabel)

    /** Button to start the game using entered player names and rounds. */
    private val startButton = Button(
        text = "START",
        width = 280,
        height = 75,
        posX = 200,
        posY = 790,
        font = Font(22, Color.WHITE, "JetBrains Mono ExtraBold"),
        visual = ColorVisual(Color(0x49585D))
    ).apply {
        // When the button is clicked, the game is started with the entered player names
        onMouseClicked = {
            val playerNames = playerInputs.filter { it.text.isNotBlank() }.map { it.text }
            val playerList = mutableListOf<Player>()
            for (playerName in playerNames) {
                playerList.add(Player(playerName, mutableListOf(), mutableListOf()))
            }
            val rounds: Int = roundsBoxInput.selectedItem ?: 2
            rootService.gameService.startGame(playerList, rounds )
        }
    }


    /**
     * Initializes the scene, and adds all UI components to the content pane.
     */
    init {
        background = ColorVisual(Color(12, 32, 39, 240))
        contentPane.addAll(
            titleLabel, textFieldFirstLabel, textFieldSecondLabel, playerSecondInput,
            playerFirstInput, removePlayer, addPlayer, roundsBoxInput, roundsLabel, startButton
        )
        addComponents(contentPane)
    }

}