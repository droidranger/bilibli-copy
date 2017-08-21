package com.ranger.xyg.demos.accessibility;

import android.accessibilityservice.AccessibilityService;
import android.app.Notification;
import android.app.PendingIntent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.ranger.xyg.library.utils.LogUtils;

import java.util.List;

/**
 * Created by xyg on 2017/4/14.
 */

public class RobMoneyService extends AccessibilityService {

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        int eventType = event.getEventType();
        switch (eventType) {
            // 第一步：监听通知栏消息
            case AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED:
                List<CharSequence> texts = event.getText();
                if (!texts.isEmpty()) {
                    for (CharSequence text : texts) {
                        String content = text.toString();
                        LogUtils.i("xyg", "text: " + content);
                        if (content.contains("[微信红包]")) {
                            // 模拟打开通知栏消息
                            if (event.getParcelableData() != null
                                    && event.getParcelableData() instanceof Notification) {
                                Notification notification = (Notification) event.getParcelableData();
                                PendingIntent pendingIntent = notification.contentIntent;
                                try {
                                    pendingIntent.send();
                                } catch (PendingIntent.CanceledException e) {
                                    e.printStackTrace();
                                }

                            }
                        }
                    }
                }
                break;
            // 第二步：监听是否进入微信红包消息界面
            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:
                String className = event.getClassName().toString();
                if (className.equals("com.tencent.mm.ui.LauncherUI")) {
                    // 开始抢红包
                    getPacket();
                } else if (className.equals("com.tencent.mm.plugin.luckymoney.ui.LuckyMoneyReceiveUI")) {
                    // 开始打开红包
                    openPacket();
                }
                break;
        }
    }

    private void openPacket() {

    }

    private void getPacket() {
        AccessibilityNodeInfo rootNode = getRootInActiveWindow();
        recycle(rootNode);
    }

    private void recycle(AccessibilityNodeInfo nodeInfo) {
        if (nodeInfo.getChildCount() == 0) {
            if (nodeInfo.getText() != null) {
                if ("领取红包".equals(nodeInfo.getText().toString())) {
                    // 这里有一个问题需要注意，就是需要找到一个可以点击的View
                    LogUtils.i("xyg", "Click" + ", isClick:" + nodeInfo.isClickable());
                    nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                    AccessibilityNodeInfo parent = nodeInfo.getParent();
                    while (parent != null) {
                        LogUtils.i("xyg", "parent isClick: " + parent.isClickable());
                        if (parent.isClickable()) {
                            parent.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                            break;
                        }
                        parent = parent.getParent();
                    }
                }
            } else {
                int size = nodeInfo.getChildCount();
                for (int i = 0; i < size; i++) {
                    if (nodeInfo.getChild(i) == null) {
                        recycle(nodeInfo.getChild(i));
                    }
                }
            }
        }
    }

    @Override
    public void onInterrupt() {

    }
}
