package com.example.mywaycompose.data.source.service

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import java.io.*

class ServiceImageStorageDataSourceImpl(
    private val context: Context
):ServiceImageStorageDataSource {
    private val sharedPreferences =
        context.getSharedPreferences("ImageData", Context.MODE_PRIVATE)

    override fun convertUriToBitmap(uri: Uri): Bitmap {
        var bitmap:Bitmap? = null

        bitmap = if (Build.VERSION.SDK_INT < 28) {
            MediaStore.Images
                .Media.getBitmap(context.contentResolver, uri)

        } else {
            val source = ImageDecoder
                .createSource(context.contentResolver, uri)
            ImageDecoder.decodeBitmap(source)
        }

        return bitmap!!
    }

    override fun convertBitmapToFile(bitmap: Bitmap): File {
        val f = File(context.getCacheDir(), "filename");
        f.createNewFile();

        val bos = ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, bos);
        val bitmapdata = bos.toByteArray();

        val fos = FileOutputStream(f);
        fos.write(bitmapdata);
        fos.flush();
        fos.close();

        return f
    }

    override fun getTypeOfImage(uri: Uri): String {
        val cR = context.contentResolver
        val mime = cR.getType(uri).toString()
        return mime.split("/")[1]
    }

    override suspend fun saveImage(uri: Uri, id:String) {

        val f = File(context.cacheDir, id.toString())
        val os: OutputStream = BufferedOutputStream(FileOutputStream(f))
        var bitmap = convertUriToBitmap(uri)
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, os)
        os.close()
    }

    override fun getImageId(): Int {
        if(sharedPreferences.getInt("id", -1) == -1){
            sharedPreferences.edit().putInt("id",1).apply()
            return 1
        }
        val nextId = sharedPreferences.getInt("id", -1) + 1
        sharedPreferences.edit().putInt("id",nextId).apply()
        return nextId
    }

    override fun getImageFileById(id: String): File {
        return File("${context.cacheDir}/${id}")
    }

    override fun deleteFile(file: File) {
        file.delete()
    }

    override fun convertByteArrayToBitmap(byteArray: ByteArray):Bitmap {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    }

    override fun saveByteArrayAsFile(id: String, byteArray: ByteArray) {
        val f = File(context.cacheDir, id.toString())
        val os: OutputStream = BufferedOutputStream(FileOutputStream(f))
        val bitmap = convertByteArrayToBitmap(byteArray)
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, os)
        os.close()
    }
}