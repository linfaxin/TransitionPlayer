package com.linfaxin.transitionplayer.transitions;

import android.animation.ArgbEvaluator;
import android.view.View;
import android.widget.TextView;

/**
 * Created by linfaxin on 2015/8/9.
 * Email: linlinfaxin@163.com
 */
public class ChangeTextColor extends AbsChangeValue{
    public ChangeTextColor() {
        super(new ArgbEvaluator(), "textColor", "hintTextColor");
    }

    @Override
    protected Object getPropertyValue(View view, String propertyName) {
        if(view instanceof TextView){
            if("textColor".equals(propertyName)){
                return ((TextView) view).getCurrentTextColor();
            }
            if("hintTextColor".equals(propertyName)){
                return ((TextView) view).getCurrentHintTextColor();
            }
        }
        return super.getPropertyValue(view, propertyName);
    }
}
