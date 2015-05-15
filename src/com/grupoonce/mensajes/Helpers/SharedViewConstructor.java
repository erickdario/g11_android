/**
 * 
 */
package com.grupoonce.mensajes.helpers;

import com.grupoonce.chat.FirebaseManager;
import com.grupoonce.mensajes.R;

import android.net.Uri;
import android.os.Build;
import android.text.InputType;
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
import android.graphics.Typeface;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout.LayoutParams;
import android.widget.LinearLayout;

/**
 * @author erickdario
 *
 */
public class SharedViewConstructor {

	public static LinearLayout ConstructHeader(final Activity main) {

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

	public static LinearLayout ConstructHeaderG11(final Activity main) {
		// Create LinearLayout
		LinearLayout view = new LinearLayout(main);
		view.setOrientation(LinearLayout.HORIZONTAL);
		view.setBackgroundResource(R.color.orange_g11);
		Point size = GetScreenSize(main);

		LinearLayout menuView = new LinearLayout(main);
		menuView.setOrientation(LinearLayout.VERTICAL);
		menuView.setGravity(Gravity.CENTER_HORIZONTAL);
		menuView.setLayoutParams(new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT, (int) (size.y * 0.21)));

		ImageButton btnG11 = CreateMediaButton(R.drawable.logo_white, main,
				(int) (size.y * 0.12), (int) (size.y * 0.21),
				"http://grupoonce.mx");
		btnG11.setBackgroundColor(Color.rgb(0, 0, 0));

		view.addView(btnG11);
		view.addView(menuView);

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
			int width, int height) {
		LinearLayout view = new LinearLayout(main);
		view.setOrientation(LinearLayout.VERTICAL);
		view.setLayoutParams(new LinearLayout.LayoutParams(width, height));
		view.setGravity(Gravity.CENTER_HORIZONTAL);
		view.setBackgroundColor(main.getResources().getColor(R.color.gray_g11));

		return view;
	}

	@SuppressLint("NewApi")
	@SuppressWarnings("deprecation")
	public static Button ConstructButton(Activity main, Point size,
			int stringId, int marginX, int marginY, int width,
			int drawableText, int drawableButton) {
		LinearLayout.LayoutParams btnLayoutParams = new LayoutParams(width,
				LayoutParams.WRAP_CONTENT);
		btnLayoutParams.setMargins(marginX, marginY, 0, 0);

		final Button sessionBtn = new Button(main);
		sessionBtn.setText(main.getResources().getString(stringId));
		sessionBtn.setLayoutParams(btnLayoutParams);
		sessionBtn.setTextColor(main.getResources().getColor(drawableText));
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			sessionBtn.setBackground(main.getResources().getDrawable(
					drawableButton, null));
		} else {
			sessionBtn.setBackground(main.getResources().getDrawable(
					drawableButton));
		}

		return sessionBtn;
	}

	public static TextView ConstructTextView(Activity main,
			LayoutParams layout, int textSize, String text, Boolean bold, int color) {
		TextView textView = new TextView(main);
		textView.setLayoutParams(layout);
		textView.setText(text);
		textView.setTextSize(textSize);
		textView.setGravity(Gravity.START);
		if (bold) {
			textView.setTypeface(null, Typeface.BOLD);
		}
		textView.setTextColor(color);

		return textView;
	}

	public static ScrollView ConstructScrollView(Activity main, int width,
			Point size) {
		ScrollView scrollView = new ScrollView(main);
		scrollView.setBackgroundColor(main.getResources().getColor(
				android.R.color.transparent));
		scrollView.setLayoutParams(new LayoutParams(width,
				(int) (size.y * 0.80)));

		return scrollView;
	}

	@SuppressWarnings("deprecation")
	@SuppressLint("NewApi")
	public static ImageButton ConstructImageButton(Activity main, int resource,
			Point size, float side) {
		ImageButton imageBtn = new ImageButton(main);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			imageBtn.setImageDrawable(main.getResources().getDrawable(resource,
					null));
		} else {
			imageBtn.setImageDrawable(main.getResources().getDrawable(resource));
		}

		imageBtn.setAdjustViewBounds(true);
		imageBtn.setBackgroundColor(Color.TRANSPARENT);
		imageBtn.setScaleType(ScaleType.CENTER_CROP);
		imageBtn.setLayoutParams(new LinearLayout.LayoutParams(
				(int) (size.y * side), (int) (size.y * side)));

		return imageBtn;
	}

	public static Button ConstructSignOut(final Activity main, Point size,
			int width, int marginY) {
		Button signOut = ConstructButton(main, size, R.string.sign_out, 0,
				marginY, width, R.drawable.session_btn_text,
				R.drawable.close_session_button);

		// Set click listener for button
		signOut.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				main.finish();
				FirebaseManager.ref.unauth();
			}
		});
		return signOut;
	}
	
	@SuppressWarnings("deprecation")
	@SuppressLint("NewApi")
	public static EditText ContructG11EditText(Activity main,
			int placeholderId, int inputType) {
		LinearLayout.LayoutParams layoutParams = new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		layoutParams.setMargins(0, 40, 0, 0);

		EditText editTextSession = new EditText(main);
		editTextSession.setHint(main.getResources().getString(placeholderId));
		editTextSession.setLayoutParams(layoutParams);
		editTextSession.setInputType(InputType.TYPE_CLASS_TEXT | inputType);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			editTextSession.setBackground(main.getResources().getDrawable(
					R.drawable.edittext, null));
		} else {
			editTextSession.setBackground(main.getResources().getDrawable(
					R.drawable.edittext));
		}

		return editTextSession;
	}

	@SuppressWarnings("deprecation")
	@SuppressLint("NewApi")
	private static ImageButton CreateMediaButton(int resource,
			final Activity main, int width, int height, final String url) {
		final ImageButton btnSocialMedia = new ImageButton(main);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			btnSocialMedia.setImageDrawable(main.getResources().getDrawable(
					resource, null));
		} else {
			btnSocialMedia.setImageDrawable(main.getResources().getDrawable(
					resource));
		}

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
