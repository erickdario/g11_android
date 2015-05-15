package com.grupoonce.mensajes.helpers;

import java.util.ArrayList;

import com.firebase.client.Firebase;
import com.grupoonce.chat.FirebaseManager;
import com.grupoonce.mensajes.AdminMenuActivity;
import com.grupoonce.mensajes.MainMenuAdvisorActivity;
import com.grupoonce.mensajes.R;
import com.grupoonce.mensajes.admin.State;
import com.grupoonce.mensajes.admin.StatesListAdapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

public class AdminViewConstructor {

	public static ArrayList<State> listStates;
	public static StatesListAdapter adapter;
	public static ListView listViewStates;
	public static Firebase statesRef;

	public static LinearLayout ConstructHeader(AdminMenuActivity main) {
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

		Button signOut = SharedViewConstructor.ConstructSignOut(main, size,
				LayoutParams.WRAP_CONTENT, (int) (size.y * 0.02));

		menu.addView(user);
		menu.addView(signOut);
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

		listViewStates = new ListView(main);
		listStates = new ArrayList<State>();

		adapter = new StatesListAdapter(main, listStates, size);
		listViewStates.setAdapter(adapter);
		view.addView(listViewStates);

		listViewStates
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						TextView state = (TextView) view
								.findViewById(R.id.txtState);
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

		Intent intent = main.getIntent();
		String statesUrl = intent.getStringExtra("statesUrl");
		statesRef = new Firebase(statesUrl);
		FirebaseManager.FindStates();

		scrollView.addView(statesView);
		view.addView(scrollView);

		return view;

	}

}
