package io.exercis.workouts

import dagger.Subcomponent
import io.exercis.base.di.FragmentScope
import io.exercis.workouts.domain.DomainModule
import io.exercis.workouts.ui.PresentationModule
import io.exercis.workouts.ui.WorkoutDetailsFragment
import io.exercis.workouts.ui.WorkoutsFragment

@FragmentScope
@Subcomponent(modules = [DomainModule::class, PresentationModule::class])
interface WorkoutsComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): WorkoutsComponent
    }

    fun inject(fragment: WorkoutsFragment)
    fun inject(fragment: WorkoutDetailsFragment)
}

interface WorkoutsComponentProvider {
    fun provideWorkoutsComponent(): WorkoutsComponent
}
