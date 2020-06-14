package io.exercis.workouts.ui

import androidx.lifecycle.SavedStateHandle
import io.exercis.base.di.ViewModelAssistedFactory
import javax.inject.Inject

// TODO can we use kotlin poet to generate this easily?

class WorkoutDetailsViewModelFactory @Inject constructor(
    //
) : ViewModelAssistedFactory<WorkoutDetailViewModel> {

    override fun create(handle: SavedStateHandle): WorkoutDetailViewModel {
        return WorkoutDetailViewModel(handle)
    }
}
