@file:OptIn(ExperimentalMaterial3Api::class)

package am.acba.compose.components.inputs.phoneInput

import am.acba.component.R
import am.acba.component.extensions.getLastCountryActionsEnum
import am.acba.component.extensions.saveCountryLastAction
import am.acba.compose.HorizontalSpacer
import am.acba.compose.VerticalSpacer
import am.acba.compose.components.PrimaryIcon
import am.acba.compose.components.PrimaryText
import am.acba.compose.components.avatar.Avatar
import am.acba.compose.components.avatar.AvatarEnum
import am.acba.compose.components.avatar.AvatarSizeEnum
import am.acba.compose.components.bottomSheet.PrimaryBottomSheet
import am.acba.compose.components.bottomSheet.closeBottomSheet
import am.acba.compose.components.chips.PrimaryChip
import am.acba.compose.components.inputs.Label
import am.acba.compose.components.inputs.SearchBar
import am.acba.compose.components.inputs.SupportAndErrorTexts
import am.acba.compose.components.inputs.createStateColors
import am.acba.compose.components.inputs.visualTransformations.PhoneNumberVisualTransformation
import am.acba.compose.components.listItem.ListItem
import am.acba.compose.theme.DigitalTheme
import am.acba.compose.theme.ShapeTokens
import am.acba.utils.Constants.EMPTY_STRING
import am.acba.utils.enums.CountryEnum
import android.annotation.SuppressLint
import android.content.Context
import android.telephony.TelephonyManager
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.google.i18n.phonenumbers.NumberParseException
import com.google.i18n.phonenumbers.PhoneNumberUtil
import kotlinx.coroutines.CoroutineScope

@Composable
fun PhoneNumberInput(
    value: TextFieldValue = TextFieldValue(""),
    onValueChange: (TextFieldValue) -> Unit,
    helpText: String? = null,
    label: String? = null,
    placeholder: String? = null,
    errorText: String? = null,
    showArrow: Boolean = true,
    isError: Boolean = false,
    onPickContactClick: (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Phone),
    keyboardActions: KeyboardActions = KeyboardActions.Default,

    ) {
    val context: Context = LocalContext.current
    var inputModifier: Modifier = Modifier
    var currencyModifier: Modifier = Modifier
    var isFocused by remember { mutableStateOf(false) }
    var isErrorState by remember { mutableStateOf(isError) }
    var isErrorStateEnabled by remember { mutableStateOf(false) }
    val bottomSheetVisible = remember { mutableStateOf(false) }
    var selectedCountry by remember {
        mutableStateOf(CountryEnum[detectCountryCodeBySim(context)])
    }
    when {
        isErrorState && isErrorStateEnabled -> {
            inputModifier = errorBorderForPhoneInput()
            currencyModifier = errorBorderForRegionCode()
        }

        isFocused -> {
            inputModifier = focusedBorderForPhoneInput()
            currencyModifier = focusedBorderForRegionCode()
        }

        !isFocused && value.text.isNotEmpty() -> {
            isErrorStateEnabled = isErrorState
        }

        value.text.isEmpty() -> {
            isErrorStateEnabled = false
        }
    }


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
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                RegionCode(
                    modifier = currencyModifier,
                    showArrow = showArrow,
                    country = selectedCountry,
                    onRegionClick = { bottomSheetVisible.value = true }
                )
                HorizontalSpacer(width = 1)
                PhoneTextField(
                    modifier = inputModifier,
                    value = value,
                    isoCode = selectedCountry.iso,
                    dialCode = selectedCountry.dialCode,
                    onValueChange = onValueChange,
                    onPickContactClick = onPickContactClick,
                    onFocusChanged = { isFocused = it.isFocused },
                    isErrorState = { isErrorState = it },
                    label = label,
                    placeholder = placeholder,
                    maxLength = 15,
                    keyboardOptions = keyboardOptions,
                    keyboardActions = keyboardActions
                )
            }
            SupportAndErrorTexts(isError, true, errorText, helpText)
        }
        CountriesBottomSheet(context, bottomSheetVisible, onCountrySelected = { newCountry ->
            selectedCountry = newCountry
            isErrorStateEnabled = false
        })
    }
}

private fun detectCountryCodeBySim(context: Context): String {
    val telephonyManager =
        context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
    val simCountryISO = telephonyManager.simCountryIso.ifEmpty { CountryEnum.ARMENIA.iso }
    return simCountryISO
}

