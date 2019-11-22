package com.chen.firstdemo.empty_recyclerview;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;


public abstract class EmptyAdapter<EVH extends EViewHolder,
        VH extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private final String TAG = "EmptyAdapter->TAG";
    protected boolean isOnlyEmptyView = false ;


    private final int TYPE_HEADER = -9999 ;
    private final int TYPE_FOOTER = -9998 ;
    private final int TYPE_EMPTY = -9997 ;


    abstract EVH onCreateEmptyHolder(@NonNull ViewGroup viewGroup, int type);
    abstract VH onCreateGeneralHolder(@NonNull ViewGroup viewGroup, int type);

    abstract void onBindEViewHolder(EVH holder , int position);
    abstract void onBindGeneralViewHolder(RecyclerView.ViewHolder holder , int position);

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int type) {
        switch(type){
            case TYPE_EMPTY:
                //空布局
                return onCreateEmptyHolder(viewGroup,type);
            default:
                return onCreateGeneralHolder(viewGroup,type);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
        if( holder instanceof EViewHolder){
            onBindEViewHolder((EVH)holder ,i);
        }else{
            onBindGeneralViewHolder(holder,i);
        }
    }

    @Override
    public int getItemViewType(int position) {
        Log.i(TAG, "getItemViewType: "+position);
        if(isOnlyEmptyView && getItemCount() == 1 ){
            return TYPE_EMPTY ;
        }
        return super.getItemViewType(position);

    }

    @Override
    public int getItemCount() {
        Log.i(TAG, "getItemCount: ");
        if(isOnlyEmptyView){
            return 1 ;
        }
        return getGeneralItemCount();
    }

    abstract int getGeneralItemCount();
}

abstract class EViewHolder extends RecyclerView.ViewHolder{
    public EViewHolder(@NonNull View itemView) {
        super(itemView);

//        initView(itemView);
    }
//    abstract void initView(View itemView);
}

