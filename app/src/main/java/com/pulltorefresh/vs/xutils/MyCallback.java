package com.pulltorefresh.vs.xutils;

import org.xutils.common.Callback;

/**
 * Created by think on 2017/11/22.
 */

public class MyCallback implements Callback.CacheCallback<String>{
    @Override
    public boolean onCache(String result) {
        return false;
    }

    @Override
    public void onSuccess(String result) {

    }

    @Override
    public void onError(Throwable ex, boolean isOnCallback) {

    }

    @Override
    public void onCancelled(CancelledException cex) {

    }

    @Override
    public void onFinished() {

    }
}
