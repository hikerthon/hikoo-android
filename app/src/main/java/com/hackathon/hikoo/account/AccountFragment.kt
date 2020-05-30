package com.hackathon.hikoo.account

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageView
import androidx.lifecycle.LifecycleOwner
import com.google.android.material.textview.MaterialTextView
import com.hackathon.hikoo.R
import com.hackathon.hikoo.maincontainer.MainActivity
import com.hackathon.hikoo.model.domain.User
import com.hackathon.hikoo.utils.DateUtils
import com.hackathon.hikoo.utils.imageloader.ImageLoadTool
import com.orhanobut.logger.Logger
import org.koin.android.ext.android.inject
import java.util.*

class AccountFragment : Fragment(), AccountView {

    companion object {
        fun  newInstance() = AccountFragment()
    }

    private lateinit var hikerAvatar: AppCompatImageView
    private lateinit var hikerName: MaterialTextView
    private lateinit var hikerGender: AppCompatEditText
    private lateinit var hikerPhoneNumber: AppCompatEditText
    private lateinit var hikerTelephoneNumber: AppCompatEditText
    private lateinit var hikerEmail: AppCompatEditText
    private lateinit var hikerEmergencyName: AppCompatEditText
    private lateinit var hikerEmergencyPhone: AppCompatEditText
    private lateinit var hikerNationality: AppCompatEditText
    private lateinit var hikerIdNumber: AppCompatEditText
    private lateinit var hikerBirthday: AppCompatEditText

    private val presenter: AccountPresenter by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_account, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attachView(this)
        (activity as? MainActivity)?.setActionBarTitle(R.string.my_account)
        retrieveViews(view)
        presenter.fetchHikerInfo()

    }

    private fun retrieveViews(view: View) {
        hikerAvatar = view.findViewById(R.id.account_avatar)
        hikerName = view.findViewById(R.id.account_name)
        hikerGender = view.findViewById(R.id.account_gender_edittext)
        hikerPhoneNumber = view.findViewById(R.id.account_phone_number_edittext)
        hikerTelephoneNumber = view.findViewById(R.id.account_telephone_number_edittext)
        hikerEmail = view.findViewById(R.id.account_email_edittext)
        hikerEmergencyName = view.findViewById(R.id.account_contact_name_edittext)
        hikerEmergencyPhone = view.findViewById(R.id.account_contact_phone_edittext)
        hikerBirthday = view.findViewById(R.id.account_birthday_edittext)
        hikerNationality = view.findViewById(R.id.account_nationality_edittext)
        hikerIdNumber = view.findViewById(R.id.account_id_edittext)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_account, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_edit -> {
                val intent = Intent(context, EditAccountActivity::class.java)
                startActivity(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    //region AccountView
    override fun provideLifecycleOwner(): LifecycleOwner {
        return this
    }

    override fun setupHikerInfo(user: User, imageLoadTool: ImageLoadTool) {
        val context = context ?: return
        with(user) {
            imageLoadTool.loadCircleImage(context, image, hikerAvatar, R.drawable.ic_profile_icon)
            hikerName.text = "$firstName $lastName"
            hikerGender.setText(gender)
            hikerPhoneNumber.setText(mobileNumber)
            hikerTelephoneNumber.setText(satelliteNumber)
            hikerEmail.setText(email)
            hikerEmergencyName.setText(emergencyContact)
            hikerEmergencyPhone.setText(emergencyNumber)
            hikerIdNumber.setText(identificationNumber)
            hikerNationality.setText(nationality)
            hikerBirthday.setText(DateUtils.getFormattedBirth(dob))
        }
    }
    //endregion


}
