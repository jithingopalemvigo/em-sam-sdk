package com.begini.androidsdkv2.workerutil

import android.content.Context
import android.util.Base64
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.util.zip.GZIPInputStream
import java.util.zip.GZIPOutputStream
import java.util.zip.ZipOutputStream
import android.util.Log
import com.begini.androidsdkv2.util.AppConstants

import java.util.zip.ZipEntry

import java.io.FileInputStream

import java.io.File

import java.io.FileOutputStream




class CompressUtil {
    fun compressStringToGzip(string: String): ByteArray? {
        return try {
            val byteArrayOS = ByteArrayOutputStream(string.length)
            val gzipOutputStream = GZIPOutputStream(byteArrayOS)
            gzipOutputStream.write(string.toByteArray())
            gzipOutputStream.finish()
            gzipOutputStream.close()
            val compressed: ByteArray = byteArrayOS.toByteArray()
            byteArrayOS.close()
            compressed
        } catch (ex: IOException) {
            null
        }
    }

    fun decompressGzipToString(compressed: ByteArray?): String? {
        return try {
            val BUFFER_SIZE = 32
            val inputStream = ByteArrayInputStream(compressed)
            val gzipInputStream = GZIPInputStream(inputStream, BUFFER_SIZE)
            val data = ByteArray(BUFFER_SIZE)
            var bytesRead: Int
            val baos = ByteArrayOutputStream()
            while (gzipInputStream.read(data).also { bytesRead = it } != -1) {
                baos.write(data, 0, bytesRead)
            }
            gzipInputStream.close()
            inputStream.close()
            baos.toString("UTF-8")
        } catch (ex: IOException) {
            null
        }
    }

    // Base64
    fun compressToBase64(strToCompress: String?): String? {
        val compressed = compressStringToGzip(strToCompress!!)
        return Base64.encodeToString(compressed, Base64.NO_WRAP)
    }

    fun decompressFromBase64(strEncoded: String?): String? {
        val decoded = Base64.decode(strEncoded, Base64.NO_WRAP)
        return decompressGzipToString(decoded)
    }

      fun zipFolder(context:Context, outZipPath: String) :Boolean{
        try {
           val inputFolderPath=context.externalCacheDir?.absolutePath+AppConstants.LOCAL_FILES.unconverted_folder
            val fos = FileOutputStream(outZipPath)
            val zos = ZipOutputStream(fos)
            val srcFile = File(inputFolderPath)
            val files = srcFile.listFiles()
            Log.d("", "Zip directory: " + srcFile.name)
            for (i in files.indices) {
                Log.d("", "Adding file: " + files[i].name)
                val buffer = ByteArray(1024)
                val fis = FileInputStream(files[i])
                zos.putNextEntry(ZipEntry(files[i].name))
                var length: Int
                while (fis.read(buffer).also { length = it } > 0) {
                    zos.write(buffer, 0, length)
                }
                zos.closeEntry()
                fis.close()
            }
            zos.close()
            return true
        } catch (ioe: IOException) {
            Log.e("", ioe.message!!)
        }
          return false
    }
}