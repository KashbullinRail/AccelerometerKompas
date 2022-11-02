package com.example.accelerometerkompas

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView


class MainActivity : AppCompatActivity() {

    lateinit var sManager: SensorManager
    private var magnit = FloatArray(9)
    private var gravity = FloatArray(9)
    private var acceler = FloatArray(3)
    private var magnetfield = FloatArray(3)
    private var value = FloatArray(3)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tvSensor = findViewById<TextView>(R.id.tvSensor)

        sManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val sensor = sManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        val sensor2 = sManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)

        val sListner = object :SensorEventListener {
            override fun onSensorChanged(event: SensorEvent?) {
                when(event?.sensor?.type){
                    Sensor.TYPE_ACCELEROMETER -> acceler = event.values.clone()
                    Sensor.TYPE_MAGNETIC_FIELD -> magnetfield = event.values.clone()
                }

            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

            }
        }
        sManager.registerListener(sListner, sensor, SensorManager.SENSOR_DELAY_NORMAL)
        sManager.registerListener(sListner, sensor2, SensorManager.SENSOR_DELAY_NORMAL)
    }
}