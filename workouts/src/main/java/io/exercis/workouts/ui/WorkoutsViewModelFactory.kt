package io.exercis.workouts.ui

import androidx.lifecycle.SavedStateHandle
import io.exercic.base.di.ViewModelAssistedFactory
import io.exercis.workouts.domain.GetWorkoutsUseCase
import javax.inject.Inject

// https://developer.android.com/topic/libraries/architecture/viewmodel-savedstate

//class WorkoutsViewModelFactory @Inject constructor(
//    private val getWorkouts: GetWorkoutsUseCase,
//    owner: SavedStateRegistryOwner,
//    defaultArgs: Bundle? = null
//) : AbstractSavedStateViewModelFactory(owner, defaultArgs) {
//
//    @Suppress("UNCHECKED_CAST")
//    override fun <T : ViewModel?> create(
//        key: String,
//        modelClass: Class<T>,
//        handle: SavedStateHandle
//    ): T {
//        return if (modelClass.isAssignableFrom(WorkoutsViewModel::class.java)) {
//            WorkoutsViewModel(getWorkouts, handle)
//        } else {
//            throw IllegalStateException("Cannot produce ViewModel: key=$key, modelClass=$modelClass")
//        } as T
//    }
//}

class WorkoutsViewModelFactory @Inject constructor(
    private val getWorkouts: GetWorkoutsUseCase
) : ViewModelAssistedFactory<WorkoutsViewModel> {

    override fun create(handle: SavedStateHandle): WorkoutsViewModel {
        return WorkoutsViewModel(getWorkouts, handle)
    }
}
