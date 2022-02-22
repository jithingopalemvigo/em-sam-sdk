package com.begini.androidsdkv2.workerutil

import android.content.ContentResolver
import android.content.Context
import android.provider.MediaStore
import android.util.Log
import java.io.IOException
import com.begini.androidsdkv2.model.devicedata.MediaMetaDataModel


class MediaMetadataUtil {
    fun getAllMediaInfo(context: Context): MutableList<MediaMetaDataModel> {
        val result: MutableList<MediaMetaDataModel> = arrayListOf()
        result.addAll(getAllImageInfo(context))
        result.addAll(getAllAudioInfo(context))
        result.addAll(getAllVideoInfo(context))
        return result
    }

    fun getAllVideoInfo(context: Context): MutableList<MediaMetaDataModel> {
        val result: MutableList<MediaMetaDataModel> = arrayListOf()
        try {
            val cr: ContentResolver = context.getContentResolver()
            val columns = arrayOf(
                MediaStore.Video.VideoColumns.ORIENTATION,
                MediaStore.Video.VideoColumns.IS_DRM,
                MediaStore.Video.VideoColumns.HEIGHT,
                MediaStore.Video.VideoColumns.IS_FAVORITE,
                MediaStore.Video.VideoColumns.SIZE,
                MediaStore.Video.VideoColumns.TITLE,
                MediaStore.Video.VideoColumns.BUCKET_DISPLAY_NAME,
                MediaStore.Video.VideoColumns.MIME_TYPE,
                MediaStore.Video.VideoColumns.DATE_ADDED,
                MediaStore.Video.VideoColumns.DATA,
                MediaStore.Video.VideoColumns._ID,
                MediaStore.Video.VideoColumns.DATE_MODIFIED,
                MediaStore.Video.VideoColumns.MINI_THUMB_MAGIC,
                MediaStore.Video.VideoColumns.BUCKET_ID,
                MediaStore.Video.VideoColumns.DISPLAY_NAME,
            )
            val cursor = cr.query(
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                columns, null, null, null
            )
            Log.e("Gallery", "Cursor count ${cursor!!.count}")
var i=0
            if (cursor!!.moveToFirst()) {
                do {
                    i++
                    try {

                        val orientation =
                            cursor.getString(cursor!!.getColumnIndexOrThrow(MediaStore.Video.VideoColumns.ORIENTATION))
                        val isDRM =
                            cursor.getString(cursor!!.getColumnIndexOrThrow(MediaStore.Video.VideoColumns.IS_DRM))
                        val height =
                            cursor.getString(cursor!!.getColumnIndexOrThrow(MediaStore.Video.VideoColumns.HEIGHT))
                        val isFavorite =
                            cursor.getString(cursor!!.getColumnIndexOrThrow(MediaStore.Video.VideoColumns.IS_FAVORITE))
                        val size =
                            cursor.getString(cursor!!.getColumnIndexOrThrow(MediaStore.Video.VideoColumns.SIZE))
                        val title =
                            cursor.getString(cursor!!.getColumnIndexOrThrow(MediaStore.Video.VideoColumns.TITLE))
                        val bucketDisplayName =
                            cursor.getString(cursor!!.getColumnIndexOrThrow(MediaStore.Video.VideoColumns.BUCKET_DISPLAY_NAME))
                        val mimeType =
                            cursor.getString(cursor!!.getColumnIndexOrThrow(MediaStore.Video.VideoColumns.MIME_TYPE))
                        val dateAdded =
                            cursor.getString(cursor!!.getColumnIndexOrThrow(MediaStore.Video.VideoColumns.DATE_ADDED))
                        val data =
                            cursor.getString(cursor!!.getColumnIndexOrThrow(MediaStore.Video.VideoColumns.DATA))
                        val id =
                            cursor.getString(cursor!!.getColumnIndexOrThrow(MediaStore.Video.VideoColumns._ID))
                        val dateModified =
                            cursor.getString(cursor!!.getColumnIndexOrThrow(MediaStore.Video.VideoColumns.DATE_MODIFIED))
                        val miniThumbMagic =
                            cursor.getString(cursor!!.getColumnIndexOrThrow(MediaStore.Video.VideoColumns.MINI_THUMB_MAGIC))
                        val bucketId =
                            cursor.getString(cursor!!.getColumnIndexOrThrow(MediaStore.Video.VideoColumns.BUCKET_ID))
                        val displayName =
                            cursor.getString(cursor!!.getColumnIndexOrThrow(MediaStore.Video.VideoColumns.DISPLAY_NAME))
                        result.add(
                            MediaMetaDataModel(
                                orientation, isDRM, height,
                                isFavorite, size, title, bucketDisplayName, mimeType, dateAdded,
                                data, id, dateModified, miniThumbMagic, bucketId, displayName
                            )
                        )

                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
//                    if(i==20)break

                } while (cursor!!.moveToNext())
            }
            cursor!!.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return result
    }

    fun getAllImageInfo(context: Context): MutableList<MediaMetaDataModel> {
        val result: MutableList<MediaMetaDataModel> = arrayListOf()
        try {
            val cr: ContentResolver = context.getContentResolver()
            val columns = arrayOf(
                MediaStore.Images.ImageColumns.ORIENTATION,
                MediaStore.Images.ImageColumns.IS_DRM,
                MediaStore.Images.ImageColumns.HEIGHT,
                MediaStore.Images.ImageColumns.IS_FAVORITE,
                MediaStore.Images.ImageColumns.SIZE,
                MediaStore.Images.ImageColumns.TITLE,
                MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME,
                MediaStore.Images.ImageColumns.MIME_TYPE,
                MediaStore.Images.ImageColumns.DATE_ADDED,
                MediaStore.Images.ImageColumns.DATA,
                MediaStore.Images.ImageColumns._ID,
                MediaStore.Images.ImageColumns.DATE_MODIFIED,
                MediaStore.Images.ImageColumns.MINI_THUMB_MAGIC,
                MediaStore.Images.ImageColumns.BUCKET_ID,
                MediaStore.Images.ImageColumns.DISPLAY_NAME,
            )
            val cursor = cr.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                columns, null, null, null
            )
            Log.e("Gallery", "Cursor count ${cursor!!.count}")
var i=0
            if (cursor!!.moveToFirst()) {
                do {
                    i++
                    try {

                        val orientation =
                            cursor.getString(cursor!!.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.ORIENTATION))
                        val isDRM =
                            cursor.getString(cursor!!.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.IS_DRM))
                        val height =
                            cursor.getString(cursor!!.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.HEIGHT))
                        val isFavorite =
                            cursor.getString(cursor!!.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.IS_FAVORITE))
                        val size =
                            cursor.getString(cursor!!.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.SIZE))
                        val title =
                            cursor.getString(cursor!!.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.TITLE))
                        val bucketDisplayName =
                            cursor.getString(cursor!!.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME))
                        val mimeType =
                            cursor.getString(cursor!!.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.MIME_TYPE))
                        val dateAdded =
                            cursor.getString(cursor!!.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.DATE_ADDED))
                        val data =
                            cursor.getString(cursor!!.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.DATA))
                        val id =
                            cursor.getString(cursor!!.getColumnIndexOrThrow(MediaStore.Images.ImageColumns._ID))
                        val dateModified =
                            cursor.getString(cursor!!.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.DATE_MODIFIED))
                        val miniThumbMagic =
                            cursor.getString(cursor!!.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.MINI_THUMB_MAGIC))
                        val bucketId =
                            cursor.getString(cursor!!.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.BUCKET_ID))
                        val displayName =
                            cursor.getString(cursor!!.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.DISPLAY_NAME))
                        result.add(
                            MediaMetaDataModel(
                                orientation, isDRM, height,
                                isFavorite, size, title, bucketDisplayName, mimeType, dateAdded,
                                data, id, dateModified, miniThumbMagic, bucketId, displayName
                            )
                        )

                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
