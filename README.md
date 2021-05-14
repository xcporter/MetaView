# MetaView

A gradle plugin based on ANTLR to generate UML diagrams from kotlin source  code.

## Install

```groovy
plugins {
    id "com.xcporter.metaview" version "0.0.4"
}
```

## Configure

```groovy

generateUml {
//    Add a closure for each chart you'd like to create
    classTree {}
    functionTree {}

//    Each chart can be customized
    classTree {
        //    folder to analyse
        target: File // default: project directory
        
        outputDir: File // default: build directory + docs
        outputFile: String // output file name

        style: List<String> // add valid plantuml skinparams

        // Parent classes/interfaces you'd like to ignore
        // Name with no parenthesis
        ignoreDelegates: List<String>
        
    // Split parameterized types into children when inherited from
    //      useful for mapping projects using kotlin react wrappers      
        splitDelegates: List<String>
    }
    
    functionTree {
        //    folder to analyse
        target: File // default: project directory

        outputDir: File // default: build directory + docs
        outputFile: String // output file name

        style: List<String> // add valid plantuml skinparams
    }
}

```

### Examples

```kotlin
//  Minimum default
generateUml {
    classTree {}
}
```

```kotlin
//    Select only one submodule from multiplatform project
generateUml {
    classTree {
        outputFile = "common.md"
        target = file(projectDir.path + "/src/commonMain")
    }
    functionTree {
        target = file(projectDir.path + "/src/commonMain")
    }
}
```

```kotlin
//    Ignore Generic parents in a Kotlin React project
generateUml {
    classTree {
        splitDelegates = ["RComponent"]
        ignoreDelegates = ["RProps", "RState"]
        style = ["skinparam BackgroundColor LightBlue"]
    }
}
```

## Generate Uml

Once you've added some chart closures in your build.gradle file,
a task called `generateUmlDiagrams` will be added to your gradle project under the documentation group. 

Charts will be saved in your project's build directory by default, in a folder called
'docs'.



## TODO
- [X] Delegation ignore
- [X] split type arguments consistently
- [X] functional hierarchies
- [X] convert to gradle plugin
- [X] style input
- [ ] ignore function by kind
- [ ] global properties

### Sample generated diagram:

![example](./Examples/example1.png)