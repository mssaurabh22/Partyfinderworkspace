package com.partyfinder.CustomView;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class CenteringHorizontalScrollView extends HorizontalScrollView implements View.OnTouchListener {

private static final int SWIPE_PAGE_ON_FACTOR = 10;

private int mActiveItem;

private float mPrevScrollX;

private boolean mStart;

private int mItemWidth;
View targetLeft, targetRight;
ImageView leftImage, rightImage;

public CenteringHorizontalScrollView(Context context, AttributeSet attrs) {
    super(context, attrs);

    mItemWidth = 100; // or whatever your item width is.
//    setScrollX(1000);
    setOnTouchListener(this);
    
    Log.i("","Horizontal scroll view class : cunstructor is calling ");
}


@Override
public boolean onTouch(View v, MotionEvent event) {
    int x = (int) event.getRawX();

    boolean handled = false;
    switch (event.getAction()) {
        case MotionEvent.ACTION_MOVE:
            if (mStart) {
                mPrevScrollX = x;
                mStart = false;
            }
            Log.i("","Horizontal scroll view class : MotionEvent.ACTION_MOVE "+" previous scroll : "+mPrevScrollX);

            break;
        case MotionEvent.ACTION_UP:
            mStart = true;
            int minFactor = mItemWidth / SWIPE_PAGE_ON_FACTOR;

            if ((mPrevScrollX - (float) x) > minFactor) {
                if (mActiveItem < getMaxItemCount() - 1) {
                    mActiveItem = mActiveItem + 1;
                    Log.i("","Horizontal scroll view class : MotionEvent.ACTION_UP "+mActiveItem +" total count : "+ getMaxItemCount()+" previous scroll : "+ mPrevScrollX);
                    
                }
            }
            else if (((float) x - mPrevScrollX) > minFactor) {
                if (mActiveItem > 0) {
                    mActiveItem = mActiveItem - 1;
                    Log.i("","Horizontal scroll view class : MotionEvent.ACTION_UP "+mActiveItem+"  total count : "+ getMaxItemCount()+" previous scroll : "+ mPrevScrollX);
                    
                }
            }

            scrollToActiveItem();

            handled = true;
            break;
    }

    return handled;
}

private int getMaxItemCount() {
    return ((LinearLayout) getChildAt(0)).getChildCount();
}

private LinearLayout getLinearLayout() {
    return (LinearLayout) getChildAt(0);
}

/**
 * Centers the current view the best it can.
 */
/*public void centerCurrentItem() {
    if (getMaxItemCount() == 0) {
        return;
    }

    int currentX = getScrollX();
    View targetChild;
    int currentChild = -1;

    do {
        currentChild++;
        targetChild = getLinearLayout().getChildAt(currentChild);
    } while (currentChild < getMaxItemCount() && targetChild.getLeft() < currentX);

    if (mActiveItem != currentChild) {
        mActiveItem = currentChild;
        scrollToActiveItem();
    }
}*/

/*public void centerCurrentItem() {
    if (getMaxItemCount() == 0) {
        return;
    }
	 Log.i("","Horizontal scroll view class : centerCurrentItem() is calling ");
    int currentX = getScrollX();
    View targetChild = null;
    int currentChild = -1;
int maxItem=getMaxItemCount();
    do {
        currentChild++;
        

        if(currentChild>getMaxItemCount()){
        	currentChild=0;
        	maxItem=getMaxItemCount();
        	currentX=0;
        }
        targetChild = getLinearLayout().getChildAt(currentChild);
        Log.i("","Horizontal scroll view class :"+" current child :"+currentChild);
        Log.i("","Horizontal scroll view class :"+" maxitem :"+maxItem);
        Log.i("","Horizontal scroll view class :"+" terget child getleft :"+targetChild.getLeft());
        Log.i("","Horizontal scroll view class :"+" currentX :"+currentX);

    } while (currentChild < maxItem && targetChild.getLeft() < currentX );

    if (mActiveItem != currentChild) {
        mActiveItem = currentChild;
        scrollToActiveItem();
    }
}*/

/**
 * Scrolls the list view to the currently active child.
 */
private void scrollToActiveItem() {
	 Log.i("","Horizontal scroll view class : scrollToActiveItem() is calling ");
    int maxItemCount = getMaxItemCount();
    if (maxItemCount == 0) {
        return;
    }

    int targetItem = Math.min(maxItemCount - 1, mActiveItem);
    targetItem = Math.max(0, targetItem);

    mActiveItem = targetItem;

    // Scroll so that the target child is centered
    View targetView = getLinearLayout().getChildAt(targetItem);

    ImageView centerImage = (ImageView)targetView;
    int height=300;//set size of centered image
    LinearLayout.LayoutParams flparams = new LinearLayout.LayoutParams(height, height);
    centerImage.setLayoutParams(flparams);

    //get the image to left of the centered image
    if((targetItem-1)>=0){
        targetLeft = getLinearLayout().getChildAt(targetItem-1);
        leftImage = (ImageView)targetLeft;
        int width=250;//set the size of left image
        LinearLayout.LayoutParams leftParams = new LinearLayout.LayoutParams(width,width);
        leftParams.setMargins(0, 30, 0, 0);
        leftImage.setLayoutParams(leftParams);
    }

    //get the image to right of the centered image
    if((targetItem+1)<maxItemCount){
        targetRight = getLinearLayout().getChildAt(targetItem+1);
        rightImage = (ImageView)targetRight;
        int width=250;//set the size of right image
        LinearLayout.LayoutParams rightParams = new LinearLayout.LayoutParams(width,width);
        rightParams.setMargins(0, 30, 0, 0);
        rightImage.setLayoutParams(rightParams);
    }

    int targetLeft = targetView.getLeft();
    int childWidth = targetView.getRight() - targetLeft;

    int width = getWidth() - getPaddingLeft() - getPaddingRight();
    int targetScroll = targetLeft - ((width - childWidth) / 2);

    super.smoothScrollTo(targetScroll, 0);
}

/**
 * Sets the current item and centers it.
 * @param currentItem The new current item.
 */
public void setCurrentItemAndCenter(int currentItem) {
    mActiveItem = currentItem;
    scrollToActiveItem();
}

}