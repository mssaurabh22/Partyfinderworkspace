package com.partyfinder.floorPlan;

import com.partyfinder.R;
import com.partyfinder.ReserveTableActivity;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

public class MyPagerAdapter extends FragmentPagerAdapter implements
		ViewPager.OnPageChangeListener {

	private MyLinearLayout cur = null;
	private MyLinearLayout next = null;
	private ReserveTableActivity context;
	private FragmentManager fm;
	private float scale;

	public MyPagerAdapter(ReserveTableActivity context, FragmentManager fm) {
		super(fm);
		this.fm = fm;
		this.context = context;
	}

	@Override
	public Fragment getItem(int position) 
	{
        // make the first pager bigger than others
        if (position == ReserveTableActivity.FIRST_PAGE)
        	scale = ReserveTableActivity.BIG_SCALE;     	
        else
        	scale = ReserveTableActivity.SMALL_SCALE;
        
        position = position % ReserveTableActivity.PAGES;
        Log.i("","Get Item position : "+position);
        return MyFragment.newInstance(context, position, scale);
	}

	@Override
	public int getCount()
	{		
		return ReserveTableActivity.PAGES * ReserveTableActivity.LOOPS;
	}

	@Override
	public void onPageScrolled(int position, float positionOffset,
			int positionOffsetPixels) 
	{	
		if (positionOffset >= 0f && positionOffset <= 1f)
		{
			cur = getRootView(position);
			next = getRootView(position +1);

			cur.setScaleBoth(ReserveTableActivity.BIG_SCALE 
					- ReserveTableActivity.DIFF_SCALE * positionOffset);
			next.setScaleBoth(ReserveTableActivity.SMALL_SCALE 
					+ ReserveTableActivity.DIFF_SCALE * positionOffset);
		}
	}

	@Override
	public void onPageSelected(int position) {
		
	}
	
	@Override
	public void onPageScrollStateChanged(int state) {
		
	}
	
	private MyLinearLayout getRootView(int position)
	{
		Log.i("","pager Item position : "+position);
		return (MyLinearLayout) 
				fm.findFragmentByTag(this.getFragmentTag(position))
				.getView().findViewById(R.id.root);
	}
	
	private String getFragmentTag(int position)
	{
	    return  "android:switcher:" + context.pager.getId() + ":" + position;
	}
}
