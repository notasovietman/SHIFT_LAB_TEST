package com.example.shift_lab.Model

interface IUser {
    var name: String
    var lastName: String
    var birthday: String
    var password: String
    var confPassword: String

    fun isDataValid(): ArrayList<Int?>
    fun isNameValid(): Int?
    fun isLastNameValid(): Int?
    fun isBirthdayValid(): Int?
    fun isPasswordValid(): Int?
    fun isConfPasswordValid(): Int?
}