package com.grupoonce.mensajes.helpers;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

import com.grupoonce.chat.FirebaseManager;
import com.grupoonce.mensajes.AdviserConfigurationActivity;
import com.grupoonce.mensajes.R;
import com.grupoonce.mensajes.admin.SharedG11ViewConstructor;

public class AdviserConfigurationConstructor {

	static String city;
	public static String oldPassword;
	public static String userName;
	public static TextView user;
	public static TextView oldPasswordView;
	public static String userKey;

	public static LinearLayout ConstructHeader(AdviserConfigurationActivity main) {
		FirebaseManager.role = "adviser";
		LinearLayout header = SharedViewConstructor.ConstructHeaderG11(main);

		LinearLayout menu = (LinearLayout) header.getChildAt(1);
		header.removeViewAt(1);

		Intent intentGet = main.getIntent();
		city = intentGet.getStringExtra("city");
		TextView user = SharedG11ViewConstructor.ConstructUserState(
				(Activity) main, city);

		FirebaseManager.FindUser(city);

		menu.addView(user);
		header.addView(menu);
		return header;
	}

	public static LinearLayout ConstructBody(
			final AdviserConfigurationActivity main) {

		Point size = SharedViewConstructor.GetScreenSize(main);

		LinearLayout view = SharedViewConstructor.ConstructBackground(main,
				size, LayoutParams.MATCH_PARENT, (int) (size.y * 0.79));

		LinearLayout viewContainer = SharedViewConstructor.ConstructBackground(
				main, size, (int) (size.x * 0.8), (int) (size.y * 0.79));

		// TODO create all elements on configuration body
		LayoutParams layout = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		layout.setMargins(0, (int) (size.x * 0.08), 0, 0);
		user = SharedViewConstructor.ConstructTextView(main, layout, 26, main
				.getResources().getString(R.string.user) + "\n" + userName,
				false, Color.BLACK);

		oldPasswordView = SharedViewConstructor.ConstructTextView(main, layout,
				26, main.getResources().getString(R.string.user) + "\n"
						+ oldPassword, false, Color.BLACK);

		final TextView newPasswordView = SharedViewConstructor
				.ContructG11EditText(main, R.string.new_password,
						InputType.TYPE_TEXT_VARIATION_PASSWORD);

		Button changePassword = SharedViewConstructor.ConstructButton(main,
				size, R.string.change_password, 0, (int) (size.y * 0.05),
				LayoutParams.WRAP_CONTENT, R.drawable.session_btn_text,
				R.drawable.session_button);
		changePassword.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				FirebaseManager.ChangePassword(userName, oldPassword,
						newPasswordView.getText().toString());
				newPasswordView.setText("");
			}
		});

		viewContainer.addView(user);
		viewContainer.addView(oldPasswordView);
		viewContainer.addView(newPasswordView);
		viewContainer.addView(changePassword);
		view.addView(viewContainer);
		return view;
	}

}
