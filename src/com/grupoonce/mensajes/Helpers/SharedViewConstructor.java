/**
 * 
 */
package com.grupoonce.mensajes.Helpers;

import com.grupoonce.mensajes.R;

import android.net.Uri;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout.LayoutParams;
import android.widget.LinearLayout;

/**
 * @author erickdario
 *
 */
public class SharedViewConstructor {

	public static LinearLayout ContructHeader(final Activity main) {

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

		ImageButton btnG11 = CreateMediaButton(R.drawable.logo_white, main,
				(int) (size.y * 0.12), (int) (size.y * 0.21),
				"http://grupoonce.mx");
		btnG11.setBackgroundColor(Color.rgb(0, 0, 0));

		view.addView(btnG11);
		view.addView(iconsView);

		return view;
	}

	public static Point GetScreenSize(Activity main) {
		WindowManager wm = (WindowManager) main
				.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		return size;
	}

	public static LinearLayout ConstructBackground(Activity main, Point size,
			int width) {
		LinearLayout view = new LinearLayout(main);
		view.setOrientation(LinearLayout.VERTICAL);
		view.setLayoutParams(new LinearLayout.LayoutParams(width,
				(int) (size.y * 0.89)));
		view.setGravity(Gravity.CENTER_HORIZONTAL);
		view.setBackgroundColor(main.getResources().getColor(R.color.gray_g11));

		return view;
	}

	@SuppressLint("NewApi")
	public static Button ConstructOrangeButton(Activity main, Point size,
			int stringId, int marginX, int marginY, int width,
			int drawableText, int drawableButton) {
		LinearLayout.LayoutParams btnLayoutParams = new LayoutParams(width,
				LayoutParams.WRAP_CONTENT);
		btnLayoutParams.setMargins(marginX, marginY, 0, 0);

		final Button sessionBtn = new Button(main);
		sessionBtn.setText(main.getResources().getString(stringId));
		sessionBtn.setLayoutParams(btnLayoutParams);
		sessionBtn.setTextColor(main.getResources().getColor(drawableText));
		sessionBtn.setBackground(main.getResources().getDrawable(
				drawableButton, null));

		return sessionBtn;
	}

	@SuppressLint("NewApi")
	private static ImageButton CreateMediaButton(int resource,
			final Activity main, int width, int height, final String url) {
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
				openG11Url(main, url);
			}
		});
		return btnSocialMedia;
	}

	private static void openG11Url(Activity main, String url) {
		Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
		main.startActivity(browserIntent);
	}

}
