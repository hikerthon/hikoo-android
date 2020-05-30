package com.hackathon.hikoo.account

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.DatePicker
import android.widget.PopupMenu
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.LifecycleOwner
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import com.hackathon.hikoo.BaseActivity
import com.hackathon.hikoo.R
import com.hackathon.hikoo.eventreport.newevent.NewEventPresenter
import com.hackathon.hikoo.model.domain.User
import com.hackathon.hikoo.utils.DateUtils
import com.hackathon.hikoo.utils.bindView
import com.hackathon.hikoo.utils.imageloader.ImageLoadTool
import com.orhanobut.logger.Logger
import org.koin.android.ext.android.inject
import java.io.File
import java.util.*

class EditAccountActivity : BaseActivity(), IEditAccount, View.OnClickListener, DatePickerDialog.OnDateSetListener {

    companion object {
        private const val REQUEST_CAMERA = 1001
        private const val REQUEST_ALBUM = 1002
    }

    private val backButton: MaterialTextView by bindView(R.id.back_button)
    private val avatar: AppCompatImageView by bindView(R.id.account_avatar)
    private val uploadImageButton: MaterialButton by bindView(R.id.upload_image)
    private val firstNameEdit: AppCompatEditText by bindView(R.id.first_name_edit)
    private val lastNameEdit: AppCompatEditText by bindView(R.id.last_name_edit)
    private val genderChoose: MaterialTextView by bindView(R.id.gender_choose)
    private val birthdayChoose: MaterialTextView by bindView(R.id.birthday_choose)
    private val nationalityChoose: MaterialTextView by bindView(R.id.nationality_choose)
    private val idNumberEdit: AppCompatEditText by bindView(R.id.id_number_edit)
    private val mobilePhoneEdit: AppCompatEditText by bindView(R.id.mobile_phone_edit)
    private val telephoneEdit: AppCompatEditText by bindView(R.id.telephone_number_edit)
    private val toolbar: Toolbar by bindView(R.id.toolbar)

