package com.andreick.room

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface EmployeeDao {
    @Insert
    suspend fun insert(employeeEntity: EmployeeEntity)

    @Update
    suspend fun update(employeeEntity: EmployeeEntity)

    @Delete
    suspend fun delete(employeeEntity: EmployeeEntity)

    @Query("SELECT * FROM employee")
    fun fetchAllEmployees(): Flow<List<EmployeeEntity>>

    @Query("SELECT * FROM employee WHERE id = :id")
    fun fetchEmployeeById(id: Int): Flow<EmployeeEntity?>
}