package com.example.lab11

import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.MapType

class MapProperties(
    val isBuildingEnabled: Boolean = false,
    val isIndoorEnabled: Boolean = false,
    val isMyLocationEnabled: Boolean = false,
    val isTrafficEnabled: Boolean = false,
    val latLngBoundsForCameraTarget: LatLngBounds? = null,
    val mapStyleOptions: MapStyleOptions? = null,
    val mapType: MapType = MapType.NORMAL,
    val maxZoomPreference: Float = 21.0f,
    val minZoomPreference: Float = 3.0f
) {
    override fun toString(): String = "MapProperties(" +
            "isBuildingEnabled=$isBuildingEnabled, isIndoorEnabled=$isIndoorEnabled," +
            "isMyLocationEnabled=$isMyLocationEnabled, isTrafficEnabled=$isTrafficEnabled," +
            "latLngBoundsForCameraTarget=$latLngBoundsForCameraTarget, mapStyleOptions=$mapStyleOptions," +
            "mapType=$mapType, maxZoomPreference=$maxZoomPreference, " +
            "minZoomPreference=$minZoomPreference)"
}
