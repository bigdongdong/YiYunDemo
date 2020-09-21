package com.chen.firstdemo.recyclers.kotlin

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import com.chen.firstdemo.R
import com.chen.firstdemo.base.BaseActivity
import com.chen.firstdemo.recyclers.empty_recyclerview.adapters.QuickAdapter
import com.chen.firstdemo.utils.DensityUtil
import com.chen.firstdemo.utils.GradientDrawableBuilder
import com.chen.firstdemo.utils.ScreenUtil
import kotlinx.android.synthetic.main.activity_kotlin_demo.*
import kotlinx.android.synthetic.main.item_kotlin_card.*
import kotlinx.android.synthetic.main.item_kotlin_card.view.*

class KotlinDemoActivity : BaseActivity(){
    lateinit var adapter:QuickAdapter<Any>
    companion object {
        fun jump(context: Context){

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin_demo)

        setViews()
        initialize()


    }

    private fun setViews():Unit{
        val conner:Float = ScreenUtil.getScreenWidth(context).toFloat() / 2
        topBgView.background = GradientDrawableBuilder()
                .color(0xff6a2c70.toInt())
                .conners(floatArrayOf(0f,0f,0f,0f,conner,conner,conner,conner))
                .build()

        GradientDrawableBuilder()
                .color(0xff6a2c70.toInt())
                .conner(DensityUtil.dip2px(context,25f))
                .into(confirmTV)
    }

    private fun initialize():Unit{
        recycler.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL,false)
        recycler.addItemDecoration(object :RecyclerView.ItemDecoration(){
            override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                super.getItemOffsets(outRect, view, parent, state)
                outRect.top = DensityUtil.dip2px(context,10f)
            }
        })

        adapter = object :QuickAdapter<Any>(this){
            override fun getEmptyIdOrView(): Any? {
                return null
            }

            override fun getItemViewOrId(): Any? {
                return LayoutInflater.from(context).inflate(R.layout.item_kotlin_card,null).apply {
                    GradientDrawableBuilder()
                            .color(0xFFFFFFFF.toInt())
                            .conner(DensityUtil.dip2px(context,20f))
                            .into(root)

                    val p:RelativeLayout.LayoutParams = RelativeLayout.LayoutParams(-1,-2)
                    p.leftMargin = DensityUtil.dip2px(context,20f)
                    p.rightMargin = DensityUtil.dip2px(context,20f)
                    root.layoutParams = p
                }
            }

            override fun onBindViewHolder(holder: ViewHolder, bean: Any?, position: Int) {
                Log.i(TAG, "onBindViewHolder: "+holder.itemView)
                holder.itemView.run{
                    iv.setImageResource(R.mipmap.ic_launcher)
                    titleTV.text = "Title:"+position
                    contentTV.text = "这是文字内容，这是文字内容，这是文字内容，" +
                            "这是文字内容，这是文字内容，这是文字内容，这是文字内容，" +
                            "这是文字内容，这是文字内容，这是文字内容，"
                }
            }

//            override fun onBindViewHolder(holder: ViewHolder, bean: Any?, position: Int) {
//                Log.i(TAG, "onBindViewHolder: "+holder.itemView)
//                holder.itemView.run{
//                    iv.setImageResource(R.mipmap.ic_launcher)
//                    titleTV.text = "Title:"+position
//                    contentTV.text = "这是文字内容，这是文字内容，这是文字内容，" +
//                            "这是文字内容，这是文字内容，这是文字内容，这是文字内容，" +
//                            "这是文字内容，这是文字内容，这是文字内容，"
//                }
//            }
        }

        recycler.adapter = adapter
        adapter.doTest(30)
    }
}