package com.chen.firstdemo.matrix_demo.matrix_study;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class MatrixStudyView extends View {
    private String TAG = "MatrixStudyView";
    private Matrix matrix ;
    public MatrixStudyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        matrix = new Matrix();
//        matrix.setTranslate(2,2);
//        log(matrix);
        matrix.postTranslate(3,3);
        log(matrix);
//        matrix.setTranslate(6,6);
//        log(matrix);
//        matrix.preTranslate(2,2);
//        log(matrix);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }

    private void log(Matrix matrix){
        StringBuilder sb = new StringBuilder("\n");
        final float[] ms = new float[9];
        matrix.getValues(ms);
        sb.append("[ ");
        for (int i = 0; i < 3; i++) {
            sb.append(ms[i]);
            sb.append(i == 2 ? " ]\n":"  ");
        }
        sb.append("[ ");
        for (int i = 3; i < 6; i++) {
            sb.append(ms[i]);
            sb.append(i == 5 ? " ]\n":"  ");
        }
        sb.append("[ ");
        for (int i = 6; i < 9; i++) {
            sb.append(ms[i]);
            sb.append(i == 8 ? " ]\n":"  ");
        }

        sb.append("\n");

        Log.i(TAG, "MatrixStudyView: "+sb);
    }
}
