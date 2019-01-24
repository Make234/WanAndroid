package com.zyh.wanandroid.utils;

import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;

/**
 * @author zyh
 * @date 2019/1/16
 */
public class LocalHandler extends Handler {
    WeakReference<IHandle> weakReference;

    public LocalHandler(IHandle handle) {
        weakReference = new WeakReference<>(handle);
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        IHandle handle = weakReference.get();
        if (handle != null) {
            handle.handleMessage(msg);
        }
    }

    public interface IHandle {

        /**
         * handler 回调
         *
         * @param msg Message
         **/
        void handleMessage(Message msg);
    }
}
