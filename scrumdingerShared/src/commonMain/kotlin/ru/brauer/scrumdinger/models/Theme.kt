package ru.brauer.scrumdinger.models

enum class Theme(val colorParams: ColorParams) {
    BUBBLEGUM(
        ColorParams(
            red = 0.933f,
            green = 0.502f,
            blue = 0.820f
        )
    ),
    BUTTERCUP(
        ColorParams(
            red = 1f,
            green = 0.945f,
            blue = 0.588f
        )
    ),
    INDIGO(
        ColorParams(
            red = 0.212f,
            green = 0f,
            blue = 0.443f
        )
    ),
    LAVENDER(
        ColorParams(
            red = 0.812f,
            green = 0.808f,
            blue = 1f
        )
    ),
    MAGENTA(
        ColorParams(
            red = 0.647f,
            green = 0.075f,
            blue = 0.467f
        )
    ),
    NAVY(
        ColorParams(
            red = 0f,
            green = 0.078f,
            blue = 0.255f
        )
    ),
    ORANGE(
        ColorParams(
            red = 1f,
            green = 0.545f,
            blue = 0.259f
        )
    ),
    OXBLOOD(
        ColorParams(
            red = 0.290f,
            green = 0.027f,
            blue = 0.043f
        )
    ),
    PERIWINKLE(
        ColorParams(
            red = 0.525f,
            green = 0.510f,
            blue = 1f
        )
    ),
    POPPY(
        ColorParams(
            red = 1f,
            green = 0.369f,
            blue = 0.369f
        )
    ),
    PURPLE(
        ColorParams(
            red = 0.569f,
            green = 0.294f,
            blue = 0.949f
        )
    ),
    SEAFOAM(
        ColorParams(
            red = 0.796f,
            green = 0.918f,
            blue = 0.898f
        )
    ),
    SKY(
        ColorParams(
            red = 0.431f,
            green = 0.573f,
            blue = 1f
        )
    ),
    TAN(
        ColorParams(
            red = 0.761f,
            green = 0.608f,
            blue = 0.494f
        )
    ),
    TEAL(
        ColorParams(
            red = 0.133f,
            green = 0.561f,
            blue = 0.620f
        )
    ),
    YELLOW(
        ColorParams(
            red = 1f,
            green = 0.875f,
            blue = 0.302f
        )
    )
}

class ColorParams(
    val red: Float,
    val green: Float,
    val blue: Float,
    val alpha: Float = 1f,
    val colorSpace: ColorSpacesEnum = ColorSpacesEnum.SRGB
)

enum class ColorSpacesEnum {
    SRGB, LINEAR_SRGB, DISPLAY_P3
}

fun Theme.accentColor(): ColorParams {
    return when (this) {
        Theme.BUBBLEGUM, Theme.BUTTERCUP, Theme.LAVENDER, Theme.ORANGE, Theme.PERIWINKLE,
        Theme.POPPY, Theme.SEAFOAM, Theme.SKY, Theme.TAN, Theme.TEAL, Theme.YELLOW -> Colors.black

        Theme.INDIGO, Theme.MAGENTA, Theme.NAVY, Theme.OXBLOOD, Theme.PURPLE -> Colors.white
    }
}

private object Colors {
    val black = ColorParams(0f, 0f, 0f)
    val white = ColorParams(1f, 1f, 1f)
}