package com.chen.firstdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.chen.firstdemo.anim_demo.AnimActivity;
import com.chen.firstdemo.banner_demo_github.Banner2Activity;
import com.chen.firstdemo.bitmap_mix_demo.BitmapMixActivity;
import com.chen.firstdemo.bottom_tab_demo.BottomTabActivity;
import com.chen.firstdemo.bottom_tab_demo_2.BottomTab2Activity;
import com.chen.firstdemo.chen_jin_pop_demo.ChenjinInputActivity;
import com.chen.firstdemo.chen_jin_pop_demo.ChenjinPopActivity;
import com.chen.firstdemo.clip_path_demo.ClipPathActivity;
import com.chen.firstdemo.dialog_demo.DialogActivity;
import com.chen.firstdemo.diy_view_demo.DIYViewActivity;
import com.chen.firstdemo.drag_recycler_demo.DragRecyclerDeniActivity;
import com.chen.firstdemo.empty_recyclerview.EmptyAdapterActivity;
import com.chen.firstdemo.floating_window_demo.FloatingWindowActivity;
import com.chen.firstdemo.greendao_demo.GreendaoActivity;
import com.chen.firstdemo.lazy_fragment.LazyLoadActivity;
import com.chen.firstdemo.matrix_demo.MatrixActivity;
import com.chen.firstdemo.my_wallet_demo.MyWalletActivity;
import com.chen.firstdemo.notification_demo.NotificationActivity;
import com.chen.firstdemo.pager_demo.PagerActivity;
import com.chen.firstdemo.progress_imageview_demo.ProgressImageViewActivity;
import com.chen.firstdemo.rank_imageview_demo.RankImageViewActivity;
import com.chen.firstdemo.recycler_manager_demo.RecyclerManagerActivity;
import com.chen.firstdemo.scrollview_toolbar.ScrollViewToolbarActivity;
import com.chen.firstdemo.select_tab_demo.SelectTabActivity;
import com.chen.firstdemo.shape_demo.ShapeActivity;
import com.chen.firstdemo.utils.ScreenUtil;
import com.chen.firstdemo.scroller_demo.ViewGroupActivity;
import com.chen.firstdemo.viewstub_demo.ViewStubActivity;

public class StartActivity extends AppCompatActivity {

    private LinearLayout linearLayout ;
    private Class[] classes ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        linearLayout = findViewById(R.id.linearLayout);
        linearLayout.setMinimumHeight(ScreenUtil.getScreenHeight(this));

        classes = new Class[]{BitmapMixActivity.class,
                BottomTabActivity.class,
                FloatingWindowActivity.class,
                GreendaoActivity.class,
                NotificationActivity.class,
                PagerActivity.class,
                ViewStubActivity.class,
                RankImageViewActivity.class,
                BottomTab2Activity.class,
                SelectTabActivity.class,
                ProgressImageViewActivity.class,
                DIYViewActivity.class,
                DialogActivity.class,
                Banner2Activity.class,
                EmptyAdapterActivity.class,
                ShapeActivity.class,
                AnimActivity.class,
                LazyLoadActivity.class,
                ScrollViewToolbarActivity.class,
                RecyclerManagerActivity.class,
                ViewGroupActivity.class,
                ChenjinInputActivity.class,
                ChenjinPopActivity.class,
                MyWalletActivity.class,
                MatrixActivity.class,
                DragRecyclerDeniActivity.class,
                ClipPathActivity.class,




                /*最后一个预留空位*/
                null
        } ;

        for(int i = classes.length-2 ; i >= 0 ; i--){
            generateView(classes[i].getSimpleName(),classes[i]);
        }

        //直接点击最后一个
        Intent intent = new Intent(this,classes[classes.length-2]);
        startActivity(intent);
    }

    /**
     * 根据类名 逐一生成button
     * @param s
     * @param c
     */
    private void generateView(String s,final Class c){
        Button button = new Button(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(100,30,100,0);
        button.setLayoutParams(params);
        button.setText(s+".class");
        button.setAllCaps(false);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StartActivity.this,c);
                StartActivity.this.startActivity(intent);
            }
        });
        linearLayout.addView(button);
    }

}
