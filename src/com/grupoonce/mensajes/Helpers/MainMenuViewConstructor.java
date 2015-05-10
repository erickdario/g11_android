package com.grupoonce.mensajes.Helpers;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.grupoonce.chat.FirebaseManager;
import com.grupoonce.mensajes.FaqActivity;
import com.grupoonce.mensajes.ServicesActivity;
import com.grupoonce.mensajes.ChatActivity;
import com.grupoonce.mensajes.MainMenuActivity;
import com.grupoonce.mensajes.R;

public class MainMenuViewConstructor {

	@SuppressLint("NewApi")
	public static LinearLayout ConstructBody(final MainMenuActivity main) {
		Point size = SharedViewConstructor.GetScreenSize(main);

		LinearLayout view = SharedViewConstructor.ConstructBackground(main,
				size, LinearLayout.LayoutParams.MATCH_PARENT,
				(int) (size.y * 0.89));

		LinearLayout btnView = SharedViewConstructor.ConstructBackground(main,
				size, (int) (size.x * 0.80), (int) (size.y * 0.89));

		LayoutParams layout = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

		TextView welcome = SharedViewConstructor.ConstructTextView(main,
				layout, 30, main.getResources().getString(R.string.welcome),
				true, Color.BLACK);

		TextView title = SharedViewConstructor.ConstructTextView(main, layout,
				26, main.getResources().getString(R.string.title_main_menu),
				true, Color.BLACK);

		final Button faq = SharedViewConstructor.ConstructButton(main, size,
				R.string.faq, 0, (int) (size.y * 0.04),
				LayoutParams.MATCH_PARENT, R.drawable.session_btn_text,
				R.drawable.session_button);

		// Set click listener for button
		faq.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(main, FaqActivity.class);
				main.startActivity(intent);
			}
		});

		final Button services = SharedViewConstructor.ConstructButton(main,
				size, R.string.services, 0, (int) (size.y * 0.04),
				LayoutParams.MATCH_PARENT, R.drawable.session_btn_text,
				R.drawable.session_button);

		// Set click listener for button
		services.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(main, ServicesActivity.class);
				main.startActivity(intent);
			}
		});

		final Button chat = SharedViewConstructor.ConstructButton(main, size,
				R.string.chat, 0, (int) (size.y * 0.04),
				LayoutParams.MATCH_PARENT, R.drawable.session_btn_text,
				R.drawable.session_button);

		// Set click listener for button
		chat.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(main, ChatActivity.class);
				main.startActivity(intent);
			}
		});

		final Button signOut = SharedViewConstructor.ConstructButton(main,
				size, R.string.sign_out, 0, (int) (size.y * 0.04),
				LayoutParams.MATCH_PARENT, R.drawable.session_btn_text,
				R.drawable.close_session_button);

		// Set click listener for button
		signOut.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				main.finish();
				FirebaseManager.ref.unauth();
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
