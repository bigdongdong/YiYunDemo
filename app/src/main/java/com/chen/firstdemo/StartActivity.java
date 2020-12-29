package com.chen.firstdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.chen.firstdemo.banner.Banner2Activity;
import com.chen.firstdemo.bitmap_mix.BitmapMixActivity;
import com.chen.firstdemo.bottom_tabs.bottom_tab_demo_2.BottomTab2Activity;
import com.chen.firstdemo.charts.ChartsActivity;
import com.chen.firstdemo.clipview.ClipViewDemoActivity;
import com.chen.firstdemo.dialog.douyindialog.douyin.DouYinDialogActivity;
import com.chen.firstdemo.diy_view_demo.DIYViewActivity;
import com.chen.firstdemo.e_number.ENumberActivity;
import com.chen.firstdemo.flight_chess.FlightChessActivity;
import com.chen.firstdemo.gauss_blur_demo.GaussBlurActivity;
import com.chen.firstdemo.giftanim.SendGiftAnimActivity;
import com.chen.firstdemo.handler.HandlerActivity;
import com.chen.firstdemo.huanXinSDK.HuanXinSDKActivity;
import com.chen.firstdemo.largeFace.LargeFaceActivity;
import com.chen.firstdemo.matrix_demo.matrix_study.MatrixStudyActivity;
import com.chen.firstdemo.matryoshka.MatryoshkaActivity;
import com.chen.firstdemo.new_beiyu.NewBeiyuActivity;
import com.chen.firstdemo.pressure_view.PressureViewDemoActivity;
import com.chen.firstdemo.recyclers.drag_recycler.DragRecyclerDeniActivity;
import com.chen.firstdemo.recyclers.empty_recyclerview.EmptyAdapterActivity;
import com.chen.firstdemo.floating_window_demo.FloatingWindowActivity;
import com.chen.firstdemo.greendao_demo.GreendaoActivity;
import com.chen.firstdemo.hexagon_view_demo.HexagonActivity;
import com.chen.firstdemo.matrix_demo.MatrixActivity;
import com.chen.firstdemo.multiple_img_view.MultipleImgViewActivity;
import com.chen.firstdemo.notification_demo.NotificationActivity;
import com.chen.firstdemo.recyclers.hover_recycler.HoverRecyclerActivity;
import com.chen.firstdemo.recyclers.kotlin.KotlinDemoActivity;
import com.chen.firstdemo.recyclers.recyclerview_layoutmanager_demo.RecyclerViewLayoutManagerActivity;
import com.chen.firstdemo.scrolls.SpringScrollActivity;
import com.chen.firstdemo.surfaceview_demo.SurfaceViewDemoActivity;
import com.chen.firstdemo.suscat.OtherApplicationsActivity;
import com.chen.firstdemo.taskstackbuilder.TaskStackBuilder1Activity;
import com.chen.firstdemo.viewpagers.viewpager_demo.PagerActivity;
import com.chen.firstdemo.progress_imageview_demo.ProgressImageViewActivity;
import com.chen.firstdemo.select_tab_demo.SelectTabActivity;
import com.chen.firstdemo.shapes.ShapeActivity;
import com.chen.firstdemo.utils.ScreenUtil;
import com.chen.firstdemo.viewpagers.viewpager_demo.wheel_anim_demo.ViewpagerAnimActivity;
import com.chen.firstdemo.weelview.WheelDemoActivity;

public class StartActivity extends AppCompatActivity {

    private LinearLayout linearLayout ;
    private ClassBean[] classes ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        linearLayout = findViewById(R.id.linearLayout);
        linearLayout.setMinimumHeight(ScreenUtil.getScreenHeight(this));

