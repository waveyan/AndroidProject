package com.trabal.linear;

//import com.trabal.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.RatingBar;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.SimpleAdapter.ViewBinder;
import android.widget.TextView;
import android.widget.Toast;

import com.trabal.LoginActivity;
import com.trabal.MainActivity;
import com.trabal.MyDialog;
import com.trabal.RegisterActivity;
import com.trabal.MyDialog.OnButtonClickListener;
import com.trabal.hotspot.Bean.HotSpotBean;
import com.trabal.user.Bean.UserBean;
import com.trabal.R;
import com.trabal.user.Bean.UserBean;
import com.trabal.util.net.FileUpload;
import com.trabal.util.net.NetTransfer;

public class assessactivity extends Activity implements OnButtonClickListener,
		OnItemClickListener {
	private RatingBar ratingbar;
    private MyDialog dialog;// 图片选择对话框
    public static final int NONE = 0;
    public static final int PHOTOHRAPH = 1;// 拍照
    public static final int PHOTOZOOM = 2; // 缩放
    public static final int PHOTORESOULT = 3;// 结果
    public static final String IMAGE_UNSPECIFIED = "image/*";

    private GridView gridView; // 网格显示缩略图
    private final int IMAGE_OPEN = 4; // 打开图片标记
    private String pathImage; // 选择图片路径
    private Bitmap bmp; // 导入临时图片
    private ArrayList<HashMap<String, Object>> imageItem;
    private SimpleAdapter simpleAdapter; // 适配器

    private TextView post,TextView_result ;
    private EditText textView_fell,textView_cost;
    private RatingBar rate;
    private ImageButton backTv;
	private UserBean user;
	private Button button;
	private String positionID;
	private Intent last_intent;
	private String didian;
	
   
	@Override
    protected void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        last_intent = assessactivity.this.getIntent();
		user = (UserBean) last_intent.getSerializableExtra("user");
		didian = (String) last_intent.getSerializableExtra("didian");
		
		  
    	
        /*
         * 防止键盘挡住输入框 不希望遮挡设置activity属性 android:windowSoftInputMode="adjustPan"
         * 希望动态调整高度 android:windowSoftInputMode="adjustResize"
         */
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        // 锁定屏幕
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_assess);
        
        backTv = (ImageButton)findViewById(R.id.leftarrow1ID);
        backTv.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				Intent last_intent = assessactivity.this.getIntent();
//				user = (UserBean) last_intent.getSerializableExtra("user");
//				Intent intent = new Intent(assessactivity.this,
//						MainActivity.class);
//				intent.putExtra("user", user);
//				assessactivity.this.startActivity(intent);
				assessactivity.this.finish();

			}
		});
        button = (Button) findViewById(R.id.positionID);
		TextView_result = (TextView) findViewById(R.id.txID);
		textView_fell = (EditText) findViewById(R.id.edID);
		textView_cost = (EditText) findViewById(R.id.ed1ID);
		rate = (RatingBar) findViewById(R.id.ratingbar);
		 TextView_result.setText(didian);
		
		
		

