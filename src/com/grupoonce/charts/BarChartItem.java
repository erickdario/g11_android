package com.grupoonce.charts;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.utils.ValueFormatter;
import com.grupoonce.mensajes.R;

public class BarChartItem extends ChartItem {

	public BarChartItem(ChartData<?> cd, Context c) {
		super(cd);
	}

	@Override
	public int getItemType() {
		return TYPE_BARCHART;
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, final Context c) {

		ViewHolder holder = null;

		if (convertView == null) {

			holder = new ViewHolder();

			convertView = LayoutInflater.from(c).inflate(
					R.layout.list_item_barchart, null);
			holder.chart = (BarChart) convertView.findViewById(R.id.chart);

			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		final BarChart mChart = holder.chart;
		ImageButton saveChart = (ImageButton) convertView
				.findViewById(R.id.saveImage);
		saveChart.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (mChart.saveToGallery(
						"Grupo_once" + System.currentTimeMillis(), 70)) {
					Toast.makeText(c, "Gráfica salvada con éxito!",
							Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(c, "La gráfica no pudo ser salvada!",
							Toast.LENGTH_SHORT).show();
				}

			}
		});

		// apply styling
		holder.chart.setDescription("");
		holder.chart.setDrawGridBackground(false);
		holder.chart.setDrawBarShadow(false);
		ValueFormatter custom = new CustomValueFormatter();

		int labelCount = (int) ((BarData) mChartData).getDataSets().get(0)
				.getYMax();
		if (labelCount > 25) {
			labelCount = 25;
		} else if (labelCount == 1) {
			labelCount = 2;
		}

		XAxis xAxis = holder.chart.getXAxis();
		xAxis.setPosition(XAxisPosition.BOTTOM);
		xAxis.setDrawGridLines(false);
		xAxis.setDrawAxisLine(true);

		YAxis leftAxis = holder.chart.getAxisLeft();
		leftAxis.setLabelCount(labelCount);
		// leftAxis.setSpaceTop(20f);
		leftAxis.setValueFormatter(custom);

		YAxis rightAxis = holder.chart.getAxisRight();
		rightAxis.setLabelCount(labelCount);
		// rightAxis.setSpaceTop(20f);
		rightAxis.setValueFormatter(custom);

		// set data
		holder.chart.setData((BarData) mChartData);

		// do not forget to refresh the chart
		// holder.chart.invalidate();
		holder.chart.animateY(700);

		return convertView;
	}

	private static class ViewHolder {
		BarChart chart;
	}
}