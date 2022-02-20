package com.andreick.contactsprovider

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.ContactsContract
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.andreick.contactsprovider.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) !=
            PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_CONTACTS),
                REQUEST_CONTACT
            )
        } else setContacts()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_CONTACT) setContacts()
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun setContacts() {
        val contactLst = arrayListOf<Contact>()
        val cursor = contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null, null, null, null
        )
        cursor?.run {
            while (moveToNext()) {
                contactLst.add(Contact(
                    getString(getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)),
                    getString(getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER))
                ))
            }
            close()
        }
        val adapter = ContactAdapter(contactLst)
        binding.rvContacts.adapter = adapter
        binding.rvContacts.layoutManager = LinearLayoutManager(this)
    }

    companion object {
        const val REQUEST_CONTACT = 0
    }
}