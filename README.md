# Developer Stories

## Iteration 1 Developer Story
```
Start the Application: 

Launch Successorator by tapping the app icon. Expect to see the main dashboard with the date displayed at the top indicating "Today, [current date]."

Add a Task for Today: 

Tap the "+" button to add a new task. Enter "Finish revising paper" and select "Save". Expect to see the task “Finish revising paper” listed under "Today's" tasks.

Navigate to Tomorrow's View: 

Tap the dropdown menu and select "Tomorrow". Expect to see "Tomorrow, [date]" at the top and an empty task list.

Add a Task for Tomorrow: 

Tap the "+" and enter "Turn in paper". Select "Save". Expect to see the task “Turn in paper” listed under "Tomorrow's" tasks.

Check Pending Tasks View: 

Navigate to the "Pending" view from the dropdown menu. Expect to see an empty list titled "Pending" with no task listed so far.

Add a Pending Task: 

Tap "+" and enter "Research plane tickets". Select "Save". Expect to see the task listed under "Pending" tasks.

Move Pending Task to Today: 

Perform a long press on the "Research plane tickets" task and select "Move to Today". Expect to see the task now listed under "Today's" tasks.

Add a Recurring Task: 

Navigate to the "Recurring" view and tap "+". Select a start date for "Put out trash", enter the task, and tap "Save". Expect to see the task listed under "Recurring" with the schedule "weekly on Sunday".
```
## Iteration 2 Final Developer Story


#### Iteration 1 Coverage:
```
Start the Application: 

Launch Successorator by tapping the app icon. Expect to see the main dashboard with the date displayed at the top indicating "Today, [current date]."

Add a Task for Today: 

Tap the "+" button to add a new task. Enter "Finish revising paper" and select "Save". Expect to see the task “Finish revising paper” listed under "Today's" tasks.

Navigate to Tomorrow's View: 

Tap the dropdown menu and select "Tomorrow". Expect to see "Tomorrow, [date]" at the top and an empty task list.

Add a Task for Tomorrow: 

Tap the "+" and enter "Turn in paper". Select "Save". Expect to see the task “Turn in paper” listed under "Tomorrow's" tasks.

Check Pending Tasks View: 

Navigate to the "Pending" view from the dropdown menu. Expect to see an empty list titled "Pending" with no task listed so far.

Add a Pending Task: 

Tap "+" and enter "Research plane tickets". Select "Save". Expect to see the task listed under "Pending" tasks.

Move Pending Task to Today: 

Perform a long press on the "Research plane tickets" task and select "Move to Today". Expect to see the task now listed under "Today's" tasks.

Add a Recurring Task: 

Navigate to the "Recurring" view and tap "+". Select a start date for "Put out trash", enter the task, and tap "Save". Expect to see the task listed under "Recurring" with the schedule "weekly on Sunday".

```
#### Iteration 2 Coverage
```

Update and Relaunch the Application: 

After updating Successorator, relaunch the app. Expect to see the main dashboard with no immediate visual changes until a task is added.

Add a Task with Context: 

Tap "+" to add "Caffe Calabria - coffee" to Today's list and assign it the "Errands" context by tapping the "E" circle. Select "Save". Expect to see the task with a moss-green circle indicating the "Errands" context.

Add Multiple Tasks with Different Contexts: 

Add additional tasks assigning each a different context (Home, Work, School, Errands). Expect tasks to be grouped by context in the order “Home, Work, School, and Errands.”

Activate Focus Mode for School Context: 

Access the hamburger menu, select Focus Mode, and choose the "School" context. Expect to see only school-related tasks and a visual indicator of Focus mode.

Complete and Remove a Task: 

Mark a task as completed and expect it to be removed from the view and moved to a completed section, ensuring the app doesn’t display too many tasks at once.

Exit Focus Mode and Review: 

Exit Focus mode and return to the standard view. Review all tasks to ensure they are correctly organized and that the context functionality works across different views.

```




# Team 12 - Successorator 

## Project Description

