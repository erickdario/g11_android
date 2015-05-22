package com.grupoonce.helpers;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Point;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.grupoonce.charts.ChartDataAdapter;
import com.grupoonce.charts.ChartItem;
import com.grupoonce.charts.FirebaseManagerCharts;
import com.grupoonce.mensajes.ChartsActivity;
import com.grupoonce.mensajes.R;

public class ChartsViewConstructor {

	public static ArrayList<ArrayList<BarEntry>> statesCharts = new ArrayList<ArrayList<BarEntry>>();
	public static ArrayList<ChartItem> chartList;
	public static ArrayList<Entry> pieEntries = new ArrayList<Entry>();
	public static ChartDataAdapter cda;

	public static LinearLayout ConstructHeader(final ChartsActivity main) {
		LinearLayout header = SharedViewConstructor.ConstructHeaderG11(main);
		Point size = SharedViewConstructor.GetScreenSize(main);

		LinearLayout menu = (LinearLayout) header.getChildAt(1);
		header.removeViewAt(1);

		LayoutParams layout = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

		TextView chartTitle = SharedViewConstructor.ConstructTextView(
				(Activity) main, layout, 20,
				main.getResources().getString(R.string.charts), false,
				Color.BLACK);
		chartTitle.setGravity(Gravity.CENTER_HORIZONTAL);

		ImageButton sendEmail = SharedViewConstructor.ConstructImageButton(
				main, R.drawable.send_email, size, 0.15f);

		sendEmail.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				FirebaseManagerCharts.SendEmailInfo(main);
			}

		});

		menu.addView(chartTitle);
		menu.addView(sendEmail);
		header.addView(menu);
		return header;
	}

	public static LinearLayout ConstructBody(ChartsActivity main) {
		Point size = SharedViewConstructor.GetScreenSize(main);

		LinearLayout view = SharedViewConstructor.ConstructBackground(main,
				size, LinearLayout.LayoutParams.MATCH_PARENT,
				(int) (size.y * 0.79));

		ListView lv = (ListView) new ListView(main);

		chartList = new ArrayList<ChartItem>();

		FirebaseManagerCharts.GetInfoCharts(main);

		cda = new ChartDataAdapter(main, chartList);
		lv.setAdapter(cda);

		view.addView(lv);
		return view;
	}

}
