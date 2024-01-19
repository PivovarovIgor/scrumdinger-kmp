//
//  DailyScrumEx.swift
//  iosScrumdinger
//
//  Created by Игорь Пивоваров on 07.01.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import scrumdingerShared

extension DailyScrum : Identifiable {
    func copy(pTitle: String? = nil, attendees: [Attendee]? = nil, pLengthInMinutes: Int32? = nil, theme: Theme? = nil, id: String? = nil) -> DailyScrum {
        return self.doCopy(
            title: pTitle ?? self.title,
            attendees: attendees ?? self.attendees,
            lengthInMinutes: pLengthInMinutes ?? self.lengthInMinutes,
            theme: theme ?? self.theme,
            id: id ?? self.id
        )
    }
}

extension DailyScrum.Attendee : Identifiable {
    public var id: String {
        return self.name
    }
}