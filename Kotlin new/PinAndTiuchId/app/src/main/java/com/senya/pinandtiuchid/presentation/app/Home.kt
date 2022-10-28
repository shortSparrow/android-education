package com.senya.pinandtiuchid.presentation.app

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.senya.pinandtiuchid.presentation.app.compoents.PinCode
import com.senya.pinandtiuchid.ui.theme.PinAndTiuchIdTheme

@Composable
fun Home(state: HomeState, onAction: (HomeAction) -> Unit) {
    if (state.isAuthorized) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Hey, welcome to tha app!".uppercase(),
                fontSize = 20.sp
            )
        }
    } else {
        if (!state.isAuthorizeByPassword) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Button(onClick = { onAction(HomeAction.OpenBiometricDialog) }) {
                    Text(text = "PRESS TO LOGIN")
                }
            }
        } else {
            PinCode(state, onAction)
        }

    }
}

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    PinAndTiuchIdTheme {
        Home(state = HomeState(), onAction = {})
    }
}

@Preview(showBackground = true)
@Composable
fun HomePreview2() {
    PinAndTiuchIdTheme {
        Home(state = HomeState(isAuthorizeByPassword = true), onAction = {})
    }
}

@Preview(showBackground = true)
@Composable
fun HomePreview3() {
    PinAndTiuchIdTheme {
        Home(state = HomeState(isAuthorized = true), onAction = {})
    }
}