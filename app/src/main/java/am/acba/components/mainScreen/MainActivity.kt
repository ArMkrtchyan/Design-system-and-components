package am.acba.components.mainScreen

import am.acba.component.badge.PrimaryBadge
import am.acba.component.chip.PrimaryChip
import am.acba.component.databinding.DialogContentTestBinding
import am.acba.component.dialog.PrimaryAlertDialog
import am.acba.component.exchange.ExchangeRate
import am.acba.component.extensions.getColorFromAttr
import am.acba.component.extensions.getColorStateListFromAttr
import am.acba.components.R
import am.acba.components.databinding.ActivityMainBinding
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    companion object {
        var darkTheme = true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(if (darkTheme) am.acba.component.R.style.ACBA_Theme_Dark else am.acba.component.R.style.ACBA_Theme_Light)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.initView()
    }

    private fun ActivityMainBinding.initView() {
        listItem.showBadge()
        binding.dropDown.setOnClickListener {
            binding.dropDown.addFocus()
        }

        listItem.badge.setBadgeType(PrimaryBadge.BadgeType.TEXT)
        listItem.badge.setBadgeText("Առաջարկ")
        listItem.badge.setBadgeTextColor(this@MainActivity.getColorFromAttr(am.acba.component.R.attr.contentWarning))
        listItem.badge.setBadgeBackgroundTint(this@MainActivity.getColorStateListFromAttr(am.acba.component.R.attr.backgroundWarning))

        input.apply {
            setEndIconOnClickListener { Toast.makeText(this@MainActivity, "Click", Toast.LENGTH_SHORT).show() }
            setStartIconOnClickListener { Toast.makeText(this@MainActivity, "Click", Toast.LENGTH_SHORT).show() }
            setOnFocusChangeListener { _, _ ->

            }
        }

//        buttonPr.apply {
//            setOnClickListener {
//                showPrimaryAlertDialog(this@MainActivity, layoutInflater)
//            }
//        }

        exchangeRates.apply {
            setOnClickListener {
                binding.dropDown.removeFocus()
            }
            val firstRate = ExchangeRate(am.acba.component.R.drawable.flag_usa, "$ 406.00", "$ 410.50")
            val secondRate = ExchangeRate(am.acba.component.R.drawable.flag_russian, "₽ 4.30", "₽ 4.72")
            val thirdRate = ExchangeRate(am.acba.component.R.drawable.flag_usa, "€ 435.50", "€ 452.00")
            val rates = Triple(firstRate, secondRate, thirdRate)
            setExchangeRates(rates)
        }
        search2.setOnClickListener {
            Toast.makeText(this@MainActivity, "Click", Toast.LENGTH_SHORT).show()
        }
        search2.setOnClickListener {
            Toast.makeText(this@MainActivity, "Click", Toast.LENGTH_SHORT).show()
        }

//        setChipClicks(chipSmall1)
//        chipSmall1.setEndIconClickListener {
//            Toast.makeText(this@MainActivity, "Click on close", Toast.LENGTH_SHORT).show()
//        }

    }

    private fun showPrimaryAlertDialog(context: Context, inflater: LayoutInflater) {

        val positiveButtonTextColor =
            context.getColorStateListFromAttr(am.acba.component.R.attr.contentBrandTonal1)
        val negativeButtonTextColor =
            context.getColorStateListFromAttr(am.acba.component.R.attr.contentDangerTonal1)

        //Create any xml file in FrameLayout and add in Alertdialog as content
        val dialogLayoutBinding = DialogContentTestBinding.inflate(inflater)

        PrimaryAlertDialog.Builder(context)
            .icon(am.acba.component.R.drawable.checkbox_button_icon)
            .title("Օգտատերը բլոկավորված է")
            .description("Դուք կարող եք ապաբլոկավորել սեղմելով ապաբլոկավորման կոճակը:")
            .positiveButtonText("Ok")
            .positiveButtonTextColor(positiveButtonTextColor)
            .negativeButtonText("Cancel")
            .negativeButtonTextColor(negativeButtonTextColor)
            .content(dialogLayoutBinding.root)
            .positiveButtonClick {
                Toast.makeText(
                    context,
                    "positive Click",
                    Toast.LENGTH_SHORT
                ).show()
            }
            .negativeButtonClick {
                Toast.makeText(
                    context,
                    "negative Click",
                    Toast.LENGTH_SHORT
                ).show()
            }
            .setCancelable(true)
            .build()
    }


    private fun setChipClicks(chip: PrimaryChip) {
        chip.setOnClickListener { chip.isSelected = !chip.isSelected }
    }

    fun hideSoftInput() {
        val view: View? = currentFocus
        if (view != null) {
            (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}