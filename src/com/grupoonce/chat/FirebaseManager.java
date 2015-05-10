package com.grupoonce.chat;

import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.grupoonce.mensajes.MainMenuActivity;

public class FirebaseManager {

	public static Firebase ref = new Firebase(
			"https://glaring-heat-1751.firebaseio.com");
	public static Firebase.AuthResultHandler authResultHandler = new Firebase.AuthResultHandler() {
		@Override
		public void onAuthenticated(AuthData authData) {
			// Authenticated successfully with payload authData
			Intent intent = new Intent(main, MainMenuActivity.class);
			intent.putExtra("user", "dario");
			main.startActivity(intent);
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
	public static Activity main;

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
								"Cuenta" + userName + "creada con éxito",
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
									"Algo salió mal, por favor verifique sus datos",
									Toast.LENGTH_LONG).show();
							break;
						}
					}
				});
	}

}
