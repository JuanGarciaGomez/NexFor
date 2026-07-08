package com.project.nexfor.feature.inventory.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.project.nexfor.feature.inventory.viewmodel.PostUiState
import com.project.nexfor.feature.inventory.viewmodel.PostViewModel
import org.koin.compose.viewmodel.koinViewModel

/**
 * Main screen for displaying a Post.
 */
@Composable
fun PostScreen(
    viewModel: PostViewModel = koinViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = { viewModel.loadPost() }) {
            Text("Load Post")
        }

        Spacer(modifier = Modifier.height(16.dp))

        when (val uiState = state) {
            PostUiState.Idle -> {
                Text("Tap the button to load data")
            }
            PostUiState.Loading -> {
                CircularProgressIndicator()
            }
            is PostUiState.Success -> {
                Text(
                    text = uiState.title,
                    style = MaterialTheme.typography.headlineMedium
                )
            }
            is PostUiState.Error -> {
                Text(
                    text = "Error: ${uiState.message}",
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}
