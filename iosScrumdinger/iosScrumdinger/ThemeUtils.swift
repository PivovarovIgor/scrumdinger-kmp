//
//  ThemeUtils.swift
//  iosScrumdinger
//
//  Created by Игорь Пивоваров on 06.01.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import scrumdingerShared

extension ColorSpacesEnum {
    var colorSpare: Color.RGBColorSpace {
        return switch(self) {
        case .srgb: .sRGB
        case .linearSrgb: .sRGBLinear
        case .displayP3: .displayP3
        default: .sRGB
        }
    }
}

extension ColorParams {
    var color: Color {
        return Color(self.colorSpace.colorSpare, red: Double(self.red), green: Double(self.green), blue: Double(self.blue), opacity: Double(self.alpha))
    }
}

extension Theme {
    var color: Color {
        return self.colorParams.color
    }
}
