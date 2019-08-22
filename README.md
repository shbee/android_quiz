# Home Quiz #
Home quiz - **[Containing 7s]**, **[Game of life]**
Implement by android application

### Containing 7s ###
The algorithm can be found in object `samuel.exercise.homequiz.q1.Containing7s`

### Game of life ###

**CellGridView `samuel.exercise.homequiz.view.CellGridView`**
Custom view to draw cell grid and detect touch on cell event.
The live cells will be updated by calling `setLiveCells(set: Set<Int>)` and draw on canvas. Callback `onCellTouched(event: MotionEvent, cellIndex: Int)` will be triggered to get cell index when user touch on CellGridView

**GameOfLifePresenter `samuel.exercise.homequiz.gameoflife.GameOfLifePresenter`**
Presenter start ticking through `Observable.interval()` when activity call `subscribe()` on resume. And `transitCellState(cellSet: Set<Int>)` is called in each tick to compute cell state for next generation.
Activity call `addLiveCell(cellIndex: Int)` to mark cell alive, or call `clearAll()` to clean all live cell.

**Architecture**
The program build with **Model-View-Presenter (MVP)**. In the pattern, I can implement and test UI flow(View) and logic(Presenter) separately.
First step of development is to implement view for draw canvas and touch event. After view created, presenter is implemented for cell live logic. Once presenter is done, interface help binding view and presenter, and the app is compelete naturally.
