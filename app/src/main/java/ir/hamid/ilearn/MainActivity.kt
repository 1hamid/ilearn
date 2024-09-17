package ir.hamid.ilearn

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi

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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ir.hamid.model.W504DataBase
import ir.hamid.model.W504Repository
import ir.hamid.viewmodel.W504ViewModel
import ir.hamid.viewmodel.WordViewModelFactory

class MainActivity : ComponentActivity() {

    private val database by lazy { W504DataBase.getDatabase(this) }
    private val repository by lazy { W504Repository(database.wordDao()) }

    private val wordViewModel: W504ViewModel by viewModels {
        WordViewModelFactory(repository)
    }

    @RequiresApi(Build.VERSION_CODES.R)
    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppBar()
        }

        wordViewModel.allWords.observe(this) {
            Log.i("data ", " " + wordViewModel.allWords.value)
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun AppBar() {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "iLearn",
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            fontSize = 20.sp
                        )
                    },
                )
            }
        ) { innerPadding ->
            val navController = rememberNavController()
            NavHost(navController, startDestination = DataSource.IlearnScreens.Home.name) {
                composable(route = DataSource.IlearnScreens.Home.name) {
                    AppUI(
                        innerPadding = innerPadding,
                        navController
                    )
                }
                composable(route = DataSource.IlearnScreens.Learning.name) {
                    LearningScreen(
                        wordViewModel
                    )
                }
            }
        }
    }

    @Composable
    private fun AppUI(innerPadding: PaddingValues, navController: NavController) {
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(top = 20.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Let's start",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Towards success",
                fontSize = 16.sp
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.8f)
                    .padding(top = 20.dp)
                    .padding(start = 25.dp, end = 25.dp, top = 25.dp, bottom = 5.dp),
                shape = RoundedCornerShape(50f),
                colors = CardColors(
                    containerColor = colorResource(id = R.color.cardColor),
                    contentColor = colorResource(id = R.color.cardColor),
                    disabledContainerColor = Color.Gray,
                    disabledContentColor = Color.DarkGray,
                )
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Search",
                        fontSize = 20.sp,
                        fontFamily = FontFamily(Font(R.font.kanitmedium)),
                        color = Color.Black
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.7f)
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f)
                        .padding(start = 25.dp, end = 5.dp, top = 5.dp, bottom = 25.dp),
                    onClick = { navController.navigate(DataSource.IlearnScreens.Learning.name) },
                    shape = RoundedCornerShape(50f),
                    colors = CardColors(
                        containerColor = colorResource(id = R.color.cardColor),
                        contentColor = colorResource(id = R.color.cardColor),
                        disabledContainerColor = Color.Gray,
                        disabledContentColor = Color.DarkGray
                    )
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Learning",
                            fontSize = 20.sp,
                            fontFamily = FontFamily(Font(R.font.kanitmedium)),
                            color = Color.Black
                        )
                    }
                }

                Card(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f)
                        .padding(start = 5.dp, end = 25.dp, top = 5.dp, bottom = 25.dp),
                    shape = RoundedCornerShape(50f),
                    colors = CardColors(
                        containerColor = colorResource(id = R.color.cardColor),
                        contentColor = colorResource(id = R.color.cardColor),
                        disabledContainerColor = Color.Gray,
                        disabledContentColor = Color.DarkGray
                    )
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Review",
                            fontSize = 20.sp,
                            fontFamily = FontFamily(Font(R.font.kanitmedium)),
                            color = Color.Black
                        )
                    }
                }
            }

            CircularProgressIndicator(
                progress = {
                    0.7f
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(3f)
                    .padding(85.dp),
                color = colorResource(id = R.color.black),
                strokeWidth = 13.dp,
                trackColor = colorResource(id = R.color.cardColor),
            )

        }
    }
}