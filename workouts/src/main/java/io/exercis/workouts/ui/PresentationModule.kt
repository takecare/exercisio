package io.exercis.workouts.ui

import dagger.Module
import dagger.Provides
import io.exercic.base.di.DelegateSavedStateRegistryOwner
import io.exercic.base.di.FragmentScope
import io.exercic.base.di.GenericSavedStateViewModelFactory
import io.exercic.base.di.ViewModelAssistedFactory
import io.exercis.workouts.domain.GetWorkoutsUseCase
import javax.inject.Named

@Module
class PresentationModule {

    @Provides
    @Named("workouts")
    @FragmentScope
    fun provideOwner(): DelegateSavedStateRegistryOwner {
        return DelegateSavedStateRegistryOwner()
    }

    @Provides
    fun provideWorkoutsViewModelFactory(getWorkoutsUseCase: GetWorkoutsUseCase): ViewModelAssistedFactory<WorkoutsViewModel> {
        return WorkoutsViewModelFactory(getWorkoutsUseCase)
    }

    @Provides
    @FragmentScope
    @Named("workouts")
    fun provideWorkoutsGenericSavedStateViewModelFactory(
        viewModelFactory: ViewModelAssistedFactory<WorkoutsViewModel>,
        @Named("workouts") owner: DelegateSavedStateRegistryOwner
    ): GenericSavedStateViewModelFactory<WorkoutsViewModel> {
        return GenericSavedStateViewModelFactory(viewModelFactory, owner)
    }
}
