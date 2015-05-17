package com.grupoonce.charts;

import java.util.ArrayList;
import java.util.Iterator;

import android.app.Activity;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.grupoonce.mensajes.ChartsActivity;
import com.grupoonce.mensajes.helpers.ChartsViewConstructor;

public class FirebaseManagerCharts {

	public static Activity main;
	public static Firebase ref = new Firebase(
			"https://glaring-heat-1751.firebaseio.com");

	public static void GetInfoCharts(final ChartsActivity main) {
		Firebase statesRef = ref.child("charts");

		statesRef.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot snapshot) {
				ChartsViewConstructor.statesCharts.clear();
				ChartsViewConstructor.pieEntries.clear();
				Iterator<DataSnapshot> iterator = snapshot.getChildren()
						.iterator();
				int id = 0;
				int[] areasMean = new int[5];
				while (iterator.hasNext()) {
					DataSnapshot stateSnapshot = iterator.next();
					Iterator<DataSnapshot> stateIterator = stateSnapshot
							.getChildren().iterator();
					int index = 0;
					ChartsViewConstructor.statesCharts
							.add(new ArrayList<BarEntry>());
					while (stateIterator.hasNext()) {
						DataSnapshot areaSnapshot = stateIterator.next();
						ChartsViewConstructor.statesCharts.get(id).add(
								new BarEntry(Float.parseFloat(areaSnapshot
										.getValue().toString()), index));
						areasMean[index] += Integer.parseInt(areaSnapshot
								.getValue().toString());
						index++;
					}
					BarDataSet d = new BarDataSet(
							ChartsViewConstructor.statesCharts.get(id),
							stateSnapshot.getKey());
					d.setBarSpacePercent(20f);
					d.setColors(ColorTemplate.JOYFUL_COLORS);
					d.setHighLightAlpha(255);

					BarData cd = new BarData(getAreas(), d);

					ChartsViewConstructor.chartList.add(new BarChartItem(cd,
							main));

					id++;
				}

				for (int i = 0; i < 5; i++) {
					ChartsViewConstructor.pieEntries.add(new Entry(
							areasMean[i], i));
				}

				PieDataSet d = new PieDataSet(ChartsViewConstructor.pieEntries,
						"");

				// space between slices
				d.setSliceSpace(2f);
				d.setColors(ColorTemplate.JOYFUL_COLORS);

				PieData cd = new PieData(getAreas(), d);
				ChartsViewConstructor.chartList.add(new PieChartItem(cd, main));

				ChartsViewConstructor.cda.notifyDataSetChanged();
			}

			@Override
			public void onCancelled(FirebaseError firebaseError) {
			}
		});
	}

	private static ArrayList<String> getAreas() {

		ArrayList<String> areas = new ArrayList<String>();
		areas.add("Cobranzas");
		areas.add("Finanzas");
		areas.add("Fiscal");
		areas.add("Otros");
		areas.add("RH");

		return areas;
	}

}
