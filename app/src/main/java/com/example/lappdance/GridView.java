package com.example.lappdance;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;


public class GridView extends View implements GestureDetector.OnGestureListener {

    private Paint paint = new Paint( Paint.ANTI_ALIAS_FLAG );
    private Grid grid = Grid.getGrid( GridLevel.GOOD );
    private GestureDetector gestureDetector;

    private float gridWidth;
    private float gridSeparatorSize;
    private float cellWidth;
    private float buttonWidth;
    private float buttonRadius;
    private float buttonMargin;

    private Bitmap eraserBitmap;
    private Bitmap pencilBitmap;
    private Bitmap littlePencilBitmap;


    public GridView(Context context) {
        super(context);
        this.init();
    }

    public GridView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.init();
    }

    private void init() {
        gestureDetector = new GestureDetector( getContext(),  this );

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        // Grid size
        gridSeparatorSize = (w / 9f) / 20f;

        gridWidth = w;
        cellWidth = gridWidth / 9f;
        buttonWidth = w / 7f;
        buttonRadius = buttonWidth / 10f;
        buttonMargin = (w - 6*buttonWidth) / 7f;


        // images
        eraserBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.eraser);
        eraserBitmap = Bitmap.createScaledBitmap(eraserBitmap,
                (int) (buttonWidth*0.8f), (int) (buttonWidth*0.8f), false);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.pencil);
        pencilBitmap = Bitmap.createScaledBitmap(bitmap,
                (int) (buttonWidth*0.8f), (int) (buttonWidth*0.8), false);
        littlePencilBitmap = Bitmap.createScaledBitmap(bitmap,
                (int) (buttonWidth/3), (int) (buttonWidth/3), false);

    }

    @Override
    protected void onDraw(Canvas canvas) {

        // cells

        paint.setTextAlign( Paint.Align.CENTER );

        for( int y=0; y<9; y++ ) {
            for( int x=0; x<9; x++ ) {
                int backgroundColor = Color.WHITE;

                if ( grid.cells[y][x].isInitial ) {
                    backgroundColor = 0xFFF0F0F0;
                }


                paint.setColor( backgroundColor );
                canvas.drawRect(x * cellWidth,
                        y * cellWidth ,
                        (x+1) * cellWidth,
                        (y+1) * cellWidth,
                        paint);

                if (grid.cells[y][x].assumedValue != 0) {

                    paint.setColor(0xFF000000);
                    paint.setTextSize( cellWidth*0.7f );
                    canvas.drawText("" + grid.cells[y][x].assumedValue,
                            x * cellWidth + cellWidth / 2,
                            y * cellWidth + cellWidth * 0.75f, paint);

                } else {

                    paint.setTextSize( cellWidth*0.33f );
                    paint.setColor( 0xFFA0A0A0 );
                    if ( grid.cells[y][x].marks[0] ) {
                        canvas.drawText("1",
                                x * cellWidth + cellWidth * 0.2f,
                                y * cellWidth + cellWidth * 0.3f, paint);
                    }
                    if ( grid.cells[y][x].marks[1] ) {
                        canvas.drawText("2",
                                x * cellWidth + cellWidth * 0.5f,
                                y * cellWidth + cellWidth * 0.3f, paint);
                    }
                    if ( grid.cells[y][x].marks[2] ) {
                        canvas.drawText("3",
                                x * cellWidth + cellWidth * 0.8f,
                                y * cellWidth + cellWidth * 0.3f, paint);
                    }
                    if ( grid.cells[y][x].marks[3] ) {
                        canvas.drawText("4",
                                x * cellWidth + cellWidth * 0.2f,
                                y * cellWidth + cellWidth * 0.6f, paint);
                    }
                    if ( grid.cells[y][x].marks[4] ) {
                        canvas.drawText("5",
                                x * cellWidth + cellWidth * 0.5f,
                                y * cellWidth + cellWidth * 0.6f, paint);
                    }
                    if ( grid.cells[y][x].marks[5] ) {
                        canvas.drawText("6",
                                x * cellWidth + cellWidth * 0.8f,
                                y * cellWidth + cellWidth * 0.6f, paint);
                    }
                    if ( grid.cells[y][x].marks[6] ) {
                        canvas.drawText("7",
                                x * cellWidth + cellWidth * 0.2f,
                                y * cellWidth + cellWidth * 0.9f, paint);
                    }
                    if ( grid.cells[y][x].marks[7] ) {
                        canvas.drawText("8",
                                x * cellWidth + cellWidth * 0.5f,
                                y * cellWidth + cellWidth * 0.9f, paint);
                    }
                    if ( grid.cells[y][x].marks[8] ) {
                        canvas.drawText("9",
                                x * cellWidth + cellWidth * 0.8f,
                                y * cellWidth + cellWidth * 0.9f, paint);
                    }
                }
            }
        }

        // lines
        paint.setColor( Color.GRAY );
        paint.setStrokeWidth( gridSeparatorSize/2 );
        for( int i=0; i<=9; i++ ) {
            canvas.drawLine( i*cellWidth, 0, i*cellWidth, cellWidth*9, paint );
            canvas.drawLine( 0,i*cellWidth, cellWidth*9, i*cellWidth, paint );
        }
        paint.setColor( Color.BLACK );
        paint.setStrokeWidth( gridSeparatorSize );
        for( int i=0; i<=3; i++ ) {
            canvas.drawLine( i*(cellWidth*3), 0, i*(cellWidth*3), cellWidth*9, paint );
            canvas.drawLine( 0,i*(cellWidth*3), cellWidth*9, i*(cellWidth*3), paint );
        }


        // selected cell
        if ( grid.currentCellX != -1 && grid.currentCellY != -1 ) {
            paint.setColor( 0xFF_30_3F_9F );
            paint.setStrokeWidth( gridSeparatorSize * 1.5f );
            paint.setStyle( Paint.Style.STROKE );
            canvas.drawRect( grid.currentCellX * cellWidth,
                    grid.currentCellY * cellWidth,
                    (grid.currentCellX+1) * cellWidth,
                    (grid.currentCellY+1) * cellWidth,
                    paint);
            paint.setStyle( Paint.Style.FILL_AND_STROKE );
            paint.setStrokeWidth( 1 );
        }

        // Buttons

        float buttonsTop = 9*cellWidth + gridSeparatorSize/2;

        paint.setColor(0xFFC7DAF8);
        canvas.drawRect(0, buttonsTop, gridWidth, getHeight(), paint);

        float buttonLeft = buttonMargin;
        float buttonTop = buttonsTop + buttonMargin;

        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(buttonWidth * 0.7f);

        for (int i = 1; i <= 9; i++) {
            paint.setColor( 0xFFFFFFFF );
            RectF rectF = new RectF(buttonLeft, buttonTop,
                    buttonLeft + buttonWidth, buttonTop + buttonWidth);
            canvas.drawRoundRect(rectF, buttonRadius, buttonRadius, paint);

            paint.setColor( 0xFF000000 );
            canvas.drawText("" + i, rectF.centerX(), rectF.top + rectF.height() * 0.75f, paint);

            if (i != 6) {
                buttonLeft += buttonWidth + buttonMargin;
            } else {
                buttonLeft = buttonMargin;
                buttonTop += buttonWidth + buttonMargin;
            }
        }

        int imageWidth = (int) (buttonWidth * 0.8f);
        int imageMargin = (int) (buttonWidth * 0.1f);

        // eraser
        paint.setColor(0xFFFFFFFF);
        RectF rectF = new RectF( buttonLeft, buttonTop,
                buttonLeft + buttonWidth, buttonTop + buttonWidth );
        canvas.drawRoundRect( rectF, buttonRadius, buttonRadius, paint );
        canvas.drawBitmap( eraserBitmap,
                buttonLeft + imageMargin, buttonTop + imageMargin, paint );
        buttonLeft += buttonWidth + buttonMargin;

        // pencil
        paint.setColor(0xFFFFFFFF);
        rectF = new RectF( buttonLeft, buttonTop, buttonLeft + buttonWidth, buttonTop + buttonWidth );
        canvas.drawRoundRect( rectF, buttonRadius, buttonRadius, paint );
        Bitmap bitmap = grid.bigNumber ? pencilBitmap : littlePencilBitmap;
        canvas.drawBitmap( bitmap, buttonLeft + imageMargin, buttonTop + imageMargin, paint );

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }


    @Override
    public boolean onDown(MotionEvent e) {
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        RectF rectF;


        if ( e.getY() < gridWidth ) {
            int cellX = (int)( e.getX() / cellWidth );
            int cellY = (int)( e.getY() / cellWidth );

            grid.currentCellX = cellX;
            grid.currentCellY = cellY;
            postInvalidate();
            return true;
        }

        float buttonLeft = buttonMargin;
        float buttonTop = 9 * cellWidth + gridSeparatorSize / 2;

        if ( grid.currentCellX != -1 && grid.currentCellY != -1 ) {


            for (int i = 1; i <= 9; i++) {
                rectF = new RectF(buttonLeft, buttonTop, buttonLeft + buttonWidth, buttonTop + buttonWidth);
                if (rectF.contains(e.getX(), e.getY())) {
                    grid.pushValue(i);
                    postInvalidate();
                    return true;
                }

                if (i != 6) {
                    buttonLeft += buttonWidth + buttonMargin;
                } else {
                    buttonLeft = buttonMargin;
                    buttonTop += buttonWidth + buttonMargin;
                }
            }

            // eraser button
            rectF = new RectF(buttonLeft, buttonTop, buttonLeft + buttonWidth, buttonTop + buttonWidth);
            if (rectF.contains(e.getX(), e.getY())) {
                grid.clearCell();
                this.invalidate();
                return true;
            }
            buttonLeft += buttonWidth + buttonMargin;
        }

        //  pencil button
        rectF = new RectF( buttonLeft, buttonTop, buttonLeft+buttonWidth, buttonTop+buttonWidth );
        if ( rectF.contains( e.getX(), e.getY() ) ) {
            grid.bigNumber = ! grid.bigNumber;
            this.invalidate();
            return true;
        }

        return true;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }
}
