package com.grupoonce.mensajes.Helpers;

import java.util.ArrayList;

import com.firebase.client.Firebase;
import com.grupoonce.chat.Conversation;
import com.grupoonce.chat.ConversationsListAdapter;
import com.grupoonce.chat.FirebaseManager;
import com.grupoonce.mensajes.ChatActivity;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.TextView;

public class MMAdvisorViewConstructor {

	public static ArrayList<Conversation> listConversations;
	public static ConversationsListAdapter adapter;
	public static ListView listConvo;
	public static Firebase conversationsRef;
	static String city;
	public static TextView newMessagesCounter;

	public static LinearLayout ConstructHeader(MainMenuAdvisorActivity main) {
		FirebaseManager.role = "adviser";
		LinearLayout header = SharedViewConstructor.ConstructHeaderG11(main);
		Point size = SharedViewConstructor.GetScreenSize(main);

		LinearLayout menu = (LinearLayout) header.getChildAt(1);
		header.removeViewAt(1);

		Intent intentGet = main.getIntent();
		city = intentGet.getStringExtra("city");
		LayoutParams layout = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		TextView user = SharedViewConstructor.ConstructTextView(
				(Activity) main, layout, 20,
				main.getResources().getString(R.string.user) + " " + city,
				false, Color.BLACK);
		user.setBackgroundColor(Color.TRANSPARENT);
		user.setGravity(Gravity.CENTER_HORIZONTAL);

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

		Button signOut = SharedViewConstructor.ConstructSignOut(main, size,
				LayoutParams.WRAP_CONTENT, (int) (size.y * 0.02));

		menu.addView(user);
		viewMessage.addView(newMessages);
		viewMessage.addView(newMessagesCounter);
		menu.addView(viewMessage);
		menu.addView(signOut);
		header.addView(menu);

		return header;
	}

	public static LinearLayout ConstructBody(final MainMenuAdvisorActivity main) {
		Point size = SharedViewConstructor.GetScreenSize(main);

		LinearLayout view = SharedViewConstructor.ConstructBackground(main,
				size, LinearLayout.LayoutParams.MATCH_PARENT,
				(int) (size.y * 0.79));

		ScrollView scrollView = SharedViewConstructor.ContructScrollView(main,
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
						.findViewById(R.id.txtConversation);
				TextView userName = (TextView) view
						.findViewById(R.id.lblUserName);
				Intent intent = new Intent(main, ChatActivity.class);
				String conversationUrl = "https://glaring-heat-1751.firebaseio.com/messages/"
						+ city + "/" + userName.getText().toString() + "%" + companyName.getText().toString();
				intent.putExtra("conversationUrl", conversationUrl);
				intent.putExtra("role", "adviser");
				MMAdvisorViewConstructor.listConversations.get(position)
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
