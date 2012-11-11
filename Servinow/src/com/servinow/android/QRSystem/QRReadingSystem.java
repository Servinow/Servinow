package com.servinow.android.QRSystem;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.servinow.android.startRestaurantSystem.CacheRestaurantSystem;

import net.sourceforge.zbar.Config;
import net.sourceforge.zbar.Image;
import net.sourceforge.zbar.ImageScanner;
import net.sourceforge.zbar.Symbol;
import net.sourceforge.zbar.SymbolSet;
import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.PreviewCallback;
import android.hardware.Camera.Size;
import android.os.Handler;
import android.util.Log;
import android.widget.FrameLayout;

public class QRReadingSystem {
	
	static {
		System.loadLibrary("iconv");
	}

	private Handler autoFocusHandler;
	private Camera mCamera;
	private ImageScanner scanner;
	protected boolean previewing = true;
	private CameraPreview mPreview;

	private FrameLayout container;
	private QRResultCallback qrCallbacks;

	public QRReadingSystem(FrameLayout container, QRResultCallback qrCallbacks) {
		this.container = container;
		this.qrCallbacks = qrCallbacks;
	}

	public void start(){
		autoFocusHandler = new Handler();
		mCamera = getCameraInstance();

		/* Instance barcode scanner */
		scanner = new ImageScanner();
		scanner.setConfig(0, Config.X_DENSITY, 3);
		scanner.setConfig(0, Config.Y_DENSITY, 3);

		mPreview = new CameraPreview(container.getContext(), mCamera, previewCb, autoFocusCB);
		container.addView(mPreview);
	}

	public void releaseCamera() {
		if (mCamera != null) {
			previewing = false;
			mCamera.setPreviewCallback(null);
			mCamera.release();
			mCamera = null;
			container.removeAllViews();
		}
		
		if(cacheRestaurantSystem != null){
			cacheRestaurantSystem.stop();
		}
	}

	/** A safe way to get an instance of the Camera object. */
	private static Camera getCameraInstance(){
		Camera c = null;
		try {
			c = Camera.open();
		} catch (Exception e){
		}
		return c;
	}

	private PreviewCallback previewCb = new PreviewCallback() {
		public void onPreviewFrame(byte[] data, Camera camera) {
			Camera.Parameters parameters = camera.getParameters();
			Size size = parameters.getPreviewSize();

			Image barcode = new Image(size.width, size.height, "Y800");
			barcode.setData(data);

			int result = scanner.scanImage(barcode);

			if (result != 0) {
				previewing  = false;
				mCamera.setPreviewCallback(null);
				mCamera.stopPreview();

				SymbolSet syms = scanner.getResults();
				for (Symbol sym : syms) {
					Log.i("QRRead", "Data:_"+sym.getData()+"_fin");
					onQRResult(sym.getData());
					return;
				}
			}
		}
	};
	
	private CacheRestaurantSystem cacheRestaurantSystem = null;
	
	private void onQRResult(String qrjson){
		try {
			QRResult qrr = new Gson().fromJson(qrjson, QRResult.class);
			
			if(qrr != null && qrr.getRestaurantID() != null && qrr.getPlaceID() != null) {
				cacheRestaurantSystem = new CacheRestaurantSystem(container.getContext(), qrr.getRestaurantID(), qrr.getPlaceID(), qrCallbacks);
				cacheRestaurantSystem.start();
			} else {
				qrCallbacks.onBadCode();
			}
		} catch(JsonSyntaxException e){
			qrCallbacks.onBadCode();
		}
	}

	// Mimic continuous auto-focusing
	private AutoFocusCallback autoFocusCB = new AutoFocusCallback() {
		public void onAutoFocus(boolean success, Camera camera) {
			autoFocusHandler.postDelayed(doAutoFocus, 1000);
		}
	};

	private Runnable doAutoFocus = new Runnable() {
		public void run() {
			if (previewing)
				mCamera.autoFocus(autoFocusCB);
		}
	};
}
