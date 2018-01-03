package com.trabal.linear;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.trabal.R;
import com.trabal.activity.Bean.ActivityBean;
import com.trabal.linear.FansLinearLayout.CustomAdapter;
import com.trabal.user.Bean.UserBean;

public class chosepositionactivity extends Activity {

	private ImageButton button;
	private Intent intent;
	private ListView listView;
	private UserBean user;
	private ArrayList<ActivityBean> follow;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choseposition);

		intent = this.getIntent();

		button = (ImageButton) findViewById(R.id.leftarrow2ID);

		button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent last_intent = chosepositionactivity.this.getIntent();
				user = (UserBean) last_intent.getSerializableExtra("user");
				Intent intent = new Intent(chosepositionactivity.this,
						assessactivity.class);
				intent.putExtra("user", user);
				startActivity(intent);
			}
		});

		listView = (ListView) this.findViewById(R.id.chose_listview);

		// ��������Դ
		follow = new ArrayList<ActivityBean>();
		ActivityBean ub = new ActivityBean();
		ub.setTitle("�t�o");
		follow.add(ub);

		ActivityBean ab = new ActivityBean();
		ab.setTitle("�ǿ��o");
		follow.add(ab);

		listView.setAdapter(new CustomAdapter());

	}

	class CustomAdapter extends BaseAdapter {

		public int getCount() {
			return follow.size();
		}

		@Override
		public Object getItem(int position) {
			return follow.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {

			// ��ȡ�����ļ�
			View view = LayoutInflater.from(chosepositionactivity.this)
					.inflate(R.layout.chose_listview_item, null);
			TextView name = (TextView) view.findViewById(R.id.chose_itemtext1);
			name.setText(follow.get(position).getTitle());
			
			view.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					Intent last_intent = chosepositionactivity.this.getIntent();
					user = (UserBean) last_intent.getSerializableExtra("user");
					Intent intent = new Intent();
					String backData = follow.get(position).getTitle();
					intent.putExtra("position", backData);
					intent.putExtra("user", user);
					setResult(2000, intent);
					// ������ǰҳ��(�رյ�ǰ����)
					finish();
				}
			});

			return view;
		}

	}
}
