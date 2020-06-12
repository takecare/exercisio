package io.exercis.workouts.data

import io.exercis.workouts.data.model.WorkoutDataModel
import io.exercis.workouts.domain.WorkoutsRepository
import io.exercis.workouts.domain.model.Workout
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import javax.inject.Inject

// https://proandroiddev.com/kotlin-flow-on-android-quick-guide-76667e872166
// https://medium.com/androiddevelopers/rxjava-to-kotlin-coroutines-1204c896a700
// https://codelabs.developers.google.com/codelabs/advanced-kotlin-coroutines/#0

// using data sources this way (local and remote) implies usage of streams (i.e. reactive
// programming). this brings about a set of issues, namely correctly merging the two streams (local
// and remote)

interface WorkoutsDataSource {
    //
    suspend fun getData(): List<WorkoutDataModel>
}

class NetworkWorkoutsDataSource : WorkoutsDataSource {
    // retrofit service as dependency here?

    override suspend fun getData(): List<WorkoutDataModel> {
        return coroutineScope {
            delay(20L)
            emptyList()
        }
    }
}

class LocalWorkoutsDataSource : WorkoutsDataSource {
    // db as dependency here? how?

    override suspend fun getData(): List<WorkoutDataModel> {
        return coroutineScope {
            delay(100L)
            DummyData.workouts
        }
    }
}

// our local db will act as a cache as offline-mode enabler. we'll need a way of maintaining
// consistency
internal class WorkoutsRepositoryImpl(
    private val networkDataSource: WorkoutsDataSource,
    private val localDataSource: WorkoutsDataSource,
    private val mapper: Mapper
) : WorkoutsRepository {

    @ExperimentalCoroutinesApi
    override suspend fun getWorkouts(): Flow<Workout> {
        val network = networkDataSource.getData().asFlow()
        val local = localDataSource.getData().asFlow()
        return merge(network, local)
            .map { mapper(it) }
    }

    override suspend fun addWorkout(workout: Workout) {
        //
    }
}

//internal typealias Mapper = (data: WorkoutDataModel) -> Workout
/*internal*/ class Mapper @Inject constructor() {
    operator fun invoke(data: WorkoutDataModel): Workout {
        return Workout(data.name, data.description, emptyList())
    }
}
