package zxy.opengldemo;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 5/15/2018.
 */

public class customgroupview extends ViewGroup {
    public customgroupview(Context context) {
        super(context);
    }

    public customgroupview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public customgroupview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public customgroupview(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode=MeasureSpec.getMode(widthMeasureSpec);
        int widthSize=MeasureSpec.getSize(widthMeasureSpec);
        int heightMode=MeasureSpec.getMode(heightMeasureSpec);
        int heightSize=MeasureSpec.getSize(heightMeasureSpec);
        // 计算出所有的childView的宽和高
        measureChildren(widthMeasureSpec,heightMeasureSpec);
        /**
         * 记录如果是wrap_content是设置的宽和高
         */
        int width = 0;
        int height = 0;

        int cCount = getChildCount();
        int cWidth = 0;
        int cHeight = 0;
        MarginLayoutParams cParams = null;
        // 用于计算左边两个childView的高度
        int lHeight = 0;
        // 用于计算右边两个childView的高度，最终高度取二者之间大值
        int rHeight = 0;

        // 用于计算上边两个childView的宽度
        int tWidth = 0;
        // 用于计算下面两个childiew的宽度，最终宽度取二者之间大值
        int bWidth = 0;
        for(int i=0;i<cCount;i++)
        {
            View childView=getChildAt(i);
            cWidth=childView.getMeasuredWidth();
            cHeight=childView.getMeasuredHeight();
            cParams= (MarginLayoutParams) childView.getLayoutParams();

            if(i==0 || i==1)
            {

                tWidth+=cWidth+cParams.leftMargin+cParams.rightMargin;
                if (i == 2 || i == 3)
                {
                    bWidth += cWidth + cParams.leftMargin + cParams.rightMargin;
                }

                if (i == 0 || i == 2)
                {
                    lHeight += cHeight + cParams.topMargin + cParams.bottomMargin;
                }

                if (i == 1 || i == 3)
                {
                    rHeight += cHeight + cParams.topMargin + cParams.bottomMargin;
                }

                width = Math.max(tWidth, bWidth);
                height = Math.max(lHeight, rHeight);
                setMeasuredDimension((widthMode == MeasureSpec.EXACTLY) ? widthSize : width,
                        (heightMode == MeasureSpec.EXACTLY) ? heightSize : height);
            }
        }

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int cCount = getChildCount();
        int cWidth = 0;
        int cHeight = 0;
        MarginLayoutParams cParams = null;
        /**
         * 遍历所有childView根据其宽和高，以及margin进行布局
         */
        for (int i = 0; i < cCount; i++)
        {
            View childView = getChildAt(i);
            cWidth = childView.getMeasuredWidth();
            cHeight = childView.getMeasuredHeight();
            cParams = (MarginLayoutParams) childView.getLayoutParams();

            int cl = 0, ct = 0, cr = 0, cb = 0;

            switch (i)
            {
                case 0:
                    cl = cParams.leftMargin;
                    ct = cParams.topMargin;
                    break;
                case 1:
                    cl = getWidth() - cWidth - cParams.leftMargin
                            - cParams.rightMargin;
                    ct = cParams.topMargin;

                    break;
                case 2:
                    cl = cParams.leftMargin;
                    ct = getHeight() - cHeight - cParams.bottomMargin;
                    break;
                case 3:
                    cl = getWidth() - cWidth - cParams.leftMargin
                            - cParams.rightMargin;
                    ct = getHeight() - cHeight - cParams.bottomMargin;
                    break;

            }
            cr = cl + cWidth;
            cb = cHeight + ct;
            childView.layout(cl, ct, cr, cb);
        }
    }

    @Override
    public  ViewGroup.LayoutParams  generateLayoutParams(AttributeSet attrs)
    {
        return new MarginLayoutParams(getContext(), attrs);
    }
}
