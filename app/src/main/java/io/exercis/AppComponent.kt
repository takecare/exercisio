package io.exercis

import dagger.Component
import dagger.Module
import io.exercic.base.NetworkModule
import io.exercis.workouts.WorkoutsComponent
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, SubcomponentsModule::class])
interface AppComponent {

    fun workoutsComponentFactory(): WorkoutsComponent.Factory

//    fun injectIntoMainActivity(activity: MainActivity)
}

@Module(subcomponents = [WorkoutsComponent::class])
class SubcomponentsModule {}
