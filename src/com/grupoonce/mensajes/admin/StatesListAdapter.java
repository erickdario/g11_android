package com.grupoonce.mensajes.admin;

import java.util.List;

import com.grupoonce.mensajes.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class StatesListAdapter extends BaseAdapter {

	private Context context;
	private List<State> statesItems;
	private Point size;

	public StatesListAdapter(Context context, List<State> navDrawerItems, Point size) {
		this.context = context;
		this.statesItems = navDrawerItems;
		this.size = size;
	}

	@Override
	public int getCount() {
		return statesItems.size();
	}

	@Override
	public State getItem(int position) {
		return statesItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint({ "InflateParams"})
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		State state = statesItems.get(position);

		LayoutInflater mInflater = (LayoutInflater) context
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.item_state, null);
		}

		TextView txtConversation = (TextView) convertView
				.findViewById(R.id.txtState);
		txtConversation.setText(state.getName());
		int stateImageId;
		switch (state.getName()) {
		case "Aguascalientes":
			stateImageId = R.drawable.aguascalientes;
			break;
		case "Celaya":
			stateImageId = R.drawable.celaya;
			break;
		case "Culiacan":
			stateImageId = R.drawable.culiacan;
			break;
		case "DF":
			stateImageId = R.drawable.df;
			break;
		case "Guadalajara":
			stateImageId = R.drawable.guadalajara;
			break;
		case "León":
			stateImageId = R.drawable.leon;
			break;
		case "Querétaro":
			stateImageId = R.drawable.queretaro;
			break;
		case "San Luis Potosí":
			stateImageId = R.drawable.san_luis;
			break;
		case "Tijuana":
			stateImageId = R.drawable.tijuana;
			break;
		case "Torreón":
			stateImageId = R.drawable.torreon;
			break;
		case "Zacatecas":
			stateImageId = R.drawable.zacatecas;
			break;
		default:
			stateImageId = R.drawable.zacatecas;
			break;
		}

		ImageView stateImage = (ImageView) convertView
				.findViewById(R.id.stateImage);
		stateImage.setImageResource(stateImageId);
		stateImage.setLayoutParams(new RelativeLayout.LayoutParams((int) (size.y * 0.1),
				(int) (size.y * 0.1)));

		return convertView;
	}
}