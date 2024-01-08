//
//  ScrumView.swift
//  iosScrumdinger
//
//  Created by Игорь Пивоваров on 06.01.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import scrumdingerShared

struct ScrumView: View {
    @Binding var scrums: [DailyScrum]
    var body: some View {
        NavigationStack {
            List($scrums) { $scrum in
                NavigationLink(destination: { DetailsView(scrum: $scrum) }) {
                    CardView(scrum: scrum)
                }
                .listRowBackground(scrum.theme.color)
            }
            .navigationTitle("Daily Scrum")
        }
    }
}

#Preview {
    ScrumView(scrums: .constant(DailyScrum.companion.sampleDaily))
}
