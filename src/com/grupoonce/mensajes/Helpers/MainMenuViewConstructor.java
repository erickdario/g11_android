package com.grupoonce.mensajes.Helpers;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Point;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.grupoonce.mensajes.FaqActivity;
import com.grupoonce.mensajes.MainMenuActivity;
import com.grupoonce.mensajes.R;

public class MainMenuViewConstructor {

	@SuppressLint("NewApi")
	public static LinearLayout ContructMainMenuBody(final MainMenuActivity main) {
		Point size = SharedViewConstructor.GetScreenSize(main);

		LinearLayout view = SharedViewConstructor.ConstructBackground(main,
				size, LinearLayout.LayoutParams.MATCH_PARENT);

		LinearLayout btnView = SharedViewConstructor.ConstructBackground(main,
				size, (int) (size.x * 0.80));

		TextView welcome = new TextView(main);
		welcome.setLayoutParams(new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		welcome.setText(main.getResources().getString(R.string.welcome));
		welcome.setTextSize(30);
		welcome.setGravity(Gravity.CENTER);

		TextView title = new TextView(main);
		title.setLayoutParams(new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		title.setText(main.getResources().getString(R.string.title_main_menu));
		title.setTextSize(26);
		title.setGravity(Gravity.CENTER);

		final Button faq = SharedViewConstructor.ConstructOrangeButton(main,
				size, R.string.log_in, 0, (int) (size.y * 0.04),
				LayoutParams.MATCH_PARENT, R.drawable.session_btn_text,
				R.drawable.session_button);

		// Set click listener for button
		faq.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(main, FaqActivity.class);
				main.startActivity(intent);
			}
		});

		final Button services = SharedViewConstructor.ConstructOrangeButton(
				main, size, R.string.services, 0, (int) (size.y * 0.04),
				LayoutParams.MATCH_PARENT, R.drawable.session_btn_text,
				R.drawable.session_button);

		// Set click listener for button
		services.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(main, FaqActivity.class);
				main.startActivity(intent);
			}
		});

		final Button chat = SharedViewConstructor.ConstructOrangeButton(main,
				size, R.string.chat, 0, (int) (size.y * 0.04),
				LayoutParams.MATCH_PARENT, R.drawable.session_btn_text,
				R.drawable.session_button);

		// Set click listener for button
		chat.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(main, FaqActivity.class);
				main.startActivity(intent);
			}
		});

		final Button signOut = SharedViewConstructor.ConstructOrangeButton(
				main, size, R.string.sign_out, 0, (int) (size.y * 0.04),
				LayoutParams.MATCH_PARENT, R.drawable.session_btn_text,
				R.drawable.close_session_button);

		// Set click listener for button
		signOut.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				main.finish();
			}
		});

		btnView.addView(welcome);
		btnView.addView(title);
		btnView.addView(faq);
		btnView.addView(services);
		btnView.addView(chat);
		btnView.addView(signOut);
		view.addView(btnView);
		return view;
	}

}
