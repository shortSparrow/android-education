package com.senya.pinandtiuchid.presentation.app


data class HomeState(
    val numberList: List<Int> = listOf(1,2,3,4,5,6,7,8,9),
    val enteredNumbers:List<Int> = emptyList(),
    val isAuthorized: Boolean = false,
    val isAuthorizeByPassword: Boolean = false,

)

sealed interface HomeAction {
    data class PressDigit(val digit: Int): HomeAction
    object DeleteLastDigit: HomeAction
    object OpenBiometricDialog: HomeAction
}