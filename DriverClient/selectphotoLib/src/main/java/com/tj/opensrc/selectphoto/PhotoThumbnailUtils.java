package com.tj.opensrc.selectphoto;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.media.ExifInterface;
import android.provider.MediaStore.Images;
import android.util.Log;

import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;

public class PhotoThumbnailUtils {
	private static final String TAG = "ThumbnailUtils";

	/* Maximum pixels size for created bitmap. */
	private static final int MAX_NUM_PIXELS_THUMBNAIL = 512 * 384;
	private static final int MAX_NUM_PIXELS_MICRO_THUMBNAIL = 128 * 128;
	private static final int UNCONSTRAINED = -1;

	/* Options used internally. */
	private static final int OPTIONS_NONE = 0x0;
	private static final int OPTIONS_SCALE_UP = 0x1;

	/**
	 * Constant used to indicate we should recycle the input in
	 * {@link #extractThumbnail(Bitmap, int, int, int)} unless the output is the
	 * input.
	 */
	public static final int OPTIONS_RECYCLE_INPUT = 0x2;

	/**
	 * Constant used to indicate the dimension of mini thumbnail.
	 * 
	 * @hide Only used by media framework and media provider internally.
	 */
	public static final int TARGET_SIZE_MINI_THUMBNAIL = 320;

	/**
	 * Constant used to indicate the dimension of micro thumbnail.
	 * 
	 * @hide Only used by media framework and media provider internally.
	 */
	public static final int TARGET_SIZE_MICRO_THUMBNAIL = 96;

	/**
	 * This method first examines if the thumbnail embedded in EXIF is bigger
	 * than our target size. If not, then it'll create a thumbnail from original
	 * image. Due to efficiency consideration, we want to let MediaThumbRequest
	 * avoid calling this method twice for both kinds, so it only requests for
	 * MICRO_KIND and set saveImage to true.
	 * 
	 * This method always returns a "square thumbnail" for MICRO_KIND thumbnail.
	 * 
	 * @param filePath
	 *            the path of image file
	 * @param kind
	 *            could be MINI_KIND or MICRO_KIND
	 * @return Bitmap, or null on failures
	 * 
	 * @hide This method is only used by media framework and media provider
	 *       internally.
	 */
	public static Bitmap createImageThumbnail(String filePath, int kind) {
		boolean wantMini = (kind == Images.Thumbnails.MINI_KIND);
		int targetSize = wantMini ? TARGET_SIZE_MINI_THUMBNAIL
				: TARGET_SIZE_MICRO_THUMBNAIL;
		int maxPixels = wantMini ? MAX_NUM_PIXELS_THUMBNAIL
				: MAX_NUM_PIXELS_MICRO_THUMBNAIL;
		Bitmap bitmap = null;

		// MediaFileType fileType = MediaFile.getFileType(filePath);
		// if (fileType != null && fileType.fileType ==
		// MediaFile.FILE_TYPE_JPEG) {
		// createThumbnailFromEXIF(filePath, targetSize, maxPixels,
		// sizedThumbnailBitmap);
		// bitmap = sizedThumbnailBitmap.mBitmap;
		// }

		if (bitmap == null) {
			try {
				FileDescriptor fd = new FileInputStream(filePath).getFD();
				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inSampleSize = 1;
				options.inJustDecodeBounds = true;
				BitmapFactory.decodeFileDescriptor(fd, null, options);
				if (options.mCancel || options.outWidth == -1
						|| options.outHeight == -1) {
					return null;
				}
				options.inSampleSize = computeSampleSize(options, targetSize,
						maxPixels);
				options.inJustDecodeBounds = false;

				options.inDither = false;
				options.inPreferredConfig = Bitmap.Config.ARGB_8888;
				bitmap = BitmapFactory.decodeFileDescriptor(fd, null, options);
			} catch (IOException ex) {
				Log.e(TAG, "", ex);
			} catch (OutOfMemoryError oom) {
				Log.e(TAG, "Unable to decode file " + filePath
						+ ". OutOfMemoryError.", oom);
			}
		}

		if (kind == Images.Thumbnails.MICRO_KIND) {
			// now we make it a "square thumbnail" for MICRO_KIND thumbnail
			bitmap = extractThumbnail(bitmap, TARGET_SIZE_MICRO_THUMBNAIL,
					TARGET_SIZE_MICRO_THUMBNAIL, OPTIONS_RECYCLE_INPUT);
		}
		return bitmap;
	}

