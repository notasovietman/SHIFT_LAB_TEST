package com.example.shift_lab.View

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.shift_lab.Presenter.SignUpPresenter
import com.example.shift_lab.R
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.activity_main.*
import ru.tinkoff.decoro.MaskImpl
import ru.tinkoff.decoro.parser.UnderscoreDigitSlotsParser
import ru.tinkoff.decoro.watchers.FormatWatcher
import ru.tinkoff.decoro.watchers.MaskFormatWatcher
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity(), ISignUpView {

    private val presenter = SignUpPresenter(this)
    lateinit var sharedPreferences: SharedPreferences
    var isRemembered: Boolean = false
    lateinit var edList: Array<EditText?>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setEditTextMask()

        edList = arrayOf(
            edt_name,
            edt_last_name,
            edt_birthday,
            edt_password,
            edt_conf_password
        )

        for (editText in edList) editText?.addTextChangedListener(textWatcher)

        sharedPreferences = getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
        isRemembered = sharedPreferences.getBoolean("CHECKBOX", false)

        if (isRemembered)
            onSignUpSuccess()

        datePicker_view.setOnClickListener { showDatePickerDialog() }

        btn_register.setOnClickListener { validateUserData() }
    }

    private val textWatcher = object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {
            for (editText in edList) {
                if (editText?.text.toString().trim().isEmpty()) {
                    btn_register.isEnabled = false
                    break
                } else btn_register.isEnabled = true
            }
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }

    override fun onSignUpSuccess() {
        val intent = Intent(this, SignUpResultActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun saveUserPref(name: String, last_name: String) {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString("NAME", name)
        editor.putString("LAST_NAME", last_name)
        editor.putBoolean("CHECKBOX", true)
        editor.apply()
    }

    override fun showAllError(errorList: ArrayList<Int?>) {
        showError(errorList[0], name_input_layout)
        showError(errorList[1], last_name_input_layout)
        showError(errorList[2], birthday_input_layout)
        showError(errorList[3], password_input_layout)
        showError(errorList[4], conf_password_input_layout)
    }

    override fun validateUserData() {
        presenter.validateUserData(
            edt_name.text.toString(),
            edt_last_name.text.toString(),
            edt_birthday.text.toString(),
            edt_password.text.toString(),
            edt_conf_password.text.toString()
        )
    }

    private fun showError(error: Int?, inputLayout: TextInputLayout) {
        if (error != null) {
            inputLayout.error = getString(error);
        } else {
            inputLayout.error = getString(R.string.correct);
        }
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()

        val dateSetListener =
            DatePickerDialog.OnDateSetListener {_, year, monthOfYear, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, monthOfYear)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                val sdf = SimpleDateFormat("dd.MM.yyyy", Locale.US)
                edt_birthday.setText(sdf.format(calendar.time))
            }

        DatePickerDialog(
            this@MainActivity, dateSetListener,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private fun setEditTextMask() {
        val slots = UnderscoreDigitSlotsParser().parseSlots("__.__.____")
        val formatWatcher: FormatWatcher = MaskFormatWatcher(MaskImpl.createTerminated(slots))
        formatWatcher.installOn(findViewById(R.id.edt_birthday))
    }

}