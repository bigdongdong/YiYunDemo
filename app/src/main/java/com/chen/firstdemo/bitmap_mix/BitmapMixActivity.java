package com.chen.firstdemo.bitmap_mix;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.chen.firstdemo.R;
import com.chen.firstdemo.floating_window_demo.floating_view.CicleImageView;
import com.chen.firstdemo.utils.ScreenUtil;

import java.util.ArrayList;
import java.util.List;

public class BitmapMixActivity extends AppCompatActivity {

    ScrollView scrollView ;
    LinearLayout linearLayout ;
    RelativeLayout.LayoutParams params ;
    int screenWidth ,screenHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        screenWidth = ScreenUtil.getScreenWidth(this);
        screenHeight = ScreenUtil.getScreenHeight(this);

        scrollView = new ScrollView(this);
        params = new RelativeLayout.LayoutParams(screenWidth,screenHeight);
        scrollView.setLayoutParams(params);

        linearLayout = new LinearLayout(this);
        params = new RelativeLayout.LayoutParams(screenWidth,screenHeight);
        linearLayout.setLayoutParams(params);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setGravity(Gravity.CENTER);

        scrollView.addView(linearLayout);

        /**
         * 设置好了基本布局 外围一个scrollview 里面一个垂直的linearlayout
         */
        this.setContentView(scrollView);

        ImageView imageView ;
        TextView textView ;

        LinearLayout hLinearLayout = new LinearLayout(this);
        params = new RelativeLayout.LayoutParams(screenWidth, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0,45,0,0);
        hLinearLayout.setLayoutParams(params);
        hLinearLayout.setGravity(Gravity.CENTER_HORIZONTAL);
        hLinearLayout.setOrientation(LinearLayout.HORIZONTAL);

        //添加第一个展示图
        View mixView = LayoutInflater.from(this).inflate(R.layout.item_bitmap_mix,null,false);
        imageView = mixView.findViewById(R.id.imageView);
        textView = mixView.findViewById(R.id.textView);
        imageView.setImageBitmap(getBitmapCicle());
        textView.setText("上层(源图像)");
        hLinearLayout.addView(mixView);

        //添加一个间隔
        View lineView = new View(this);
        lineView.setLayoutParams(new LinearLayout.LayoutParams(45, RelativeLayout.LayoutParams.WRAP_CONTENT));
        hLinearLayout.addView(lineView);

        //添加第二个展示图
        mixView = LayoutInflater.from(this).inflate(R.layout.item_bitmap_mix,null,false);
        imageView = mixView.findViewById(R.id.imageView);
        textView = mixView.findViewById(R.id.textView);
        imageView.setImageBitmap(getBitmapRect());
        textView.setText("底部(目标图像)");
        hLinearLayout.addView(mixView);

        //添加一个间隔
        lineView = new View(this);
        lineView.setLayoutParams(new LinearLayout.LayoutParams(45, RelativeLayout.LayoutParams.WRAP_CONTENT));
        hLinearLayout.addView(lineView);

        //添加自制的CicleImageView
        CicleImageView cicleImageView = new CicleImageView(this);
        cicleImageView.setImageBitmap(BitmapFactory.decodeResource(this.getResources(),
                R.mipmap.test_picture));
        params = new RelativeLayout.LayoutParams(300,300);
        cicleImageView.setLayoutParams(params);
        hLinearLayout.addView(cicleImageView);


        linearLayout.addView(hLinearLayout);

