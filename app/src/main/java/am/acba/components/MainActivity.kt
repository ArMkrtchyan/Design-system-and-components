package am.acba.components

import am.acba.component.badge.PrimaryBadge
import am.acba.component.databinding.DialogContentTestBinding
import am.acba.component.dialog.PrimaryAlertDialog
import am.acba.component.exchange.ExchangeRate
import am.acba.component.extensions.getColorFromAttr
import am.acba.component.extensions.getColorStateListFromAttr
import am.acba.component.input.PrimaryInput
import am.acba.components.databinding.ActivityMainBinding
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    companion object {
        var darkTheme = false
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
        setSupportActionBar(toolbar)

        listItem.showBadge()
        listItem.badge.setBadgeType(PrimaryBadge.BadgeType.TEXT)
        listItem.badge.setBadgeText("Առաջարկ")
        listItem.badge.setBadgeTextColor(this@MainActivity.getColorFromAttr(am.acba.component.R.attr.contentWarning))
        listItem.badge.setBadgeBackgroundTint(this@MainActivity.getColorStateListFromAttr(am.acba.component.R.attr.backgroundWarning))

        input.apply {
            setEndIconOnClickListener {
                Toast.makeText(
                    this@MainActivity,
                    "Click",
                    Toast.LENGTH_SHORT
                ).show()
            }
            setStartIconOnClickListener {
                Toast.makeText(
                    this@MainActivity,
                    "Click",
                    Toast.LENGTH_SHORT
                ).show()
            }
            setOnFocusChangeListener { v, hasFocus ->

            }
        }
        buttonSec.apply {
            setOnClickListener {
                input.apply {
                    isErrorEnabled = false
                }
            }
        }
        buttonPr.apply {
            setOnClickListener {
                showPrimaryAlertDialog()
            }
        }
        buttonGhost.apply {
            setOnClickListener {
                this@MainActivity.findViewById<PrimaryInput>(R.id.input).apply {
                    clearFocus()
                }
            }
        }
        exchangeRates.apply {
            setOnClickListener {
                Toast.makeText(this@MainActivity, "Click", Toast.LENGTH_SHORT).show()
            }
            val firstRate =
                ExchangeRate(am.acba.component.R.drawable.flag_usa, "$ 406.00", "$ 410.50")
            val secondRate =
                ExchangeRate(am.acba.component.R.drawable.flag_russian, "₽ 4.30", "₽ 4.72")
            val thirdRate =
                ExchangeRate(am.acba.component.R.drawable.flag_usa, "€ 435.50", "€ 452.00")
            val rates = Triple(firstRate, secondRate, thirdRate)
            setExchangeRates(rates)
        }
        switcher.setOnCheckedChangeListener { buttonView, isChecked ->
            if (buttonView.isPressed) {
                darkTheme = isChecked
                recreate()
            }
        }
        search2.setOnClickListener {
            Toast.makeText(this@MainActivity, "Click", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showPrimaryAlertDialog() {

        val positiveButtonTextColor =
            this@MainActivity.getColorStateListFromAttr(am.acba.component.R.attr.contentBrandTonal1)
        val negativeButtonTextColor =
            this@MainActivity.getColorStateListFromAttr(am.acba.component.R.attr.contentDangerTonal1)

        //Create any xml file in FrameLayout and add in Alertdialog as content
        val dialogLayoutBinding = DialogContentTestBinding.inflate(layoutInflater)

        PrimaryAlertDialog.Builder(this@MainActivity)
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
                    this@MainActivity,
                    "positive Click",
                    Toast.LENGTH_SHORT
                ).show()
            }
            .negativeButtonClick {
                Toast.makeText(
                    this@MainActivity,
                    "negative Click",
                    Toast.LENGTH_SHORT
                ).show()
            }
            .setCancelable(true)
            .build()

    }
}