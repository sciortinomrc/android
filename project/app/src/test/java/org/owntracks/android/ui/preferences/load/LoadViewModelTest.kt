package org.owntracks.android.ui.preferences.load

import android.content.Context
import android.content.res.Resources
import org.greenrobot.eventbus.EventBus
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.owntracks.android.preferences.InMemoryPreferencesStore
import org.owntracks.android.support.Parser
import org.owntracks.android.preferences.Preferences
import org.owntracks.android.preferences.PreferencesGettersAndSetters
import org.owntracks.android.preferences.PreferencesStore
import org.owntracks.android.ui.NoopAppShortcuts
import java.net.URI

class LoadViewModelTest {
    private lateinit var mockResources: Resources
    private lateinit var mockContext: Context
    private lateinit var preferencesStore: PreferencesStore
    private val eventBus: EventBus = mock {}

    @Before
    fun createMocks() {
        mockContext = mock {
            on { packageName } doReturn javaClass.canonicalName
        }
        preferencesStore = InMemoryPreferencesStore()
    }

    @Test
    fun `When invalid JSON on an inline owntracks config URL, then the error is correctly set`() {
        val parser = Parser(null)
        val preferences = Preferences(mockContext, preferencesStore, NoopAppShortcuts())
        val vm = LoadViewModel(preferences, parser, InMemoryWaypointsRepo(eventBus))

        vm.extractPreferences(URI("owntracks:///config?inline=e30k"))
        assertEquals(
            """Import failed: Message is not a valid configuration message""",
            vm.displayedConfiguration
        )
    }
}
