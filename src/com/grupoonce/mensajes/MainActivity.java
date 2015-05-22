package com.grupoonce.mensajes;

import com.grupoonce.chat.FirebaseManager;
import com.grupoonce.helpers.MMAdviserViewConstructor;
import com.grupoonce.helpers.SessionViewConstructor;
import com.grupoonce.helpers.SharedViewConstructor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

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
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == 0xe110) {
			if (FirebaseManager.childEventListenerConversations != null) {
				MMAdviserViewConstructor.conversationsRef
						.removeEventListener(FirebaseManager.childEventListenerConversations);
			}
		}
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
