# Task 1: Onboarding and Environment Setup

## Objective
To complete onboarding activities, set up the Android development environment, initialize Git, and build an introductory Android application to verify the development workflow.

---

## 1. Onboarding Timeline (Days 1-2)
- **Orientation Sessions**: Familiarized with company guidelines, development culture, and internship milestones.
- **Mentor Introduction**: Introduced to the development mentor and team members to align on task expectations.
- **Internship Plan**: Established a 5-task project plan with the final goal of mastering Android app development.

---

## 2. Environment Setup (Days 3-5)
To prepare for native Android application development, the following tools were installed and configured:
- **Java SE Development Kit (JDK)**: Installed JDK 20 to support modern Kotlin and Gradle toolchains.
- **Git & GitHub**: 
  - Installed Git version `2.53.0.windows.1`.
  - Configured user credentials and initialized the GitHub repository: [TaskFlow-Todo-List-App](https://github.com/dhanish0711/TaskFlow-Todo-List-App).
- **Android SDK & Platform Tools**:
  - Set up path variables for Android SDK tools.
  - Installed **Android CLI** (v1.0.15498356) for running emulator and ADB tasks from the command line.
  - Downloaded and configured the **Android 36 Platform SDK** (API 36).

---

## 3. App Project Selection: TaskFlow (Todo List)
For the introductory Android project, a **Todo List App (TaskFlow)** was selected to practice the fundamentals of Jetpack Compose:
- **Layouts**: column, row, card, and LazyColumn layouts.
- **State Management**: state hoisting, `remember`, and `rememberSaveable` to maintain UI state across screen rotations.
- **Material Design 3**: Dark theme, modern card shapes, and interactive text fields.

### Core Features:
- **Interactive Checklist**: Dynamic checklist where tapping checks off items with clean animations.
- **Task Categorization**: Group tasks by categories (Work, Personal, Urgent, etc.) and filter them using scrollable chips.
- **Completion Tracker**: A visual header detailing total tasks, completed tasks, and a progress percentage.
- **Add / Remove Operations**: Simple form input for adding new tasks, and clear button triggers to delete tasks.

---

## 4. How to Run the App
1. Open the project folder in **Android Studio**.
2. Sync the project with Gradle files.
3. Build the project:
   ```bash
   ./gradlew assembleDebug
   ```
4. Deploy the application to a connected device or emulator.
