package com.senya.pinandtiuchid.presentation.app.compoents

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.senya.pinandtiuchid.presentation.app.HomeAction
import com.senya.pinandtiuchid.presentation.app.HomeState
import com.senya.pinandtiuchid.presentation.app.HomeViewModel
import com.senya.pinandtiuchid.ui.theme.PinAndTiuchIdTheme

@Composable
fun PinCode(state: HomeState, onAction: (HomeAction) -> Unit) {
    val listNumbers = listOf(
        state.numberList.subList(0, 3),
        state.numberList.subList(3, 6),
        state.numberList.subList(6, 9),
    )

    Column() {
        Text(text = "Enter pin code: 1234")

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 50.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier
                    .padding(bottom = 70.dp)
                    .height(50.dp)
                    .width(130.dp)
                    .align(Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically
            ) {
                state.enteredNumbers.forEach {
                    Text(
                        text = it.toString(),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 10.dp)
                    )
                }
            }
            listNumbers.forEach { subList ->
                Row(
                    horizontalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp)
                ) {
                    subList.forEach { digit ->
                        Digit(digit = digit, onClick = { onAction(HomeAction.PressDigit(digit)) })
                    }
                }
            }

            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp)
            ) {
                Column(Modifier.size(80.dp)) {

                }
                Digit(digit = 0, onClick = { onAction(HomeAction.PressDigit(0)) })

                Surface(
                    shape = CircleShape,
                    modifier = Modifier.size(80.dp),
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .clickable { onAction(HomeAction.DeleteLastDigit) },
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            painter = painterResource(id = com.senya.pinandtiuchid.R.drawable.backspace),
                            contentDescription = "backspace"
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PinCodePreview() {
    PinAndTiuchIdTheme {
        PinCode(state = HomeState(), onAction = {})
    }
}