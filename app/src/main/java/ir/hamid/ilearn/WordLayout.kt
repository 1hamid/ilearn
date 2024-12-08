package ir.hamid.ilearn

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
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
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ir.hamid.model.QueryResult

@Composable
fun WordLayout(words: List<QueryResult>, index: Int, showAnswer: Boolean, modifier: Modifier) {

    var isPlaySound by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
        .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = words[index].word,
            modifier = Modifier.padding(bottom = 10.dp),
            fontSize = 40.sp
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 30.dp, start = 20.dp, end = 30.dp)
        ) {
            Text(
                text = if (showAnswer) words[index].pronunciation else "",
                modifier = Modifier.padding(end = 5.dp)
            )
            Box(modifier = Modifier
                .width(20.dp)
                .height(20.dp)
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }) {
                    isPlaySound = true
                }
            ) {
                Image(
                    painter = painterResource(id = R.drawable.play),
                    contentDescription = "play sound"
                )
            }
        }
        Text(
            text = if (showAnswer) words[index].definition else "",
            modifier = Modifier.padding(bottom = 20.dp, start = 20.dp, end = 20.dp),
            fontSize = 20.sp
        )
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            Text(
                text = if (showAnswer) words[index].translate else "",
                modifier = Modifier.padding(bottom = 20.dp, start = 20.dp, end = 20.dp),
                fontSize = 20.sp
            )
        }
        val sample = words[index].sample.replace(Regex("(b\\.|c\\.)"), "\n$1")
        Text(
            text = if (showAnswer) sample else "",
            modifier = Modifier.padding(bottom = 20.dp, start = 20.dp, end = 20.dp),
            fontSize = 20.sp
        )
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            Text(
                text = if (showAnswer) words[index].code else "",
                modifier = Modifier.padding(start = 20.dp, end = 20.dp)
            )
        }
    }

    if (isPlaySound) {
        PlaySound(words[index].word)
        isPlaySound = false
    }
}