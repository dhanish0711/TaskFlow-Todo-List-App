# Task 2: Designing and Implementing UI/UX (Days 10-18)

## Objective
To design and implement a premium user interface (UI) and user experience (UX) for the TaskFlow application using Jetpack Compose and Material 3 design systems.

---

## Accomplished Activities

### 🎨 1. Premium Color Palettes & Dark Theme Engine
- Created 4 premium Material 3 design systems in [Color.kt](file:///e:/App%20Development/app/src/main/java/com/example/androidbasicstutorial/theme/Color.kt):
  - **Classic Indigo**: Professional corporate look.
  - **Neon Teal**: High-contrast modern coder vibe.
  - **Rose Pink**: Vibrant, creative energetic palette.
  - **Amber Sunset**: Warm, elegant cozy aesthetic.
- Engineered a dynamic theme resolver in [Theme.kt](file:///e:/App%20Development/app/src/main/java/com/example/androidbasicstutorial/theme/Theme.kt) which applies light/dark modes and color options instantly across the entire application interface.

### 🏛️ 2. Advanced State Filtering (Search, Category & Sorting)
- Updated [TodoTask.kt](file:///e:/App%20Development/app/src/main/java/com/example/androidbasicstutorial/data/TodoTask.kt) with `TaskPriority` (LOW, MEDIUM, HIGH) and `createdAt` timestamp fields.
- Implemented state combinations in [MainScreenViewModel.kt](file:///e:/App%20Development/app/src/main/java/com/example/androidbasicstutorial/ui/main/MainScreenViewModel.kt) to filter task elements dynamically:
  - **Search**: Dynamic matching of title queries (case-insensitive).
  - **Category**: Interactive chips filter row.
  - **Sort Options**: Sorting list elements by *Date Created*, *Alphabetical*, or *Priority Level*.

### 📱 3. Multi-Tab Scaffold & Custom Dashboards
Overhauled [MainScreen.kt](file:///e:/App%20Development/app/src/main/java/com/example/androidbasicstutorial/ui/main/MainScreen.kt) to use a bottom `NavigationBar` hosting three distinct views:
1. **Dashboard Tab**:
   - Features a custom **Animated Circular Progress Gauge** drawn via a Canvas stroke path showing the percentage of completed tasks.
   - Summarized statistics cards (Total tasks, total active categories).
   - "Recent Tasks" section listing the 3 most recently created tasks with color-coded priority badges.
2. **Tasks Tab**:
   - Contains a search bar with quick-clear functionality.
   - Categorized chip scroll list.
   - Floating Action Button to launch a styled dialog with priority segmented controls.
3. **Settings Tab**:
   - Provides theme switches to toggle Dark Mode.
   - Segmented buttons to select Indigo, Teal, Pink, or Amber color themes.
   - A confirmable danger-zone button to "Clear All Tasks".

---

## Build Verification
We ran the compilation wrapper to verify code correctness:
```powershell
.\gradlew.bat assembleDebug --no-daemon --stacktrace
```
- **Result**: `BUILD SUCCESSFUL` in 2 minutes 8 seconds.
- **Generated Package**: `app/build/outputs/apk/debug/app-debug.apk` is updated and ready to be run in Android Studio!
