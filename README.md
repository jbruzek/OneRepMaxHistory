# OneRepMaxHistory

Find a history of your one rep max lift values by analyzing a file of workouts

One rep max values are calculated using the Brzycki Formula.

## Using the app
There are a number of input files in the input directory, featuring various data scenarios to be viewed in the app. Transfering the files either to your Android device [or an emulator](https://stackoverflow.com/questions/30434451/how-to-push-files-to-an-emulator-instance-using-android-studio) allows them to be accessed from the file chooser in the application.

## Design Considerations
#### One Activity Many Fragments
With I/O 2018, Google has made it clear that the direction moving forward for Android applications is to use one activity and many fragments. This app is designed in that style, with the different screens of the application taking place within fragments, not separate activities.

#### Singleton Design Pattern
The calculator logic in the app is placed within a singleton object. The decision to use a singleton is reasonable for the scale of this app, although it is not a recommended design consideration for a larger Android app. Additionally, the choice to use a singleton was made to explore the Kotlin `object` keyword, as I have not used it before in a project. The data is stored in the calculator object using map of lists. This is satisfactory for the scale of this application as we don't expect the data to be extremely large. If the data was expected to change in any meaningful way going forward, for example if we were importing and having multiple workout histories available, a different method of storage should be explored, probably either a local SQLite db or a remote server.

#### Importing Data Files
The app uses a menu item as a button for importing new data files. This makes it easy to import new different files into the app. Presumedly inside of a larger application this functionality would not be as prominently displayed in the toolbar.

#### Github Management
I treated this repository as if it were being worked on in a team environment. All new features are developed in separate branches and rebased into master using pull requests. Issues and enhancements are tracked in the repo.

There are some things that I did not do in this Github repo for this project, that I would have if this were a larger project:
* Protected Branch - Ideally the master branch (or a staging branch in front of master) would be protected and require a status check before a PR can be merged.
* Continuous Integration - As part of the protected branch, there should be a CI instance set up with this repo that runs tests on every PR before rebase. No broken code should ever make it into the master branch.
* Required Reviews - All PRs should require not only a passing test run, but also a positive review before being able to rebase.

## Assumptions
There are a few key assumptions I made when building this project

* Data Format - The format of the imported data is assumed to be in the form `MMM dd YYYY,ExerciseName,Sets,Reps,Weight`, e.g. `Oct 05 2017,Barbell Bench Press,1,2,225` This assumption prevented me from spending extra time building out data validation steps into the app. I fully admit there are failing cases existing in this app if the data is not in the assumed format.

* Exercise Names are Standard - With the data consistence above, I assumed a standard naming convention for exercise names. My implementation would consider `Barbell Bench Press` and `barbell bench press` to be two different exercises.

* Data is in Imperial Units - I assumed all data was in lbs, and did not provide a method for alternating between imperial and metric units.

## Libraries Used

I used the [MPAndroidChart](https://github.com/PhilJay/MPAndroidChart) library for displaying the chart. I have never built a chart in Android before, but with some research, this library seemed to be a robust and stable solution for displaying a chart.
