package org.owntracks.android.ui.preferences

import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import org.owntracks.android.R
import javax.inject.Inject

@AndroidEntryPoint
class NotificationFragment @Inject constructor() : AbstractPreferenceFragment() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        super.onCreatePreferences(savedInstanceState, rootKey)
        setPreferencesFromResource(R.xml.preferences_notification, rootKey)
    }
}
