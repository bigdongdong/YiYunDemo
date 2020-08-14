package com.chen.firstdemo.recyclers.recyclerview_layoutmanager_demo;

import android.graphics.Path;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class PathLayoutManager extends RecyclerView.LayoutManager {
    private Path mPath ;
    private int mOrientation;
    private int mItemOffset;
    private Keyframes mKeyframes ;
    private int mItemCountInScreen ;
    private int mFirstVisibleItemPos;
    private int mOffsetX,mOffsetY;

    /**
     *
     * @param path 目标路径
     * @param itemOffset Item间距
     */
    public PathLayoutManager(Path path, int itemOffset) {
        this(path,itemOffset, LinearLayout.VERTICAL);
    }

    /**
     *
     * @param path 目标路径
     * @param itemOffset Item间距
     * @param orientation 滑动方向
     */
    public PathLayoutManager(Path path, int itemOffset, int orientation) {
        mOrientation = orientation;
        mItemOffset = itemOffset;
        updatePath(path);
    }

    /**
     * 更新path
     * 也就是创建一个KeyFrames
     * @param path
     */
    private void updatePath(Path path) {
        if(path != null){
            mKeyframes = new Keyframes(path);
            if(mItemOffset == 0){
                /*这里我们不允许间距为0，因为间距如果为0的话，全部Item都会叠在一起，这样就没有意义了*/
                throw new IllegalStateException("itemOffset must be > 0 !!!");
            }
            /*计算这个path最多能同时出现几个Item*/
            mItemCountInScreen = mKeyframes.getPathLength() / mItemOffset + 1;
        }
        requestLayout();
    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(-2,-2);
    }


    /**
     * Item在Path上的百分比 = Item当前position * 指定的Item间距 / Path总长度
     *      但由于我们的Item是会滚动的，也就是说，上面的方法算出来的是死的，
     *      列表一滚动就不对了，所以，还要减去滚动的偏移量。
     *Item在Path上的百分比 = (Item当前position * 指定的Item间距 - 滑动偏移量) / Path总长度
     */
    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        super.onLayoutChildren(recycler, state);

        if(state.getItemCount() == 0){
            /*没有Item可布局，就回收全部临时缓存（参考自带的LinearLayoutManager）*/
            /*这里的没有Item，是指Adapter里面的数据集，
            可能临时被清空了，但不确定何时还会继续添加回来*/
            removeAndRecycleAllViews(recycler);
            return;
        }

        /*暂时分离和回收全部有效的Item*/
        detachAndScrapAttachedViews(recycler);

        List<PosTan> needLayoutItems = new ArrayList<>();
        /*获取需要布局的items*/
        initNeedLayoutItems(needLayoutItems,state.getItemCount());
        /*检查一下*/
        if(needLayoutItems.isEmpty() || mKeyframes == null){
            removeAndRecycleAllViews(recycler);
            return;
        }
        /*开始布局*/
        onLayout(recycler,needLayoutItems);
    }

    /**
     * 初始化需要布局的item数据
     * @param result 结果
     * @param itemCount Item总数
     */
    private void initNeedLayoutItems(List<PosTan> result , int itemCount){
        float currentDistance;
        /*必须从第一个item开始，因为要拿到最小的，也就是最先的*/
        for (int i = 0; i < itemCount; i++) {
            currentDistance = i * mItemOffset - getScrollOffset();
            /*判断当前距离 >= 0 的即表示可见*/
            if(currentDistance >= 0){
                /*得到第一个可见的position*/
                mFirstVisibleItemPos = i;
                break;
            }
        }

        /*结束的position*/
        int endIndex = mFirstVisibleItemPos + mItemCountInScreen ;
        /*防止溢出*/
        if(endIndex > getItemCount()){
            endIndex = getItemCount();
        }
        float fraction ;
        PosTan posTan;
        for (int i = mFirstVisibleItemPos; i < endIndex; i++) {
            /*得到当前距离*/
            currentDistance = i *mItemOffset - getScrollOffset();
            /*得到百分比*/
            fraction = currentDistance / mKeyframes.getPathLength();
            /*根据百分比从keyFrames中取出对应的坐标和角度*/
            posTan = mKeyframes.getValue(fraction);
            if(posTan == null){
                continue;
            }
            /*添加进list中*/
            result.add(new PosTan(posTan,i,fraction));
        }
    }


    private void onLayout(RecyclerView.Recycler recycler, List<PosTan> needLayoutItems) {
        int x,y;
        View item ;
        for (PosTan tmp : needLayoutItems) {
            /*根据position获取view*/
            item = recycler.getViewForPosition(tmp.index);
            /*添加进去，当然里面不一定每次都是调用RecyclerView的addView方法的，
            * 如果是从缓存区里面找到的，只需要调用attachView方法把它重新连接上就行了*/
            addView(item);
            /*测量item，当然，也不是每次都会调用measure方法进行测量的，
            * 它里面会判断，如果已经测量过，而且当前尺寸又没有收到更新的通知，就不会重新测量*/
            measureChild(item,0,0);

            /*path线条在view的中间， x y 都是负值*/
            x = (int) (tmp.x - getDecoratedMeasuredWidth(item) / 2);
            y = (int) (tmp.y - getDecoratedMeasuredHeight(item) / 2);

            /*进行布局*/
            layoutDecorated(item,x,y,x+getDecoratedMeasuredWidth(item),y+getDecoratedMeasuredHeight(item));
            /*旋转item*/
            item.setRotation(tmp.getChildAngle());

        }

    }

    /**
     * 根据当前设置的滚动方向来获取对应的滚动偏移量
     */
    private int getScrollOffset() {
        return  mOrientation == RecyclerView.VERTICAL ? mOffsetY : mOffsetX;
    }


    @Override
    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        return super.scrollHorizontallyBy(dx, recycler, state);
    }

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        return super.scrollVerticallyBy(dy, recycler, state);
    }


    @Override
    public boolean canScrollHorizontally() {
        return mOrientation == RecyclerView.VERTICAL;
    }

    @Override
    public boolean canScrollVertically() {
        return  mOrientation == RecyclerView.HORIZONTAL;
    }
}
