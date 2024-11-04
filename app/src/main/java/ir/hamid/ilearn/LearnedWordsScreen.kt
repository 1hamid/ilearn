package ir.hamid.ilearn


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ir.hamid.model.QueryResult2
import ir.hamid.viewmodel.W504ViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LearnedWordsScreen(wordViewModel: W504ViewModel, box: Int) {
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
            GetLearnedWords(wordViewModel, box, innerPadding)
        })
}

@Composable
fun GetLearnedWords(wordViewModel: W504ViewModel, box: Int, innerPadding: PaddingValues) {
    wordViewModel.fetchLearnedWordsByBox(box)
    val words by wordViewModel.learnedWords.observeAsState()

    if (words.isNullOrEmpty()) {
        Loading()
    } else {
        WordsList(words!!, innerPadding)
    }
}

@Composable
fun WordsList(words: List<QueryResult2>, innerPadding: PaddingValues) {

    var index by remember { mutableIntStateOf(0) }
    var nextButtonState by remember { mutableStateOf(true) }
    var previousButtonState by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = innerPadding.calculateTopPadding()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = words[index].word,
            modifier = Modifier.padding(bottom = 10.dp),
            fontSize = 40.sp
        )
        Text(
            text = words[index].pronunciation,
            modifier = Modifier.padding(bottom = 30.dp, start = 20.dp, end = 20.dp)
        )
        Text(
            text = words[index].definition,
            modifier = Modifier.padding(bottom = 20.dp, start = 20.dp, end = 20.dp),
            fontSize = 20.sp
        )
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            Text(
                text = words[index].translate,
                modifier = Modifier.padding(bottom = 20.dp, start = 20.dp, end = 20.dp),
                fontSize = 20.sp
            )
        }
        Text(
            text = words[index].sample,
            modifier = Modifier.padding(bottom = 20.dp, start = 20.dp, end = 20.dp),
            fontSize = 20.sp
        )
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            Text(text = words[index].code, modifier = Modifier.padding(start = 20.dp, end = 20.dp))
        }
        Row(modifier = Modifier.padding(start = 50.dp, top = 50.dp, end = 50.dp)) {
            Button(
                modifier = Modifier
                    .padding(15.dp)
                    .fillMaxWidth()
                    .weight(1f)
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
                    .fillMaxWidth()
                    .weight(1f)
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
    }
}