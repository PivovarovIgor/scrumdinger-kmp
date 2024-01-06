package ru.brauer.scrumdinger.android.extensions

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.colorspace.ColorSpaces
import ru.brauer.scrumdinger.models.ColorParams
import ru.brauer.scrumdinger.models.ColorSpacesEnum
import ru.brauer.scrumdinger.models.Theme

val Theme.color: Color get() = colorParams.color

val ColorParams.color: Color
    get() = Color(
        red = red,
        green = green,
        blue = blue,
        alpha = alpha,
        colorSpace = when (colorSpace) {
            ColorSpacesEnum.SRGB -> ColorSpaces.Srgb
            ColorSpacesEnum.LINEAR_SRGB -> ColorSpaces.LinearSrgb
            ColorSpacesEnum.DISPLAY_P3 -> ColorSpaces.DisplayP3
        }
    )