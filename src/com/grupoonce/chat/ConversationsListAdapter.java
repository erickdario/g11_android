package com.grupoonce.chat;

import java.util.List;

import com.grupoonce.mensajes.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ConversationsListAdapter extends BaseAdapter {

	private Context context;
	private List<Conversation> conversationsItems;

	public ConversationsListAdapter(Context context,
			List<Conversation> navDrawerItems) {
		this.context = context;
		this.conversationsItems = navDrawerItems;
	}

	public int getCountUnread() {
		int unread = 0;
		for (int index = 0; index < conversationsItems.size(); index++) {
			if (!conversationsItems.get(index).getRead()) {
				unread++;
			}
		}

		return unread;
	}

	@Override
	public int getCount() {
		return conversationsItems.size();
	}

	@Override
	public Conversation getItem(int position) {
		return conversationsItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		Conversation conversation = conversationsItems.get(position);

		LayoutInflater mInflater = (LayoutInflater) context
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.item_conversation, null);
		}

		TextView txtConversation = (TextView) convertView
				.findViewById(R.id.txtConversation);
		txtConversation.setText(conversation.getCompanysName());
		
		TextView txtUserName = (TextView) convertView
				.findViewById(R.id.lblUserName);
		txtUserName.setText(conversation.getUserName());

		TextView read = (TextView) convertView.findViewById(R.id.read);

		if (conversation.getRead()) {
			read.setText(Html.fromHtml("&#9898;"));
		} else {
			read.setText(Html.fromHtml("&#9679;"));
		}

		TextView txtTimeFrom = (TextView) convertView
				.findViewById(R.id.lblTimeFrom);
		txtTimeFrom.setText(conversation.getLastDateMsg());

		return convertView;
	}
}