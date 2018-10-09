package com.example.jack.rxgame

import android.annotation.SuppressLint
import android.content.Intent
import android.gesture.Gesture
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import io.reactivex.subjects.PublishSubject
import org.reactivestreams.Subscriber
import java.util.*

class RxActivity : AppCompatActivity() {
    lateinit var btnGoToGame : Button
    lateinit var singleTapView : TextView
    lateinit var doubleTapView : TextView
    lateinit var swipeUpView: TextView
    lateinit var swipeDownView: TextView
    lateinit var touchArea: TextView
    lateinit var motionEvents: PublishSubject<MotionEvent>
    lateinit var subscriber: Subscriber<MotionEvent>
    var rnd : Random = Random()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rx)
        initializeViews()

        motionEvents = PublishSubject.create()

        assignEventHandlers()
        subscribeToEvent()
    }

    fun initializeViews() {
        btnGoToGame = findViewById(R.id.btnToGame)
        singleTapView = findViewById(R.id.text1)
        doubleTapView = findViewById(R.id.text2)
        swipeUpView = findViewById(R.id.text3)
        swipeDownView= findViewById(R.id.text4)
        touchArea = findViewById(R.id.text5)
    }

    fun assignEventHandlers() {
        touchArea.setOnTouchListener { view, motionEvent ->
            motionEvents.onNext(motionEvent)
            true
        }

        btnGoToGame.setOnClickListener{
            Toast.makeText(this@RxActivity, "Welcome to Game!", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, GameActivity::class.java)
            startActivity(intent)
        }
    }

    fun subscribeToEvent() {
        motionEvents.filter{motionEvent ->motionEvent.getAction()== MotionEvent.ACTION_DOWN}.subscribe{motionEvent->
            Log.i("Event", motionEvent.toString())
            singleTapView.setBackgroundColor(randomizeColors())
            doubleTapView.setBackgroundColor(randomizeColors())
            swipeUpView.setBackgroundColor(randomizeColors())
            swipeDownView.setBackgroundColor(randomizeColors())
        }
    }

    fun randomizeColors() : Int {
        return Color.rgb(
                rnd.nextInt(255),
                rnd.nextInt(255),
                rnd.nextInt(255)
        )
    }
}
