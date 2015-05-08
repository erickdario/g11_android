/**
 * 
 */
package com.grupoonce.mensajes.Helpers;

import com.grupoonce.mensajes.MainActivity;
import com.grupoonce.mensajes.R;

import android.net.Uri;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.widget.ImageButton;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * @author erickdario
 *
 */
public class ViewConstructor {

	public static LinearLayout ContructHeader(final MainActivity main) {
		// Create LinearLayout
		LinearLayout view = new LinearLayout(main);
		view.setOrientation(LinearLayout.HORIZONTAL);

		Point size = GetScreenSize(main);

		LinearLayout iconsView = new LinearLayout(main);
		iconsView.setOrientation(LinearLayout.VERTICAL);
		iconsView.setBackgroundResource(R.drawable.cover);
		iconsView.setLayoutParams(new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT, (int) (size.y * 0.21)));

		ImageButton btnFacebook = CreateMediaButton(R.drawable.facebook, main,
				(int) (size.y * 0.07), (int) (size.y * 0.07),
				"https://www.facebook.com/grupoONCE11");
		ImageButton btnTwitter = CreateMediaButton(R.drawable.twitter, main,
				(int) (size.y * 0.07), (int) (size.y * 0.07),
				"https://twitter.com/grupoONCE11");
		ImageButton btnYoutube = CreateMediaButton(R.drawable.youtube, main,
				(int) (size.y * 0.07), (int) (size.y * 0.07),
				"http://www.youtube.com/user/grupo11ONCE");

		// Add button to LinearLayout
		iconsView.addView(btnFacebook);
		iconsView.addView(btnTwitter);
		iconsView.addView(btnYoutube);

		ImageButton btnG11 = CreateMediaButton(R.drawable.youtube, main,
				(int) (size.y * 0.12), (int) (size.y * 0.21),
				"http://grupoonce.mx");

		view.addView(btnG11);
		view.addView(iconsView);

		return view;
	}

	public static Point GetScreenSize(MainActivity main) {
		WindowManager wm = (WindowManager) main
				.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		return size;
	}

	@SuppressLint("NewApi")
	private static ImageButton CreateMediaButton(int resource,
			final MainActivity main, int width, int height, final String url) {
		final ImageButton btnSocialMedia = new ImageButton(main);
		btnSocialMedia.setImageDrawable(main.getResources().getDrawable(
				resource, null));
		// set the layoutParams on the button
		btnSocialMedia.setAdjustViewBounds(true);
		btnSocialMedia.setBackgroundColor(Color.argb(100, 195, 195, 195));
		btnSocialMedia.setScaleType(ScaleType.CENTER_CROP);
		btnSocialMedia.setLayoutParams(new LinearLayout.LayoutParams(width,
				height));
		// Set click listener for button
		btnSocialMedia.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				Log.i("TAG", "index :" + btnSocialMedia.getId());

				Toast.makeText(main.getApplicationContext(),
						"Clicked Button Index :" + btnSocialMedia.getId(),
						Toast.LENGTH_SHORT).show();
				openG11Website(main, url);
			}
		});
		return btnSocialMedia;
	}

	private static void openG11Website(MainActivity main, String url) {
		Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
		main.startActivity(browserIntent);
	}

}
