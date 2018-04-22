# ToDo Lite Android MVP example [![Build Status](https://circleci.com/gh/waleedsarwar86/todo-mvp.svg?style=shield)](https://circleci.com/gh/waleedsarwar86/todo-mvp)


Todo Lite app, shows the **MVP** pattern usage on Android.

## Screenshots:
<p float="left">
<img src="screenshots/1-login.png" width="220"/>
<img src="screenshots/2-home_empty.png" width="220"/>
<img src="screenshots/3-home_tasks.png" width="220"/>
<img src="screenshots/4-add_edit_task.png" width="220"/>
<img src="screenshots/5-add_edit_task_deadline.png" width="220"/>
<img src="screenshots/6-add_edit_task_priority.png" width="220"/>


</p>

## App

- Login/Register 
- Add Task
- Update Task
- Delete/Complete Task by swiping the card left/right
- Set Reminder on the Task. User will be notified by notification.

## Project structure

**data**: Implementation of the Repository pattern, contains all the data accessing.<br/>
**di**: Contains the classes that provide dependencies using Dagger2.<br />
**ui**: MVP structure, all Activities, Fragments and UI elements in this package.<br />
**util**: Utility classes.<br />

## Tools/Libraries

* [Constraint Layout](https://developer.android.com/training/constraint-layout/index.html)
* [Firebase Auth](https://firebase.google.com/docs/auth/)
* [Firebase Database](https://firebase.google.com/docs/database/)
* [Firebase JobDispatcher](https://github.com/firebase/firebase-jobdispatcher-android)
* [RxJava 2](https://github.com/ReactiveX/RxJava)
* [RxAndroid 2](https://github.com/ReactiveX/RxAndroid)
* [RxBindings 2](https://github.com/JakeWharton/RxBinding)
* [ButterKnife](https://github.com/JakeWharton/butterknife)
* [Timber](https://github.com/JakeWharton/timber)
* [Dagger 2](https://github.com/google/dagger)

## Future work

### Todo

- Update form validatoins code.
- Write Unit and instrumentation tests
- Convert the project to kotlin.

