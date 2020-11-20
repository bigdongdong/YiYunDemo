package com.chen.firstdemo.flight_chess;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.Shape;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;

import com.chen.firstdemo.R;


public class FlightChessView extends ViewGroup implements ChessService {
    /*常量设置*/
    private final String TAG = "FlightChessViewTAG" ;
    private final int COLOR_CELL = Color.WHITE ;
    private final int COLOR_1 =  Color.parseColor("#00DD01") ;
    private final int COLOR_2 = Color.parseColor("#FEBC20");
    private final int COLOR_3 = Color.parseColor("#FE3150");
    private final int COLOR_4 = Color.parseColor("#1F9AF1");
    private final int INSET_CELL = 2 ;
    private final int RADIUS_CELL = 6 ;
    private final int RADIUS_BOARD ; //棋盘的圆角
    private final int DURATION_FLY_CELL = 500 ;
    private Context context ;

    private int W, H ;
    private int cw; //单元宽度

    private Rect[] camps; //阵营
    private Rect[][] aprons ; //停机坪
    private Rect[] cells; //细胞
    private Rect center ; //中心
    private Rect[] wins ; //胜利区域

    /*棋子*/
    private View[][] chess ;


    /*api*/
    private Plane[] preheatPlanes ; //预热的飞机
    private ChessClickListener cclietener ; //棋子点击的监听

    public FlightChessView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context ;

