package com.grupoonce.chat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.grupoonce.mensajes.MainMenuActivity;
import com.grupoonce.mensajes.MainMenuAdvisorActivity;
import com.grupoonce.mensajes.Helpers.ChatViewConstructor;
import com.grupoonce.mensajes.Helpers.MMAdvisorViewConstructor;

public class FirebaseManager {

	public static ChildEventListener childEventListenerConversation;
	public static ChildEventListener childEventListenerConversations;
	public static ValueEventListener valueEventListener;
	public static Activity main;
	public static String role;

	public static Firebase ref = new Firebase(
			"https://glaring-heat-1751.firebaseio.com");
	public static Firebase.AuthResultHandler authResultHandler = new Firebase.AuthResultHandler() {
		@Override
		public void onAuthenticated(final AuthData authData) {
			UserWasAuthenticated(authData);
		}

		@Override
		public void onAuthenticationError(FirebaseError firebaseError) {

			switch (firebaseError.getCode()) {
			case FirebaseError.USER_DOES_NOT_EXIST:
				Toast.makeText(main,
						"El usuario no existe en la base de datos",
						Toast.LENGTH_SHORT).show();
				break;
			case FirebaseError.INVALID_PASSWORD:
				Toast.makeText(main, "La contraseña es incorrecta",
						Toast.LENGTH_SHORT).show();
				break;
			default:
				Toast.makeText(main,
						"Algo salió mal, por favor verifique sus datos",
						Toast.LENGTH_SHORT).show();
				break;
			}
		}
	};

	public static void UserWasAuthenticated(final AuthData authData) {
		// Authenticated successfully with payload authData
		Firebase userRef = ref.child("users").child(authData.getUid());
		valueEventListener = new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot snapshot) {
				@SuppressWarnings("unchecked")
				Map<String, Object> user = (Map<String, Object>) snapshot
						.getValue();
				if (user.get("companysName").equals("grupoonce")) {
					Intent intent = new Intent(main,
							MainMenuAdvisorActivity.class);
					intent.putExtra("sessionId", authData.getUid());
					intent.putExtra("city", user.get("city").toString());
					intent.putExtra("conversationsUrl",
							"https://glaring-heat-1751.firebaseio.com/messages/"
									+ user.get("city"));
					main.startActivity(intent);
				} else {
					Intent intent = new Intent(main, MainMenuActivity.class);
					intent.putExtra(
							"conversationUrl",
							"https://glaring-heat-1751.firebaseio.com/messages/"
									+ user.get("city") + "/"
									+ user.get("companysName"));
					main.startActivity(intent);
				}
			}

