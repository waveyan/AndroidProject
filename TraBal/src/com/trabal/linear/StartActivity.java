package com.trabal.linear;


import com.trabal.LoginActivity;
import com.trabal.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

public class StartActivity extends Activity {
	
	private ImageView start;
	
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏标题栏
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        //隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_start);
        initImage();
    }
	
	

	
	private void supportRequestWindowFeature(int featureNoTitle) {
		// TODO Auto-generated method stub
		
	}




	private void initImage() {
        start = (ImageView) findViewById(R.id.start);
        start.setImageResource(R.drawable.start2);
        //进行缩放动画
        ScaleAnimation scaleAnimation = new ScaleAnimation(1.4f, 1.0f, 1.4f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(4000);
        //动画播放完成后保持形状
        scaleAnimation.setFillAfter(true);
        scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                //可以在这里先进行某些操作
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
         Intent intent = new Intent(StartActivity.this, LoginActivity.class);
         startActivity(intent);
      overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
         finish();
     }

}