        this.setWillNotDraw(false);
        this.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        RADIUS_BOARD = dip2px(20);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if(W != getMeasuredWidth()){
            H = W = getMeasuredWidth();
            cw = W / 15 ;

            computeCamps();
            computeAprons();
            computeCells();
            computeCenter();
            resetChess();
            computeWins();

            ChessboardShape shape = new ChessboardShape();
            this.setBackground(new ShapeDrawable(shape));
        }
    }

    /*计算4个阵营*/
    private void computeCamps(){
        camps = new Rect[4];
        camps[0] = new Rect(0,0,cw*6,cw*6);
        camps[1] = new Rect(cw*9,0,W,cw*6);
        camps[2] = new Rect(0,cw*9,cw*6,H);
        camps[3] = new Rect(cw*9,cw*9,W,H);
    }
    /*计算16个停机坪*/
    private void computeAprons(){
        aprons = new Rect[4][4];
        for (int i = 0; i < 4; i++) {
            Rect rect = new Rect(camps[i]);
            rect.inset(cw,cw);

            /*分成四个小块*/

            /*第一块*/
            Rect temp ;
            temp = new Rect(rect.left,rect.top,rect.left+cw*2,rect.top+cw*2);
            temp.inset(cw/2,cw/2);
            aprons[i][0] = new Rect(temp);
            /*第二块*/
            temp = new Rect(rect.left+cw*2,rect.top,rect.left+cw*4,rect.top+cw*2);
            temp.inset(cw/2,cw/2);
            aprons[i][1] = new Rect(temp);
            /*第三块*/
            temp = new Rect(rect.left,rect.top+cw*2,rect.left+cw*2,rect.top+cw*4);
            temp.inset(cw/2,cw/2);
            aprons[i][2] = new Rect(temp);
            /*第四块*/
            temp = new Rect(rect.left+cw*2,rect.top+cw*2,rect.left+cw*4,rect.top+cw*4);
            temp.inset(cw/2,cw/2);
            aprons[i][3] = new Rect(temp);
        }
    }
    /*计算72个细胞*/
    private void computeCells(){
        Rect[][] temp = new Rect[4][18];

        for (int i = 0; i < 18; i++) {
            /*上象限的cells*/
            int surplus = i%3 ;
            int times = i/3 ;
            int left = cw*(6+surplus);
            int top = cw*times;
            temp[0][i] = new Rect(left,top,left+cw,top+cw);

            /*右象限的cells*/
            surplus = i%6 ;
            times = i/6 ;
            left = cw*(9+surplus);
            top = cw*(6+times);
            temp[1][i] = new Rect(left,top,left+cw,top+cw);

            /*下象限的cells*/
            surplus = i%3 ;
            times = i/3 ;
            left = cw*(6+surplus);
            top = cw*(9+times);
            temp[2][i] = new Rect(left,top,left+cw,top+cw);

            /*左象限的cells*/
            surplus = i%6 ;
            times = i/6 ;
            left = cw*surplus;
            top = cw*(6+times);
            temp[3][i] = new Rect(left,top,left+cw,top+cw);
        }

        cells = new Rect[72];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 18; j++) {
                cells[i*18+j] = temp[i][j];
            }
        }
    }
    /*计算中心区域*/
    private void computeCenter(){
        center = new Rect(cw*6,cw*6,cw*9,cw*9);
    }
    /*计算胜利区域*/
    private void computeWins(){
        wins = new Rect[4];
        wins[0] = new Rect(cw*6,cw*7,cw*7,cw*8);
        wins[1] = new Rect(cw*7,cw*6,cw*8,cw*7);
        wins[2] = new Rect(cw*7,cw*8,cw*8,cw*9);
        wins[3] = new Rect(cw*8,cw*7,cw*9,cw*8);
    }

    /**
     * 获取飞机飞行的路径对应的属性动画
     * @param rects
     * @return
     */
    private AnimatorSet getFlyAnimators(Rect[] rects ,final View target){
        if(rects == null || rects.length < 2){
            return null;
        }

        AnimatorSet as = new AnimatorSet();
        ObjectAnimator[] os = new ObjectAnimator[rects.length-1];
        for (int i = 0; i < rects.length-1; i++) {
            final ObjectAnimator animator = new ObjectAnimator();
            animator.setObjectValues(rects[i],rects[i+1]);
            animator.setEvaluator(new TypeEvaluator<Rect>() {
                @Override
                public Rect evaluate(float fraction, Rect startValue, Rect endValue) {
                    final int left = (int) (startValue.left + fraction*(endValue.left-startValue.left));
                    final int top = (int) (startValue.top + fraction*(endValue.top-startValue.top));
                    final int right = (int) (startValue.right + fraction*(endValue.right-startValue.right));
                    final int bottom = (int) (startValue.bottom + fraction*(endValue.bottom-startValue.bottom));
                    return new Rect(left,top,right,bottom);
                }
            });
            animator.setTarget(target);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    View target = (View) animator.getTarget();
                    Rect rect = (Rect) animation.getAnimatedValue();
                    target.layout(rect.left,rect.top,rect.right,rect.bottom);
                }
            });
            animator.setDuration(DURATION_FLY_CELL);
            animator.setInterpolator(new DecelerateInterpolator());
            os[i] = animator ;
        }
        as.playSequentially(os);
        return as ;
    }

    /*重置16个棋子*/
    private void resetChess(){
        this.removeAllViews();
        chess = new View[4][4];

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                Rect rect = new Rect(aprons[i][j]);

                ViewGroup.LayoutParams p = new ViewGroup.LayoutParams(cw,cw);
                View v = new View(context);
                v.setLayoutParams(p);
                v.setBackgroundResource(R.mipmap.chess);
                this.addView(v);
                chess[i][j] = v ; //封装棋子view
                v.layout(rect.left,rect.top,rect.right,rect.bottom);

                final int curCamp = i ;
                final int curApron = j ;
                v.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        /*判断该棋子是否预热了*/
                        boolean isContains = false ;
                        if(preheatPlanes != null && preheatPlanes.length > 0){
                            for (int k = 0; k < preheatPlanes.length; k++) {
                                final Plane plane = preheatPlanes[k];
                                if(plane != null){
                                    int camp = plane.getCamp();
                                    int apron = plane.getApron();
                                    isContains = ( camp == curCamp && apron == curApron );
                                    if(isContains){
                                        break ;
                                    }
                                }
                            }
                        }

                        /*预热了*/
                        if(isContains && cclietener != null){
                            cclietener.onClick(new Plane(curCamp,curApron));
                        }

                    }
                });
            }
        }
    }

    @Override
    public void reset() {
        resetChess();
    }

    @Override
    public void set(Plane plane, int position) {
        View v = chess[plane.getCamp()][plane.getApron()];
        Rect rect = new Rect(cells[position]);
        v.layout(rect.left,rect.top,rect.right,rect.bottom);
    }

    @Override
    public void preheat(Plane[] planes) {
        preheatPlanes = planes ;
    }

    @Override
    public void flyOff(Plane plane,FlightChessView.ChessActionListener listener) {
        final int camp = plane.getCamp() ;
        final int apron = plane.getApron() ;
        final View target = chess[camp][apron];
        int toPosition = 0 ;
        switch (camp){
            case 0:
                toPosition = 56-1;
                break;
            case 1:
                toPosition = 6-1;
                break;
            case 2:
                toPosition = 35-1;
                break;
            case 3:
                toPosition = 49-1;
                break;
        }
        Rect[] rects = new Rect[2];
        rects[0] = new Rect(target.getLeft(),target.getTop(),target.getRight(),target.getBottom());
        rects[1] = cells[toPosition];
        AnimatorSet as = getFlyAnimators(rects,target);
        if(listener != null){
            as.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    listener.onFinish(plane);
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
        }
        as.start();
    }

    @Override
    public void fly(Plane plane, int[] positions,FlightChessView.ChessActionListener listener) {
        if(positions != null && positions.length > 0){
            /*计算需要位移的矩形s*/
            Rect[] rects = new Rect[positions.length];
            /*顺序添加需要行进的cell的矩形*/
            for (int i = 0; i < positions.length; i++) {
                rects[i] = new Rect(cells[positions[i]-1]);
            }
            /*根据矩形s来进行属性动画*/
            AnimatorSet as = getFlyAnimators(rects,chess[plane.getCamp()][plane.getApron()]);
            if(listener != null){
                as.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        listener.onFinish(plane);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
            }
            as.start();
        }
    }

    @Override
    public void crash(Plane plane,FlightChessView.ChessActionListener listener) {

    }

    @Override
    public void fold(Plane[] planes) {

    }

    @Override
    public void win(Plane plane ,FlightChessView.ChessActionListener listener) {

    }

    /*棋盘背景*/
    private class ChessboardShape extends Shape {
        private Bitmap bgBitmap ;
        public ChessboardShape() {
            bgBitmap = BitmapFactory.decodeResource(context.getResources(),R.mipmap.bg_chess_board);
        }

        private final int[] CAMP_COLORS = new int[]{COLOR_1,COLOR_2,COLOR_3,COLOR_4};

        /*获取十字细胞区域裁剪path*/
        private Path getCrossPath(){
            Path path = new Path();
            path.reset();
            path.moveTo(cw*6,0);
            path.rLineTo(cw*3,0); //右3
            path.rLineTo(0,cw*6); //下6
            path.rLineTo(cw*6,0); //右6
            path.rLineTo(0,cw*3); //下3
            path.rLineTo(-cw*6,0); //左6
            path.rLineTo(0,cw*6); //下6
            path.rLineTo(-cw*3,0); //左3
            path.rLineTo(0,-cw*6); //上6
            path.rLineTo(-cw*6,0); //左6
            path.rLineTo(0,-cw*3); //上3
            path.rLineTo(cw*6,0); //右6
            path.close(); //上6
            return path ;
        }

        private Paint getCellPaint(){
            Paint paint = new Paint();
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(2);
            paint.setColor(Color.parseColor("#EBAE39"));
            paint.setStrokeCap(Paint.Cap.SQUARE);
            paint.setAntiAlias(true);
            return paint ;
        }

        private Paint getFillPaint(int color){
            Paint paint = new Paint();
            paint.setColor(color);
            paint.setStyle(Paint.Style.FILL);
            paint.setAntiAlias(true);
            return paint ;
        }

        /*TODO DELETE*/
        private Paint getTextPaint(){
            Paint paint = new Paint();
            paint.setStyle(Paint.Style.FILL_AND_STROKE);
            paint.setTextSize(20);
            paint.setColor(Color.BLACK);
            paint.setAntiAlias(true);
            return paint;
        }

        @Override
        public void draw(Canvas canvas, Paint paint) {
            canvas.drawBitmap(bgBitmap,null,new RectF(0,0,W,H),null);
            /*裁切整个棋盘的圆角*/
//            if(true){
//                Path path = new Path();
//                path.reset();
//                path.addRoundRect(new RectF(0,0,W,H),
//                        new float[]{RADIUS_BOARD,RADIUS_BOARD,RADIUS_BOARD,RADIUS_BOARD,0,0,0,0}, Path.Direction.CCW);
//                canvas.clipPath(path);
//            }

//            /*绘制四个细胞区域[线条绘制法]*/
//            canvas.save();
//            canvas.clipPath(getCrossPath());
//            canvas.drawColor(Color.WHITE);

//            /*绘制cells[线条绘制法]*/
//            final Paint cellPaint = getCellPaint();
//            for (int i = 0; i < 15; i++) {
//                canvas.drawLine(0,cw*i,W,cw*i,cellPaint);
//                canvas.drawLine(cw*i,0,cw*i,H,cellPaint);
//            }
//            canvas.restore();

            /*绘制阵营区域*/
//            for (int i = 0; i < 4; i++) {
//                RectF rectF = new RectF(camps[i]) ;
//                canvas.drawRect(rectF,getFillPaint(CAMP_COLORS[i]));
//
//                rectF.inset(cw/2,cw/2);
//                canvas.drawRect(rectF,getFillPaint(0x55111111));
//            }

            /*停机坪绘制*/
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    RectF rectF = new RectF(aprons[i][j]);
                    canvas.drawCircle(rectF.centerX(),rectF.centerY(),cw/2,getFillPaint(COLOR_CELL));
                    /*TODO  文字*/
                    final String str = i+"-"+j ;
                    canvas.drawText(str,rectF.centerX()-10, rectF.centerY()+10,getTextPaint());
                }
            }

            /*绘制cells*/
            /*绘制白块cells*/
