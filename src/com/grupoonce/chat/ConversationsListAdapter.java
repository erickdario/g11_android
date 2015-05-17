package com.grupoonce.chat;

import java.util.List;

import com.grupoonce.mensajes.R;
import com.grupoonce.mensajes.helpers.MMAdviserViewConstructor;
import com.grupoonce.mensajes.helpers.SharedViewConstructor;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

		ImageButton closeConversation = (ImageButton) convertView
				.findViewById(R.id.closeConversation);

		closeConversation.setOnClickListener(closeConversationClickListener);

		TextView txtCompany = (TextView) convertView
				.findViewById(R.id.txtCompany);
		txtCompany.setText(conversation.getCompanysName());

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

	private OnClickListener closeConversationClickListener = new OnClickListener() {
		@SuppressLint("InflateParams")
		@Override
		public void onClick(View trigeringButton) {
			Point size = SharedViewConstructor
					.GetScreenSize((Activity) context);
			View parentRow = (View) trigeringButton.getParent();
			TextView txtCompany = (TextView) parentRow
					.findViewById(R.id.txtCompany);
			TextView txtUserName = (TextView) parentRow
					.findViewById(R.id.lblUserName);
			final String conversation = txtUserName.getText().toString() + "%"
					+ txtCompany.getText().toString();

			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			final View popUpView = inflater.inflate(
					R.layout.close_conversation_popup, null, false);
			final PopupWindow popupWindow = new PopupWindow(popUpView,
					(int) (size.x * 0.9), LayoutParams.WRAP_CONTENT, true);

			final Button btnDismiss = (Button) popUpView
					.findViewById(R.id.dismiss);

			btnDismiss.setOnClickListener(new Button.OnClickListener() {

				@Override
				public void onClick(View v) {
					popupWindow.dismiss();
				}
			});

			final Button btnAccept = (Button) popUpView
					.findViewById(R.id.accept);
			btnAccept.setTag(trigeringButton);

			final EditText commentConversation = (EditText) popUpView
					.findViewById(R.id.comment_close_conversation);

			btnAccept.setOnClickListener(new Button.OnClickListener() {

				@SuppressWarnings("deprecation")
				@SuppressLint("NewApi")
				@Override
				public void onClick(View innerView) {

					if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
						((ImageButton) innerView.getTag())
								.setBackground(context
										.getResources()
										.getDrawable(R.drawable.completed, null));
					} else {
						((ImageButton) innerView.getParent())
								.setBackground(context.getResources()
										.getDrawable(R.drawable.completed));
					}

					RadioGroup radioGroup = (RadioGroup) popUpView
							.findViewById(R.id.areas);
					int selectedId = radioGroup.getCheckedRadioButtonId();

					View radioAreaButton = radioGroup.findViewById(selectedId);

					int radioId = radioGroup.indexOfChild(radioAreaButton);
					RadioButton btn = (RadioButton) radioGroup
							.getChildAt(radioId);
					String area = (String) btn.getText();

					FirebaseManager.CloseConversation(
							MMAdviserViewConstructor.city, conversation,
							commentConversation.getText().toString(), area);
					popupWindow.dismiss();
				}
			});

			popupWindow.showAtLocation(parentRow, Gravity.CENTER, 0, 0);
		}
	};
}