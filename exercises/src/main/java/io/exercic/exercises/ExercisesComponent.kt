package io.exercic.exercises

import dagger.Subcomponent
import io.exercic.base.di.FragmentScope
import io.exercic.exercises.domain.DomainModule
import io.exercic.exercises.ui.ExercisesFragment

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
