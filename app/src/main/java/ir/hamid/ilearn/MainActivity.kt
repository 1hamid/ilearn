package ir.hamid.ilearn

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.airbnb.lottie.LottieProperty
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.airbnb.lottie.compose.rememberLottieDynamicProperties
import com.airbnb.lottie.compose.rememberLottieDynamicProperty
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import ir.hamid.ilearn.ui.theme.IlearnTheme
import ir.hamid.model.DataStoreRepository
import ir.hamid.model.QueryResult2
import ir.hamid.model.W504Repository
import ir.hamid.viewmodel.W504ViewModel
import ir.hamid.viewmodel.WordViewModelFactory
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var repository: W504Repository

    @Inject
    lateinit var dataStoreRepository: DataStoreRepository
    private val wordViewModel: W504ViewModel by viewModels {
        WordViewModelFactory(repository, dataStoreRepository)
    }

    @RequiresApi(Build.VERSION_CODES.R)
    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        var isDark: Boolean? = null
        wordViewModel.isDarkTheme.observe(this) { isDarkTheme ->
            isDark = isDarkTheme

            setContent {
                IlearnTheme(
                    isDark = isDark,
                    setThemeState = { isDark -> wordViewModel.saveThemeState(isDark) }) {
                    AppBar()
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun AppBar() {

        var isPlaying by remember { mutableStateOf(true) }
        var darkMode by remember { mutableStateOf(true) }

        wordViewModel.isDarkTheme.observe(this) { isDarkTheme ->
            darkMode = isDarkTheme == true
        }

        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.sun))

        val dynamicPropertiesLight = rememberLottieDynamicProperties(
            rememberLottieDynamicProperty(
                LottieProperty.COLOR,
                value = colorResource(R.color.black).toArgb(),
                keyPath = arrayOf("**")
            )
        )

        val dynamicPropertiesDark = rememberLottieDynamicProperties(
            rememberLottieDynamicProperty(
                LottieProperty.COLOR,
                value = colorResource(R.color.white).toArgb(),
                keyPath = arrayOf("**")
            )
        )

        val lightProgress by animateLottieCompositionAsState(
            composition = composition,
            isPlaying = isPlaying,
            iterations = 1,
        )

        val darkProgress by animateLottieCompositionAsState(
            composition = composition,
            isPlaying = isPlaying,
            iterations = 1,
            speed = -1f
        )

        LaunchedEffect(lightProgress) {
            if (lightProgress == 1f) {
                isPlaying = false
            }
        }
        LaunchedEffect(darkProgress) {
            if (lightProgress == 1f) {
                isPlaying = false
            }
        }

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
                    actions = {
                        Box(
                            modifier = Modifier
                                .padding(end = 25.dp)
                                .clickable(
                                    indication = null,
                                    interactionSource = remember { MutableInteractionSource() })
                                {
                                    isPlaying = true
                                    darkMode = !darkMode
                                    wordViewModel.saveThemeState(darkMode)
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            LottieAnimation(
                                composition = composition,
                                progress = { if (darkMode) lightProgress else darkProgress },
                                modifier = Modifier.size(25.dp),
                                dynamicProperties = if (darkMode) dynamicPropertiesDark else dynamicPropertiesLight
                            )
                        }
                    }
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
                composable(route = DataSource.IlearnScreens.Review.name) {
                    ReviewScreen(wordViewModel)
                }
                composable(route = DataSource.IlearnScreens.WordsBox.name) {
                    WordsBoxScreen(wordViewModel, navController)
                }
                composable(route = DataSource.IlearnScreens.Search.name) {
                    SearchScreen(wordViewModel, navController)
                }
                composable(route = DataSource.IlearnScreens.WordsList.name + "/{boxNumber}") { backStackEntry ->
                    val boxNumber = backStackEntry.arguments?.getString("boxNumber")?.toIntOrNull()
                    LearnedWordsScreen(wordViewModel, boxNumber!!)
                }
                composable(route = DataSource.IlearnScreens.WordScreen.name + "/{word}") { backStackEntry ->
                    val wordJson = backStackEntry.arguments?.getString("word")
                    val word = Gson().fromJson(wordJson, QueryResult2::class.java)
                    WordsScreen(word)
                }
            }
        }
    }

    @Composable
    private fun AppUI(innerPadding: PaddingValues, navController: NavController) {
        val context = LocalContext.current
        var showDialog by remember { mutableStateOf(false) }
        var resetDialog by remember { mutableStateOf(false) }

        var progress by remember { mutableFloatStateOf(0f) }
        var progress2 by remember { mutableIntStateOf(0) }
        var count by remember { mutableIntStateOf(0) }

        wordViewModel.counterData.observe(this) { counter ->
            if (counter != null) {
                count = counter
                progress2 = ((100 * counter) / 540)
                progress = ((100f * counter.toFloat()) / 540f) / 100f
            }
        }

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
                ),
                onClick = { navController.navigate(DataSource.IlearnScreens.Search.name) }
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
                    onClick = { navController.navigate(DataSource.IlearnScreens.Review.name) },
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

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(2.5f)
                    .padding(85.dp)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() })
                    {
                        navController.navigate(DataSource.IlearnScreens.WordsBox.name)
                    },
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.fillMaxSize(),
                    progress = {
                        progress
                    },
                    color = colorResource(id = R.color.black),
                    strokeWidth = 13.dp,
                    trackColor = colorResource(id = R.color.cardColor),
                )
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "%$progress2", fontSize = 23.sp, fontWeight = FontWeight.Bold)
                    Text(text = "$count / 504", fontSize = 15.sp)
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(0.3f)
                    .padding(bottom = 20.dp, end = 25.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Box(modifier = Modifier
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() })
                    {
                        showDialog = true
                    }) {
                    Icon(
                        painter = painterResource(id = R.drawable.settings),
                        contentDescription = "reset"
                    )
                }
            }

        }
        if (showDialog) {
            SettingsDialog(repo = {
                showDialog = false
                resetDialog = false
                val intent =
                    Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/1hamid/ilearn"))
                startActivity(intent)
            }, reset = {
                showDialog = false
                resetDialog = true
            }, onDismiss = {
                showDialog = false
                resetDialog = false
            })
        }

        if (resetDialog) {
            ResetAlertDialog(
                onConfirm = {
                    Toast.makeText(context, "Reset", Toast.LENGTH_LONG).show()
                    resetDialog = false
                },
                onDismiss = {
                    resetDialog = false
                }
            )
        }

    }

    @Composable
    private fun ResetAlertDialog(onConfirm: () -> Unit, onDismiss: () -> Unit) {
        AlertDialog(
            onDismissRequest = {
                onDismiss()
            },
            title = {
                Text(text = "warning")
            },
            text = {
                Text("Are you sure to reset database ?")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        Log.i("reset", "reset")
                        wordViewModel.reset()
                        onConfirm()
                    }
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        onDismiss()
                    }
                ) {
                    Text("Cancel")
                }
            }
        )
    }

    @Composable
    private fun SettingsDialog(repo: () -> Unit, reset: () -> Unit, onDismiss: () -> Unit) {
        Dialog(
            onDismissRequest = {
                onDismiss()
            }
        ) {
            Card(
                modifier = Modifier
                    .wrapContentWidth(),
                shape = RoundedCornerShape(16.dp),
            ) {
                Column {
                    Row(modifier = Modifier.wrapContentWidth()) {
                        Spacer(modifier = Modifier.weight(1f))
                        Icon(
                            painterResource(R.drawable.close),
                            "close Dialog",
                            modifier = Modifier
                                .padding(top = 8.dp, end = 8.dp)
                                .clickable(
                                    indication = null,
                                    interactionSource = remember { MutableInteractionSource() }) { onDismiss() }
                        )
                    }

                    Row(
                        modifier = Modifier
                            .wrapContentSize()
                            .clickable(
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() }) { reset() }
                            .padding(start = 16.dp, top = 18.dp, bottom = 16.dp, end = 100.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.reset),
                            contentDescription = "Reset database",
                            modifier = Modifier.size(24.dp)
                        )
                        Text(
                            text = "Reset",
                            modifier = Modifier.padding(start = 8.dp),
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Row(
                        modifier = Modifier
                            .wrapContentSize()
                            .clickable(
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() }) { repo() }
                            .padding(start = 16.dp, bottom = 16.dp, end = 100.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.repository),
                            contentDescription = "Repository address",
                            modifier = Modifier.size(24.dp)
                        )
                        Text(
                            text = "Repository",
                            modifier = Modifier.padding(start = 8.dp),
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

            }
        }
    }
}