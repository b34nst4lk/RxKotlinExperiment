package com.example.jack.rxgame

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.TextView
import android.widget.Toast
import io.reactivex.subjects.PublishSubject
import org.reactivestreams.Subscriber
import android.widget.ImageView
import java.util.concurrent.TimeUnit


class GameActivity : AppCompatActivity() {
    lateinit var motionEvents: PublishSubject<MotionEvent>
    lateinit var subscriber: Subscriber<MotionEvent>
    lateinit var characterSprite: ImageView
    lateinit var touchPad : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        initializeViews()
        motionEvents = PublishSubject.create()

        assignEventHandlers()
        subscribeToEvent()

        characterSprite = findViewById<ImageView>(R.id.imgView).apply {
            setBackgroundResource(R.drawable.stickman)
        }
    }

    fun initializeViews() {
        touchPad = findViewById(R.id.tvTouchPad)
    }

    fun assignEventHandlers() {
        touchPad.setOnTouchListener { view, motionEvent ->
            motionEvents.onNext(motionEvent)
            true
        }
    }

    fun subscribeToEvent() {
        //to subscribe to inactivity 3s after activity
        motionEvents
            .debounce(3, TimeUnit.SECONDS)
            .subscribe{motionEvent->
               runOnUiThread {
                   Toast.makeText(this@GameActivity, "Are you sleeping?", Toast.LENGTH_SHORT).show()
               }
            }

        //to subscribe to down event (effect: animates image)
        motionEvents
            .filter{motionEvent ->motionEvent.getAction()== MotionEvent.ACTION_DOWN}
            .subscribe{motionEvent->
                runOnUiThread {
                    //move character
                    characterSprite.animate()
                            .scaleXBy(-6f)
                            .scaleYBy(-6f)
//                                .alpha(.1f)
                            .translationX(20F) // trying to make my location
                            .translationY(0F)
                            .setDuration(1000)
                            .start();

                }
            }
    }
}
