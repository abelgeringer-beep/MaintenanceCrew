# Mobil

This application is made for the maintenance crew in so that they can easily track the tasks they have to do. They can also edit the task description if they find it necessary (if some steps were missed out or have found new problems that needs to be solved). The task can be modified to show what stage is it currently on (weather it is started or finished).

### Installing

* First you need to install `JAVA 8` and `PHP 7`
* Next you'll need [XAMPP](https://www.apachefriends.org/download.html) and [Android Studio](https://developer.android.com/studio)
* Then you have to copy the [api](https://github.com/abelgeringer-beep/MaintenanceCrew/tree/main/app/src/main/java/com/example/beadando/api) folder to the following location: `(YOUR XAMPP LOCATION)/htdocs`
* Start `XAMPP`and in it you need to start `Apache` and `MySQL`
* Create a new DB for the project and name it `Maintenance`
* Go to the SQL terminal a paste in the `db.sql` content and run it
* If you wish to test the api endpoints you can use [Postman](https://www.postman.com/downloads/)
* Open the mobil folder in Android Studio and graddle will install the necessary dependencies for the project

### Api

* `Config.php` Holds the necessary informations for the MySQL db
* `DB_Connect` Connects to the db
* `DB_Functions` necessary functins that are used at the api endpoints:
  * login.php needs a username and password with POST method
  * register.php needs username and password with POST method
  * tasksForUser.php needs an user_id with POST method
  * users.php just a simple GET method to get users 

### Android

When the program starts it shows the login page, on succesfull login the maintaner can see his/her tasks that are needed to be done. The tasks contain information on what should one do, the stage at which is it currently.
