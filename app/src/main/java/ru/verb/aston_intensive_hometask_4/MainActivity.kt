package ru.verb.aston_intensive_hometask_4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.verb.aston_intensive_hometask_4.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }

        with(binding) {
            buttonChangeMinutesColor.setOnClickListener {
                customClock.minutesArrowColor = getRandomColor()
            }
            buttonChangeHoursColor.setOnClickListener {
                customClock.hoursArrowColor = getRandomColor()
            }
            buttonChangeSecondsColor.setOnClickListener {
                customClock.secondsArrowColor = getRandomColor()
            }
        }

    }
    private fun getRandomColor(): Int = -Random.nextInt(0xFFFFFF)
}