package am.acba.components

import am.acba.component.button.PrimaryButton
import am.acba.component.input.PrimaryInput
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.constraintlayout.widget.ConstraintHelper
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible

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
            setEndIconOnClickListener { Toast.makeText(this@MainActivity, "Click", Toast.LENGTH_SHORT).show() }
            setStartIconOnClickListener { Toast.makeText(this@MainActivity, "Click", Toast.LENGTH_SHORT).show() }
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
        findViewById<PrimaryButton>(R.id.buttonGhost).apply {
            setOnClickListener {
                this@MainActivity.findViewById<PrimaryInput>(R.id.input).apply {
                    clearFocus()
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