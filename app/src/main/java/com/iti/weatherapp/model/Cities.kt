package com.iti.weatherapp.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.versionedparcelable.ParcelField
import androidx.versionedparcelable.VersionedParcelize
import com.google.gson.annotations.SerializedName

data class Cities(

		@field:SerializedName("metadata")
	val metadata: Metadata? = null,

		@field:SerializedName("data")
	val data: List<City>? = null,

		@field:SerializedName("links")
	val links: List<LinksItem?>? = null
)

@Entity(tableName = "cities")
data class City(

	@ColumnInfo(name = "country")
	@field:SerializedName("country")
	val country: String? = null,


	@field:SerializedName("wikiDataId")
	val wikiDataId: String? = null,

	@field:SerializedName("regionCode")
	val regionCode: String? = null,

	@ColumnInfo(name = "city")
	@field:SerializedName("city")
	val city: String? = null,

	@field:SerializedName("countryCode")
	val countryCode: String? = null,

	@ColumnInfo(name = "latitude")
	@field:SerializedName("latitude")
	val latitude: Double? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@PrimaryKey
	@ColumnInfo(name = "id")
	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@ColumnInfo(name = "region")
	@field:SerializedName("region")
	val region: String? = null,

	@ColumnInfo(name = "longitude")
	@field:SerializedName("longitude")
	val longitude: Double? = null
): Parcelable {
	constructor(parcel: Parcel) : this(
		parcel.readString(),
		parcel.readString(),
		parcel.readString(),
		parcel.readString(),
		parcel.readString(),
		parcel.readValue(Double::class.java.classLoader) as? Double,
		parcel.readString(),
		parcel.readValue(Int::class.java.classLoader) as? Int,
		parcel.readString(),
		parcel.readString(),
		parcel.readValue(Double::class.java.classLoader) as? Double
	) {
	}

	override fun writeToParcel(parcel: Parcel, flags: Int) {
		parcel.writeString(country)
		parcel.writeString(wikiDataId)
		parcel.writeString(regionCode)
		parcel.writeString(city)
		parcel.writeString(countryCode)
		parcel.writeValue(latitude)
		parcel.writeString(name)
		parcel.writeValue(id)
		parcel.writeString(type)
		parcel.writeString(region)
		parcel.writeValue(longitude)
	}

	override fun describeContents(): Int {
		return 0
	}

	companion object CREATOR : Parcelable.Creator<City> {
		override fun createFromParcel(parcel: Parcel): City {
			return City(parcel)
		}

		override fun newArray(size: Int): Array<City?> {
			return arrayOfNulls(size)
		}
	}
}

data class Metadata(

	@field:SerializedName("currentOffset")
	val currentOffset: Int? = null,

	@field:SerializedName("totalCount")
	val totalCount: Int? = null
)


data class LinksItem(

	@field:SerializedName("rel")
	val rel: String? = null,

	@field:SerializedName("href")
	val href: String? = null
)
