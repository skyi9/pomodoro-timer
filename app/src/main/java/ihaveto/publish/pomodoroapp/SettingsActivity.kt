package ihaveto.publish.pomodoroapp

import android.content.Context
import android.content.SharedPreferences
import android.media.AudioManager
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SeekBar
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity() {

    lateinit var seekBar: SeekBar

    private lateinit var audioManager: AudioManager

    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        seekBar = findViewById(R.id.volumeBar)
        audioManager = CustomAudioManager(applicationContext).getAudioManager()

        sharedPreferences =
            applicationContext.getSharedPreferences("AUDIO_PREFERENCE", Context.MODE_PRIVATE)
        audioManager.setStreamVolume(
            AudioManager.STREAM_MUSIC,
            sharedPreferences.getInt("volume_lvl", 10),
            0
        )

        try {
            seekBar.max = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
            seekBar.progress = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)

            seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0)
                    sharedPreferences.edit().putInt("volume_lvl", progress).apply()
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {

                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {

                }

            })
        } catch (e: Exception) {

        }

    }
}