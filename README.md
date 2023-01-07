# Android Random User App

An app created as a fulfillment of a university assignment. It is capable of fetching random user information from the random user API, and displaying a list of users which were fetched so far. It also has a QR code scanner feature, which will display a user profile if it scans a QR code which contains the username of a user.

## Building

1. Open project in Android Studio
   * You may need to sync Gradle dependencies in case that's not done automagically
2. Build
3. ???
4. Profit

(it really should justwerk)

If it doesn't justwerk - make sure you use latest bleeding edge Android Studio. Don't forget to `git clean -xfdf`.

## Running

The build was tested using this particular Emulator config:

- Pixel 4
- API 33
- Android Tiramisu x86_64

To give this junk the best chance of actually working you probably want to have this too.

## Description

The following tasks have been implemented:

- SQLite-based database for users
   * Saves users
   * Loads users
   * Can delete users
- Accessing the random user API
- Deserializing JSON from API responses
- Using camera to scan QR codes using zxing library
- A user list screen, which lists users from database, sorted by creation date
   * On this screen, you may generate a random user one at a time using "Generate Random User" button
- A detailed user card triggered by a QR code scan, displaying all information about a user
- A "settings" screen which contains the following buttons:
   * A "Flush Database" button, which deletes all users in the database
   * A "Seed Database" button, which creates ten (10) random users fetched from the random user API

Liberties/Alternatives taken:

- This uses normal camera instead of AR view
- User details are displayed on a separate screen rather than in AR

## Footnotes

- Build was only tested on Linux using latest bleeding-edge Android Studio
- UI in general is really wonky and quickly slapped together. I am not very good at making UIs (unless it's HTML + CSS).
- Core features should work and were tested

## Legal

This project at some point used HelloArKotlin example, and some of its code may have not been completely cleaned up when I switched away from AR. See copyright notices in respective files.

## Links 

- [Git repo](https://github.com/Meow/testbed)
