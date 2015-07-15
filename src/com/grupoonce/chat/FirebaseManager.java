package com.grupoonce.chat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
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
import com.grupoonce.helpers.AdviserConfigurationConstructor;
import com.grupoonce.helpers.ChatViewConstructor;
import com.grupoonce.helpers.MMAdviserViewConstructor;
import com.grupoonce.helpers.SessionViewConstructor;
import com.grupoonce.mensajes.AdminMenuActivity;
import com.grupoonce.mensajes.MainMenuActivity;
import com.grupoonce.mensajes.MainMenuAdvisorActivity;
import com.grupoonce.mensajes.R;
import com.parse.ParseInstallation;

/**
 * Manages the interaction between the application and Firebase
 * 
 * @author erickdario
 *
 */
public class FirebaseManager {

	public static ChildEventListener childEventListenerConversation;
	public static ChildEventListener childEventListenerConversations;
	public static ValueEventListener valueEventListener;
	public static Activity main;
	public static String role;

	// TODO change for development URL
	public static Firebase ref = new Firebase(
			"https://glaring-heat-1751.firebaseio.com");
	public static Firebase.AuthResultHandler authResultHandler = new Firebase.AuthResultHandler() {
		@Override
		public void onAuthenticated(AuthData authData) {
			UserWasAuthenticated(authData);
			SessionViewConstructor.loginBtn.setEnabled(true);
		}

		@Override
		public void onAuthenticationError(FirebaseError firebaseError) {
			DisplayError(firebaseError,
					"Algo salió mal, por favor verifique sus datos");
			SessionViewConstructor.loginBtn.setEnabled(true);
		}
	};

	/**
	 * Finds one of the grupo once workers according the city
	 * 
	 * @param city
	 *            Name of the city of the employee we are going to retrieved
	 */
	public static void FindUser(final String city) {
		Firebase usersRef = ref.child("users");
		usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot snapshot) {
				Iterator<DataSnapshot> iterator = snapshot.getChildren()
						.iterator();
				while (iterator.hasNext()) {
					DataSnapshot userSnapshot = iterator.next();

					if (userSnapshot.child("city").getValue().toString()
							.equals(city)
							&& userSnapshot.child("companysName").getValue()
									.toString().equals("grupoonce")) {
						AdviserConfigurationConstructor.userKey = userSnapshot
								.getKey();
						AdviserConfigurationConstructor.userName = userSnapshot
								.child("userName").getValue().toString();
						AdviserConfigurationConstructor.oldPassword = userSnapshot
								.child("password").getValue().toString();

						AdviserConfigurationConstructor.user.setText(main
								.getResources().getString(R.string.user)
								+ "\n"
								+ AdviserConfigurationConstructor.userName);

						AdviserConfigurationConstructor.oldPasswordView
								.setText(main.getResources().getString(
										R.string.old_password)
										+ "\n"
										+ AdviserConfigurationConstructor.oldPassword);
					}
				}
			}