//                    if(i==20) break
                } while (cursor!!.moveToNext())
            }
            cursor!!.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return result
    }

    fun getAllAudioInfo(context: Context): MutableList<MediaMetaDataModel> {
        val result: MutableList<MediaMetaDataModel> = arrayListOf()
        try {
            val cr: ContentResolver = context.getContentResolver()
            val columns = arrayOf(
                MediaStore.Audio.AudioColumns.ORIENTATION,
                MediaStore.Audio.AudioColumns.IS_DRM,
                MediaStore.Audio.AudioColumns.HEIGHT,
                MediaStore.Audio.AudioColumns.IS_FAVORITE,
                MediaStore.Audio.AudioColumns.SIZE,
                MediaStore.Audio.AudioColumns.TITLE,
                MediaStore.Audio.AudioColumns.BUCKET_DISPLAY_NAME,
                MediaStore.Audio.AudioColumns.MIME_TYPE,
                MediaStore.Audio.AudioColumns.DATE_ADDED,
                MediaStore.Audio.AudioColumns.DATA,
                MediaStore.Audio.AudioColumns._ID,
                MediaStore.Audio.AudioColumns.DATE_MODIFIED,
                MediaStore.Audio.AudioColumns.BUCKET_ID,
                MediaStore.Audio.AudioColumns.DISPLAY_NAME,
            )
            val cursor = cr.query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                columns, null, null, null
            )
            Log.e("Gallery", "Cursor count ${cursor!!.count}")

            if (cursor!!.moveToFirst()) {
                var i=0
                do {
                    i++
                    try {

                        val orientation =
                            cursor.getString(cursor!!.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.ORIENTATION))
                        val isDRM =
                            cursor.getString(cursor!!.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.IS_DRM))
                        val height =
                            cursor.getString(cursor!!.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.HEIGHT))
                        val isFavorite =
                            cursor.getString(cursor!!.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.IS_FAVORITE))
                        val size =
                            cursor.getString(cursor!!.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.SIZE))
                        val title =
                            cursor.getString(cursor!!.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.TITLE))
                        val bucketDisplayName =
                            cursor.getString(cursor!!.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.BUCKET_DISPLAY_NAME))
                        val mimeType =
                            cursor.getString(cursor!!.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.MIME_TYPE))
                        val dateAdded =
                            cursor.getString(cursor!!.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.DATE_ADDED))
                        val data =
                            cursor.getString(cursor!!.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.DATA))
                        val id =
                            cursor.getString(cursor!!.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns._ID))
                        val dateModified =
                            cursor.getString(cursor!!.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.DATE_MODIFIED))
                        val miniThumbMagic = null
                        val bucketId =
                            cursor.getString(cursor!!.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.BUCKET_ID))
                        val displayName =
                            cursor.getString(cursor!!.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.DISPLAY_NAME))
                        result.add(
                            MediaMetaDataModel(
                                orientation, isDRM, height,
                                isFavorite, size, title, bucketDisplayName, mimeType, dateAdded,
                                data, id, dateModified, miniThumbMagic, bucketId, displayName
                            )
                        )

                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
//                    if(i==20)break
                } while (cursor!!.moveToNext())
            }
            cursor!!.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return result
    }


}