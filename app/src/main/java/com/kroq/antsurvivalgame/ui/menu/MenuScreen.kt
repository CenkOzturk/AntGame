package com.kroq.antsurvivalgame.ui.menu

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kroq.antsurvivalgame.utils.Utils.PopupUtil
import com.kroq.antsurvivalgame.utils.Utils.ToastUtil
import com.kroq.antsurvivalgame.R

@Composable
fun MainMenuScreen(
    onNewGame: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            contentScale = ContentScale.FillHeight,
            alignment = Alignment.CenterEnd,
            painter = painterResource(R.drawable.background),
            contentDescription = ""
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            MenuButton(text = "Devam Et") {
                ToastUtil.show("Hazır değil 🚧")
            }
            MenuButton(text = "Yeni Oyun") { onNewGame() }
            MenuButton(text = "Yüksek Skor") {}
            MenuButton(text = "Hakkımda") {}
            MenuButton(text = "Çıkış") {
                PopupUtil.show("Çıkmak istediğinize emin misiniz :(")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainMenuPreview() {
    MainMenuScreen() {}
}