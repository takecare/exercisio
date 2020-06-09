package io.exercis.workouts.data

import dagger.Module
import dagger.Provides
import io.exercic.base.di.ActivityScope
import io.exercis.workouts.domain.WorkoutsRepository
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Named

@Module
class DataModule {

    @Provides
    @ActivityScope
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .client(client)
            .build()
        return retrofit
    }

//    @Provides
//    @ActivityScope
//    fun provideLoginService(retrofit: Retrofit): LoginService {
//        return retrofit.create(LoginService::class.java)
//    }

    @Provides // @Binds
    @ActivityScope
    fun bindUserRepository(
        @Named("remote") networkWorkoutsDataSource: WorkoutsDataSource,
        @Named("local") localWorkoutsDataSource: WorkoutsDataSource,
        mapper: Mapper
    ): WorkoutsRepository {
        return WorkoutsRepositoryImpl(networkWorkoutsDataSource, localWorkoutsDataSource, mapper)
    }

    @Provides
    @Named("local") // TODO custom annotation
    @ActivityScope
    fun provideLocalDataSource(): WorkoutsDataSource {
        return LocalWorkoutsDataSource()
    }

    @Provides
    @Named("remote")
    @ActivityScope
    fun provideRemoteDataSource(): WorkoutsDataSource {
        return NetworkWorkoutsDataSource()
    }

//    @Provides
//    @ActivityScope
//    fun provideMapper(): Mapper {
//        return { } // TODO
//    }
}
