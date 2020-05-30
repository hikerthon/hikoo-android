package com.hackathon.hikoo.hikoopage

import android.location.Location
import com.hackathon.hikoo.IBase
import com.hackathon.hikoo.model.domain.Shelter
import com.hackathon.hikoo.utils.imageloader.ImageLoadTool

interface IHikoo: IBase {

    fun setupShelter(list: List<Shelter>, imageLoadTool: ImageLoadTool)
    fun setupMyLocation(location: Location)
    fun setupMyLocationMarker(shelter: Shelter)
    fun setupShelterMarker(shelter: Shelter)
    fun showSOSSuccess()
    fun showSOSFailed()
    fun showSOSFailedReason(errorMessage: String?)
}