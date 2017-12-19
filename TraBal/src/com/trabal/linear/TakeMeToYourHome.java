package com.trabal.linear;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import org.apache.http.message.BasicNameValuePair;

import com.trabal.LoginActivity;
import com.trabal.R;
import com.trabal.hotspot.Bean.HotSpotBean;
import com.trabal.util.net.NetTransfer;
import com.trabal.util.net.ImageDownloadTask;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class TakeMeToYourHome extends Activity {
	
	private ImageView pic1,pic2,pic3,pic4;
	
	private TextView englishname,chinaname,introduction,openTimes,cost,address,hs_telephone,content,pic1_text,pic2_text,pic3_text;
	
	private HotSpotBean hsb;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_takemetoyourhome);
		
		// ÍøÂç´«Êä
		ArrayList params = new ArrayList();
		params.add(new BasicNameValuePair("id", "1"));
		String url="hotspot/base";
		try {
			String data=NetTransfer.transfer(url, "get",params , true);
			NetTransfer nt=new NetTransfer();
			hsb=nt.handle_HS_data(data);
			initView();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void initView(){
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
		
	}
}
