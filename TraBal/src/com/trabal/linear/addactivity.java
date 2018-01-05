package com.trabal.linear;

import java.io.ByteArrayOutputStream;
import java.io.File;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.trabal.MainActivity;
import com.trabal.MyDialog;
import com.trabal.MyDialog.OnButtonClickListener;
import com.trabal.R;
import com.trabal.user.Bean.UserBean;

public class addactivity extends Activity implements
OnButtonClickListener{
	private Button button, button1, button2, button3, button4, button5,
			button6, button7, button11, button12, button13, button14, button15,
			button16, button17, button18;
	private TextView TextView_result, TextView_result1, TextView_result2,
			TextView_result3, TextView_result4, TextView_result5,
			TextView_result6, TextView_result7;
	private ImageButton backTv;
	private ImageView poster;
	private UserBean user;
	private Button submit;
	private MyDialog myDialog;
    private final int IMAGE_OPEN = 4;
	public static final int PHOTOHRAPH = 1;
	public static final int NONE = 0;
    public static final int PHOTOZOOM = 2; // 缩放
    public static final int PHOTORESOULT = 3;// 结果
    public static final String IMAGE_UNSPECIFIED = "image/*";
	private Intent last_intent;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_addactivity);
		//海报
		poster = (ImageView)findViewById(R.id.posterID);
		//用戶傳輸
		last_intent = addactivity.this.getIntent();
		user = (UserBean) last_intent.getSerializableExtra("user");
		
		backTv = (ImageButton)findViewById(R.id.leftarrow2ID);
		button = (Button) findViewById(R.id.classifyID);
		button1 = (Button) findViewById(R.id.themeID);
		button2 = (Button) findViewById(R.id.timeID);
		button3 = (Button) findViewById(R.id.placeID);
		button4 = (Button) findViewById(R.id.introduceID);
		button5 = (Button) findViewById(R.id.priceID);
		button6 = (Button) findViewById(R.id.telephoneID);
		button7 = (Button) findViewById(R.id.officialID);
		button11 = (Button) findViewById(R.id.right7ID);
		button12 = (Button) findViewById(R.id.right3ID);
		button13 = (Button) findViewById(R.id.right5ID);
		button14 = (Button) findViewById(R.id.right4ID);
		button15 = (Button) findViewById(R.id.right2ID);
		button16 = (Button) findViewById(R.id.right6ID);
		button17 = (Button) findViewById(R.id.right1ID);
		button18 = (Button) findViewById(R.id.rightID);
		TextView_result = (TextView) findViewById(R.id.tx1ID);
		TextView_result1 = (TextView) findViewById(R.id.tx2ID);
		TextView_result2 = (TextView) findViewById(R.id.tx3ID);
		TextView_result3 = (TextView) findViewById(R.id.tx4ID);
		TextView_result4 = (TextView) findViewById(R.id.tx5ID);
		TextView_result5 = (TextView) findViewById(R.id.tx6ID);
		TextView_result6 = (TextView) findViewById(R.id.tx7ID);
		TextView_result7 = (TextView) findViewById(R.id.tx8ID);
		submit=(Button)this.findViewById(R.id.addactivityID);
		
		myDialog = new MyDialog(addactivity.this);
		myDialog.setOnButtonClickListener(this);
		
		poster.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				myDialog.show();
				
			}
		});
		
        backTv.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(addactivity.this,MainActivity.class);
				intent.putExtra("user", user);
				addactivity.this.startActivity(intent);
				finish();
				
			}
		});
		button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(addactivity.this,
						classifyactivity.class);
				intent.putExtra("user", user);
				addactivity.this.startActivityForResult(intent, 200);
			}
		});
		button11.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(addactivity.this,
						classifyactivity.class);
				intent.putExtra("user", user);
				addactivity.this.startActivityForResult(intent, 200);
			}
		});

		TextView_result7.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(addactivity.this,
						classifyactivity.class);
				intent.putExtra("user", user);
				addactivity.this.startActivityForResult(intent, 200);
			}
		});

		button1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(addactivity.this,
						themeactivity.class);
				intent.putExtra("user", user);
				addactivity.this.startActivityForResult(intent, 200);
			}
		});
		button12.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(addactivity.this,
						themeactivity.class);
				intent.putExtra("user", user);
				addactivity.this.startActivityForResult(intent, 200);
			}
		});

		TextView_result3.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(addactivity.this,
						themeactivity.class);
				intent.putExtra("user", user);
				addactivity.this.startActivityForResult(intent, 200);
			}
		});

		button2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(addactivity.this, timeactivity.class);
				intent.putExtra("user", user);
				addactivity.this.startActivityForResult(intent, 200);
			}
		});
		button13.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(addactivity.this, timeactivity.class);
				intent.putExtra("user", user);
				addactivity.this.startActivityForResult(intent, 200);
			}
		});

		TextView_result5.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(addactivity.this, timeactivity.class);
				intent.putExtra("user", user);
				addactivity.this.startActivityForResult(intent, 200);
			}
		});

		button3.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(addactivity.this,
						placeactivity.class);
				intent.putExtra("user", user);
				addactivity.this.startActivityForResult(intent, 200);
				finish();
			}
		});
		button14.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(addactivity.this,
						placeactivity.class);
				intent.putExtra("user", user);
				addactivity.this.startActivityForResult(intent, 200);
			}
		});
		TextView_result4.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(addactivity.this,
						placeactivity.class);
				intent.putExtra("user", user);
				addactivity.this.startActivityForResult(intent, 200);
			}
		});

		button4.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(addactivity.this,
						introduceactivity.class);
				intent.putExtra("user", user);
				addactivity.this.startActivityForResult(intent, 200);
			}
		});
		button15.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(addactivity.this,
						introduceactivity.class);
				intent.putExtra("user", user);
				addactivity.this.startActivityForResult(intent, 200);
			}
		});
		TextView_result2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(addactivity.this,
						introduceactivity.class);
				intent.putExtra("user", user);
				addactivity.this.startActivityForResult(intent, 200);
			}
		});

		button5.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(addactivity.this,
						priceactivity.class);
				intent.putExtra("user", user);
				addactivity.this.startActivityForResult(intent, 200);
			}
		});
		button16.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(addactivity.this,
						priceactivity.class);
				intent.putExtra("user", user);
				addactivity.this.startActivityForResult(intent, 200);
			}
		});
		TextView_result6.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(addactivity.this,
						priceactivity.class);
				intent.putExtra("user", user);
				addactivity.this.startActivityForResult(intent, 200);
			}
		});

		button6.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(addactivity.this,
						telephoneactivity.class);
				intent.putExtra("user", user);
				addactivity.this.startActivityForResult(intent, 200);
			}
		});
		button17.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(addactivity.this,
						telephoneactivity.class);
				intent.putExtra("user", user);
				addactivity.this.startActivityForResult(intent, 200);
			}
		});
		TextView_result1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(addactivity.this,
						telephoneactivity.class);
				intent.putExtra("user", user);
				addactivity.this.startActivityForResult(intent, 200);
			}
		});

		button7.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(addactivity.this,
						officialactivity.class);
				intent.putExtra("user", user);
				addactivity.this.startActivityForResult(intent, 200);
			}
		});
		button18.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(addactivity.this,
						officialactivity.class);
				intent.putExtra("user", user);
				addactivity.this.startActivityForResult(intent, 200);
			}
		});

		TextView_result.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(addactivity.this,
						officialactivity.class);
				intent.putExtra("user", user);
				addactivity.this.startActivityForResult(intent, 200);
			}
		});
	}
	

	
	//跳转系统相机
	@Override
	    public void camera() {                                                

	        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(
	                Environment.getExternalStorageDirectory(), "temp.jpg")));
	        startActivityForResult(intent, PHOTOHRAPH);
	        myDialog.dismiss();
	    }
	//跳转系统相册
	@Override
	    public void gallery() {                                                
	        Intent intent = new Intent(Intent.ACTION_PICK,
	                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
	        intent.setType("image/*");
	        startActivityForResult(intent, IMAGE_OPEN);
	        myDialog.dismiss();

	    }
	@Override
	    public void cancel() {
	        myDialog.cancel();
	    
	    
	    }

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		// 可以根据多个请求代码来作相应的操作
		if (20 == resultCode) {
			// "11111111111"+TextView_result+"===="+data.getExtras().getString("officialsite"));
			String officialsite = data.getExtras().getString("officialsite");

			TextView_result.setText(officialsite);

		}
		if (21 == resultCode) {
			String telephone = data.getExtras().getString("telephone");
			TextView_result1.setText(telephone);

		}
		if (22 == resultCode) {
			String introduce = data.getExtras().getString("introduce");
			TextView_result2.setText(introduce);

		}
		if (23 == resultCode) {
			String theme = data.getExtras().getString("theme");
			TextView_result3.setText(theme);

		}
		if (24 == resultCode) {
			String place = data.getExtras().getString("place");
			TextView_result4.setText(place);

		}
		if (25 == resultCode) {
			String time = data.getExtras().getString("time");
			TextView_result5.setText(time);

		}
		if (26 == resultCode) {
			String price = data.getExtras().getString("price");
			TextView_result6.setText(price);

		}
		if (37 == resultCode) {
			String free = data.getStringExtra("free");
			TextView_result6.setText(free);

		}
		if (27 == resultCode) {
			String xmas = data.getStringExtra("xmas");
			TextView_result7.setText(xmas);

		}
		if (28 == resultCode) {
			String exhibit = data.getStringExtra("exhibit");
			TextView_result7.setText(exhibit);

		}
		if (29 == resultCode) {
			String attention = data.getStringExtra("attention");
			TextView_result7.setText(attention);

		}
		if (30 == resultCode) {
			String music = data.getStringExtra("music");
			TextView_result7.setText(music);

		}
		if (31 == resultCode) {
			String opera = data.getStringExtra("opera");
			TextView_result7.setText(opera);

		}
		if (32 == resultCode) {
			String buy = data.getStringExtra("buy");
			TextView_result7.setText(buy);

		}
		if (33 == resultCode) {
			String casual = data.getStringExtra("casual");
			TextView_result7.setText(casual);

		}
		if (34 == resultCode) {
			String discount = data.getStringExtra("discount");
			TextView_result7.setText(discount);

		}
		if (35 == resultCode) {
			String cartoon = data.getStringExtra("cartoon");
			TextView_result7.setText(cartoon);

		}
		if (36 == resultCode) {
			String sport = data.getStringExtra("sport");
			TextView_result7.setText(sport);

		}

		  super.onActivityResult(requestCode, resultCode, data);

	        //获取图片路径
		  if (resultCode == NONE)
	            return;
	        // 拍照
	        if (requestCode == PHOTOHRAPH) {                                     //拍照
	            // 设置文件保存路径这里放在跟目录下
	            File picture = new File(Environment.getExternalStorageDirectory()
	                    + "/temp.jpg");
	            startImageZoom(Uri.fromFile(picture));
	          
	        }

	        if (data == null)
	            return;
	        
	        
	        if (requestCode == PHOTORESOULT) {                                  //返回结果
	            Bundle extras = data.getExtras();                               //获得intent放回的值
	            if (extras != null) {
	                Bitmap photo = extras.getParcelable("data");
	                ByteArrayOutputStream stream = new ByteArrayOutputStream(); //ByteArrayOutputStream 获取内存缓存区的数据
	                photo.compress(Bitmap.CompressFormat.JPEG, 100, stream);  
	                
	                ((ImageView)findViewById(R.id.posterID)).setImageBitmap(photo);    //加载图片到头像
	                
	                
//                    Uri uri = saveBitmap(photo, "temp");
//	                           //启动图像裁剪
//	                           startImageZoom(uri);
	                       }
	                   }
	        if (resultCode == RESULT_OK && requestCode == IMAGE_OPEN) {           //系统相册打开且选择了照片
	            startImageZoom(data.getData());                                     
	        }
      super.onActivityResult(requestCode, resultCode, data);   


	}

	public void startImageZoom(Uri uri) {                                                //剪切图片
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, IMAGE_UNSPECIFIED);
        Log.e("uri",uri.getPath());
      
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1.5);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 920);
        intent.putExtra("outputY", 240);
        intent.putExtra("return-data", true);
        
        startActivityForResult(intent, PHOTORESOULT);
    }
	

 

}
	