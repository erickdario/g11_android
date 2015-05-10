package com.grupoonce.mensajes.Helpers;

import android.graphics.Color;
import android.graphics.Point;
import android.view.Gravity;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

import com.grupoonce.mensajes.R;
import com.grupoonce.mensajes.ServicesActivity;

;

public class ServicesViewConstructor {

	public static LinearLayout ContructBody(final ServicesActivity main) {
		Point size = SharedViewConstructor.GetScreenSize(main);

		LinearLayout view = SharedViewConstructor.ConstructBackground(main,
				size, LinearLayout.LayoutParams.MATCH_PARENT,
				(int) (size.y * 0.89));
		view.setBackgroundColor(Color.BLACK);

		LinearLayout titleView = SharedViewConstructor.ConstructBackground(
				main, size, LinearLayout.LayoutParams.MATCH_PARENT,
				(int) (size.y * 0.1));
		titleView.setBackgroundColor(main.getResources().getColor(
				R.color.orange_g11));
		titleView.setGravity(Gravity.CENTER);

		LayoutParams layout = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

		TextView title = SharedViewConstructor.ConstructTextView(main, layout,
				24, main.getResources().getString(R.string.services), true,
				Color.BLACK);
		titleView.addView(title);

		LinearLayout servicesView = SharedViewConstructor.ConstructBackground(
				main, size, (int) (size.x * 0.9), (int) (size.y * 0.69));
		ScrollView servicesScrollView = SharedViewConstructor
				.ContructScrollView(main, LayoutParams.MATCH_PARENT, size);
		servicesView.setBackgroundColor(Color.BLACK);

		String[] services_array = main.getResources().getStringArray(
				R.array.services_array);
		for (int i = 0; i < services_array.length; i++) {

			LinearLayout serviceView = new LinearLayout(main);
			serviceView.setOrientation(LinearLayout.HORIZONTAL);

			LayoutParams layoutService = new LinearLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

			layoutService.setMargins((int) (size.x * 0.01),
					(int) (size.y * 0.02), 0, 0);
			serviceView.setLayoutParams(layoutService);
			serviceView.setGravity(Gravity.START);

			String fname = "service" + (i + 1);
			int id = main.getResources().getIdentifier(fname, "drawable",
					"com.grupoonce.mensajes");
			
			ImageButton btnService = SharedViewConstructor.ContructImageButton(main, id, size, 0.09f);

			TextView text = SharedViewConstructor.ConstructTextView(main,
					layout, 18, services_array[i], false, main.getResources()
							.getColor(R.color.orange_g11));

			serviceView.addView(btnService);
			serviceView.addView(text);
			servicesView.addView(serviceView);
		}

		servicesScrollView.addView(servicesView);
		view.addView(titleView);
		view.addView(servicesScrollView);
		return view;
	}

}
