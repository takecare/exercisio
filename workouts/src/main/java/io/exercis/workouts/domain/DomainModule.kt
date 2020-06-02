package io.exercis.workouts.domain

import dagger.Module
import dagger.Provides
import io.exercic.base.ActivityScope
import io.exercis.workouts.data.DataModule

@Module(includes = [DataModule::class])
class DomainModule {

    @Provides
    @ActivityScope
    fun provideUseCase(repository: WorkoutsRepository): GetWorkoutsUseCase {
        return GetWorkoutsUseCase(repository)
    }
}
