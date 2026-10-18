package com.example.shopify.presentation.camera

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

@Composable
fun CameraScreen() {
    Permissions()
}

@Composable
fun Permissions() {

    val permission = listOf(
        android.Manifest.permission.CAMERA
    )

    var isGranted by remember {
        mutableStateOf(false)
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { isGranted = true }
    )

    if(isGranted){
        Camera()
    } else {
        Box(
            modifier = Modifier.fillMaxSize()
        ){

            Button(
                onClick ={
                    launcher.launch(permission.toTypedArray())
                }
            ) {
                Text(text = "Grant Permission")
            }

        }

    }

}