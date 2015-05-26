/**
 * 
 */

package com.grupoonce.helpers;

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
 * Draws and manages all the interaction for some shared elements among the
 * activities
 * 
 * @author erickdario
 */
public class SharedViewConstructor {

	/**
	 * Draws all the visible elements for the header of the given activity this
	 * is the same header for all the views on the client side
	 * 
	 * @param main
	 *            Activity we are going to draw the elements to
	 * @return A linear layout containing all the elements for the header
	 */
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

	/**
	 * Draws all the visible elements for the header of the given activity based
	 * of the header for the administrator views
	 * 
	 * @param main
	 *            Activity we are going to draw the elements to
	 * @return A linear layout containing all the elements for the header
	 */
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

	/**
	 * Gets the size of the current screen size
	 * 
	 * @param main
	 *            Activity that is trying to get the current screen size
	 * @return A Point element containing the screen details
	 */
	public static Point GetScreenSize(Activity main) {
		WindowManager wm = (WindowManager) main
				.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		return size;
	}

	/**
	 * Draws the gray background which is used in all the views in the
	 * application
	 * 
	 * @param main
	 *            Activity trying to draw the background
	 * @param size
	 *            Point element containing the screen details
	 * @param width
	 *            for the background
	 * @param height
	 *            for the background
	 * @return A liner layout with the proper background and the specified
	 *         measures
	 */
	public static LinearLayout ConstructBackground(Activity main, Point size,
			int width, int height) {
		LinearLayout view = new LinearLayout(main);
		view.setOrientation(LinearLayout.VERTICAL);
		view.setLayoutParams(new LinearLayout.LayoutParams(width, height));
		view.setGravity(Gravity.CENTER_HORIZONTAL);
		view.setBackgroundColor(main.getResources().getColor(R.color.gray_g11));

		return view;
	}

	/**
	 * Constructs a button with the given information and returns it
	 * 
	 * @param main
	 *            Activity trying to draw the button
	 * @param size
	 *            Point element containing the screen details
	 * @param stringId
	 *            The resource identifier for the given String
	 * @param marginLeft
	 *            the left margin size
	 * @param marginTop
	 *            the top margin size
	 * @param marginRight
	 *            the right margin size
	 * @param marginBottom
	 *            the bottom margin size
	 * @param width
	 *            the width of the button
	 * @param drawableText
	 *            The resource identifier for the given drawable for the
	 *            appearance of the text in the button
	 * @param drawableButton
	 *            The resource identifier for the given drawable for the
	 *            appearance of the background of the button
	 * @return A Button with the all the proper attributes
	 */
	@SuppressLint("NewApi")
	@SuppressWarnings("deprecation")
	public static Button ConstructButton(Activity main, Point size,
			int stringId, int marginLeft, int marginTop, int marginRight,
			int marginBottom, int width, int drawableText, int drawableButton) {
		LinearLayout.LayoutParams btnLayoutParams = new LayoutParams(width,
				LayoutParams.WRAP_CONTENT);
		btnLayoutParams.setMargins(marginLeft, marginTop, marginRight,
				marginBottom);

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

	/**
	 * Constructs a text view with the given information and returns it
	 * 
	 * @param main
	 *            Activity trying to draw the text view
	 * @param layout
	 *            Layout parameters to be assigned to the text view
	 * @param textSize
	 *            font size of the text view
	 * @param text
	 *            Text to be displayed inside the text view
	 * @param bold
	 *            true if the test is not shown as bold, false is not
	 * @param color
	 *            for the text view
	 * @return A text view with the all the proper attributes
	 */
	public static TextView ConstructTextView(Activity main,
			LayoutParams layout, int textSize, String text, Boolean bold,
			int color) {
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

	/**
	 * Constructs a scroll view with the given information and returns it
	 * 
	 * @param main
	 *            Activity trying to draw the text view
	 * @param width
	 *            width of the scroll view
	 * @param size
	 *            A Point element containing the screen details
	 * @return A scroll view with the all the proper attributes
	 */
	public static ScrollView ConstructScrollView(Activity main, int width,
			Point size) {
		ScrollView scrollView = new ScrollView(main);
		scrollView.setBackgroundColor(main.getResources().getColor(
				android.R.color.transparent));
		scrollView.setLayoutParams(new LayoutParams(width,
				(int) (size.y * 0.80)));

		return scrollView;
	}

	/**
	 * Constructs a image button with the given information and returns it
	 * 
	 * @param main
	 *            Activity trying to draw the image button
	 * @param resource
	 * @param size
	 *            A Point element containing the screen details
	 * @param side
	 *            The button will be draw as a square so we only need to know
	 *            one side
	 * @return A image button with the all the proper attributes
	 */
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

	/**
	 * Constructs a button with the sign out drawable and attach the proper
	 * click listener
	 * 
	 * @param main
	 *            Activity trying to draw the sign out button
	 * @param size
	 *            A Point element containing the screen details
	 * @param width
	 *            the width of the button
	 * @param marginLeft
	 *            the left margin size
	 * @param marginTop
	 *            the top margin size
	 * @return A image button with the all the proper attributes
	 */
	public static Button ConstructSignOut(final Activity main, Point size,
			int width, int marginLeft, int marginTop) {
		Button signOut = ConstructButton(main, size, R.string.sign_out,
				marginLeft, marginTop, 0, 0, width,
				R.drawable.session_btn_text, R.drawable.close_session_button);

		// Set click listener for button
		signOut.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				main.finish();
				FirebaseManager.ref.unauth();
			}
		});
		return signOut;
	}

	/**
	 * Constructs an edit text with the G11 theme
	 * 
	 * @param main
	 *            Activity trying to draw the sign out button
	 * @param placeholderId
	 *            the resource identifier for the string to be taken as the
	 *            placeholder
	 * @param inputType
	 *            integer specifying which type of content the text view is
	 *            going to have
	 * @return A edit text with the all the proper attributes
	 */
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

	/**
	 * Constructs a button with one of G11 the social networks for the shared
	 * header
	 * 
	 * @param resource
	 *            the resource identifier containing the image to be drawn on
	 *            the button
	 * @param main
	 *            Activity trying to draw the media button
	 * @param width
	 *            the width of the button
	 * @param height
	 *            the height of the button
	 * @param url
	 *            string containing the link pointing to the social network
	 * @return A image button for the social network with the all the proper
	 *         attributes
	 */
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

	/**
	 * Opens a link using a web browser
	 * 
	 * @param main
	 *            Activity trying to open the given link
	 * @param url
	 *            link to be open
	 */
	private static void openG11Url(Activity main, String url) {
		Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
		main.startActivity(browserIntent);
	}

}
