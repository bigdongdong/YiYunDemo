package com.chen.firstdemo.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

/**
 * create by chenxiaodong on 2019/11/12
 *
 * 实现了懒加载的fragment
 *
 * attention: 如果viewpager中只有两个fragment则会失效，三个及以上则正常工作
 *
 * 两个fragment的懒加载策略:
 * 当viewpager中仅有两个fragment时，可以直接在setUserVisibleHint中判断
 * 因为两个fragment都必然处于alive活动状态
 *
 */
public abstract class LazyFragment extends Fragment {

    private final String TAG = "LazyFragment";
    private Boolean isAlreadyInvokedLazyLoad  = false;  //是否已经加载过lazyLoad
    protected Activity context ;

    View view ;
    
    //返回布局id
    protected abstract int getLayoutId();
    

    protected abstract void onBundle(Bundle arguments);
    
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView: ");
        view = LayoutInflater.from(this.getContext()).inflate(getLayoutId() ,container,false);
        context = this.getActivity();
        onCreateView(view);
//        onBundle(getArguments());
        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if(getUserVisibleHint() && isAlreadyInvokedLazyLoad == false){
                    //do something ...
                    lazyLoad();

                    // 由于onGlobalLayout()会重复调用
                    // 所以避免多次调用，lazyLoad()一旦调用，立即将 isAlreadyInvokedLazyLoad 置为true
                    isAlreadyInvokedLazyLoad = true ;
                    view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onActivityCreated: ");
        super.onActivityCreated(savedInstanceState);
//        initialize();
    }
    

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if(isVisibleToUser == false){
            isAlreadyInvokedLazyLoad = false ; //隐藏之后，重新将该变量置为空
        }
    }

    //初始化View
    protected abstract void onCreateView(View view);

    protected abstract void initialize();
    
    //实现懒加载
    protected abstract void  lazyLoad();

    /**
     * 返回view对象
     * @return
     */
    public View getView(){
        if(view!=null){
            return view;
        }else{
            throw new RuntimeException("view未完成初始化！");
        }
    }
}
