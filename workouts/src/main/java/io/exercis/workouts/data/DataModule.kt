package io.exercis.workouts.data

import dagger.Module
import dagger.Provides
import io.exercis.base.di.FragmentScope
import io.exercis.workouts.domain.WorkoutsRepository
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Named

@Module
class DataModule {

    @Provides
    @FragmentScope
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .client(client)
            .build()
        return retrofit
    }

    @Provides
    @FragmentScope
    fun bindWorkoutsRepository(
        @Named("remote") networkWorkoutsDataSource: WorkoutsDataSource,
        @Named("local") localWorkoutsDataSource: WorkoutsDataSource,
        mapper: Mapper
    ): WorkoutsRepository {
        return WorkoutsRepositoryImpl(networkWorkoutsDataSource, localWorkoutsDataSource, mapper)
    }

    @Provides
    @Named("local") // TODO custom annotation
    @FragmentScope
    fun provideLocalDataSource(): WorkoutsDataSource {
        return LocalWorkoutsDataSource()
    }

    @Provides
    @Named("remote")
    @FragmentScope
    fun provideRemoteDataSource(): WorkoutsDataSource {
        return NetworkWorkoutsDataSource()
    }
}
