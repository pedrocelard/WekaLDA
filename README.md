# WekaLDA

WekaLDA is a custom Weka build including a plugin for representing documents based on LDA (Latent Dirichlet Allocation)
LDA assumes that each document deals with a set of predefined topics, which are distributions over an entire vocabulary. 
Our main objective is to use the probability of a document belonging to each topic to implement a new text representation model. 
This proposed technique is implemented as an extension of the Weka software as a new filter.

## System Requirements

    A Java 1.8.0 Virtual Machine. The Java JRE / JDK can be downloaded from Sun's site.
    Apache Ant software for building the system or any IDE that supports Ant build files: Eclipse, IntelliJ IDEA, etc.


## IDE Installation

Download the source code and import it into the IDE you usually use.

This project uses Apache ANT, a Java-based build tool. To build the project you only need to invoke the ANT file called build.xml

``` build line ant [-f <build-file>] [<target>]
ant -f build.xml 
```

## Jar usage

```command line
java -jar -Xmx5000m .\LDA.jar
```

## GUI plugin installation
If you prefer to install the plugin separately in an existing Weka build, you can do it through the GUI provided by the tool.

```GUI installation
 - 
 -
```
