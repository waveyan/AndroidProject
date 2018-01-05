package com.trabal;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import com.squareup.picasso.Picasso;
import com.trabal.ContentAdapter;
import com.trabal.ContentModel;
import com.trabal.linear.DynamicLinearLayout;
import com.trabal.linear.IndexLinearLayout;
import com.trabal.linear.MoreLinearLayout;
import com.trabal.linear.addactivity;
import com.trabal.linear.XiaoxiActivity;
import com.trabal.linear.collectActivity;
import com.trabal.linear.haoyouActivity;
import com.trabal.linear.huodongActivity;
import com.trabal.linear.luxianActivity;
import com.trabal.linear.pingjiaActivity;
import com.trabal.linear.recommendationactivity;
import com.trabal.routeplan.RouteplanActivity1;
import com.trabal.routeplan.RouteplanActivity2;
import com.trabal.user.Bean.UserBean;
import com.trabal.util.net.NetTransfer;
import com.trabal.R;
import org.apache.http.message.BasicNameValuePair;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ext.SatelliteMenu.SateliteClickedListener;
import android.view.ext.SatelliteMenuItem;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.trabal.MyDialog.OnButtonClickListener;
import com.trabal.MyDialog;
import com.trabal.linear.DynamicLinearLayout;
import com.trabal.linear.IndexLinearLayout;
import com.trabal.linear.MoreLinearLayout;
import com.trabal.linear.addactivity;
import com.trabal.linear.assessactivity;
import com.trabal.linear.collectActivity;
import com.trabal.linear.haoyouActivity;
import com.trabal.linear.huodongActivity;
import com.trabal.linear.luxianActivity;
import com.trabal.linear.pingjiaActivity;
import com.trabal.linear.recommendationactivity;
import com.trabal.user.Bean.UserBean;
import com.trabal.util.net.NetTransfer;

public class MainActivity extends Activity implements OnButtonClickListener{

	private ArrayList<LinearLayout> linears;
	private android.support.v4.view.ViewPager content;
	private TextView indexTv, moreTv, dynamicTv, rightname;
	private DrawerLayout drawerLayout;
	private RelativeLayout rightLayout;
	private List<ContentModel> list;
	private ContentAdapter adapter;
	private ImageButton imageButton,imageButton1;
	private ListView listView;
	private ImageView p_pic;
	public UserBean user;
	Intent last_intent;
	private MyDialog myDialog;
    private final int IMAGE_OPEN = 4;
	public static final int PHOTOHRAPH = 1;
	public static final int NONE = 0;
    public static final int PHOTOZOOM = 2; // 缩放
    public static final int PHOTORESOULT = 3;// 结果
    public static final String IMAGE_UNSPECIFIED = "image/*";
    private String pathImage="headpic";
    private TextView district;
    
    
    

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// 显示用户头像和昵称
		last_intent = MainActivity.this.getIntent();
		user = (UserBean) last_intent.getSerializableExtra("user");       

		linears = new ArrayList<LinearLayout>();
		linears.add(new IndexLinearLayout(MainActivity.this));
		linears.add(new MoreLinearLayout(MainActivity.this));
		linears.add(new DynamicLinearLayout(MainActivity.this));
		getActionBar().hide();

		imageButton = (ImageButton) findViewById(R.id.personID);
		drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
		rightLayout = (RelativeLayout) findViewById(R.id.right);
		listView = (ListView) findViewById(R.id.right_listview);
		
