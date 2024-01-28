package sections

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import ru.brauer.scrumdinger.android.baseview.Label


internal object InnerColors {
    val Blue: Color = Color(0xFF0676FF)
}
@Composable
internal fun ItemDivider() {
    Row {
        Spacer(modifier = Modifier.width(42.dp))
        Divider()
    }
}

@Composable
internal fun SectionHeader(modifier: Modifier = Modifier, text: String) {
    Spacer(modifier = modifier.height(30.dp))
    Text(
        modifier = Modifier.padding(start = 16.dp),
        text = text,
        style = MaterialTheme.typography.titleLarge,
        color = Color.Gray
    )
    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
internal fun Section(
    headerTitle: String,
    itemsContents: List<@Composable () -> Unit>,
    innerPadding: PaddingValues? = null
) {
    if (itemsContents.isNotEmpty()) {
        SectionHeader(modifier = innerPadding?.let { Modifier.padding(it) } ?: Modifier,
            text = headerTitle)
        Surface(shape = RoundedCornerShape(10.dp)) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                itemsContents.forEachIndexed { index, content ->
                    content.invoke()
                    if (itemsContents.lastIndex > index) {
                        ItemDivider()
                    }
                }
            }
        }
    }
}

@Composable
internal fun SectionRow(content: @Composable RowScope.() -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(horizontal = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        content = content
    )
}

@Composable
internal fun SectionLabel(
    imageVector: ImageVector,
    text: String,
) {
    Label(
        modifier = Modifier.padding(horizontal = 6.dp),
        imageVector = imageVector,
        text = text,
        style = labelStyle,
        tint = InnerColors.Blue
    )
}

internal val labelStyle
    @Composable get() = MaterialTheme.typography.headlineSmall