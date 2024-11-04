package com.example.lab11
import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.Polygon
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState

@Composable
fun MapScreen() {
    val arequipaLocation = LatLng(-16.4040102, -71.559611) // Centro de Arequipa, Perú
    val cameraPositionState = rememberCameraPositionState {
        position = com.google.android.gms.maps.model.CameraPosition.fromLatLngZoom(arequipaLocation, 12f)
    }

    // UBICACIONES
    val locations = listOf(
        LatLng(-16.433415, -71.5442652), // JLByR
        LatLng(-16.4205151, -71.4945209), // Paucarpata
        LatLng(-16.3524187, -71.5675994)  // Zamacola
    )

    // Nombres de las ubicaciones
    val locationNames = listOf("JLByR", "Paucarpata", "Zamacola")

    // Definir puntos para los polígonos
    val mallAventuraPolygon = listOf(
        LatLng(-16.432292, -71.509145),
        LatLng(-16.432757, -71.509626),
        LatLng(-16.433013, -71.509310),
        LatLng(-16.432566, -71.508853)
    )

    val parqueLambramaniPolygon = listOf(
        LatLng(-16.422704, -71.530830),
        LatLng(-16.422920, -71.531340),
        LatLng(-16.423264, -71.531110),
        LatLng(-16.423050, -71.530600)
    )

    val plazaDeArmasPolygon = listOf(
        LatLng(-16.398866, -71.536961),
        LatLng(-16.398744, -71.536529),
        LatLng(-16.399178, -71.536289),
        LatLng(-16.399299, -71.536721)
    )

    // Definir puntos para una polilínea de ejemplo
    val examplePolyline = listOf(
        LatLng(-16.410000, -71.540000),
        LatLng(-16.420000, -71.550000),
        LatLng(-16.430000, -71.560000)
    )

    // Variables para el tipo de mapa y ubicación actual
    var mapType by remember { mutableStateOf(MapType.NORMAL) }
    var currentLocation by remember { mutableStateOf<LatLng?>(null) }

    // Función para obtener la ubicación actual
    fun getCurrentLocation(context: Context) {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(context as Activity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
            return
        }

        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            location?.let {
                currentLocation = LatLng(it.latitude, it.longitude)
                cameraPositionState.move(CameraUpdateFactory.newLatLngZoom(currentLocation!!, 12f))
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // Botones para cambiar el tipo de mapa
        MapTypeSelection(mapType = mapType) { selectedMapType ->
            mapType = selectedMapType
        }

        // Botón para obtener la ubicación actual
        val context = LocalContext.current
        Button(onClick = { getCurrentLocation(context) }) {
            Text("Obtener ubicación actual")
        }

        Box(modifier = Modifier.fillMaxSize()) {
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState,
                properties = MapProperties(mapType = mapType)
            ) {
                // Añadir marcador en Arequipa, Perú
                Marker(
                    state = rememberMarkerState(position = arequipaLocation),
                    icon = BitmapDescriptorFactory.fromResource(R.drawable.icon), // Cambia este icono según sea necesario
                    title = "Arequipa, Perú"
                )

                // Añadir marcadores personalizados para las ubicaciones
                locations.forEachIndexed { index, location ->
                    Marker(
                        state = rememberMarkerState(position = location),
                        icon = BitmapDescriptorFactory.fromResource(R.drawable.ubi2), // Cambia este icono según sea necesario
                        title = locationNames[index],
                        snippet = "Punto de interés en ${locationNames[index]}"
                    )
                }

                // Dibujar polígonos
                Polygon(
                    points = mallAventuraPolygon,
                    strokeColor = Color.Black,
                    fillColor = Color.Red.copy(alpha = 0.3f),
                    strokeWidth = 5f
                )
                Polygon(
                    points = parqueLambramaniPolygon,
                    strokeColor = Color.Black,
                    fillColor = Color.Red.copy(alpha = 0.3f),
                    strokeWidth = 5f
                )
                Polygon(
                    points = plazaDeArmasPolygon,
                    strokeColor = Color.Black,
                    fillColor = Color.Red.copy(alpha = 0.3f),
                    strokeWidth = 5f
                )

                // Dibujar una polilínea
                Polyline(
                    points = examplePolyline,
                    color = Color.Green,
                    width = 5f
                )

                // Marcador para la ubicación actual
                currentLocation?.let { loc ->
                    Marker(
                        state = rememberMarkerState(position = loc),
                        title = "Ubicación Actual"
                    )
                }
            }
        }
    }
}

@Composable
fun MapTypeSelection(mapType: MapType, onMapTypeSelected: (MapType) -> Unit) {
    Column {
        Text("Selecciona el tipo de mapa:")
        Row(horizontalArrangement = Arrangement.SpaceEvenly) {
            Button(
                onClick = { onMapTypeSelected(MapType.NORMAL) },
                enabled = mapType != MapType.NORMAL
            ) {
                Text("Normal")
            }
            Button(
                onClick = { onMapTypeSelected(MapType.SATELLITE) },
                enabled = mapType != MapType.SATELLITE
            ) {
                Text("Satélite")
            }
            Button(
                onClick = { onMapTypeSelected(MapType.HYBRID) },
                enabled = mapType != MapType.HYBRID
            ) {
                Text("Híbrido")
            }
            Button(
                onClick = { onMapTypeSelected(MapType.TERRAIN) },
                enabled = mapType != MapType.TERRAIN
            ) {
                Text("Terreno")
            }
        }
    }
}
