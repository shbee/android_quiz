package samuel.exercise.homequiz.gameoflife

import android.os.Bundle
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import samuel.exercise.homequiz.R
import samuel.exercise.homequiz.view.CellGridView

class GameOfLifeActivity : AppCompatActivity(), GameOfLifeContract.View, CellGridView.CellTouchListener {

    override lateinit var presenter: GameOfLifeContract.Presenter
    private lateinit var gridView: CellGridView
    private val liveList = mutableListOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gridView = findViewById<CellGridView>(R.id.grid_view).apply {
            cellTouchListener = this@GameOfLifeActivity
        }
    }

    override fun onCellTouched(event: MotionEvent, cellIndex: Int) {
        if (!liveList.remove(cellIndex)) {
            liveList.add(cellIndex)
        }
        gridView.post { gridView.setLiveCells(liveList) }
    }
}
