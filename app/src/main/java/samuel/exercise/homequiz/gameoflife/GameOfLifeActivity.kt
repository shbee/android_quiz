package samuel.exercise.homequiz.gameoflife

import android.os.Bundle
import android.view.MotionEvent
import android.widget.Button
import android.widget.CompoundButton
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import samuel.exercise.homequiz.R
import samuel.exercise.homequiz.view.CellGridView

class GameOfLifeActivity : AppCompatActivity(), GameOfLifeContract.View,
    CellGridView.CellTouchListener, CompoundButton.OnCheckedChangeListener {

    override lateinit var presenter: GameOfLifeContract.Presenter
    private lateinit var tickSwitch: Switch
    private lateinit var gridView: CellGridView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gridView = findViewById<CellGridView>(R.id.grid_view).apply {
            setDimenSize(GameOfLifeContract.DIMEN_SIZE_X, GameOfLifeContract.DIMEN_SIZE_Y)
            cellTouchListener = this@GameOfLifeActivity
        }
        tickSwitch = findViewById<Switch>(R.id.tick_switch).apply {
            setOnCheckedChangeListener(this@GameOfLifeActivity)
        }
        findViewById<Button>(R.id.clear_button).apply {
            setOnClickListener { presenter.clearAll() }
        }
        presenter = GameOfLifePresenter(this)
        onCheckedChanged(tickSwitch, tickSwitch.isChecked)
    }

    override fun onResume() {
        super.onResume()
        presenter.subscribe()
    }

    override fun onPause() {
        super.onPause()
        presenter.unsubscribe()
    }

    override fun updateLiveCells(cellSet: Set<Int>) {
        gridView.setLiveCells(cellSet)
    }

    override fun onCellTouched(event: MotionEvent, cellIndex: Int) {
        presenter.addLiveCell(cellIndex)
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        if (isChecked) {
            presenter.resumeTransition()
        } else {
            presenter.pauseTransition()
        }
    }
}
