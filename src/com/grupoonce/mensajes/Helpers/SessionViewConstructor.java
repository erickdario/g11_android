package com.grupoonce.mensajes.Helpers;

import com.grupoonce.mensajes.MainActivity;
import com.grupoonce.mensajes.MainMenuActivity;
import com.grupoonce.mensajes.R;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.StateListDrawable;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Switch;

public class SessionViewConstructor {

	@SuppressLint("NewApi")
	public static LinearLayout ContructSessionBody(MainActivity main) {
		Point size = SharedViewConstructor.GetScreenSize(main);

		LinearLayout view = SharedViewConstructor.ConstructBackground(main,
				size, LinearLayout.LayoutParams.MATCH_PARENT);

		final LinearLayout viewLogin = ConstructLoginView(main, size);
		final LinearLayout viewSignup = ConstructSignup(main, size);
		viewSignup.setVisibility(View.GONE);

		Switch tb = new Switch(main);
		tb.setTextOn(main.getResources().getString(R.string.log_in));
		tb.setTextOff(main.getResources().getString(R.string.sign_up));
		tb.setSwitchTextAppearance(main, R.style.SwitchTextAppearance);
		tb.setChecked(true);

		StateListDrawable thumbStates = new StateListDrawable();
		thumbStates.addState(
				new int[] { android.R.attr.state_enabled },
				new ColorDrawable(main.getResources().getColor(
						R.color.orange_g11)));
		thumbStates.addState(new int[] {}, new ColorDrawable(main
				.getResources().getColor(R.color.gray_g11)));
		tb.setThumbDrawable(thumbStates);

		tb.setBackgroundColor(main.getResources().getColor(R.color.gray_g11));

		LinearLayout.LayoutParams switchLayoutParams = new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		switchLayoutParams.setMargins(0, 40, 0, 0);
		tb.setLayoutParams(switchLayoutParams);
		tb.setBackground(main.getResources().getDrawable(
				R.drawable.switch_shape, null));

		tb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					viewLogin.setVisibility(View.VISIBLE);
					viewSignup.setVisibility(View.GONE);
				} else {
					viewLogin.setVisibility(View.GONE);
					viewSignup.setVisibility(View.VISIBLE);
				}
			}
		});

		view.addView(tb);
		view.addView(viewSignup);
		view.addView(viewLogin);
		return view;
	}

	@SuppressLint("NewApi")
	private static LinearLayout ConstructLoginView(final MainActivity main,
			Point size) {
		final LinearLayout viewLogin = new LinearLayout(main);
		viewLogin.setOrientation(LinearLayout.VERTICAL);
		viewLogin.setLayoutParams(new LinearLayout.LayoutParams(
				(int) (size.x * 0.80), (int) (size.y * 0.85)));

		EditText email = ContructSessionEditText(main, R.string.email,
				InputType.TYPE_TEXT_VARIATION_WEB_EMAIL_ADDRESS);

		EditText password = ContructSessionEditText(main, R.string.password,
				InputType.TYPE_TEXT_VARIATION_PASSWORD);

		LinearLayout.LayoutParams btnLayoutParams = new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		btnLayoutParams.setMargins((int) (size.x * 0.4), (int) (size.y * 0.4),
				0, 0);

		final Button loginBtn = SharedViewConstructor.ConstructOrangeButton(
				main, size, R.string.log_in, (int) (size.x * 0.4),
				(int) (size.y * 0.4), LayoutParams.WRAP_CONTENT,
				R.drawable.session_btn_text, R.drawable.session_button);

		// Set click listener for button
		loginBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(main, MainMenuActivity.class);
				intent.putExtra("user", "dario");
				main.startActivity(intent);
			}
		});

		viewLogin.addView(email);
		viewLogin.addView(password);
		viewLogin.addView(loginBtn);

		return viewLogin;
	}

	private static LinearLayout ConstructSignup(final MainActivity main,
			Point size) {
		LinearLayout viewSignup = new LinearLayout(main);
		viewSignup.setOrientation(LinearLayout.VERTICAL);
		viewSignup.setLayoutParams(new LinearLayout.LayoutParams(
				(int) (size.x * 0.80), (int) (size.y * 0.85)));

		EditText companysName = ContructSessionEditText(main,
				R.string.company_name, InputType.TYPE_CLASS_TEXT);
		EditText email = ContructSessionEditText(main, R.string.email,
				InputType.TYPE_TEXT_VARIATION_WEB_EMAIL_ADDRESS);
		EditText password = ContructSessionEditText(main, R.string.password,
				InputType.TYPE_TEXT_VARIATION_PASSWORD);
		EditText confirmPassword = ContructSessionEditText(main,
				R.string.confirm_password,
				InputType.TYPE_TEXT_VARIATION_PASSWORD);

		final Button signUp = SharedViewConstructor.ConstructOrangeButton(main,
				size, R.string.sign_up, (int) (size.x * 0.38),
				(int) (size.y * 0.09), LayoutParams.WRAP_CONTENT,
				R.drawable.session_btn_text, R.drawable.session_button);

		// Set click listener for button
		signUp.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				Log.i("TAG", "index :" + signUp.getId());

				Toast.makeText(main.getApplicationContext(),
						"Clicked Button Index :" + signUp.getId(),
						Toast.LENGTH_SHORT).show();
			}
		});

		Spinner spinner = new Spinner(main);
		// Create an ArrayAdapter using the string array and a default spinner
		// layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				main, R.array.states_array,
				android.R.layout.simple_dropdown_item_1line);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinner.setAdapter(adapter);

		viewSignup.addView(companysName);
		viewSignup.addView(email);
		viewSignup.addView(password);
		viewSignup.addView(confirmPassword);
		viewSignup.addView(spinner);
		viewSignup.addView(signUp);
		return viewSignup;
	}

	@SuppressLint("NewApi")
	private static EditText ContructSessionEditText(MainActivity main,
			int placeholderId, int inputType) {
		LinearLayout.LayoutParams layoutParams = new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		layoutParams.setMargins(0, 40, 0, 0);

		EditText editTextSession = new EditText(main);
		editTextSession.setHint(main.getResources().getString(placeholderId));
		editTextSession.setLayoutParams(layoutParams);
		editTextSession.setInputType(InputType.TYPE_CLASS_TEXT | inputType);
		editTextSession.setBackground(main.getResources().getDrawable(
				R.drawable.edittext, null));

		return editTextSession;
	}
}
