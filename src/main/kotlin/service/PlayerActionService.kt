package service


/**
 * Service responsible for managing player actions during the Schiebe Poker game.
 * @property rootService reference to the RootService, used to access the current game state.
 */
class PlayerActionService(private val rootService: RootService): AbstractRefreshingService() {

    /**
     * Swaps a single card between the current player's open hand and the middle cards.
     * @param playerSelection index of the card in the player's hand to swap.
     * @param middleSelection index of the card in the middle to swap with.
     * @throws IllegalStateException if no game is currently running.
     * @throws IllegalArgumentException if middleSelection or playerSelection is not in the valid range.
     */
    fun swap(playerSelection: Int, middleSelection: Int){
        val currentGame = rootService.mainGame ?: throw IllegalStateException("No game currently running.")
        require(playerSelection in 0..2) { "playerSelection is not in the valid range." }
        require(middleSelection in 0..2) {"middleSelection is not in the valid range."}

        val currentPlayer = currentGame.playerList[currentGame.currentPlayerIndex]

        //removeAt: removes and returns the card with (index)
        val playerCard = currentPlayer.openCards[playerSelection]
        val middleCard = currentGame.middleCards[middleSelection]

        currentPlayer.openCards[playerSelection]= middleCard
        currentGame.middleCards[middleSelection] =playerCard

        currentGame.logList.add("${currentPlayer.name} hat Swap zwischen $middleCard und $playerCard ausgeführt")
        currentPlayer.actionCount+=1
        onAllRefreshables { refreshAfterSwap() }
        rootService.gameService.nextTurn()
    }
    /**
     * Swaps all open cards of the current player with the middle cards.
     * @throws IllegalStateException if no game is currently running.
     */
    fun swapAll() {
        val currentGame = rootService.mainGame ?: throw IllegalStateException("No game currently running.")
        val currentPlayer = currentGame.playerList[currentGame.currentPlayerIndex]

        //copy for player cards has done
        val copyPlayerCards = currentPlayer.openCards.toMutableList()

        currentPlayer.openCards.clear()
        currentPlayer.openCards.addAll(currentGame.middleCards)

        currentGame.middleCards.clear()
        currentGame.middleCards.addAll(copyPlayerCards)

        currentGame.logList.add("${currentPlayer.name} hat SwapALL ausgeführt")
        currentPlayer.actionCount+=1
        onAllRefreshables { refreshAfterSwapAll() }
        rootService.gameService.nextTurn()
    }

    /**
     * Pushes cards in the middle either to the left or the right.
     * @param left if true, Pushes cards in the middle to the left,
     * else (false) Pushes cards in the middle to the right.
     * @throws IllegalStateException if no game is currently running.
     */
    fun pushCards(left: Boolean){
        val currentGame = rootService.mainGame ?: throw IllegalStateException("No game currently running.")

        if (left) {
            val leftEdgeCard = currentGame.middleCards.removeAt(0)
            currentGame.discardStack.add(leftEdgeCard)
            if (currentGame.drawStack.isEmpty()) {
                rootService.gameService.reshuffleDrawStack()
                currentGame.middleCards.add(currentGame.drawStack.removeAt(0))
            } else {
                currentGame.middleCards.add(currentGame.drawStack.removeAt(0))
            }
        }else{
            val rightEdgeCard = currentGame.middleCards.removeAt(2)
            currentGame.discardStack.add(rightEdgeCard)
            if (currentGame.drawStack.isEmpty()) {
                rootService.gameService.reshuffleDrawStack()
                currentGame.middleCards.add(currentGame.drawStack.removeAt(0))
            } else {
                currentGame.middleCards.add(currentGame.drawStack.removeAt(0))
            }
            //swap indexes
            val savedCardOnFirstIndex =currentGame.middleCards[0]
            val savedCardOnSecondIndex =currentGame.middleCards[1]
            currentGame.middleCards[0]=currentGame.middleCards[2]
            currentGame.middleCards[1]=savedCardOnFirstIndex
            currentGame.middleCards[2]=savedCardOnSecondIndex

        }

        if(left){
            currentGame.logList.add("${currentGame.playerList[currentGame.currentPlayerIndex]
                .name} hat pushLeft ausgeführt")
        }else{
            currentGame.logList.add("${currentGame.playerList[currentGame.currentPlayerIndex]
                .name} hat pushRight ausgeführt")
        }
        currentGame.playerList[currentGame.currentPlayerIndex].actionCount+=1
        onAllRefreshables { refreshAfterPushCards() }
        rootService.gameService.nextTurn()
    }

    /**
     * Player passes and does nothing.
     * @throws IllegalStateException if no game is currently running.
     */
    fun swapNone(){
        val currentGame = rootService.mainGame ?: throw IllegalStateException("No game currently running.")
        currentGame.logList.add("${currentGame.playerList[currentGame.currentPlayerIndex]
            .name} hat swapNone ausgeführt")
        currentGame.playerList[currentGame.currentPlayerIndex].actionCount+=1
        rootService.gameService.nextTurn()
    }
}