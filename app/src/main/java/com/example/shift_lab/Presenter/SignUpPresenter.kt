package com.example.shift_lab.Presenter

import com.example.shift_lab.Model.User
import com.example.shift_lab.View.ISignUpView

class SignUpPresenter(var iSignUpView: ISignUpView?) : ISignUpPresenter {

    override lateinit var errorList: ArrayList<Int?>
    lateinit var user: User

    override fun onSignUpSuccess() {
        iSignUpView?.saveUserPref(user.name, user.lastName)
        iSignUpView?.onSignUpSuccess()
    }

    override fun onSignUpFailed() {
        iSignUpView?.apply {
            showAllError(errorList)
        }
    }

    override fun validateUserData(
        name: String,
        lastName: String,
        birthday: String,
        password: String,
        confPassword: String
    ) {
        user = User(name, lastName, birthday, password, confPassword)
        errorList = user.isDataValid()
        if (errorList == arrayListOf(null, null, null, null, null)) {
            onSignUpSuccess()
        } else {
            onSignUpFailed()
        }
    }

    fun onDestroy() {
        iSignUpView = null
    }
}