//            for (int i = 0; i < 72; i++) {
//                RectF rectF = new RectF(cells[i]);
//                rectF.inset(INSET_CELL, INSET_CELL);
//                canvas.drawRoundRect(rectF, RADIUS_CELL, RADIUS_CELL,getFillPaint(COLOR_CELL));
//            }

            /*绘制有色块cells*/
//            final int[] camp1 = new int[]{56,62,63,64,65,66};
//            final int[] camp2 = new int[]{6,5,8,11,14,17};
//            final int[] camp3 = new int[]{49,50,47,44,41,38};
//            final int[] camp4 = new int[]{35,29,28,27,26,25};
//            final int[][] colors = new int[][]{camp1,camp2,camp3,camp4};
//
//            for (int i = 0; i < 4; i++) {
//                for (int j = 0; j < 6; j++) {
//                    RectF rectF = new RectF(cells[colors[i][j]-1]);
//                    rectF.inset(INSET_CELL, INSET_CELL);
//                    canvas.drawRect(rectF, getFillPaint(CAMP_COLORS[i]));
//                }
//            }

            /*TODO DELETE 绘制cells的坐标文字*/
//            for (int i = 0; i < 72; i++) {
//                Rect rectF = new Rect(cells[i]);
//                final String str = String.valueOf(i+ 1) ;
//                canvas.drawText(str,rectF.centerX()-10, rectF.centerY()+10,getTextPaint());
//            }

            /*绘制中心区域*/
