package am.acba.compose.components

import am.acba.component.R
import am.acba.component.extensions.getActivity
import am.acba.compose.theme.DigitalTheme
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun PrimaryToolbar(
    title: String = "",
    actions: @Composable RowScope.() -> Unit = {},
) {
    val activity = LocalContext.current.getActivity()
    TopAppBar(
        title = {
            PrimaryText(
                modifier = Modifier
                    .padding(end = 16.dp, start = 16.dp)
                    .semantics {
                        testTagsAsResourceId = true
                    }
                    .testTag("android:id/centre_title"),
                text = title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = DigitalTheme.colorScheme.backgroundBase,
            titleContentColor = DigitalTheme.colorScheme.contentPrimary,
        ),
        actions = actions,
        navigationIcon = {
            IconButton(onClick = {
                activity?.onBackPressed()
            }, modifier = Modifier
                .semantics {
                    testTagsAsResourceId = true
                }
                .testTag("android:id/leftIcon")) {
                PrimaryIcon(painterResource(R.drawable.ic_back))
            }
        }
    )
}

@Composable
@PreviewLightDark
fun PrimaryToolbarPreview() {
    DigitalTheme {
        PrimaryToolbar(title = "jdscnkjdsc kjdbcnjkds cjksdncjkna")
    }
}