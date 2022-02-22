package com.begini.androidsdkv2.workerutil

import android.content.ContentResolver
import android.content.Context
import android.provider.MediaStore
import android.util.Log
import java.io.IOException
import android.media.ExifInterface
import android.os.Build
import androidx.annotation.RequiresApi
import com.begini.androidsdkv2.model.devicedata.GalleryExifModel
import com.begini.androidsdkv2.model.devicedata.GalleryModel
import java.io.InputStream


class GalleryUtil {

    fun getAllFileInfo(context: Context): MutableList<GalleryExifModel> {
        val result: MutableList<GalleryExifModel> = arrayListOf()
        try {
            val cr: ContentResolver = context.getContentResolver()
            val columns = arrayOf(
                MediaStore.Images.ImageColumns._ID,
                MediaStore.Images.ImageColumns.TITLE,
                MediaStore.Images.ImageColumns.DATE_MODIFIED,
                MediaStore.Images.ImageColumns.DATA,
                MediaStore.Images.ImageColumns.MIME_TYPE,
                MediaStore.Images.ImageColumns.SIZE,
                MediaStore.Images.ImageColumns.ORIENTATION,
                MediaStore.Images.ImageColumns.WIDTH,
                MediaStore.Images.ImageColumns.HEIGHT,
                MediaStore.Images.ImageColumns.DISPLAY_NAME,
                MediaStore.Images.ImageColumns.LATITUDE,
                MediaStore.Images.ImageColumns.LONGITUDE,
                MediaStore.Images.ImageColumns.RESOLUTION,
            )
            val cursor = cr.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                columns, null, null, null
            )
            Log.e("Gallery", "Cursor count ${cursor!!.count}")
var i=0
            if (cursor!!.moveToFirst()) {
                do {
                    try {
                        val data =
                            cursor.getString(cursor!!.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.DATA))
                        val date =
                            cursor.getString(cursor!!.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.DATE_MODIFIED))
                        val size =
                            cursor.getString(cursor!!.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.SIZE))
                        val id =
                            cursor.getString(cursor!!.getColumnIndexOrThrow(MediaStore.Images.ImageColumns._ID))
                        val title =
                            cursor.getString(cursor!!.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.TITLE))
                        val mimeType =
                            cursor.getString(cursor!!.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.MIME_TYPE))
                        val width =
                            cursor.getString(cursor!!.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.WIDTH))
                        val height =
                            cursor.getString(cursor!!.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.HEIGHT))
                        val orientation =
                            cursor.getString(cursor!!.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.ORIENTATION))
                        val displayName =
                            cursor.getString(cursor!!.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.DISPLAY_NAME))
                        val galleryModel = GalleryModel(
                            id,
                            title,
                            date,
                            data,
                            mimeType,
                            size,
                            orientation,
                            width,
                            height, displayName
                        )
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            var galleryExifModel = readExifData(data, size)
                            if (galleryExifModel != null) {
                                if(galleryExifModel.exposureBiasValue!=null)
                                {
                                    i++
                                    result.add(galleryExifModel)
                                }
                            }
                        }

                    } catch (e: IOException) {
                        Log.e("Gallery", "Exception occur $e")
                        e.printStackTrace()
                    }
