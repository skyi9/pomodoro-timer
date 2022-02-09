package ihaveto.publish.pomodoroapp

import android.content.Context
import android.media.AudioManager

class CustomAudioManager(private  val context: Context) {
    private var audioManager : AudioManager? = null

    @JvmName("getAudioManager1")
    fun getAudioManager() : AudioManager{
        if (audioManager == null) {
            audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        }
        return audioManager as AudioManager
    }
}