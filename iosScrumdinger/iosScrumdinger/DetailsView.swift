//
//  DetailsView.swift
//  iosScrumdinger
//
//  Created by Игорь Пивоваров on 09.01.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import scrumdingerShared

struct DetailsView: View {
    @Binding var scrum: DailyScrum
    @State var isPresentingEditView: Bool = false
    var body: some View {
        List {
            Section(header: Text("Meeting info")) {
                Label("Start meeting", systemImage: "timer")
                HStack {
                    Label("Length", systemImage: "clock")
                    Spacer()
                    Text("\(scrum.lengthInMinutes) minutes")
                }
                HStack {
                    Label("Theme", systemImage: "paintpalette")
                    Spacer()
                    Text(scrum.theme.viewName)
                        .padding(4)
                        .background(scrum.theme.color)
                        .cornerRadius(4)
                        .foregroundColor(scrum.theme.accentColor().color)
                }
            }
            Section(header: Text("Attendees")) {
                ForEach(scrum.attendees, id: \.name) { item in
                    Label(item.name, systemImage: "person")
                }
            }
        }
        .navigationTitle(scrum.title)
        .toolbar {
            Button("Edit") {
                isPresentingEditView = true
            }
        }
        .sheet(isPresented: $isPresentingEditView) {
            EditScrumSheet(isPresentingEditView: $isPresentingEditView, scrum: $scrum)
        }
    }
}

#Preview {
    DetailsView(scrum: .constant(DailyScrum.companion.sampleScrum))
}
