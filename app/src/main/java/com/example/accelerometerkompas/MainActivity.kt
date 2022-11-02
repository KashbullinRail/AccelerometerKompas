package com.example.accelerometerkompas

import android.content.Context
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView


class MainActivity : AppCompatActivity() {

    lateinit var sManager: SensorManager
    private var magnit = FloatArray(9)
    private var gravity = FloatArray(9)
    private var acceler = FloatArray(3)
    private var magnetfield = FloatArray(3)
    private var values = FloatArray(3)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tvSensor = findViewById<TextView>(R.id.tvSensor)
        val lRotation = findViewById<LinearLayout>(R.id.lRotation)

        sManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val sensor = sManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        val sensor2 = sManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)

        val sListner = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent?) {
                when (event?.sensor?.type) {
                    Sensor.TYPE_ACCELEROMETER -> acceler = event.values.clone()
                    Sensor.TYPE_MAGNETIC_FIELD -> magnetfield = event.values.clone()
                }
                SensorManager.getRotationMatrix(gravity, magnit, acceler, magnetfield)
                val outGravity = FloatArray(9)
                SensorManager.remapCoordinateSystem(
                    gravity,
                    SensorManager.AXIS_X,
                    SensorManager.AXIS_Z,
                    outGravity
                )
                SensorManager.getOrientation(outGravity, values)

                val degree = Math.toDegrees(values[2].toDouble())
                lRotation.rotation = degree.toFloat() + 270
                val rotateMiddle = degree + 90

                val colorLayout = if(rotateMiddle.toInt() == 0) {
                    Color.GREEN
                } else {
                    Color.RED
                }
                lRotation.setBackgroundColor(colorLayout)
                tvSensor.text = rotateMiddle.toInt().toString()
            }
            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
            }
        }
        sManager.registerListener(sListner, sensor, SensorManager.SENSOR_DELAY_NORMAL)
        sManager.registerListener(sListner, sensor2, SensorManager.SENSOR_DELAY_NORMAL)
    }
}