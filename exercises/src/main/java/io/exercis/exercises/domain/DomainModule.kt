package io.exercis.exercises.domain

import dagger.Module
import io.exercis.exercises.data.DataModule

@Module(includes = [DataModule::class])
class DomainModule {
    //
}
