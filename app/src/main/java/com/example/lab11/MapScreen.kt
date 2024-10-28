package com.example.lab11

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.Polygon
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import com.google.android.gms.maps.model.LatLng

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

    Box(modifier = Modifier.fillMaxSize()) {
        // Añadir GoogleMap al layout
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState
        ) {
            // Añadir marcador en Arequipa, Perú
            Marker(
                state = rememberMarkerState(position = arequipaLocation),
                icon = BitmapDescriptorFactory.fromResource(R.drawable.icon), // Icono para Arequipa
                title = "Arequipa, Perú"
            )
            // Añadir marcadores personalizados para las ubicaciones
            locations.forEachIndexed { index, location ->
                Marker(
                    state = rememberMarkerState(position = location),
                    icon = BitmapDescriptorFactory.fromResource(R.drawable.ubi2), // Icono personalizado para cada ubicación
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
        }
    }

    // Controlar el movimiento de la cámara programáticamente
    LaunchedEffect(Unit) {
        cameraPositionState.animate(
            update = CameraUpdateFactory.newLatLngZoom(LatLng(-16.2520984, -71.6836503), 12f), // Mover a Yura
            durationMs = 3000
        )
    }
}
