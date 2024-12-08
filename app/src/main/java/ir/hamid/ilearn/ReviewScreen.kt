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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import ir.hamid.ilearn.IlearnApplication.Companion.getStartOfDayTimestamp
import ir.hamid.model.QueryResult
import ir.hamid.viewmodel.W504ViewModel
import kotlin.math.pow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReviewScreen(wordViewModel: W504ViewModel, navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(
                    text = "iLearn",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 20.sp
                )
            })
        }, content = { innerPadding ->
            GetDataForReview(wordViewModel, innerPadding, navController)
        }
    )
}

@Composable
fun GetDataForReview(
    wordViewModel: W504ViewModel,
    innerPadding: PaddingValues,
    navController: NavHostController
) {
    val date = System.currentTimeMillis() / 1000
    LaunchedEffect(Unit) {
        wordViewModel.fetchWordsByDate(date.toInt())

    }
    val reviewWords by wordViewModel.reviewWords.observeAsState()
    if (reviewWords.isNullOrEmpty()) {
        Loading()
    } else {
        ReviewLayout(innerPadding, reviewWords!!, wordViewModel, navController)
    }
}


@Preview(showBackground = true)
@Composable
private fun PreviewReviewLayout() {
    val sampleWords: List<QueryResult> = listOf(
        QueryResult(
            id = 0,
            word = "word",
            code = "code",
            pronunciation = "prounuciation",
            sample = "sample",
            definition = "definition",
            translate = "translate"
        )
    )
    ReviewLayout(innerPadding = PaddingValues(5.dp), sampleWords, null, null)
}

@Composable
fun ReviewLayout(
    innerPadding: PaddingValues,
    words: List<QueryResult>,
    wordViewModel: W504ViewModel?,
    navController: NavHostController?
) {
    var index by remember { mutableIntStateOf(0) }
    var showAnswer by remember { mutableStateOf(false) }
    var isPlaySound by remember { mutableStateOf(false) }
    Column(modifier = Modifier.fillMaxSize()) {
        WordLayout(
            words, index, showAnswer,
            Modifier
                .fillMaxWidth()
                .weight(70f)
                .padding(top = innerPadding.calculateTopPadding())
        )
        Row(
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .padding(top = 50.dp, bottom = 20.dp)
                .weight(15f)
        ) {
            Button(
                modifier = Modifier
                    .padding(start = 10.dp, end = 5.dp)
                    .height(50.dp)
                    .weight(1f),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.cardColor)),

                onClick = {
                    showAnswer = false
                    updateReviewDate(words[index].id, 1.0, wordViewModel)
                    if (index < words.size - 1) {
                        index++
                    } else {
                        navController!!.popBackStack()
                    }
                }
            ) {
                Text(text = "1", color = Color.Black)
            }
            Button(
                modifier = Modifier
                    .padding(start = 5.dp, end = 5.dp)
                    .height(50.dp)
                    .weight(1f),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.cardColor)),
                onClick = {
                    showAnswer = false
                    updateReviewDate(words[index].id, 2.0, wordViewModel)
                    if (index < words.size - 1) {
                        index++
                    } else {
                        navController!!.popBackStack()
                    }
                }
            ) {
                Text(text = "2", color = Color.Black)
            }
            Button(
                modifier = Modifier
                    .padding(start = 5.dp, end = 5.dp)
                    .height(50.dp)
                    .weight(1f),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.cardColor)),
                onClick = {
                    showAnswer = false
                    updateReviewDate(words[index].id, 3.0, wordViewModel)
                    if (index < words.size - 1) {
                        index++
                    } else {
                        navController!!.popBackStack()
                    }
                }
            ) {
                Text(text = "3", color = Color.Black)
            }
            Button(
                modifier = Modifier
                    .padding(start = 5.dp, end = 5.dp)
                    .height(50.dp)
                    .weight(1f),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.cardColor)),
                onClick = {
                    showAnswer = false
                    updateReviewDate(words[index].id, 4.0, wordViewModel)
                    if (index < words.size - 1) {
                        index++
                    } else {
                        navController!!.popBackStack()
                    }
                }
            ) {
                Text(text = "4", color = Color.Black)
            }
            Button(
                modifier = Modifier
                    .padding(start = 5.dp, end = 10.dp)
                    .height(50.dp)
                    .weight(1f),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.cardColor)),
                onClick = {
                    updateReviewDate(words[index].id, 5.0, wordViewModel)
                    showAnswer = false
                    if (index < words.size - 1) {
                        index++
                    } else {
                        navController!!.popBackStack()
                    }
                }
            ) {
                Text(text = "5", color = Color.Black)
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(15f)
        ) {
            Button(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .padding(bottom = 50.dp)
                    .fillMaxWidth()
                    .fillMaxHeight(0.6f),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.submit)),
                onClick = {
                    showAnswer = true
                }
            ) {
                Text(text = "Show answer", color = Color.Black)
            }
        }

    }

    if (isPlaySound) {
        PlaySound(words[index].word)
        isPlaySound = false
    }
}

fun updateReviewDate(id: Int, level: Double, wordViewModel: W504ViewModel?) {
    val date = getStartOfDayTimestamp() + ((level.pow(2)) * 24 * 60 * 60)
    wordViewModel!!.updateReviewDate(date.toInt(), level.toInt(), id)
}

