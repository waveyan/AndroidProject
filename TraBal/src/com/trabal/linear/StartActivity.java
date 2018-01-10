package com.trabal.linear;

import com.trabal.LoginActivity;
import com.trabal.MainActivity;
import com.trabal.R;
import com.trabal.user.Bean.UserBean;
import com.trabal.user.Dao.UserDao;
import com.trabal.util.SharePreferencesTool;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.Toast;

public class StartActivity extends Activity {

	private ImageView start;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ���ر�����
		supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
		// ����״̬��
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_start);
		initImage();
	}

	private void supportRequestWindowFeature(int featureNoTitle) {

	}

	private void initImage() {
		start = (ImageView) findViewById(R.id.start);
		start.setImageResource(R.drawable.start2);
		// �������Ŷ���
		ScaleAnimation scaleAnimation = new ScaleAnimation(1.4f, 1.0f, 1.4f,
				1.0f, Animation.RELATIVE_TO_SELF, 0.5f,
				Animation.RELATIVE_TO_SELF, 0.5f);
		scaleAnimation.setDuration(4000);
		// ����������ɺ󱣳���״
		scaleAnimation.setFillAfter(true);
		scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
				// �����������Ƚ���ĳЩ����
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				startActivity();
			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}
		});
		start.startAnimation(scaleAnimation);
	}

	private void startActivity() {
		// ��ת��ҳ��
		SharePreferencesTool spt = new SharePreferencesTool(StartActivity.this,
				MODE_PRIVATE);
		String access_token = spt.popOut("online");
		// û�е�¼״̬ʱ
		if (access_token != "" && access_token != null) {
			UserDao dao = new UserDao(StartActivity.this, "local", 1);
			UserBean user = dao.querryOne(access_token);
			if (user != null) {
				Intent intent = new Intent(StartActivity.this,
						MainActivity.class);
				intent.putExtra("user", user);
				this.startActivity(intent);
				overridePendingTransition(android.R.anim.fade_in,
						android.R.anim.fade_out);
				finish();
				return;
			}

		}
		Intent intent = new Intent(StartActivity.this, LoginActivity.class);
		startActivity(intent);
		overridePendingTransition(android.R.anim.fade_in,
				android.R.anim.fade_out);
		finish();

	}

}
