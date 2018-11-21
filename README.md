# MetricsReloaded-gradle
For add this project as a submodule please write this lines in your `build.gradle` file:
```
plugins {
    id 'org.jetbrains.intellij' version '0.3.12' apply false
}

allprojects {
    apply plugin: 'java'
    sourceCompatibility = 1.8

    apply plugin: 'org.jetbrains.intellij'
}
```
