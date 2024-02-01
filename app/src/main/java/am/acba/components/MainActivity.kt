package am.acba.components

import am.acba.component.button.PrimaryActionTextButton
import am.acba.component.exchange.ExchangeRate
import am.acba.component.input.PrimaryInput
import am.acba.components.databinding.ActivityMainBinding
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible

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
        input.apply {
            setEndIconOnClickListener { Toast.makeText(this@MainActivity, "Click", Toast.LENGTH_SHORT).show() }
            setStartIconOnClickListener { Toast.makeText(this@MainActivity, "Click", Toast.LENGTH_SHORT).show() }
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
                input.apply {
                    isErrorEnabled = true
                    error = "Error"
                }
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
            val firstRate = ExchangeRate(am.acba.component.R.drawable.flag_usa, "$ 406.00", "$ 410.50")
            val secondRate = ExchangeRate(am.acba.component.R.drawable.flag_russian, "₽ 4.30", "₽ 4.72")
            val thirdRate = ExchangeRate(am.acba.component.R.drawable.flag_usa, "€ 435.50", "€ 452.00")
            val rates = Triple(firstRate, secondRate, thirdRate)
            setExchangeRates(rates)
        }
        switcher.setOnCheckedChangeListener { buttonView, isChecked ->
            if (buttonView.isPressed) {
                darkTheme = isChecked
                recreate()
            }
        }
        search2.setOnClickListener { Toast.makeText(this@MainActivity, "Click", Toast.LENGTH_SHORT).show() }
    }
}