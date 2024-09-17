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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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


//@Preview(showBackground = true)
//@Composable
//private fun PreviewLayout() {
//    Layout(innerPadding = PaddingValues(5.dp))
//}

@Composable
private fun Layout(innerPadding: PaddingValues, words: List<QueryResult>) {
    var index by remember { mutableIntStateOf(0) }
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
            fontSize = 30.sp
        )
        Text(text = words[index].pronunciation, modifier = Modifier.padding(bottom = 30.dp, start = 20.dp, end = 20.dp))
        Text(text = words[index].definition, modifier = Modifier.padding(bottom = 20.dp, start = 20.dp, end = 20.dp), fontSize = 20.sp)
        Text(
            text = words[index].translate,
            modifier = Modifier.padding(bottom = 20.dp, start = 20.dp, end = 20.dp),
            fontSize = 20.sp
        )
        Text(text = words[index].sample, modifier = Modifier.padding(bottom = 20.dp, start = 20.dp, end = 20.dp), fontSize = 20.sp)
        Text(text = words[index].code, modifier = Modifier.padding(start = 20.dp, end = 20.dp))
        Row(modifier = Modifier.padding(50.dp)) {
            Button(
                modifier = Modifier
                    .padding(15.dp)
                    .fillMaxWidth()
                    .weight(1f),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.cardColor)),

                onClick = {
                    if (index > 0) {
                        index--
                    }
                }) {
                Text(text = "Previous", color = Color.Black)
            }
            Button(
                modifier = Modifier
                    .padding(15.dp)
                    .fillMaxWidth()
                    .weight(1f),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.cardColor)),
                onClick = {
                    if (index < words.size - 1) {
                        index++
                    }
                }) {
                Text(text = "Next", color = Color.Black)
            }
        }
    }
}

@Composable
private fun GetData(
    wordViewModel: W504ViewModel,
    innerPadding: PaddingValues
) {
    wordViewModel.fetchDataByDate(null)
    val words by wordViewModel.words.observeAsState()
    if (words.isNullOrEmpty()) {
        Loading()
    } else {
        Layout(innerPadding, words!!)
    }
}

//@Preview
@Composable
fun Loading() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Loading . . .",
            fontSize = 25.sp,
            modifier = Modifier.padding(100.dp)

        )
    }
}