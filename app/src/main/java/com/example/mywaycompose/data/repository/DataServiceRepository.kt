package com.example.mywaycompose.data.repository

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import com.example.mywaycompose.data.source.service.ServiceImageStorageDataSource
import com.example.mywaycompose.data.source.service.ServiceRemoteDataSource
import com.example.mywaycompose.data.source.service.ServiceSharedPreferenceDataSource
import com.example.mywaycompose.data.source.user.UserSharedPreferenceDataSource
import com.example.mywaycompose.domain.repository.ServiceRepository
import com.example.mywaycompose.utils.Constants
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.flow.Flow
import java.io.File
import java.time.LocalDate
import java.time.LocalTime
import java.time.YearMonth
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date

class DataServiceRepository(
    private val serviceImageStorageDataSource: ServiceImageStorageDataSource,
    private val serviceSharedPreferenceDataSource: ServiceSharedPreferenceDataSource,
    private val serviceRemoteDataSource: ServiceRemoteDataSource,
    private val userSharedPreferenceDataSource: UserSharedPreferenceDataSource
): ServiceRepository {

    override fun saveFirstDate(date: String) {
        userSharedPreferenceDataSource.saveFirstDate(date)
    }

    override fun getFirstDate(): String {
        return userSharedPreferenceDataSource.getFirstDate()
    }

    override fun getCountOfDaysByDate(year: Int, month: Int): Int {
        val yearMonthObject = YearMonth.of(year, month) // TODO: fix getting days of month
        return yearMonthObject.lengthOfMonth()
    }

    override fun getCurrentMonthName(month: Int): String {
        when(month){
            1 -> return "Января"
            2 -> return "Февраля"
            3 -> return "Марта"
            4 -> return "Апреля"
            5 -> return "Мая"
            6 -> return "Июня"
            7 -> return "Июля"
            8 -> return "Августа"
            9 -> return "Сентября"
            10 -> return "Октября"
            11 -> return "Ноября"
            12 -> return "Декабря"
            else -> return "None"

        }
    }

    override fun checkTimeCorrect(time: String): Boolean {
        Log.d("parse_time",time)
        return try{
            val parser = DateTimeFormatter.ofPattern(if(time.length == 5) "HH:mm" else "H:mm")
            val localTime = LocalTime.parse(time, parser)
            true
        }catch (e:Exception){
            Log.d("parse_time",e.toString())
            false
        }

    }

    override fun getCurrentDate(): LocalDate {
        return LocalDate.now()
    }

    override fun dateToDayOfWeek(localDate: LocalDate): String {
        return when(localDate.dayOfWeek.value){
            1 -> "Пн"
            2 -> "Вт"
            3 -> "Ср"
            4 -> "Чт"
            5 -> "Пт"
            6 -> "Сб"
            7 -> "Вс"
            else -> "lol"
        }
    }

    override fun compareDateWithCurrent(localDate: LocalDate, kind: Int): Boolean {
        val current = getCurrentDate().toEpochDay()
        val actuallyDate = localDate.toEpochDay()

        return when(kind){
            Constants.MoreThenCurrentDate -> actuallyDate > current
            Constants.SmallThenCurrentDate -> actuallyDate < current
            else -> actuallyDate == current
        }

    }

    override fun checkDateInRange(firstDate: String, secondDate: String, checkableDate:String): Boolean {
        val date1 = firstDate.toLocalDate().toEpochDay()
        val date2 = secondDate.toLocalDate().toEpochDay()
        val currentDate = checkableDate.toLocalDate().toEpochDay()
        return (date1 <= currentDate) && (currentDate <= date2)
    }

    override fun getDatesInRange(firstDate: String, secondDate: String): List<String> {
        val date1 = firstDate.toLocalDate().minusDays(1)
        var date2 = secondDate.toLocalDate()
        Log.d("dates_first","first: $firstDate  second:$secondDate")
        val output = mutableListOf<String>()

        while (date1 != date2){
            output.add(date2.toDateString())
            date2 = date2.minusDays(1)
        }

        return output.reversed()
    }

    override fun convertUriToBitmap(uri: Uri): Bitmap {
        return serviceImageStorageDataSource.convertUriToBitmap(uri)
    }

    override fun convertBitmapToFile(bitmap: Bitmap): File {
        return serviceImageStorageDataSource.convertBitmapToFile(bitmap)
    }

    override fun getTypeOfImage(uri: Uri): String {
        return serviceImageStorageDataSource.getTypeOfImage(uri)
    }

    override suspend fun saveImage(uri: Uri, id:String) {
        serviceImageStorageDataSource.saveImage(uri,id)
    }

    override fun getImageFileById(id: String): File {
        return serviceImageStorageDataSource.getImageFileById(id)
    }

    override fun convertFileToByteArray(file: File): ByteArray {
        return file.readBytes()
    }

    override fun deleteFile(file: File) {
        serviceImageStorageDataSource.deleteFile(file)
    }

    override fun saveByteArrayAsFile(id: String, byteArray: ByteArray) {
        serviceImageStorageDataSource.saveByteArrayAsFile(id, byteArray)
    }

    override fun putActuallyMainTaskId(id: Int) {
        serviceSharedPreferenceDataSource.putActuallyMainTaskId(id)
    }

    override fun getActuallyMainTaskId(): Int {
        return serviceSharedPreferenceDataSource.getActuallyMainTaskId()
    }


    override fun putActuallyVisualTaskId(id: Int) {
        serviceSharedPreferenceDataSource.putActuallyVisualTaskId(id)
    }

    override fun getActuallyVisualTaskId(): Int {
        return serviceSharedPreferenceDataSource.getActuallyVisualTaskId()
    }

    override fun saveAppTheme(theme: String) {
        serviceSharedPreferenceDataSource.saveAppTheme(theme)
    }

    override fun getAppTheme(): String {
        return serviceSharedPreferenceDataSource.getAppTheme()
    }

    override fun pushActuallyMainTaskId(id: Int) {
        serviceRemoteDataSource.putActuallyMainTaskId(id)
    }

    override fun deletePhotoById(id: String) {
        serviceRemoteDataSource.deletePhotoById(id)
    }

    override fun getImageReference(id: String): StorageReference {
        return serviceRemoteDataSource.getImageReference(id)
    }

    override fun getCurrentIdBySomeModel(kind: String): Flow<Int> {
        return serviceRemoteDataSource.getCurrentIdBySomeModel(kind)
    }


    override fun pushActuallyVisualTaskId(id: Int) {
        serviceRemoteDataSource.putActuallyVisualTaskId(id)
    }

    override fun saveFirebaseImage(id: String) {
        val ref = getImageReference(id)

        val limit: Long = 1024 * 1024 * 10
        ref.getBytes(limit).addOnSuccessListener {
            saveByteArrayAsFile(id, it)
        }
    }

}

fun String.toLocalDate():LocalDate{
    val dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    return LocalDate.parse(this, dtf)
}


fun LocalDate.toDateString():String{

    val day = this.dayOfMonth
    val month = this.monthValue
    val year = this.year

    return "${if(day < 10) "0$day" else day}-${if(month < 10) "0$month" else month}-$year"
}

fun Date.toLocalDate(): LocalDate {
    return toInstant()
        .atZone(ZoneId.systemDefault())
        .toLocalDate()
}