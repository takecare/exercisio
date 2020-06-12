package io.exercis

import android.app.Application
import io.exercis.exercises.ExercisesComponentProvider
import io.exercis.workouts.WorkoutsComponentProvider

class ExecisioApplication : Application(), WorkoutsComponentProvider,
    ExercisesComponentProvider {
    lateinit var applicationComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        applicationComponent = DaggerAppComponent.builder()
            .build()
    }

    override fun provideWorkoutsComponent() =
        applicationComponent.workoutsComponentFactory().create()

    override fun provideExercisesComponent() =
        applicationComponent.exercisesComponentFactory().create()
}

