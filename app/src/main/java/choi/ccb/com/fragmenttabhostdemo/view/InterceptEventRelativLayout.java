package choi.ccb.com.fragmenttabhostdemo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

public class InterceptEventRelativLayout extends RelativeLayout {
    public InterceptEventRelativLayout(Context context) {
        super(context);
    }

    public InterceptEventRelativLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public InterceptEventRelativLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }
}