		// 获取内容组件
		content = (android.support.v4.view.ViewPager) this
				.findViewById(R.id.content);
		indexTv = (TextView) this.findViewById(R.id.indexID);
		moreTv = (TextView) this.findViewById(R.id.moreID);
		dynamicTv = (TextView) this.findViewById(R.id.dynamicID);
		//地域
		district=(TextView)this.findViewById(R.id.district);
		district.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent=new Intent(MainActivity.this,RouteplanActivity1.class);
				intent.putExtra("user", user);
				MainActivity.this.startActivity(intent);
			}
		});
		
		
		myDialog = new MyDialog(this);
		myDialog.setOnButtonClickListener(this);
	
   
		// 卫星菜单动态实现

		android.view.ext.SatelliteMenu menu = (android.view.ext.SatelliteMenu) this
				.findViewById(R.id.menu);

		float distance = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				120, getResources().getDisplayMetrics());
		menu.setSatelliteDistance((int) distance);
		menu.setExpandDuration(600);
		menu.setCloseItemsOnClick(true);
		menu.setTotalSpacingDegree(90);
		menu.setMainImage(R.drawable.a018);
		
		List<SatelliteMenuItem> items = new ArrayList<SatelliteMenuItem>();
		items.add(new SatelliteMenuItem(1, R.drawable.a014));
		items.add(new SatelliteMenuItem(2, R.drawable.a015));
		items.add(new SatelliteMenuItem(3, R.drawable.a016));
		items.add(new SatelliteMenuItem(4, R.drawable.a017));
		menu.addItems(items);

		menu.setOnItemClickedListener(new SateliteClickedListener() {

			@Override
			public void eventOccured(int id) {

				switch ((int) id) {
				case 1:
					new Thread(new JumpPageThread1()).start();
					break;
					
				case 2:
					new Thread(new JumpPageThread2()).start();
					break;
					
				case 3:
					new Thread(new JumpPageThread3()).start();
					break;
					
				case 4:
					new Thread(new JumpPageThread4()).start();
					break;

				default:
					break;
				}

			}

		});

		// 设置适配器
		content.setAdapter(new CustomPager());
		// 设置当前页面
		if("more".equals(last_intent.getStringExtra("from"))){
			indexTv.setTextColor(android.graphics.Color.BLACK);
			moreTv.setTextColor(android.graphics.Color.CYAN);
			dynamicTv.setTextColor(android.graphics.Color.BLACK);
			content.setCurrentItem(1);
		}
		else{
			indexTv.setTextColor(android.graphics.Color.CYAN);
			moreTv.setTextColor(android.graphics.Color.BLACK);
			dynamicTv.setTextColor(android.graphics.Color.BLACK);
			content.setCurrentItem(0);
		}
		// 设置内容组件事件处理
		content.setOnPageChangeListener(new CustomPagerChange());

		initData();
		adapter = new ContentAdapter(this, list);
		listView.setAdapter(adapter);
		imageButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				drawerLayout.openDrawer(Gravity.RIGHT);


				// 网络传输获取用户头像和昵称
				String url = "user/base";
				NetTransfer nt = new NetTransfer();
				String data;
				try {
					data = NetTransfer.transfer(url, "get", null, true,
							user.getAccess_token(),null);
					user = nt.handle_user_data(data, user);
					// 设置远程头像
					p_pic = (ImageView) MainActivity.this
							.findViewById(R.id.p_pic);
					Picasso.with(MainActivity.this).load(user.getPic()).into(p_pic);
					// 设置昵称
					rightname = (TextView) MainActivity.this
							.findViewById(R.id.right_name);
					rightname.setText(user.getName());
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		});
		indexTv.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				content.setCurrentItem(0);
				indexTv.setTextColor(android.graphics.Color.CYAN);
				moreTv.setTextColor(android.graphics.Color.BLACK);
				dynamicTv.setTextColor(android.graphics.Color.BLACK);
			}
		});

		moreTv.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				content.setCurrentItem(1);
				indexTv.setTextColor(android.graphics.Color.BLACK);
				moreTv.setTextColor(android.graphics.Color.CYAN);
				dynamicTv.setTextColor(android.graphics.Color.BLACK);

			}
		});

		dynamicTv.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
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
				case 0:
					myDialog.show();
					break;
				case 1:
					Intent intent = new Intent(MainActivity.this,
							pingjiaActivity.class);
					intent.putExtra("user", user);
					MainActivity.this.startActivity(intent);
//					MainActivity.this.finish();
					break;
				case 2:
					Intent intent1 = new Intent(MainActivity.this,
							collectActivity.class);
					intent1.putExtra("user", user);
					intent1.putExtra("from", "main");
					MainActivity.this.startActivity(intent1);
//					MainActivity.this.finish();
					break;
				case 3:
					Intent intent2 = new Intent(MainActivity.this,
							luxianActivity.class);
					intent2.putExtra("user", user);
					MainActivity.this.startActivity(intent2);
//					MainActivity.this.finish();
					break;
				case 4:
					Intent intent3 = new Intent(MainActivity.this,
							huodongActivity.class);
					intent3.putExtra("user", user);
					MainActivity.this.startActivity(intent3);
//					MainActivity.this.finish();
					break;
				case 5:
					Intent intent4 = new Intent(MainActivity.this,
							haoyouActivity.class);
					intent4.putExtra("user", user);
					MainActivity.this.startActivity(intent4);
