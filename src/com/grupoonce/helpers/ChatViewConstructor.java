/**
 * 
 */

package com.grupoonce.helpers;

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

/**
 * Draws and manages all the interaction for the elements to be displayed in the
 * ChatActivity activity
 * 
 * @author erickdario
 *
 */
public class ChatViewConstructor {

	public static ArrayList<Msg> listMessages;
	public static MessagesListAdapter adapter;
	public static ListView listMsg;
	public static Firebase conversationRef;
	public static String role;

	/**
	 * Sets up and loads a previous conversation from Firebase, if any, and sets
	 * the cursor of the conversation on the last item
	 * 
	 * @param main
	 *            Activity trying to draw the list of active messages in the
	 *            cloud
	 * @return A linear layout containing a list view with all the messages
	 */
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
		role = intent.getStringExtra("role");
		conversationRef = new Firebase(conversationUrl);
		FirebaseManager.FindConversation();

		return view;
	}

	/**
	 * Constructs the input edit text and send button for the chat view
	 * 
	 * @param main
	 *            Activity trying to draw the input view
	 * @return A linear layout containing all the elements for the chat input
	 *         view
	 */
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

		ImageButton btnSend = SharedViewConstructor.ConstructImageButton(main,
				R.drawable.send_now, size, 0.1f);
		// Set click listener for button
		btnSend.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				SendMessage(inputChat.getText().toString());
				inputChat.setText("");
			}
		});
		view.setFocusableInTouchMode(true);
		view.addView(inputChat);
		view.addView(btnSend);

		return view;
	}

	/**
	 * Takes the information and send a new message to the cloud, in this case
	 * Firebase
	 * 
	 * @param message
	 *            String containing the message to be send to the client
	 */
	public static void SendMessage(String message) {

		Calendar c = Calendar.getInstance();
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);
		int date = c.get(Calendar.DATE);

		String month = DecideMonth(c.get(Calendar.MONTH));

		Msg msg = new Msg(message, role, "" + hour + ":" + minute, month + " "
				+ date, "false");

		conversationRef.push().setValue(msg);
	}

	/**
	 * Decides the proper month name in Spanish according to the given Integer,
	 * i.e. 1 = "Enero"
	 * 
	 * @param month
	 *            number of the current month
	 * @return The proper name of the month
	 */
	private static String DecideMonth(int month) {
		switch (month) {
		case 0:
			return "Enero";

		case 1:
			return "Febrero";

		case 2:
			return "Marzo";

		case 3:
			return "Abril";

		case 4:
			return "Mayo";

		case 5:
			return "Junio";

		case 6:
			return "Julio";

		case 7:
			return "Agosto";

		case 8:
			return "Septiembre";

		case 9:
			return "Octubre";

		case 10:
			return "Noviembre";

		case 11:
			return "Diciembre";

		default:
			return "Invalid month";

		}
	}
}
