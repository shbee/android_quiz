package samuel.exercise.homequiz.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import samuel.exercise.homequiz.R

class CellGridView : View {

    private val liveCellPaint: Paint
    private val deadCellPaint: Paint
    private var dimenSizeX = 3
    private var dimenSizeY = 3

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        setBackgroundColor(Color.TRANSPARENT) // must set default background to enable onDraw callback

        liveCellPaint = Paint().apply { color = ContextCompat.getColor(context, R.color.colorAccent) }
        deadCellPaint = Paint().apply { color = Color.WHITE }
    }

    fun setDimenSize(sizeX: Int, sizeY: Int) {
        dimenSizeX = sizeX
        dimenSizeY = sizeY
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
            var liveOrDead = dy % 2 == 0
            for (dx in 0 until dimenSizeX) {
                val left = dx * cellWidth
                val top = dy * cellHeight
                var right = left + cellWidth
                if (width - right < cellWidth) right = width
                var bottom = top + cellHeight
                if (height - bottom < cellHeight) bottom = height

                canvas.drawRect(
                    left, top, right, bottom,
                    if (liveOrDead) liveCellPaint else deadCellPaint
                )
                liveOrDead = !liveOrDead
            }
        }
    }

}