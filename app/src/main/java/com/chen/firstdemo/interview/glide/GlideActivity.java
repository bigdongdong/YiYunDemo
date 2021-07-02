package com.chen.firstdemo.interview.glide;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.chen.firstdemo.R;
import com.chen.firstdemo.base.BaseActivity;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GlideActivity extends BaseActivity {

    @BindView(R.id.iv)
    ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glide);
        ButterKnife.bind(this);


//        Glide.with(context)
//                .load("https://image.baidu.com/search/detail?ct=503316480&z=0&ipn=false&word=meinv&step_word=&hs=0&pn=17&spn=0&di=155430&pi=0&rn=1&tn=baiduimagedetail&is=0%2C0&istype=0&ie=utf-8&oe=utf-8&in=&cl=2&lm=-1&st=undefined&cs=1753589914%2C3429261228&os=1726675762%2C3259593668&simid=4166213449%2C710527619&adpicid=0&lpn=0&ln=2574&fr=&fmq=1624848483324_R&fm=&ic=undefined&s=undefined&hd=undefined&latest=undefined&copyright=undefined&se=&sme=&tab=0&width=undefined&height=undefined&face=undefined&ist=&jit=&cg=girl&bdtype=0&oriquery=&objurl=https%3A%2F%2Fgimg2.baidu.com%2Fimage_search%2Fsrc%3Dhttp%3A%2F%2Fpic.jj20.com%2Fup%2Fallimg%2F1114%2F0H320120Z3%2F200H3120Z3-3-1200.jpg%26refer%3Dhttp%3A%2F%2Fpic.jj20.com%26app%3D2002%26size%3Df9999%2C10000%26q%3Da80%26n%3D0%26g%3D0n%26fmt%3Djpeg%3Fsec%3D1627440880%26t%3D180947e7b27e36b5ec025845c5460cf8&fromurl=ippr_z2C%24qAzdH3FAzdH3Fooo_z%26e3B33da_z%26e3Bv54AzdH3FkzAzdH3FgxxzAzdH3Fgx4pAzdH3Fdccl0m_n_z%26e3Bip4s&gsm=13&rpstart=0&rpnum=0&islist=&querylist=&nojc=undefined")
//                .into(iv);

//        Glide.with(context)
//                .downloadOnly()
//                .load(url)
//                .listener(new RequestListener<File>() {
//                    @Override
//                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<File> target, boolean isFirstResource) {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean onResourceReady(File resource, Object model, Target<File> target, DataSource dataSource, boolean isFirstResource) {
//                        return false;
//                    }
//                })
//                .preload();


        Glide.with(context)
                .asBitmap()
                .load(R.mipmap.beauty_1)
//                .load("https://image.baidu.com/search/detail?ct=503316480&z=0&ipn=false&word=meinv&step_word=&hs=0&pn=9&spn=0&di=230340&pi=0&rn=1&tn=baiduimagedetail&is=0%2C0&istype=0&ie=utf-8&oe=utf-8&in=&cl=2&lm=-1&st=undefined&cs=1613029778%2C1507777131&os=1762196392%2C3301889417&simid=0%2C0&adpicid=0&lpn=0&ln=2574&fr=&fmq=1624848483324_R&fm=&ic=undefined&s=undefined&hd=undefined&latest=undefined&copyright=undefined&se=&sme=&tab=0&width=undefined&height=undefined&face=undefined&ist=&jit=&cg=girl&bdtype=0&oriquery=&objurl=https%3A%2F%2Fgimg2.baidu.com%2Fimage_search%2Fsrc%3Dhttp%3A%2F%2Fpic.jj20.com%2Fup%2Fallimg%2F1114%2F0H320120Z3%2F200H3120Z3-6-1200.jpg%26refer%3Dhttp%3A%2F%2Fpic.jj20.com%26app%3D2002%26size%3Df9999%2C10000%26q%3Da80%26n%3D0%26g%3D0n%26fmt%3Djpeg%3Fsec%3D1627440504%26t%3D40ea3542304d18389d96b07b41189e71&fromurl=ippr_z2C%24qAzdH3FAzdH3Fooo_z%26e3B33da_z%26e3Bv54AzdH3FkzAzdH3FgxxzAzdH3Fgx4pAzdH3Fdccl0m_m_z%26e3Bip4s&gsm=a&rpstart=0&rpnum=0&islist=&querylist=&nojc=undefined")
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        iv.setImageBitmap(resource);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });

    }
}
