package com.trabal;

import java.util.ArrayList;

import com.trabal.linear.DynamicLinearLayout;
import com.trabal.linear.IndexLinearLayout;
import com.trabal.linear.MoreLinearLayout;

import android.os.Bundle;
import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Menu;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

	private ArrayList<LinearLayout> linears;
	private android.support.v4.view.ViewPager content;
	private  TextView indexTv,moreTv,dynamicTv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		linears = new ArrayList<LinearLayout>();
		linears.add(new IndexLinearLayout(MainActivity.this));
		linears.add(new MoreLinearLayout(MainActivity.this));
		linears.add(new DynamicLinearLayout(MainActivity.this));
		
		//获取内容组件
		content = (android.support.v4.view.ViewPager) this
				.findViewById(R.id.content);
		indexTv =(TextView)this.findViewById(R.id.indexID);
		moreTv =(TextView)this.findViewById(R.id.moreID);
		dynamicTv =(TextView)this.findViewById(R.id.dynamicID);
		
		indexTv.setTextColor(android.graphics.Color.BLUE);
		moreTv.setTextColor(android.graphics.Color.WHITE);
		dynamicTv.setTextColor(android.graphics.Color.WHITE);
				
				//设置适配器
		content.setAdapter(new CustomPager());
		//设置当前页面
		content.setCurrentItem(0);
		//设置内容组件事件处理
		content.setOnPageChangeListener(new CustomPagerChange());
		
		indexTv.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				content.setCurrentItem(0);
			indexTv.setTextColor(android.graphics.Color.BLUE);
			moreTv.setTextColor(android.graphics.Color.WHITE);
			dynamicTv.setTextColor(android.graphics.Color.WHITE);
			}
		});
		
        moreTv.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				content.setCurrentItem(1);
			indexTv.setTextColor(android.graphics.Color.WHITE);
			moreTv.setTextColor(android.graphics.Color.BLUE);
			dynamicTv.setTextColor(android.graphics.Color.WHITE);
				
			}
		});

        dynamicTv.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				content.setCurrentItem(2);
			indexTv.setTextColor(android.graphics.Color.WHITE);
			moreTv.setTextColor(android.graphics.Color.WHITE);
			dynamicTv.setTextColor(android.graphics.Color.BLUE);
				
			}
		});
        
      
	}

	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/**
	 * 内容组件适配器
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
	 * 内容事件处理
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
			if(arg0==0){
				indexTv.setTextColor(android.graphics.Color.BLUE);
				moreTv.setTextColor(android.graphics.Color.WHITE);
				dynamicTv.setTextColor(android.graphics.Color.WHITE);
				
			}else if (arg0==1) {
				indexTv.setTextColor(android.graphics.Color.WHITE);
				moreTv.setTextColor(android.graphics.Color.BLUE);
				dynamicTv.setTextColor(android.graphics.Color.WHITE);
			}else if(arg0==2){
				indexTv.setTextColor(android.graphics.Color.WHITE);
				moreTv.setTextColor(android.graphics.Color.WHITE);
				dynamicTv.setTextColor(android.graphics.Color.BLUE);
		}
	}

}
}
