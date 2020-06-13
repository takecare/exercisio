package io.exercis.exercises.data

import dagger.Module
import dagger.Provides
import io.exercis.base.di.FragmentScope
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
    @Named("local") // TODO custom annotation
    @FragmentScope
    fun provideLocalDataSource() =
        LocalExercisesDataSource()

    @Provides
    @Named("remote")
    @FragmentScope
    fun provideRemoteDataSource() =
        NetworkExercisesDataSource()
}
