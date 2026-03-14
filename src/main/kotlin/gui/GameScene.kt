package gui

import entity.*
import service.*
import tools.aqua.bgw.components.container.LinearLayout
import tools.aqua.bgw.components.gamecomponentviews.CardView
import tools.aqua.bgw.core.BoardGameScene
import tools.aqua.bgw.visual.ImageVisual
import tools.aqua.bgw.components.uicomponents.Button
import tools.aqua.bgw.components.uicomponents.Label
import tools.aqua.bgw.components.uicomponents.TextArea
import tools.aqua.bgw.core.Color
import tools.aqua.bgw.util.Font
import tools.aqua.bgw.visual.ColorVisual


/**
 * Main game scene for the Schiebe Poker Game.
 * Provides the interactive board and UI for playing the game including,
 * Displaying players’ cards (active, top, left, right)
 * Middle cards and draw/discard stacks
 * Action buttons: Swap, Swap-All, Swap-None, Push-Left, Push-Right
 * Logs, rounds counter, and action count for the current player
 * @property rootService containing game and player services.
 */
class GameScene(private val rootService: RootService) :
    BoardGameScene(1201, 850, background = ImageVisual("WandtapeteDunkelBlau.png")), Refreshable{

    /** Reference to the current game. */
    val game = rootService.mainGame
    /** Loader for card images. */
    private val cardImageLoader = CardImageLoader()

    /** Indices of selected cards. */
    private var cardDeckIndex = -1
    private var openCardIndex = -1

    /** Flags for clicked cards in the middle and for the active player. */
    private var clickedMiddleFirstCard = false
    private var clickedMiddleSecondCard = false
    private var clickedMiddleThirdCard = false
    private var clickedActivePlayerFirstCard = false
    private var clickedActivePlayerSecondCard = false
    private var clickedActivePlayerThirdCard = false

    /** Action buttons. */
    private val pushRightButton = Button(
        text = "Push-Right",
        posY = 500,
        posX = 565,
        width = 100,
        height = 25,
    ).apply {
        onMouseClicked = {
            rootService.playerService.pushCards(false)
        }
    }

    private val pushLefttButton = Button(
        text = "Push-Left",
        posY = 500,
        posX = 450,
        width = 100,
        height = 25,
    ).apply {
        onMouseClicked = {
            rootService.playerService.pushCards(true)
        }
    }

    private val swapButton = Button(
        text = "Swap",
        posY = 530,
        posX = 450,
        width = 100,
        height = 25,
    ).apply {
        onMouseClicked = {
            if (cardDeckIndex != -1 && openCardIndex != -1) {
                rootService.playerService.swap(openCardIndex,cardDeckIndex)
            }
            cardDeckIndex = -1
            openCardIndex = -1
        }
    }

    private val swapAllButton = Button(
        text = "Swap-All",
        posY = 530,
        posX = 565,
        width = 100,
        height = 25,
    ).apply {
        onMouseClicked = {
            rootService.playerService.swapAll()
        }
    }

    private val swapNoneButton = Button(
        text = "Swap-None",
        posY = 515,
        posX = 680,
        width = 100,
        height = 25,
    ).apply {
        onMouseClicked = {
            rootService.playerService.swapNone()
        }
    }

    /** Middle cards. */
    private val firstMiddleCard = CardView(
        posX = 410,
        posY = 270,
        front = cardImageLoader.blankImage
    ).apply {
        this.scale(0.5)
        this.showFront()
        this.onMouseClicked = {
            if (clickedMiddleFirstCard) {
                this.scale(0.6)
                clickedMiddleFirstCard = false
                cardDeckIndex = -1
            } else {
                this.scale(0.7)
                cardDeckIndex= 0
                secondMiddleCard.scale(0.6)
                thirdMiddleCard. scale(0.6)
                clickedMiddleFirstCard = true
                clickedMiddleSecondCard = false
                clickedMiddleThirdCard = false
            }
        }
    }

    private val secondMiddleCard: CardView = CardView(
        posX = 500,
        posY = 270,
        front = cardImageLoader.blankImage
    ).apply {
        this.scale(0.5)
        this.showFront()
        onMouseClicked =
            {
                if (clickedMiddleSecondCard) {
                    this.scale(0.6)
                    clickedMiddleSecondCard = false
                    cardDeckIndex = -1
                } else {
                    this.scale(0.7)
                    cardDeckIndex = 1
                    firstMiddleCard. scale(0.6)
                    thirdMiddleCard. scale(0.6)
                    clickedMiddleSecondCard = true
                    clickedMiddleFirstCard = false
                    clickedMiddleThirdCard = false
                }
            }
    }

    private val thirdMiddleCard: CardView = CardView(
        posX = 590,
        posY = 270,
        front = cardImageLoader.blankImage
    ).apply {
        this.showFront()
        this.scale(0.5)
        onMouseClicked =
            {
                if (clickedMiddleThirdCard) {
                    this.scale(0.6)
                    clickedMiddleThirdCard = false
                    cardDeckIndex = -1
                } else {
                    this.scale(0.7)
                    cardDeckIndex = 2
                    firstMiddleCard. scale(0.6)
                    secondMiddleCard. scale(0.6)
                    clickedMiddleThirdCard = true
                    clickedMiddleFirstCard = false
                    clickedMiddleSecondCard = false
                }
            }
    }

    private val logListLabel = TextArea(
        posX = 0,
        posY = 680,
        width = 200,
        height = 200,
        text = "",
        font = Font(size = 15, color = Color.WHITE, fontWeight = Font.FontWeight.BOLD),
        visual = ColorVisual(Color.GRAY)
    )

    private val roundsLabel = Label(
        height = 200,
        width = 300,
        posX = 5,
        posY = 25,
        text = "",
        font = Font(size = 25, color = Color.WHITE, fontWeight = Font.FontWeight.BOLD)
    )

    private val actionCountLabel = Label(
        height = 200,
        width = 300,
        posX = 5,
        posY = -15,
        text = "",
        font = Font(size = 25, color = Color.WHITE, fontWeight = Font.FontWeight.BOLD)
    )

    private val labelForTopPlayer: Label = Label(
        height = 200,
        width = 300,
        posX = 280,
        posY = -20,
        text = "",
        font = Font(size = 25, color = Color.WHITE, fontWeight = Font.FontWeight.BOLD)
    )

    private val topPlayerFirstCard = CardView(
        posX = 630,
        posY = 70,
        front = cardImageLoader.blankImage

    ).apply {
        this.scale(0.50)
        this.showFront()
    }

    private val topPlayerSecondCard = CardView(
        posX = 540,
        posY = 70,
        front = cardImageLoader.blankImage
    ).apply {
        this.showFront()
        this.scale(0.50)
    }


    private val topPlayerThirdCard = CardView(
        posX = 450,
        posY = 70,
        front = cardImageLoader.blankImage
    ).apply {
        this.showFront()
        this.scale(0.50)

    }

    private val labelForLeftPlayer: Label = Label(
        height = 200,
        width = 300,
        posX = 0,
        posY = 120,
        text = "",
        font = Font(size = 25, color = Color.WHITE, fontWeight = Font.FontWeight.BOLD)
    )

    private val leftPlayerFirstCard = CardView(
        posX = 120,
        posY = 185,
        front = cardImageLoader.blankImage
    ).apply {
        this.showFront()
        this.rotation = 90.0
        this.scale(0.50)
    }

    private val leftPlayerSecondCard = CardView(
        posX = 120,
        posY = 275,
        front = cardImageLoader.blankImage
    ).apply {
        this.showFront()
        this.rotation = 90.0
        this.scale(0.50)
    }

    private val leftPlayerThirdCard = CardView(
        posX = 120,
        posY = 365,
        front = cardImageLoader.blankImage
    ).apply {
        this.showFront()
        this.rotation = 90.0
        this.scale(0.50)
    }

    private val labelForRightPlayer: Label = Label(
        height = 200,
        width = 300,
        posX = 920,
        posY = 120,
        text = "",
        font = Font(size = 25, color = Color.WHITE, fontWeight = Font.FontWeight.BOLD)
    )

    private val rightPlayerFirstCard = CardView(
        posX = 950,
        posY = 185,
        front = cardImageLoader.blankImage
    ).apply {
        this.showFront()
        this.rotation = 90.0
        this.scale(0.50)
    }

    private val rightPlayerSecondCard = CardView(
        posX = 950,
        posY = 275,
        front = cardImageLoader.blankImage
    ).apply {
        this.showFront()
        this.rotation = 90.0
        this.scale(0.50)
    }

    private val rightPlayerThirdCard = CardView(
        posX = 950,
        posY = 365,
        front = cardImageLoader.blankImage
    ).apply {
        this.showFront()
        this.rotation = 90.0
        this.scale(0.50)
    }

    private val labelForActivePlayer = Label(
        height = 200,
        width = 300,
        posX = 210,
        posY = 600,
        text = "Aktueller Spieler:",
        font = Font(size = 25, color = Color.WHITE, fontWeight = Font.FontWeight.BOLD)

    )

    private val labelForActivePlayerName = Label(
        height = 200,
        width = 200,
        posX = 250,
        posY = 650,
        text = "",
        font = Font(size = 25, color = Color.WHITE, fontWeight = Font.FontWeight.BOLD)
    )

    /** Active player openCards. */
    private val activePlayerFirstCard = CardView(
        posX = 450,
        posY = 565,
        front = cardImageLoader.backImage
    ).apply {
        this.scale(0.50)
        this.showFront()
        onMouseClicked = {
            if (clickedActivePlayerFirstCard) {
                this.scale(0.50)
                clickedActivePlayerFirstCard = false
                openCardIndex = -1
            } else {
                openCardIndex = 0
                this.scale(0.60)
                activePlayerSecondCard.scale(0.55)
                activePlayerThirdCard. scale(0.55)
                clickedActivePlayerFirstCard = true
                clickedActivePlayerSecondCard = false
                clickedActivePlayerThirdCard = false
            }
        }
    }

    private val activePlayerSecondCard: CardView = CardView(
        posX = 540,
        posY = 565,
        front = cardImageLoader.blankImage
    ).apply {
        this.scale(0.50)
        this.showFront()
        onMouseClicked = {
            if (clickedActivePlayerSecondCard) {
                this.scale(0.50)
                clickedActivePlayerSecondCard = false
                openCardIndex = -1
            } else {
                openCardIndex = 1
                this.scale(0.60)
                activePlayerFirstCard.scale(0.50)
                activePlayerThirdCard.scale(0.50)
                clickedActivePlayerSecondCard = true
                clickedActivePlayerFirstCard = false
                clickedActivePlayerThirdCard = false
            }
        }
    }

    private val activePlayerThirdCard: CardView = CardView(
        posX = 630,
        posY = 565,
        front = cardImageLoader.blankImage
    ).apply {
        this.scale(0.50)
        this.showFront()
        onMouseClicked = {
            if (clickedActivePlayerThirdCard) {
                this.scale(0.50)
                clickedActivePlayerThirdCard = false
                openCardIndex = -1
            } else {
                openCardIndex = 2
                this.scale(0.60)
                activePlayerFirstCard.scale(0.50)
                activePlayerSecondCard. scale(0.50)
                clickedActivePlayerThirdCard = true
                clickedActivePlayerFirstCard = false
                clickedActivePlayerSecondCard = false
            }
        }
    }
    /** Active player hiddenCards. */
    private val hiddenActivePlayerFirstCard = CardView(
        posX = 490,
        posY = 695,
        front = cardImageLoader.backImage
    ).apply {
        this.scale(0.50)
        this.showBack()
        onMousePressed = {
            this.showFront()
        }
        onMouseReleased = {
            this.showBack()
        }
    }


    private val hiddenActivePlayerSecondCard = CardView(
        posX = 570,
        posY = 695,
        front = cardImageLoader.backImage
    ).apply {
        this.scale(0.50)
        this.showBack()
        onMousePressed = {
            this.showFront()
        }
        onMouseReleased = {
            this.showBack()
        }
    }

    private val drawStack = CardView(
        height = 200,
        width = 130,
        posX = 700,
        posY = 270,
        front = cardImageLoader.backImage
    ).apply {
        this.scale(0.5)
        this.showBack()
    }

    /** LinearLayouts for arranging players cards. */
    private val linearLayoutForTopPlayer = LinearLayout<CardView>(
        posX = 500,
        posY = -50,
        spacing = 0,
        width = 200,
        height = 200
    )

    private val linearLayoutForLeftPlayer = LinearLayout<CardView>(
        posX = -80,
        posY = 250,
        spacing = 0,
        width = 200,
        height = 250,
    ).apply {
        this.rotation = 90.0
    }

    private val linearLayoutForRightPlayer = LinearLayout<CardView>(
        posX = 1030,
        posY = 250,
        spacing = 0,
        width = 200,
        height = 250
    ).apply {
        this.rotation = 90.0
    }


    private val dicardStack = CardView(
        height = 200,
        width = 130,
        posX = 300,
        posY = 270,
        front = cardImageLoader.blankImage
    ).apply {
        this.scale(0.5)
        this.showFront()
    }

    /** Initializes the scene and adds all components. */
    init {

        linearLayoutForRightPlayer.addAll(
            CardView(front = cardImageLoader.backImage).apply { scale(0.45) },
            CardView(front = cardImageLoader.backImage).apply { scale(0.45) }
        )
        linearLayoutForLeftPlayer.addAll(
            CardView(front = cardImageLoader.backImage).apply { scale(0.45) },
            CardView(front = cardImageLoader.backImage).apply { scale(0.45) }
        )
        linearLayoutForTopPlayer.addAll(
            CardView(front = cardImageLoader.backImage).apply { scale(0.45) },
            CardView(front = cardImageLoader.backImage).apply { scale(0.45) }
        )

        addComponents(
            roundsLabel,
            actionCountLabel,
            logListLabel,
            pushLefttButton, pushRightButton,
            swapButton, swapAllButton, swapNoneButton,
            labelForActivePlayer,
            topPlayerFirstCard, topPlayerSecondCard, topPlayerThirdCard,
            activePlayerFirstCard, activePlayerSecondCard, activePlayerThirdCard,
            hiddenActivePlayerFirstCard, hiddenActivePlayerSecondCard,
            linearLayoutForTopPlayer,
            firstMiddleCard, secondMiddleCard, thirdMiddleCard,
            drawStack, dicardStack
        )
    }

    /**
     * Updates the remaining rounds, the number of actions and the log list.
     * Throws an exception if no game is currently running.
     */
    private fun refreshBoard() {
        val game = rootService.mainGame
        checkNotNull(game) { "No game is currently running" }


        roundsLabel.text = "Übrige Runden: ${game.roundCount}"


        actionCountLabel.text = "Gespielten Züge: ${game.playerList[game.currentPlayerIndex].actionCount}"

        logListLabel.apply {
            text = "Log: "
            for (log in game.logList.asReversed()) {
                text += "$log\n\n"
            }
        }}

    /**
     * Updates a card in a given CardView.
     * @param cardView the CardView component that should display the card
     * @param card the card whose should be shown
     */
    private fun refreshCard(cardView: CardView, card: Card) {
        cardView.apply {
            this.scale(0.5)
            frontVisual = cardImageLoader.frontImageFor(card.suit, card.value)
            this.showFront()}
    }

    /**
     * Refreshes the three cards in the middle of the table.
     * Throws an exception if no game is currently running.
     */
    private fun refreshMiddleCards() {
        val game = rootService.mainGame
        checkNotNull(game) { "No game is currently running" }

        refreshCard(firstMiddleCard,game.middleCards[0])
        refreshCard(secondMiddleCard,game.middleCards[1])
        refreshCard(thirdMiddleCard,game.middleCards[2])

    }

    /**
     * Refreshes the cards of the currently active player and resets all card selection states.
     * Throws an exception if no game is currently running.
     */
    private fun refreshActivePlayerCards() {
        val game = rootService.mainGame
        checkNotNull(game) { "No game is currently running" }
        val player = game.playerList[game.currentPlayerIndex]

        clickedMiddleFirstCard = false
        clickedMiddleSecondCard = false
        clickedMiddleThirdCard = false
        clickedActivePlayerFirstCard = false
        clickedActivePlayerSecondCard = false
        clickedActivePlayerThirdCard = false

        refreshCard(activePlayerFirstCard,player.openCards[0])
        refreshCard(activePlayerSecondCard,player.openCards[1])
        refreshCard(activePlayerThirdCard,player.openCards[2])

        hiddenActivePlayerFirstCard.frontVisual =
            cardImageLoader.frontImageFor(
                game.playerList[game.currentPlayerIndex].hiddenCards[0].suit,
                game.playerList[game.currentPlayerIndex].hiddenCards[0].value
            )

        hiddenActivePlayerSecondCard.frontVisual =
            cardImageLoader.frontImageFor(
                game.playerList[game.currentPlayerIndex].hiddenCards[1].suit,
                game.playerList[game.currentPlayerIndex].hiddenCards[1].value
            )
    }

    /**
     * Depending on the number of players (2–4), the cards and names
     * of the surrounding players are displayed at the correct
     * positions (top, left, right).
     * Throws an exception if no game is currently running.
     */
    private fun refreshPlayersArrangement(){

        val game = rootService.mainGame
        checkNotNull(game) { "No game is currently running" }

        when (game.playerList.size) {
            2 -> {
                refreshCard(topPlayerFirstCard,
                    game.playerList[(game.currentPlayerIndex + 1) % game.playerList.size].openCards[0])

                refreshCard(topPlayerSecondCard,
                    game.playerList[(game.currentPlayerIndex + 1) % game.playerList.size].openCards[1])

                refreshCard(topPlayerThirdCard,
                    game.playerList[(game.currentPlayerIndex + 1) % game.playerList.size].openCards[2])



                labelForTopPlayer.text = game.playerList[(game.currentPlayerIndex + 1) % game.playerList.size].name

            }

            3 -> {
                refreshCard(topPlayerFirstCard,
                    game.playerList[(game.currentPlayerIndex + 2) % game.playerList.size].openCards[0])

                refreshCard(topPlayerSecondCard,
                    game.playerList[(game.currentPlayerIndex + 2) % game.playerList.size].openCards[1])

                refreshCard(topPlayerThirdCard,
                    game.playerList[(game.currentPlayerIndex + 2) % game.playerList.size].openCards[2])



                refreshCard(leftPlayerFirstCard,
                    game.playerList[(game.currentPlayerIndex + 1) % game.playerList.size].openCards[0])

                refreshCard(leftPlayerSecondCard,
                    game.playerList[(game.currentPlayerIndex + 1) % game.playerList.size].openCards[1])

                refreshCard(leftPlayerThirdCard,
                    game.playerList[(game.currentPlayerIndex + 1) % game.playerList.size].openCards[2])



                labelForTopPlayer.text = game.playerList[(game.currentPlayerIndex + 2) % game.playerList.size].name

                labelForLeftPlayer.text = game.playerList[(game.currentPlayerIndex + 1) % game.playerList.size].name

            }

            4 -> {
                refreshCard(topPlayerFirstCard,
                    game.playerList[(game.currentPlayerIndex + 2) % game.playerList.size].openCards[0])

                refreshCard(topPlayerSecondCard,
                    game.playerList[(game.currentPlayerIndex + 2) % game.playerList.size].openCards[1])

                refreshCard(topPlayerThirdCard,
                    game.playerList[(game.currentPlayerIndex + 2) % game.playerList.size].openCards[2])



                refreshCard(leftPlayerFirstCard,
                    game.playerList[(game.currentPlayerIndex + 1) % game.playerList.size].openCards[0])

                refreshCard(leftPlayerSecondCard,
                    game.playerList[(game.currentPlayerIndex + 1) % game.playerList.size].openCards[1])

                refreshCard(leftPlayerThirdCard,
                    game.playerList[(game.currentPlayerIndex + 1) % game.playerList.size].openCards[2])



                refreshCard(rightPlayerFirstCard,
                    game.playerList[(game.currentPlayerIndex + 3) % game.playerList.size].openCards[2])

                refreshCard(rightPlayerSecondCard,
                    game.playerList[(game.currentPlayerIndex + 3) % game.playerList.size].openCards[1])

                refreshCard(rightPlayerThirdCard,
                    game.playerList[(game.currentPlayerIndex + 3) % game.playerList.size].openCards[0])



                labelForTopPlayer.text = game.playerList[(game.currentPlayerIndex + 2) % game.playerList.size].name

                labelForLeftPlayer.text = game.playerList[(game.currentPlayerIndex + 1) % game.playerList.size].name

                labelForRightPlayer.text = game.playerList[(game.currentPlayerIndex + 3) % game.playerList.size].name

            }
        }


    }

    /**
     * Called after a new game has been started to refresh the UI.
     */
    override fun refreshAfterStartGame() {
        val game = rootService.mainGame
        checkNotNull(game) { "No game is running" }
        labelForActivePlayerName.text = game.playerList[game.currentPlayerIndex].name
        refreshBoard()
        refreshMiddleCards()
        refreshActivePlayerCards()
        refreshPlayersArrangement()

        when (game.playerList.size) {
            3 -> {
                removeComponents(
                    leftPlayerFirstCard, leftPlayerSecondCard, leftPlayerThirdCard, linearLayoutForLeftPlayer,
                    rightPlayerFirstCard, rightPlayerSecondCard, rightPlayerThirdCard, linearLayoutForRightPlayer,
                    labelForActivePlayer, labelForLeftPlayer, labelForRightPlayer, labelForTopPlayer,
                    labelForActivePlayerName
                )
                addComponents(
                    leftPlayerFirstCard, leftPlayerSecondCard, leftPlayerThirdCard, linearLayoutForLeftPlayer,
                    labelForActivePlayer, labelForLeftPlayer, labelForTopPlayer, labelForActivePlayerName
                )
            }

            4 -> {

                removeComponents(
                    leftPlayerFirstCard, leftPlayerSecondCard, leftPlayerThirdCard, linearLayoutForLeftPlayer,
                    rightPlayerFirstCard, rightPlayerSecondCard, rightPlayerThirdCard, linearLayoutForRightPlayer,
                    labelForActivePlayer, labelForLeftPlayer, labelForRightPlayer, labelForTopPlayer,
                    labelForActivePlayerName
                )
                addComponents(
                    rightPlayerFirstCard, rightPlayerSecondCard, rightPlayerThirdCard, linearLayoutForRightPlayer,
                    leftPlayerFirstCard, leftPlayerSecondCard, leftPlayerThirdCard, linearLayoutForLeftPlayer,
                    labelForActivePlayer, labelForLeftPlayer, labelForRightPlayer, labelForTopPlayer,
                    labelForActivePlayerName
                )
            }

            else -> {
                removeComponents(
                    topPlayerFirstCard, topPlayerSecondCard, topPlayerThirdCard, labelForActivePlayer,
                    labelForLeftPlayer, labelForRightPlayer, labelForTopPlayer,  activePlayerFirstCard,
                    activePlayerSecondCard, activePlayerThirdCard, hiddenActivePlayerFirstCard,
                    hiddenActivePlayerSecondCard, linearLayoutForTopPlayer, leftPlayerFirstCard, leftPlayerSecondCard,
                    leftPlayerThirdCard, linearLayoutForLeftPlayer, rightPlayerFirstCard, rightPlayerSecondCard,
                    rightPlayerThirdCard, linearLayoutForRightPlayer, labelForActivePlayerName
                )
                addComponents(
                    topPlayerFirstCard, topPlayerSecondCard, topPlayerThirdCard, labelForActivePlayer,
                    labelForTopPlayer, activePlayerFirstCard, activePlayerSecondCard, activePlayerThirdCard,
                    hiddenActivePlayerFirstCard, hiddenActivePlayerSecondCard, linearLayoutForTopPlayer,
                    labelForActivePlayerName
                )
            }
        }
    }


    /**
     * Called after a swap action for one card from the middle has done to refresh the UI.
     */
    override fun refreshAfterSwap() {
        refreshBoard()
        refreshMiddleCards()
        refreshActivePlayerCards()
    }

    /**
     * Called after a swap action for all the middle cards has done to refresh the UI.
     */
    override fun refreshAfterSwapAll() {
        refreshBoard()
        refreshMiddleCards()
        refreshActivePlayerCards()
    }

    /**
     * Called after a push action for the middle cards has done to refresh the UI.
     */
    override fun refreshAfterPushCards() {
        val game = rootService.mainGame
        checkNotNull(game) { "No game is currently running" }
        dicardStack.apply {
            frontVisual =
                cardImageLoader.frontImageFor(
                    game.discardStack[game.discardStack.size - 1].suit,
                    game.discardStack[game.discardStack.size - 1].value
                )
            this.showFront()
        }
        refreshBoard()
        refreshMiddleCards()
    }

    /**
     * Called after the turn has given to the next player to refresh the UI.
     */
    override fun refreshAfterNextTurn() {
        val game = rootService.mainGame
        checkNotNull(game) { "No game is currently running" }

         cardDeckIndex = -1
         openCardIndex = -1

        labelForActivePlayerName.text = game.playerList[game.currentPlayerIndex].name

        refreshBoard()
        refreshPlayersArrangement()
        refreshActivePlayerCards()
    }


}