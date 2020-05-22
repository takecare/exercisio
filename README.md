### exercis.io

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
