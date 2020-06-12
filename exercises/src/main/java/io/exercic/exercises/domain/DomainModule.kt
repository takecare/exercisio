package io.exercic.exercises.domain

import dagger.Module
import io.exercic.exercises.data.DataModule

@Module(includes = [DataModule::class])
class DomainModule {
    //
}
