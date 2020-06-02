package io.exercis.workouts

import dagger.Subcomponent
import io.exercic.base.ActivityScope
import io.exercis.workouts.domain.DomainModule
import io.exercis.workouts.ui.WorkoutsFragment

@ActivityScope
@Subcomponent(modules = [DomainModule::class])
interface WorkoutsComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): WorkoutsComponent
    }

    fun inject(fragment: WorkoutsFragment)
}

interface WorkoutsComponentProvider {
    fun provideWorkoutsComponent(): WorkoutsComponent
}
