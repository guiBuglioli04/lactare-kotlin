package com.example.lactare.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.lactare.ui.theme.LactareBlue
import com.example.lactare.ui.theme.LactareTextSecondary

@Composable
fun Stepper(currentStep: Int, totalSteps: Int = 4) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
        for (i in 1..totalSteps) {
            val selected = i <= currentStep
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .clip(CircleShape)
                    .background(if (selected) LactareBlue else Color(0xFFE5E7EB)),
                contentAlignment = Alignment.Center
            ) {
                Text("$i", color = if (selected) Color.White else LactareTextSecondary)
            }
            if (i < totalSteps) {
                HorizontalDivider(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 6.dp),
                    color = Color(0xFFD1D5DB)
                )
            }
        }
    }
}
