package io.exercis.workouts.ui

import androidx.lifecycle.SavedStateHandle
import io.exercic.base.di.ViewModelAssistedFactory
import io.exercis.workouts.domain.GetWorkoutsUseCase
import javax.inject.Inject

class WorkoutsViewModelFactory @Inject constructor(
    private val getWorkouts: GetWorkoutsUseCase
) : ViewModelAssistedFactory<WorkoutsViewModel> {

    override fun create(handle: SavedStateHandle): WorkoutsViewModel {
        return WorkoutsViewModel(getWorkouts, handle)
    }
}