//                    if(i==21)break
                } while (cursor!!.moveToNext())
            }
            cursor!!.close()
        } catch (e: Exception) {
            Log.e("Gallery", "Exception occur $e")
            e.printStackTrace()
        }
        Log.e("Gallery", "data : ${result.toString()}")

        return result
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun readExifData(fileName: String, fileSize: String): GalleryExifModel? {
        var inputStream: InputStream? = null
        try {
//            inputStream = ctx.getContentResolver().openInputStream(uri)
            val exifInterface = ExifInterface(fileName)
            // Now you can extract any Exif tag you want
            // Assuming the image is a JPEG or supported raw format
            if (exifInterface != null)

                return GalleryExifModel(
                    exifInterface.getAttribute(ExifInterface.TAG_LIGHT_SOURCE),
                    exifInterface.getAttribute(ExifInterface.TAG_Y_RESOLUTION),
                    exifInterface.getAttribute(ExifInterface.TAG_PIXEL_X_DIMENSION),
                    exifInterface.getAttribute(ExifInterface.TAG_COMPRESSION),
                    exifInterface.getAttribute(ExifInterface.TAG_DATETIME),
                    exifInterface.getAttribute(ExifInterface.TAG_EXPOSURE_MODE),
                    exifInterface.getAttribute(ExifInterface.TAG_FLASH),
                    exifInterface.getAttribute(ExifInterface.TAG_FLASHPIX_VERSION),
                    exifInterface.getAttribute(ExifInterface.TAG_SCENE_CAPTURE_TYPE),
                    exifInterface.getAttribute(ExifInterface.TAG_DATETIME),
                    exifInterface.getAttribute(ExifInterface.TAG_METERING_MODE),
                    exifInterface.getAttribute(ExifInterface.TAG_EXIF_VERSION),
                    exifInterface.getAttribute(ExifInterface.TAG_JPEG_INTERCHANGE_FORMAT_LENGTH),
                    exifInterface.getAttribute(ExifInterface.TAG_IMAGE_UNIQUE_ID),
                    exifInterface.getAttribute(ExifInterface.TAG_EXPOSURE_BIAS_VALUE),
                    exifInterface.getAttribute(ExifInterface.TAG_MAKER_NOTE),
                    exifInterface.getAttribute(ExifInterface.TAG_EXPOSURE_PROGRAM),
                    exifInterface.getAttribute(ExifInterface.TAG_SHUTTER_SPEED_VALUE),
                    exifInterface.getAttribute(ExifInterface.TAG_COLOR_SPACE),
                    fileName,
                    exifInterface.getAttribute(ExifInterface.TAG_PIXEL_Y_DIMENSION),
                    fileSize,
                    exifInterface.getAttribute(ExifInterface.TAG_DATETIME_ORIGINAL),
                    exifInterface.getAttribute(ExifInterface.TAG_IMAGE_WIDTH),
                    exifInterface.getAttribute(ExifInterface.TAG_BRIGHTNESS_VALUE),
                    exifInterface.getAttribute(ExifInterface.TAG_MAKE),
                    exifInterface.getAttribute(ExifInterface.TAG_DATETIME_DIGITIZED),
                    exifInterface.getAttribute(ExifInterface.TAG_F_NUMBER),
                    exifInterface.getAttribute(ExifInterface.TAG_APERTURE_VALUE),
                    exifInterface.getAttribute(ExifInterface.TAG_FOCAL_LENGTH),
                    exifInterface.getAttribute(ExifInterface.TAG_THUMBNAIL_IMAGE_LENGTH),
                    exifInterface.getAttribute(ExifInterface.TAG_SOFTWARE),
                    exifInterface.getAttribute(ExifInterface.TAG_ISO_SPEED_RATINGS),
                    exifInterface.getAttribute(ExifInterface.TAG_MODEL),
                    exifInterface.getAttribute(ExifInterface.TAG_ORIENTATION),
                    exifInterface.getAttribute(ExifInterface.TAG_EXPOSURE_TIME),
                    exifInterface.getAttribute(ExifInterface.TAG_IMAGE_LENGTH),
                    exifInterface.getAttribute(ExifInterface.TAG_MAX_APERTURE_VALUE),
                    exifInterface.getAttribute(ExifInterface.TAG_THUMBNAIL_IMAGE_WIDTH),
                    exifInterface.getAttribute(ExifInterface.TAG_WHITE_BALANCE),
                    exifInterface.getAttribute(ExifInterface.TAG_JPEG_INTERCHANGE_FORMAT),
                    exifInterface.getAttribute(ExifInterface.TAG_Y_CB_CR_POSITIONING),
                    exifInterface.getAttribute(ExifInterface.TAG_X_RESOLUTION)
                )


        } catch (e: IOException) {
            // Handle any errors
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close()
                } catch (ignored: IOException) {
                }
            }
        }
        return null
    }


}