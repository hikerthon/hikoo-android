package com.hackathon.hikoo.account

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import com.hackathon.hikoo.BasePresenter
import com.hackathon.hikoo.BuildConfig
import com.hackathon.hikoo.manager.UserManager
import com.hackathon.hikoo.model.domain.User
import com.hackathon.hikoo.network.APIManager
import com.hackathon.hikoo.utils.ImageUtils
import com.hackathon.hikoo.utils.imageloader.ImageLoadTool
import com.orhanobut.logger.Logger
import io.reactivex.rxkotlin.subscribeBy
import java.io.*

class EditAccountPresenter(
    private val userManager: UserManager,
    private val imageLoadTool: ImageLoadTool,
    private val apiManager: APIManager
) : BasePresenter<EditAccountActivity>() {

    companion object {
        const val CUSTOM_ICON_DIRECTORY = "event"
        const val CUSTOM_ICON_TEMP_FILE = "temp-event.jpg"

        private const val CUSTOM_ICON_PREFIX = "event_"
        private const val CUSTOM_ICON_POSTFIX = ".jpg"
        private const val CUSTOM_ICON_WIDTH_HD = 1280
        private const val CUSTOM_ICON_HEIGHT_HD = 720
        private const val CUSTOM_ICON_QUALITY = 80
    }

    var hikerUser: User? = null

    private var uploadImagePath = ""

    init {
        hikerUser = userManager.hikerUser
    }

    fun fetchHikerInfo() {
        view?.setupHikerInfo(userManager.hikerUser, imageLoadTool)
    }

    fun startUpdateHikerInfo() {
        if (uploadImagePath.isEmpty()) {
            updateHikerInfo(null)
        } else {
            apiManager.postUploadImage(uploadImagePath).subscribeBy(
                onNext = { response ->
                    if (response.isSuccessful) {
                        response.body()?.let {
                            updateHikerInfo(it.imagePath)
                        }
                    }
                },
                onError = {
                    Logger.e(Log.getStackTraceString(it))
                }
            ).autoClear()
        }
    }

    private fun updateHikerInfo(imagePath: String?) {
        if (imagePath != null) {
            hikerUser?.image = imagePath
        }
        userManager.updateUserProfile(hikerUser!!, object : UserManager.OnUserProfileUpdateListener {
            override fun onUpdateUserProfileSuccess() {
                view?.showUpdateSuccess()
            }

            override fun onUpdateUserProfileFailed() {
                view?.showUpdateFailed()
            }
        })

    }

    fun handleNativeCameraResult(context: Context) {
        val directory = context.getExternalFilesDir(CUSTOM_ICON_DIRECTORY)
        val tempFilePath = File(directory,
            CUSTOM_ICON_TEMP_FILE
        )
        if (BuildConfig.DEBUG) {
            Logger.d("handleNativeCameraResult: create + ${tempFilePath.name}, length = ${tempFilePath.length()}")
        }
        createPhotoFromFile(context, tempFilePath.absolutePath)
    }

    fun handleAlbumResult(context: Context, uri: Uri?) {
        if (uri == null) {
            return
        }

        var inputStream: InputStream? = null
        var outputStream: FileOutputStream? = null

        try {
            inputStream = context.contentResolver.openInputStream(uri)

            val directory = context.getExternalFilesDir(CUSTOM_ICON_DIRECTORY)
            val tempFile = File(directory,
                CUSTOM_ICON_TEMP_FILE
            )
            outputStream = FileOutputStream(tempFile)
            val buffer = ByteArray(4 * 1024)
            var read: Int
            while ((inputStream?.read(buffer).also { read = it!! }) != -1) {
                outputStream.write(buffer, 0, read)
            }
            outputStream.flush()
            if (BuildConfig.DEBUG) {
                Logger.d("handleAlbumResult: create + ${tempFile.name}, length = ${tempFile.length()}")
            }
            createPhotoFromFile(context, tempFile.absolutePath)
        } catch (ex: IOException) {
            ex.printStackTrace()
            Logger.e(ex, "handleAlbumResult")
        } catch (ex: NullPointerException) {
            ex.printStackTrace()
            Logger.e(ex, "handleAlbumResult")
        } finally {
            try {
                inputStream?.close()
                outputStream?.close()
            } catch (ex: IOException) {
                ex.printStackTrace()
                Logger.e(ex, "handleAlbumResult")
            }
        }
    }

    private fun createPhotoFromFile(context: Context, path: String) {
        val bitmap = ImageUtils.getScaledBitmapFromFile(path,
            CUSTOM_ICON_WIDTH_HD,
            CUSTOM_ICON_HEIGHT_HD, true)
        var outputStream: FileOutputStream? = null

        try {
            val stream = ByteArrayOutputStream()
            val orientation = ImageUtils.getOrientationFromExif(path)
            val rotateBitmap = ImageUtils.rotateBitmap(bitmap, orientation)

            if (rotateBitmap == null) {
                return
            }

            rotateBitmap.compress(
                Bitmap.CompressFormat.JPEG,
                CUSTOM_ICON_QUALITY, stream)

            val outputDirectory = context.getExternalFilesDir(CUSTOM_ICON_DIRECTORY)
            val outputFile = File(outputDirectory, CUSTOM_ICON_PREFIX + System.currentTimeMillis() + CUSTOM_ICON_POSTFIX)

            outputStream = FileOutputStream(outputFile)
            outputStream.write(stream.toByteArray())
            outputStream.flush()
            uploadImagePath = outputFile.absolutePath
            hikerUser?.image = outputFile.absolutePath
            view?.showUploadImage(imageLoadTool, outputFile.absolutePath)
        } catch (ex: IOException) {
            ex.printStackTrace()
            Logger.e(ex, "handleNativeCameraResult")
        } catch (ex: NullPointerException) {
            ex.printStackTrace()
            Logger.e(ex, "handleNativeCameraResult")
        } finally {
            try {
                outputStream?.close()
            } catch (ex: IOException) {
                ex.printStackTrace()
                Logger.e(ex, "handleNativeCameraResult")
            }
        }
    }

    fun isValidate(): Boolean {
        return hikerUser?.isValidate ?: false
    }
}