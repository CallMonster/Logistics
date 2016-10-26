package com.tj.opensrc.selectphoto;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore.Images.Media;
import android.provider.MediaStore.Images.Thumbnails;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SystemAlbumImageObjectVO  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String id;
	public String display_name;
	public String bucket_display_name;
	public String data;
	public String count;
	public String bucket_id;
	public String thumbnail_data;
	public String create_date;
	public int position;

	public static final String[] selects = new String[] { "count(*) as _count",
			Media._ID, Media.BUCKET_DISPLAY_NAME, Media.DISPLAY_NAME,
			Media.DATA, Media.BUCKET_ID , Media.DATE_ADDED};

	public SystemAlbumImageObjectVO() {
		super();
	}

	public SystemAlbumImageObjectVO(String data) {
		super();
		this.data = data;
	}

	
	
/*	private static HashMap<String ,Integer> folderMap=null;
	
	
	public static HashMap<String ,Integer> getFolderMap(){
		return folderMap;
	}*/
	
	/**
	 * 
	 * @param cursor
	 * @return
	 */
	public static List<SystemAlbumImageObjectVO> readCursor(Cursor cursor) {
		List<SystemAlbumImageObjectVO> list = new ArrayList<SystemAlbumImageObjectVO>();
		//folderMap=new HashMap<String ,Integer>();
		try {
			if (cursor != null && !cursor.isClosed()) {
				int id_index = cursor.getColumnIndex(Media._ID);
				int bucket_display_name_index = cursor.getColumnIndex(Media.BUCKET_DISPLAY_NAME);
				int display_name_index = cursor.getColumnIndex(Media.DISPLAY_NAME);
				int data_index = cursor.getColumnIndex(Media.DATA);
				int bucket_id_index = cursor.getColumnIndex(Media.BUCKET_ID);
				int count_index = cursor.getColumnIndex(Media._COUNT);
				int date_index=cursor.getColumnIndex(Media.DATE_ADDED);
				int i=0;
				while (cursor.moveToNext()) {
					SystemAlbumImageObjectVO io = new SystemAlbumImageObjectVO();
					io.count = cursor.getString(count_index);
					io.id = cursor.getString(id_index);
					io.bucket_display_name = cursor
							.getString(bucket_display_name_index);
					io.display_name = cursor.getString(display_name_index);
					io.data = cursor.getString(data_index);
					io.bucket_id = cursor.getString(bucket_id_index);
					io.create_date=cursor.getString(date_index);
					list.add(io);
					
					//folderMap.put(io.bucket_display_name, i++);
				}
			}

		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
		return list;
	}

	public static SystemAlbumImageObjectVO readImageObjectVO(Uri data, ContentResolver contentResolver) {
		Cursor cursor = contentResolver.query(data, selects, null, null, null);

		List<SystemAlbumImageObjectVO> list = readCursor(cursor);
		if (list.isEmpty()) {
			return null;
		}
		SystemAlbumImageObjectVO vo = list.get(0);

		Cursor c = contentResolver.query(Thumbnails.EXTERNAL_CONTENT_URI, null,
				Thumbnails.IMAGE_ID + "=?", new String[] { vo.id }, null);
		ArrayList<SystemAlbumThumbnailVO> thumlist = parseThumbnailCursor(c);

		setImageThubnail(thumlist, vo);

		return vo;
	}

	public static List<SystemAlbumImageObjectVO> readCursor(Cursor cursor, ArrayList<SystemAlbumThumbnailVO> lists) {
		List<SystemAlbumImageObjectVO> list = new ArrayList<SystemAlbumImageObjectVO>();
		try {
			if (cursor != null) {
				int id_index = cursor.getColumnIndex(Media._ID);
				int bucket_display_name_index = cursor.getColumnIndex(Media.BUCKET_DISPLAY_NAME);
				int display_name_index = cursor.getColumnIndex(Media.DISPLAY_NAME);
				int data_index = cursor.getColumnIndex(Media.DATA);
				int bucket_id_index = cursor.getColumnIndex(Media.BUCKET_ID);
				int date_index=cursor.getColumnIndex(Media.DATE_ADDED);
				while (cursor.moveToNext()) {
					String path = cursor.getString(data_index);
					if (!new File(path).exists()) {
						continue;
					}
					SystemAlbumImageObjectVO io = new SystemAlbumImageObjectVO();
					io.data = path;
					io.id = cursor.getString(id_index);
					io.bucket_display_name = cursor.getString(bucket_display_name_index);
					io.display_name = cursor.getString(display_name_index);
					io.bucket_id = cursor.getString(bucket_id_index);
					io.create_date=cursor.getString(date_index);
					setImageThubnail(lists, io);
					list.add(io);
				}
			}

		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
		return list;
	}

	public static void setImageThubnail(ArrayList<SystemAlbumThumbnailVO> list, SystemAlbumImageObjectVO io) {
		for (SystemAlbumThumbnailVO thumbnail : list) {
			if (thumbnail.image_id.equals(io.id)) {
				io.thumbnail_data = thumbnail.data;
				break;
			}
		}
	}

	public static ArrayList<SystemAlbumThumbnailVO> loadThumbnail(Context contenxt) {
		Cursor cursor = contenxt.getContentResolver().query(Thumbnails.EXTERNAL_CONTENT_URI, null, null, null, null);
		ArrayList<SystemAlbumThumbnailVO> thumlist = new ArrayList<SystemAlbumThumbnailVO>();
		try {
			if (cursor != null) {
				thumlist = parseThumbnailCursor(cursor);
			}
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
		return thumlist;
	}

	private static ArrayList<SystemAlbumThumbnailVO> parseThumbnailCursor(Cursor cursor) {
		ArrayList<SystemAlbumThumbnailVO> thumlist = new ArrayList<SystemAlbumThumbnailVO>();
		int id_index = cursor.getColumnIndex(Thumbnails._ID);
		int image_id_index = cursor.getColumnIndex(Thumbnails.IMAGE_ID);
		int data_index = cursor.getColumnIndex(Thumbnails.DATA);
		while (cursor.moveToNext()) {
			SystemAlbumThumbnailVO thumbnail = new SystemAlbumThumbnailVO();
			thumbnail.id = cursor.getString(id_index);
			thumbnail.image_id = cursor.getString(image_id_index);
			thumbnail.data = cursor.getString(data_index);
			thumlist.add(thumbnail);
		}
		return thumlist;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SystemAlbumImageObjectVO other = (SystemAlbumImageObjectVO) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		return true;
	}

}
