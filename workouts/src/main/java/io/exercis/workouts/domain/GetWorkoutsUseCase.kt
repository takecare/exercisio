package io.exercis.workouts.domain

import io.exercis.workouts.domain.model.Workout
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.toList

class GetWorkoutsUseCase(private val workoutsRepository: WorkoutsRepository) {
    suspend fun execute(): List<Workout> {
        // TODO set dispatcher

        // we're emitting one by one and then collecting them to a list here but we're fine with
        // that as we want to grasp flows first (and then we can drop these unnecessary operations)
        return workoutsRepository.getWorkouts().toList()
    }
}
