package com.android.camera;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.UUID;
import java.util.Vector;
import java.util.regex.Pattern;

import com.android.R;
import com.android.view.View1;
import com.android.assist.DecodeFormatManager;
import com.android.assist.ViewfinderResultPointCallback;
import com.android.assist.PlanarYUVLuminanceSource;
//import com.android.assist.BarcodeFormat;
//import com.android.assist.DecodeHintType;
//import com.android.assist.LuminanceSource;
//import com.android.assist.BinaryBitmap;
//import com.android.assist.ReaderException;
//import com.android.assist.Result;
//import com.android.assist.HybridBinarizer;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ReaderException;
import com.google.zxing.Result;
//import com.google.zxing.client.android.PlanarYUVLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
//import com.google.zxing.client.android.DecodeFormatManager;




import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 实现扫描码，要点：
 * 1 通过Message、Handler实现AF和画面预览之间转换；
 * 2 由屏幕分辨率得到：扫描里框、外框、亮度、camera分辨率
 * 3 相关类：ViewfinderResultPointCallback、DecodeFormatManager、DecodeHintType、BinaryBitmap、
 * HybridBinarizer、PlanarYUVLuminanceSource、
 * 4 最终由MultiFormatReader类的decodeWithState方法解码并得到结果，由View1类的drawResultBitmap方法得到结果图预览
 * 
 * 注意：
 * 1 在AndroidManifest.xml的<activity里加入：
 * android:screenOrientation="landscape" android:theme="@android:style/Theme.NoTitleBar.Fullscreen"
 */
public class CameraTest6 extends Activity implements SurfaceHolder.Callback {

	private Camera camera;
	private TextView textView;
//	private ImageView imageView;
	private View1 view1;
	
	private AutoFocusCallback autoFocusCallback;
	private PreviewCallback previewCallback;
	
	private static final long AUTOFOCUS_INTERVAL_MS = 1500L;
	private static final int AUTOFOCUS_MSG=1;
	private static final int PREVIEW_MSG=2;
	private boolean useOneShotPreviewCallback;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cameratest6);
		setTitle(this.getClass().getName());
		textView=(TextView) findViewById(R.id.textview);
