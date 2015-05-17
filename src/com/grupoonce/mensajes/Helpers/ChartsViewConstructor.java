package com.grupoonce.mensajes.helpers;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Point;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.grupoonce.charts.ChartItem;
import com.grupoonce.charts.FirebaseManagerCharts;
import com.grupoonce.mensajes.ChartsActivity;

public class ChartsViewConstructor {

	public static ArrayList<ArrayList<BarEntry>> statesCharts = new ArrayList<ArrayList<BarEntry>>();
	public static ArrayList<ChartItem> chartList;
	public static ArrayList<Entry> pieEntries = new ArrayList<Entry>();
	public static ChartDataAdapter cda;

	public static LinearLayout ConstructHeader(ChartsActivity main) {
		LinearLayout header = SharedViewConstructor.ConstructHeaderG11(main);

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

	/** adapter that supports 3 different item types */
	public static class ChartDataAdapter extends ArrayAdapter<ChartItem> {

		public ChartDataAdapter(Context context, List<ChartItem> objects) {
			super(context, 0, objects);

		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			return getItem(position).getView(position, convertView,
					getContext());
		}

		@Override
		public int getItemViewType(int position) {
			// return the views type
			return getItem(position).getItemType();
		}

		@Override
		public int getViewTypeCount() {
			return 3; // we have 2 different item-types
		}
	}

}
