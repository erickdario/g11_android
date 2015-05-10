package com.grupoonce.mensajes.Helpers;

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
import android.widget.LinearLayout.LayoutParams;

public class ChatViewConstructor {

	public static LinearLayout ContructConversation(ChatActivity main){
		Point size = SharedViewConstructor.GetScreenSize(main);

		LinearLayout view = SharedViewConstructor.ConstructBackground(main,
				size, LinearLayout.LayoutParams.MATCH_PARENT,
				(int) (size.y * 0.901));
		return view;
	}

	public static LinearLayout ContructInputChat(ChatActivity main){
		Point size = SharedViewConstructor.GetScreenSize(main);

		LinearLayout view = new LinearLayout(main);
		view.setOrientation(LinearLayout.HORIZONTAL);
		view.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
		view.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
		view.setBackgroundColor(main.getResources().getColor(R.color.black_g11));

		LinearLayout.LayoutParams layoutParams = new LayoutParams(
				(int) (size.x * 0.85), LayoutParams.WRAP_CONTENT);

		EditText inputChat = new EditText(main);
		inputChat.setHint(main.getResources().getString(R.string.chat_input_holder));
		inputChat.setTextColor(Color.WHITE);

		inputChat.setLayoutParams(layoutParams);

		ImageButton btnSend = SharedViewConstructor.ContructImageButton(main, R.drawable.send_now, size, 0.1f);
		// Set click listener for button
		btnSend.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				SendMessage();
			}
		});

		view.addView(inputChat);
		view.addView(btnSend);

		return view;
	}

	public static void SendMessage(){
		//TODO Send message to cloud 
		//TODO Draw message bubble and arrage view
	}

}
