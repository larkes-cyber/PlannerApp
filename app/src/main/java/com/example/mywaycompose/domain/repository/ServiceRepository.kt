package com.example.mywaycompose.domain.repository

import android.graphics.Bitmap
import android.net.Uri
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.flow.Flow
import java.io.File
import java.time.LocalDate

interface ServiceRepository {
    fun saveFirstDate(date:String)
    fun getFirstDate():String

    fun getCountOfDaysByDate(year:Int, month:Int): Int
    fun getCurrentMonthName(month:Int):String
    fun checkTimeCorrect(time:String):Boolean
    fun getCurrentDate(): LocalDate
    fun dateToDayOfWeek(localDate: LocalDate):String
    fun compareDateWithCurrent(localDate: LocalDate, kind:Int):Boolean
    fun checkDateInRange(firstDate:String, secondDate:String, checkableDate:String):Boolean
    fun getDatesInRange(firstDate: String, secondDate: String):List<String>

    fun convertUriToBitmap(uri: Uri): Bitmap
    fun convertBitmapToFile(bitmap: Bitmap): File
    fun getTypeOfImage(uri: Uri):String
    suspend fun saveImage(uri: Uri, id:String)
    fun getImageFileById(id:String): File
    fun convertFileToByteArray(file: File):ByteArray
    fun deleteFile(file: File)
    fun saveByteArrayAsFile(id:String, byteArray: ByteArray)

    fun putActuallyMainTaskId(id:Int)
    fun getActuallyMainTaskId():Int

    fun putActuallyVisualTaskId(id:Int)
    fun getActuallyVisualTaskId():Int

    fun saveAppTheme(theme:String)
    fun getAppTheme():String

    fun pushActuallyMainTaskId(id:Int)
    fun deletePhotoById(id:String)
    fun getImageReference(id:String): StorageReference
    fun getCurrentIdBySomeModel(kind:String): Flow<Int>
    fun pushActuallyVisualTaskId(id:Int)

    fun saveFirebaseImage(id:String)
}