package com.trabal.linear;

import java.util.ArrayList;

import com.squareup.picasso.Picasso;
import com.trabal.R;
import com.trabal.activity.Bean.ActivityBean;
import com.trabal.hotspot.Bean.HotSpotBean;
import com.trabal.linear.FriendLinearLayout.CustomAdapter;
import com.trabal.user.Bean.EvaluationBean;
import com.trabal.user.Bean.UserBean;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class Fansxiangqingye extends Activity {
	private ListView listView;
	private ArrayList<UserBean> fans;
	private UserBean user;
	private Intent last_intent;
	ArrayList<EvaluationBean> hwb;
	private View headerView;
	private ArrayList<ActivityBean> ab_list;
	private Button button;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_hyxiangqingye);

		last_intent = Fansxiangqingye.this.getIntent();
		user = (UserBean) last_intent.getSerializableExtra("user");
		/*
		 * hwb=(ArrayList<EvaluationBean>)last_intent.getSerializableExtra("hwb")
		 * ;
		 * 
		 * for(EvaluationBean item: hwb){ item.getMood(); item.getPrice();
		 * item.getUser(); }
		 */
		listView = (ListView) this.findViewById(R.id.hyxiangqingyeID);
		// header_listview

		headerView = LayoutInflater.from(Fansxiangqingye.this).inflate(
				R.layout.hyxiangqingye3_item, null);

		// 创建数据源
		ab_list = new ArrayList<ActivityBean>();

		ActivityBean ab = new ActivityBean();
		ab.setPerson("admin");
		ab.setPrice("100/人");
		ab.setTime("12:35");
		ab.setIntroduction("位于写字楼下的独立咖啡馆，仿佛是理想中咖啡馆甚佳的选址。几乎必点拿铁，出品很有诚意，再看15-20的均价超良心,我想這是個好去處。");
		ab.setTitle("Poem");
		ab.setPic1(String.valueOf(R.drawable.person));
		ab.setPic2(String.valueOf(R.drawable.l2));
		ab.setPic3(String.valueOf(R.drawable.l2));
		ab.setPic4(String.valueOf(R.drawable.l2));
		ab.setPic5(String.valueOf(R.drawable.person));
		ab.setPic6(String.valueOf(R.drawable.person));
		ab.setPic7(String.valueOf(R.drawable.person));
		ab.setPic8(String.valueOf(R.drawable.person));
		ab.setPic9(String.valueOf(R.drawable.person));

		ab_list.add(ab);

		ActivityBean ab1 = new ActivityBean();
		ab1.setPerson("admin");
		ab1.setPrice("100/人");
		ab1.setTime("12:35");
		ab1.setIntroduction("我想這是個好去處");
		ab1.setTitle("Poem");
		ab1.setPic1(String.valueOf(R.drawable.person));
		ab1.setPic2(String.valueOf(R.drawable.l2));
		ab1.setPic3(String.valueOf(R.drawable.l2));
		ab1.setPic4(String.valueOf(R.drawable.l2));
		ab1.setPic5(String.valueOf(R.drawable.person));
		ab1.setPic6(String.valueOf(R.drawable.person));
		ab1.setPic7(String.valueOf(R.drawable.person));
		ab1.setPic8(String.valueOf(R.drawable.person));
		ab1.setPic9(String.valueOf(R.drawable.person));

		ab_list.add(ab1);
		// 创建一个Adapter的实例
		listView.addHeaderView(headerView);
		listView.setAdapter(new CustomAdapter());
		
		//返回上一个界面
		button =(Button)findViewById(R.id.left3ID);
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(Fansxiangqingye.this,haoyouActivity.class);
				intent.putExtra("user", user);
				Fansxiangqingye.this.startActivity(intent);
				
			}
		});

	}

	class CustomAdapter extends BaseAdapter {

		public int getCount() {
			return ab_list.size();
		}

		@Override
		public Object getItem(int position) {
			return ab_list.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {

			// 获取布局文件
			View view = View.inflate(Fansxiangqingye.this,
					R.layout.pjxiangqingye3_item, null);
/*			TextView pay = (TextView) view.findViewById(R.id.tv7ID);
			TextView mood = (TextView) view.findViewById(R.id.tx12ID);
			TextView consumer = (TextView) view.findViewById(R.id.hyxqy_name6);

			pay.setText(hwb.get(position).getPrice() + "");
			mood.setText(hwb.get(position).getMood());
			consumer.setText(user.getName() + "");*/

			TextView mTextView1 = (TextView) view
					.findViewById(R.id.pjxqy_name3);
			TextView mTextView2 = (TextView) view
					.findViewById(R.id.pjxqy_date3);
			TextView mTextView3 = (TextView) view.findViewById(R.id.pjxqy_pj3);
			TextView mTextView4 = (TextView) view
					.findViewById(R.id.pjxqy_site3);
			TextView mTextView5 = (TextView) view
					.findViewById(R.id.pjxqy_price3);
			mTextView1.setText(ab_list.get(position).getPerson());
			mTextView2.setText(ab_list.get(position).getTime());
			mTextView3.setText(ab_list.get(position).getIntroduction());
			mTextView4.setText(ab_list.get(position).getTitle());
			mTextView5.setText(ab_list.get(position).getPrice());
			ImageView imageView1 = (ImageView) view
					.findViewById(R.id.pjxqy_headpic3);
			ImageView imageView2 = (ImageView) view
					.findViewById(R.id.pjxqy_pic3);
			ImageView imageView3 = (ImageView) view
					.findViewById(R.id.pjxqy_pic4);
			ImageView imageView4 = (ImageView) view
					.findViewById(R.id.pjxqy_pic5);
			ImageView imageView5 = (ImageView) view
					.findViewById(R.id.pjxqy_head6);
			ImageView imageView6 = (ImageView) view
					.findViewById(R.id.pjxqy_head7);
			ImageView imageView7 = (ImageView) view
					.findViewById(R.id.pjxqy_head8);
			ImageView imageView8 = (ImageView) view
					.findViewById(R.id.pjxqy_head9);
			ImageView imageView9 = (ImageView) view
					.findViewById(R.id.pjxqy_head10);

			imageView1.setBackgroundResource(Integer.parseInt(ab_list.get(
					position).getPic1()));
			imageView2.setBackgroundResource(Integer.parseInt(ab_list.get(
					position).getPic2()));
			imageView3.setBackgroundResource(Integer.parseInt(ab_list.get(
					position).getPic3()));
			imageView4.setBackgroundResource(Integer.parseInt(ab_list.get(
					position).getPic4()));
			imageView5.setBackgroundResource(Integer.parseInt(ab_list.get(
					position).getPic5()));
			imageView6.setBackgroundResource(Integer.parseInt(ab_list.get(
					position).getPic6()));
			imageView7.setBackgroundResource(Integer.parseInt(ab_list.get(
					position).getPic7()));
			imageView8.setBackgroundResource(Integer.parseInt(ab_list.get(
					position).getPic8()));
			imageView9.setBackgroundResource(Integer.parseInt(ab_list.get(
					position).getPic9()));
			return view;
		}

	}
}
