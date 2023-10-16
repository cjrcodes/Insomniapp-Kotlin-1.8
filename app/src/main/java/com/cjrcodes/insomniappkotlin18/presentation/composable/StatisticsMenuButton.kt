package com.cjrcodes.insomniappkotlin18.presentation.composable

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoGraph
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import androidx.wear.compose.material.Icon

@Composable
fun StatisticsMenuButton(onClick: () -> Unit){
    Button(
        onClick = { /*TODO*/ },
        modifier = Modifier.size(ButtonDefaults.DefaultButtonSize)
    ) {
        Icon(
            imageVector = Icons.Filled.AutoGraph,
            contentDescription = "Statistics Menu",
            modifier = Modifier.size(24.dp)
        )
    }
}

@Preview
@Composable
fun StatisticsMenuButtonPreview() {
    StatisticsMenuButton(onClick = {})
}