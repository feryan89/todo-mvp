ToDo, Android MVP example
==========

Simple Todo List app, shows the **MVP** pattern usage on Android.

# Screenshots:
<p float="left">
<img src="screenshots/1-login.png" width="200"/>
<img src="screenshots/2-home_empty.png" width="200"/>
<img src="screenshots/3-home_tasks.png" width="200"/>
<img src="screenshots/4-add_edit_task.png" width="200"/>

</p>



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