			@Override
			public void onCancelled(FirebaseError firebaseError) {
				DisplayError(firebaseError, "Lo sentimos, intente mas tarde");
			}
		});
	}

	/**
	 * Changes the password of the account on Firebase based on the given
	 * parameters
	 * 
	 * @param email
	 *            Email of the account to change the password
	 * @param oldPassword
	 *            Old password of the account
	 * @param newPassword
	 *            New password of the account
	 */
	public static void ChangePassword(final String email,
			final String oldPassword, final String newPassword) {
		ref.changePassword(email, oldPassword, newPassword,
				new Firebase.ResultHandler() {
					@Override
					public void onSuccess() {
						Toast.makeText(
								main,
								"La contraseña del usuario " + email
										+ " fue cambiada con éxito",
								Toast.LENGTH_SHORT).show();
						AdviserConfigurationConstructor.oldPassword = newPassword;
						AdviserConfigurationConstructor.oldPasswordView
								.setText(main.getResources().getString(
										R.string.old_password)
										+ "\n"
										+ AdviserConfigurationConstructor.oldPassword);
						ref.child("users")
								.child(AdviserConfigurationConstructor.userKey)
								.child("password").setValue(newPassword);
					}

					@Override
					public void onError(FirebaseError firebaseError) {
						DisplayError(firebaseError,
								"Lo sentimos, intente mas tarde");
					}
				});
	}

	/**
	 * Handles the sign in if the user was successfully subscribed
	 * 
	 * @param authData
	 *            Callback information from firebase
	 */
	public static void UserWasAuthenticated(final AuthData authData) {
		// Authenticated successfully with payload authData
		// Store user's city, email and company
		final ParseInstallation installation = ParseInstallation
				.getCurrentInstallation();
		installation.put("session", "open");
		Firebase userRef = ref.child("users").child(authData.getUid());
		valueEventListener = new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot snapshot) {
				@SuppressWarnings("unchecked")
				Map<String, Object> user = (Map<String, Object>) snapshot
						.getValue();
				if (user.get("companysName").equals("grupoonce")) {
					if (user.get("city").toString().equals("admin")) {
						Intent intent = new Intent(main,
								AdminMenuActivity.class);
						main.startActivityForResult(intent, 0xe110);
					} else {
						Intent intent = new Intent(main,
								MainMenuAdvisorActivity.class);
						intent.putExtra("sessionId", authData.getUid());
						intent.putExtra("city", user.get("city").toString());
						intent.putExtra("conversationsUrl", ref.toString()
								+ "/messages/" + user.get("city"));
						main.startActivityForResult(intent, 0xe110);
					}
					installation.put("companysName", "grupoonce");
				} else {
					Intent intent = new Intent(main, MainMenuActivity.class);
					String userName = user.get("userName").toString();
					String companysName = user.get("companysName").toString();
					userName = CleanString(userName);
					companysName = CleanString(companysName);

					installation.put("userName", userName);
					installation.put("companysName", companysName);

					intent.putExtra("conversationUrl", ref.toString()
							+ "/messages/" + user.get("city") + "/" + userName
							+ "%" + companysName);
					main.startActivityForResult(intent, 0xe110);
				}
				installation.put("city", user.get("city").toString());
			}

			@Override
			public void onCancelled(FirebaseError firebaseError) {
				Log.d("Firebase ", firebaseError.getMessage());
			}
		};
		installation.saveInBackground();

		userRef.addValueEventListener(valueEventListener);
	}

	/**
	 * Finds the list of conversations for the given city
	 */
	public static void FindConversations() {
		childEventListenerConversations = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot conversationSnapshot,
					String previousChild) {
				Boolean read = GetIfAnyNotRead(conversationSnapshot, role);
				String lastDate = GetLastDate(conversationSnapshot);
				String conversationNameOnFirebase = conversationSnapshot
						.getKey();
				String companysNameConversation = conversationNameOnFirebase
						.substring(conversationNameOnFirebase.indexOf("%") + 1,
								conversationNameOnFirebase.length());
				String userNameConversation = conversationNameOnFirebase
						.substring(0, conversationNameOnFirebase.indexOf("%"));
				MMAdviserViewConstructor.listConversations
						.add(new Conversation(companysNameConversation,
								userNameConversation, lastDate, read));
				MMAdviserViewConstructor.newMessagesCounter.setText(""
						+ MMAdviserViewConstructor.adapter.getCountUnread());
				MMAdviserViewConstructor.adapter.notifyDataSetChanged();
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
					for (int index = 0; index < MMAdviserViewConstructor.listConversations
							.size(); index++) {
						String conversationNameOnFirebase = conversationSnapshot
								.getKey();
						conversationNameOnFirebase = conversationNameOnFirebase
								.substring(
										conversationNameOnFirebase.indexOf("%") + 1,
										conversationNameOnFirebase.length());
						if (MMAdviserViewConstructor.listConversations
								.get(index).getCompanysName()
								.equals(conversationNameOnFirebase)) {
							MMAdviserViewConstructor.listConversations.get(
									index).setRead(false);
							MMAdviserViewConstructor.listConversations.get(
									index).setLastDateMsg(
									GetLastDate(conversationSnapshot));
						}
					}
				}
				MMAdviserViewConstructor.newMessagesCounter.setText(""
						+ MMAdviserViewConstructor.adapter.getCountUnread());
				MMAdviserViewConstructor.adapter.notifyDataSetChanged();
			}

			@Override
			public void onChildMoved(DataSnapshot arg0, String arg1) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onChildRemoved(DataSnapshot conversationSnapshot) {
				for (int index = 0; index < MMAdviserViewConstructor.listConversations
						.size(); index++) {
					String conversationNameOnFirebase = conversationSnapshot
							.getKey();
					conversationNameOnFirebase = conversationNameOnFirebase
							.substring(
									conversationNameOnFirebase.indexOf("%") + 1,
									conversationNameOnFirebase.length());
					if (MMAdviserViewConstructor.listConversations.get(index)
							.getCompanysName()
							.equals(conversationNameOnFirebase)) {
						MMAdviserViewConstructor.listConversations
								.remove(index);
					}
				}
				MMAdviserViewConstructor.newMessagesCounter.setText(""
						+ MMAdviserViewConstructor.adapter.getCountUnread());
				MMAdviserViewConstructor.adapter.notifyDataSetChanged();

			}
		};
		MMAdviserViewConstructor.conversationsRef
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
			}

			@Override
			public void onChildRemoved(DataSnapshot arg0) {
			}
		};

		ChatViewConstructor.conversationRef
				.addChildEventListener(childEventListenerConversation);
	}

	/**
	 * Closes a conversation
	 * 
	 * @param state
	 *            State or city the conversation belongs to
	 * @param conversation
	 *            Conversation's name
	 * @param comment
	 *            Comment from the adviser after closing the conversation
	 * @param area
	 *            Specific area the conversation was about, this helps to
	 *            generate the charts
	 */
	public static void CloseConversation(final String state,
			String conversation, String comment, final String area) {

		Map<String, Object> conversationMap = new HashMap<String, Object>();
		conversationMap.put("area", area);
		conversationMap.put("comment", comment);
		ref.child("/closed_conversations/" + state + "/" + conversation).push()
				.setValue(conversationMap);
		ref.child("/charts/").child(state + "/")
				.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot snapshot) {
						int amount;
						try {
							amount = Integer.parseInt(snapshot
									.child("/" + area).getValue().toString());
						} catch (NullPointerException nullException) {
							amount = 0;
						}
						ref.child("/charts/").child(state + "/" + area + "/")
								.setValue(amount + 1);
					}

					@Override
					public void onCancelled(FirebaseError firebaseError) {
					}
				});
		ref.child("/messages/" + state + "/" + conversation).removeValue();
	}

	/**
	 * Creates a user in Firebase
	 * 
	 * @param userName
	 * @param city
	 * @param password
	 * @param companysName
	 */
	public static void CreateUser(final String userName, final String city,
			final String password, final String companysName) {
		ref.createUser(userName, password,
				new Firebase.ValueResultHandler<Map<String, Object>>() {
					@Override
					public void onSuccess(Map<String, Object> result) {
						String accountId = (String) result.get("uid");
						User user = new User(userName, city, companysName,
								password);
						Firebase usersRef = ref.child("users");
						usersRef.child(accountId).setValue(user);
						Toast.makeText(main,
								"Cuenta " + userName + " creada con éxito",
								Toast.LENGTH_SHORT).show();
					}

					@Override
					public void onError(FirebaseError firebaseError) {
						DisplayError(firebaseError,
								"Por favor verifique sus datos");
					}
				});
	}

	/**
	 * Manages the type of error message the app will display to the user
	 * 
	 * @param firebaseError
	 *            Variable containing the details about the error
	 * @param defaultMessage
	 *            If the error is not managed by the app, it will display this
	 *            default message
	 */
	private static void DisplayError(FirebaseError firebaseError,
			String defaultMessage) {
		switch (firebaseError.getCode()) {
		case FirebaseError.USER_DOES_NOT_EXIST:
			Toast.makeText(main, "El usuario no existe en la base de datos",
					Toast.LENGTH_SHORT).show();
			break;
		case FirebaseError.INVALID_PASSWORD:
			Toast.makeText(main, "La contraseña es incorrecta",
					Toast.LENGTH_SHORT).show();
			break;
		case FirebaseError.NETWORK_ERROR:
			Toast.makeText(main, "Error de conexión", Toast.LENGTH_SHORT)
					.show();
			break;
		case FirebaseError.DISCONNECTED:
			Toast.makeText(main, "Hubo un problema de conectividad",
					Toast.LENGTH_SHORT).show();
			break;
		case FirebaseError.OPERATION_FAILED:
			Toast.makeText(main,
					"Ocurrió un error en el servidor, intente más tarde",
					Toast.LENGTH_SHORT).show();
			break;
		case FirebaseError.EMAIL_TAKEN:
			Toast.makeText(main, "Correo registrado con otra cuenta",
					Toast.LENGTH_SHORT).show();
			break;
		case FirebaseError.INVALID_EMAIL:
			Toast.makeText(main, "Correo inválido", Toast.LENGTH_SHORT).show();
			break;
		default:
			Toast.makeText(main, defaultMessage, Toast.LENGTH_SHORT).show();
			break;
		}
	}

	/**
	 * Looks if a message has not been read by the receiver
	 * 
	 * @param conversationSnapshot
	 *            Conversation to look for unread messages from
	 * @param role
	 *            Role to determine which kind of message we're interested on
	 * @return if any not read, true, otherwise false
	 */
	private static Boolean GetIfAnyNotRead(DataSnapshot conversationSnapshot,
			String role) {
		Iterator<DataSnapshot> iterator = conversationSnapshot.getChildren()
				.iterator();
		while (iterator.hasNext()) {
			Boolean read;
			DataSnapshot messageSnapshot = iterator.next();
			if (!messageSnapshot.child("/sender").getValue().toString()
					.equals(role)) {
				read = Boolean.valueOf(messageSnapshot.child("/read")
						.getValue().toString());

				if (!read) {
					return read;
				}
			}
		}
		return true;
	}

	/**
	 * Gets the date of the last message on the conversation
	 * 
	 * @param conversationSnapshot
	 *            Conversation to the last message from
	 * @return The date of the last message on this conversation
	 */
	private static String GetLastDate(DataSnapshot conversationSnapshot) {
		Iterator<DataSnapshot> iterator = conversationSnapshot.getChildren()
				.iterator();
		String maxDateStr = "";
		while (iterator.hasNext()) {
			DataSnapshot messageSnapshot = iterator.next();
			maxDateStr = messageSnapshot.child("/date").getValue().toString();
		}
		SimpleDateFormat format = new SimpleDateFormat("MMMM d", new Locale(
				"es", "ES"));
		Date maxDate = null;
		try {
			maxDate = format.parse(maxDateStr);
		} catch (ParseException e) {
			e.printStackTrace();
			Log.d("Firebase", "The date wasn't in the right format");
		}
		return format.format(maxDate);
	}

	/**
	 * Cleans a string to match the rules Firebase has to manage strings
	 * 
	 * @param stringToClean
	 * @return String without the special characters Firebase cannot use
	 */
	private static String CleanString(String stringToClean) {
		stringToClean = stringToClean.replace(".", "");
		stringToClean = stringToClean.replace("#", "");
		stringToClean = stringToClean.replace("$", "");
		stringToClean = stringToClean.replace("[", "");
		stringToClean = stringToClean.replace("]", "");
		return stringToClean;
	}
}
