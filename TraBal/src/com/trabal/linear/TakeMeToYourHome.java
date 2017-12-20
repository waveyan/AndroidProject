package com.trabal.linear;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import org.apache.http.message.BasicNameValuePair;

import com.trabal.LoginActivity;
import com.trabal.R;
import com.trabal.hotspot.Bean.HotSpotBean;
import com.trabal.user.Bean.UserBean;
import com.trabal.util.net.NetTransfer;
import com.trabal.util.net.ImageDownloadTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class TakeMeToYourHome extends Activity {
	
	private ImageView pic1,pic2,pic3,pic4;
	
	private ImageButton favourButton;
	
	private TextView englishname,chinaname,introduction,openTimes,cost,address,hs_telephone,content,pic1_text,pic2_text,pic3_text;
	
	private HotSpotBean hsb;
	
	private UserBean user;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_takemetoyourhome);
		
		// 网络传输
		ArrayList params = new ArrayList();
		params.add(new BasicNameValuePair("id", "1"));
		String url="hotspot/base";
		try {
			Intent intent=TakeMeToYourHome.this.getIntent();
			user=(UserBean)intent.getSerializableExtra("user");
			String data=NetTransfer.transfer(url, "get",params , true,user.getAccess_token());
			NetTransfer nt=new NetTransfer();
			hsb=nt.handle_HS_data(data);
			initView();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void initView(){
		//初始化数据
		pic1=(ImageView)this.findViewById(R.id.pic1);
		pic2=(ImageView)this.findViewById(R.id.pic2);
		pic3=(ImageView)this.findViewById(R.id.pic3);
		pic4=(ImageView)this.findViewById(R.id.pic4);
		englishname=(TextView)this.findViewById(R.id.EnglishName);
		chinaname=(TextView)this.findViewById(R.id.ChinaName);
		introduction=(TextView)this.findViewById(R.id.Introduction);
		openTimes=(TextView)this.findViewById(R.id.OpenTimes);
		cost=(TextView)this.findViewById(R.id.Cost);
		address=(TextView)this.findViewById(R.id.Address);
		hs_telephone=(TextView)this.findViewById(R.id.hs_telephone);
		content=(TextView)this.findViewById(R.id.content);
		pic1_text=(TextView)this.findViewById(R.id.pic1_text);
		pic2_text=(TextView)this.findViewById(R.id.pic2_text);
		pic3_text=(TextView)this.findViewById(R.id.pic3_text);
		new ImageDownloadTask(pic1).execute(hsb.getPic1());
		new ImageDownloadTask(pic2).execute(hsb.getPic2());
		new ImageDownloadTask(pic3).execute(hsb.getPic3());
		new ImageDownloadTask(pic4).execute(hsb.getPic2());
		englishname.setText(hsb.getEnglishName());
		chinaname.setText(hsb.getName());
		introduction.setText(hsb.getWord());
		openTimes.setText(hsb.getWorktime());
		cost.setText(String.valueOf(hsb.getPrice()));
		address.setText(hsb.getAddress());
		hs_telephone.setText(hsb.getTelephone());
		content.setText(hsb.getContent());
		pic1_text.setText(hsb.getPic1_text());
		pic2_text.setText(hsb.getPic2_text());
		pic3_text.setText(hsb.getPic3_text());
		favourButton=(ImageButton)this.findViewById(R.id.favourButton);
		if(hsb.getIsfavour()==1){
			favourButton.setBackgroundResource(R.drawable.favour_clicked);
		}
		//绑定收藏事件
		favourButton.setOnClickListener(new FavourClick());
	}
	
	//收藏事件
	private class FavourClick implements View.OnClickListener{

		@Override
		public void onClick(View arg0) {
			// 网络传输
			ArrayList params = new ArrayList();
			params.add(new BasicNameValuePair("hotspot_id", "1"));
			params.add(new BasicNameValuePair("action","favour_hs"));
			String url="user/base";
			try {
				String data=NetTransfer.transfer(url, "put",params , true,user.getAccess_token());
				NetTransfer nt=new NetTransfer();
				nt.return_data(data);
				if("success_favour".equals(nt.getStatus())){
					TakeMeToYourHome.this.favourButton.setBackgroundResource(R.drawable.favour_clicked);

				}
				else if("success_unfavour".equals(nt.getStatus())){
					TakeMeToYourHome.this.favourButton.setBackgroundResource(R.drawable.favour);
				}
				Toast.makeText(TakeMeToYourHome.this, nt.getMsg(),
						Toast.LENGTH_LONG).show();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
		}
		
	}
}
