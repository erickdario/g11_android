/**
 * 
 */

package com.grupoonce.helpers;

import java.util.ArrayList;

import com.firebase.client.Firebase;
import com.grupoonce.chat.Conversation;
import com.grupoonce.chat.ConversationsListAdapter;
import com.grupoonce.chat.FirebaseManager;
import com.grupoonce.helpers.admin.SharedG11ViewConstructor;
import com.grupoonce.mensajes.AdviserConfigurationActivity;
import com.grupoonce.mensajes.ChatActivity;
import com.grupoonce.mensajes.MainMenuAdvisorActivity;
import com.grupoonce.mensajes.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.TextView;

/**
 * Draws and manages all the interaction for the elements to be displayed in the
 * MainMenuAdvisorActivity activity
 * 
 * This is the activity for the administrator or the adviser when they have the
 * list of current active conversations
 * 
 * @author erickdario
 *
 */
public class MMAdviserViewConstructor {

	public static ArrayList<Conversation> listConversations;
	public static ConversationsListAdapter adapter;
	public static ListView listConvo;
	public static Firebase conversationsRef;
	public static String city;
	public static TextView newMessagesCounter;

	/**
	 * Draws all the visible elements for the header of the given activity
	 * 
	 * @param main
	 *            Activity we are going to draw the elements to
	 * @return A linear layout containing all the elements for the header
	 */
	public static LinearLayout ConstructHeader(
			final MainMenuAdvisorActivity main) {
		FirebaseManager.role = "adviser";
		LinearLayout header = SharedViewConstructor.ConstructHeaderG11(main);
		Point size = SharedViewConstructor.GetScreenSize(main);

		LinearLayout menu = (LinearLayout) header.getChildAt(1);
		header.removeViewAt(1);

		Intent intentGet = main.getIntent();
		city = intentGet.getStringExtra("city");
		LayoutParams layout = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		TextView user = SharedG11ViewConstructor.ConstructUserState(
				(Activity) main, city);

		LinearLayout viewMessage = new LinearLayout(main);
		viewMessage.setLayoutParams(layout);
		viewMessage.setOrientation(LinearLayout.HORIZONTAL);
		viewMessage.setBackgroundColor(Color.TRANSPARENT);
		viewMessage.setGravity(Gravity.CENTER_HORIZONTAL);

		LayoutParams layoutTextView = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		TextView newMessages = SharedViewConstructor.ConstructTextView(
				(Activity) main, layoutTextView, 20, main.getResources()
						.getString(R.string.new_messages), false, Color.BLACK);
		newMessages.setBackgroundColor(Color.TRANSPARENT);

		newMessagesCounter = SharedViewConstructor.ConstructTextView(
				(Activity) main, layoutTextView, 20, "0", false, Color.BLACK);
		newMessagesCounter.setBackgroundColor(Color.TRANSPARENT);

		Intent intent = main.getIntent();
		String role = intent.getStringExtra("role");

		Button signOutOrConfiguration = null;
		if (role == null) {
			signOutOrConfiguration = SharedViewConstructor.ConstructSignOut(
					main, size, LayoutParams.WRAP_CONTENT, 0,
					(int) (size.y * 0.02));
		} else {
			signOutOrConfiguration = SharedViewConstructor.ConstructButton(
					main, size, R.string.configuration, 0,
					(int) (size.y * 0.02), 0, 0, LayoutParams.WRAP_CONTENT,
					R.drawable.session_btn_text,
					R.drawable.close_session_button);

			// Set click listener for button
			signOutOrConfiguration.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					Intent intent = new Intent(main,
							AdviserConfigurationActivity.class);
					intent.putExtra("city", city);
					main.startActivity(intent);
				}
			});
		}

		viewMessage.addView(newMessages);
		viewMessage.addView(newMessagesCounter);
		menu.addView(user);
		menu.addView(viewMessage);
		menu.addView(signOutOrConfiguration);
		header.addView(menu);

		return header;
	}

	/**
	 * Draws all the visible elements inside the body for the given activity
	 * 
	 * @param main
	 *            Activity we are going to draw the elements on
	 * @return A linear layout containing all the elements for the body
	 */
	public static LinearLayout ConstructBody(final MainMenuAdvisorActivity main) {
		Point size = SharedViewConstructor.GetScreenSize(main);

		LinearLayout view = SharedViewConstructor.ConstructBackground(main,
				size, LinearLayout.LayoutParams.MATCH_PARENT,
				(int) (size.y * 0.79));

		ScrollView scrollView = SharedViewConstructor.ConstructScrollView(main,
				LayoutParams.MATCH_PARENT, size);

		LinearLayout conversationsView = SharedViewConstructor
				.ConstructBackground(main, size,
						LinearLayout.LayoutParams.MATCH_PARENT,
						(int) (size.y * 0.79));

		listConvo = new ListView(main);
		listConversations = new ArrayList<Conversation>();

		adapter = new ConversationsListAdapter(main, listConversations);
		listConvo.setAdapter(adapter);
		view.addView(listConvo);

		listConvo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				TextView companyName = (TextView) view
						.findViewById(R.id.txtCompany);
				TextView userName = (TextView) view
						.findViewById(R.id.lblUserName);
				Intent intent = new Intent(main, ChatActivity.class);
				String conversationUrl = FirebaseManager.ref.toString()
						+ "/messages/" + city + "/"
						+ userName.getText().toString() + "%"
						+ companyName.getText().toString();
				intent.putExtra("conversationUrl", conversationUrl);
				intent.putExtra("role", "adviser");
				MMAdviserViewConstructor.listConversations.get(position)
						.setRead(true);
				main.startActivityForResult(intent, 0xe110);
			}
		});

		Intent intent = main.getIntent();
		String conversationsUrl = intent.getStringExtra("conversationsUrl");
		conversationsRef = new Firebase(conversationsUrl);
		FirebaseManager.FindConversations();
		scrollView.addView(conversationsView);
		view.addView(scrollView);

		return view;
	}

}
