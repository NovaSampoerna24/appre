package id.go.patikab.rsud.remun.remunerasi.view.ubahfoto

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.View
import android.widget.Toast
import id.go.patikab.rsud.remun.remunerasi.R
import id.go.patikab.rsud.remun.remunerasi.data.api.ApiClient
import id.go.patikab.rsud.remun.remunerasi.data.api.ApiInterface
import id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse.ServerResponse
import kotlinx.android.synthetic.main.activity_ubah_foto.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

import id.go.patikab.rsud.remun.remunerasi.config.util.openhome
import id.go.patikab.rsud.remun.remunerasi.data.lokal.sharepreference.SharePref.pref
import id.go.patikab.rsud.remun.remunerasi.data.lokal.sharepreference.SharePref.foto_profil
import org.jetbrains.anko.indeterminateProgressDialog

private const val FILE_PICKER_ID = 12
private const val PERMISSION_REQUEST = 10

class UbahFoto : AppCompatActivity() {
    lateinit var mediaPath: String
    lateinit var str1: String
    lateinit var message: String
    var id_d = ""
    var namaDokter = ""
    private lateinit var context: Context
    private var permissions = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
    var v = false
    internal lateinit var mActionBarToolbar: Toolbar
    internal lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ubah_foto)

        mActionBarToolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(mActionBarToolbar)
        supportActionBar?.setTitle("Ubah Foto")


        context = this
        id_d = intent.getStringExtra("id_dokter")
        namaDokter = intent.getStringExtra("nama_dokter");

////        toast(id_d.toString())
//        btn_ambil_gambar.setOnClickListener {
//
//        }
        save_foto.setOnClickListener {
            if (v == false) toast("Pilih gambar terlebih dulu !") else uploadFile(id_d, namaDokter)
        }
        foto_profile.setOnClickListener {
            ambilGambarGalery()
        }
//        btn_simpan_profile.setOnClickListener({
//            uploadFile()
//
//        })
    }

    fun ambilGambarGalery() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkpermission(permissions)) {
//                    toast("Permission are already provider !")
                val galleryIntent = Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(galleryIntent, 0)
            } else {
                requestPermissions(permissions, PERMISSION_REQUEST)
            }
        } else {
//                toast("Permission are already provider !")
            val galleryIntent = Intent(Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(galleryIntent, 0)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        try {
            // When an Image is picked
            if (requestCode == 0 && resultCode == Activity.RESULT_OK && null != data) {

                //    imageview.setImageBitmap(bitmap);
                // Get the Image from data
                val selectedImage = data.data
                val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)

                val cursor = contentResolver.query(selectedImage!!, filePathColumn, null, null, null)!!
                cursor.moveToFirst()

                val columnIndex = cursor.getColumnIndex(filePathColumn[0])
                mediaPath = cursor.getString(columnIndex)
                str1 = mediaPath;
//                toast(str1)
                // Set the Image in ImageView for Previewing the Media
                val bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(selectedImage))
                v = true
                foto_profile.setImageBitmap(bitmap)
                cursor.close()

            } else {
                Toast.makeText(this, "You haven't picked Image/Video", Toast.LENGTH_LONG).show()
            }
        } catch (e: Exception) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show()
        }

    }

    // Uploading Image/Video
    fun uploadFile(id: String, nama: String) {
//        progressDialog.setMessage("Mengupload data...")
//        progressDialog.setCancelable(false)
//        progressDialog.show()
        // Map is used to multipart the file using okhttp3.RequestBody

        indeterminateProgressDialog("This a progress dialog").show()

        val file = File(mediaPath)
        Log.d("lval", mediaPath)
        // Parsing any Media type file
        val requestBody = RequestBody.create(MediaType.parse("*/*"), file)
        val fileToUpload = MultipartBody.Part.createFormData("foto", file.getName(), requestBody)
        val filename = RequestBody.create(MediaType.parse("text/plain"), file.getName())
        val iddokter = RequestBody.create(MediaType.parse("text/plain"), id)
        val nama_dokter = RequestBody.create(MediaType.parse("text/plain"), nama)

        var getResponse: ApiInterface
        getResponse = ApiClient.getClient().create(ApiInterface::class.java)
        val call: Call<ServerResponse>
        call = getResponse.uploadFile(fileToUpload, iddokter, filename, nama_dokter)

        call.enqueue(object : Callback<ServerResponse> {
            override fun onResponse(call: Call<ServerResponse>, response: Response<ServerResponse>) {
                message = response.body()?.message.toString()
                Log.d("response code", response.code().toString() + " --")
                if (response.isSuccessful) {
//                        progressDialog.dismiss()
                    toast(message)
                    finish()
                    openhome()
                } else {
//                    progressDialog.dismiss()
//                    toast(message)
                }

            }

            override fun onFailure(call: Call<ServerResponse>, t: Throwable) {
//                progressDialog.dismiss()
                Log.d("failure","failed")
            }
        })
    }

    fun checkpermission(permissionArray: Array<String>): Boolean {
        var allSuccess = true
        for (i in permissionArray.indices) {
            if (checkCallingOrSelfPermission(permissionArray[i]) == PackageManager.PERMISSION_DENIED) {
                allSuccess = false
            }
        }
        return allSuccess
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST) {
            var allSuccess = true

            for (i in permissions.indices) {
                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    allSuccess = false
                    var requestAgain = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && shouldShowRequestPermissionRationale(permissions[i])
                    if (requestAgain) {
                        toast("Permission tidak dibolehkan")
                    } else {
                        toast("Pergi ke pengaturan untuk mengaktifkan akses file")
                    }
                }
            }
            if (allSuccess)
                toast("Permission dibolehkan")
            ambilGambarGalery()
        }
    }

}