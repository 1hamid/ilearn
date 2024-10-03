package ir.hamid.ilearn


import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ir.hamid.model.QueryResult
import ir.hamid.viewmodel.W504ViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LearnedWordsScreen(wordViewModel: W504ViewModel) {
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
        content = { innerPadding -> GetLearnedWords(wordViewModel, innerPadding) })
}

@Composable
fun GetLearnedWords(wordViewModel: W504ViewModel, innerPadding: PaddingValues) {
    wordViewModel.fetchLearnedWords()
    val words by wordViewModel.learnedWords.observeAsState()

    if (words.isNullOrEmpty()) {
        Loading()
    } else {
        WordsList(words!!, innerPadding)
    }
}


@Composable
fun WordsList(words: List<QueryResult>, innerPadding: PaddingValues) {
    LazyRow(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 20.dp)
            .padding(innerPadding)
    ) {
        items(words) { item ->
            Text(
                text = item.word,
                fontSize = 15.sp,
                modifier = Modifier.padding(start = 20.dp, top = 5.dp),
                fontWeight = FontWeight.Bold
            )
        }
    }
}