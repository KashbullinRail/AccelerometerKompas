package com.example.accelerometerkompas

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log

abstract class Compas(
   private val sensorManager: SensorManager,
//   val coroutineScope: CoroutineScope
) {
    private var magnit = FloatArray(9)
    private var gravity = FloatArray(9)
    private var acceler = FloatArray(3)
    private var magnetfield = FloatArray(3)
    private var values = FloatArray(3)
//    private var sensorsWork: Boolean = true

    fun start() {

        val sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        val sensor2 = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)

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
                SensorManager.getOrientation(outGravity, values)

                val degree = Math.toDegrees(values[0].toDouble())

                rotateMiddle = when(degree.toInt()) {
                    in 0..180 -> degree
                    in -180..0 -> degree + 360
                    else -> { degree }
                }
                Log.d("TAGG", rotateMiddle.toString())

                compasAngle(rotateMiddle.toInt())
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
            }
        }

//        if (sensorsWork) {
            sensorManager.registerListener(sListner, sensor, SensorManager.SENSOR_DELAY_NORMAL)
            sensorManager.registerListener(sListner, sensor2, SensorManager.SENSOR_DELAY_NORMAL)
//        } else {
//            sensorManager.unregisterListener(sListner, sensor)
//        }
    }
//    fun close() {
//        sensorsWork = false
//    }

    abstract fun compasAngle(angle: Int)
}