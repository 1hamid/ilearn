package ir.hamid.ilearn

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.gson.Gson
import ir.hamid.viewmodel.W504ViewModel
import java.net.URLEncoder

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(wordViewModel: W504ViewModel, navController: NavHostController) {
    Scaffold(topBar = {
        TopAppBar(title = {
            Text(
                text = "iLearn", maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontSize = 20.sp
            )
        })
    }, content = { innerPadding -> TabsLayout(innerPadding, wordViewModel, navController) })
}

@Composable
fun TabsLayout(
    innerPaddingValues: PaddingValues,
    wordViewModel: W504ViewModel,
    navController: NavHostController
) {

    var tabIndex by remember { mutableIntStateOf(0) }
    val tabTitles = listOf("by Word", "by Translate", "by Sample")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPaddingValues)
    ) {
        TabRow(
            selectedTabIndex = tabIndex,
            contentColor = colorResource(id = R.color.black)
        ) {
            tabTitles.forEachIndexed { index, title ->
                Tab(
                    text = { Text(text = title) },
                    onClick = { tabIndex = index },
                    selected = tabIndex == index
                )
            }
        }

        when (tabIndex) {
            0 -> TabContent("word", wordViewModel, navController)
            1 -> TabContent("translate", wordViewModel, navController)
            2 -> TabContent("sample", wordViewModel, navController)
        }
    }
}

@Composable
fun TabContent(type: String, wordViewModel: W504ViewModel, navController: NavHostController) {

    var str by remember { mutableStateOf("") }
    var search by remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(top = 5.dp, start = 5.dp, end = 5.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.1f)
        ) {
            TextField(modifier = Modifier
                .fillMaxHeight()
                .weight(0.8f),
                value = str,
                onValueChange = { newText -> str = newText },
                label = { Text(text = "Search") })
            Button(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(0.2f),
                onClick = {
                    search = true
                    str = str.replace('ی', 'ي')
                    wordViewModel.fetchWordsBySearch(type, str)
                },
                shape = RectangleShape,
            ) {}
        }

        if (search) {

            val wordsResult by wordViewModel.searchedWords.observeAsState()
            if (!wordsResult.isNullOrEmpty()) {

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 5.dp, start = 5.dp, end = 5.dp)
                ) {
                    items(wordsResult!!) { item ->
                        Box(modifier = Modifier.clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() })
                        {
                            val itemJson = Gson().toJson(item)
                            val encodedItemJson = URLEncoder.encode(itemJson, "UTF-8")
                            navController.navigate(DataSource.IlearnScreens.WordScreen.name + "/$encodedItemJson")
                        }) {
                            Text(
                                text = item.word + "  |  " + item.translate,
                                fontSize = 18.sp,
                                modifier = Modifier.padding(top = 7.dp, bottom = 7.dp),
                            )
                            HorizontalDivider()
                        }


                    }
                }
            }
        }
    }

}