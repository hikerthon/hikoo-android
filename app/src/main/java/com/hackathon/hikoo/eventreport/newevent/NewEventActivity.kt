package com.hackathon.hikoo.eventreport.newevent

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.Gravity
import android.view.View
import android.widget.PopupMenu
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.LifecycleOwner
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import com.hackathon.hikoo.BaseActivity
import com.hackathon.hikoo.R
import com.hackathon.hikoo.utils.bindView
import com.hackathon.hikoo.utils.imageloader.ImageLoadTool
import com.orhanobut.logger.Logger
import org.koin.android.ext.android.inject
import java.io.File

class NewEventActivity : BaseActivity(),
    INewEvent, View.OnClickListener {

    companion object {
        private const val REQUEST_CAMERA = 1001
        private const val REQUEST_ALBUM = 1002
    }

    private val cancelButton: MaterialTextView by bindView(R.id.cancel_button)
    private val alertTypeChoose: MaterialTextView by bindView(R.id.alert_type_choose)
    private val eventTypeChoose: MaterialTextView by bindView(R.id.event_type_choose)
    private val locationChoose: MaterialTextView by bindView(R.id.event_location_choose)
    private val eventDescriptionEdit: AppCompatEditText by bindView(R.id.event_description_edittext)
    private val eventImage: AppCompatImageView by bindView(R.id.event_image)
    private val eventUploadImageButton: MaterialButton by bindView(R.id.event_upload_image)
    private val sendButton: MaterialButton by bindView(R.id.event_send_submit)

    private val presenter: NewEventPresenter by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_report)
        presenter.attachView(this)

        addListener()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        val granted = grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED
        when (requestCode) {
            REQUEST_CAMERA -> {
                if (granted) {
                    takePhotoByCamera()
                }
            }
            REQUEST_ALBUM -> {
                if (granted) {
                    takePhotoByAlbum()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_CAMERA -> {
                if (resultCode == Activity.RESULT_OK) {
                    presenter.handleNativeCameraResult(this)
                }
            }
            REQUEST_ALBUM -> {
                if (resultCode == Activity.RESULT_OK) {
                    val uri = data?.data as Uri
                    presenter.handleAlbumResult(this, uri)
                }
            }
        }
    }

    private fun takePhotoByAlbum() {
        val albumIntent = Intent(Intent.ACTION_GET_CONTENT)
        albumIntent.type = "image/*"
        albumIntent.putExtra(Intent.EXTRA_LOCAL_ONLY, true)
        albumIntent.addCategory(Intent.CATEGORY_OPENABLE)
        startActivityForResult(albumIntent,
            REQUEST_ALBUM
        )
    }

    private fun takePhotoByCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (cameraIntent.resolveActivity(packageManager) != null) {
            val directory = getExternalFilesDir(NewEventPresenter.CUSTOM_ICON_DIRECTORY)
            val tempFile = File(directory, NewEventPresenter.CUSTOM_ICON_TEMP_FILE)
            val iconUri = FileProvider.getUriForFile(this, getString(R.string.file_provider_authority), tempFile)

            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, iconUri)
            startActivityForResult(cameraIntent,
                REQUEST_CAMERA
            )
        }
    }

    private fun showCustomIconPopupDialog() {
        val popupMenu = PopupMenu(this, eventUploadImageButton, Gravity.START)
        popupMenu.menuInflater.inflate(R.menu.menu_upload_image, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.custom_icon_camera -> {
                    if (checkPermissions(mutableListOf(Manifest.permission.CAMERA),
                            REQUEST_CAMERA
                        )) {
                        takePhotoByCamera()
                    }
                }
                R.id.custom_icon_album -> {
                    if (checkPermissions(mutableListOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                            REQUEST_ALBUM
                        )) {
                        takePhotoByAlbum()
                    }
                }
            }
            return@setOnMenuItemClickListener false
        }
        popupMenu.show()
    }

    private fun addListener() {
        sendButton.setOnClickListener(this)
        eventUploadImageButton.setOnClickListener(this)
        locationChoose.setOnClickListener(this)
        eventTypeChoose.setOnClickListener(this)
        cancelButton.setOnClickListener(this)
        alertTypeChoose.setOnClickListener(this)

        eventDescriptionEdit.doOnTextChanged { text, start, count, after ->
            Logger.d("doOnTextChanged = ${text.toString()}")
            presenter.eventReport?.eventInfo = text.toString()
        }
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.cancel_button -> {
                finish()
            }
            R.id.event_upload_image -> {
                showCustomIconPopupDialog()
            }
            R.id.event_send_submit -> {
                if (presenter.isValidate()) {
                    presenter.uploadImage(eventDescriptionEdit.text.toString())
                } else {
                    AlertDialog.Builder(this)
                        .setMessage("Do not entry empty")
                        .create()
                        .show()
                }
            }
            R.id.event_type_choose -> {
                openEventTypeChooseDialog()
            }
            R.id.alert_type_choose -> {
                openAlertTypeChooseDialog()
            }
        }
    }

    private fun openAlertTypeChooseDialog() {
        AlertDialog.Builder(this)
            .setTitle("Choose event type")
            .setItems(arrayOf("Information", "Caution", "Danger")) { dialog, which ->
                presenter.eventReport?.alertLevelId = which + 1
                dialog.dismiss()
                when (which) {
                    0 -> {
                        alertTypeChoose.text = "Information"
                    }
                    1 -> {
                        alertTypeChoose.text = "Caution"
                    }
                    2 -> {
                        alertTypeChoose.text = "Danger"
                    }
                }
            }
            .create()
            .show()

    }

    private fun openEventTypeChooseDialog() {
        AlertDialog.Builder(this)
            .setTitle("Choose alert type")
            .setItems(arrayOf("Wild Animal", "Item Found", "Blocked Route")) { dialog, which ->
                presenter.eventReport?.eventTypeId = which + 1
                dialog.dismiss()
                when (which) {
                    0 -> {
                        eventTypeChoose.text = "Wild Animal"
                    }
                    1 -> {
                        eventTypeChoose.text = "Item Found"
                    }
                    2 -> {
                        eventTypeChoose.text = "Blocked Route"
                    }
                }
            }
            .create()
            .show()
    }

    //region NewEventView
    override fun provideLifecycleOwner(): LifecycleOwner {
        return this
    }

    override fun showUploadImage(imageLoader: ImageLoadTool, path: String) {
        imageLoader.loadImage(this, path, eventImage)
    }

    override fun showSendReportSuccess() {
        val dialog = AlertDialog.Builder(this)
            .setCancelable(false)
            .setTitle("Result")
            .setMessage("Send event successfully")
            .setPositiveButton("OK") { dialog, which ->
                dialog.dismiss()
                finish()
            }
            .create()

        dialog.setOnShowListener {
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setBackgroundColor(ContextCompat.getColor(this, R.color.transparent))
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(this, R.color.eastern_blue))
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setBackgroundColor(ContextCompat.getColor(this, R.color.transparent))
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(this, R.color.eastern_blue))
        }
        dialog.show()
    }

    override fun showSendReportFailed() {
        val dialog = AlertDialog.Builder(this)
            .setCancelable(false)
            .setTitle("Result")
            .setMessage("Send event failed")
            .setPositiveButton("OK") { dialog, which ->
                dialog.dismiss()
            }
            .create()
        dialog.setOnShowListener {
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setBackgroundColor(ContextCompat.getColor(this, R.color.transparent))
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(this, R.color.eastern_blue))
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setBackgroundColor(ContextCompat.getColor(this, R.color.transparent))
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(this, R.color.eastern_blue))
        }
        dialog.show()
    }

    //endregion


}
