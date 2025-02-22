package org.owntracks.android.ui.preferences

import android.content.Intent
import android.os.Bundle
import androidx.preference.Preference
import dagger.hilt.android.AndroidEntryPoint
import org.owntracks.android.R
import org.owntracks.android.preferences.types.ConnectionMode
import org.owntracks.android.preferences.Preferences.Companion.EXPERIMENTAL_FEATURE_SHOW_EXPERIMENTAL_PREFERENCE_UI
import org.owntracks.android.ui.preferences.connection.ConnectionActivity
import org.owntracks.android.ui.preferences.editor.EditorActivity

@AndroidEntryPoint
class PreferencesFragment : AbstractPreferenceFragment() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        super.onCreatePreferences(savedInstanceState, rootKey)
        setPreferencesFromResource(R.xml.preferences_root, rootKey)
        // Have to do these manually here, as there's an android bug that prevents the activity from being found when launched from intent declared on the preferences XML.
        findPreference<Preference>(UI_SCREEN_CONFIGURATION)!!.intent = Intent(context, EditorActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)

        // TODO move this to a preferences fragment rather than its own activity.
        findPreference<Preference>(UI_PREFERENCE_SCREEN_CONNECTION)!!.intent = Intent(context, ConnectionActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)

        findPreference<Preference>(UI_PREFERENCE_SCREEN_EXPERIMENTAL)?.run {
            this.isVisible = preferences.experimentalFeatures.contains(EXPERIMENTAL_FEATURE_SHOW_EXPERIMENTAL_PREFERENCE_UI)
        }

    }

    override fun onResume() {
        super.onResume()
        findPreference<Preference>(UI_PREFERENCE_SCREEN_CONNECTION)!!.summary = connectionMode
        findPreference<Preference>(UI_PREFERENCE_SCREEN_EXPERIMENTAL)?.run {
            this.isVisible = preferences.experimentalFeatures.contains(EXPERIMENTAL_FEATURE_SHOW_EXPERIMENTAL_PREFERENCE_UI)
        }
    }

    private val connectionMode: String
        get() = when (preferences.mode) {
            ConnectionMode.HTTP -> getString(R.string.mode_http_private_label)
            ConnectionMode.MQTT -> getString(R.string.mode_mqtt_private_label)
        }

    companion object {
        private const val UI_PREFERENCE_SCREEN_CONNECTION = "connectionScreen"
        private const val UI_SCREEN_CONFIGURATION = "configuration"
        private const val UI_PREFERENCE_SCREEN_EXPERIMENTAL = "experimentalScreen"
    }
}
