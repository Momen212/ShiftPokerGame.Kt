package service

import kotlin.test.*

/**
 * This test class verifies that proper of all dependent services are initialized.
 */
class RootServiceTest {


    /**
     * Ensures that all internal services are created.
     */
    @Test
    fun `test if rootservice initializes services correctly`() {
        val rootService = RootService()

        assertNotNull(rootService.playerService)
        assertNotNull(rootService.gameService)
    }


    /**
     * Confirms that no game is active directly after initialization.
     */
    @Test
    fun `test if mainGame is null initially`() {
        val rootService = RootService()

        assertNull(rootService.mainGame)
    }

    /**
     * Tests whether the addRefreshable function of RootService correctly adds
     * the given Refreshable to both connected services (GameService and PlayerActionService).
     */
    @Test
    fun `test addRefreshable adds refreshable to both services`() {
        val rootService = RootService()

        val refreshable = object : Refreshable {}

        rootService.addRefreshable(refreshable)

        assertTrue(rootService.gameService.refreshables.contains(refreshable))
        assertTrue(rootService.playerService.refreshables.contains(refreshable))
    }

}