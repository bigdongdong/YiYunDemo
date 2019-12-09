package com.chen.firstdemo.empty_recyclerview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * create by chenxiaodong on 2019/11/29
 *
 * 不用写类，用匿名内部类实例化效果极佳
 * @param <Bean>
 */
public abstract class EaseAdapter<Bean> extends RecyclerView.Adapter<EaseAdapter.ViewHolder> {

    private  final int TYPE_EASE = -9998;
    private final int TYPE_EMPTY = -9999 ;
    private boolean isInitialize = true ;  //初次加载，也就是recyclerView.setAdapter时，默认显示空白
    private boolean isEmpty = false ;
    private List<Bean> listData ;

    protected Context context ;

    protected EaseAdapter(Context context){
        this.context = context ;
    }

    public void update(List<Bean> listData){
        if(isInitialize == true){
            isInitialize = false ;
        }
        this.listData = listData ;
        notifyDataSetChanged();
    }

    public void append(List<Bean> listData){
        if(isInitialize == true){
            isInitialize = false ;
        }

        if(this.listData == null){
            this.listData = new ArrayList<>();
        }
        this.listData.addAll(listData);
        notifyDataSetChanged();
    }
    protected  List<Bean> getList(){
        return this.listData;
    }

    protected  Bean getBean(int position){
        if(listData!=null && position < listData.size()){
            return listData.get(position);
        }else {
            return null ;
        }
    }

    /**
     * 之所以提供view的返回选项，是为了多元化空布局的操作空间，让空布局view可以跟用户交互
     * @return 获取空布局的布局id或者view实例
     */
    protected abstract Object getEmptyIdOrView();

    protected abstract Object getItemViewOrId();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if(i == TYPE_EMPTY){
            if( getEmptyIdOrView() instanceof Integer){
                return new ViewHolder(LayoutInflater.from(context).inflate((int) getEmptyIdOrView(),viewGroup,false));
            }else if( getEmptyIdOrView() instanceof View){
                return new ViewHolder((View) getEmptyIdOrView());
            }else {
                throw new RuntimeException(" getEmptyIdOrView()只接收布局id或者view类型！！！");
            }
        }

        if(i == TYPE_EASE){
            if(getItemViewOrId() instanceof View){
                return new ViewHolder((View)getItemViewOrId());
            }else if(getItemViewOrId() instanceof Integer){
                return new ViewHolder(LayoutInflater.from(context).inflate((int)getItemViewOrId(),viewGroup,false));
            }
        }

        throw new RuntimeException("getItemViewOrId()只能返回view或者int类型");
    }

    @Override
    public int getItemViewType(int position) {
        if(getItemCount() == 1 && isEmpty == true){
            return TYPE_EMPTY ;
        }else{
            return TYPE_EASE;
        }
    }

    @Override
    public int getItemCount() {
        if(isInitialize == true){
            return 0 ;
        }

        if(listData == null || listData.size() == 0){
            isEmpty = (getEmptyIdOrView() != null);
            return (getEmptyIdOrView() != null) ? 1:0 ;
        }else{
            isEmpty = false ;
            return listData.size();
        }
    }


    class ViewHolder extends RecyclerView.ViewHolder{
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public <V>V getView(int id){
            return (V)itemView.findViewById(id);
        }

        public <V>V getItemView(){
            return (V)itemView;
        }
    }
}