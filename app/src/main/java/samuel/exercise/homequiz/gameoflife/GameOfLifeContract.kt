package samuel.exercise.homequiz.gameoflife

import samuel.exercise.homequiz.BasePresenter
import samuel.exercise.homequiz.BaseView

interface GameOfLifeContract {

    interface View : BaseView<Presenter> {

        fun updateLiveCells(cellSet: Set<Int>)
    }

    interface Presenter : BasePresenter {

        fun resumeTransition()

        fun pauseTransition()

        fun addLiveCell(cellIndex: Int)
    }

    companion object {
        const val DIMEN_SIZE_X = 7
        const val DIMEN_SIZE_Y = 7
    }

}