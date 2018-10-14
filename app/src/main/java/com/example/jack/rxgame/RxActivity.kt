package com.example.jack.rxgame

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import io.reactivex.subjects.PublishSubject
import java.util.*

class RxActivity : AppCompatActivity() {
    private var motionEvents: PublishSubject<MotionEvent> = PublishSubject.create()
    private var rnd : Random = Random()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rx)
        assignTouchListener()
        subscribeToEvent()
    }

    private fun assignTouchListener() {
        findViewById<TextView>(R.id.touch_area_view).setOnTouchListener() {
            view: View, event : MotionEvent ->
                motionEvents.onNext(event)
                true
        }
    }

    private fun subscribeToEvent() {
        motionEvents
            .filter { it.action != MotionEvent.ACTION_MOVE }
            .subscribe { event ->
                Log.i("Pointer Id", "Finger " + event.getPointerId(event.actionIndex).toString())
                findViewById<TextView>(R.id.single_tap_view).setBackgroundColor(getRandomColor())
            }
    }

    private fun getRandomColor() : Int {
        return Color.rgb(
            rnd.nextInt(255),
            rnd.nextInt(255),
            rnd.nextInt(255)
        )
    }
}
