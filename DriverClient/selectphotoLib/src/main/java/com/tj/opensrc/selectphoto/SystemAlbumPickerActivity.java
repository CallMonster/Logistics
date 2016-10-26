package com.tj.opensrc.selectphoto;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore.Images.Media;

import android.app.Activity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class SystemAlbumPickerActivity extends Activity implements OnClickListener {
	private final String TAG = "SystemAlbumPickerActivity";
	public static final int requestCode_CAMERA = 1000;
	private String cameraPath = "";

	public static final int resultCode_SINGLE_PATH = 2000;
	public static final String key_singlePath = "singlePath";
	public static final String key_appPath = "appPath";

	private String appPath;


	private TextView txt_nav_OtherAlbum;
	private TextView txt_nav_Title;
	private Context context;
	private GridView imageGridView = null;
	private ListView imageListView = null;
	private FolderAdapter folderAdapter;
	private RelativeLayout rel_back,rel_otheralbum;


	
	private SystemAlbumPickerActivity_Adapter adapter = null;
	private List<SystemAlbumImageObjectVO> imglistname;
	private int mImageThumbSpacing;
	
	private ArrayList<String> savepicpaths = new ArrayList<String>();
	List<SystemAlbumImageObjectVO> list = new ArrayList<SystemAlbumImageObjectVO>();
	private ProgressDialog mDialog;

	public Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			try {
				list.clear();
				String bucket_id = msg.obj.toString();
				ArrayList<SystemAlbumThumbnailVO> lists = SystemAlbumImageObjectVO.loadThumbnail(context);
				Cursor cursor;
				if("".equals(bucket_id)){
					cursor = getContentResolver().query(Media.EXTERNAL_CONTENT_URI, new String[] { Media._ID, Media.BUCKET_DISPLAY_NAME, Media.DISPLAY_NAME, Media.DATA, Media.BUCKET_ID, Media.DATE_ADDED }, null, null, Media.DATE_ADDED + " desc ");
				}else{
					cursor = getContentResolver().query(Media.EXTERNAL_CONTENT_URI, new String[] { Media._ID, Media.BUCKET_DISPLAY_NAME, Media.DISPLAY_NAME, Media.DATA, Media.BUCKET_ID, Media.DATE_ADDED }, Media.BUCKET_ID + "=?", new String[] { bucket_id }, Media.DATE_ADDED + " desc ");
				}
				List<SystemAlbumImageObjectVO> list = SystemAlbumImageObjectVO.readCursor(cursor, lists);
				
				adapter = new SystemAlbumPickerActivity_Adapter(context, imageGridView, list);
				imageGridView.setAdapter(adapter);
				imageGridView.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
					@Override
					public void onGlobalLayout() {
						if (adapter != null) {

							if (adapter.getCount() > 0) {
								int width = imageGridView.getWidth();
								final int columnWidth = (width / 4) - mImageThumbSpacing;
								adapter.setItemHeight(columnWidth);
							}
						}
					}
				});

			} catch (Exception e) {
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_system_album_image_picker);
		context = this;

		appPath = getIntent().getStringExtra(key_appPath);
		
		imageGridView = (GridView) findViewById(R.id.system_album_image_picker_GridView);
		txt_nav_OtherAlbum = (TextView) findViewById(R.id.system_album_image_picker_nav_otheralbum_txt);
		txt_nav_Title = (TextView) this.findViewById(R.id.system_album_image_picker_nav_title_txt);
		rel_back = (RelativeLayout) findViewById(R.id.system_album_image_picker_nav_rel_back);
		rel_otheralbum = (RelativeLayout) findViewById(R.id.system_album_image_picker_nav_rel_other);
		imageListView = (ListView) findViewById(R.id.system_album_image_picker_listview);

		mImageThumbSpacing = getResources().getDimensionPixelSize(R.dimen.image_thumbnail_spacing);

		String selection = "1=1) group by (" + Media.BUCKET_ID;
		Cursor imglist = getContentResolver().query(Media.EXTERNAL_CONTENT_URI,
				                                    new String[] { "count(*) as _count", Media._ID, Media.BUCKET_DISPLAY_NAME, Media.DISPLAY_NAME, Media.DATA, Media.BUCKET_ID, Media.DATE_ADDED },
				                                    selection, 
				                                    null, 
				                                    Media.DATE_ADDED + " desc ");
		
		imglistname = SystemAlbumImageObjectVO.readCursor(imglist);
		if (imglistname.size() > 0) {
			Message msg = new Message();
			msg.obj = "";
			handler.sendMessage(msg);
		}

		rel_back.setOnClickListener(this);
		rel_otheralbum.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.system_album_image_picker_nav_rel_back)
			finish();
		if(v.getId() == R.id.system_album_image_picker_nav_rel_other){
			openImgFolderDlg();
		}
	}

	private void openImgFolderDlg() {
		if (imageListView.getVisibility() == View.VISIBLE) {
			hideListAnimation();
		} else {
			imageListView.setVisibility(View.VISIBLE);
			showListAnimation();

			folderAdapter = new FolderAdapter();
			imageListView.setAdapter(folderAdapter);
			imageListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					SystemAlbumImageObjectVO imgObj = imglistname.get(position);
					txt_nav_Title.setText(imgObj.bucket_display_name);
					hideListAnimation();
					Message msg = new Message();
					msg.obj = imgObj.bucket_id;
					handler.sendMessage(msg);
				}
			});

		}
	}

	class FolderAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return imglistname != null ?imglistname.size() : 0;
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			FolderViewHolder holder = null;
			if (convertView == null) {
				convertView = View.inflate(context, R.layout.list_dir_item, null);
				holder = new FolderViewHolder();
				holder.id_dir_item_image = (ImageView) convertView.findViewById(R.id.id_dir_item_image_pic);
				holder.id_dir_item_name = (TextView) convertView.findViewById(R.id.id_dir_item_name);
				holder.id_dir_item_count = (TextView) convertView.findViewById(R.id.id_dir_item_count);
				holder.choose = (ImageView) convertView.findViewById(R.id.choose);
				convertView.setTag(holder);
			} else {
				holder = (FolderViewHolder) convertView.getTag();
			}
			SystemAlbumImageObjectVO imgObj = imglistname.get(position);

			String picturePath=imgObj.data;
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;
			options.inPreferredConfig = Bitmap.Config.RGB_565;		//使用不带透明度的方法解析,比8888方法,减少一半内存占用
			BitmapFactory.decodeFile(picturePath, options);
			int height = options.outHeight;
			int width = options.outWidth;
			int inSampleSize = 1;

			if (height > 200 || width > 200) {
				inSampleSize = Math.round((float) width / (float) 200);
			}
			options.inSampleSize = inSampleSize;
			options.inJustDecodeBounds = false; // 设置了此属性一定要记得将值设置为false
			Bitmap icon = BitmapFactory.decodeFile(picturePath, options);
			 if(icon==null){
				 holder.id_dir_item_image.setImageDrawable(context.getResources().getDrawable(R.drawable.img_loading_bg));
			 }else{
				 holder.id_dir_item_image.setImageBitmap(icon);
			 }


			holder.id_dir_item_name.setText(imgObj.bucket_display_name);
			holder.id_dir_item_count.setText(imgObj.count + "张");
			holder.choose.setVisibility(txt_nav_Title.getText() == imgObj.bucket_display_name ? View.VISIBLE : View.GONE);
			return convertView;
		}
	}

	class FolderViewHolder {
		ImageView id_dir_item_image;
		ImageView choose;
		TextView id_dir_item_name;
		TextView id_dir_item_count;
	}


	@Override
	public void onDestroy() {
		super.onDestroy();
		if (adapter != null) {
			adapter = null;
		}
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			finish();
			return true;
		} else {
			return false;
		}
	}

	@Override
	protected synchronized Dialog onCreateDialog(int id) {
		switch (id) {
		case 0: {
			if (mDialog == null) {
				mDialog = new ProgressDialog(this);
				mDialog.setMessage("请稍等，图片保存中...");
				mDialog.setIndeterminate(true);
			}
			return mDialog;
		}
		}
		return super.onCreateDialog(id);
	}


	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	};

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		System.out.println("内存回收了");

	}



	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == requestCode_CAMERA && resultCode == RESULT_OK){
			if(null == data){
				return;
			}
			if(null == appPath){
				cameraPath = Environment.getExternalStorageDirectory()+"/DCIM/camera/"+System.currentTimeMillis()+ ".jpg";
			}else {
				cameraPath = Environment.getExternalStorageDirectory()+"/"+appPath+"/"+System.currentTimeMillis()+ ".jpg";
			}

			File file=new File(cameraPath);

			Bundle bundle = data.getExtras();
			Bitmap bitmap = (Bitmap) bundle.get("data");// 获取相机返回的数据，并转换为Bitmap图片格式

			boolean successful = saveBitmap2file(bitmap, file, Bitmap.CompressFormat.JPEG, 100);
			if (bitmap != null && !bitmap.isRecycled()) {
				bitmap.recycle();
			}
			if(successful){
				Intent data2 = new Intent();
				data2.putExtra(key_singlePath, cameraPath);
				setResult(resultCode_SINGLE_PATH, data2);
				finish();
			}
		}
	}

	private boolean saveBitmap2file(Bitmap bmp, File file, Bitmap.CompressFormat format, int quality) {
		if (!file.exists()) {
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
		}
		if (file.isFile())
			file.delete();
		OutputStream stream = null;
		try {
			stream = new FileOutputStream(file);
		} catch (FileNotFoundException e) {
			return false;
		}
		return bmp.compress(format, quality, stream);
	}




	public void showListAnimation() {
		TranslateAnimation ta = new TranslateAnimation(1, 0f, 1, 0f, 1, 1f, 1, 0f);
		ta.setDuration(200);
		imageListView.startAnimation(ta);
	}

	public void hideListAnimation() {
		TranslateAnimation ta = new TranslateAnimation(1, 0f, 1, 0f, 1, 0f, 1, 1f);
		ta.setDuration(200);
		imageListView.startAnimation(ta);
		ta.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				imageListView.setVisibility(View.GONE);
			}
		});
	}
}
