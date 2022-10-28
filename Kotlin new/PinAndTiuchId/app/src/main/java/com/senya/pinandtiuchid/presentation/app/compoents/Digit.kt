package com.senya.pinandtiuchid.presentation.app.compoents

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.senya.pinandtiuchid.ui.theme.PinAndTiuchIdTheme

@Composable
fun Digit(digit: Int, onClick: () -> Unit) {
    Surface(
        shape = CircleShape,
        modifier = Modifier.size(80.dp),
        color = Color.LightGray,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .clickable { onClick() },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = digit.toString(),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DigitPreview() {
    PinAndTiuchIdTheme {
        Column() {
            Digit(1, onClick = {})
        }
    }
}