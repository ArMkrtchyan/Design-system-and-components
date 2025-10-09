package am.acba.compose.components

import am.acba.component.R
import am.acba.component.extensions.getActivity
import am.acba.compose.theme.DigitalTheme
import am.acba.utils.extensions.id
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrimaryToolbar(
    title: String = "",
    contentColor: Color = DigitalTheme.colorScheme.contentPrimary,
    containerColor: Color = DigitalTheme.colorScheme.backgroundBase,
    actions: @Composable RowScope.() -> Unit = {},
) {
    val activity = LocalContext.current.getActivity()
    TopAppBar(
        title = {
            PrimaryText(
                modifier = Modifier
                    .padding(end = 16.dp, start = 16.dp)
                    .id("centre_title"),
                text = title,
                style = DigitalTheme.typography.body1Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = contentColor
            )
        }, colors = TopAppBarDefaults.topAppBarColors(
            containerColor = containerColor,
            titleContentColor = contentColor,
        ), actions = actions, navigationIcon = {
            IconButton(onClick = {
                activity?.onBackPressed()
            }, modifier = Modifier.id("leftIcon")) {
                PrimaryIcon(painterResource(R.drawable.ic_back), tint = contentColor)
            }
        })
}

@Composable
@PreviewLightDark
fun PrimaryToolbarPreview() {
    DigitalTheme {
        PrimaryToolbar(title = "jdscnkjdsc kjdbcnjkds cjksdncjkna")
    }
}