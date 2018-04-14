ToDo, Android MVP example
==========

Simple Todo List app, shows the **MVP** pattern usage on Android.

# Screenshots:
<img src="screenshots/1-login.png" />
<img src="screenshots/2-home_empty.png" />
<img src="screenshots/3-home_tasks.png" />
<img src="screenshots/4-add_edit_task.png" />




## Project structure

**data**: Implementation of the Repository pattern, contains all the data accessing.
**di**: Contains the classes that provide dependencies.         
**ui**: MVP structure, all Activities, Fragments and UI elements in this package.          
**util**: Utility classes.

## Tools
* Dagger 2
* RxJava
* Retrofit 2
* ButterKnife
