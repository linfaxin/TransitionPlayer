package com.linfaxin.transitionplayer.interpolators;

import android.animation.TimeInterpolator;
import android.util.Pair;

import java.util.ArrayList;

/**
 * Created by linfaxin on 2015/8/9.
 * Email: linlinfaxin@163.com
 */
public class MultiTimeInterpolator implements TimeInterpolator{
    ArrayList<Pair<TimeInterpolator, Float>> pairs = new ArrayList<>();
    private int totalWeight = 0;
    public MultiTimeInterpolator(TimeInterpolator... interpolators){
        addInterpolators(interpolators);
    }

    public void addInterpolators(TimeInterpolator... interpolators){
        for(TimeInterpolator interpolator : interpolators){
            addInterpolator(interpolator, 1);
        }
    }
    public void addInterpolator(TimeInterpolator interpolator){
        addInterpolator(interpolator, 1);
    }

    public void addInterpolator(TimeInterpolator interpolator, float weight){
        if(interpolator==null || weight<0) return;
        pairs.add(new Pair<>(interpolator, weight));
        totalWeight += weight;
    }

    @Override
    public float getInterpolation(float input) {
        float weightSum = 0;
        for(Pair<TimeInterpolator, Float> pair : pairs){
            float weight = pair.second;
            float weightStart = weightSum;
            float weightEnd = weightStart + weight;
            float inputWeight = input * totalWeight;
            if(inputWeight > weightStart && inputWeight <= weightEnd){
                float convertInput = (inputWeight - weightStart) / weight;
                return pair.first.getInterpolation(convertInput);
            }
            weightSum = weightEnd;
        }
        return 0;
    }
}
