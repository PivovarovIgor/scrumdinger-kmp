//
//  ThemeView.swift
//  iosScrumdinger
//
//  Created by Игорь Пивоваров on 31.01.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import scrumdingerShared

struct ThemeView: View {
    let theme: Theme
    var body: some View {
        Text(theme.viewName)
            .padding(4)
            .frame(maxWidth: .infinity)
            .background(theme.color)
            .foregroundColor(theme.accentColor().color)
            .clipShape(RoundedRectangle(cornerRadius: 4))
    }
}

#Preview {
    ThemeView(theme: DailyScrum.companion.sampleScrum.theme)
}
