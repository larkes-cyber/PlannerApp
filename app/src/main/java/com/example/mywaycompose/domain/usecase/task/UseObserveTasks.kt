package com.example.mywaycompose.domain.usecase.task

import com.example.mywaycompose.domain.mapper.toTask
import com.example.mywaycompose.domain.model.Task
import com.example.mywaycompose.domain.repository.ServiceRepository
import com.example.mywaycompose.domain.repository.TaskRepository
import com.example.mywaycompose.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

import javax.inject.Inject

class UseObserveTasks @Inject constructor(
    private val taskRepository: TaskRepository,
    private val serviceRepository: ServiceRepository
    ) {

      operator fun invoke(date:String):Flow<Resource<List<Task>>> = flow{
          val timeComparator = compareBy<Task> { task ->
              val (hours, minutes) = task.time.split(":").map { it.toInt() }
              hours * 60 + minutes
          }
          try {
              emit(Resource.Loading())

              emit(Resource.Success(
                  taskRepository.observeTasksByDate(date).map { it.toTask() }.sortedWith(timeComparator)
              ))
          }catch (e:Exception){
              emit(Resource.Success(listOf()))
          }

     }

}