//
//  TrailingIconLabelStyle.swift
//  iosScrumdinger
//
//  Created by Игорь Пивоваров on 06.01.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct TrailingIconLabelStyle : LabelStyle {
    
    func makeBody(configuration: Configuration) -> some View {
        HStack {
            configuration.title
            configuration.icon
        }
    }
}

extension LabelStyle where Self == TrailingIconLabelStyle {
    static var trailingIcon: Self { Self() }
}
