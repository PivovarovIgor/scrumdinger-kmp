import SwiftUI
import scrumdingerShared

struct ContentView: View {
	let greet = Greeting().greet()

    var body: some View {
        ScrumView(scrums: <#T##Binding<[DailyScrum]>#>)
    }
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		ContentView()
	}
}
