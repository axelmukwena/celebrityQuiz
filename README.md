# Celebrity Quiz

Feel free to use the sourcecode :recycle::grinning:

A simple Android quiz app that allows a user to select a posibble answer to each question, given a hint by an image.

Build in Android Studio 3.5.3, Minimum SDK Version 22

### Libraries Used
* [Litepal](https://github.com/LitePalFramework/LitePal): for managing sqlite database configurations
* [OkHttp](https://github.com/codepath/android_guides/wiki/Using-OkHttp): for sending and receive HTTP-based network requests
* [Glide](https://github.com/bumptech/glide): media management and image loading
* [Gson](https://github.com/google/gson): convert Java Objects into their JSON representation and vise versa

## Core Idea
* This app allows a user to answer multiple choice questions (some or all per choice)

### How to use the app
* Main window shows app name, and two menu buttons: Score and Settings page
  * Score page Allows the user to view how much of the answers selected are correct out of total answers A well-done image will be displayed if user gets all the answers correct.
  
  * Settings page Allows the user to change the level of difficulty of the game Easy mode: 6 questions Hard Mode: 10 questions  
* Main content contains mutiple questions, each has an image related to the question and possible answers that can be selected
* If user leaves the main page, the answers selected, and the user’s score are/is reset


