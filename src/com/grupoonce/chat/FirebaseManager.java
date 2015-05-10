package com.grupoonce.chat;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.grupoonce.mensajes.MainMenuActivity;

public class FirebaseManager {
	
	public static Firebase ref = new Firebase("https://glaring-heat-1751.firebaseio.com");
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
	    	Toast.makeText(main, "Incorrect user or password, please verify",
	    			   Toast.LENGTH_LONG).show();	    }
	};
	public static Activity main;

}
