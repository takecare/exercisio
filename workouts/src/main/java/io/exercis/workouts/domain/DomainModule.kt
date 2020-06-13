package io.exercis.workouts.domain

import dagger.Module
import dagger.Provides
import io.exercis.base.di.ActivityScope
import io.exercis.base.di.FragmentScope
import io.exercis.workouts.data.DataModule

@Module(includes = [DataModule::class])
class DomainModule {

    @Provides
    @FragmentScope
    fun provideUseCase(repository: WorkoutsRepository): GetWorkoutsUseCase { // TODO drop this
        return GetWorkoutsUseCase(repository)
    }
}
