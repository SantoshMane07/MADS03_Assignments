package com.example.counterprogram

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.counterprogram.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    var counter :Int = 0
    lateinit var mbinding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mbinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mbinding.root)

        mbinding.counterView.text = counter.toString()

        mbinding.counterButton.setOnClickListener {
            counter++
            mbinding.counterView.text = counter.toString()
        }
    }

    override fun onResume() {
        super.onResume()
        counter = 0
    }
}