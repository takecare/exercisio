package io.exercis

import android.app.Application
import io.exercis.workouts.WorkoutsComponent
import io.exercis.workouts.WorkoutsComponentProvider

class ExecisioApplication : Application(), WorkoutsComponentProvider {
    lateinit var applicationComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        applicationComponent = DaggerAppComponent.builder()
            .build()
    }

    override fun provideWorkoutsComponent(): WorkoutsComponent {
        return applicationComponent.workoutsComponentFactory().create()
    }
}

