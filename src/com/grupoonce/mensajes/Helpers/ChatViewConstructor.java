package com.grupoonce.mensajes.Helpers;

import java.util.ArrayList;
import java.util.Calendar;

import com.firebase.client.Firebase;
import com.grupoonce.chat.FirebaseManager;
import com.grupoonce.chat.MessagesListAdapter;
import com.grupoonce.chat.Msg;
import com.grupoonce.mensajes.ChatActivity;
import com.grupoonce.mensajes.R;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.LinearLayout.LayoutParams;

public class ChatViewConstructor {

	public static ArrayList<Msg> listMessages;
	public static MessagesListAdapter adapter;
	public static ListView listMsg;
	public static Firebase conversationRef;

	public static LinearLayout ContructConversation(ChatActivity main) {
		Point size = SharedViewConstructor.GetScreenSize(main);

		LinearLayout view = SharedViewConstructor.ConstructBackground(main,
				size, LinearLayout.LayoutParams.MATCH_PARENT,
				(int) (size.y * 0.901));

		listMsg = new ListView(main);
		listMessages = new ArrayList<Msg>();

		adapter = new MessagesListAdapter(main, listMessages);
		listMsg.setAdapter(adapter);
		listMsg.setSelection(listMsg.getCount() - 1);
		view.addView(listMsg);

		Intent intent = main.getIntent();
		String conversationUrl = intent.getStringExtra("conversationUrl");
		conversationRef = new Firebase(conversationUrl);
		FirebaseManager.FindConversation();

		return view;
	}

	public static LinearLayout ContructInputChat(final ChatActivity main) {
		Point size = SharedViewConstructor.GetScreenSize(main);

		LinearLayout view = new LinearLayout(main);
		view.setOrientation(LinearLayout.HORIZONTAL);
		view.setLayoutParams(new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT));
		view.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
		view.setBackgroundColor(main.getResources().getColor(R.color.black_g11));

		LinearLayout.LayoutParams layoutParams = new LayoutParams(
				(int) (size.x * 0.85), LayoutParams.WRAP_CONTENT);

		final EditText inputChat = new EditText(main);
		inputChat.setHint(main.getResources().getString(
				R.string.chat_input_holder));
		inputChat.setTextColor(Color.WHITE);
		inputChat.clearFocus();
		inputChat.setFocusableInTouchMode(true);

		inputChat.setLayoutParams(layoutParams);

		ImageButton btnSend = SharedViewConstructor.ContructImageButton(main,
				R.drawable.send_now, size, 0.1f);
		// Set click listener for button
		btnSend.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				SendMessage(listMessages, adapter, inputChat.getText()
						.toString());
				inputChat.setText("");
			}
		});
		view.setFocusableInTouchMode(true);
		view.addView(inputChat);
		view.addView(btnSend);

		return view;
	}

	public static void SendMessage(ArrayList<Msg> listMessages,
			MessagesListAdapter adapter, String message) {

		Calendar c = Calendar.getInstance();
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);
		int date = c.get(Calendar.DATE);

		String month = DecideMonth(Calendar.MONTH);

		Msg msg = new Msg(message, "client", "" + hour + ":" + minute, month
				+ " " + date);

		conversationRef.child("msg" + (listMsg.getCount() + 1)).setValue(msg);
	}

	private static String DecideMonth(int month) {
		switch (month) {
		case 1:
			return "January";

		case 2:
			return "February";

		case 3:
			return "March";

		case 4:
			return "April";

		case 5:
			return "May";

		case 6:
			return "June";

		case 7:
			return "July";

		case 8:
			return "August";

		case 9:
			return "September";

		case 10:
			return "October";

		case 11:
			return "November";

		case 12:
			return "December";

		default:
			return "Invalid month";

		}
	}
}
