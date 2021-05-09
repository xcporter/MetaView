# MetaView

A gradle plugin based on ANTLR to generate UML diagrams from kotlin source  code.

## Install

```groovy
plugins {
    //not yet published to gradle plugin portal!
    id 'com.xcporter.metaview' version '0.0.1'
}
```

## Configure

```groovy

generateUml {
//    folder to analyse
    target: File // default: project directory
    
//    Add a closure for each chart you'd like to create
    classTree {}
    functionReceiverTree {}

//    Each chart can be customized
    classTree {
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
}

```

### Example

```kotlin
generateUml {
    classTree {
        outputFile = "classHierarchy.md"
        ignoreDelegates = ["DefaultTask"]
        splitDelegates = ["Plugin"]
        style = ["skinparam BackgroundColor LightBlue"]
    }
}
```

TODO
- [X] Delegation ignore
- [X] split type arguments consistently
- [ ] functional hierarchies
- [X] convert to gradle plugin
- [X] style input

### Sample generated diagram:

![example](./Examples/example1.png)