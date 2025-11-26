package am.acba.compose.components.inputs

import am.acba.component.R
import am.acba.compose.theme.DigitalTheme
import am.acba.compose.theme.ShapeTokens
import am.acba.utils.extensions.id
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SearchBar(
    hint: String = stringResource(R.string.search),
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true,
    height: Dp = 40.dp,
    cornerShape: Shape = ShapeTokens.shapePrimaryInput,
    backgroundColor: Color = DigitalTheme.colorScheme.backgroundTonal2,
    toolbarMode: Boolean = false,
    onBackButtonClick: () -> Unit = {},
    onSearchClicked: () -> Unit = {},
    onTextChange: (String) -> Unit = {},
    onComponentClick: (() -> Unit)? = null,
) {
    onComponentClick?.let {
        modifier.clickable {

        }
    }
    var text by remember { mutableStateOf(TextFieldValue()) }
    var startIcon by remember { mutableIntStateOf(R.drawable.ic_search) }
    Row(
        modifier = modifier
            .height(height)
            .fillMaxWidth()
            .background(color = backgroundColor, shape = cornerShape),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .size(40.dp)
        ) {
            Icon(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
                    .clickable {
                        if (startIcon == R.drawable.ic_back) {
                            onBackButtonClick.invoke()
                        }
                    },
                painter = painterResource(id = startIcon),
                contentDescription = "search",
                tint = DigitalTheme.colorScheme.contentPrimaryTonal1,
            )
        }
        BasicTextField(
            modifier = Modifier
                .id("search")
                .weight(5f)
                .fillMaxWidth()
                .onFocusChanged {
                    if (toolbarMode) {
                        if (it.isFocused) {
                            startIcon = R.drawable.ic_back
                        }
                    }
                },
            value = text,
            cursorBrush = SolidColor(DigitalTheme.colorScheme.contentBrand),
            onValueChange = {
                text = it
                onTextChange(it.text)
            },
            enabled = isEnabled,
            textStyle = TextStyle(
                color = DigitalTheme.colorScheme.contentPrimary,
                fontSize = 16.sp
            ),
            decorationBox = { innerTextField ->
                if (text.text.isEmpty()) {
                    Text(
                        text = hint,
                        color = DigitalTheme.colorScheme.contentPrimaryTonal1,
                        fontSize = 16.sp
                    )
                }
                innerTextField()
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(onSearch = { onSearchClicked() }),
            singleLine = true
        )
        Box(
            modifier = Modifier
                .size(40.dp)
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }) {
                    if (text.text.isNotEmpty()) {
                        text = TextFieldValue(text = "")
                        onTextChange("")
                    }
                }
        ) {
            if (text.text.isNotEmpty()) {
                Icon(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(8.dp),
                    painter = painterResource(id = R.drawable.ic_close),
                    contentDescription = "Search",
                    tint = DigitalTheme.colorScheme.contentPrimaryTonal1,
                )
            }
        }
    }
}