//		imageView = (ImageView) findViewById(R.id.imageView);
		SurfaceView surfaceView=(SurfaceView) findViewById(R.id.surfaceView);
		SurfaceHolder surfaceHolder=surfaceView.getHolder();
		surfaceHolder.addCallback(this);
		surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		autoFocusCallback=new AutoFocusCallback();
		previewCallback=new PreviewCallback();
		useOneShotPreviewCallback = Integer.parseInt(Build.VERSION.SDK) > 3;
		System.out.println("SDK 版本："+Build.VERSION.SDK);
		view1=(View1) findViewById(R.id.view1);
	}
	
	//some CameraManager
	//-----------------------------------------------------------------------------------------------
	private Rect framingRect;

	private Rect getFramingRect() {
		int MIN_FRAME_WIDTH = 240;
		int MIN_FRAME_HEIGHT = 240;
		int MAX_FRAME_WIDTH = 480;
		int MAX_FRAME_HEIGHT = 360;
		String TAG = this.getClass().getSimpleName();
		Point screenResolution = getScreenResolution();
		if (framingRect == null) {
			if (camera == null) {
				return null;
			}
			int width = screenResolution.x * 3 / 4;
			if (width < MIN_FRAME_WIDTH) {
				width = MIN_FRAME_WIDTH;
			} else if (width > MAX_FRAME_WIDTH) {
				width = MAX_FRAME_WIDTH;
			}
			int height = screenResolution.y * 3 / 4;
			if (height < MIN_FRAME_HEIGHT) {
				height = MIN_FRAME_HEIGHT;
			} else if (height > MAX_FRAME_HEIGHT) {
				height = MAX_FRAME_HEIGHT;
			}
			int leftOffset = (screenResolution.x - width) / 2;
			int topOffset = (screenResolution.y - height) / 2;
			framingRect = new Rect(leftOffset, topOffset, leftOffset + width,
					topOffset + height);
			Log.d(TAG, "Calculated framing rect: " + framingRect);
		}
		return framingRect;
	}

	private Rect framingRectInPreview;

	public Rect getFramingRectInPreview() {
		if (framingRectInPreview == null) {
			Rect rect = new Rect(getFramingRect());
			Point cameraResolution = getCameraResolution();
			Point screenResolution = getScreenResolution();
			rect.left = rect.left * cameraResolution.x / screenResolution.x;
			rect.right = rect.right * cameraResolution.x / screenResolution.x;
			rect.top = rect.top * cameraResolution.y / screenResolution.y;
			rect.bottom = rect.bottom * cameraResolution.y / screenResolution.y;
			framingRectInPreview = rect;
		}
		return framingRectInPreview;
	}

	public PlanarYUVLuminanceSource buildLuminanceSource(byte[] data, int width, int height) {
		Rect rect = getFramingRectInPreview();
		Camera.Parameters parameters = camera.getParameters();
		int previewFormat = parameters.getPreviewFormat();
		String previewFormatString = parameters.get("preview-format");
		switch (previewFormat) {
		// This is the standard Android format which all devices are REQUIRED to
		// support.
		// In theory, it's the only one we should ever care about.
		case PixelFormat.YCbCr_420_SP:
			// This format has never been seen in the wild, but is compatible as
			// we only care
			// about the Y channel, so allow it.
		case PixelFormat.YCbCr_422_SP:
			return new PlanarYUVLuminanceSource(data, width, height, rect.left,
					rect.top, rect.width(), rect.height());
		default:
			// The Samsung Moment incorrectly uses this variant instead of the
			// 'sp' version.
			// Fortunately, it too has all the Y data up front, so we can read
			// it.
			if ("yuv420p".equals(previewFormatString)) {
				return new PlanarYUVLuminanceSource(data, width, height,
						rect.left, rect.top, rect.width(), rect.height());
			}
		}
		throw new IllegalArgumentException("Unsupported picture format: "
				+ previewFormat + '/' + previewFormatString);
	}
	//-----------------------------------------------------------------------------------------------
	
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case AUTOFOCUS_MSG:
				requestAF();
//				textView.setText("UUID:"+UUID.randomUUID().toString());
				textView.setText("AF...");
//				img(msg.getData(), camera);
				break;
			case PREVIEW_MSG:
				requestPreview();
//				textView.setText("AF again");
//				img(msg.getData(), camera);
			}
		}
		
	};
	
	@SuppressLint({ "NewApi" })
	private void img(Bundle bundle,Camera camera){
		if (bundle != null) {
			Bitmap barcode = bundle == null ? null : (Bitmap) bundle.getParcelable(String.valueOf(PREVIEW_MSG));
			view1.drawResultBitmap(barcode);
		}
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		
	}

	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		openCamera(holder);
		view1.setFrame(getFramingRect());
		requestAF();
