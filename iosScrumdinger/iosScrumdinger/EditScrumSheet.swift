//
//  EditScrumSheet.swift
//  iosScrumdinger
//
//  Created by Игорь Пивоваров on 17.01.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import scrumdingerShared

struct EditScrumSheet: View {
    @State private var editingScrum: DailyScrum = DailyScrum.companion.empty
    @Binding var isPresentingEditView: Bool
    @Binding var scrum: DailyScrum
    var body: some View {
        NavigationStack {
            DetailEditView(scrum: $editingScrum)
                .toolbar {
                    ToolbarItem(placement: .confirmationAction) {
                        Button("Done") {
                            scrum = editingScrum
                            isPresentingEditView = false
                        }
                    }
                    ToolbarItem(placement: .cancellationAction) {
                        Button("Dissmiss") {
                            isPresentingEditView = false
                        }
                    }
                }
                .onAppear {
                    editingScrum = scrum
                }
        }
    }
}

#Preview {
    EditScrumSheet(isPresentingEditView: .constant(true), scrum: .constant(DailyScrum.companion.sampleScrum))
}