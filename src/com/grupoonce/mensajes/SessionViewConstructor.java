package com.grupoonce.mensajes;

import com.grupoonce.mensajes.Helpers.ViewConstructor;

import android.annotation.SuppressLint;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.StateListDrawable;
import android.text.InputType;
import android.view.Gravity;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Switch;

public class SessionViewConstructor {

	@SuppressLint("NewApi")
	public static LinearLayout ContructSessionBody(MainActivity main){
		Point size = ViewConstructor.GetScreenSize(main);
		
		LinearLayout view = new LinearLayout(main);
		view.setOrientation(LinearLayout.VERTICAL);
		view.setLayoutParams(new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT, (int) (size.y * 0.89)));
		view.setGravity(Gravity.CENTER_HORIZONTAL);
		
		view.setBackgroundColor(main.getResources().getColor(R.color.gray_g11));
		
		
		Switch tb = new Switch(main);
        tb.setTextOn(main.getResources().getString(R.string.log_in));
        tb.setTextOff(main.getResources().getString(R.string.sign_up));
        tb.setSwitchTextAppearance(main, R.style.SwitchTextAppearance);
        tb.setChecked(true);
        
        
        StateListDrawable thumbStates = new StateListDrawable();
        thumbStates.addState(new int[]{android.R.attr.state_enabled}, new ColorDrawable(main.getResources().getColor(R.color.orange_g11)));
        thumbStates.addState(new int[]{}, new ColorDrawable(main.getResources().getColor(R.color.gray_g11))); // this one has to come last
        tb.setThumbDrawable(thumbStates);
        
        tb.setBackgroundColor(main.getResources().getColor(R.color.gray_g11));
        tb.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		tb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
		    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		        if (isChecked) {
		            // The toggle is enabled
		        } else {
		            // The toggle is disabled
		        }
		    }
		});
		
		
		LinearLayout viewLogin = new LinearLayout(main);
		viewLogin.setOrientation(LinearLayout.VERTICAL);
		viewLogin.setLayoutParams(new LinearLayout.LayoutParams(
				(int) (size.x * 0.80), (int) (size.y * 0.85)));
		
		EditText email = new EditText(main);
		email.setHint(main.getResources().getString(R.string.email));
		email.setLayoutParams(new LayoutParams(
	            LayoutParams.MATCH_PARENT,
	            LayoutParams.WRAP_CONTENT));
		email.setInputType(InputType.TYPE_TEXT_VARIATION_WEB_EMAIL_ADDRESS);
		EditText password = new EditText(main);
		password.setHint(main.getResources().getString(R.string.password));
		password.setLayoutParams(new LayoutParams(
	            LayoutParams.MATCH_PARENT,
	            LayoutParams.WRAP_CONTENT));
		password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
		viewLogin.addView(email);
		viewLogin.addView(password);
		
		LinearLayout viewSignup = new LinearLayout(main);
		viewSignup.setOrientation(LinearLayout.VERTICAL);
		viewSignup.setLayoutParams(new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT, (int) (size.y * 0.85)));
		
		
		
		view.addView(tb);
		view.addView(viewLogin);
		return view;
	}
}
