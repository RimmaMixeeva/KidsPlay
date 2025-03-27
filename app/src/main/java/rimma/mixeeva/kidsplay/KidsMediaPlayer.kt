package rimma.mixeeva.kidsplay

import android.content.Context
import android.media.MediaPlayer
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class KidsMediaPlayer @Inject constructor(@ApplicationContext val context: Context) {

    private var mediaPlayer: MediaPlayer? = null

    fun playSong(song: Int, isLooping: Boolean){
        if (mediaPlayer != null){
            destroy()
        }
        mediaPlayer = MediaPlayer.create(context, song)
        mediaPlayer?.isLooping = isLooping // Зацикливать музыку
        mediaPlayer?.start()
    }

    fun stop(){
        mediaPlayer?.stop()
    }

    fun resume(){
        mediaPlayer?.reset()
    }
    fun destroy(){
        mediaPlayer?.release()
        mediaPlayer = null
    }

    fun playShortSongAndRelease(song: Int){
        val player = MediaPlayer.create(context, song)
        player?.setOnCompletionListener {
            it.release()
        }
        player?.start()
    }


}