@Composable
fun RegionCode(
    modifier: Modifier = Modifier,
    showArrow: Boolean,
    country: CountryEnum,
    onRegionClick: (() -> Unit)?
) {
    Row(
        modifier = modifier
            .wrapContentWidth()
            .height(58.dp)
            .background(
                shape = ShapeTokens.inputShapeLeftSide,
                color = DigitalTheme.colorScheme.backgroundTonal2
            )
            .padding(horizontal = 12.dp)
            .clickable {
                onRegionClick?.invoke()
            },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Avatar(
            avatarSize = AvatarSizeEnum.AVATAR_SIZE_20,
            avatarType = AvatarEnum.IMAGE,
            imageUrl = country.flagUrl,
            clipPercent = 50
        )

        PrimaryText(
            modifier = Modifier.padding(start = 4.dp),
            text = country.dialCode,
            style = DigitalTheme.typography.body1Regular,
            color = DigitalTheme.colorScheme.contentPrimaryTonal1
        )
        if (showArrow) {
            PrimaryIcon(
                painterResource(R.drawable.ic_down), modifier = Modifier
                    .padding(start = 4.dp)
                    .width(16.dp)
                    .height(16.dp),
                tint = DigitalTheme.colorScheme.contentPrimary
            )
        }
    }
}

@Composable
fun RowScope.PhoneTextField(
    modifier: Modifier,
    value: TextFieldValue,
    label: String? = null,
    placeholder: String?,
    isoCode: String,
    dialCode: String,
    onValueChange: (TextFieldValue) -> Unit,
    onPickContactClick: (() -> Unit)?,
    isErrorState: ((Boolean) -> Unit),
    onFocusChanged: (FocusState) -> Unit,
    maxLength: Int,
    keyboardOptions: KeyboardOptions,
    keyboardActions: KeyboardActions,
    focusRequester: FocusRequester = remember { FocusRequester() }
) {
    val pattern = Regex("^\\d*$")
    val visualTransformation =
        PhoneNumberVisualTransformation(isoCode, dialCode) { isErrorState::invoke.invoke(!it) }
    TextField(
        value = value,
        onValueChange = {
            if (checkInputValidation(value, maxLength, pattern, it))
                onValueChange(it)
        },
        visualTransformation = visualTransformation,
        placeholder = placeholder?.let { { Text(text = it) } },
        shape = ShapeTokens.inputShapeRightSide,
        modifier = modifier
            .height(58.dp)
            .weight(1f)
            .focusRequester(focusRequester)
            .onFocusChanged(onFocusChanged),
        label = label?.let { { Label(text = label, isEnabled = true) } },
        colors = createStateColors(),
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        trailingIcon = {
            IconButton(onClick = { onPickContactClick?.invoke() }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_contacts),
                    contentDescription = "Contacts Icon",
                    modifier = Modifier.padding(0.dp, 0.dp, 4.dp, 0.dp),
                    tint = DigitalTheme.colorScheme.contentPrimary
                )
            }
        }

    )
}

private fun checkInputValidation(
    value: TextFieldValue,
    maxLength: Int,
    pattern: Regex,
    textFieldValue: TextFieldValue
): Boolean {
    return (value.text.length != maxLength || textFieldValue.text.length <= maxLength)
        && textFieldValue.text.matches(pattern)
}

@SuppressLint("ModifierFactoryExtensionFunction")
@Composable
private fun errorBorderForRegionCode() =
    Modifier.border(
        1.dp,
        DigitalTheme.colorScheme.contentDangerTonal1,
        ShapeTokens.inputShapeLeftSide
    )

@SuppressLint("ModifierFactoryExtensionFunction")
@Composable
private fun errorBorderForPhoneInput() =
    Modifier.border(
        1.dp,
        DigitalTheme.colorScheme.contentDangerTonal1,
        ShapeTokens.inputShapeRightSide
    )

@SuppressLint("ModifierFactoryExtensionFunction")
@Composable
private fun focusedBorderForPhoneInput() =
    Modifier.border(
        1.dp,
        DigitalTheme.colorScheme.borderPrimary,
        ShapeTokens.inputShapeRightSide
    )

@SuppressLint("ModifierFactoryExtensionFunction")
@Composable
private fun focusedBorderForRegionCode() =
    Modifier.border(
        1.dp,
        DigitalTheme.colorScheme.borderPrimary,
        ShapeTokens.inputShapeLeftSide
    )

