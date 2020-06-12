package io.exercic.exercises.data

import io.exercic.exercises.data.model.ExerciseDataModel
import io.exercic.exercises.domain.Exercise
import io.exercic.exercises.domain.ExercisesRepository
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import javax.inject.Inject
import javax.inject.Named

interface ExercisesDataSource {
    //
    suspend fun getData(): List<ExerciseDataModel>
}

class NetworkExercisesDataSource : ExercisesDataSource {
    //
    override suspend fun getData(): List<ExerciseDataModel> {
        return coroutineScope {
            delay(200L)
            emptyList<ExerciseDataModel>()
        }
    }
}

class LocalExercisesDataSource : ExercisesDataSource {
    //
    override suspend fun getData(): List<ExerciseDataModel> {
        return coroutineScope {
            delay(100L)
            DummyData.exercises
        }
    }
}

class ExercisesRepositoryImpl @Inject constructor(
    @Named("remote") private val networkSource: ExercisesDataSource,
    @Named("local") private val localSource: ExercisesDataSource,
    private val mapper: Mapper
) : ExercisesRepository {

    override suspend fun getExercises(): Flow<Exercise> {
        val network = networkSource.getData().asFlow()
        val local = localSource.getData().asFlow()
        return merge(network, local)
            .map { mapper(it) }
    }

    override suspend fun addExercise(workout: Exercise) {
        // TODO: addWorkout not implemented
    }
}

class Mapper @Inject constructor() {
    operator fun invoke(data: ExerciseDataModel): Exercise {
        return Exercise(data.name, data.description)
    }
}
