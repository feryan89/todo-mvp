ToDo, Android MVP example
==========

Simple Todo List app, shows the **MVP** pattern usage on Android.

# Screenshots:
<p float="left">
<img src="screenshots/1-login.png" width="220"/>
<img src="screenshots/2-home_empty.png" width="220"/>
<img src="screenshots/3-home_tasks.png" width="220"/>
<img src="screenshots/4-add_edit_task.png" width="220"/>

</p>



## Project structure

**data**: Implementation of the Repository pattern, contains all the data accessing.<br/>
**di**: Contains the classes that provide dependencies.<br />
**ui**: MVP structure, all Activities, Fragments and UI elements in this package.<br />
**util**: Utility classes.<br />

## Tools
* Dagger 2
* RxJava
* Retrofit 2
* ButterKnife
