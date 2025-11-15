//
// Created by Arnold Arnesto on 11/1/25.
//

import Foundation
import SwiftUI
import shared

struct CounterScreen: View {
    @StateObject private var viewModelStoreOwner = IosViewModelStoreOwner()

    var body: some View {
        let viewModel: TestViewModel = viewModelStoreOwner.viewModel(
            factory: TestViewModelKt.testViewModelFactory
        )

        CounterScreenContent(viewModel: viewModel)
    }
}

struct CounterScreenContent: View {
    let viewModel: TestViewModel
    @State private var counter: Int32
    @State private var autoUpdate: Bool
    
    init(viewModel: TestViewModel) {
        self.viewModel = viewModel
        
        let currentCounter: Int32 = Int32(truncatingIfNeeded: viewModel.counter.value.intValue)
        _counter = State(initialValue: currentCounter)
        
        let currentAutoUpdate: Bool = Bool(viewModel.autoUpdate.value.boolValue)
        _autoUpdate = State(initialValue: currentAutoUpdate)
    }
    
    var body: some View {
        VStack {
            Text("\(counter)")
            
            TextField(
                "0",
                text: Binding(
                    get: { String(counter) },
                    set: { newValue in
                        // Keep only digits and convert to Int; fallback to 0 if empty
                        let digitsOnly = newValue.filter { $0.isNumber }
                        counter = Int32(digitsOnly) ?? 0
                        
                        if (digitsOnly.count < 1) {
                            counter = 0
                        }
                    }
                )
            )
            .keyboardType(.numberPad)
            .textFieldStyle(.roundedBorder)
            .onChange(of: counter) { oldValue, newValue in
                print()
            }
            .padding()
            
            Toggle("Enable Auto-update", isOn: $autoUpdate)
                .onChange(of: autoUpdate) { _, newValue in
                    viewModel.setAutoUpdate(value: newValue)
                }
        }
        .padding()
        .task {
            for await kInt in viewModel.counter {
                counter = kInt.int32Value
            }
            
            for await kBool in viewModel.autoUpdate {
                autoUpdate = kBool.boolValue
            }
        }
    }
}
