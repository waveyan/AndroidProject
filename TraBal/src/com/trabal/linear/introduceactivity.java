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
    public static final int PHOTOHRAPH = 1;// ����
    public static final int PHOTOZOOM = 2; // ����
    public static final int PHOTORESOULT = 3;// ���
    public static final String IMAGE_UNSPECIFIED = "image/*";
    private final int IMAGE_OPEN = 4; // ��ͼƬ���
    
    private GridView gridView; // ������ʾ����ͼ
    private String pathImage; // ѡ��ͼƬ·��
    private Bitmap bmp; // ������ʱͼƬ
    private ArrayList<HashMap<String, Object>> imageItem;
    private SimpleAdapter simpleAdapter; // ������

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
				// �����������Լ����ã��������ó�20
				setResult(22, intent);
				// �رյ����Activity
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
        // activity�е�������activity������ķ���
        LayoutInflater layout = this.getLayoutInflater();
        View view = layout.inflate(R.layout.layout_select_photo, null);


    }
    private void initData() {
        /*
         * ����Ĭ��ͼƬ���ͼƬ�Ӻ�
         */
        bmp = BitmapFactory.decodeResource(getResources(), 
                R.drawable.w013);                            //   bitmap���ؼӺ�ͼƬgridview_addpic
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
	
	//��תϵͳ���
		@Override
		    public void camera() {                                                

		        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(
		                Environment.getExternalStorageDirectory(), "temp.jpg")));
		        startActivityForResult(intent, PHOTOHRAPH);
		        dialog.dismiss();
		    }
		//��תϵͳ���
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
		    protected void onActivityResult(int requestCode, int resultCode, Intent data) {   //�ص���������Activity�رպ� ��Activity�õ�
		        super.onActivityResult(requestCode, resultCode, data);

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
		                photo.compress(Bitmap.CompressFormat.JPEG, 100, stream);     // (0-100)ѹ���ļ�
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
//		                            hm.put("pic", value)
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
		            Toast.makeText(introduceactivity.this, "ͼƬ��3������",
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
		        AlertDialog.Builder builder = new Builder(introduceactivity.this);
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
		        intent.putExtra("aspectX", 1.5);
		        intent.putExtra("aspectY", 1);
		        // outputX outputY �ǲü�ͼƬ���
		        intent.putExtra("outputX", 480);
		        intent.putExtra("outputY", 480);
		        intent.putExtra("return-data", true);
		        
		        startActivityForResult(intent, PHOTORESOULT);
		    }
		    
		
}
