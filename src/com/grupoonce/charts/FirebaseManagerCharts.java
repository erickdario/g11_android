package com.grupoonce.charts;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

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
	static String messageConversations = "";

	public static void SendEmailInfo(final ChartsActivity main) {
		Firebase closedConversationsRef = ref.child("closed_conversations");
		closedConversationsRef
				.addListenerForSingleValueEvent(new ValueEventListener() {

					@Override
					public void onCancelled(FirebaseError arg0) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onDataChange(DataSnapshot conversationSnapshot) {
						messageConversations = "";
						for (DataSnapshot cityConversation : conversationSnapshot
								.getChildren()) {
							messageConversations += "Ciudad: "
									+ cityConversation.getKey() + "\n";
							for (DataSnapshot clientConversation : cityConversation
									.getChildren()) {
								messageConversations += "-Cliente: "
										+ clientConversation.getKey() + "\n";
								messageConversations += "--Area: "
										+ clientConversation.child("/area")
												.getValue().toString() + "\n";
								messageConversations += "--Comentario de asesor: "
										+ clientConversation.child("/comment")
												.getValue().toString() + "\n";
							}
						}
						SendEmail(main);
					}

				});
	}

	public static void GetInfoCharts(final ChartsActivity main) {
		Firebase statesRef = ref.child("charts");

		Calendar cal = Calendar.getInstance();
		int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);

		if (dayOfMonth == 1) {
			statesRef.removeValue();
			ref.child("closed_conversations").removeValue();
		}

		statesRef.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot snapshot) {
				ChartsViewConstructor.statesCharts.clear();
				ChartsViewConstructor.pieEntries.clear();
				Iterator<DataSnapshot> iterator = snapshot.getChildren()
						.iterator();
				int id = 0;
				ArrayList<Integer> colors = new ArrayList<Integer>();

				for (int c : ColorTemplate.JOYFUL_COLORS)
					colors.add(c);

				for (int c : ColorTemplate.COLORFUL_COLORS)
					colors.add(c);

				int[] areasMean = new int[9];
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
					d.setColors(colors);
					d.setHighLightAlpha(255);

					BarData cd = new BarData(getAreas(), d);

					ChartsViewConstructor.chartList.add(new BarChartItem(cd,
							main));

					id++;
				}

				for (int i = 0; i < areasMean.length; i++) {
					ChartsViewConstructor.pieEntries.add(new Entry(
							areasMean[i], i));
				}

				PieDataSet d = new PieDataSet(ChartsViewConstructor.pieEntries,
						"");

				// space between slices
				d.setSliceSpace(2f);

				d.setColors(colors);

				PieData cd = new PieData(getAreas(), d);
				ChartsViewConstructor.chartList.add(new PieChartItem(cd, main));

				ChartsViewConstructor.cda.notifyDataSetChanged();
			}

			@Override
			public void onCancelled(FirebaseError firebaseError) {
			}
		});
	}

	private static void SendEmail(ChartsActivity main) {
		try {
			ArrayList<Uri> URIs = new ArrayList<Uri>();
			String email = "erick_dario2492@hotmail.com";
			String subject = "Mensajes asesores de conversaciones concluidas y gráficas";
			String message = messageConversations;
			File sdcard = Environment.getExternalStorageDirectory();
			File file = new File(sdcard, "DCIM/Grupo_once/");

			final Intent emailIntent = new Intent(
					android.content.Intent.ACTION_SEND_MULTIPLE);
			emailIntent.setType("plain/text");
			emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
					new String[] { email });
			emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
			try {
				List<File> chartImgs = getListFiles(file);
				for (File img : chartImgs) {
					URIs.add(Uri.parse("file://" + img.getAbsolutePath()));
				}
				emailIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM,
						URIs);
			} catch (Throwable t) {
				Toast.makeText(main,
						"No ha salvado ninguna gráfica hasta el momento ",
						Toast.LENGTH_SHORT).show();
			}

			emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, message);
			main.startActivity(Intent.createChooser(emailIntent,
					"Sending email..."));

		} catch (Throwable t) {
			Toast.makeText(
					main,
					"Algo salió mal, por favor intente mas tarde: "
							+ t.toString(), Toast.LENGTH_SHORT).show();
		}
	}

	private static List<File> getListFiles(File parentDir) {
		ArrayList<File> inFiles = new ArrayList<File>();
		File[] files = parentDir.listFiles();
		for (File file : files) {
			if (file.getName().startsWith("G11")) {
				inFiles.add(file);
			}
		}
		return inFiles;
	}

	private static ArrayList<String> getAreas() {

		ArrayList<String> areas = new ArrayList<String>();
		areas.add("Recibos");
		areas.add("Facturas");
		areas.add("IMSS");
		areas.add("Jurídico");
		areas.add("Requerimiento");
		areas.add("Tesorería");
		areas.add("Operativo");
		areas.add("Contabilidad");
		areas.add("Auditoría");

		return areas;
	}

}
