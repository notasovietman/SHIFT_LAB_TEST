package com.example.shift_lab.Presenter

interface ISignUpPresenter {

    var errorList: ArrayList<Int?>
    fun onSignUpSuccess()
    fun onSignUpFailed()
    fun validateUserData(
        name: String,
        lastName: String,
        birthday: String,
        password: String,
        confPassword: String
    )
}