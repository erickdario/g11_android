/**
 * 
 */

package com.grupoonce.helpers;

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

/**
 * Draws and manages all the interaction for the elements to be displayed in the
 * MainMenuActivity activity
 * 
 * This is the activity where the user can see all the options within the
 * application
 * 
 * @author erickdario
 *
 */
public class MainMenuViewConstructor {

	/**
	 * Draws all the visible elements inside the body for the given activity
	 * This is activity with the main menu for the user after a succeful log in
	 * 
	 * @param main
	 *            Activity we are going to draw the elements on
	 * @return A linear layout containing all the elements for the body
	 */
	public static LinearLayout ConstructBody(final MainMenuActivity main) {
		FirebaseManager.role = "client";
		Point size = SharedViewConstructor.GetScreenSize(main);

		LinearLayout view = SharedViewConstructor.ConstructBackground(main,
				size, LinearLayout.LayoutParams.MATCH_PARENT,
				(int) (size.y * 0.79));

		LinearLayout btnView = SharedViewConstructor.ConstructBackground(main,
				size, (int) (size.x * 0.80), (int) (size.y * 0.79));

		LayoutParams layout = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

		TextView welcome = SharedViewConstructor.ConstructTextView(main,
				layout, 30, main.getResources().getString(R.string.welcome),
				true, Color.BLACK);

		TextView title = SharedViewConstructor.ConstructTextView(main, layout,
				26, main.getResources().getString(R.string.title_main_menu),
				true, Color.BLACK);

		Button faq = SharedViewConstructor.ConstructButton(main, size,
				R.string.faq, 0, (int) (size.y * 0.04), 0, 0,
				LayoutParams.MATCH_PARENT, R.drawable.session_btn_text,
				R.drawable.session_button);

		// Set click listener for button
		faq.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(main, FaqActivity.class);
				main.startActivity(intent);
			}
		});

		Button services = SharedViewConstructor.ConstructButton(main, size,
				R.string.services, 0, (int) (size.y * 0.04), 0, 0,
				LayoutParams.MATCH_PARENT, R.drawable.session_btn_text,
				R.drawable.session_button);

		// Set click listener for button
		services.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(main, ServicesActivity.class);
				main.startActivity(intent);
			}
		});

		Button chat = SharedViewConstructor.ConstructButton(main, size,
				R.string.chat, 0, (int) (size.y * 0.04), 0, 0,
				LayoutParams.MATCH_PARENT, R.drawable.session_btn_text,
				R.drawable.session_button);

		// Set click listener for button
		chat.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(main, ChatActivity.class);
				Intent intentGet = main.getIntent();
				String conversationUrl = intentGet
						.getStringExtra("conversationUrl");
				intent.putExtra("conversationUrl", conversationUrl);
				intent.putExtra("role", "client");
				main.startActivityForResult(intent, 0xe110);
			}
		});

		Button signOut = SharedViewConstructor.ConstructSignOut(main, size,
				LayoutParams.MATCH_PARENT, 0, (int) (size.y * 0.04));

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
