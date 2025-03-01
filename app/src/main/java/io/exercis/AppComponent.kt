package io.exercis

import dagger.Component
import dagger.Module
import io.exercis.base.NetworkModule
import io.exercis.exercises.ExercisesComponent
import io.exercis.workouts.WorkoutsComponent
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, SubcomponentsModule::class])
interface AppComponent {

    fun workoutsComponentFactory(): WorkoutsComponent.Factory

    fun exercisesComponentFactory(): ExercisesComponent.Factory

//    fun injectIntoMainActivity(activity: MainActivity)
}

@Module(subcomponents = [WorkoutsComponent::class, ExercisesComponent::class])
class SubcomponentsModule {}
