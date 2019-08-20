package samuel.exercise.homequiz.gameoflife

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import samuel.exercise.homequiz.R

class GameOfLifeActivity : AppCompatActivity(), GameOfLifeContract.View {

    override lateinit var presenter: GameOfLifeContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
