package io.exercis.workouts.domain

import dagger.Module
import dagger.Provides
import io.exercic.base.di.ActivityScope
import io.exercic.base.di.FragmentScope
import io.exercis.workouts.data.DataModule

@Module(includes = [DataModule::class])
class DomainModule {

    @Provides
    @FragmentScope
    fun provideUseCase(repository: WorkoutsRepository): GetWorkoutsUseCase { // TODO drop this
        return GetWorkoutsUseCase(repository)
    }
}
