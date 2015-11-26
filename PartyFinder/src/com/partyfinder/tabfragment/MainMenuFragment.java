package com.partyfinder.tabfragment;


import com.partyfinder.R;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MainMenuFragment extends Fragment{
	
	public MainMenuFragment(){
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		//inflate the layout for this fragment
		View v = inflater.inflate(R.layout.tab_main_menu, container, false);
		return v;
		
	}

}
