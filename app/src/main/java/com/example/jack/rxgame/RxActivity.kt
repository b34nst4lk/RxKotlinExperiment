package com.example.jack.rxgame

import android.gesture.Gesture
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import java.util.*

class RxActivity : AppCompatActivity() {
    lateinit var singleTapView : TextView
    lateinit var doubleTapView : TextView
    lateinit var swipeUpView: TextView
    lateinit var swipeDownView: TextView
    lateinit var touchArea: TextView
    var rnd : Random = Random()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rx)
        initializeViews()
        assignEventHandlers()
    }

    fun initializeViews() {
        singleTapView = findViewById(R.id.text1)
        doubleTapView = findViewById(R.id.text1)
        swipeUpView = findViewById(R.id.text1)
        swipeDownView= findViewById(R.id.text1)
        touchArea = findViewById(R.id.text1)
    }

    fun assignEventHandlers() {
    }
}