//            if(true){
//                Path path = new Path();
//                Rect rect = new Rect(center) ;
//                /*左三角*/
//                path.reset();
//                path.moveTo(rect.left,rect.top);
//                path.lineTo(rect.centerX(),rect.centerY());
//                path.lineTo(rect.left,rect.bottom);
//                path.close();
//                canvas.save();
//                canvas.clipPath(path);
//                canvas.drawColor(CAMP_COLORS[0]);
//                canvas.restore();
//
//                /*上三角*/
//                path.reset();
//                path.moveTo(rect.left,rect.top);
//                path.lineTo(rect.centerX(),rect.centerY());
//                path.lineTo(rect.right,rect.top);
//                path.close();
//                canvas.save();
//                canvas.clipPath(path);
//                canvas.drawColor(CAMP_COLORS[1]);
//                canvas.restore();
//
//                /*下三角*/
//                path.reset();
//                path.moveTo(rect.left,rect.bottom);
//                path.lineTo(rect.centerX(),rect.centerY());
//                path.lineTo(rect.right,rect.bottom);
//                path.close();
//                canvas.save();
//                canvas.clipPath(path);
//                canvas.drawColor(CAMP_COLORS[2]);
//                canvas.restore();
//
//                /*右三角*/
//                path.reset();
//                path.moveTo(rect.right,rect.top);
//                path.lineTo(rect.centerX(),rect.centerY());
//                path.lineTo(rect.right,rect.bottom);
//                path.close();
//                canvas.save();
//                canvas.clipPath(path);
//                canvas.drawColor(CAMP_COLORS[3]);
//                canvas.restore();
//            }

            /*TODO 绘制胜利区域*/
            for (int i = 0; i < 4; i++) {
                RectF rectF = new RectF(wins[i]);
                canvas.drawRect(rectF, getFillPaint(0x33111111));
            }
        }
    }


    public void setChessClickListener(ChessClickListener cclietener){
        this.cclietener = cclietener ;
    }

    /*棋子点击的监听*/
    public interface ChessClickListener{
        void onClick(Plane plane);
    }

    /*棋子操作完成的监听*/
    public interface ChessActionListener{
        void onFinish(Plane plane);
    }


    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public int dip2px(float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
