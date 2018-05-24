package com.android.gesture;

import android.view.MotionEvent;

public class SwipeDetector {

	/**
	 * ָ�����յĻ�������
	 */
    private int swipe_distance;
    /**
     * ָ�����յĻ����ٶ�
     */
    private int swipe_velocity;
    /**
     * Ĭ����С��������
     */
    private static final int SWIPE_MIN_DISTANCE = 120;
    /**
     * Ĭ�ϻ����ٶ�
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
     * ��������
     * @param e1
     * @param e2
     * @param velocityY
     * @return
     */
    public boolean isSwipeDown(MotionEvent e1, MotionEvent e2, float velocityY) {
        return isSwipe(e2.getY(), e1.getY(), velocityY);
    }

    /**
     * ��������
     * @param e1
     * @param e2
     * @param velocityY
     * @return
     */
    public boolean isSwipeUp(MotionEvent e1, MotionEvent e2, float velocityY) {
        return isSwipe(e1.getY(), e2.getY(), velocityY);
    }

    /**
     * ��������
     * @param e1
     * @param e2
     * @param velocityX
     * @return
     */
    public boolean isSwipeLeft(MotionEvent e1, MotionEvent e2, float velocityX) {
        return isSwipe(e1.getX(), e2.getX(), velocityX);
    }

    /**
     * ��������
     * @param e1
     * @param e2
     * @param velocityX
     * @return
     */
    public boolean isSwipeRight(MotionEvent e1, MotionEvent e2, float velocityX) {
        return isSwipe(e2.getX(), e1.getX(), velocityX);
    }

    /**
     * ʵ�ʻ��������Ƿ����ָ�����յĻ�������
     * @param coordinateA
     * @param coordinateB
     * @return
     */
    private boolean isSwipeDistance(float coordinateA, float coordinateB) {
        return (coordinateA - coordinateB) > this.swipe_distance;
    }

    /**
     * ʵ�ʻ����ٶ��Ƿ����ָ�����յĻ����ٶ�
     * @param velocity
     * @return
     */
    private boolean isSwipeSpeed(float velocity) {
        return Math.abs(velocity) > this.swipe_velocity;
    }

    /**
     * �����Ƿ���Ч
     * @param coordinateA
     * @param coordinateB
     * @param velocity
     * @return ��ʵ�ʻ����������ָ�����յĻ������벢��ʵ�ʻ����ٶȴ���ָ�����յĻ����ٶȷ���true
     */
    private boolean isSwipe(float coordinateA, float coordinateB, float velocity) {
        return isSwipeDistance(coordinateA, coordinateB) && isSwipeSpeed(velocity);
    }
}