        RecyclerView recyclerView = new RecyclerView(this);
        params = new RelativeLayout.LayoutParams(screenWidth, RelativeLayout.LayoutParams.WRAP_CONTENT);
        recyclerView.setLayoutManager(new GridLayoutManager(this,3));
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);

                int space = (screenWidth-900)/4;

                int position = parent.getChildAdapterPosition(view);

                outRect.top = space ;

                switch((position+1) % 3){
                    case 1:
                        outRect.left = space;
                        outRect.right = space/3;
                        break;
                    case 2:
                        outRect.left = space/3*2;
                        outRect.right = space/3*2;
                        break;
                    case 0:
                        outRect.left = space/3;
                        outRect.right = space;
                        break;
                }
            }
        });
        recyclerView.setAdapter(new MixAdapter(this,generateList()) {
            @Override
            void onBindViewHolder(ViewHolder viewHolder , MixBean bean) {
                viewHolder.imageView.setImageBitmap(generateMixBitmap(bean.getMode()));
                viewHolder.textView.setText(bean.getText());
            }
        });

        linearLayout.addView(recyclerView);

    }
    private List<MixBean> generateList(){
        List<MixBean> list = new ArrayList<>();
        MixBean bean ;

        bean = new MixBean(PorterDuff.Mode.SRC_IN,"SRC_IN");
        list.add(bean);

        bean = new MixBean(PorterDuff.Mode.DST,"DST");
        list.add(bean);

        bean = new MixBean(PorterDuff.Mode.DST_IN,"DST_IN");
        list.add(bean);

        bean = new MixBean(PorterDuff.Mode.DST_ATOP,"DST_ATOP");
        list.add(bean);

        bean = new MixBean(PorterDuff.Mode.DST_OVER,"DST_OVER");
        list.add(bean);

        bean = new MixBean(PorterDuff.Mode.DST_OUT,"DST_OUT");
        list.add(bean);

        bean = new MixBean(PorterDuff.Mode.SRC,"SRC");
        list.add(bean);

        bean = new MixBean(PorterDuff.Mode.SRC_OVER,"SRC_OVER");
        list.add(bean);

        bean = new MixBean(PorterDuff.Mode.DARKEN,"DARKEN");
        list.add(bean);

        bean = new MixBean(PorterDuff.Mode.MULTIPLY,"MULTIPLY");
        list.add(bean);

        bean = new MixBean(PorterDuff.Mode.SCREEN,"SCREEN");
        list.add(bean);

        bean = new MixBean(PorterDuff.Mode.LIGHTEN,"LIGHTEN");
        list.add(bean);

        return list;

    }

    class MixBean{
        private PorterDuff.Mode mode ;
        private String text ;

        public MixBean(PorterDuff.Mode mode, String text) {
            this.mode = mode;
            this.text = text;
        }

        public PorterDuff.Mode getMode() {
            return mode;
        }

        public String getText() {
            return text;
        }
    }

    abstract class  MixAdapter extends RecyclerView.Adapter<MixAdapter.ViewHolder>{

        private Context context ;
        private List<MixBean> list = null ;

        public MixAdapter(Context context , List<MixBean> list) {
            this.context = context;
            if(list == null){
                list = new ArrayList<>();
            }
            this.list = list;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_bitmap_mix,viewGroup,false));
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
            onBindViewHolder(viewHolder,list.get(i));
        }

        abstract void onBindViewHolder(MixAdapter.ViewHolder viewHolder,MixBean bean);

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder{

            public ImageView imageView ;
            public TextView textView ;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.imageView);
                textView = itemView.findViewById(R.id.textView);
            }
        }
    }

    /**
     * 返回混合后的bitmap
     * @param mode
     * @return
     */
    private Bitmap generateMixBitmap(PorterDuff.Mode mode){

        Bitmap bitmapRect = getBitmapRect() ;
        Canvas canvas = new Canvas(bitmapRect);
        Rect rect = new Rect();
        rect.set(0,0,250,250);
        Paint paint = new Paint();

        paint.setXfermode(new PorterDuffXfermode(mode));
        canvas.drawBitmap(getBitmapCicle(),rect,rect,paint);

        return bitmapRect;
    }

    /**
     * 一个边长200的蓝色正方形
     * @return
     */
    private Bitmap getBitmapRect(){
        Bitmap bitmap = Bitmap.createBitmap(300,300,Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setColor(Color.parseColor("#46A3FF"));
        paint.setAntiAlias(true);
        RectF rectF = new RectF();
        rectF.set(50,50,250,250);
        canvas.drawRect(rectF,paint);
        return  bitmap;
    }

    /**
     * 画一个半径100的红色圆
     * @return
     */
    private Bitmap getBitmapCicle(){
        Bitmap bitmap = Bitmap.createBitmap(300,300,Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setColor(Color.parseColor("#FFF4C1"));
        paint.setAntiAlias(true);
        canvas.drawCircle(150,150,100,paint);
        return  bitmap;
    }



}
