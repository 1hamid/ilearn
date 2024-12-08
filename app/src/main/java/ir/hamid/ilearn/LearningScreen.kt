package ir.hamid.ilearn

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ir.hamid.ilearn.IlearnApplication.Companion.getStartOfDayTimestamp
import ir.hamid.model.QueryResult
import ir.hamid.viewmodel.W504ViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LearningScreen(wordViewModel: W504ViewModel) {
    Scaffold(topBar = {
        TopAppBar(title = {
            Text(
                text = "iLearn",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontSize = 20.sp
            )
        })
    }, content = { innerPadding ->
        GetData(wordViewModel, innerPadding)
    })
}


@Preview(showBackground = true)
@Composable
private fun PreviewLayout() {
    val sampleWords: List<QueryResult> = listOf(
        QueryResult(
            id = 0,
            word = "word",
            code = "code",
            pronunciation = "prounuciation",
            sample = "sample",
            definition = "definition",
            translate = "translate",
        )
    )
    Layout(innerPadding = PaddingValues(5.dp), sampleWords, null)
}

@Composable
private fun Layout(
    innerPadding: PaddingValues,
    words: List<QueryResult>,
    wordViewModel: W504ViewModel?
) {
    var index by remember { mutableIntStateOf(0) }
    var nextButtonState by remember { mutableStateOf(true) }
    var previousButtonState by remember { mutableStateOf(false) }
    Column(modifier = Modifier.fillMaxSize()) {
        WordLayout(
            words, index, true, Modifier
                .fillMaxWidth()
                .weight(70f)
                .padding(top = innerPadding.calculateTopPadding())
        )
        Row(
            modifier = Modifier
                .padding(horizontal = 25.dp)
                .padding(top = 50.dp)
                .fillMaxWidth()
                .weight(15f)
        ) {
            Button(
                modifier = Modifier
                    .padding(15.dp)
                    .fillMaxHeight()
                    .weight(50f)
                    .alpha(if (previousButtonState) 1f else 0.5f),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.cardColor)),

                onClick = {
                    if (index > 0) {
                        nextButtonState = true
                        index--
                        if (index == 0)
                            previousButtonState = false
                    }
                },
                enabled = previousButtonState
            ) {
                Text(text = "Previous", color = Color.Black)
            }
            Button(
                modifier = Modifier
                    .padding(15.dp)
                    .fillMaxHeight()
                    .weight(50f)
                    .alpha(if (nextButtonState) 1f else 0.5f),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.cardColor)),
                onClick = {
                    if (index < words.size - 1) {
                        previousButtonState = true
                        index++
                        if (index == words.size - 1)
                            nextButtonState = false
                    }
                },
                enabled = nextButtonState
            ) {
                Text(text = "Next", color = Color.Black)
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(15f)
        ) {
            Button(
                modifier = Modifier
                    .padding(start = 40.dp, end = 40.dp, bottom = 50.dp)
                    .fillMaxWidth()
                    .fillMaxHeight(0.6f),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.submit)),
                onClick = {
                    insertReviewDate(words[index].id, wordViewModel)
                    if (index < words.size - 1) {
                        previousButtonState = true
                        index++
                        if (index == words.size - 1) {
                            nextButtonState = false
                        }
                    }
                }) {
                Text(text = "OK", color = Color.Black)
            }
        }
    }
}

@Composable
private fun GetData(
    wordViewModel: W504ViewModel,
    innerPadding: PaddingValues
) {
    LaunchedEffect(Unit) {
        wordViewModel.fetchNewWords()
    }
    val newWords by wordViewModel.newWords.observeAsState()
    if (newWords.isNullOrEmpty()) {
        Loading()
    } else {
        Layout(innerPadding, newWords!!, wordViewModel)
    }
}

fun insertReviewDate(id: Int, wordViewModel: W504ViewModel?) {
    val date = getStartOfDayTimestamp()
    wordViewModel!!.updateReviewDate(date, 1, id)

    val c = wordViewModel.counterData.value
    wordViewModel.saveCounter(c!! + 1)
}