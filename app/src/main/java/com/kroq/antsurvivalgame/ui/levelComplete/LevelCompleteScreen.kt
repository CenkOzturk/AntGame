package com.kroq.antsurvivalgame.ui.levelComplete

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kroq.antsurvivalgame.ui.menu.MenuButton
import com.kroq.antsurvivalgame.ui.upgrade.UpgradeSection
import com.kroq.antsurvivalgame.upgrade.UpgradeType

@Composable
fun LevelCompleteScreen(
    level: Int,
    score: Int,
    onContinue: () -> Unit,
    onUpgradeSelected: (UpgradeType) -> Unit
) {
    val isUpgradeLevel = remember { level % 5 == 0 } // her 5 levelda upgrade

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "LEVEL $level COMPLETE 🎉",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Score: $score",
                fontSize = 20.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (isUpgradeLevel) {
                // Upgrade seçeneklerini göster
                UpgradeSection(
                    onUpgradeSelected = {
                        onUpgradeSelected(it)
                        onContinue() // upgrade seçildiğinde devam et
                    }
                )
            } else {
                // Normal devam et butonu
                MenuButton(
                    modifier = Modifier.padding(top = 8.dp),
                    text = "Devam Et",
                    onClick = onContinue
                )
            }
        }
    }
}