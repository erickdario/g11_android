package com.grupoonce.mensajes.Helpers;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.grupoonce.chat.FirebaseManager;
import com.grupoonce.mensajes.MainActivity;
import com.grupoonce.mensajes.R;

import android.annotation.SuppressLint;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Switch;
import android.widget.Toast;

public class SessionViewConstructor {

	@SuppressWarnings("deprecation")
	@SuppressLint("NewApi")
	public static LinearLayout ContructBody(MainActivity main) {
		Firebase.setAndroidContext(main);
		FirebaseManager.main = main;
		AuthData authData = FirebaseManager.ref.getAuth();
		if (authData != null) {
			FirebaseManager.UserWasAuthenticated(authData);
		} else {
			// no user authenticated with Firebase
		}

		Point size = SharedViewConstructor.GetScreenSize(main);

		LinearLayout view = SharedViewConstructor.ConstructBackground(main,
				size, LinearLayout.LayoutParams.MATCH_PARENT,
				(int) (size.y * 0.89));

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

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			tb.setBackground(main.getResources().getDrawable(
					R.drawable.switch_shape, null));
		} else {
			tb.setBackground(main.getResources().getDrawable(
					R.drawable.switch_shape));
		}

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

	private static LinearLayout ConstructLoginView(final MainActivity main,
			Point size) {
		final LinearLayout viewLogin = new LinearLayout(main);
		viewLogin.setOrientation(LinearLayout.VERTICAL);
		viewLogin.setLayoutParams(new LinearLayout.LayoutParams(
				(int) (size.x * 0.80), (int) (size.y * 0.85)));

		final EditText email = ContructSessionEditText(main, R.string.email,
				InputType.TYPE_TEXT_VARIATION_WEB_EMAIL_ADDRESS);

		final EditText password = ContructSessionEditText(main,
				R.string.password, InputType.TYPE_TEXT_VARIATION_PASSWORD);

		LinearLayout.LayoutParams btnLayoutParams = new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		btnLayoutParams.setMargins((int) (size.x * 0.4), (int) (size.y * 0.4),
				0, 0);

		final Button loginBtn = SharedViewConstructor.ConstructButton(main,
				size, R.string.log_in, (int) (size.x * 0.4),
				(int) (size.y * 0.4), LayoutParams.WRAP_CONTENT,
				R.drawable.session_btn_text, R.drawable.session_button);

		// Set click listener for button
		loginBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				FirebaseManager.ref.authWithPassword(
						email.getText().toString(), password.getText()
								.toString(), FirebaseManager.authResultHandler);
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

		final EditText companysName = ContructSessionEditText(main,
				R.string.company_name, InputType.TYPE_CLASS_TEXT);
		final EditText email = ContructSessionEditText(main, R.string.email,
				InputType.TYPE_TEXT_VARIATION_WEB_EMAIL_ADDRESS);
		final EditText password = ContructSessionEditText(main,
				R.string.password, InputType.TYPE_TEXT_VARIATION_PASSWORD);
		EditText confirmPassword = ContructSessionEditText(main,
				R.string.confirm_password,
				InputType.TYPE_TEXT_VARIATION_PASSWORD);

		final Button signUp = SharedViewConstructor.ConstructButton(main, size,
				R.string.sign_up, (int) (size.x * 0.38), (int) (size.y * 0.09),
				LayoutParams.WRAP_CONTENT, R.drawable.session_btn_text,
				R.drawable.session_button);

		final Spinner spinner = new Spinner(main);
		// Create an ArrayAdapter using the string array and a default spinner
		// layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				main, R.array.states_array,
				android.R.layout.simple_dropdown_item_1line);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinner.setAdapter(adapter);

		// Set click listener for button
		signUp.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (companysName.getText().toString().equals("grupoonce")) {
					Toast.makeText(main,
							"Por favor escriba otro nombre para su compañia",
							Toast.LENGTH_SHORT).show();
				} else {
					FirebaseManager.CreateUser(email.getText().toString(),
							spinner.getSelectedItem().toString(), password
									.getText().toString(), companysName
									.getText().toString());
				}
			}
		});

		viewSignup.addView(companysName);
		viewSignup.addView(email);
		viewSignup.addView(password);
		viewSignup.addView(confirmPassword);
		viewSignup.addView(spinner);
		viewSignup.addView(signUp);
		return viewSignup;
	}

	@SuppressWarnings("deprecation")
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

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			editTextSession.setBackground(main.getResources().getDrawable(
					R.drawable.edittext, null));
		} else {
			editTextSession.setBackground(main.getResources().getDrawable(
					R.drawable.edittext));
		}

		return editTextSession;
	}
}
