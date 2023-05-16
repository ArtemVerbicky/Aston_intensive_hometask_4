package ru.verb.aston_intensive_hometask_4

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color.BLACK
import android.graphics.Paint
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.AttributeSet
import android.view.View
import androidx.core.content.withStyledAttributes
import java.util.*
import kotlin.math.cos
import kotlin.math.sin

class CustomClock @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var circle = Paint()
    private var point = Paint()
    private var line = Paint()

    private var degreeHour = 0
    private var degreeMin = 0
    private var degreeSec = 0

    private var centerX = 0F
    private var centerY = 0F
    private var radius = 0F

    private var hoursArrowSize = 0f
    private var minutesArrowSize = 0f
    private var secondsArrowSize = 0f

    var hoursArrowColor = 0
        set(value) {
            field = value
            invalidate()
        }
    var minutesArrowColor = 0
        set(value) {
            field = value
            invalidate()
        }
    var secondsArrowColor = 0
        set(value) {
            field = value
            invalidate()
        }

    private var calendar = Calendar.getInstance()

    private val handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            getTime()
            invalidate()
        }
    }

    init {
        getAttributes(attrs)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        initdata()
        drawCalibration(canvas)
        drawLines(canvas)
    }

    private fun initdata() {

        getTime()

        circle = Paint().apply {
            color = BLACK
            style = Paint.Style.STROKE
            strokeWidth = 10F
        }
        point = Paint().apply {
            strokeWidth = 30F
            strokeCap = Paint.Cap.ROUND
        }
        line = Paint().apply {
            color = BLACK
        }

        centerX = (width / 2).toFloat()
        centerY = (height / 2).toFloat()
        radius = centerX - 10 * circle.strokeWidth

        handler.sendEmptyMessageDelayed(1, 1000)
    }

    private fun getAttributes(attrs: AttributeSet?) {
        context.withStyledAttributes(attrs, R.styleable.Clock) {
            hoursArrowColor = getColor(R.styleable.Clock_hours_color, 0)
            minutesArrowColor = getColor(R.styleable.Clock_minutes_color, 0)
            secondsArrowColor = getColor(R.styleable.Clock_seconds_color, 0)

            hoursArrowSize = getFloat(R.styleable.Clock_hours_size, 0f)
            minutesArrowSize = getFloat(R.styleable.Clock_minutes_size, 0f)
            secondsArrowSize = getFloat(R.styleable.Clock_seconds_size, 0f)
        }
    }

    private fun getTime() {
        calendar = Calendar.getInstance(TimeZone.getTimeZone("Europe/Moscow"))
        degreeSec = calendar.get(Calendar.SECOND) * 6
        degreeMin = calendar.get(Calendar.MINUTE) * 6

        degreeHour = if (calendar.get(Calendar.HOUR) > 12) {
            (calendar.get(Calendar.HOUR) - 12) * 30
        } else {
            calendar.get(Calendar.HOUR) * 30
        }
    }

    private fun drawCalibration(canvas: Canvas) {

        canvas.drawCircle(centerX, centerY, radius, circle)

        for (i in 1..12) {
            line.strokeWidth = 10F
            canvas.drawLine(
                centerX + (0.9 * radius * sin(Math.toRadians(i * 30.toDouble()))).toFloat(),
                centerY - (0.9 * radius * cos(Math.toRadians(i * 30.toDouble()))).toFloat(),
                centerX + (radius * sin(Math.toRadians(i * 30.toDouble()))).toFloat(),
                centerY - (radius * cos(Math.toRadians(i * 30.toDouble()))).toFloat(),
                line
            )
            line.textSize = 40F
            canvas.drawText(
                "" + i,
                centerX + (0.7 * radius * sin(Math.toRadians(i * 30.toDouble()))).toFloat(),
                centerY - (0.7 * radius * cos(Math.toRadians(i * 30.toDouble()))).toFloat(),
                line
            )
        }

        for (i in 1..60) {
            line.strokeWidth = 5F
            canvas.drawLine(
                centerX + (0.95 * radius * sin(Math.toRadians(i * 6.toDouble()))).toFloat(),
                centerY - (0.95 * radius * cos(Math.toRadians(i * 6.toDouble()))).toFloat(),
                centerX + (radius * sin(Math.toRadians(i * 6.toDouble()))).toFloat(),
                centerY - (radius * cos(Math.toRadians(i * 6.toDouble()))).toFloat(),
                line
            )
        }
    }

    private fun drawLines(canvas: Canvas) {
        canvas.apply {
            line.strokeWidth = secondsArrowSize
            line.color = hoursArrowColor
            canvas.drawLine(
                centerX,
                centerY,
                centerX + (0.5 * radius * sin(Math.toRadians(degreeHour.toDouble()))).toFloat(),
                centerY - (0.5 * radius * cos(Math.toRadians(degreeHour.toDouble()))).toFloat(),
                line
            )

            line.strokeWidth = minutesArrowSize
            line.color = minutesArrowColor
            canvas.drawLine(
                centerX,
                centerY,
                centerX + (0.7 * radius * sin(Math.toRadians(degreeMin.toDouble()))).toFloat(),
                centerY - (0.7 * radius * cos(Math.toRadians(degreeMin.toDouble()))).toFloat(),
                line
            )

            line.strokeWidth = hoursArrowSize
            line.color = secondsArrowColor
            canvas.drawLine(
                centerX,
                centerY,
                centerX + (radius * sin(Math.toRadians(degreeSec.toDouble()))).toFloat(),
                centerY - (radius * cos(Math.toRadians(degreeSec.toDouble()))).toFloat(),
                line
            )
        }
        canvas.drawPoint(centerX, centerY, point)
    }
}