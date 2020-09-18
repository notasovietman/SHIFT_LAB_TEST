package com.example.shift_lab.Model

import com.example.shift_lab.R

class User(
    _name: String, _lastName: String, _birthday: String,
    _password: String, _confPassword: String
) : IUser {
    override var name: String = _name
    override var lastName: String = _lastName
    override var birthday: String = _birthday
    override var password: String = _password
    override var confPassword: String = _confPassword

    override fun isDataValid(): ArrayList<Int?> =
        arrayListOf(
            isNameValid(),
            isLastNameValid(),
            isBirthdayValid(),
            isPasswordValid(),
            isConfPasswordValid()
        )

    override fun isNameValid(): Int? {
        val newName = name.trim()
        if (!newName[0].isUpperCase())
            return R.string.upperCase_name_error
        else if (newName.length !in (2..15))
            return R.string.wrong_name_length_error
        else return null
    }

    override fun isLastNameValid(): Int? {
        val newLastName = lastName.trim()
        if (!newLastName[0].isUpperCase())
            return R.string.upperCase_last_name_error
        else if (newLastName.length !in (3..20))
            return R.string.wrong_last_name_length_error
        else return null
    }

    override fun isBirthdayValid(): Int? {
        val newBirthday = birthday
        if (newBirthday.length == 10) {
            val newDay = newBirthday.substring(0, 2).toInt()
            val newMonth = newBirthday.substring(3, 5).toInt()
            val newYear = newBirthday.substring(6).toInt()
            if (newDay !in (1..31) || newMonth !in (1..12) || newYear !in (1900..2020)
            ) {
                return R.string.warning_2
            } else if (newDay >= 30 && newMonth == 2) {
                return R.string.warning_2
            }
            return null
        }
        return R.string.warning_2
    }

    override fun isPasswordValid(): Int? {
        val newPassword = password.trim()
        if (!newPassword.any { it.isDigit() })
            return R.string.no_digits_error
        else if (!newPassword.any { it.isLetter() })
            return R.string.no_letters_error
        else if (newPassword.length !in (6..15))
            return R.string.wrong_password_length_error
        return null
    }

    override fun isConfPasswordValid(): Int? {
        val newConfPassword = confPassword.trim()
        if (newConfPassword != password)
            return R.string.not_match_password
        return null
    }
}