package com.example.mywaycompose.domain.usecase.local_service

import com.example.mywaycompose.domain.model.DateServer
import com.example.mywaycompose.domain.repository.ServiceRepository
import javax.inject.Inject

class UseGetListOfMonthDays @Inject constructor(
    private val serviceRepository: ServiceRepository
) {
    fun execute(dateServer: DateServer):List<Pair<DateServer, String>>{
        val countOfDays = serviceRepository.getCountOfDaysByDate(year = dateServer.year.toInt(), month = dateServer.month.toInt())
        val monthName = serviceRepository.getCurrentMonthName(dateServer.month.toInt())
        val outputDays = ArrayList<Pair<DateServer, String>>()

        for(i in 1..countOfDays){
            val date = DateServer(
                year = dateServer.year,
                month = dateServer.month,
                day = if(i < 10) "0$i" else i.toString()
            )
            outputDays.add(Pair(date, monthName))
        }
        return outputDays
    }
}