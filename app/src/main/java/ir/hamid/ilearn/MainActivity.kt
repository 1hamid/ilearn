package ir.hamid.ilearn

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.ViewGroup
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.updateLayoutParams
import kotlin.contracts.contract


class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.R)
    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppBar()
        }
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
//                colors = TopAppBarDefaults.topAppBarColors(containerColor = colorResource(id = R.color.background))
            )
        },
        content = { innerPadding ->
            AppUI(innerPadding)
        }
    )
}

@Composable
private fun AppUI(innerPadding: PaddingValues) {
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