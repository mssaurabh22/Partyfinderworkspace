package com.partyfinder.CustomView;

import java.util.Hashtable;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

public class CustomFontSemiBold extends TextView{

	private static final String asset="opensanssemibold.ttf";
	public CustomFontSemiBold(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public CustomFontSemiBold(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		setCustomFont(context, asset);
		// TODO Auto-generated constructor stub
	}

	public CustomFontSemiBold(Context context, AttributeSet attrs) {
		super(context, attrs);
		setCustomFont(context, asset);
		// TODO Auto-generated constructor stub
	}
	public boolean setCustomFont(Context ctx, String asset) {

		setTypeface(TypeFaceSemiBold .get(ctx, asset));
		return true;
	}
}
class TypeFaceSemiBold {
	private static final String TAG = "Typefaces";

	private static final Hashtable<String, Typeface> cache = new Hashtable<String, Typeface>();

	public static Typeface get(Context c, String assetPath) {
		synchronized (cache) {
			if (!cache.containsKey(assetPath)) {
				try {
					Typeface t = Typeface.createFromAsset(c.getAssets(),
							assetPath);
					cache.put(assetPath, t);
				} catch (Exception e) {
					Log.e(TAG, "Could not get typeface '" + assetPath
							+ "' because " + e.getMessage());
					return null;
				}
			}
			return cache.get(assetPath);
		}
	}
}


