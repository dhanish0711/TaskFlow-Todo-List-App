# TaskFlow - Android Studio Internship Portfolio

This repository contains the projects and documentation completed during the App Development Internship using Android Studio.

## 📂 Repository Structure

The repository is structured as follows:
- **`app/`**: The core Android application module containing source code, layouts, and resource files.
- **`tasks/`**: A dedicated folder containing individual documentation for each internship task.
  - 📝 **[Task 1: Onboarding and Environment Setup](tasks/TASK_1.md)** - Initial setup and creation of the TaskFlow App.
  - 📝 **[Task 2: Advanced UI Design with Jetpack Compose](tasks/TASK_2.md)** - UI layouts and animations (*Planned*).
  - 📝 **[Task 3: Data Persistence and State Management](tasks/TASK_3.md)** - Room Database and ViewModel (*Planned*).
  - 📝 **[Task 4: API Integration and Networking](tasks/TASK_4.md)** - Networking and Retrofit API integration (*Planned*).
  - 📝 **[Task 5: App Optimization, Testing, and Release](tasks/TASK_5.md)** - Testing, WorkManager, and APK generation (*Planned*).

---

## 🛠️ Environment Setup & Installation Guide

To run this application locally, ensure you have the following prerequisites configured:

### 1. Install Java Development Kit (JDK)
Install JDK 17 or higher (JDK 20 is recommended for this project). Verify installation:
```bash
java -version
```

### 2. Install Android Studio
- Download and install the latest stable version of **Android Studio**.
- Ensure Android SDK Platform 36 and Android SDK Build-Tools are installed through the SDK Manager.

### 3. Clone and Initialize Repository
Clone this repository to your local machine:
```bash
git clone https://github.com/dhanish0711/TaskFlow-Todo-List-App.git
```

### 4. Build and Run Project
In the root directory of the project, run:
- **Build the debug APK**:
  ```bash
  ./gradlew assembleDebug
  ```
- **Run Unit Tests**:
  ```bash
  ./gradlew test
  ```

---

## 📱 Project Showcase: TaskFlow (Todo App)

**TaskFlow** is the Task 1 introductory Android project. It is built using **Kotlin** and **Jetpack Compose** (Material 3).

### Features
- **Dashboard Progress Card**: Real-time progress bar showing percentage of completed tasks.
- **Scrollable Category Filters**: Scroll and click category chips (All, Setup, App Dev, Study, Personal) to filter tasks.
- **Interactive Checkboxes**: Toggle task completion with custom checkmark animations.
- **Dynamic Task Addition**: Dialog modal form to quickly add custom tasks with specific categories.
- **Swipe or Tap to Delete**: Instantly remove tasks with red trashcan triggers.

---

## 🏆 Internship Milestones
- [x] **Task 1**: Onboarding, Environment Setup & TaskFlow App.
- [ ] **Task 2**: Jetpack Compose Advanced UI styling & Animations.
- [ ] **Task 3**: Room Database storage.
- [ ] **Task 4**: API integration.
- [ ] **Task 5**: Performance optimization and Release APK.
