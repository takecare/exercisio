### exercis.io

#### App structure

- Home (3 tabs)
-- Workouts: list of all stored/defined workouts
-- Home: details on last workout (e.g. date, duration)
-- Settings: app-wide settings

- Workout: name, sortable list of exercises that are part of this workout & button to add exercise

- Exercise: details about an exercise

- In-play: workout in play (details like timer, current exercise, next exercise, etc.)

##### Concerns

- Since we're gonna have the one activity approach, this means the home screen will consist of a
fragment that hosts the other 3 fragments (workouts, home and settings)

#### Module structure

Structure still under review.

- App: main module
- Workouts: list of workouts, creating a new workout flow (will probably depend on 'exercises')
- Exercises: exercise screen, new exercise flow
- Session: exercise session/in-play
- Settings: app-wide settings, including settings screen

#### Architecture and dependencies

Questions left to answer:

- MVVM vs MVI?
- RxJava, yes or no?

##### Dependencies

- ViewModel
- Room

- Dagger

- OkHttp
- Retrofit
