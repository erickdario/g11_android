package com.grupoonce.mensajes.Helpers;

import java.util.ArrayList;

import com.grupoonce.chat.MessagesListAdapter;
import com.grupoonce.chat.Msg;
import com.grupoonce.mensajes.ChatActivity;
import com.grupoonce.mensajes.R;

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

	static ArrayList<Msg> listMessages;
	static MessagesListAdapter adapter;
	static ListView listMsg;

	public static LinearLayout ContructConversation(ChatActivity main) {
		Point size = SharedViewConstructor.GetScreenSize(main);

		LinearLayout view = SharedViewConstructor.ConstructBackground(main,
				size, LinearLayout.LayoutParams.MATCH_PARENT,
				(int) (size.y * 0.901));

		listMsg = new ListView(main);
		listMessages = new ArrayList<Msg>();

		// TODO retrieve previous messages from this conversation

		adapter = new MessagesListAdapter(main, listMessages);
		listMsg.setAdapter(adapter);
		listMsg.setSelection(listMsg.getCount() - 1);
		view.addView(listMsg);

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

		inputChat.setLayoutParams(layoutParams);

		ImageButton btnSend = SharedViewConstructor.ContructImageButton(main,
				R.drawable.send_now, size, 0.1f);
		// Set click listener for button
		btnSend.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				SendMessage(listMessages, adapter, inputChat.getText()
						.toString());
			}
		});

		view.addView(inputChat);
		view.addView(btnSend);

		return view;
	}

	public static void SendMessage(ArrayList<Msg> listMessages,
			MessagesListAdapter adapter, String message) {
		// TODO Send message to cloud

		listMessages.add(new Msg("2cle", "2email", message, null, true, "2f",
				"2time", "2date"));
		adapter.notifyDataSetChanged();
		listMsg.setSelection(listMsg.getCount() - 1);
	}
}
