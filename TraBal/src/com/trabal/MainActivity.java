package com.trabal;

import java.util.ArrayList;
import java.util.List;


import com.trabal.ContentAdapter;
import com.trabal.ContentModel;
import com.trabal.linear.DynamicLinearLayout;
import com.trabal.linear.IndexLinearLayout;
import com.trabal.linear.MoreLinearLayout;
import com.trabal.linear.haoyouActivity;
import com.trabal.linear.huodongActivity;
import com.trabal.linear.luxianActivity;
import com.trabal.linear.pingjiaActivity;
import com.trabal.linear.shoucangActivity;
import com.trabal.R;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends Activity {

	private ArrayList<LinearLayout> linears;
	private android.support.v4.view.ViewPager content;
	private TextView indexTv, moreTv, dynamicTv;
	private DrawerLayout drawerLayout;
	private RelativeLayout rightLayout;
	private List<ContentModel> list;
	private ContentAdapter adapter;
	private ImageButton imageButton;
	private ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		linears = new ArrayList<LinearLayout>();
		linears.add(new IndexLinearLayout(MainActivity.this));
		linears.add(new MoreLinearLayout(MainActivity.this));
		linears.add(new DynamicLinearLayout(MainActivity.this));
		getActionBar().hide();

		imageButton = (ImageButton) findViewById(R.id.personID);
		drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
		rightLayout = (RelativeLayout) findViewById(R.id.right);
		listView = (ListView) findViewById(R.id.right_listview);
		// ��ȡ�������
		content = (android.support.v4.view.ViewPager) this
				.findViewById(R.id.content);
		indexTv = (TextView) this.findViewById(R.id.indexID);
		moreTv = (TextView) this.findViewById(R.id.moreID);
		dynamicTv = (TextView) this.findViewById(R.id.dynamicID);

		indexTv.setTextColor(android.graphics.Color.CYAN);
		moreTv.setTextColor(android.graphics.Color.BLACK);
		dynamicTv.setTextColor(android.graphics.Color.BLACK);

		// ����������
		content.setAdapter(new CustomPager());
		// ���õ�ǰҳ��
		content.setCurrentItem(0);
		// ������������¼�����
		content.setOnPageChangeListener(new CustomPagerChange());

		initData();
		adapter = new ContentAdapter(this, list);
		listView.setAdapter(adapter);
		imageButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				drawerLayout.openDrawer(Gravity.RIGHT);
			}
		});
		indexTv.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				content.setCurrentItem(0);
				indexTv.setTextColor(android.graphics.Color.CYAN);
				moreTv.setTextColor(android.graphics.Color.BLACK);
				dynamicTv.setTextColor(android.graphics.Color.BLACK);
			}
		});

		moreTv.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				content.setCurrentItem(1);
				indexTv.setTextColor(android.graphics.Color.BLACK);
				moreTv.setTextColor(android.graphics.Color.CYAN);
				dynamicTv.setTextColor(android.graphics.Color.BLACK);

			}
		});

		dynamicTv.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				content.setCurrentItem(2);
				indexTv.setTextColor(android.graphics.Color.BLACK);
				moreTv.setTextColor(android.graphics.Color.BLACK);
				dynamicTv.setTextColor(android.graphics.Color.CYAN);

			}
		});
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				switch ((int) id) {
				case 1:
					Intent intent = new Intent(MainActivity.this,pingjiaActivity.class);
					MainActivity.this.startActivity(intent);
					break;
				case 2:
					Intent intent1 = new Intent(MainActivity.this,shoucangActivity.class);
					MainActivity.this.startActivity(intent1);
					break;
				case 3:
					Intent intent2 = new Intent(MainActivity.this,luxianActivity.class);
					MainActivity.this.startActivity(intent2);
					break;
				case 4:
					Intent intent3 = new Intent(MainActivity.this,huodongActivity.class);
					MainActivity.this.startActivity(intent3);
					break;
				case 5:
					Intent intent4 = new Intent(MainActivity.this,haoyouActivity.class);
					MainActivity.this.startActivity(intent4);
					break;
				default:
					break;
				}
				drawerLayout.closeDrawer(Gravity.RIGHT);
			}
		});
	}

	private void initData() {
		// TODO Auto-generated method stub
		list = new ArrayList<ContentModel>();

		list.add(new ContentModel(R.drawable.pingjia, "�ҵ�����", 1));
		list.add(new ContentModel(R.drawable.shoucang, "�ҵ��ղ�", 2));
		list.add(new ContentModel(R.drawable.luxian, "�ҵ�·��", 3));
		list.add(new ContentModel(R.drawable.huodong, "��ʷ�", 4));
		list.add(new ContentModel(R.drawable.haoyou, "�ҵĺ���", 5));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/**
	 * �������������
	 */
	private class CustomPager extends PagerAdapter {

		@Override
		public int getCount() {
			return linears.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView(linears.get(position));
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {

			container.addView(linears.get(position));
			return linears.get(position);

		}

	}

	/**
	 * 
	 * �����¼�����
	 * 
	 */
	private class CustomPagerChange implements OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int arg0) {
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageSelected(int arg0) {
			if (arg0 == 0) {
				indexTv.setTextColor(android.graphics.Color.CYAN);
				moreTv.setTextColor(android.graphics.Color.BLACK);
				dynamicTv.setTextColor(android.graphics.Color.BLACK);

			} else if (arg0 == 1) {
				indexTv.setTextColor(android.graphics.Color.BLACK);
				moreTv.setTextColor(android.graphics.Color.CYAN);
				dynamicTv.setTextColor(android.graphics.Color.BLACK);
			} else if (arg0 == 2) {
				indexTv.setTextColor(android.graphics.Color.BLACK);
				moreTv.setTextColor(android.graphics.Color.BLACK);
				dynamicTv.setTextColor(android.graphics.Color.CYAN);
			}
		}

	}
}
