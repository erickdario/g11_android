package com.grupoonce.helpers;

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
				(int) (size.y * 0.80));
		view.setBackgroundColor(Color.BLACK);

		ScrollView servicesScrollView = SharedViewConstructor
				.ConstructScrollView(main, LayoutParams.MATCH_PARENT, size);
		LinearLayout servicesView = SharedViewConstructor.ConstructBackground(
				main, size, LayoutParams.MATCH_PARENT, (int) (size.y * 0.80));
		servicesView.setBackgroundColor(Color.BLACK);

		LayoutParams layout = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

		LinearLayout titleView = ConstructTitleView(main, size, layout,
				R.string.services);
		servicesView.addView(titleView);

		String[] services_array = main.getResources().getStringArray(
				R.array.services_array);

		for (int i = 0; i < services_array.length; i++) {
			LinearLayout serviceView = ConstructServiceView(main, size);

			String fname = "service" + (i + 1);
			int id = main.getResources().getIdentifier(fname, "drawable",
					"com.grupoonce.mensajes");

			ImageButton btnService = SharedViewConstructor
					.ConstructImageButton(main, id, size, 0.09f);

			TextView text = SharedViewConstructor.ConstructTextView(main,
					layout, 18, services_array[i], false, main.getResources()
							.getColor(R.color.orange_g11));

			serviceView.addView(btnService);
			serviceView.addView(text);
			servicesView.addView(serviceView);
		}

		LinearLayout titleView2 = ConstructTitleView(main, size, layout,
				R.string.services_section2);
		servicesView.addView(titleView2);

		services_array = main.getResources().getStringArray(
				R.array.services_section2_array);
		for (int i = 0; i < services_array.length; i++) {
			LinearLayout serviceView = ConstructServiceView(main, size);

			String fname = "multichanel" + (i + 1);
			int id = main.getResources().getIdentifier(fname, "drawable",
					"com.grupoonce.mensajes");

			ImageButton btnService = SharedViewConstructor
					.ConstructImageButton(main, id, size, 0.09f);

			TextView text = SharedViewConstructor.ConstructTextView(main,
					layout, 18, services_array[i], false, main.getResources()
							.getColor(R.color.orange_g11));

			serviceView.addView(btnService);
			serviceView.addView(text);
			servicesView.addView(serviceView);
		}

		LinearLayout titleView3 = ConstructTitleView(main, size, layout,
				R.string.services_section3);
		servicesView.addView(titleView3);

		services_array = main.getResources().getStringArray(
				R.array.services_section3_array);
		for (int i = 0; i < services_array.length; i++) {
			LinearLayout serviceView = ConstructServiceView(main, size);

			String fname = "value" + (i + 1);
			int id = main.getResources().getIdentifier(fname, "drawable",
					"com.grupoonce.mensajes");

			ImageButton btnService = SharedViewConstructor
					.ConstructImageButton(main, id, size, 0.09f);

			TextView text = SharedViewConstructor.ConstructTextView(main,
					layout, 18, services_array[i], false, main.getResources()
							.getColor(R.color.orange_g11));

			serviceView.addView(btnService);
			serviceView.addView(text);
			servicesView.addView(serviceView);
		}

		LinearLayout titleView4 = ConstructTitleView(main, size, layout,
				R.string.certification);
		servicesView.addView(titleView4);

		TextView textCertification = SharedViewConstructor.ConstructTextView(
				main,
				layout,
				18,
				main.getResources().getString(
						R.string.certification_explanation), false, main
						.getResources().getColor(R.color.orange_g11));
		LinearLayout certificationView = ConstructServiceView(main, size);
		certificationView.addView(textCertification);
		servicesView.addView(certificationView);

		LinearLayout titleView5 = ConstructTitleView(main, size, layout,
				R.string.customer_service);
		servicesView.addView(titleView5);

		TextView textCustomerService = SharedViewConstructor.ConstructTextView(
				main,
				layout,
				18,
				main.getResources().getString(
						R.string.customer_service_explanation), false, main
						.getResources().getColor(R.color.orange_g11));
		LinearLayout customerServiceView = ConstructServiceView(main, size);
		customerServiceView.addView(textCustomerService);
		servicesView.addView(customerServiceView);

		servicesScrollView.addView(servicesView);
		view.addView(servicesScrollView);
		return view;
	}

	private static LinearLayout ConstructTitleView(ServicesActivity main,
			Point size, LayoutParams layout, int stringId) {
		LinearLayout titleView = SharedViewConstructor.ConstructBackground(
				main, size, LinearLayout.LayoutParams.MATCH_PARENT,
				(int) (size.y * 0.1));
		titleView.setBackgroundColor(main.getResources().getColor(
				R.color.orange_g11));
		titleView.setGravity(Gravity.CENTER);

		TextView title = SharedViewConstructor.ConstructTextView(main, layout,
				24, main.getResources().getString(stringId), true, Color.BLACK);
		titleView.addView(title);

		return titleView;

	}

	private static LinearLayout ConstructServiceView(ServicesActivity main,
			Point size) {
		LinearLayout serviceView = new LinearLayout(main);
		serviceView.setOrientation(LinearLayout.HORIZONTAL);

		LayoutParams layoutService = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

		layoutService.setMargins((int) (size.x * 0.01), (int) (size.y * 0.02),
				0, 0);
		serviceView.setLayoutParams(layoutService);
		serviceView.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);

		return serviceView;
	}

}
