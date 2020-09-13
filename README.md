# TypeSafeMapMap

![gradle-build-test](https://github.com/dave99galloway/TypeSafeMapMap/workflows/gradle-build-test/badge.svg)
## great project, daft name

### Purpose
to create a type safe Map of Maps where the outer map items are stored by type of the values in the inner maps.

in tests this can be used to store lots of related items of different types and retrieve them using type inference and no nasty casting (on the client side anyway)

### Usage 
currently not published as a package on maven central or github packages. until this is resolved, use this workaround in your build.gradle

```$groovy
plugins {
...
    id "de.undercouch.download" version "4.1.1"
}
...

repositories {
    mavenCentral()
    flatDir {
        dirs 'libs'
    }
}
...

dependencies {
...
    implementation files('libs/TypeSafeMapMap-shadow-0.1.2.jar')
...
}
...

task downloadFile(type: Download) {
    src 'https://github.com/dave99galloway/TypeSafeMapMap/releases/download/0.1.2/TypeSafeMapMap-shadow-0.1.2.jar'
    dest 'libs'// buildDir
    onlyIfModified  true
}

compileKotlin {
    dependsOn(downloadFile)
}
```