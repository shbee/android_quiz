package samuel.exercise.homequiz.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.MotionEvent.INVALID_POINTER_ID
import android.view.View
import androidx.core.content.ContextCompat
import samuel.exercise.homequiz.R
import kotlin.math.min

class CellGridView : View {

    private val liveCellPaint: Paint
    private val deadCellPaint: Paint
    private var dimenSizeX = 3
    private var dimenSizeY = 3
    private var activePointerId = INVALID_POINTER_ID
    private var lastTouchCellIdx = -1
    private val liveCellSet = hashSetOf<Int>()

    var cellTouchListener: CellTouchListener? = null

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        liveCellPaint = Paint().apply { color = ContextCompat.getColor(context, R.color.colorAccent) }
        deadCellPaint = Paint().apply { color = Color.WHITE }
    }

    fun setDimenSize(sizeX: Int, sizeY: Int) {
        dimenSizeX = sizeX
        dimenSizeY = sizeY
        invalidate()
    }

    fun setLiveCells(set: Set<Int>) {
        liveCellSet.clear()
        liveCellSet.addAll(set)
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val height = measuredHeight.toFloat()
        val cellHeight = measuredHeight.toFloat() / dimenSizeY
        val width = measuredWidth.toFloat()
        val cellWidth = measuredWidth.toFloat() / dimenSizeX

        // Draw cell grid
        for (dy in 0 until dimenSizeY) {
            for (dx in 0 until dimenSizeX) {
                val left = dx * cellWidth
                val top = dy * cellHeight
                var right = left + cellWidth
                if (width - right < cellWidth) right = width
                var bottom = top + cellHeight
                if (height - bottom < cellHeight) bottom = height

                val liveOrDead = liveCellSet.contains(getCellIndex(dx, dy))
                canvas.drawRect(
                    left, top, right, bottom,
                    if (liveOrDead) liveCellPaint else deadCellPaint
                )
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean = when (event.actionMasked) {
        MotionEvent.ACTION_DOWN -> {
            event.actionIndex.also { pointerIndex ->
                activePointerId = event.getPointerId(pointerIndex)
                point2CellIndex(event.getX(pointerIndex), event.getY(pointerIndex)).let {
                    lastTouchCellIdx = it
                    onCellTouch(event, lastTouchCellIdx)
                }
            }
            true
        }
        MotionEvent.ACTION_MOVE -> {
            event.findPointerIndex(activePointerId).takeIf { it >= 0 }?.also { pointerIndex ->
                point2CellIndex(event.getX(pointerIndex), event.getY(pointerIndex))
                    .takeIf {
                        it != lastTouchCellIdx
                    }?.let {
                        lastTouchCellIdx = it
                        onCellTouch(event, lastTouchCellIdx)
                    }
            }
            true
        }
        MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
            activePointerId = INVALID_POINTER_ID
            true
        }
        else -> super.onTouchEvent(event)
    }

    private fun point2CellIndex(px: Float, py: Float): Int {
        var dx = (px * dimenSizeX / measuredWidth).toInt()
        var dy = (py * dimenSizeY / measuredHeight).toInt()
        dx = min(dx, dimenSizeX - 1)
        dy = min(dy, dimenSizeY - 1)
        return getCellIndex(dx, dy)
    }

    private fun getCellIndex(dx: Int, dy: Int) = dy * dimenSizeY + dx

    private fun onCellTouch(event: MotionEvent, cellIndex: Int) {
        Log.d(TAG, "onCellTouch, ${event.actionMasked}, index=$cellIndex")
        cellTouchListener?.onCellTouched(event, cellIndex)
    }

    interface CellTouchListener {
        fun onCellTouched(event: MotionEvent, cellIndex: Int)
    }

    companion object {
        const val TAG = "CellGridView"
    }
}