			@Override
			public void onCancelled(FirebaseError firebaseError) {
				System.out.println("The read failed: "
						+ firebaseError.getMessage());
			}
		};
		userRef.addValueEventListener(valueEventListener);
	}

	public static void FindConversations() {
		Log.d("debug", "conversations");
		childEventListenerConversations = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot conversationSnapshot,
					String previousChild) {
				// TODO get last date of last message
				Boolean read = GetIfAnyNotRead(conversationSnapshot, role);
				String lastDate = GetLastDate(conversationSnapshot);
				MMAdvisorViewConstructor.listConversations
						.add(new Conversation(conversationSnapshot.getKey(),
								lastDate, read));
				MMAdvisorViewConstructor.newMessagesCounter.setText(""
						+ MMAdvisorViewConstructor.adapter.getCountUnread());
				MMAdvisorViewConstructor.adapter.notifyDataSetChanged();
				System.out.println(MMAdvisorViewConstructor.newMessagesCounter
						.getText().toString());
			}

			@Override
			public void onCancelled(FirebaseError arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onChildChanged(DataSnapshot conversationSnapshot,
					String previousChildKey) {
				Boolean read = GetIfAnyNotRead(conversationSnapshot, role);
				if (!read) {
					for (int index = 0; index < MMAdvisorViewConstructor.listConversations
							.size(); index++) {
						if (MMAdvisorViewConstructor.listConversations
								.get(index).getCompanysName()
								.equals(conversationSnapshot.getKey())) {
							String lastDate = GetLastDate(conversationSnapshot);
							MMAdvisorViewConstructor.listConversations.get(
									index).setRead(false);
							MMAdvisorViewConstructor.listConversations.get(
									index).setLastDateMsg(lastDate);
							MMAdvisorViewConstructor.adapter
									.notifyDataSetChanged();
						}
					}
				}
				MMAdvisorViewConstructor.newMessagesCounter.setText(""
						+ MMAdvisorViewConstructor.adapter.getCountUnread());
			}

			@Override
			public void onChildMoved(DataSnapshot arg0, String arg1) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onChildRemoved(DataSnapshot arg0) {
				// TODO Auto-generated method stub

			}
		};
		MMAdvisorViewConstructor.conversationsRef
				.addChildEventListener(childEventListenerConversations);
	}

	public static void FindConversation() {

		childEventListenerConversation = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot msgSnapshot,
					String previousChild) {
				@SuppressWarnings("unchecked")
				Map<String, Object> msg = (Map<String, Object>) msgSnapshot
						.getValue();
				Msg message = new Msg(msg.get("text").toString(), msg.get(
						"sender").toString(), msg.get("time").toString(), msg
						.get("date").toString(), msg.get("read").toString());
				if (!message.getSender().equals(role)) {
					message.setRead("true");
					ChatViewConstructor.conversationRef.child(
							msgSnapshot.getKey()).setValue(message);
				}
				ChatViewConstructor.listMessages.add(message);
				ChatViewConstructor.adapter.notifyDataSetChanged();
				ChatViewConstructor.listMsg
						.setSelection(ChatViewConstructor.listMsg.getCount() - 1);
			}

			@Override
			public void onCancelled(FirebaseError arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onChildChanged(DataSnapshot arg0, String arg1) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onChildMoved(DataSnapshot arg0, String arg1) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onChildRemoved(DataSnapshot arg0) {
				// TODO Auto-generated method stub

			}
		};

		ChatViewConstructor.conversationRef
				.addChildEventListener(childEventListenerConversation);
	}

	public static void CreateUser(final String userName, final String city,
			String password, final String companysName) {

		ref.createUser(userName, password,
				new Firebase.ValueResultHandler<Map<String, Object>>() {
					@Override
					public void onSuccess(Map<String, Object> result) {
						String accountId = (String) result.get("uid");
						User user = new User(userName, city, companysName);
						Firebase usersRef = ref.child("users");
						usersRef.child(accountId).setValue(user);
						Toast.makeText(main,
								"Cuenta " + userName + " creada con éxito",
								Toast.LENGTH_SHORT).show();
					}

					@Override
					public void onError(FirebaseError firebaseError) {
						switch (firebaseError.getCode()) {
						case FirebaseError.EMAIL_TAKEN:
							Toast.makeText(main,
									"Correo registrado con otra cuenta",
									Toast.LENGTH_SHORT).show();
							break;
						case FirebaseError.INVALID_EMAIL:
							Toast.makeText(main, "Correo inválido",
									Toast.LENGTH_SHORT).show();
							break;
						case FirebaseError.NETWORK_ERROR:
							Toast.makeText(main, "Error de conectividad",
									Toast.LENGTH_SHORT).show();
							break;
						default:
							Toast.makeText(main,
									"Por favor verifique sus datos",
									Toast.LENGTH_SHORT).show();
							break;
						}
					}
				});
	}

	private static Boolean GetIfAnyNotRead(DataSnapshot conversationSnapshot,
			String role) {
		for (int index = 1; index <= conversationSnapshot.getChildrenCount(); index++) {
			Boolean read;
			if (!conversationSnapshot.child("/" + index + "/sender").getValue()
					.toString().equals(role)) {
				read = Boolean.valueOf(conversationSnapshot
						.child("/" + index + "/read").getValue().toString());
				if (!read) {
					return read;
				}
			}
		}
		return true;
	}

	private static String GetLastDate(DataSnapshot conversationSnapshot) {
		String maxDateStr = conversationSnapshot.child("/1/date").getValue()
				.toString();
		SimpleDateFormat format = new SimpleDateFormat("MMMM d", Locale.ENGLISH);
		Date maxDate = null;
		try {
			maxDate = format.parse(maxDateStr);
		} catch (ParseException e) {
			e.printStackTrace();
			System.out.println("The date wasn't in the right format");
		}
		System.out.println(maxDate);
		for (int index = 2; index <= conversationSnapshot.getChildrenCount(); index++) {
			String dateStr = conversationSnapshot.child("/" + index + "/date")
					.getValue().toString();
			Date date = null;
			try {
				date = format.parse(dateStr);
			} catch (ParseException e) {
				e.printStackTrace();
				System.out.println("The date wasn't in the right format");
			}

			if (date.after(maxDate)) {
				maxDate = date;
			}
		}
		return format.format(maxDate);
	}
}