        classes = new ClassBean[]{
                new ClassBean(BitmapMixActivity.class,"bitmap混合实例列表"),
                new ClassBean(FloatingWindowActivity.class,"悬浮窗"),
                new ClassBean(GreendaoActivity.class,"greendao数据库"),
                new ClassBean(BottomTab2Activity.class,"自定义底部导航栏"),
                new ClassBean(SelectTabActivity.class,"自定义圆角Tab"),
                new ClassBean(ProgressImageViewActivity.class,"使用OkHttp实现的带进度的图片加载"),
                new ClassBean(EmptyAdapterActivity.class,"RecyclerView 空布局Adapter"),
                new ClassBean(ShapeActivity.class,"自定义Shape"),
                new ClassBean(MatrixActivity.class,"Matrix矩阵"),
                new ClassBean(DragRecyclerDeniActivity.class,"Drag拖动item的列表"),
                new ClassBean(PagerActivity.class,"viewpager变形初探"),
                new ClassBean(NotificationActivity.class,"Notification研究"),
                new ClassBean(RecyclerViewLayoutManagerActivity.class,"RecyclerView LayoutManager研究"),
                new ClassBean(ClipViewDemoActivity.class,"ClipView和Photor框架接入"),
                new ClassBean(HexagonActivity.class,"尖尖朝上的正多边形ImageView"),
                new ClassBean(HoverRecyclerActivity.class,"分组悬停RecyclerView的item装饰类"),
                new ClassBean(GaussBlurActivity.class,"高斯模糊"),
                new ClassBean(OtherApplicationsActivity.class,"上体掌中宝中的三个小应用"),
                new ClassBean(Banner2Activity.class,"Banner框架接入"),
                new ClassBean(NewBeiyuActivity.class,"为贝语实现的进度条"),
                new ClassBean(DIYViewActivity.class,"一些自定义View"),
                new ClassBean(KotlinDemoActivity.class,"用Kotlin实现的activity"),
                new ClassBean(ChartsActivity.class,"图标控件Charts"),
                new ClassBean(WheelDemoActivity.class,"选择器轮子控件Wheel"),
                new ClassBean(ViewpagerAnimActivity.class,"基于ViewPager实现的开宝箱动画"),
                new ClassBean(LargeFaceActivity.class,"自定义大脸View，根据进度改变颜色"),
                new ClassBean(SurfaceViewDemoActivity.class,"SurfaceView研究"),
                new ClassBean(TaskStackBuilder1Activity.class,"TaskStackBuilder研究"),
                new ClassBean(MultipleImgViewActivity.class,"钉钉和微信群组头像view"),
                new ClassBean(SpringScrollActivity.class,"自带阻尼的SpringScrollview接入"),
                new ClassBean(MatryoshkaActivity.class,"套娃view"),
                new ClassBean(HandlerActivity.class,"解析Handler"),
                new ClassBean(DouYinDialogActivity.class,"类似抖音评论弹窗框架LikeDouYin...接入"),
                new ClassBean(FlightChessActivity.class,"英国十字棋棋盘"),
                new ClassBean(ENumberActivity.class,"电子数字"),
                new ClassBean(PressureViewDemoActivity.class,"压力View"),
                new ClassBean(MatrixStudyActivity.class,"Matrix 研究"),
                new ClassBean(HuanXinSDKActivity.class,"环信SDK研究"),
                new ClassBean(SendGiftAnimActivity.class,"房间送礼动画"),


                /*最后一个预留空位*/
                null
        } ;

        for(int i = classes.length-2 ; i >= 0 ; i--){
            View v = generateView(classes[i].c,classes[i].cStr);

            //直接点击第一个
            if(i == classes.length-2){
                v.callOnClick();
            }
        }
    }

    /**
     * 根据类名 逐一生成button
     * @param c
     * @param s
     */
    private View generateView(final Class c,String s){
        Button button = new Button(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-1,-2);
        params.setMargins(100,0,100,0);
        button.setLayoutParams(params);
        button.setMinHeight(200);
        button.setPadding(100,50,100,50);
        button.setGravity(Gravity.CENTER);
        button.setText(c.getSimpleName()+".class\n"+s);
        button.setAllCaps(false);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StartActivity.this,c);
                StartActivity.this.startActivity(intent);
            }
        });
        linearLayout.addView(button);
        return button ;
    }


    private class ClassBean {
        private Class c ;
        private String cStr ;

        public ClassBean(Class c, String cStr) {
            this.c = c;
            this.cStr = cStr;
        }
    }
}
