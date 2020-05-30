package com.hackathon.hikoo.mountainpermit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView

import com.hackathon.hikoo.R
import com.hackathon.hikoo.maincontainer.MainActivity
import com.hackathon.hikoo.model.domain.MountainPermit
import com.hackathon.hikoo.view.adpater.MountainPermitAdapter
import org.koin.android.ext.android.inject

class MountainPermitFragment : Fragment(), IMountainPermit, MountainPermitAdapter.MountainPermitItemCallback {

    companion object {
        fun newInstance() = MountainPermitFragment()
    }

    private lateinit var permitList: RecyclerView

    private val presenter: MountainPermitPresenter by inject()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_mountain_permit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attachView(this)
        (activity as? MainActivity)?.setActionBarTitle(R.string.my_mountain_permit)
        permitList = view.findViewById(R.id.permit_list)

        presenter.fetchPermit()
    }

    override fun onMountainPermitItemClicked(permit: MountainPermit) {

    }

    override fun setupPermitList(list: List<MountainPermit>) {
        val permitAdapter = MountainPermitAdapter(this)
        permitList.let {
            it.setHasFixedSize(true)
            it.adapter = permitAdapter
        }
        permitAdapter.addMountainPermitItem(list)
    }

    override fun provideLifecycleOwner(): LifecycleOwner {
        return this
    }
}
