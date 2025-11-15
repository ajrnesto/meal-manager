//
// Created by Arnold Arnesto on 11/2/25.
//

import Foundation
import shared

class IosViewModelStoreOwner: ObservableObject, ViewModelStoreOwner {
    let viewModelStore = ViewModelStore()

    /// Retrieves a ViewModel from the store
    func viewModel<T: ViewModel>(
        key: String? = nil,
        factory: ViewModelProviderFactory,
        extras: CreationExtras? = nil
    ) -> T {
        do {
            return try viewModelStore.resolveViewModel(
                modelClass: T.self,
                factory: factory,
                key: key,
                extras: extras
            ) as! T
        } catch {
            fatalError("Failed to create ViewModel of type \(T.self)")
        }
    }

    /// Called when the view is deinitialized
    deinit {
        viewModelStore.clear()
    }
}