package space.alehandrozed.compass

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), SensorEventListener {
    var manager: SensorManager? = null
    var current_gradus: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        manager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }

    override fun onResume() {
        super.onResume()
        manager?.registerListener(
            this,
            manager?.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),
            SensorManager.SENSOR_DELAY_GAME
        )
    }

    override fun onPause() {
        super.onPause()
        manager?.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        val gradus: Int = event?.values?.get(0)?.toInt()!!
        tvGrad.text = gradus.toString()
        val rotationAnim = RotateAnimation(
            current_gradus.toFloat(), ((-gradus).toFloat()),
            Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f
        )
        rotationAnim.duration = 210
        rotationAnim.fillAfter = true
        current_gradus = -gradus
        ivDinamic.startAnimation(rotationAnim)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }
}