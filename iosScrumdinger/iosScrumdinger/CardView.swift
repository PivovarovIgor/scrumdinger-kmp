//
//  CardView.swift
//  iosScrumdinger
//
//  Created by Игорь Пивоваров on 06.01.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import scrumdingerShared

struct CardView: View {
    let scrum: DailyScrum
    var body: some View {
        VStack(alignment: .leading) {
            Text(scrum.title)
                .font(.headline)
            Spacer()
            HStack {
                Label("\(scrum.attendees.count)", systemImage: "person.3")
                Spacer()
                Label("\(scrum.lengthInMinutes)", systemImage: "clock")
                    .labelStyle(.trailingIcon)
            }
            .font(.caption)
        }
        .padding()
        .foregroundColor(scrum.theme.accentColor().color)
    }
}

struct CardView_Previews: PreviewProvider {
    static var scrum = DailyScrum.companion.sampleScrum
    static var previews: some View {
        CardView(scrum: scrum)
            .background(scrum.theme.color)
            .previewLayout(.fixed(width: 400, height: 60))
    }
}
