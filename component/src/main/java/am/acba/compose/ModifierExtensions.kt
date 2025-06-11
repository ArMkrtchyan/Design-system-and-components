@file:OptIn(ExperimentalComposeUiApi::class)

package am.acba.compose

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId

fun Modifier.id(id: String, prefix: String = "android:id/"): Modifier {
    return this then Modifier
        .semantics { testTagsAsResourceId = true }
        .testTag("$prefix$id")
}
