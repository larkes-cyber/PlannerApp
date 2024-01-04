package com.example.mywaycompose.data.source.service

import android.graphics.Bitmap
import android.net.Uri
import java.io.File

interface ServiceImageStorageDataSource {
    fun convertUriToBitmap(uri: Uri): Bitmap
    fun convertBitmapToFile(bitmap: Bitmap): File
    fun getTypeOfImage(uri: Uri):String
    suspend fun saveImage(uri: Uri, id:String)
    fun getImageId():Int
    fun getImageFileById(id:String): File
    fun deleteFile(file: File)
    fun convertByteArrayToBitmap(byteArray: ByteArray): Bitmap
    fun saveByteArrayAsFile(id:String, byteArray: ByteArray)
}