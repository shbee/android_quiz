package samuel.exercise.homequiz.gameoflife

import android.util.Log
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import samuel.exercise.homequiz.gameoflife.GameOfLifeContract.Companion.DIMEN_SIZE_X
import samuel.exercise.homequiz.gameoflife.GameOfLifeContract.Companion.DIMEN_SIZE_Y
import samuel.exercise.homequiz.gameoflife.GameOfLifeContract.Companion.TICK_INTERVAL_MS
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicBoolean

class GameOfLifePresenter(private val view: GameOfLifeContract.View) : GameOfLifeContract.Presenter {

    private val disposables = CompositeDisposable()
    private var isRunning = AtomicBoolean(true)
    private val liveCellSet = hashSetOf<Int>()

    override fun subscribe() {
        disposables.add(
            nextGenerationObservable().subscribe({
                Log.d(TAG, "onNext generation, $it")
                view.updateLiveCells(it)
            }, {
                Log.d(TAG, "onError, $it")
            })
        )
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

    private fun nextGenerationObservable(): Observable<Set<Int>> {
        return Observable.interval(TICK_INTERVAL_MS, TimeUnit.MILLISECONDS).filter {
            isRunning.get()
        }.observeOn(AndroidSchedulers.mainThread()).map {
            Log.d(TAG, "Tick, transition")
            val nextSet = transitCellState(liveCellSet.toSet())
            liveCellSet.clear()
            liveCellSet.addAll(nextSet)
            nextSet
        }
    }

    override fun addLiveCell(cellIndex: Int) {
        if (liveCellSet.contains(cellIndex)) return

        Log.d(TAG, "addLiveCell, $cellIndex")
        liveCellSet.add(cellIndex)
        view.updateLiveCells(liveCellSet.toSet())
    }

    private fun transitCellState(cellSet: Set<Int>): Set<Int> {
        val nextSet = hashSetOf<Int>()

        for (index in 0 until DIMEN_SIZE_X * DIMEN_SIZE_Y) {
            val liveCount = liveNeighbourCount(index, cellSet)

            if (cellSet.contains(index)) {
                // live cell
                when (liveCount) {
                    in 2..3 -> nextSet.add(index)
                }
            } else {
                // dead cell
                if (liveCount == 3) nextSet.add(index)
            }
        }
        return nextSet
    }

    private fun liveNeighbourCount(cellIndex: Int, cellSet: Set<Int>): Int {
        val dx = cellIndex % DIMEN_SIZE_X
        val dy = cellIndex / DIMEN_SIZE_X
        var liveCount = 0

        for (y in -1..1) {
            val ny = dy + y
            if (ny < 0 || ny >= DIMEN_SIZE_Y) continue

            for (x in -1..1) {
                val nx = dx + x
                if (nx < 0 || nx >= DIMEN_SIZE_X || (x == 0 && y == 0)) continue

                val nIndex = ny * DIMEN_SIZE_X + nx
                if (cellSet.contains(nIndex)) liveCount++
            }
        }
        return liveCount
    }

    companion object {
        const val TAG = "GameOfLifePresenter"
    }
}