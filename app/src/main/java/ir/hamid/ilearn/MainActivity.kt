package ir.hamid.ilearn

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LessonsList()
        }
    }
}


@Composable
private fun LessonsList() {

    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 40.dp),
        columns = GridCells.Adaptive(minSize = 128.dp),
    ) {
        items(count = 46) { index ->
            LessonsCard(text = index.toString())
        }
    }
}

@Composable
private fun LessonsCard(text: String) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .wrapContentSize()            ,
        shape = RoundedCornerShape(5.dp)
    ) {
        Column {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = "description",
                modifier = Modifier.size(120.dp)
            )
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(text = text)
            }
        }
    }
}