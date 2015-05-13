package com.grupoonce.chat;

import java.util.List;

import com.grupoonce.mensajes.R;
import com.grupoonce.mensajes.Helpers.ChatViewConstructor;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MessagesListAdapter extends BaseAdapter {

	private Context context;
	private List<Msg> messagesItems;

	public MessagesListAdapter(Context context, List<Msg> navDrawerItems) {
		this.context = context;
		this.messagesItems = navDrawerItems;
	}

	@Override
	public int getCount() {
		return messagesItems.size();
	}

	@Override
	public Msg getItem(int position) {
		return messagesItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		Msg m = messagesItems.get(position);

		LayoutInflater mInflater = (LayoutInflater) context
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

		if (ChatViewConstructor.role.equals("client")) {
			if (messagesItems.get(position).getSender().equals("client")) {
				convertView = mInflater.inflate(R.layout.item_msg_right, null);
			} else {
				convertView = mInflater.inflate(R.layout.item_msg_left, null);
			}
		}else{
			if (messagesItems.get(position).getSender().equals("adviser")) {
				convertView = mInflater.inflate(R.layout.item_msg_right, null);
			} else {
				convertView = mInflater.inflate(R.layout.item_msg_left, null);
			}
		}

		TextView lblFrom = (TextView) convertView.findViewById(R.id.lblMsgFrom);
		TextView txtMsg = (TextView) convertView.findViewById(R.id.txtMsg);
		txtMsg.setText(m.getText());
		lblFrom.setText(m.getDate() + " - " + m.getTime());

		return convertView;
	}
}