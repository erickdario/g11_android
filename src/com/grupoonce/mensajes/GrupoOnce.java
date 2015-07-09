package com.grupoonce.mensajes;

import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.SaveCallback;

import android.app.Application;
import android.util.Log;

/**
 * Class that initializes the services for the application
 * 
 * @author erickdario
 *
 */
public class GrupoOnce extends Application {

	/**
	 * Method overriding the on create to initialize the parse instance, save
	 * the installation and subscribe to the broadcast channel
	 */
	@Override
	public void onCreate() {
		Parse.initialize(this, "RwxxnpNW9zRrZ0Bwpx0NxLXIaJy6BHdlMLjLdeyQ",
				"MJ1FXYFTDKb6JqhfLP41kp5KeHjzFaE0WzKzCMTu");
		ParseInstallation.getCurrentInstallation().saveInBackground();
		ParsePush.subscribeInBackground("", new SaveCallback() {
			@Override
			public void done(com.parse.ParseException e) {
				if (e == null) {
					Log.d("com.parse.push",
							"successfully subscribed to the broadcast channel.");
				} else {
					Log.e("com.parse.push", "failed to subscribe for push", e);
				}
			}
		});
		Log.d("onCreate", "Parse init");
	}

}
