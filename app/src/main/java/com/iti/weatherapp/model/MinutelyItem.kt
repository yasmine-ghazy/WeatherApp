package com.iti.weatherapp.model

import com.google.gson.annotations.SerializedName

data class MinutelyItem(

	@field:SerializedName("dt")
	val dt: Int? = null,

	@field:SerializedName("precipitation")
	val precipitation: Int? = null
)