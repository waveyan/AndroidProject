package com.trabal.util.net;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.squareup.picasso.Transformation;
public class Tools {

		/**
		 * @param is 输入流
		 * @return String 返回的字符串
		 * @throws IOException 
		 */
		public static String readFromStream(InputStream is) throws IOException{
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int len = 0;
			while((len = is.read(buffer))!=-1){
				baos.write(buffer, 0, len);
			}
			is.close();
			String result = baos.toString();
			baos.close();
			return result;
		}

		public static class CircleTransform implements Transformation {
		@Override
		public Bitmap transform(Bitmap source) {
		int size = Math.min(source.getWidth(), source.getHeight());

		int x = (source.getWidth() - size) / 2;
		int y = (source.getHeight() - size) / 2;

		Bitmap squaredBitmap = Bitmap.createBitmap(source, x, y, size, size);
		if (squaredBitmap != source) {
		source.recycle();
		}

		Bitmap bitmap = Bitmap.createBitmap(size, size, source.getConfig());

		Canvas canvas = new Canvas(bitmap);
		Paint paint = new Paint();
		BitmapShader shader = new BitmapShader(squaredBitmap,
		BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
		paint.setShader(shader);
		paint.setAntiAlias(true);

		float r = size / 2f;
		canvas.drawCircle(r, r, r, paint);

		squaredBitmap.recycle();
		return bitmap;
		}

		@Override
		public String key() {
		return "circle";
		}
		}

}