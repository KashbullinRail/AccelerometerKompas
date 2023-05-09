package com.example.accelerometerkompas

import android.content.Context
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    private lateinit var sensorManager: SensorManager
    private var sensor: Sensor? = null
    private var sensor2: Sensor? = null
//    private val sListner by lazy { SensorEventListener() }
    private var magnit = FloatArray(9)
    private var gravity = FloatArray(9)
    private var acceler = FloatArray(3)
    private var magnetfield = FloatArray(3)
    private var values = FloatArray(3)

//    private lateinit var sensorManager: SensorManager
//    private val accelerometerReading = FloatArray(3)
//    private val magnetometerReading = FloatArray(3)
//
//    private val rotationMatrix = FloatArray(9)
//    private val orientationAngles = FloatArray(3)



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        val tvSensor = findViewById<TextView>(R.id.tvSensor)
        val lRotation = findViewById<LinearLayout>(R.id.lRotation)
//
//        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        val sensor2 = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
//
//        val sensor2 = sManager.getDefaultSensor(Sensor.TYPE_ORIENTATION)

//        private lateinit var sensorManager: SensorManager
//        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
//        if (sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD) != null) {
//            // Success! There's a magnetometer.
//        } else {
//            // Failure! No magnetometer.
//        }

        val sListner = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent?) {
                var rotateMiddle = 0.0
                when (event?.sensor?.type) {
                    Sensor.TYPE_ACCELEROMETER -> acceler = event.values.clone()
                    Sensor.TYPE_MAGNETIC_FIELD -> magnetfield = event.values.clone()
                }

                SensorManager.getRotationMatrix(gravity, magnit, acceler, magnetfield)
                val outGravity = FloatArray(9)
                SensorManager.remapCoordinateSystem(
                    gravity,
                    SensorManager.AXIS_X,
                    SensorManager.AXIS_Y,
                    outGravity
                )
//                SensorManager.getOrientation(outGravity, values)
                SensorManager.getOrientation(gravity, values)

                val degree = Math.toDegrees(values[0].toDouble())

                rotateMiddle = when(degree.toInt()) {
                   in 0..180 -> degree
                   in -180..0 -> degree + 360
                    else -> { degree }
                }

//                if (degree < 0) {
////                    lRotation.rotation = degree.toFloat() + 270
//                    var rotateMiddle =  degree + 360
//                } else {
//                    rotateMiddle = degree
//                }


                val colorLayout = if(rotateMiddle.toInt() == 0) {
                    Color.GREEN
                } else {
                    Color.RED
                }
                lRotation.setBackgroundColor(colorLayout)
                tvSensor.text = rotateMiddle.toInt().toString()


            }
//
            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
            }
//
        }
        sensorManager.registerListener(sListner, sensor, SensorManager.SENSOR_DELAY_NORMAL)
        sensorManager.registerListener(sListner, sensor2, SensorManager.SENSOR_DELAY_NORMAL)

    }

    override fun onResume() {
        super.onResume()
//        sensor?.also { sensor ->
//            sensorManager.registerListener(sListner, sensor, SensorManager.SENSOR_DELAY_NORMAL)
//        }
//        sensor2?.also { sensor ->
//            sensorManager.registerListener(this, sensor2, SensorManager.SENSOR_DELAY_NORMAL)
//        }

    }

    override fun onStop() {
        super.onStop()
//        sensorManager.unregisterListener(this)

    }




}