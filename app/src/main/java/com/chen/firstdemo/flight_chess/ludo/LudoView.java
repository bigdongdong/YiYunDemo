package com.chen.firstdemo.flight_chess.ludo;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;


import com.chen.firstdemo.R;
import com.chen.firstdemo.utils.MyUtils;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 * create by chenxiaodong on 2020/11/23
 * ludo's view
 */
public class LudoView extends ViewGroup implements LudoViewService {
    /*常量设置*/
    private final String TAG = "FlightChessViewTAG" ;
    private final int DURATION_FLY_CELL = 400 ; //每一格飞行的时间
    private final int DURATION_CRASH = 800 ; //坠机的时间
    private Context context ;

    private int W, H ;
    private int cw; //单元宽度
    private final int CELL_INSET ; //细胞的inset

    private Rect[] camps; //阵营
    private Rect[][] aprons ; //停机坪
    private Rect[] cells; //细胞
    private Rect[] wins ; //胜利区域

    /*棋子*/
    private View[][] chess ;

    private MediaPlayer mediaPlayer ;
    private boolean isVoice = true ;

    /*api*/
    private Plane[] preheatPlanes ; //预热的飞机
    private ChessClickListener cclietener ; //棋子点击的监听
    private AnimatorSet flyAnimators ; //飞机飞行的动画
    private AnimatorSet crashAnimators ; //飞机坠机的动画
    private ValueAnimator preheatAnimator; //飞机预热的动画
    private HashMap<Integer,Plane[]> folds = new HashMap<>(); //飞机叠加缓存

    private LudoLayoutListener ludoLayoutListener ;

    public LudoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context ;

