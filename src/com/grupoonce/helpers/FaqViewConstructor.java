/**
 * 
 */
package com.grupoonce.helpers;

import com.grupoonce.mensajes.FaqActivity;
import com.grupoonce.mensajes.R;

import android.graphics.Color;
import android.graphics.Point;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

/**
 * @author erickdario
 *
 */
public class FaqViewConstructor {

	public static LinearLayout ContructBody(final FaqActivity main) {
		Point size = SharedViewConstructor.GetScreenSize(main);

		LinearLayout view = SharedViewConstructor.ConstructBackground(main,
				size, LinearLayout.LayoutParams.MATCH_PARENT,
				(int) (size.y * 0.79));

		LinearLayout titleView = SharedViewConstructor.ConstructBackground(
				main, size, (int) (size.x * 0.80), (int) (size.y * 0.1));

		LinearLayout questionsView = SharedViewConstructor.ConstructBackground(
				main, size, (int) (size.x * 0.70), (int) (size.y * 0.69));

		ScrollView faqScrollView = SharedViewConstructor.ConstructScrollView(
				main, (int) (size.x * 0.70), size);

		LayoutParams layout = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		layout.setMargins(0, (int) (size.y * 0.01), 0, 0);

		TextView title = SharedViewConstructor.ConstructTextView(main, layout,
				24, main.getResources().getString(R.string.faq), true,
				Color.BLACK);
		titleView.addView(title);

		String[] faq_array = main.getResources().getStringArray(
				R.array.faq_array);
		layout.setMargins(0, (int) (size.y * 0.02), 0, 0);
		for (int i = 0; i < faq_array.length; i++) {
			Boolean bold = false;
			if (i % 2 == 0) {
				bold = true;
			}
			TextView text = SharedViewConstructor.ConstructTextView(main,
					layout, 16, faq_array[i], bold, Color.BLACK);
			questionsView.addView(text);
		}

		faqScrollView.addView(questionsView);
		view.addView(titleView);
		view.addView(faqScrollView);
		return view;
	}
}
