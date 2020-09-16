# TypeSafeMapMap

![gradle-build-test](https://github.com/dave99galloway/TypeSafeMapMap/workflows/gradle-build-test/badge.svg)
## great project, daft name

### Purpose
to create a type safe Map of Maps where the outer map items are stored by type of the values in the inner maps.

in tests this can be used to store lots of related items of different types and retrieve them using type inference and no nasty casting (on the client side anyway)

### Usage 
you can add this dependency to your build.gradle ...
```groovy
compile 'com.github.dave99galloway.TypeSafeMapMap:typesafemapmap:0.1.10'
```
BUT you need to provide your username and a token to do this. I figure most people won't do this so follow the workaround below:-
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
    implementation files('libs/TypeSafeMapMap-shadow.jar')
...
}
...

task downloadFile(type: Download) {
    src 'https://github.com/dave99galloway/TypeSafeMapMap/releases/download/0.1.11/TypeSafeMapMap-shadow.jar'
    dest 'libs'// buildDir
    onlyIfModified  true
}

compileKotlin {
    dependsOn(downloadFile)
}
```

create the dir 'libs' in the root of your project if that's where you want this lib to be downloaded to. 
You probably want to git ignore jar files from this dir, but add a text file and commit it so that the directory exists at build time. 
For an easier life you can use the build dir instead, but then you lose the benefit of setting onlyIfModified to true 