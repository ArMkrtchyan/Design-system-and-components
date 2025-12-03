package am.acba.compose.components

import am.acba.component.R
import am.acba.component.extensions.getActivity
import am.acba.compose.theme.DigitalTheme
import am.acba.utils.extensions.id
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrimaryToolbar(
    title: String = "",
    contentColor: Color = DigitalTheme.colorScheme.contentPrimary,
    containerColor: Color = DigitalTheme.colorScheme.backgroundBase,
    expandedHeight: Dp = TopAppBarDefaults.TopAppBarExpandedHeight,
    content: @Composable (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {},
) {
    val activity = LocalContext.current.getActivity()
    TopAppBar(
        expandedHeight = expandedHeight,
        title = {
            if (content != null) {
                content()
            } else {
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
            }
        }, colors = TopAppBarDefaults.topAppBarColors(
            containerColor = containerColor,
            titleContentColor = contentColor,
            scrolledContainerColor = containerColor,
            navigationIconContentColor = contentColor,
            actionIconContentColor = contentColor
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
        Column {
            PrimaryToolbar(content = { ToolbarContent() }, expandedHeight = 81.dp)
            PrimaryToolbar(content = { ToolbarContent() }, expandedHeight = 81.dp, actions = {
                Row {
                    Image(
                        painter = painterResource(id = R.drawable.ic_info),
                        contentDescription = null
                    )
                    Image(
                        painter = painterResource(id = R.drawable.ic_right),
                        contentDescription = null
                    )
                }
            })
            PrimaryToolbar(title = "title")
            PrimaryToolbar(title = "title", actions = {
                Row {
                    Image(
                        painter = painterResource(id = R.drawable.ic_info),
                        contentDescription = null
                    )
                    Image(
                        painter = painterResource(id = R.drawable.ic_right),
                        contentDescription = null
                    )
                }
            })
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun ToolbarContent() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Image(
            modifier = Modifier
                .size(32.dp, 20.dp)
                .align(Alignment.CenterHorizontally),
            painter = painterResource(id = R.drawable.empty_card_logo_light),
            contentDescription = null
        )
        PrimaryText(
            modifier = Modifier
                .padding(end = 16.dp, start = 16.dp)
                .id("centre_title")
                .align(Alignment.CenterHorizontally),
            text = "Աշխատավարձային",
            style = DigitalTheme.typography.xSmallRegular,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
        PrimaryText(
            modifier = Modifier
                .padding(end = 16.dp, start = 16.dp)
                .id("centre_title")
                .align(Alignment.CenterHorizontally),
            text = "**** AMD",
            style = DigitalTheme.typography.body1Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}