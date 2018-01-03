package com.trabal.linear;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SimpleAdapter.ViewBinder;

import com.trabal.MyDialog;
import com.trabal.R;
import com.trabal.MyDialog.OnButtonClickListener;
import com.trabal.linear.assessactivity.PostOnclick;

public class introduceactivity extends Activity implements
OnButtonClickListener, OnItemClickListener{
	private ImageButton button;
	private Button addpicture;
	private TextView sure;
	private EditText EditText_introduce;
	private Intent intent;
	private MyDialog dialog;
	public static final int NONE = 0;
    public static final int PHOTOHRAPH = 1;// 拍照
    public static final int PHOTOZOOM = 2; // 缩放
    public static final int PHOTORESOULT = 3;// 结果
    public static final String IMAGE_UNSPECIFIED = "image/*";
    private final int IMAGE_OPEN = 4; // 打开图片标记
    
    private GridView gridView; // 网格显示缩略图
    private String pathImage; // 选择图片路径
    private Bitmap bmp; // 导入临时图片
    private ArrayList<HashMap<String, Object>> imageItem;
    private SimpleAdapter simpleAdapter; // 适配器

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_introduce);

		intent = this.getIntent();
		EditText_introduce = (EditText) findViewById(R.id.ed6ID);
		button = (ImageButton) findViewById(R.id.leftarrow8ID);
		sure = (TextView) findViewById(R.id.sure2ID);
		addpicture = (Button) findViewById(R.id.addpictureID);
		button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(introduceactivity.this,
						addactivity.class);
				startActivity(intent);
			}
		});
		
		sure.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String str_introduce = EditText_introduce.getText().toString();
				// Intent data = new
				// Intent(officialactivity.this,addactivity.class);
				intent.putExtra("introduce", str_introduce);
				// 请求代码可以自己设置，这里设置成20
				setResult(22, intent);
				// 关闭掉这个Activity
				finish();
			}
		});
		
//		addpicture.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View arg0) {
//				// TODO Auto-generated method stub
//				dialog.show();
//			}
//		});
		 init();
	     initData();
	}
	
	private void init() {
        gridView = (GridView) findViewById(R.id.gridView1);

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
                R.drawable.w013);                            //   bitmap加载加号图片gridview_addpic
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
	
	//跳转系统相机
		@Override
		    public void camera() {                                                

		        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(
		                Environment.getExternalStorageDirectory(), "temp.jpg")));
		        startActivityForResult(intent, PHOTOHRAPH);
		        dialog.dismiss();
		    }
		//跳转系统相册
		@Override
		    public void gallery() {                                                
		        Intent intent = new Intent(Intent.ACTION_PICK,
		                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		        intent.setType("image/*");
		        startActivityForResult(intent, IMAGE_OPEN);
		        dialog.dismiss();

		    }
		@Override
		    public void cancel() {
		        dialog.cancel();
		    
            }
		
		 @Override
		    protected void onActivityResult(int requestCode, int resultCode, Intent data) {   //回调函数：新Activity关闭后 旧Activity得到
		        super.onActivityResult(requestCode, resultCode, data);

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
		                photo.compress(Bitmap.CompressFormat.JPEG, 100, stream);     // (0-100)压缩文件
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
//		                            hm.put("pic", value)
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
		            Toast.makeText(introduceactivity.this, "图片数3张已满",
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
		        AlertDialog.Builder builder = new Builder(introduceactivity.this);
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
		        intent.putExtra("aspectX", 1.5);
		        intent.putExtra("aspectY", 1);
		        // outputX outputY 是裁剪图片宽高
		        intent.putExtra("outputX", 480);
		        intent.putExtra("outputY", 480);
		        intent.putExtra("return-data", true);
		        
		        startActivityForResult(intent, PHOTORESOULT);
		    }
		    
		
}
