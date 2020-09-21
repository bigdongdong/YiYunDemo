package com.chen.firstdemo.weelview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout

@SuppressLint("DrawAllocation")
public class WheelViewKt(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private val cities :Array<String> = arrayOf("兰州","拉萨","来宾","莱芜","廊坊","乐山","凉山","连云港",
            "聊城","辽阳","辽源","丽江","临沧","临汾","临夏","临沂","林芝","丽水","六安","六盘水",
            "柳州","陇南","龙岩","娄底","漯河","洛阳","泸州","吕梁","济南","佳木斯","吉安","江门",
            "焦作","嘉兴","嘉峪关","揭阳","吉林","金昌","晋城","景德镇","荆门","荆州","金华","济宁","晋中","锦州","九江",
            "酒泉","杂多县","赞皇县","枣强县","枣阳市","枣庄","泽库县","增城市","曾都区","泽普县","泽州县","札达县",
            "扎赉特旗","扎兰屯市","扎鲁特旗","扎囊县","张北县","张店区","章贡区","张家港","张家界","张家口",
            "漳平市","漳浦县","章丘市","樟树市","张湾区","彰武县","漳县","张掖","漳州","长子县","湛河区",
            "湛江","站前区","沾益县","诏安县","召陵区","昭平县","肇庆","昭通","赵县","昭阳区","招远市",
            "肇源县","肇州县","柞水县","柘城县","浙江","镇安县","振安区","镇巴县","正安县","正定县",
            "正定新区","正蓝旗","正宁县","蒸湘区","正镶白旗","正阳县","郑州","镇海区","镇江","浈江区",
            "镇康县","镇赉县","镇平县","振兴区","镇雄县","镇原县","志丹县","治多县","芝罘区","枝江市",
            "芷江侗族自治县","织金县","中方县","中江县","钟楼区","中牟县","中宁县","中山","中山区",
            "钟山区","钟山县","中卫","钟祥市","中阳县","中原区","周村区","周口","周宁县","舟曲县",
            "舟山","周至县","庄河市","诸城市","珠海","珠晖区","诸暨市","驻马店","准格尔旗","涿鹿县",
            "卓尼","涿州市","卓资县","珠山区","竹山县","竹溪县","株洲","株洲县","淄博","子长县",
            "淄川区","自贡","秭归县","紫金县","自流井区","资溪县","资兴市","资阳")
    private val FONT_SIZE = sp2px(20f)

    private var w :Int = 0
    private var h :Int = 0
    private var offsety:Float = 0f

    private lateinit var beans:Array<DrawBean?>

    init {
        setWillNotDraw(false)
        isClickable = true
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
//        var dx:Float = 0f
//        var dy:Float = 0f
        var lasty:Float = 0f
        when(event?.action){
            MotionEvent.ACTION_DOWN -> {
//              dx = event.x
//              dy = event.y
                offsety = 0f
                lasty = event.y
            }
            MotionEvent.ACTION_MOVE -> {
                var nowy = event.y

                offsety = nowy-lasty
                matrix.postTranslate(0f,offsety)
                invalidate()

                lasty = nowy
            }
            MotionEvent.ACTION_UP,MotionEvent.ACTION_CANCEL -> {

            }
        }

        return super.onTouchEvent(event)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        w = measuredWidth
        h = measuredHeight


        beans = arrayOfNulls<DrawBean>(cities.size)
        val fontSpace = 10
        for (i in cities.indices){
            val p:Path = Path()
            p.reset()
            p.moveTo(0f, ((FONT_SIZE+fontSpace)*i).toFloat())
            p.rLineTo(w.toFloat(),0f)
            beans[i] = DrawBean(cities[i],p)
        }
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.let {
            val paint = Paint()
            paint.style = Paint.Style.FILL
            paint.color = Color.RED
            paint.isAntiAlias = true
            paint.textAlign = Paint.Align.CENTER
            paint.textSize = FONT_SIZE.toFloat()

            for (b in beans){
                b?.let {
                    canvas.drawTextOnPath(it.text,it.p,0f, FONT_SIZE.toFloat(),paint)
                    canvas.drawPath(it.p,paint)
                }
            }
        }

    }

    class DrawBean (text:String,p:Path){
        public val text:String = text
        public val p: Path = p

    }

    /**
     * sp转px
     * @return
     */
    fun sp2px(spVal: Float): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, context.resources.displayMetrics).toInt()
    }
}