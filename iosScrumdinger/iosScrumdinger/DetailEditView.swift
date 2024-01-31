//
//  DetailEditView.swift
//  iosScrumdinger
//
//  Created by Игорь Пивоваров on 18.01.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import scrumdingerShared

struct DetailEditView: View {
    @Binding var scrum: DailyScrum
    @State var newAttendeeName: String = ""
    
    var body: some View {
        Form {
            Section(header: Text("Meeting info")) {
                let titleBinding = Binding<String>(
                    get: {
                        self.scrum.title
                    },
                    set: {
                        self.scrum = self.scrum.copy(pTitle: $0)
                    }
                )
                let lengthInMinutes = Binding<Double>(
                    get: {
                        Double(self.scrum.lengthInMinutes)
                    },
                    set: {
                        self.scrum = self.scrum.copy(pLengthInMinutes: Int32($0))
                    }
                )
                let themeSelection = Binding<Theme>(
                    get: {
                        self.scrum.theme
                    },
                    set: {
                        self.scrum = self.scrum.copy(pTheme: $0)
                    }
                )
                TextField("Title", text: titleBinding)
                HStack{
                    Slider(value: lengthInMinutes, in: 5...30, step: 1) {
                        Text("Length in minutes")
                    }
                    Spacer()
                    Text("\(scrum.lengthInMinutes) minutes")
                }
                ThemePicker(selection: themeSelection)
                
            }
            Section(header: Text("Attendees")) {
                ForEach(scrum.attendees) { attendee in
                    Text(attendee.name)
                }
                .onDelete { index in
                    var newAttandees = scrum.attendees
                    newAttandees.remove(atOffsets: index)
                    scrum = scrum.copy(attendees: newAttandees)
                }
                HStack {
                    TextField("New attendee", text: $newAttendeeName)
                    Button(action: {
                        var newAttandees = scrum.attendees
                        newAttandees.append(DailyScrum.Attendee(name: newAttendeeName))
                        scrum = scrum.copy(attendees: newAttandees)
                        newAttendeeName = ""
                    }) {
                        Image(systemName: "plus.circle.fill")
                    }
                    .disabled(newAttendeeName.isEmpty)
                }
            }
        }
    }
}

struct DetailEditView_Previews: PreviewProvider {
    @State static var scrum = DailyScrum.companion.sampleScrum
    static var previews: some View {
        DetailEditView(scrum: $scrum)
    }
}
