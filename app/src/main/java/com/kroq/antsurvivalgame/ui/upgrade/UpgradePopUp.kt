package com.kroq.antsurvivalgame.ui.upgrade

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kroq.antsurvivalgame.upgrade.UpgradeType

@Composable
fun UpgradePopup(
    onUpgradeSelected: (UpgradeType) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.6f)),
        contentAlignment = Alignment.Center
    ) {

        Card(
            modifier = Modifier
                .fillMaxWidth(0.9f),
            shape = RoundedCornerShape(24.dp)
        ) {

            Column(
                modifier = Modifier
                    .background(Color.Blue)
                    .padding(horizontal = 24.dp, vertical = 40.dp),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    color = Color.White,
                    text = "Choose Your Upgrade",
                    style = MaterialTheme.typography.headlineSmall
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    UpgradeButton(
                        modifier = Modifier
                            .weight(1f),
                        title = "Damage Up",
                        description = "Increase tap damage",
                        onClick = { onUpgradeSelected(UpgradeType.ADD_SOLDIER) }
                    )

                    UpgradeButton(
                        modifier = Modifier
                            .weight(1f),
                        title = "Chain Hit",
                        description = "Hits bounce to nearest ant",
                        onClick = { onUpgradeSelected(UpgradeType.CHAIN_HIT) }
                    )

                    UpgradeButton(
                        modifier = Modifier
                            .weight(1f),
                        title = "Double Damage",
                        description = "Hits x2 damage",
                        onClick = { onUpgradeSelected(UpgradeType.DOUBLE_DAMAGE) }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UpgradePopupPreview() {
    MaterialTheme {
        UpgradePopup(
            onUpgradeSelected = {}
        )
    }
}