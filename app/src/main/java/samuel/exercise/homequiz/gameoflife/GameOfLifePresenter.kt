package samuel.exercise.homequiz.gameoflife

import android.util.Log
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicBoolean

class GameOfLifePresenter(private val view: GameOfLifeContract.View) : GameOfLifeContract.Presenter {

    private val disposables = CompositeDisposable()
    private var isRunning = AtomicBoolean(true)
    private val liveCellSet = hashSetOf<Int>()

    override fun subscribe() {
        disposables.add(Observable.interval(1000L, TimeUnit.MILLISECONDS).filter {
            Log.d(TAG, "Tick, isRunning = $isRunning")
            isRunning.get()
        }.observeOn(AndroidSchedulers.mainThread()).map {
            Log.d(TAG, "Tick, transition")
            it
        }.subscribe({
            Log.d(TAG, "Tick, onNext, $it")
        }, {
            Log.d(TAG, "Tick, onError, $it")
        }))
    }

    override fun unsubscribe() {
        disposables.clear()
    }

    override fun resumeTransition() {
        isRunning.set(true)
    }

    override fun pauseTransition() {
        isRunning.set(false)
    }

    override fun addLiveCell(cellIndex: Int) {
        if (liveCellSet.contains(cellIndex)) return

        Log.d(TAG, "addLiveCell, $cellIndex")
        liveCellSet.add(cellIndex)
        view.updateLiveCells(liveCellSet.toSet())
    }

    companion object {
        const val TAG = "GameOfLifePresenter"
    }
}