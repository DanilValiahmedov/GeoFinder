package com.valimade.geofinder

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.valimade.geofinder.ui.theme.GeoFinderTheme
import com.valimade.geofinder.ui.viewmodel.GeoFinderViewModel
import com.valimade.geofinder.ui.viewmodel.GeoFinderViewModelFactory
import javax.inject.Inject

class MainActivity : ComponentActivity() {
    @Inject
    lateinit var viewModelFactory: GeoFinderViewModelFactory

    private val viewModel: GeoFinderViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.inject(this)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GeoFinderTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val clipboardManager = LocalClipboardManager.current
                    val state by viewModel.state.collectAsState()

                    val permissionLauncher = rememberLauncherForActivityResult(
                        contract = ActivityResultContracts.RequestPermission()
                    ) { isPermission ->
                        viewModel.getPermission(isPermission)
                    }

                    // Запрос разрешения на геолокацию
                    LaunchedEffect(Unit) {
                        if (!state.isPermission) {
                            permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                        }
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Text(
                            text = "Определим вашу Геолокацию?",
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp,
                            textAlign = TextAlign.Center,
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Button(
                                onClick = { viewModel.onFLPClick() },
                                modifier = Modifier.weight(1f),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.Red
                                )
                            ) {
                                Text(
                                    text = "Fused Location Provider",
                                    textAlign = TextAlign.Center,
                                )
                            }

                            Button(
                                onClick = { viewModel.onLocationManagerClick() },
                                modifier = Modifier.weight(1f),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.Blue
                                )
                            ) {
                                Text(
                                    text = "Location Manager",
                                    textAlign = TextAlign.Center,
                                )
                            }
                        }

                        Text(
                            text = "Информация из Fused Location Provider: \n"
                                + state.flpLocation,
                        )


                        Text(
                            text = "Информация из Location Manager: \n"
                                    + state.locationManagerLocation,
                        )

                        Spacer(modifier = Modifier.weight(1f))

                        Button(
                            onClick = {
                                clipboardManager.setText(
                                    AnnotatedString(
                                        "Информация из Fused Location Provider: \n"
                                                + state.flpLocation
                                                +"\nИнформация из Location Manager: \n"
                                                + state.locationManagerLocation,
                                    )
                                )
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Magenta
                            )
                        ) {
                            Text("Скопировать")
                        }
                    }
                }
            }
        }
    }
}
