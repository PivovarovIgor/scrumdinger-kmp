package ru.brauer.scrumdinger.android.baseview

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.brauer.scrumdinger.android.MyApplicationTheme

@Composable
fun Label(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    text: String,
    trailingIcon: Boolean = false,
    contentDescription: String? = null,
    style: TextStyle = MaterialTheme.typography.labelMedium
) {
    Row(modifier = modifier.padding(all = 3.dp), verticalAlignment = Alignment.CenterVertically) {
        if (trailingIcon) {
            Text(text = text, style = style)
            Spacer(modifier = Modifier.width(8.dp))
            Icon(imageVector = imageVector, contentDescription = contentDescription)
        } else {
            Icon(imageVector = imageVector, contentDescription = contentDescription)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = text, style = style)
        }
    }
}

@Preview
@Composable
fun LabelPreview() {
    MyApplicationTheme {
        Label(imageVector = Icons.Outlined.Image, text = "image")
    }
}