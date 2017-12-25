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
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.SimpleAdapter.ViewBinder;
import android.widget.TextView;
import android.widget.Toast;

import com.trabal.MyDialog;
import com.trabal.MyDialog.OnButtonClickListener;
import com.trabal.R;
import com.trabal.util.net.FileUpload;
import com.trabal.util.net.NetTransfer;


public class assessactivity extends Activity implements
OnButtonClickListener, OnItemClickListener{
	private RatingBar ratingbar;
	private ImageButton addID;
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
    private TextView post;
    private RatingBar rate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    	
        /*
         * ��ֹ���̵�ס����� ��ϣ���ڵ�����activity���� android:windowSoftInputMode="adjustPan"
         * ϣ����̬�����߶� android:windowSoftInputMode="adjustResize"
         */
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        // ������Ļ
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_assess);
        init();
        initData();
    }

    private void init() {
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
                R.drawable.gridview_addpic); // �Ӻ�
        imageItem = new ArrayList<HashMap<String, Object>>();
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("itemImage", bmp);
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
    }

//���dialog��ת
    @Override
    public void camera() {
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == NONE)
            return;
        // ����
        if (requestCode == PHOTOHRAPH) {
            // �����ļ�����·��������ڸ�Ŀ¼��
            File picture = new File(Environment.getExternalStorageDirectory()
                    + "/temp.jpg");
            startPhotoZoom(Uri.fromFile(picture));
        }

        if (data == null)
            return;

        // ������
        if (requestCode == PHOTORESOULT) {
            Bundle extras = data.getExtras();
            if (extras != null) {
                Bitmap photo = extras.getParcelable("data");
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                photo.compress(Bitmap.CompressFormat.JPEG, 75, stream);// (0-100)ѹ���ļ�
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
                simpleAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }

        }
        // ��ͼƬ
        if (resultCode == RESULT_OK && requestCode == IMAGE_OPEN) {
            startPhotoZoom(data.getData());
        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!TextUtils.isEmpty(pathImage)) {
        	//
        	Log.e("pathImage",pathImage);
            Bitmap addbmp = BitmapFactory.decodeFile(pathImage);
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
    protected void dialog(final int position) {
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

    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, IMAGE_UNSPECIFIED);
        Log.e("uri",uri.getPath());
        
        intent.putExtra("crop", "true");
        // aspectX aspectY �ǿ�ߵı���
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY �ǲü�ͼƬ���
        intent.putExtra("outputX", 64);
        intent.putExtra("outputY", 64);
        intent.putExtra("return-data", true);
        
        startActivityForResult(intent, PHOTORESOULT);
    }
    
    /**
     * ѹ��ͼƬ������ѹ����
     * @param bitmap
     */
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
			if(every_pic.size()!=0){
				HashMap<String,Object> temp=new HashMap<String,Object>();
				for(HashMap<String,Object> item : every_pic){
					i++;
					if(i==1)
						continue;
					File f=assessactivity.compressImage((Bitmap)item.get("itemImage"),String.valueOf(i));				
					temp.put("pic"+String.valueOf(i-1), f);
				}
//				// test upload file
//				 String url="activity/base";
//				 ArrayList<BasicNameValuePair> nv_list=new ArrayList<BasicNameValuePair>();
//				 nv_list.add(new BasicNameValuePair("subject","����CSҰս"));
//				 nv_list.add(new BasicNameValuePair("title","����CSҰս"));
//				 nv_list.add(new BasicNameValuePair("time","2013-09-05 12:46:57"));
//				 nv_list.add(new BasicNameValuePair("type","����CSҰս"));
//				 nv_list.add(new BasicNameValuePair("introduction","����CSҰս"));
//				 nv_list.add(new BasicNameValuePair("person","1"));
//				 nv_list.add(new BasicNameValuePair("telephone","����CSҰս"));
//				 nv_list.add(new BasicNameValuePair("website","����CSҰս"));
//				 nv_list.add(new BasicNameValuePair("price","11"));
//				 try {
//				 NetTransfer.transfer(url, "post", nv_list, true,
//				 "8e7c2373b74341b399da25c537bc8513", temp);
//				 } catch (IOException e) {
//				 Log.e("ssssssssss",e.getMessage());
//				 e.printStackTrace();
//				 }
			}
			
		}
    	
    }
    

}



