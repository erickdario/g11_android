package com.grupoonce.helpers.admin;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

import com.grupoonce.helpers.SharedViewConstructor;
import com.grupoonce.mensajes.R;

public class SharedG11ViewConstructor {

	public static TextView ConstructUserState(Activity main, String city) {
		LayoutParams layout = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		TextView user = SharedViewConstructor.ConstructTextView(main, layout,
				20, main.getResources().getString(R.string.user) + " " + city,
				false, Color.BLACK);
		user.setBackgroundColor(Color.TRANSPARENT);
		user.setGravity(Gravity.CENTER_HORIZONTAL);

		return user;
	}
}