	/**
	 * Creates a centered bitmap of the desired size.
	 * 
	 * @param source
	 *            original bitmap source
	 * @param width
	 *            targeted width
	 * @param height
	 *            targeted height
	 */
	public static Bitmap extractThumbnail(Bitmap source, int width, int height) {
		return extractThumbnail(source, width, height, OPTIONS_NONE);
	}

	/**
	 * Creates a centered bitmap of the desired size.
	 * 
	 * @param source
	 *            original bitmap source
	 * @param width
	 *            targeted width
	 * @param height
	 *            targeted height
	 * @param options
	 *            options used during thumbnail extraction
	 */
	public static Bitmap extractThumbnail(Bitmap source, int width, int height,
			int options) {
		if (source == null) {
			return null;
		}

		float scale;
		if (source.getWidth() < source.getHeight()) {
			scale = width / (float) source.getWidth();
		} else {
			scale = height / (float) source.getHeight();
		}
		Matrix matrix = new Matrix();
		matrix.setScale(scale, scale);
		Bitmap thumbnail = transform(matrix, source, width, height,
				OPTIONS_SCALE_UP | options);
		return thumbnail;
	}

	/*
	 * Compute the sample size as a function of minSideLength and
	 * maxNumOfPixels. minSideLength is used to specify that minimal width or
	 * height of a bitmap. maxNumOfPixels is used to specify the maximal size in
	 * pixels that is tolerable in terms of memory usage.
	 * 
	 * The function returns a sample size based on the constraints. Both size
	 * and minSideLength can be passed in as IImage.UNCONSTRAINED, which
	 * indicates no care of the corresponding constraint. The functions prefers
	 * returning a sample size that generates a smaller bitmap, unless
	 * minSideLength = IImage.UNCONSTRAINED.
	 * 
	 * Also, the function rounds up the sample size to a power of 2 or multiple
	 * of 8 because BitmapFactory only honors sample size this way. For example,
	 * BitmapFactory downsamples an image by 2 even though the request is 3. So
	 * we round up the sample size to avoid OOM.
	 */
	public static int computeSampleSize(BitmapFactory.Options options,
			int minSideLength, int maxNumOfPixels) {
		int initialSize = computeInitialSampleSize(options, minSideLength,
				maxNumOfPixels);

		int roundedSize;
		if (initialSize <= 8) {
			roundedSize = 1;
			while (roundedSize < initialSize) {
				roundedSize <<= 1;
			}
		} else {
			roundedSize = (initialSize + 7) / 8 * 8;
		}

		return roundedSize;
	}

	private static int computeInitialSampleSize(BitmapFactory.Options options,
			int minSideLength, int maxNumOfPixels) {
		double w = options.outWidth;
		double h = options.outHeight;

		int lowerBound = (maxNumOfPixels == UNCONSTRAINED) ? 1 : (int) Math
				.ceil(Math.sqrt(w * h / maxNumOfPixels));
		int upperBound = (minSideLength == UNCONSTRAINED) ? 128 : (int) Math
				.min(Math.floor(w / minSideLength),
						Math.floor(h / minSideLength));

		if (upperBound < lowerBound) {
			// return the larger one when there is no overlapping zone.
			return lowerBound;
		}

		if ((maxNumOfPixels == UNCONSTRAINED)
				&& (minSideLength == UNCONSTRAINED)) {
			return 1;
		} else if (minSideLength == UNCONSTRAINED) {
			return lowerBound;
		} else {
			return upperBound;
		}
	}