//					MainActivity.this.finish();
					break;
				
				default:
					break;
				}
				drawerLayout.closeDrawer(Gravity.RIGHT);
			}
		});
		
		//MessageID
		imageButton1=(ImageButton)findViewById(R.id.messageID);
		imageButton1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent=new Intent(MainActivity.this,XiaoxiActivity.class);
				intent.putExtra("user", user);
				MainActivity.this.startActivity(intent);
				MainActivity.this.finish();
				
			}
		});
	}

	private void initData() {
		list = new ArrayList<ContentModel>();
		list.add(new ContentModel(R.drawable.pingjia, "修改头像", 0));
		list.add(new ContentModel(R.drawable.pingjia, "我的评价", 1));
		list.add(new ContentModel(R.drawable.shoucang, "我的收藏", 2));
		list.add(new ContentModel(R.drawable.luxian, "我的路线", 3));
		list.add(new ContentModel(R.drawable.huodong, "历史活动", 4));
		list.add(new ContentModel(R.drawable.haoyou, "我的好友", 5));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
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
		                HashMap<String, Object> map = new HashMap<String, Object>();
		                File f1 = MainActivity.compressImage(photo,pathImage);
		                map.put("pic", f1);
		                HashMap<String, String> data1 = new HashMap<String, String>();
		                data1.put("action", "alter");
		                ((ImageView)findViewById(R.id.p_pic)).setImageBitmap(photo);    //加载图片到头像
		                String url = "user/base";
		               try {
						NetTransfer.upload_pic(url, data1, user.getAccess_token(), map);
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
//	                    Uri uri = saveBitmap(photo, "temp");
//		                           //启动图像裁剪
//		                           startImageZoom(uri);
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
	        intent.putExtra("aspectX", 1);
	        intent.putExtra("aspectY", 1);
	        // outputX outputY 是裁剪图片宽高
	        intent.putExtra("outputX", 480);
	        intent.putExtra("outputY", 480);
	        intent.putExtra("return-data", true);
	        
	        startActivityForResult(intent, PHOTORESOULT);
	    }
		
		
		 //bitmap转File
	    public static File compressImage(Bitmap bitmap,String filename) { 
	        ByteArrayOutputStream baos = new ByteArrayOutputStream();
	        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
	        int options = 100;
	        while (baos.toByteArray().length / 1024 > 500) {  //循环判断如果压缩后图片是否大于500kb,大于继续压缩
	            baos.reset();//重置baos即清空baos
	            options -= 10;//每次都减少10
	            bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
	            long length = baos.toByteArray().length;
	        }
//	        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
//	        Date date = new Date(System.currentTimeMillis());
//	        String filename = format.format(date);
	        File file = new File(Environment.getExternalStorageDirectory(),filename+".png");
	        try {
	            FileOutputStream fos = new FileOutputStream(file);
	            try {
	                fos.write(baos.toByteArray());
	                fos.flush();
	                fos.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        }
	        return file;
	    }
		

	// @Override
	// public boolean onCreateOptionsMenu(Menu menu) {
	// getMenuInflater().inflate(R.menu.main, menu);
	// return true;
	// }

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

	// 卫星菜单1 延迟2秒后跳转
	private class JumpPageThread1 implements Runnable {

		@Override
		public void run() {
			try {
				Thread.sleep(650);
			} catch (Exception ex) {
				ex.printStackTrace();
			}

			Intent intent = new Intent(MainActivity.this, assessactivity.class);
			intent.putExtra("user", user);
			MainActivity.this.startActivity(intent);

		}

	}
	// 卫星菜单2 延迟2秒后跳转
	private class JumpPageThread2 implements Runnable {

		@Override
		public void run() {
			try {
				Thread.sleep(650);
			} catch (Exception ex) {
				ex.printStackTrace();
			}

			Intent intent = new Intent(MainActivity.this, RouteplanActivity1.class);
			intent.putExtra("user", user);
			
			MainActivity.this.startActivity(intent);

		}

	}
	// 卫星菜单3 延迟2秒后跳转
	private class JumpPageThread3 implements Runnable {

		@Override
		public void run() {
			try {
				Thread.sleep(650);
			} catch (Exception ex) {
				ex.printStackTrace();
			}

			Intent intent = new Intent(MainActivity.this, recommendationactivity.class);
			intent.putExtra("user", user);
			MainActivity.this.startActivity(intent);

		}

	}
	// 卫星菜单4 延迟2秒后跳转
	private class JumpPageThread4 implements Runnable {

		@Override
		public void run() {
			try {
				Thread.sleep(650);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			Intent intent = new Intent(MainActivity.this, addactivity.class);
			intent.putExtra("user", user);
			MainActivity.this.startActivity(intent);

		}

	}
}
