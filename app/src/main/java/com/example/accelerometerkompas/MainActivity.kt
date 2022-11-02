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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tvSensor = findViewById<TextView>(R.id.tvSensor)

        sManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val sensor = sManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        val sListner = object :SensorEventListener {
            override fun onSensorChanged(event: SensorEvent?) {
                val value = event?.values
                val sData = "X ${value?.get(0)} \nY ${value?.get(1)} \nZ ${value?.get(2)}"
                tvSensor.text = sData
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
                TODO("Not yet implemented")
            }
        }
        sManager.registerListener(sListner, sensor, SensorManager.SENSOR_DELAY_NORMAL)
    }
}