//		requestPreview();
	}
	
	private void requestAF(){
		autoFocusCallback.setHandler(handler, AUTOFOCUS_MSG);
	    camera.autoFocus(autoFocusCallback);
	}
	
	@SuppressLint("NewApi")
	private void requestPreview() {
		previewCallback.setHandler(handler, PREVIEW_MSG);
		if (useOneShotPreviewCallback) {
			camera.setOneShotPreviewCallback(previewCallback);
		} else {
			camera.setPreviewCallback(previewCallback);
		}
	}
	
	@SuppressLint("NewApi")
	private void openCamera(SurfaceHolder holder){
		if(camera==null){
			camera = Camera.open();
		}
		try {
			camera.setPreviewDisplay(holder);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Camera.Parameters parameters = camera.getParameters();
		Point cameraResolution=getCameraResolution();
	    parameters.setPreviewSize(cameraResolution.x, cameraResolution.y);
	    camera.setParameters(parameters);
//		camera.setDisplayOrientation(90);
		camera.startPreview();
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		camera.stopPreview();
		if (camera != null){
			camera.release();
		}
		if (!useOneShotPreviewCallback) {
	        camera.setPreviewCallback(null);
	    }
		autoFocusCallback.setHandler(null, 0);
//		autoFocusCallback=null;
		previewCallback.setHandler(null, 0);
//		handler=null;
	}
	
	class AutoFocusCallback implements Camera.AutoFocusCallback {
		private int i=0;
		private final String TAG = AutoFocusCallback.class.getSimpleName();
		private Handler autoFocusHandler;
		private int autoFocusMessage;

		void setHandler(Handler autoFocusHandler, int autoFocusMessage) {
			this.autoFocusHandler = autoFocusHandler;
			this.autoFocusMessage = autoFocusMessage;
		}

		public void onAutoFocus(boolean success, Camera camera) {
			if (autoFocusHandler != null) {
				Message message = autoFocusHandler.obtainMessage(autoFocusMessage, success);
//				while(i++>=3){
					if(success){
						message.what=PREVIEW_MSG;
						i=0;
					}
//				}
//				autoFocusHandler.sendMessage(message);
				autoFocusHandler.sendMessageDelayed(message, AUTOFOCUS_INTERVAL_MS);
				autoFocusHandler = null;
			}else{
				Log.d(TAG, "Got auto-focus callback, but no handler for it");
			}

		}
	}
	class PreviewCallback implements Camera.PreviewCallback {
		private final String TAG = PreviewCallback.class.getSimpleName();
//		private final MultiFormatReader multiFormatReader=new MultiFormatReader();;
		
		private Handler previewHandler;
		private int previewMessage;
		

		void setHandler(Handler previewHandler, int previewMessage) {
			this.previewHandler = previewHandler;
			this.previewMessage = previewMessage;
		}

		public void onPreviewFrame(byte[] data, Camera camera) {
			if (previewHandler != null) {
//				img(data, camera);
				textView.setText("decoding...");
				Message message = previewHandler.obtainMessage(previewMessage, data);
				LuminanceSource source=img_bundle(data);
				decode(source,message);
				previewHandler = null;
			} else {
				Log.d(TAG, "Got preview callback, but no handler for it");
			}
		}
		//decode
		private void decode(LuminanceSource source,Message message) {
			MultiFormatReader multiFormatReader=new MultiFormatReader();
			Hashtable<DecodeHintType, Object> hints = new Hashtable<DecodeHintType, Object>(3);
			Vector<BarcodeFormat> decodeFormats = new Vector<BarcodeFormat>();
//			decodeFormats.addAll(DecodeFormatManager.ONE_D_FORMATS);
			decodeFormats.addAll(DecodeFormatManager.QR_CODE_FORMATS);
//			decodeFormats.addAll(DecodeFormatManager.DATA_MATRIX_FORMATS);
			hints.put(DecodeHintType.POSSIBLE_FORMATS, decodeFormats);
			hints.put(DecodeHintType.CHARACTER_SET, "utf-8");
			hints.put(DecodeHintType.NEED_RESULT_POINT_CALLBACK, new ViewfinderResultPointCallback(view1));
			multiFormatReader.setHints(hints);
			BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(source));
			Result result = null;
			try {
				result = multiFormatReader.decodeWithState(binaryBitmap);
			} catch (ReaderException re) {
//				System.out.println(re.getLocalizedMessage());
			} finally {
				multiFormatReader.reset();
			}
			if (result != null) {
				textView.setText("scanned result:"+result.toString());
				Bitmap bitmap=((PlanarYUVLuminanceSource) source).renderCroppedGreyscaleBitmap();
				if(bitmap!=null){
					view1.drawResultBitmap(bitmap);
				}
			}else{
//				textView.setText("AF again");
				message.what=AUTOFOCUS_MSG;
				message.sendToTarget();
			}
		}
		private LuminanceSource img_bundle(byte[] data){
			Point cameraResolution = getCameraResolution();
			PlanarYUVLuminanceSource source = buildLuminanceSource(data, cameraResolution.x, cameraResolution.y);
//			Bitmap bitmap=source.renderCroppedGreyscaleBitmap();
//			if(bitmap!=null){
//				view1.drawResultBitmap(bitmap);
//			}
//			Bundle bundle = new Bundle();
//		    bundle.putParcelable(String.valueOf(PREVIEW_MSG),source.renderCroppedGreyscaleBitmap());
//		    message.setData(bundle);
		    return source;
		}
		@SuppressLint({ "NewApi" })
		private void img(byte[] data,Camera camera){
			if (data != null) {
				Camera.Parameters parameters = camera.getParameters();
				int imageFormat = parameters.getPreviewFormat();
				Log.i("map", "Image Format: " + imageFormat);
				Log.i("CameraPreviewCallback", "data length:" + data.length);
				if (imageFormat == ImageFormat.NV21) {
					// get full picture
					Bitmap image = null;
					int w = parameters.getPreviewSize().width;
					int h = parameters.getPreviewSize().height;
					Rect rect = new Rect(0, 0, w, h);
					YuvImage img = new YuvImage(data, ImageFormat.NV21, w, h, null);
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					if (img.compressToJpeg(rect, 100, baos)) {
						image = BitmapFactory.decodeByteArray(baos.toByteArray(), 0, baos.size());
						//屏幕预览
//						imageView.setImageBitmap(image);
						view1.drawResultBitmap(image);
					}
				}
			}
		}

	}
	
	//some CameraConfigurationManager
	//-----------------------------------------------------------------------------------------------
	private static Point getCameraResolution(Camera.Parameters parameters, Point screenResolution) {
		String previewSizeValueString = parameters.get("preview-size-values");
		// saw this on Xperia
		if (previewSizeValueString == null) {
			previewSizeValueString = parameters.get("preview-size-value");
		}

		Point cameraResolution = null;

		if (previewSizeValueString != null) {
			Log.d("CameraTest6", "preview-size-values parameter: " + previewSizeValueString);
			cameraResolution = findBestPreviewSizeValue(previewSizeValueString, screenResolution);
		}

		if (cameraResolution == null) {
			// Ensure that the camera resolution is a multiple of 8, as the
			// screen may not be.
			cameraResolution = new Point((screenResolution.x >> 3) << 3, (screenResolution.y >> 3) << 3);
		}

		return cameraResolution;
	}

	private Point getCameraResolution() {
		Point cameraResolution;
		cameraResolution = getCameraResolution(camera.getParameters(), getScreenResolution());
		return cameraResolution;
	}

	private Point getScreenResolution() {
		String TAG = this.getClass().getSimpleName();
		WindowManager manager = (WindowManager) getApplication().getSystemService(Context.WINDOW_SERVICE);
		Display display = manager.getDefaultDisplay();
		Point screenResolution = new Point(display.getWidth(), display.getHeight());
		Log.d(TAG, "Screen resolution: " + screenResolution);
		return screenResolution;//new Point(480,320);
	}

	private static final Pattern COMMA_PATTERN = Pattern.compile(",");

	private static Point findBestPreviewSizeValue(CharSequence previewSizeValueString, Point screenResolution) {
		int bestX = 0;
		int bestY = 0;
		int diff = Integer.MAX_VALUE;
		for (String previewSize : COMMA_PATTERN.split(previewSizeValueString)) {

			previewSize = previewSize.trim();
			int dimPosition = previewSize.indexOf('x');
			if (dimPosition < 0) {
				Log.w("CameraTest6", "Bad preview-size: " + previewSize);
				continue;
			}

			int newX;
			int newY;
			try {
				newX = Integer.parseInt(previewSize.substring(0, dimPosition));
				newY = Integer.parseInt(previewSize.substring(dimPosition + 1));
			} catch (NumberFormatException nfe) {
				Log.w("CameraTest6", "Bad preview-size: " + previewSize);
				continue;
			}

			int newDiff = Math.abs(newX - screenResolution.x)
					+ Math.abs(newY - screenResolution.y);
			if (newDiff == 0) {
				bestX = newX;
				bestY = newY;
				break;
			} else if (newDiff < diff) {
				bestX = newX;
				bestY = newY;
				diff = newDiff;
			}

		}

		if (bestX > 0 && bestY > 0) {
			return new Point(bestX, bestY);
		}
		return null;
	}
	
	//-----------------------------------------------------------------------------------------------

}
