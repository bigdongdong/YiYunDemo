package com.chen.firstdemo.weelview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

@SuppressLint("DrawAllocation")
class WheelView extends View {
    private final String TAG = "WheelView";
    private Context context;
    private Matrix matrix ;
    private Bitmap bitmap ;
    private int w , h ;


    private final int FONT_SIZE ; //文字的尺寸
    private final int GRID_HEIGHT ; //格子的高度
    private int lineOneY ; //第一条线的y坐标
    private int lineTwoY ; //第二条线的y坐标
    private Item[] items ;

    private final String[] cities = new String[]{"兰州","拉萨","来宾","莱芜","廊坊","乐山","凉山","连云港",
            "聊城","辽阳","辽源","丽江","临沧","临汾","临夏","临沂","林芝","丽水","六安","六盘水"};
//    private final String[] cities = new String[]{"333333","222222","111111","000000","111111","222222","333333"};

    public WheelView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        this.context = getContext();
        matrix = new Matrix();
        FONT_SIZE = sp2px(20);
        GRID_HEIGHT = FONT_SIZE + 40 ;

        setClickable(true);
        setWillNotDraw(true);

    }

    float lasty = 0f ;
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                lasty = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float nowy = ev.getY();
                float curOffsetY = nowy-lasty ;
                float tTotalOffsetY = curOffsetY + totalOffsetY ; //临时计算叠加后的总偏移量
//                if(tTotalOffsetY > critical[0] && tTotalOffsetY < critical[1]){
//                    totalOffsetY += curOffsetY ;
//                    matrix.setTranslate(0, totalOffsetY);
//                    invalidate();
//                }

                totalOffsetY += curOffsetY ;
                matrix.setTranslate(0, totalOffsetY);
                invalidate();

                lasty = nowy;
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                final int offset = Math.abs(totalOffsetY%GRID_HEIGHT);
                matrix.postTranslate(0,offset);
                postInvalidate();
                break;
            default:
                break;
        }
        return super.onTouchEvent(ev);

    }

//    private int sliderH; //滑块的高度
    private int[] critical = new int[2]; //滑块滑动的范围
    private int totalOffsetY = 0 ; //总偏移量
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        w = getMeasuredWidth() ;
        h = getMeasuredHeight() ;

        /*边框画笔*/
        Paint borderPaint = new Paint();
        borderPaint.setColor(Color.BLACK);
        borderPaint.setStrokeWidth(2);
        borderPaint.setStyle(Paint.Style.STROKE);
        /*文字画笔*/
        Paint charPaint = new Paint();
        charPaint.setStyle(Paint.Style.FILL);
        charPaint.setColor(Color.RED);
        charPaint.setAntiAlias(true);
        charPaint.setTextSize(FONT_SIZE);
        charPaint.setTextAlign(Paint.Align.CENTER);

        /*格子总数*/
        final int count = cities.length ;
        /*计算滑块slider的高度*/
        final int sliderH = GRID_HEIGHT * count;
        /*构造items*/
        items = new Item[count];
        Item it = null ;
        Rect rect = null;
        Point pt = null ;
        //矩形中心与基线的距离
        Paint.FontMetrics fontMetrics = charPaint.getFontMetrics();
        final int spaceFromRectCenterToBaseLine = (int) ((fontMetrics.bottom - fontMetrics.top)/2 - fontMetrics.bottom);
        for (int i = 0; i < count; i++) {
            rect = new Rect(0,GRID_HEIGHT*i,w,GRID_HEIGHT*(i+1));
            pt = new Point(rect.centerX(),rect.centerY()+spaceFromRectCenterToBaseLine);
            it = new Item(cities[i], rect , pt);
            items[i] = it ;
        }
        /*设置偏移界限和初始偏移量*/
//        int initOffsetY = 0 ;
//        if(sliderH < h){
//            //滑块填充不满
//            critical[0] = -(sliderH - h/2);
//            critical[1] = h/2 ;
//            initOffsetY = (h - sliderH)/2;
//        }else if(sliderH > h){
//            //滑块超出
//            critical[0] = -(sliderH - h);
//            critical[1] = h/2 ;
//            initOffsetY = -(sliderH - h)/2;
//        }
//        totalOffsetY = initOffsetY ;
//        matrix.setTranslate(0f,initOffsetY);
//        postInvalidate();

        /*bitmap构造*/
        bitmap = Bitmap.createBitmap(w,sliderH, Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(bitmap);
        /*对bitmap进行绘制*/
        Rect rect1 ;
        Point pt1 ;
        for (Item bean:items) {
            rect1 = bean.getRect() ;
            pt1 = bean.getCharBasePoint() ;
            /*绘制边框*/
//            canvas.drawRect(rect1,borderPaint);
            /*绘制文字*/
            canvas.drawText(bean.getChars(),pt1.x,pt1.y,charPaint);
        }

        /*两条基准线*/
        lineOneY = h / 2 - GRID_HEIGHT / 2 ;
        lineTwoY = h / 2 + GRID_HEIGHT / 2 ;
    }

    static class Item {
        private String chars ;
        private Rect rect ;
        private Point charBasePoint ; //文字基准点

        public Item(String chars, Rect rect, Point charBasePoint) {
            this.chars = chars;
            this.rect = rect;
            this.charBasePoint = charBasePoint;
        }

        public String getChars() {
            return chars;
        }

        public Rect getRect() {
            return rect;
        }

        public Point getCharBasePoint() {
            return charBasePoint;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmap(bitmap,matrix,null);

        Paint linePaint = new Paint();
        linePaint.setColor(0xFF111111);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeWidth(3);

        /*画两条线*/
        Path path = new Path();
        path.reset();
        path.moveTo(0,lineOneY);
        path.rLineTo(w,0);
        canvas.drawPath(path,linePaint);

        path.reset();
        path.moveTo(0,lineTwoY);
        path.rLineTo(w,0);
        canvas.drawPath(path,linePaint);
    }


    /**
     * sp转px
     * @return
     */
    public int sp2px(float spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, context.getResources().getDisplayMetrics());
    }
}
