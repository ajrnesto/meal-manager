import SwiftUI
import shared

struct TestView: View {
    @StateObject private var viewModelStoreOwner = IosViewModelStoreOwner()

    var body: some View {
        // Retrieve the TestViewModel
        let viewModel: TestViewModel = viewModelStoreOwner.viewModel(
            factory: TestViewModelKt.testViewModelFactory
        )

        TestViewContent(viewModel: viewModel)
    }
}

struct TestViewContent: View {
    let viewModel: TestViewModel
    @State private var counter: Int32 = 0
    @State private var autoUpdate: Bool = false

    var body: some View {
        VStack(spacing: 20) {
            Text("Counter: \(counter)")
                .font(.largeTitle)

            Button("Increment") {
                viewModel.setCounter(value: counter + 1)
            }

            Toggle("Auto Update", isOn: $autoUpdate)
                .onChange(of: autoUpdate) { _, newValue in
                    viewModel.setAutoUpdate(value: newValue)
                }
        }
        .padding()
        .task {
            // Convert KotlinInt to Int32
            for await kInt in viewModel.counter {
                counter = kInt.int32Value
            }
        }
        .task {
            // Convert KotlinBoolean to Bool
            for await kBool in viewModel.autoUpdate {
                autoUpdate = kBool.boolValue
            }
        }
    }
}

