package am.acba.composeComponents.timeLine


import am.acba.component.R
import am.acba.component.primaryTimeLine.TimeLineType
import am.acba.compose.common.VerticalSpacer
import am.acba.compose.components.PrimaryToolbar
import am.acba.compose.components.timeLine.PrimaryTimeLine
import am.acba.compose.components.timeLine.TimeLineComposeModel
import am.acba.compose.components.timeLine.TimeLineStatusComposeEnum
import am.acba.compose.theme.DigitalTheme
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimeLineScreen(title: String = "") {
    val list = arrayListOf(
        TimeLineComposeModel(
            "14/10/2024",
            "400,000.00 AMD",
            "Մարումը՝ այսօր",
            "LinkText",
            timeLineStatus = TimeLineStatusComposeEnum.PENDING
        ),
        TimeLineComposeModel(
            "14/10/2024",
            "400,000.00 AMD",
            "Մարումը՝ այսօր",
            "LinkText",
            timeLineStatus = TimeLineStatusComposeEnum.PENDING_2
        ),
        TimeLineComposeModel(
            "14/10/2024",
            "400,000.00 AMD",
            "Մարումը՝ այսօր",
            "LinkText",
            timeLineStatus = TimeLineStatusComposeEnum.WARNING
        ),
        TimeLineComposeModel(
            "14/10/2024",
            "400,000.00 AMD",
            "Մարումը՝ այսօր",
            "LinkText",
            timeLineStatus = TimeLineStatusComposeEnum.SUCCESS
        ),
        TimeLineComposeModel(
            "14/10/2024",
            "400,000.00 AMD",
            "Մարումը՝ այսօր",
            "LinkText",
            timeLineStatus = TimeLineStatusComposeEnum.DANGER,
        ),
        TimeLineComposeModel(
            "14/10/2024",
            "400,000.00 AMD",
            "Մարումը՝ այսօր",
            "LinkText",
            timeLineStatus = TimeLineStatusComposeEnum.DANGER,
            timeLineStartIcon = R.drawable.ic_close_small_2,
            timeLineContentBackgroundColor = Color.Transparent,
            timeLineLinkTextColor = DigitalTheme.colorScheme.contentInverse
        ),
        TimeLineComposeModel(
            "14/10/2024",
            "400,000.00 AMD",
            timeLineStatus = TimeLineStatusComposeEnum.NONE
        ),
        TimeLineComposeModel(
            "14/10/2024",
            "400,000.00 AMD",
            timeLineStatus = TimeLineStatusComposeEnum.NONE
        ),
    )
    val progressList = arrayListOf(
        TimeLineComposeModel(
            timeLineTitle = "Դիմումի ուղարկում",
            timeLineDescription = "Ուղղարկվել է՝ 20.02.2024",
            timeLineStatus = TimeLineStatusComposeEnum.SUCCESS,
            timeLineContentBackgroundColor = Color.Transparent
        ),
        TimeLineComposeModel(
            timeLineTitle = "Դիմումի դիտարկում",
            timeLineDescription = "Դիտարկվել է՝ 20.02.2024",
            timeLineStatus = TimeLineStatusComposeEnum.SUCCESS,
            timeLineContentBackgroundColor = Color.Transparent
        ),
        TimeLineComposeModel(
            timeLineTitle = "ՀԴՄ սարքի  գրանցում բանկում",
            timeLineDescription = "Մինչև՝ 20.02.2024",
            timeLineStatus = TimeLineStatusComposeEnum.SUCCESS,
            timeLineContentBackgroundColor = Color.Transparent
        ),
        TimeLineComposeModel(
            timeLineTitle = "Պայմանագրի կնքում",
            timeLineDescription = "Մինչև ՝ 20.04.2023",
            timeLineStatus = TimeLineStatusComposeEnum.PENDING
        ),
        TimeLineComposeModel(
            timeLineTitle = "Սարքի ակտիվացում",
            timeLineDescription = "Պայմանագրի կնքումից հետո 2-3 աշխ. օրվա ընթացքում",
            timeLineStatus = TimeLineStatusComposeEnum.DEFAULT,
            timeLineContentBackgroundColor = Color.Transparent
        )
    )
    Box(
        modifier = Modifier
            .background(DigitalTheme.colorScheme.backgroundBase)
            .fillMaxSize()
            .padding(
                bottom = TopAppBarDefaults.windowInsets
                    .only(WindowInsetsSides.Bottom)
                    .asPaddingValues()
                    .calculateBottomPadding()
            )
    ) {
        Column(Modifier.fillMaxSize()) {
            PrimaryToolbar(title = title)
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .verticalScroll(rememberScrollState()),
            ) {

                PrimaryTimeLine(
                    title = "Մարման գրաֆիկ",
                    endIconPainter = painterResource(R.drawable.ic_right),
                    timeLineItems = listOf(
                        TimeLineComposeModel(
                            "14/10/2024",
                            "400,000.00 AMD",
                            "Մարումը՝ այսօր",
                            "LinkText",
                            timeLineStatus = TimeLineStatusComposeEnum.DANGER,
                        )
                    ),
                )
                VerticalSpacer(30.dp)
                PrimaryTimeLine(
                    title = "Մարման գրաֆիկ",
                    endIconPainter = painterResource(R.drawable.ic_right),
                    timeLineItems = list,
                )
                VerticalSpacer(30.dp)
                PrimaryTimeLine(
                    timeLineItems = progressList,
                    timeLineType = TimeLineType.PROGRESS
                )
                VerticalSpacer(30.dp)
            }
        }
    }
}

@Composable
@PreviewLightDark
fun TimeLinePreview() {
    DigitalTheme {
        TimeLineScreen()
    }
}