        CELL_INSET = dip2px(1);
        this.setWillNotDraw(false);
        this.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        this.setBackgroundResource(R.drawable.bg_ludo_chessboard);

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if(W != getMeasuredWidth()){
            H = W = getMeasuredWidth();
            cw = W / 15 ;

            computeCamps();
            computeAprons();
            computeCells();
            computeWins();

            if(ludoLayoutListener != null){
                ludoLayoutListener.layoutEnd();
            }
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
        //计算阵营的缩进 inset:6cw = 32.75:150
        final int campInset = (int) (cw*6*32.75f/150);
        //计算停机坪的边长 len:6cw = 30:150 ;
        final int apronLen = (int) (cw*6*30f/150);
        //停机坪rect偏移 inset:6cw = 2.5:150
        final int apronInset = (int) (cw*6*1.5f/150);
        for (int i = 0; i < 4; i++) {
            Rect rect = new Rect(camps[i]);
            rect.inset(campInset,campInset);//阵营缩进后可以四边贴合停机坪

            /*分成四个小块*/
            /*第一块*/
            Rect temp = new Rect(rect.left,rect.top,rect.left+apronLen,rect.top+apronLen);
            temp.inset(apronInset,apronInset);
            aprons[i][0] = temp ;
            /*第二块*/
            temp = new Rect(rect.right-apronLen,rect.top,rect.right,rect.top+apronLen);
            temp.inset(apronInset,apronInset);
            aprons[i][1] = temp;
            /*第三块*/
            temp = new Rect(rect.left,rect.bottom-apronLen,rect.left+apronLen,rect.bottom);
            temp.inset(apronInset,apronInset);
            aprons[i][2] = temp;
            /*第四块*/
            temp = new Rect(rect.right-apronLen,rect.bottom-apronLen,rect.right,rect.bottom);
            temp.inset(apronInset,apronInset);
            aprons[i][3] = temp;
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
                temp[i][j].inset(CELL_INSET,CELL_INSET);
                cells[i*18+j] = temp[i][j];
            }
        }
    }
    /*计算4个胜利区域*/
    private void computeWins(){
        wins = new Rect[4];
        wins[0] = new Rect(cw*6,cw*7,cw*7,cw*8);
        wins[1] = new Rect(cw*7,cw*6,cw*8,cw*7);
        wins[2] = new Rect(cw*7,cw*8,cw*8,cw*9);
        wins[3] = new Rect(cw*8,cw*7,cw*9,cw*8);
    }

    /*获取飞机飞行的路径对应的属性动画*/
    private synchronized AnimatorSet getFlyAnimators(Rect[] rects ,final View target){
        if(rects == null || rects.length < 2){
            return null;
        }

        flyAnimators = new AnimatorSet();
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
            animator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    musicChessMove();
                }

                @Override
                public void onAnimationEnd(Animator animation) {

                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            animator.setDuration(DURATION_FLY_CELL);
            animator.setInterpolator(new DecelerateInterpolator());
            os[i] = animator ;
        }
        flyAnimators.playSequentially(os);
        target.bringToFront(); //飞行中的view默认在最上层
        return flyAnimators ;
    }
    /*坠机的动画*/
    private synchronized AnimatorSet getCrashAnimators(Plane[] planes){
        if(planes == null ||planes.length == 0){
            return null;
        }

        crashAnimators = new AnimatorSet();
        ObjectAnimator[] os = new ObjectAnimator[planes.length];
        for (int i = 0; i < planes.length; i++) {
            final Plane plane = planes[i];
            final ObjectAnimator animator = new ObjectAnimator();
            final int camp = plane.getCampIndex();
            final int apron = plane.getApronIndex();
            View target = chess[plane.getCampIndex()][plane.getApronIndex()];
            Rect[] rects = new Rect[2];
            rects[0] = new Rect(target.getLeft(),target.getTop(),target.getRight(),target.getBottom());
            rects[1] = new Rect(aprons[camp][apron]);
            animator.setObjectValues((Object[])rects);
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
            animator.setDuration(DURATION_CRASH);
            animator.setInterpolator(new DecelerateInterpolator());
            os[i] = animator ;
        }

        crashAnimators.playTogether(os);
        return crashAnimators ;
    }

    private void musicChessMove(){
        if(!isVoice){
            return;
        }
        if(mediaPlayer == null){
            mediaPlayer = MediaPlayer.create(context,R.raw.chess_move);
        }
        mediaPlayer.start();
    }

    /*重置动画*/
    private void resetAnimators(){
        try {
            if(flyAnimators != null && flyAnimators.isRunning()){
                flyAnimators.end();
            }
            if(preheatAnimator != null && preheatAnimator.isRunning()){
                preheatAnimator.end();
            }
            if(crashAnimators != null && crashAnimators.isRunning()){
                crashAnimators.end();
            }
        }catch (Exception e){}
    }

    private void unFold(final Plane plane){
        int position = 0;
        int I = 0; //plane 在 planes中的位置
        Iterator<Map.Entry<Integer, Plane[]>> it = folds.entrySet().iterator();
        Find:
        while (it.hasNext()){
            Map.Entry<Integer, Plane[]> entry = it.next();
            Plane[] planes = entry.getValue();
            if(planes == null || planes.length == 0){
                it.remove();
                continue;
            }
            for (int i = 0; i < planes.length; i++) {
                Plane plane1 = planes[i];
                if(plane.equals(plane1)){
                    position = entry.getKey(); //找到了
                    I = i ;
                    break Find;
                }
            }
        }

        /*当前飞机*/
        if(position == 0){
            return;
        }

        /*需要解叠加*/
        Plane[] olds = folds.get(position);
        if(olds.length == 1){ //出错情况，正常情况下叠加缓存必len>=2
            folds.remove(position);
        }else if(olds.length == 2){
            folds.remove(position);
            //安置剩下的那架飞机
            final Plane otherPlane = olds[I^1];
            View target = chess[otherPlane.getCampIndex()][otherPlane.getApronIndex()];
            Rect rect = new Rect(cells[position -1]);
            target.layout(rect.left,rect.top,rect.right,rect.bottom);
        }else{
            Plane[] news = new Plane[olds.length -1];
            for (int i = 0; i < olds.length; i++) {
                if(i < I){
                    news[i] = olds[i];
                }else if(i > I){
                    news[i-1] = olds[i];
                }
            }

            /*重新走叠加逻辑*/
            fold(news,position);
        }
    }

    @Override
    public void reset(@NotNull int[] camps) {
        resetAnimators();

        this.removeAllViews();
        chess = new View[4][4];

        for (int k = 0; k < camps.length; k++) {
            final int i = camps[k]-1;
            int resId = 0;
            switch (i){
                case 0:
                    resId = R.drawable.icon_ludo_chess_green ;
                    break;
                case 1:
                    resId = R.drawable.icon_ludo_chess_yellow ;
                    break;
                case 2:
                    resId = R.drawable.icon_ludo_chess_red ;
                    break;
                case 3:
                    resId = R.drawable.icon_ludo_chess_blue ;
                    break;
            }
            for (int j = 0; j < 4; j++) {
                Rect rect = new Rect(aprons[i][j]);

                LayoutParams p = new LayoutParams(cw,cw);
                View v = new View(context);
                v.setLayoutParams(p);
                v.setBackgroundResource(resId);
                this.addView(v);
                chess[i][j] = v ; //封装棋子view
                v.layout(rect.left,rect.top,rect.right,rect.bottom);

                final int curCamp = i ;
                final int curApron = j ;
                v.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MyUtils.viewClickCheck(v);
                        /*判断该棋子是否预热了*/
                        boolean isContains = false ;
                        Plane plane = null ;
                        if(preheatPlanes != null && preheatPlanes.length > 0){
                            for (int k = 0; k < preheatPlanes.length; k++) {
                                plane = preheatPlanes[k];
                                if(plane != null){
                                    int camp = plane.getCampIndex();
                                    int apron = plane.getApronIndex();
                                    isContains = ( camp == curCamp && apron == curApron );
                                    if(isContains){
                                        break ;
                                    }
                                }
                            }
                        }

                        /*预热了*/
                        if(isContains && cclietener != null){
                            if(cclietener.onClick(plane,v)){
                                preheatPlanes = null ;
                            }
                        }

                    }
                });
            }
        }
    }

    @Override
    public void set(Plane plane, int position) {
        resetAnimators();

        View target = chess[plane.getCampIndex()][plane.getApronIndex()];
        if(target.getParent() == null){
            return;
        }

        Rect rect ;
        if(position == -1){
            //该棋子已经win
            this.removeView(target);
            /*插一朵小红旗*/
            rect = new Rect(aprons[plane.getCampIndex()][plane.getApronIndex()]);
            ImageView iv = new ImageView(context);
            ViewGroup.LayoutParams vp = new LayoutParams(rect.width(),rect.height());
            iv.setLayoutParams(vp);
            iv.setImageResource(R.drawable.icon_ludo_chess_finish);
            this.addView(iv);
            iv.layout(rect.left,rect.top,rect.right,rect.bottom);
        }else{
            //棋子还在空中
            rect = new Rect(cells[position -1]);
            target.layout(rect.left,rect.top,rect.right,rect.bottom);
        }


    }

    @Override
    public void preheat(@Nullable Plane[] planes) {
        resetAnimators();

        preheatPlanes = planes ;

        /*棋子发光，代表可点击*/
        if(preheatPlanes == null || preheatPlanes.length == 0){
            return ;
        }
        final View[] targets = new View[preheatPlanes.length];
        for (int i = 0; i < preheatPlanes.length; i++) {
            Plane plane = preheatPlanes[i];
            targets[i] = chess[plane.getCampIndex()][plane.getApronIndex()];
            targets[i].bringToFront();
        }

        preheatAnimator = new ValueAnimator();
        preheatAnimator.setRepeatCount(-1);
        preheatAnimator.setFloatValues(1.2f,1.0f);
        preheatAnimator.setDuration(800);
        preheatAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                final float scale = (float) animation.getAnimatedValue();
                for (View v : targets){
                    v.setScaleX(scale);
                    v.setScaleY(scale);
                }
            }
        });
        preheatAnimator.start();

    }

    @Override
    public void flyOff(Plane plane, LudoView.ChessActionListener listener) {
        resetAnimators();

        final int camp = plane.getCampIndex() ;
        final int apron = plane.getApronIndex() ;
        final View target = chess[camp][apron];
        if(target.getParent() == null){
            return;
        }

        int toPosition = 0 ;
        switch (camp){
            case 0:
                toPosition = 56-1;
                break;
            case 1:
                toPosition = 6-1;
                break;
            case 2:
                toPosition = 49-1;
                break;
            case 3:
                toPosition = 35-1;
                break;
        }
        Rect[] rects = new Rect[2];
        rects[0] = new Rect(aprons[camp][apron]);
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
    public void fly(Plane plane, @NonNull int[] positions, LudoView.ChessActionListener listener) {
        resetAnimators();

        View target = chess[plane.getCampIndex()][plane.getApronIndex()];
        if(target.getParent() == null){
            return;
        }

        /*计算需要位移的矩形s*/
        Rect[] rects = new Rect[positions.length];
        /*顺序添加需要行进的cell的矩形*/
        for (int i = 0; i < positions.length; i++) {
            rects[i] = new Rect(cells[positions[i]-1]);
        }
        /*根据矩形s来进行属性动画*/
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

        /*解除叠加*/
        unFold(plane);
    }

    @Override
    public void crash(@NonNull final Plane[] planes, LudoView.ChessActionListener listener) {
        resetAnimators();

        AnimatorSet as = getCrashAnimators(planes);
        if(listener != null){
            as.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    listener.onFinish(null);
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

        /*如果多架飞机同时坠机，不需要清除对应position的叠加缓存，
            因为下一次触发fold时会替换该position的叠加数据*/
    }

    @Override
    public void fold(@NonNull Plane[] planes, int position) {
        if(preheatAnimator != null && preheatAnimator.isRunning()){
            preheatAnimator.end();
        }

        /*存入叠加缓存*/
        if(planes.length > 1){
            folds.put(position,planes);
        }else{
            folds.remove(position);
            return ;
        }

        /*进行叠加*/
        final int offsetX = 10 ; //偏移量
        final Rect rect = cells[position-1];
        for (int i = 0; i < planes.length; i++) {
            final Plane plane = planes[i];
            View v = chess[plane.getCampIndex()][plane.getApronIndex()];
            final int left = rect.left + i*offsetX ;
            v.layout(left,rect.top,left+(cw-CELL_INSET*2),rect.bottom); //rect的边长是(cw-CELL_INSET*2)
            v.bringToFront();
        }

    }

    @Override
    public void win(final Plane plane , @NotNull final int[] positions , LudoView.ChessActionListener listener) {
        resetAnimators();

        final int camp = plane.getCampIndex();
        final int apron = plane.getApronIndex();
        final View target = chess[camp][apron];
        if(target.getParent() == null){
            return;
        }

        Rect[] rects = new Rect[positions.length+1];
        for (int i = 0; i < positions.length; i++) {
            rects[i] = new Rect(cells[positions[i]-1]);
        }
        rects[positions.length] = new Rect(wins[camp]);

        AnimatorSet as = getFlyAnimators(rects,target);
        as.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                musicChessMove();
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                LudoView.this.removeView(target);

                /*插一朵小红旗*/
                Rect rect = new Rect(aprons[plane.getCampIndex()][plane.getApronIndex()]);
                ImageView iv = new ImageView(context);
                ViewGroup.LayoutParams vp = new LayoutParams(rect.width(),rect.height());
                iv.setLayoutParams(vp);
                iv.setImageResource(R.drawable.icon_ludo_chess_finish);
                LudoView.this.addView(iv);
                iv.layout(rect.left,rect.top,rect.right,rect.bottom);

                if(listener != null){
                    listener.onFinish(plane);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        as.start();

        /*解除叠加*/
        unFold(plane);
    }

    /**
     * 设置音效的开关【默认开启】
     * @param isTurnOn true打开  false关闭
     */
    public void setVoice(boolean isTurnOn){
        isVoice = isTurnOn ;
    }

    /*设置棋子点击事件*/
    public void setChessClickListener(ChessClickListener cclietener){
        this.cclietener = cclietener ;
    }
    /*棋子点击的监听*/
    public interface ChessClickListener{
        boolean onClick(Plane plane, View v);
    }
    /*棋子操作完成的监听*/
    public interface ChessActionListener{
        void onFinish(Plane plane);
    }

    /*设置layout完成的监听*/
    public void setLudoLayoutListener(LudoLayoutListener ludoLayoutListener){
        this.ludoLayoutListener = ludoLayoutListener ;
    }
    public interface LudoLayoutListener{
        void layoutEnd();
    }

    public int dip2px(float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
