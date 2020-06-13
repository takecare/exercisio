package io.exercis.exercises

import dagger.Subcomponent
import io.exercis.base.di.FragmentScope
import io.exercis.exercises.domain.DomainModule
import io.exercis.exercises.ui.ExercisesFragment

@FragmentScope
@Subcomponent(modules = [DomainModule::class])
interface ExercisesComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): ExercisesComponent
    }

    fun inject(fragment: ExercisesFragment)
}

interface ExercisesComponentProvider {
    fun provideExercisesComponent(): ExercisesComponent
}
