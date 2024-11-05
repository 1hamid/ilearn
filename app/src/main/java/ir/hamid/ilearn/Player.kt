package ir.hamid.ilearn

import android.media.MediaPlayer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

@Composable
fun PlaySound(file: String) {

    var fileName = file.lowercase()
    val context = LocalContext.current
    val mediaPlayer = remember { MediaPlayer() }

    val assetManager = context.assets
    val assetFileDescriptor = assetManager.openFd("sounds/$fileName.mp3")

    mediaPlayer.apply {
        setDataSource(
            assetFileDescriptor.fileDescriptor,
            assetFileDescriptor.startOffset,
            assetFileDescriptor.length
        )
        prepare()
        start()

        setOnCompletionListener {
            release()
        }
    }

}