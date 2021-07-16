package com.example.tipcalculator

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.tipcalculator.databinding.ActivityMainBinding
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var komunikat = Toast.makeText(applicationContext, "Wypełnij dane", Toast.LENGTH_SHORT)

        binding.button.setOnClickListener {

            obliczNapiwek(komunikat)
        }
    }

    fun obliczNapiwek(kom: Toast): Boolean {
        try {
            if (binding.radioGroup.getCheckedRadioButtonId() == -1 || binding.costOfService.getText().toString().trim().isEmpty())

             {
                popUp(kom)
                return false

            }
        } catch (e: NumberFormatException) {
            popUp(kom)
            return false
        }
        val tipPercent = when (binding.radioGroup.checkedRadioButtonId) {
            R.id.ten_percent -> 0.10
            R.id.fifteen_percent -> 0.15
            else -> 0.2
        }
        val roundUp = binding.roundUpSwitch.isChecked
        var tip = (binding.costOfService.text.toString().toFloat() * tipPercent)


        if (roundUp) {
            tip = kotlin.math.ceil(tip)
        }
        val formattedTip = NumberFormat.getCurrencyInstance().format(tip)
        binding.wynik.text = formattedTip.toString()
        hideKeyboard()
        return true
    }

    fun popUp(kom: Toast) {
        kom.cancel()
        kom.show()
    }

    fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }

    fun Activity.hideKeyboard() {
        hideKeyboard(currentFocus ?: View(this))
    }

    fun Context.hideKeyboard(view: View) {
        val inputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

}
