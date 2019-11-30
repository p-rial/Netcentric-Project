package com.example.iq180

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.location.LocationManager
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.view.Menu
import android.view.MenuItem
import com.example.iq180.lobby.LobbyFragment
import com.example.iq180.lobby.WelcomeFragment
import kotlinx.android.synthetic.main.activity_game.*
import java.io.*
import android.graphics.drawable.BitmapDrawable
import android.R.attr.data
import android.support.v4.app.NotificationCompat.getExtras
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private var uri: Uri? = null

    private val REQUEST_CAMERA = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var name = intent.getStringExtra("name")

        if (savedInstanceState == null) {

            supportFragmentManager.beginTransaction()
                    .add(R.id.contentContainer, WelcomeFragment.newInstance(name), "WelcomeFragment")
                    .commit()

            Handler().postDelayed({
                supportFragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.from_right, R.anim.to_left)
                        .replace(R.id.contentContainer, LobbyFragment.newInstance(name), "LobbyFragment")
                        .commit()
            }, 3000)

        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_CAMERA -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initial()
                }

            }
        }

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        cameraIntent()
        return super.onOptionsItemSelected(item)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.mymenumain, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun cameraIntent() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, REQUEST_CAMERA)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CAMERA) {
            val extras = data.extras
            if (extras != null) {
                val photo = extras.get("data") as Bitmap
                if (photo != null) {
                    // mView should refer to view whose reference is obtained in onCreate() using findViewById(), and whose background you want to update
                    contentContainer.setBackground(BitmapDrawable(resources, photo))
                }
            }
        }
    }


    private fun initial() {

        val permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 1001)
        } else {
            val p = this.getSharedPreferences("rial", Context.MODE_PRIVATE)
            val s = p.getString("image", "Image Not Found")
            uri = Uri.parse(s)
            background.setImageURI(uri)
        }

    }

    private fun onCaptureImageResult(data: Intent) {
        val thumbnail = data.extras!!.get("data") as Bitmap
        val bytes = ByteArrayOutputStream()
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes)

        val destination = File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis().toString() + ".jpg")

        val fo: FileOutputStream
        try {
            destination.createNewFile()
            fo = FileOutputStream(destination)
            fo.write(bytes.toByteArray())
            fo.close()
//            uri = Uri.fromFile(destination)

        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        background.setImageBitmap(thumbnail)
    }
}




