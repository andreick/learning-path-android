package com.andreick.room

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.andreick.room.databinding.ActivityMainBinding
import com.andreick.room.databinding.DialogUpdateBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val employeeDao = (application as EmployeeApp).database.employeeDao()

        binding.btnAddRecord.setOnClickListener {
            addRecord(employeeDao)
        }

        lifecycleScope.launch {
            employeeDao.fetchAllEmployees().collect {
                val list = ArrayList(it)
                displayDataIntoRecyclerView(list, employeeDao)
            }
        }
    }

    private fun addRecord(employeeDao: EmployeeDao) {
        val name = binding.etName.text.toString()
        val email = binding.etEmail.text.toString()

        if (name.isNotBlank() && email.isNotBlank()) {
            lifecycleScope.launch {
                employeeDao.insert(EmployeeEntity(name = name, email = email))
                Toast.makeText(applicationContext, "Record saved", Toast.LENGTH_SHORT).show()
            }
            binding.etName.text.clear()
            binding.etEmail.text.clear()
        } else {
            Toast.makeText(this, "Name or email cannot be blank", Toast.LENGTH_SHORT).show()
        }
    }

    private fun displayDataIntoRecyclerView(
        employeeList: ArrayList<EmployeeEntity>,
        employeeDao: EmployeeDao
    ) {
        if (employeeList.isNotEmpty()) {
            val itemAdapter = ItemAdapter(
                employeeList,
                { updateId -> updateRecordDialog(updateId, employeeDao) },
                { deleteId -> deleteRecordAlertDialog(deleteId, employeeDao) }
            )
            binding.rvRecords.layoutManager = LinearLayoutManager(this)
            binding.rvRecords.adapter = itemAdapter
            binding.rvRecords.visibility = View.VISIBLE
            binding.tvNoRecordsAvailable.visibility = View.GONE
        } else {
            binding.rvRecords.visibility = View.INVISIBLE
            binding.tvNoRecordsAvailable.visibility = View.VISIBLE
        }
    }

    private fun updateRecordDialog(id: Int, employeeDao: EmployeeDao) {
        val updateDialog = Dialog(this, R.style.Theme_Dialog)
        val binding = DialogUpdateBinding.inflate(layoutInflater)
        updateDialog.setContentView(binding.root)

        lifecycleScope.launch {
            employeeDao.fetchEmployeeById(id).collect {
                it?.let {
                    binding.etName.setText(it.name)
                    binding.etEmail.setText(it.email)
                }
            }
        }

        binding.tvUpdate.setOnClickListener {
            val name = binding.etName.text.toString()
            val email = binding.etEmail.text.toString()

            if (name.isNotBlank() && email.isNotBlank()) {
                lifecycleScope.launch {
                    employeeDao.update(EmployeeEntity(id, name, email))
                    Toast.makeText(
                        applicationContext,
                        "Record Updated",
                        Toast.LENGTH_SHORT
                    ).show()
                    updateDialog.dismiss()
                }
            } else {
                Toast.makeText(this, "Name or email cannot be blank", Toast.LENGTH_SHORT).show()
            }
        }

        binding.tvCancel.setOnClickListener { updateDialog.dismiss() }

        updateDialog.setCancelable(false)
        updateDialog.show()
    }

    private fun deleteRecordAlertDialog(id: Int, employeeDao: EmployeeDao) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Delete Record")
        builder.setIcon(android.R.drawable.ic_dialog_alert)
        builder.setPositiveButton("Yes") { dialogInterface, _ ->
            lifecycleScope.launch {
                employeeDao.delete(EmployeeEntity(id))
                Toast.makeText(
                    applicationContext,
                    "Record deleted successfully",
                    Toast.LENGTH_SHORT
                ).show()
            }
            dialogInterface.dismiss()
        }
        builder.setNegativeButton("No") { dialogInterface, _ -> dialogInterface.dismiss() }

        val deleteDialog = builder.create()
        deleteDialog.setCancelable(false)
        deleteDialog.show()
    }
}