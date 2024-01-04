package com.example.mywaycompose.utils

import com.example.mywaycompose.domain.model.DateServer
import java.time.LocalDate

object DateService {

    fun convertDateToProduct(date:DateServer):String{

        val month = when(date.month.toInt()){
            1 ->  "Январь"
            2 ->  "Февраль"
            3 ->  "Март"
            4 ->  "Апрель"
            5 ->  "Май"
            6 ->  "Июнь"
            7 ->  "Июль"
            8 ->  "Август"
            9 ->  "Сентябрь"
            10 ->  "Октябрь"
            11 ->  "Ноябрь"
            12 ->  "Декабрь"
            else ->  "None"
        }
        return "${date.day} $month"
    }

    fun localDateToDateServer(localDate: LocalDate):DateServer{
        return DateServer(
            day = if(localDate.dayOfMonth < 10) "0${localDate.dayOfMonth}" else localDate.dayOfMonth.toString(),
            month = if(localDate.monthValue.toString().length==1)"0${localDate.monthValue}" else localDate.monthValue.toString(),
            year = localDate.year.toString()
        )
    }
}

fun String.toTimeFormatString():String{
    val splitedString = this.split(":")
    val firstPart = (if(splitedString[0].length == 1) "0" else "") + splitedString[0]
    val secondPart = (if(splitedString[1].length == 1) "0" else "") + splitedString[1]
    return "$firstPart:$secondPart"
}