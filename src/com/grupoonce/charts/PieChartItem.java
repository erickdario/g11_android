package com.grupoonce.charts;

import java.io.File;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendPosition;
import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.utils.PercentFormatter;
import com.grupoonce.mensajes.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.Toast;

public class PieChartItem extends ChartItem {

	public PieChartItem(ChartData<?> cd, Context c) {
		super(cd);
	}

	@Override
	public int getItemType() {
		return TYPE_PIECHART;
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, final Context c) {

		ViewHolder holder = null;

		if (convertView == null) {

			holder = new ViewHolder();

			convertView = LayoutInflater.from(c).inflate(
					R.layout.list_item_piechart, null);
			holder.chart = (PieChart) convertView.findViewById(R.id.chart);

			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		final PieChart mChart = holder.chart;
		ImageButton saveChart = (ImageButton) convertView
				.findViewById(R.id.saveImage);
		saveChart.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				File extBaseDir = Environment.getExternalStorageDirectory();
				File file = new File(extBaseDir.getAbsolutePath() + "/DCIM/Grupo_once");
		        if (!file.exists()) {
		            if (!file.mkdirs()) {
		                return;
		            }
		        }
				if (mChart.saveToGallery(
						"Grupo_once/G11_" + System.currentTimeMillis() + ".jpeg", 70)) {
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
		holder.chart.setHoleRadius(52f);
		holder.chart.setTransparentCircleRadius(57f);
		holder.chart.setCenterText("Grupo Once");
		holder.chart.setCenterTextSize(18f);
		holder.chart.setUsePercentValues(true);

		mChartData.setValueFormatter(new PercentFormatter());
		mChartData.setValueTextSize(11f);
		mChartData.setValueTextColor(Color.WHITE);
		// set data
		holder.chart.setData((PieData) mChartData);

		Legend l = holder.chart.getLegend();
		l.setPosition(LegendPosition.RIGHT_OF_CHART);

		// do not forget to refresh the chart
		// holder.chart.invalidate();
		holder.chart.animateXY(900, 900);

		return convertView;
	}

	public static class ViewHolder {
		PieChart chart;
	}
}