@SuppressLint("MutableCollectionMutableState")
@Composable
private fun CountriesBottomSheet(
    context: Context,
    bottomSheetVisible: MutableState<Boolean>,
    onCountrySelected: (CountryEnum) -> Unit
) {
    var dBActionsList by remember { mutableStateOf(context.getLastCountryActionsEnum()) }
    var searchQuery by remember { mutableStateOf(EMPTY_STRING) }
    val countries = CountryEnum.entries.toList()
    val visibleItems: List<CountryEnum> = filterCountries(countries, searchQuery)

    PrimaryBottomSheet(
        title = stringResource(R.string.select_country_code),
        contentHorizontalPadding = 0.dp,
        bottomSheetVisible = bottomSheetVisible.value,
        dismiss = {
            bottomSheetVisible.value = false
            searchQuery = EMPTY_STRING
        }) { state: SheetState, coroutineScope: CoroutineScope ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            VerticalSpacer(24)
            SearchBar(
                hint = stringResource(R.string.search),
                onTextChange = { countryText ->
                    searchQuery = countryText
                })
            VerticalSpacer(32)
            PrimaryText(
                text = stringResource(R.string.phone_number_most_searched).toUpperCase(Locale.current),
                style = DigitalTheme.typography.smallRegular,
                color = DigitalTheme.colorScheme.contentPrimaryTonal1
            )
            VerticalSpacer(10)
            CountrySelectionRow(
                dBActionsList = dBActionsList,
                coroutineScope = coroutineScope,
                bottomSheetVisible = bottomSheetVisible,
                state = state,
                onCountrySelected = onCountrySelected,
                onUpdateList = { dBActionsList = it },
                context = context
            )
            VerticalSpacer(32)
            PrimaryText(
                text = stringResource(R.string.all).toUpperCase(Locale.current),
                style = DigitalTheme.typography.smallRegular,
                color = DigitalTheme.colorScheme.contentPrimaryTonal1
            )
            VerticalSpacer(20)
            CountrySelectionList(
                countries = visibleItems,
                state = state,
                coroutineScope = coroutineScope,
                bottomSheetVisible = bottomSheetVisible,
                onUpdateList = { dBActionsList = it },
                onCountrySelected = onCountrySelected,
                context = context
            )
        }

    }
}

@Composable
private fun filterCountries(
    countries: List<CountryEnum>,
    searchQuery: String
): List<CountryEnum> {
    return if (searchQuery.length > 2) {
        countries.filter {
            stringResource(it.titleResId).contains(searchQuery, ignoreCase = true) ||
                it.dialCode.contains(searchQuery, ignoreCase = true) ||
                it.name.contains(searchQuery, ignoreCase = true)
        }
    } else {
        countries
    }
}

@Composable
private fun CountrySelectionList(
    countries: List<CountryEnum>,
    state: SheetState,
    coroutineScope: CoroutineScope,
    bottomSheetVisible: MutableState<Boolean>,
    onUpdateList: (MutableList<CountryEnum>) -> Unit,
    onCountrySelected: (CountryEnum) -> Unit,
    context: Context,
) {
    LazyColumn {
        itemsIndexed(countries) { index, item ->
            ListItem(
                contentHorizontalPadding = 0.dp,
                title = "${stringResource(item.titleResId)} (${item.dialCode})",
                titleStyle = DigitalTheme.typography.body1Regular,
                backgroundColor = Color.Transparent,
                avatarType = AvatarEnum.IMAGE,
                avatarImageUrl = item.flagUrl,
                avatarClipPercent = 50,
                showDivider = true,
                onClick = {
                    closeBottomSheet(state = state, scope = coroutineScope) {
                        bottomSheetVisible.value = false
                    }
                    onCountrySelected(item)
                    onUpdateList(context.saveCountryLastAction(item))
                }
            )
        }
    }
}

@Composable
private fun CountrySelectionRow(
    dBActionsList: MutableList<CountryEnum>,
    coroutineScope: CoroutineScope,
    bottomSheetVisible: MutableState<Boolean>,
    state: SheetState,
    onCountrySelected: (CountryEnum) -> Unit,
    onUpdateList: (MutableList<CountryEnum>) -> Unit,
    context: Context,
) {
    FlowRow(
        itemVerticalAlignment = Alignment.CenterVertically
    ) {
        dBActionsList.forEach { country ->
            PrimaryChip(
                modifier = Modifier.padding(top = 10.dp, end = 10.dp),
                title = stringResource(country.titleResId),
                imageUrl = country.flagUrl,
                clipPercent = 50
            ) {
                closeBottomSheet(state = state, scope = coroutineScope) {
                    bottomSheetVisible.value = false
                }
                onCountrySelected(country)
                onUpdateList(context.saveCountryLastAction(country))
            }
        }
    }
}

private fun formatAndValidatePhone(phone: String, isoCode: String): Pair<String?, Boolean> {
    val phoneUtil = PhoneNumberUtil.getInstance()
    return try {
        val parsed = phoneUtil.parse(phone, isoCode)
        val isValid = phoneUtil.isValidNumber(parsed)
        val formatted = phoneUtil.format(parsed, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL)
        val lastValue = formatted.removePrefix("+${parsed.countryCode}").trimStart()
        lastValue to isValid
    } catch (_: NumberParseException) {
        null to false
    }
}

@Composable
@PreviewLightDark
fun PhoneInputPreview() {
    val textNormal =
        rememberSaveable(stateSaver = TextFieldValue.Saver) { mutableStateOf(TextFieldValue("")) }
    DigitalTheme {
        Column(
            Modifier
                .background(DigitalTheme.colorScheme.backgroundBase)
                .padding(10.dp)
        ) {
            PhoneNumberInput(
                value = textNormal.value,
                onValueChange = { textNormal.value = it },
                helpText = "help text",
                errorText = "error text",
                label = "Phone",
                placeholder = "Phone Number",
                isError = false,
            )
        }
    }
}
