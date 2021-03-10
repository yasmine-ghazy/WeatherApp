package com.iti.weatherapp.model.model

data class Place(
    var bbox: FloatArray? = null,
    var category: String? = null,
    var categoryTitle: String? = null,
    var completion: String? = null,
    var distance: Double = 0.0,
    var highlightedTitle: String? = null,
    var highlightedVicinity: String? = null,
    var href: String? = null,
    var id: String? = null,
    var position: FloatArray? = null,
    var resultType: String? = null,
    var title: String? = null,
    var type: String? = null,
    var vicinity: String? = null
)

data class PlaceResult(var results: List<Place>? = null)