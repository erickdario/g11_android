package com.grupoonce.chat;

import java.util.Map;

import android.app.Activity;
import android.content.Intent;
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

public class FirebaseManager {

	public static ChildEventListener childEventListener;
	public static ValueEventListener valueEventListener;
	public static Activity main;

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
						Toast.LENGTH_LONG).show();
				break;
			case FirebaseError.INVALID_PASSWORD:
				Toast.makeText(main, "La contraseña es incorrecta",
						Toast.LENGTH_LONG).show();
				break;
			default:
				Toast.makeText(main,
						"Algo salió mal, por favor verifique sus datos",
						Toast.LENGTH_LONG).show();
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
					main.startActivity(intent);
				} else {
					Intent intent = new Intent(main, MainMenuActivity.class);
					intent.putExtra(
							"conversationUrl",
							"https://glaring-heat-1751.firebaseio.com/messages/"
									+ user.get("city") + "_"
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

	public static void FindConversation() {

		childEventListener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot msgSnapshot,
					String previousChild) {
				@SuppressWarnings("unchecked")
				Map<String, Object> msg = (Map<String, Object>) msgSnapshot
						.getValue();
				ChatViewConstructor.listMessages.add(new Msg(msg.get("text")
						.toString(), msg.get("sender").toString(), ""
						+ msg.get("time"), msg.get("date").toString()));
				ChatViewConstructor.adapter.notifyDataSetChanged();
				ChatViewConstructor.listMsg
						.setSelection(ChatViewConstructor.listMsg.getCount() - 1);
				System.out.println(msg.get("text").toString());
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
				.addChildEventListener(childEventListener);
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
								Toast.LENGTH_LONG).show();
					}

					@Override
					public void onError(FirebaseError firebaseError) {
						switch (firebaseError.getCode()) {
						case FirebaseError.EMAIL_TAKEN:
							Toast.makeText(main,
									"Correo registrado con otra cuenta",
									Toast.LENGTH_LONG).show();
							break;
						case FirebaseError.INVALID_EMAIL:
							Toast.makeText(main, "Correo inválido",
									Toast.LENGTH_LONG).show();
							break;
						case FirebaseError.NETWORK_ERROR:
							Toast.makeText(main, "Error de conectividad",
									Toast.LENGTH_LONG).show();
							break;
						default:
							Toast.makeText(
									main,
									"Por favor verifique sus datos",
									Toast.LENGTH_LONG).show();
							break;
						}
					}
				});
	}

}
