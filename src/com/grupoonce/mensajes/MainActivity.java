package com.grupoonce.mensajes;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.grupoonce.mensajes.Helpers.SessionViewConstructor;
import com.grupoonce.mensajes.Helpers.SharedViewConstructor;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Firebase.setAndroidContext(this);
		//Firebase myFirebaseRef = new Firebase("https://glaring-heat-1751.firebaseio.com");
		
		Firebase ref = new Firebase("https://glaring-heat-1751.firebaseio.com");
		ref.addAuthStateListener(new Firebase.AuthStateListener() {
		    @Override
		    public void onAuthStateChanged(AuthData authData) {
		        if (authData != null) {
		            // user is logged in
		        } else {
		            // user is not logged in
		        }
		    }
		});

		LinearLayout linearLayout = new LinearLayout(this);
		linearLayout.setOrientation(LinearLayout.VERTICAL);

		LinearLayout.LayoutParams rlp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT);

		LinearLayout header = SharedViewConstructor.ConstructHeader(this);
		LinearLayout body = SessionViewConstructor.ContructBody(this);

		linearLayout.addView(header);
		linearLayout.addView(body);

		setContentView(linearLayout, rlp);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
