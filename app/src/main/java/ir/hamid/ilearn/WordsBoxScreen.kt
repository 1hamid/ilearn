package ir.hamid.ilearn

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import ir.hamid.viewmodel.W504ViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WordsBoxScreen(wordViewModel: W504ViewModel, navController: NavHostController) {
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
            WordsBoxView(innerPadding, wordViewModel, navController)
        })
}

@Composable
fun WordsBoxView(innerPadding: PaddingValues, wordViewModel: W504ViewModel, navController: NavHostController) {

    var boxNumber by remember { mutableIntStateOf(0) }

    if (boxNumber == 0) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(1f / 2f)
                    .padding(innerPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(start = 20.dp, end = 20.dp),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .padding(10.dp),
                        shape = RoundedCornerShape(50f),
                        colors = CardColors(
                            containerColor = colorResource(id = R.color.cardColor),
                            contentColor = colorResource(id = R.color.cardColor),
                            disabledContainerColor = Color.Gray,
                            disabledContentColor = Color.DarkGray
                        ),
                        onClick = {
                            boxNumber = 1
                            navController.navigate(DataSource.IlearnScreens.WordsList.name + "/$boxNumber")
                        }
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "1",
                                fontSize = 20.sp,
                                fontFamily = FontFamily(Font(R.font.kanitmedium)),
                                color = Color.Black
                            )
                        }
                    }
                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .padding(10.dp),
                        shape = RoundedCornerShape(50f),
                        colors = CardColors(
                            containerColor = colorResource(id = R.color.cardColor),
                            contentColor = colorResource(id = R.color.cardColor),
                            disabledContainerColor = Color.Gray,
                            disabledContentColor = Color.DarkGray
                        ),
                        onClick = {
                            boxNumber = 2
                            navController.navigate(DataSource.IlearnScreens.WordsList.name + "/$boxNumber")
                        }
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "2",
                                fontSize = 20.sp,
                                fontFamily = FontFamily(Font(R.font.kanitmedium)),
                                color = Color.Black
                            )
                        }
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(start = 20.dp, end = 20.dp),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .padding(10.dp),
                        shape = RoundedCornerShape(50f),
                        colors = CardColors(
                            containerColor = colorResource(id = R.color.cardColor),
                            contentColor = colorResource(id = R.color.cardColor),
                            disabledContainerColor = Color.Gray,
                            disabledContentColor = Color.DarkGray
                        ),
                        onClick = {
                            boxNumber = 3
                            navController.navigate(DataSource.IlearnScreens.WordsList.name + "/$boxNumber")
                        }
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "3",
                                fontSize = 20.sp,
                                fontFamily = FontFamily(Font(R.font.kanitmedium)),
                                color = Color.Black
                            )
                        }
                    }
                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .padding(10.dp),
                        shape = RoundedCornerShape(50f),
                        colors = CardColors(
                            containerColor = colorResource(id = R.color.cardColor),
                            contentColor = colorResource(id = R.color.cardColor),
                            disabledContainerColor = Color.Gray,
                            disabledContentColor = Color.DarkGray
                        ),
                        onClick = {
                            boxNumber = 4
                            navController.navigate(DataSource.IlearnScreens.WordsList.name + "/$boxNumber")
                        }
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "4",
                                fontSize = 20.sp,
                                fontFamily = FontFamily(Font(R.font.kanitmedium)),
                                color = Color.Black
                            )
                        }
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(start = 20.dp, end = 20.dp),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .padding(10.dp),
                        shape = RoundedCornerShape(50f),
                        colors = CardColors(
                            containerColor = colorResource(id = R.color.cardColor),
                            contentColor = colorResource(id = R.color.cardColor),
                            disabledContainerColor = Color.Gray,
                            disabledContentColor = Color.DarkGray
                        ),
                        onClick = {
                            boxNumber = 5
                            navController.navigate(DataSource.IlearnScreens.WordsList.name + "/$boxNumber")
                        }
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "5",
                                fontSize = 20.sp,
                                fontFamily = FontFamily(Font(R.font.kanitmedium)),
                                color = Color.Black
                            )
                        }
                    }
                }
            }
        }
    }
}