	/**
	 * Transform source Bitmap to targeted width and height.
	 */
	private static Bitmap transform(Matrix scaler, Bitmap source,
			int targetWidth, int targetHeight, int options) {
		boolean scaleUp = (options & OPTIONS_SCALE_UP) != 0;
		boolean recycle = (options & OPTIONS_RECYCLE_INPUT) != 0;

		int deltaX = source.getWidth() - targetWidth;
		int deltaY = source.getHeight() - targetHeight;
		if (!scaleUp && (deltaX < 0 || deltaY < 0)) {
			/*
			 * In this case the bitmap is smaller, at least in one dimension,
			 * than the target. Transform it by placing as much of the image as
			 * possible into the target and leaving the top/bottom or left/right
			 * (or both) black.
			 */
			Bitmap b2 = Bitmap.createBitmap(targetWidth, targetHeight,
					Bitmap.Config.ARGB_8888);
			Canvas c = new Canvas(b2);

			int deltaXHalf = Math.max(0, deltaX / 2);
			int deltaYHalf = Math.max(0, deltaY / 2);
			Rect src = new Rect(deltaXHalf, deltaYHalf, deltaXHalf
					+ Math.min(targetWidth, source.getWidth()), deltaYHalf
					+ Math.min(targetHeight, source.getHeight()));
			int dstX = (targetWidth - src.width()) / 2;
			int dstY = (targetHeight - src.height()) / 2;
			Rect dst = new Rect(dstX, dstY, targetWidth - dstX, targetHeight
					- dstY);
			c.drawBitmap(source, src, dst, null);
			if (recycle) {
				source.recycle();
			}
			c.setBitmap(null);
			return b2;
		}
		float bitmapWidthF = source.getWidth();
		float bitmapHeightF = source.getHeight();

		float bitmapAspect = bitmapWidthF / bitmapHeightF;
		float viewAspect = (float) targetWidth / targetHeight;

		if (bitmapAspect > viewAspect) {
			float scale = targetHeight / bitmapHeightF;
			if (scale < .9F || scale > 1F) {
				scaler.setScale(scale, scale);
			} else {
				scaler = null;
			}
		} else {
			float scale = targetWidth / bitmapWidthF;
			if (scale < .9F || scale > 1F) {
				scaler.setScale(scale, scale);
			} else {
				scaler = null;
			}
		}

		Bitmap b1;
		if (scaler != null) {
			// this is used for minithumb and crop, so we want to filter here.
			b1 = Bitmap.createBitmap(source, 0, 0, source.getWidth(),
					source.getHeight(), scaler, true);
		} else {
			b1 = source;
		}

		if (recycle && b1 != source) {
			source.recycle();
		}

		int dx1 = Math.max(0, b1.getWidth() - targetWidth);
		int dy1 = Math.max(0, b1.getHeight() - targetHeight);

		Bitmap b2 = Bitmap.createBitmap(b1, dx1 / 2, dy1 / 2, targetWidth,
				targetHeight);

		if (b2 != b1) {
			if (recycle || b1 != source) {
				b1.recycle();
			}
		}

		return b2;
	}

	public static Bitmap orientationBitMap(String filepath, Bitmap bit) {
		int orientation = getExifOrientation(filepath);
		if (orientation != 0) {
			Matrix matrix = new Matrix();
			matrix.setRotate(orientation);
			return Bitmap.createBitmap(bit, 0, 0, bit.getWidth(), bit.getHeight(),
					matrix, true);
		}
		return bit;
	}

	public static int getExifOrientation(String filepath) {
		int degree = 0;
		try {
			ExifInterface exif = new ExifInterface(filepath);
			if (exif != null) {
				int orientation = exif.getAttributeInt(
						ExifInterface.TAG_ORIENTATION, -1);
				if (orientation != -1) {
					switch (orientation) {
					case ExifInterface.ORIENTATION_ROTATE_90:
						degree = 90;
						break;
					case ExifInterface.ORIENTATION_ROTATE_180:
						degree = 180;
						break;
					case ExifInterface.ORIENTATION_ROTATE_270:
						degree = 90;
						break;
					}
				}
			}
			return degree;
		} catch (IOException ex) {
			Log.e("ExifOrientation", "cannot read exif", ex);
		}
		return degree;
	}

}
