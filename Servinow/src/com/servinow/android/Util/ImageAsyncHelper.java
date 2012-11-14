package com.servinow.android.Util;

import java.io.IOException;
import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;

import com.servinow.android.synchronization.ServinowApiImage;

public class ImageAsyncHelper {
	
	ArrayList<ImageAsyncHelperTask> taskList = new ArrayList<ImageAsyncHelperTask>();

	public ImageAsyncHelper() {
	}

	public Bitmap getBitmap(String imageNameAsJPG, ImageAsyncHelperCallBack callback, Bitmap defaultImage){
		String absolutePathImg = Environment.getExternalStorageDirectory()+"/servinow/.cache/"+imageNameAsJPG;
		Bitmap img = BitmapFactory.decodeFile(absolutePathImg);
		
		if(img == null){
			ImageAsyncHelperTask task = new ImageAsyncHelperTask(imageNameAsJPG, callback);
			
			taskList.add(task);
			
			task.execute();
			
			return defaultImage;
		} else {
			return img;
		}
	}

	public void stop(){
		for(ImageAsyncHelperTask task: taskList){
			if(!task.isCancelled())
				task.cancel(true);
		}
	}
	
	public interface ImageAsyncHelperCallBack{
		public void onImageSyn(Bitmap img);
	}
	
	private class ImageAsyncHelperTask extends AsyncTask<Void, Void, Bitmap>{
		
		private String imageNameAsJPG;
		private ImageAsyncHelperCallBack callback;

		public ImageAsyncHelperTask(String imageNameAsJPG, ImageAsyncHelperCallBack callback){
			this.imageNameAsJPG = imageNameAsJPG;
			this.callback = callback;
		}

		@Override
		protected Bitmap doInBackground(Void... params) {
			try {
				Bitmap img = new ServinowApiImage(imageNameAsJPG).getBitMap();
				return img;
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			return null;
		}

		@Override
		protected void onPostExecute(Bitmap img) {
			super.onPostExecute(img);
			
			if(img != null)
				callback.onImageSyn(img);
		}
	}
}
