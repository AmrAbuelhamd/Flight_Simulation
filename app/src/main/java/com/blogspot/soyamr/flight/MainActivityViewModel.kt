package com.blogspot.soyamr.flight

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.mikephil.charting.data.Entry
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivityViewModel : ViewModel() {
    private val _points: MutableLiveData<ArrayList<Entry>> = MutableLiveData(ArrayList())
    val points: LiveData<ArrayList<Entry>> = _points


    private val _maxY: MutableLiveData<Int> = MutableLiveData(30)
    val maxY: LiveData<Int> = _maxY

    private val _maxX: MutableLiveData<Int> = MutableLiveData(30)
    val maxX: LiveData<Int> = _maxX

    val height: MutableLiveData<String> = MutableLiveData("10")
    val angel: MutableLiveData<String> = MutableLiveData("10")
    val speed: MutableLiveData<String> = MutableLiveData("10")

    val time: MutableLiveData<Double> = MutableLiveData(0.0)
    var ctr = 0;

    private val ball = Ball()

    var job: Job? = null
    var pause = false
    fun launch() {
        if (job != null && job!!.isActive) {
            return
        }
        if (job == null || (job != null && job!!.isCompleted && !job!!.isCancelled)) {
            ball.reset(
                speed.value!!.toDouble(),
                height.value!!.toDouble(),
                angel.value!!.toDouble()
            )
            ctr = 0;
            time.value = 0.0
            _points.value?.clear()
            _points.value = _points.value

            _maxY.value = ball.maximumHeight.toInt()
            _maxX.value = ball.maximumWidth.toInt()
            _points.value?.add(Entry(ball.x.toFloat(), ball.y.toFloat()))
            _points.value = _points.value
        }

        job = viewModelScope.launch {
            while (ball.y >= 0) {
                delay(10)
//                ctr += 10;
//                if (ball.t % 1 == 0.0) {
                time.value = ball.t
//                    ctr = 0
//                }
                ball.update()
                _points.value?.add(Entry(ball.x.toFloat(), ball.y.toFloat()))
                _points.value = _points.value
            }
        }
    }

    fun pause() {
        if (job != null && job!!.isActive) {
            job?.cancel()
            pause = true
        }
    }
}