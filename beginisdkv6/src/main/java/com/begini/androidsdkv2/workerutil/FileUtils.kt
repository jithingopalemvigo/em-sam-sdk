package com.begini.androidsdkv2.workerutil

import android.content.Context
import android.util.Log
import com.begini.androidsdkv2.util.AppConstants
import java.io.File
import java.io.FileOutputStream


class FileUtils {
    val TAG = " FileUtils"
    fun getExternalPath(context: Context) {
        val path = context.getFilesDir();
        Log.e(TAG, "Path= " + path)
    }

    fun getExternalStorageCacheDirectory(context: Context): String {
        val path = context.externalCacheDir?.absolutePath+AppConstants.LOCAL_FILES.unconverted_folder/*"/unconverted_files/"*/
        var dir=makeDirectory(path)
        Log.e("Jithin ","creating dir: "+dir.toString())
        return path.toString()
    }

    fun makeDirectory(directoryPath: String): Boolean {
        var directory: File = File(directoryPath)
        if (directory.exists()) return true
        else return directory.mkdirs()
    }

    fun saveToFile(
        dataByteArray: ByteArray,
        file: File
    ):Boolean {
        try {
            if (!file.exists()) {
                file.createNewFile()
            }
            val fos = FileOutputStream(file)
            fos.write(dataByteArray)
            fos.close()
            return true
        } catch (e: Exception) {
            Log.e(TAG, e.message!!)
            return false
        }
    }

    fun saveJsonResultToFile(context: Context,content :String,fileName:String) {
        Log.e("Jithin "," Saving : "+fileName)
        saveToFile(
           content.toByteArray()/*CompressUtil().compressStringToGzip(content)!!*/ , File(
                 getExternalStorageCacheDirectory(
                    context
                ) +  fileName
            )
        )

    }

    fun  deleteAllGzipFiles(context: Context){
       deleteRecursive(File(
            getExternalStorageCacheDirectory(
                context
            )
        ))
    }
      fun deleteFinalZipFile(context: Context){
        val outputFolderPath = context.externalCacheDir?.absolutePath +  AppConstants.LOCAL_FILES.zip_folder
        deleteRecursive((File(outputFolderPath)))
    }
    private fun deleteRecursive(fileOrDirectory: File) {
        if (fileOrDirectory.isDirectory) for (child in fileOrDirectory.listFiles()) deleteRecursive(
            child
        )
        fileOrDirectory.delete()
    }
}