Successorator is an innovative Android application designed to transform the way individuals manage their daily tasks and goals. Born out of the necessity to juggle multiple aspects of life seamlessly, this app introduces a simplified, yet powerful approach to task management. Unlike traditional to-do lists and calendar apps, Successorator employs a hierarchical structure that prioritizes the user's most important tasks (MITs) for the day, encouraging focus and productivity.

The creation of Successorator was inspired by the challenges faced by those struggling to balance work, school, and personal life. Recognizing the inefficiency of existing tools, our team developed Successorator to offer a straightforward solution that fits effortlessly into the user's lifestyle. The app leverages voice input for easy task entry, making it accessible and convenient for users on the go.

One of the unique features of Successorator is its emphasis on daily prioritization. By limiting the focus to a few select goals each day, users can achieve a sense of accomplishment and clarity. The application is designed to adapt to the user's needs, rolling over uncompleted tasks to the next day, ensuring that nothing falls through the cracks.

Our journey in developing Successorator involved navigating several challenges, particularly in creating an intuitive user interface that encourages daily engagement. We also focused on implementing efficient data management to ensure a seamless user experience. Future updates will aim to incorporate more personalized features, such as task categorization and progress tracking.

## Table of Contents

- [Project Description](#project-description)
- [Installation and Setup](#installation-and-setup)
- [How to Use](#how-to-use)
- [Credits](#credits)
  - [Developer Velcity](#developer-velocity)
- [License](#license)

## Installation and Setup

To install Successorator on your Android device, follow these simple steps:

1. Ensure your device is running Android version 13 (Tiramisu) or higher.
2. Download the Successorator APK (to be made available to the public).
3. Open the APK file on your device and allow installation from unknown sources if prompted.
4. Follow the on-screen instructions to complete the installation.
5. Once installed, open Successorator and grant the necessary permissions for optimal functionality.

## How to Use

To get started with Successorator:

1. Launch the app and tap the '+' icon to add your Most Important Thing (MIT) for the day.
2. Use the microphone icon for voice input or the keyboard to type in your task.
3. To mark a task as completed, simply tap on it. Completed tasks will move to the bottom and appear in strike-through.
4. Unfinished tasks automatically roll over to the next day, ensuring you never lose track of your priorities.
5. Click Timeskip button to mock run the app on the next day delete all completed task (implemented with the intention to test the roll over function)

Remember, Successorator is designed to focus on simplicity and effectiveness, helping you achieve your most crucial goals each day.

## Credits

![Custom Badge](https://img.shields.io/badge/Contributors-6-blue)

Successorator was developed by a dedicated team of developers passionate about creating practical solutions for everyday challenges. Special thanks to:

- [OscarKhaing](https://github.com/OscarKhaing)
- [shruti-create](https://github.com/shruti-create)
- [stevendtran](https://github.com/stevendtran)
- [claireandolina](https://github.com/claireandolina)
- [KaChunLeung](https://github.com/KaChunLeung)
- [dr-donut](https://github.com/dr-donut)

We also acknowledge the valuable insights and suggestions from our beta testers and the continuous support from our user community in the future.

#### Developer Velocity
MS1
- [x] iteration 1:
   - 25/72 hours we can work was used
   - 22 were expected to be used, ( 1 uncompleted task )
   - 12/25 = 0.48

- [x] iteration 2:
   - 72*(12/25) = 34.56 hours

MS2
- [x] iteration 1:
   - 22 hours of work was expected.
   - 30/72 hours of work was used.
   - 22/30 = 0.73
We were able to complete all the task we wanted for iteration 1 within iteration 1. However, because we did not take account of decencies of the task, we were not able to do the testing for any of the User stories. We need to complete the testing in iteration 2 after everything that is needed for testing is completed.

- [x] iteration 2:
   - 42 * (22/30) = 30.8 hours


## License

Successorator is made available under the GPL License, allowing for modification and use in both personal and commercial projects. For more details on the license and your rights under it, please visit [GPL License](https://www.gnu.org/licenses/gpl-3.0.en.html).

For further assistance or to provide feedback, please contact us through our project repository or social media platforms.