    private val presenter: EditAccountPresenter by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_account)
        presenter.attachView(this)
        setSupportActionBar(toolbar)
        supportActionBar?.run {
            setDisplayShowTitleEnabled(false)
        }

        addListener()
        presenter.fetchHikerInfo()
    }

    override fun onDestroy() {
        presenter.hikerUser = null
        super.onDestroy()
    }

    private fun addListener() {
        backButton.setOnClickListener(this)
        uploadImageButton.setOnClickListener(this)
        birthdayChoose.setOnClickListener(this)
        genderChoose.setOnClickListener(this)
        nationalityChoose.setOnClickListener(this)

        firstNameEdit.doOnTextChanged { text, start, count, after ->
            presenter.hikerUser?.firstName = text.toString()
        }

        lastNameEdit.doOnTextChanged { text, start, count, after ->
            presenter.hikerUser?.lastName = text.toString()
        }

        idNumberEdit.doOnTextChanged { text, start, count, after ->
            presenter.hikerUser?.identificationNumber = text.toString()
        }

        mobilePhoneEdit.doOnTextChanged { text, start, count, after ->
            presenter.hikerUser?.mobileNumber = text.toString()
            presenter.hikerUser?.emergencyMobile = text.toString()
        }

        telephoneEdit.doOnTextChanged { text, start, count, after ->
            presenter.hikerUser?.satelliteNumber = text.toString()
        }


    }

    private fun showCustomIconPopupDialog() {
        val popupMenu = PopupMenu(this, uploadImageButton, Gravity.START)
        popupMenu.menuInflater.inflate(R.menu.menu_upload_image, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.custom_icon_camera -> {
                    if (checkPermissions(mutableListOf(Manifest.permission.CAMERA), REQUEST_CAMERA)) {
                        takePhotoByCamera()
                    }
                }
                R.id.custom_icon_album -> {
                    if (checkPermissions(mutableListOf(Manifest.permission.READ_EXTERNAL_STORAGE), REQUEST_ALBUM)) {
                        takePhotoByAlbum()
                    }
                }
            }
            return@setOnMenuItemClickListener false
        }
        popupMenu.show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_edit_account, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_save -> {
                if (presenter.isValidate())  {
                    presenter.startUpdateHikerInfo()
                } else {
                    AlertDialog.Builder(this)
                        .setMessage("Do not entry empty")
                        .create()
                        .show()
                }
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.back_button -> {
                finish()
            }
            R.id.upload_image -> {
                showCustomIconPopupDialog()
            }
            R.id.birthday_choose -> {
                openDatePicker()
            }
            R.id.gender_choose -> {
                openGenderChooseDialog()
            }
            R.id.nationality_choose -> {
                openNationalityChooseDialog()
            }
        }
    }

    private fun openNationalityChooseDialog() {
        AlertDialog.Builder(this)
            .setTitle("Choose your nationality")
            .setItems(arrayOf("Taiwan")) { dialog, which ->
                dialog.dismiss()
                when (which) {
                    0 -> {
                        presenter.hikerUser?.nationality = "Taiwan"
                        nationalityChoose.text = "Taiwan"
                    }
                }
            }.create().show()
    }

    private fun openGenderChooseDialog() {
        AlertDialog.Builder(this)
            .setTitle("Choose your gender")
            .setItems(arrayOf("Male", "Female")) { dialog, which ->
                dialog.dismiss()
                when (which) {
                    0 -> {
                        presenter.hikerUser?.gender = "M"
                        genderChoose.text = "M"
                    }
                    1 -> {
                        presenter.hikerUser?.gender = "F"
                        genderChoose.text = "F"
                    }
                }
            }.create().show()
    }

    private fun openDatePicker() {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        val dialog = DatePickerDialog(this, AlertDialog.THEME_DEVICE_DEFAULT_DARK,this, year, month, day)
        dialog.setOnShowListener {
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setBackgroundColor(ContextCompat.getColor(this, R.color.transparent))
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setBackgroundColor(ContextCompat.getColor(this, R.color.transparent))
        }
        dialog.show()
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val calendar: Calendar = GregorianCalendar(year, month, dayOfMonth)
        presenter.hikerUser?.dob = calendar.timeInMillis
        birthdayChoose.text = DateUtils.getFormattedNormal(calendar.timeInMillis)
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
        startActivityForResult(albumIntent, REQUEST_ALBUM)
    }

    private fun takePhotoByCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (cameraIntent.resolveActivity(packageManager) != null) {
            val directory = getExternalFilesDir(NewEventPresenter.CUSTOM_ICON_DIRECTORY)
            val tempFile = File(directory, NewEventPresenter.CUSTOM_ICON_TEMP_FILE)
            val iconUri = FileProvider.getUriForFile(this, getString(R.string.file_provider_authority), tempFile)

            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, iconUri)
            startActivityForResult(cameraIntent, REQUEST_CAMERA)
        }
    }

    //region IEditAccount
    override fun showUploadImage(imageLoadTool: ImageLoadTool, path: String) {
        imageLoadTool.loadCircleImage(this, path, avatar, R.drawable.ic_profile_icon)
    }

    override fun setupHikerInfo(user: User?, imageLoadTool: ImageLoadTool) {
        user?.let {
            imageLoadTool.loadCircleImage(this, it.image, avatar, R.drawable.ic_profile_icon)
            firstNameEdit.setText(it.firstName)
            lastNameEdit.setText(it.lastName)
            genderChoose.text = it.gender
            birthdayChoose.text = DateUtils.getFormattedNormal(it.dob)
            nationalityChoose.text = it.nationality
            idNumberEdit.setText(it.identificationNumber)
            mobilePhoneEdit.setText(it.mobileNumber)
            telephoneEdit.setText(it.satelliteNumber)
        }
    }

    override fun showUpdateFailed() {
        val dialog = AlertDialog.Builder(this)
            .setMessage("Update Failed")
            .setPositiveButton("OK") { dialog, which ->
                dialog.dismiss()
            }
            .create()
        dialog.show()
    }

    override fun showUpdateSuccess() {
        val dialog = AlertDialog.Builder(this)
            .setMessage("Update Success")
            .setPositiveButton("OK") { dialog, which ->
                dialog.dismiss()
                finish()
            }
            .create()
        dialog.show()
    }

    override fun provideLifecycleOwner(): LifecycleOwner {
        return this
    }
    //endregion
}
