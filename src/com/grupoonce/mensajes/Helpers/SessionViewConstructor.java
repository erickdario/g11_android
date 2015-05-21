package com.grupoonce.mensajes.helpers;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.grupoonce.chat.FirebaseManager;
import com.grupoonce.mensajes.MainActivity;
import com.grupoonce.mensajes.R;

import android.annotation.SuppressLint;
import android.graphics.Point;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class SessionViewConstructor {
	
	public static Button loginBtn;

	public static LinearLayout ContructBody(MainActivity main) {
		Firebase.setAndroidContext(main);
		FirebaseManager.main = main;
		AuthData authData = FirebaseManager.ref.getAuth();
		if (authData != null) {
			Toast.makeText(main, "Recuperando sesión activa",
					Toast.LENGTH_SHORT).show();
			FirebaseManager.UserWasAuthenticated(authData);
		} else {
			// no user authenticated with Firebase
		}

		Point size = SharedViewConstructor.GetScreenSize(main);

		LinearLayout view = SharedViewConstructor.ConstructBackground(main,
				size, LinearLayout.LayoutParams.MATCH_PARENT,
				(int) (size.y * 0.89));

		final LinearLayout viewLogin = ConstructLoginView(main, size);

		view.addView(viewLogin);
		return view;
	}

	@SuppressLint("NewApi")
	private static LinearLayout ConstructLoginView(final MainActivity main,
			Point size) {
		final LinearLayout viewLogin = new LinearLayout(main);
		viewLogin.setOrientation(LinearLayout.VERTICAL);
		viewLogin.setLayoutParams(new LinearLayout.LayoutParams(
				(int) (size.x * 0.80), (int) (size.y * 0.79)));

		final EditText email = SharedViewConstructor
				.ContructG11EditText(main, R.string.email,
						InputType.TYPE_TEXT_VARIATION_WEB_EMAIL_ADDRESS);

		final EditText password = SharedViewConstructor
				.ContructG11EditText(main, R.string.password,
						InputType.TYPE_TEXT_VARIATION_PASSWORD);

		loginBtn = SharedViewConstructor.ConstructButton(main,
				size, R.string.log_in, 0, 0, 0, 0, LayoutParams.WRAP_CONTENT,
				R.drawable.session_btn_text, R.drawable.session_button);

		RelativeLayout relativeLayout = new RelativeLayout(main);
		relativeLayout.setPadding(0, 0, 0, 100);
		RelativeLayout.LayoutParams btnLayoutParams = new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		btnLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		btnLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		loginBtn.setLayoutParams(btnLayoutParams);

		// Set click listener for button
		loginBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				FirebaseManager.ref.authWithPassword(
						email.getText().toString(), password.getText()
								.toString(), FirebaseManager.authResultHandler);
				loginBtn.setEnabled(false);
			}
		});

		relativeLayout.addView(loginBtn);
		viewLogin.addView(email);
		viewLogin.addView(password);
		viewLogin.addView(relativeLayout);

		return viewLogin;
	}
}
