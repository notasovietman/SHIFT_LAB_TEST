package com.example.shift_lab.View

interface ISignUpView {
    fun onSignUpSuccess()
    fun saveUserPref(name:String, last_name:String)
    fun showAllError(errorList: ArrayList<Int?>)
    fun validateUserData()
}