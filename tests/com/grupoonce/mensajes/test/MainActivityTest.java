package com.grupoonce.mensajes.test;

import com.grupoonce.mensajes.MainActivity;

import android.content.Intent;
import android.widget.Button;

/**
 * @author erickdario
 *
 */
public class MainActivityTest extends
		android.test.ActivityUnitTestCase<MainActivity> {

	private int buttonId;
	private MainActivity activity;

	public MainActivityTest() {
		super(MainActivity.class);
	}

	@Override
	// here is the place to setup the var types you want to test
	protected void setUp() throws Exception {
		super.setUp();
		Intent intent = new Intent(getInstrumentation().getTargetContext(),
				MainActivity.class);
		startActivity(intent, null, null);
		activity = getActivity();
	}

	public void testLayout() {
		buttonId = 0x33D;
		assertNotNull(activity.findViewById(buttonId));
		Button view = (Button) activity.findViewById(buttonId);
		assertEquals("Incorrect label of the button", "Iniciar sesión",
				view.getText());
	}

}
