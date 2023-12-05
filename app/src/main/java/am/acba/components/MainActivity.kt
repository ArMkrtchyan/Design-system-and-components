package am.acba.components

import am.acba.component.button.PrimaryButton
import am.acba.component.input.PrimaryInput
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    companion object {
        var darkTheme = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(if (darkTheme) am.acba.component.R.style.Theme_Dark else am.acba.component.R.style.Theme_Light)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        findViewById<PrimaryInput>(R.id.input).apply {
//            isErrorEnabled = true
//            error = "Error"
            setOnFocusChangeListener { v, hasFocus ->

            }
        }
        findViewById<PrimaryButton>(R.id.buttonSec).apply {
            setOnClickListener {
                this@MainActivity.findViewById<PrimaryInput>(R.id.input).apply {
                    isErrorEnabled = false
                }
            }
        }
        findViewById<PrimaryButton>(R.id.buttonPr).apply {
            setOnClickListener {
                this@MainActivity.findViewById<PrimaryInput>(R.id.input).apply {
                    isErrorEnabled = true
                    error = "Error"
                }
            }
        }
        findViewById<SwitchCompat>(R.id.switcher).setOnCheckedChangeListener { buttonView, isChecked ->
            if (buttonView.isPressed) {
                darkTheme = isChecked
                recreate()
            }
        }
    }
}