//		rate.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
//			
//			@Override
//			public void onRatingChanged(RatingBar arg0, float arg1, boolean arg2) {
//				// TODO Auto-generated method stub
//				
//			}
//		})
		
		
		//选择地点
		button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent last_intent = assessactivity.this.getIntent();
				user = (UserBean) last_intent.getSerializableExtra("user");
				Intent intent = new Intent(assessactivity.this,
						chosepositionactivity.class);
				intent.putExtra("user", user);
				assessactivity.this.startActivityForResult(intent, 2000);
			}
		});

		TextView_result.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent last_intent = assessactivity.this.getIntent();
				user = (UserBean) last_intent.getSerializableExtra("user");
				Intent intent = new Intent(assessactivity.this,
						chosepositionactivity.class);
				intent.putExtra("user", user);
				assessactivity.this.startActivityForResult(intent, 2000);
			}
		});
        
        init();
        initData();
        
   
    }


    private void init() {
    	//发布评论
    	post=(TextView)this.findViewById(R.id.post);
    	post.setOnClickListener(new PostOnclick());    
    	
        gridView = (GridView) findViewById(R.id.gridView);
        gridView.setOnItemClickListener(this);
        dialog = new MyDialog(this);
        dialog.setOnButtonClickListener(this);
        // activity中调用其他activity中组件的方法
        LayoutInflater layout = this.getLayoutInflater();
        View view = layout.inflate(R.layout.layout_select_photo, null);


    }
    private void initData() {
        /*
         * 载入默认图片添加图片加号
         */
        bmp = BitmapFactory.decodeResource(getResources(), 
                R.drawable.w012);                            //   bitmap加载加号图片gridview_addpic
        imageItem = new ArrayList<HashMap<String, Object>>();           //   动态数组
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("itemImage", bmp);
        imageItem.add(map);
        simpleAdapter = new SimpleAdapter(this, imageItem,
                R.layout.griditem_addpic, new String[] { "itemImage" },
                new int[] { R.id.imageView1 });
        simpleAdapter.setViewBinder(new ViewBinder() {
            @Override
            public boolean setViewValue(View view, Object data,         //  数据与适配器绑定
                    String textRepresentation) {
                if (view instanceof ImageView && data instanceof Bitmap) {
                    ImageView i = (ImageView) view;
                    i.setImageBitmap((Bitmap) data);
                    return true;
                }
                return false;
            }
        });
        gridView.setAdapter(simpleAdapter);
    }

