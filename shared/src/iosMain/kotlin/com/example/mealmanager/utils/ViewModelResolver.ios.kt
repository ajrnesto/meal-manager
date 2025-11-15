package com.example.mealmanager.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.viewmodel.CreationExtras
import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ObjCClass
import kotlinx.cinterop.getOriginalKotlinClass
import kotlin.reflect.KClass

@OptIn(BetaInteropApi::class)
@Throws(IllegalArgumentException::class)
fun ViewModelStore.resolveViewModel(
    modelClass: ObjCClass,
    factory: ViewModelProvider.Factory,
    key: String? = null,
    extras: CreationExtras? = null,
): ViewModel {
    @Suppress("UNCHECKED_CAST")
    val vmClass = getOriginalKotlinClass(modelClass) as? KClass<ViewModel>
    require(vmClass != null) {
        "The modelClass parameter must be a ViewModel type."
    }

    val provider = ViewModelProvider.Companion.create(
        this,
        factory,
        extras ?: CreationExtras.Empty
    )
    return key?.let { provider[key, vmClass] } ?: provider[vmClass]
}