package com.android.gesture;

import android.view.MotionEvent;

public class SwipeDetector {

	/**
	 * 指定参照的划动距离
	 */
    private int swipe_distance;
    /**
     * 指定参照的划动速度
     */
    private int swipe_velocity;
    /**
     * 默认最小划动距离
     */
    private static final int SWIPE_MIN_DISTANCE = 120;
    /**
     * 默认划动速度
     */
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;

    public SwipeDetector(int distance, int velocity) {
        super();
        this.swipe_distance = distance;
        this.swipe_velocity = velocity;
    }

    public SwipeDetector() {
        super();
        this.swipe_distance = SWIPE_MIN_DISTANCE;
        this.swipe_velocity = SWIPE_THRESHOLD_VELOCITY;
    }

    /**
     * 从上往下
     * @param e1
     * @param e2
     * @param velocityY
     * @return
     */
    public boolean isSwipeDown(MotionEvent e1, MotionEvent e2, float velocityY) {
        return isSwipe(e2.getY(), e1.getY(), velocityY);
    }

    /**
     * 从下往上
     * @param e1
     * @param e2
     * @param velocityY
     * @return
     */
    public boolean isSwipeUp(MotionEvent e1, MotionEvent e2, float velocityY) {
        return isSwipe(e1.getY(), e2.getY(), velocityY);
    }

    /**
     * 从右往左
     * @param e1
     * @param e2
     * @param velocityX
     * @return
     */
    public boolean isSwipeLeft(MotionEvent e1, MotionEvent e2, float velocityX) {
        return isSwipe(e1.getX(), e2.getX(), velocityX);
    }

    /**
     * 从左往右
     * @param e1
     * @param e2
     * @param velocityX
     * @return
     */
    public boolean isSwipeRight(MotionEvent e1, MotionEvent e2, float velocityX) {
        return isSwipe(e2.getX(), e1.getX(), velocityX);
    }

    /**
     * 实际划动距离是否大于指定参照的划动距离
     * @param coordinateA
     * @param coordinateB
     * @return
     */
    private boolean isSwipeDistance(float coordinateA, float coordinateB) {
        return (coordinateA - coordinateB) > this.swipe_distance;
    }

    /**
     * 实际划动速度是否大于指定参照的划动速度
     * @param velocity
     * @return
     */
    private boolean isSwipeSpeed(float velocity) {
        return Math.abs(velocity) > this.swipe_velocity;
    }

    /**
     * 划动是否有效
     * @param coordinateA
     * @param coordinateB
     * @param velocity
     * @return 若实际划动距离大于指定参照的划动距离并且实际划动速度大于指定参照的划动速度返回true
     */
    private boolean isSwipe(float coordinateA, float coordinateB, float velocity) {
        return isSwipeDistance(coordinateA, coordinateB) && isSwipeSpeed(velocity);
    }
}
