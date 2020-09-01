# WekaLDA

WekaLDA is a custom Weka build including a plugin for representing documents based on LDA (Latent Dirichlet Allocation)
LDA assumes that each document deals with a set of predefined topics, which are distributions over an entire vocabulary. 
Our main objective is to use the probability of a document belonging to each topic to implement a new text representation model. 
This proposed technique is implemented as an extension of the Weka software as a new filter.

## System Requirements

- A Java 1.8.0 Virtual Machine. The Java JRE / JDK can be downloaded from Sun's site.

- Apache Ant software for building the system or any IDE that supports Ant build files: Eclipse, IntelliJ IDEA, etc.


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

 - The first step is to access the top menu of the initial weka window and select the package manager (Tools> Package Manager)
 
 <img src="https://github.com/pedrocelard/WekaLDA/blob/master/UssageImgs/7_accessPackagemanager.jpg?raw=true" width="300">
 
 - The second step is to select the .jar package from our filesystem through the File/URL button.
 
 <img src="https://github.com/pedrocelard/WekaLDA/blob/master/UssageImgs/8_loadfile.JPG?raw=true" width="500">
 
 - Once installed the plugin will appear in the list below.


## Plugin ussage

To execute this project there are several possibilities. The first is to import the project where the source code is located in some IDE with support for Java projects and ANT, compile and run the project. The second possibility is to execute the file with a JAR extension with a command directly from a console, and finally it can be executed by double clicking directly on the executable file with an EXE extension.

To execute LDA_WEKA.jar it is necessary to open a command console and go to the same location as the file. Once this is done, it is simply necessary to execute the following command: java -jar -Xmx5000m .\LDA_WEKA.jar

The first screen that loads is Weka's own view selector, where the Explorer view will be selected by clicking directly on the indicated button.

<img src="https://github.com/pedrocelard/WekaLDA/blob/master/UssageImgs/1_openExplorer.png?raw=true" width="400">

The view where you will work with the filter will then open. To select the original corpora with which to work, click on "Open file" and select a file in valid .arff format that contains the original text corpus.

<img src="https://github.com/pedrocelard/WekaLDA/blob/master/UssageImgs/2_selectDataset.png?raw=true" width="500">

The next step is to select the filter to be applied to the original texts, in this case it is called “LDA”. To use it, click on the “Choose filter” button and navigate through the drop-down menu to the sections WEKA > FILTERS > UNSUPERVISED > ATTRIBUTE > LDA.

<img src="https://github.com/pedrocelard/WekaLDA/blob/master/UssageImgs/3_selectLDAFilter.png?raw=true" width="500">

The area where the filter options will appear can be accessed using the left mouse button and a new window will open where you can edit each of the attributes of this filter, number of topics, iterations, alpha and beta values, etc.

<img src="https://github.com/pedrocelard/WekaLDA/blob/master/UssageImgs/4_selectOptions.png?raw=true" width="500">

When you have selected the corpus and the filter, all you have to do is press the “Apply” button and Weka will begin its execution. You can recognize that the documents are being processed because the kiwi bird icon that appears in the lower right corner is lifted and changes its orientation from right to left until the execution is complete and it is seated again. When its execution ends, you can see how the number of attributes changes to equal the number of topics indicated. In each of the attributes the probability of each of the documents of belonging to that topic is stored.

<img src="https://github.com/pedrocelard/WekaLDA/blob/master/UssageImgs/5_getResults.png?raw=true" width="500">

As soon as the results of having applied the filter are obtained, a classifier can be applied to it as is done in the normal way in Weka. Below you can see a brief example of the result of applying an SVM classifier.

<img src="https://github.com/pedrocelard/WekaLDA/blob/master/UssageImgs/6_applyClassifier.png?raw=true" width="500">