//点击dialog跳转
    @Override

    public void camera() {                                                //跳转系统相机

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(
                Environment.getExternalStorageDirectory(), "temp.jpg")));
        startActivityForResult(intent, PHOTOHRAPH);
    }

    @Override
    public void gallery() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, IMAGE_OPEN);

    }

    @Override
    public void cancel() {
        dialog.cancel();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {   //回调函数：新Activity关闭后 旧Activity得到
        super.onActivityResult(requestCode, resultCode, data);
        
        
    	if (resultCode == 2000) {
			// 获取返回的数据
			String backData = data.getStringExtra("position");
			//返回的id
			positionID=data.getStringExtra("positionID");
			// 设置给页面的文本TextView显示
			TextView_result.setText(backData);
		}

        if (resultCode == NONE)
            return;
        // 拍照
        if (requestCode == PHOTOHRAPH) {                                     //拍照
            // 设置文件保存路径这里放在跟目录下
            File picture = new File(Environment.getExternalStorageDirectory()
                    + "/temp.jpg");
            startPhotoZoom(Uri.fromFile(picture));
        }

        if (data == null)
            return;

        // 处理结果
        if (requestCode == PHOTORESOULT) {                                  //返回结果
            Bundle extras = data.getExtras();                               //获得intent放回的值
            if (extras != null) {
                Bitmap photo = extras.getParcelable("data");
                ByteArrayOutputStream stream = new ByteArrayOutputStream(); //ByteArrayOutputStream 获取内存缓存区的数据
                photo.compress(Bitmap.CompressFormat.JPEG, 75, stream);     // (0-100)压缩文件
                // 将图片放入gridview中
                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("itemImage", photo);
                imageItem.add(map);
                simpleAdapter = new SimpleAdapter(this, imageItem,
                        R.layout.griditem_addpic, new String[] { "itemImage" },
                        new int[] { R.id.imageView1 });
                simpleAdapter.setViewBinder(new ViewBinder() {
                    @Override
                    public boolean setViewValue(View view, Object data,
                            String textRepresentation) {
                        // TODO Auto-generated method stub
                        if (view instanceof ImageView && data instanceof Bitmap) {
                            ImageView i = (ImageView) view;
                            Bitmap bm_data=(Bitmap) data;
                            HashMap<String,Object> hm=new HashMap<String, Object>();
//                            hm.put("pic", value)
                            i.setImageBitmap(bm_data);
                            return true;
                        }
                        return false;
                    }
                });
                gridView.setAdapter(simpleAdapter);
                simpleAdapter.notifyDataSetChanged();                        //适配器内容改变时动态刷新列表
                dialog.dismiss();
            }

        }
        // 打开图片
        if (resultCode == RESULT_OK && requestCode == IMAGE_OPEN) {           //系统相册打开且选择了照片
            startPhotoZoom(data.getData());                                     
        }
        super.onActivityResult(requestCode, resultCode, data);                //返回新Activity数据

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!TextUtils.isEmpty(pathImage)) {                                   //判断字符串是否为空
            Bitmap addbmp = BitmapFactory.decodeFile(pathImage);               //Bitmap获取图片资源
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("itemImage", addbmp);
            imageItem.add(map);
            simpleAdapter = new SimpleAdapter(this, imageItem,
                    R.layout.griditem_addpic, new String[] { "itemImage" },
                    new int[] { R.id.imageView1 });
            simpleAdapter.setViewBinder(new ViewBinder() {
                @Override
                public boolean setViewValue(View view, Object data,
                        String textRepresentation) {
                    if (view instanceof ImageView && data instanceof Bitmap) {
                        ImageView i = (ImageView) view;
                        i.setImageBitmap((Bitmap) data);
                        return true;
                    }
                    return false;
                }
            });
            gridView.setAdapter(simpleAdapter);
            simpleAdapter.notifyDataSetChanged();
            // 刷新后释放防止手机休眠后自动添加
            pathImage = null;
            dialog.dismiss();
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
        if (imageItem.size() == 4) { // 第一张为默认图片
            Toast.makeText(assessactivity.this, "图片数3张已满",
                    Toast.LENGTH_SHORT).show();
        } else if (position == 0) { // 点击图片位置为+ 0对应0张图片
            // 选择图片
            dialog.show();

            // 通过onResume()刷新数据
        } else {
            dialog(position);
        }

    }

    /*
     * Dialog对话框提示用户删除操作 position为删除图片位置
     */
    protected void dialog(final int position) {                                       //已添加的图片进行删除操作
        AlertDialog.Builder builder = new Builder(assessactivity.this);
        builder.setMessage("确认移除已添加图片吗？");
        builder.setTitle("提示");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                imageItem.remove(position);
                simpleAdapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    public void startPhotoZoom(Uri uri) {                                                //剪切图片
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
    
    /**
     * 压缩图片（质量压缩）
     * @param bitmap
     */
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
//        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
//        Date date = new Date(System.currentTimeMillis());
//        String filename = format.format(date);
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
    
    class PostOnclick implements View.OnClickListener{

		@Override
		public void onClick(View arg0) {
		    ArrayList<HashMap<String, Object>> every_pic=assessactivity.this.imageItem;
		    int i=0;
		    HashMap<String,Object> temp=new HashMap<String,Object>();
			if(every_pic.size()!=0){
				for(HashMap<String,Object> item : every_pic){
					i++;
					if (i == 1)
						continue;
					File f = assessactivity.compressImage(
							(Bitmap) item.get("itemImage"), String.valueOf(i));
					temp.put("pic" + String.valueOf(i - 1), f);
				}
			}
			String feel_text = textView_fell.getText().toString().trim();
			String cost_text =textView_cost.getText().toString().trim();
			String rate_text =rate.getRating()+"";
			
			//地点发布
			if(positionID==null)
				positionID=last_intent.getStringExtra("hotspot_id");

			HashMap<String,String> assess = new HashMap<String,String>();
			assess.put("feeling", feel_text);
			assess.put("hotspot_id", positionID);
			assess.put("price", cost_text);
			assess.put("rate", rate_text);
			
			String url="evaluation/base";
			NetTransfer nt = new NetTransfer();
			try {
				NetTransfer.upload_pic(url,assess, user.getAccess_token(), temp);
			
			} catch (Exception e) {
				e.printStackTrace();
			}
			Intent intent = new Intent(assessactivity.this,MainActivity.class);
			intent.putExtra("user", user);
			assessactivity.this.startActivity(intent);
			assessactivity.this.finish();
			
		}
    	
    }
    
}

