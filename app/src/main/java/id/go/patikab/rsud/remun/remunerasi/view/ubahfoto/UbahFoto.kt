package id.go.patikab.rsud.remun.remunerasi.view.ubahfoto

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import id.go.patikab.rsud.remun.remunerasi.R
import id.go.patikab.rsud.remun.remunerasi.data.api.ApiClient
import id.go.patikab.rsud.remun.remunerasi.data.api.ApiInterface
import id.go.patikab.rsud.remun.remunerasi.data.api.ServerResponse
import kotlinx.android.synthetic.main.ubah_foto_profil.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

private const val FILE_PICKER_ID = 12
private const val PERMISSION_REQUEST = 10

class UbahFoto : AppCompatActivity() {
    lateinit var mediaPath: String
    lateinit var str1: String
    var id_d: String? = null
    var namaDokter: String? = null
    lateinit var message: String
    private lateinit var context: Context
    private var permissions = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ubah_foto_profil)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        context=this
        id_d = intent?.getStringExtra("id_dokter")
        namaDokter = intent?.getStringExtra("nama_dokter");

//        toast(id_d.toString())
        btn_ambil_gambar.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkpermission(context, permissions)) {
//                    toast("Permission are already provider !")
                    val galleryIntent = Intent(Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    startActivityForResult(galleryIntent, 0)
                } else {
                    requestPermissions(permissions, PERMISSION_REQUEST)
                }
            }else{
//                toast("Permission are already provider !")
                val galleryIntent = Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(galleryIntent, 0)
            }


        }

//        btn_simpan_profile.setOnClickListener({
//            uploadFile()
//
//        })
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

                foto_profile.setImageBitmap(bitmap)
                cursor.close()

                uploadFile()

            } // When an Video is picked
            else {
                Toast.makeText(this, "You haven't picked Image/Video", Toast.LENGTH_LONG).show()
            }
        } catch (e: Exception) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show()
        }

    }

    // Uploading Image/Video
    private fun uploadFile() {

        // Map is used to multipart the file using okhttp3.RequestBody
        val file = File(mediaPath)
        Log.d("lval", mediaPath)
        // Parsing any Media type file
        val requestBody = RequestBody.create(MediaType.parse("*/*"), file)
        val fileToUpload = MultipartBody.Part.createFormData("foto", file.getName(), requestBody)
        val filename = RequestBody.create(MediaType.parse("text/plain"), file.getName())
        val iddokter = RequestBody.create(MediaType.parse("text/plain"), id_d)
        val nama_dokter = RequestBody.create(MediaType.parse("text/plain"), namaDokter)

        var getResponse: ApiInterface
        getResponse = ApiClient.getClient().create(ApiInterface::class.java)
        val call: Call<ServerResponse>
        call = getResponse.uploadFile(fileToUpload, iddokter, filename, nama_dokter)

        call.enqueue(object : Callback<ServerResponse> {
            override fun onResponse(call: Call<ServerResponse>, response: Response<ServerResponse>) {
                message = response.body()?.message.toString()
                if (response?.isSuccessful) {
                    toast(message)
                    finish()
                } else {
                    toast(message)
                }
            }

            override fun onFailure(call: Call<ServerResponse>, t: Throwable) {
                toast(t.message + " ")

            }
        })
    }

    fun checkpermission(context: Context, permissionArray: Array<String>): Boolean {
        var allSuccess = true
        for (i in permissionArray.indices){
            if(checkCallingOrSelfPermission(permissionArray[i])== PackageManager.PERMISSION_DENIED){
                allSuccess =false

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
                        toast("Permission denied")
                    } else {
                        toast("Go to setting and enable the permission ")
                    }
                }
            }
            if (allSuccess)
                toast("Permission granted")
        }
    }

}