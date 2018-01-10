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
    private MyDialog dialog;// ͼƬѡ��Ի���
    public static final int NONE = 0;
    public static final int PHOTOHRAPH = 1;// ����
    public static final int PHOTOZOOM = 2; // ����
    public static final int PHOTORESOULT = 3;// ���
    public static final String IMAGE_UNSPECIFIED = "image/*";

    private GridView gridView; // ������ʾ����ͼ
    private final int IMAGE_OPEN = 4; // ��ͼƬ���
    private String pathImage; // ѡ��ͼƬ·��
    private Bitmap bmp; // ������ʱͼƬ
    private ArrayList<HashMap<String, Object>> imageItem;
    private SimpleAdapter simpleAdapter; // ������

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
         * ��ֹ���̵�ס����� ��ϣ���ڵ�����activity���� android:windowSoftInputMode="adjustPan"
         * ϣ����̬�����߶� android:windowSoftInputMode="adjustResize"
         */
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        // ������Ļ
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
		
		
		//ѡ��ص�
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
    	//��������
    	post=(TextView)this.findViewById(R.id.post);
    	post.setOnClickListener(new PostOnclick());    
    	
        gridView = (GridView) findViewById(R.id.gridView);
        gridView.setOnItemClickListener(this);
        dialog = new MyDialog(this);
        dialog.setOnButtonClickListener(this);
        // activity�е�������activity������ķ���
        LayoutInflater layout = this.getLayoutInflater();
        View view = layout.inflate(R.layout.layout_select_photo, null);


    }
    private void initData() {
        /*
         * ����Ĭ��ͼƬ���ͼƬ�Ӻ�
         */
        bmp = BitmapFactory.decodeResource(getResources(), 
                R.drawable.w012);                            //   bitmap���ؼӺ�ͼƬgridview_addpic
        imageItem = new ArrayList<HashMap<String, Object>>();           //   ��̬����
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("itemImage", bmp);
        imageItem.add(map);
        simpleAdapter = new SimpleAdapter(this, imageItem,
                R.layout.griditem_addpic, new String[] { "itemImage" },
                new int[] { R.id.imageView1 });
        simpleAdapter.setViewBinder(new ViewBinder() {
            @Override
            public boolean setViewValue(View view, Object data,         //  ��������������
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

//���dialog��ת
    @Override

    public void camera() {                                                //��תϵͳ���

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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {   //�ص���������Activity�رպ� ��Activity�õ�
        super.onActivityResult(requestCode, resultCode, data);
        
        
    	if (resultCode == 2000) {
			// ��ȡ���ص�����
			String backData = data.getStringExtra("position");
			//���ص�id
			positionID=data.getStringExtra("positionID");
			// ���ø�ҳ����ı�TextView��ʾ
			TextView_result.setText(backData);
		}

        if (resultCode == NONE)
            return;
        // ����
        if (requestCode == PHOTOHRAPH) {                                     //����
            // �����ļ�����·��������ڸ�Ŀ¼��
            File picture = new File(Environment.getExternalStorageDirectory()
                    + "/temp.jpg");
            startPhotoZoom(Uri.fromFile(picture));
        }

        if (data == null)
            return;

        // ������
        if (requestCode == PHOTORESOULT) {                                  //���ؽ��
            Bundle extras = data.getExtras();                               //���intent�Żص�ֵ
            if (extras != null) {
                Bitmap photo = extras.getParcelable("data");
                ByteArrayOutputStream stream = new ByteArrayOutputStream(); //ByteArrayOutputStream ��ȡ�ڴ滺����������
                photo.compress(Bitmap.CompressFormat.JPEG, 75, stream);     // (0-100)ѹ���ļ�
                // ��ͼƬ����gridview��
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
                simpleAdapter.notifyDataSetChanged();                        //���������ݸı�ʱ��̬ˢ���б�
                dialog.dismiss();
            }

        }
        // ��ͼƬ
        if (resultCode == RESULT_OK && requestCode == IMAGE_OPEN) {           //ϵͳ������ѡ������Ƭ
            startPhotoZoom(data.getData());                                     
        }
        super.onActivityResult(requestCode, resultCode, data);                //������Activity����

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!TextUtils.isEmpty(pathImage)) {                                   //�ж��ַ����Ƿ�Ϊ��
            Bitmap addbmp = BitmapFactory.decodeFile(pathImage);               //Bitmap��ȡͼƬ��Դ
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
            // ˢ�º��ͷŷ�ֹ�ֻ����ߺ��Զ����
            pathImage = null;
            dialog.dismiss();
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
        if (imageItem.size() == 4) { // ��һ��ΪĬ��ͼƬ
            Toast.makeText(assessactivity.this, "ͼƬ��3������",
                    Toast.LENGTH_SHORT).show();
        } else if (position == 0) { // ���ͼƬλ��Ϊ+ 0��Ӧ0��ͼƬ
            // ѡ��ͼƬ
            dialog.show();

            // ͨ��onResume()ˢ������
        } else {
            dialog(position);
        }

    }

    /*
     * Dialog�Ի�����ʾ�û�ɾ������ positionΪɾ��ͼƬλ��
     */
    protected void dialog(final int position) {                                       //����ӵ�ͼƬ����ɾ������
        AlertDialog.Builder builder = new Builder(assessactivity.this);
        builder.setMessage("ȷ���Ƴ������ͼƬ��");
        builder.setTitle("��ʾ");
        builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                imageItem.remove(position);
                simpleAdapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    public void startPhotoZoom(Uri uri) {                                                //����ͼƬ
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, IMAGE_UNSPECIFIED);
        Log.e("uri",uri.getPath());
        
        intent.putExtra("crop", "true");
        // aspectX aspectY �ǿ�ߵı���
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY �ǲü�ͼƬ���
        intent.putExtra("outputX", 480);
        intent.putExtra("outputY", 480);
        intent.putExtra("return-data", true);
        
        startActivityForResult(intent, PHOTORESOULT);
    }
    
    /**
     * ѹ��ͼƬ������ѹ����
     * @param bitmap
     */
    //bitmapתFile
    public static File compressImage(Bitmap bitmap,String filename) { 
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);//����ѹ������������100��ʾ��ѹ������ѹ��������ݴ�ŵ�baos��
        int options = 100;
        while (baos.toByteArray().length / 1024 > 500) {  //ѭ���ж����ѹ����ͼƬ�Ƿ����500kb,���ڼ���ѹ��
            baos.reset();//����baos�����baos
            options -= 10;//ÿ�ζ�����10
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);//����ѹ��options%����ѹ��������ݴ�ŵ�baos��
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
			
			//�ص㷢��
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

