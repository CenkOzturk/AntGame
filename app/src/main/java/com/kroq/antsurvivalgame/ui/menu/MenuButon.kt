package com.kroq.antsurvivalgame.ui.menu

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MenuButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit) {
    Button(
        onClick = { onClick() },
        modifier = modifier
            .fillMaxWidth(0.7f)
            .height(52.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Yellow
        )
    ) {
        Text(
            text = text,
            color = Color.Blue,
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium
        )
    }
}
