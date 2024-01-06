import SwiftUI
import scrumdingerShared

@main
struct iOSApp: App {
    @State var scrums = DailyScrum.companion.sampleDaily
	var body: some Scene {
		WindowGroup {
			ScrumView(scrums: $scrums)
		}
	}
}
