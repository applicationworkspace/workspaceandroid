package com.workspaceandroid.ui.screens.settings

import androidx.navigation.NavController
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.workspaceandroid.navigation.navGraph.Graph.AUTHENTICATION_ROUTE

@Composable
fun SettingsScreen(navController: NavController) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        Column {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                textAlign = TextAlign.Center,
                text = "Settings",
                color = MaterialTheme.colorScheme.primary,
                fontSize = MaterialTheme.typography.headlineMedium.fontSize,
                fontWeight = FontWeight.Bold
            )
            UserRowItem(navController = navController)
            SettingRowItem(text = "Notifications", imageVector = Icons.Filled.Notifications) { }
            SettingRowItem(text = "Privacy", imageVector = Icons.Filled.Lock) { }
            SettingRowItem(text = "Language", imageVector = Icons.Filled.Place) { }
            SettingRowItem(text = "Log out", imageVector = Icons.Filled.ExitToApp) { }
        }
    }
}

@Composable
fun SettingRowItem(text: String,
                   imageVector: ImageVector,
                   onclick: () -> Unit) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight()
        .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier
                .size(32.dp)
                .wrapContentWidth(Alignment.Start),
            imageVector = imageVector,
            contentDescription = "Current user"
        )
        Text(
            text = text,
            fontSize = MaterialTheme.typography.bodyMedium.fontSize
        )
        Icon(
            modifier = Modifier
                .weight(1f)
                .size(32.dp)
                .wrapContentWidth(Alignment.End),
            imageVector = Icons.Filled.KeyboardArrowRight,
            contentDescription = "",
            tint = Color.Gray
        )
        Spacer(modifier = Modifier.width(12.dp))
    }
}

@Composable
fun UserRowItem(navController: NavController) {
    Row(modifier = Modifier
        .clickable {
            navController.navigate(route = AUTHENTICATION_ROUTE)
        }
        .fillMaxWidth()
        .wrapContentHeight()
        .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = rememberAsyncImagePainter("https://picsum.photos/300/300"),
            contentDescription = "User image",
            modifier = Modifier
                .size(94.dp)
                .padding(8.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
        Column(modifier = Modifier.padding(start = 8.dp)) {
            Text(
                text = "Name Name",
                fontSize = MaterialTheme.typography.titleMedium.fontSize
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "User email",
                fontSize = MaterialTheme.typography.bodyMedium.fontSize
            )
        }
        Icon(
            modifier = Modifier
                .weight(1f)
                .size(32.dp)
                .wrapContentWidth(Alignment.End),
            imageVector = Icons.Filled.KeyboardArrowRight,
            contentDescription = "Current user",
            tint = Color.Gray
        )
        Spacer(modifier = Modifier.width(12.dp))
    }
}

@Composable
@Preview(showBackground = true)
fun SettingsScreenPreview() {
    SettingsScreen(navController = rememberNavController())
}
