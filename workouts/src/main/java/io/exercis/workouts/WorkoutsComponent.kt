package io.exercis.workouts

import dagger.Subcomponent
import io.exercic.base.di.FragmentScope
import io.exercis.workouts.domain.DomainModule
import io.exercis.workouts.ui.PresentationModule
import io.exercis.workouts.ui.WorkoutsFragment

@FragmentScope
@Subcomponent(modules = [DomainModule::class, PresentationModule::class])
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
