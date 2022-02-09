package ihaveto.publish.pomodoroapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import ihaveto.publish.pomodoroapp.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private val start = 600_000L
    var timer = start
    private lateinit var countDownTimer: CountDownTimer
    private lateinit var binding : ActivityMainBinding
    private lateinit var mp : MediaPlayer
    private lateinit var audioManager: AudioManager
    private lateinit var sharedPreferences: SharedPreferences

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main
        binding = DataBindingUtil.setContentView(this , R.layout.activity_main)


        sharedPreferences = applicationContext.getSharedPreferences("AUDIO_PREFERENCE", Context.MODE_PRIVATE)
        val currentVolume = sharedPreferences.getInt("volume_lvl", 10);
        audioManager = CustomAudioManager(applicationContext).getAudioManager()
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, currentVolume, 0)
        mp = MediaPlayer.create(this , R.raw.ring_sound)

        //      yhe first value
        setTextTimer()

        button.text = "Start"
        button.setOnClickListener {
            if(button.text == "Start"){
                setText()
                button.text = "Cancel"
                startTimer()
            }
            else if (button.text == "Cancel"){
                txtTask.visibility = View.GONE
                etTask.visibility = View.VISIBLE
                resetTimer()
                button.text = "Start"
            }
        }
        settings.setOnClickListener {
            val intent = Intent(this , SettingsActivity::class.java)
            intent.putExtra("button_id" , R.id.settings.toString())
            startActivity(intent)
        }
    }
    //    btn start
    private fun startTimer() {
        countDownTimer = object : CountDownTimer(timer,1000){
            //            end of timer
            override fun onFinish() {
                mp.start()
            }
            override fun onTick(millisUntilFinished: Long) {
                timer = millisUntilFinished
                setTextTimer()
            }
        }.start()
    }

    //    btn restart
    private fun resetTimer() {
        countDownTimer.cancel()
        timer = start
        setTextTimer()
    }
    //  timer format
    fun setTextTimer() {
        val m = (timer / 1000) / 60
        val s = (timer / 1000) % 60

        val format = String.format("%02d:%02d", m, s)
        tv_main_timer.text = format
        ProgressBar_countdown.progress = s.toInt()
    }
    private fun setText() {
        txtTask.text = etTask.text.toString()
        etTask.visibility = View.GONE
        txtTask.visibility = View.VISIBLE
    }

}

