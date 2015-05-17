package com.grupoonce.mensajes.helpers;

import com.grupoonce.chat.FirebaseManager;
import com.grupoonce.mensajes.AdminMenuActivity;
import com.grupoonce.mensajes.ChartsActivity;
import com.grupoonce.mensajes.MainMenuAdvisorActivity;
import com.grupoonce.mensajes.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

public class AdminViewConstructor {

	public static LinearLayout ConstructHeader(final AdminMenuActivity main) {
		FirebaseManager.role = "adviser";
		LinearLayout header = SharedViewConstructor.ConstructHeaderG11(main);
		Point size = SharedViewConstructor.GetScreenSize(main);

		LinearLayout menu = (LinearLayout) header.getChildAt(1);
		header.removeViewAt(1);

		LayoutParams layout = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

		TextView user = SharedViewConstructor.ConstructTextView(
				(Activity) main, layout, 20,
				main.getResources().getString(R.string.admin_user), false,
				Color.BLACK);
		user.setBackgroundColor(Color.TRANSPARENT);
		user.setGravity(Gravity.CENTER_HORIZONTAL);

		LinearLayout view = new LinearLayout(main);
		view.setOrientation(LinearLayout.HORIZONTAL);
		view.setGravity(Gravity.CENTER_HORIZONTAL);

		Button signOut = SharedViewConstructor.ConstructSignOut(main, size,
				LayoutParams.WRAP_CONTENT, (int) (size.y * 0.01),
				(int) (size.y * 0.01));

		Button charts = SharedViewConstructor.ConstructButton(main, size,
				R.string.charts, 0, (int) (size.y * 0.01),
				LayoutParams.WRAP_CONTENT, R.drawable.session_btn_text,
				R.drawable.close_session_button);
		
		charts.setOnClickListener(new AdapterView.OnClickListener() {
			public void onClick(View view) {
				Intent intent = new Intent(main,
						ChartsActivity.class);
				intent.putExtra("role", "admin");
				main.startActivityForResult(intent, 0xe110);
			}
		});
		

		view.addView(charts);
		view.addView(signOut);
		menu.addView(user);
		menu.addView(view);
		header.addView(menu);

		return header;

	}

	public static LinearLayout ConstructBody(final AdminMenuActivity main) {

		Point size = SharedViewConstructor.GetScreenSize(main);

		LinearLayout view = SharedViewConstructor.ConstructBackground(main,
				size, LinearLayout.LayoutParams.MATCH_PARENT,
				(int) (size.y * 0.79));

		ScrollView scrollView = SharedViewConstructor.ConstructScrollView(main,
				LayoutParams.MATCH_PARENT, size);

		LinearLayout statesView = SharedViewConstructor.ConstructBackground(
				main, size, LinearLayout.LayoutParams.MATCH_PARENT,
				(int) (size.y * 0.79));

		String[] states_array = main.getResources().getStringArray(
				R.array.states_array);
		String[] states_array_img_ids = main.getResources().getStringArray(
				R.array.states_array_img_ids);
		for (int i = 0; i < states_array.length; i++) {
			LinearLayout stateView = new LinearLayout(main);
			stateView.setOrientation(LinearLayout.HORIZONTAL);
			stateView.setLayoutParams(new LinearLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			stateView.setBackgroundColor(Color.TRANSPARENT);

			String fname = states_array_img_ids[i];
			int id = main.getResources().getIdentifier(fname, "drawable",
					"com.grupoonce.mensajes");

			ImageButton stateImage = SharedViewConstructor
					.ConstructImageButton(main, id, size, 0.1f);
			LayoutParams params = (LayoutParams) stateImage.getLayoutParams();
			params.gravity = Gravity.START;
			stateImage.setLayoutParams(params);

			final TextView state = SharedViewConstructor.ConstructTextView(
					main, new LinearLayout.LayoutParams(
							LayoutParams.WRAP_CONTENT,
							LayoutParams.WRAP_CONTENT), 28, states_array[i],
					false, Color.BLACK);

			stateView.setOnClickListener(new AdapterView.OnClickListener() {
				public void onClick(View view) {
					Intent intent = new Intent(main,
							MainMenuAdvisorActivity.class);
					String conversationsUrl = "https://glaring-heat-1751.firebaseio.com/messages/"
							+ state.getText().toString();
					intent.putExtra("conversationsUrl", conversationsUrl);
					intent.putExtra("city", state.getText().toString());
					intent.putExtra("role", "admin");
					main.startActivityForResult(intent, 0xe110);
				}
			});

			stateView.addView(stateImage);
			stateView.addView(state);
			statesView.addView(stateView);
		}

		scrollView.addView(statesView);
		view.addView(scrollView);
		return view;
	}

}
