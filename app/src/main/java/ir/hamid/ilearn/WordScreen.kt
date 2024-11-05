package ir.hamid.ilearn

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ir.hamid.model.QueryResult2

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WordsScreen(word: QueryResult2) {
    Scaffold(topBar = {
        TopAppBar(title = {
            Text(
                text = "iLearn",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontSize = 20.sp
            )
        })
    },
        content = { innerPadding ->
            WordDetail(word, innerPadding)
        })
}


@Composable
fun WordDetail(word: QueryResult2, innerPadding: PaddingValues) {

    var isPlaySound by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = innerPadding.calculateTopPadding()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = word.word,
            modifier = Modifier.padding(bottom = 10.dp),
            fontSize = 40.sp
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 30.dp, start = 20.dp, end = 30.dp)
        ) {
            Text(
                text = word.pronunciation,
                modifier = Modifier.padding(end = 5.dp)
            )
            Box(modifier = Modifier
                .width(20.dp)
                .height(20.dp)
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() })
                {
                    isPlaySound = true
                }) {
                Image(
                    painter = painterResource(id = R.drawable.play),
                    contentDescription = "play sound"
                )
            }
        }
        Text(
            text = word.definition,
            modifier = Modifier.padding(bottom = 20.dp, start = 20.dp, end = 20.dp),
            fontSize = 20.sp
        )
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            Text(
                text = word.translate,
                modifier = Modifier.padding(bottom = 20.dp, start = 20.dp, end = 20.dp),
                fontSize = 20.sp
            )
        }
        val sample = word.sample.replace(Regex("(b\\.|c\\.)"), "\n$1")
        Text(
            text = sample,
            modifier = Modifier.padding(bottom = 20.dp, start = 20.dp, end = 20.dp),
            fontSize = 20.sp
        )
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            Text(text = word.code, modifier = Modifier.padding(start = 20.dp, end = 20.dp))
        }
    }

    if (isPlaySound) {
        PlaySound(word.word)
        isPlaySound = false
    }
}