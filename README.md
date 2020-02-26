# Teaching-HEIGVD-RES-2019-Labo-Java-IO

### About the 2019 version of this lab

We changed the project structure. In previous editions, there was a "test" project (with its own pom.xml), a "production" project (with its own pom.xml) and a top-level "build" project. This is what you will see in the webcasts. This year, we have moved to a single project, with production and test packages.

**Warning: do NOT change the code in the test packages**

### How many branches should I create? How many PRs? When?

For this lab, we will try the following workflow:
* create a branch for the entire lab (you call it "dev", "lab" or whatever you want)
* commit code as soon as possible and push the branch to your fork
* open a PR. In the name of the PR, start with `[WIP] `. This makes it easy for us to see that you are still working on the lab (Work In Progress) 
* even if tests are still red, we have a communication space while you work on the lab
* whenever you push a new commit, we will be able to see it (and possibly to comment it)
* at some point, all your tests will be green: you can then signal that you are done; for that, change the name of your PR. Replace `[WIP] ` with `[TOREVIEW] `.

Push commits as often as possible (whenever you make progress, turn a test green, etc.).

If you want to use private branches, you can do so. But then, don't create individual PRs.

Final note: because you all work in the same packages, we will not merge (more than one) PR at the end of the lab. But we will be able to get your branches and evaluate them.


### About previous versions of this lab

Before reading this documentation and diving into the code, you should watch [this webcast](https://www.youtube.com/watch?v=qgncWAUqcbY&list=PLfKkysTy70QYVU0wz_n14gA07c3PdYm3_&index=2), which shows a **demo** of the application that you will implement. Later on, you might also watch [this webcast](https://www.youtube.com/watch?v=v_ZpVgf0lGc&list=PLfKkysTy70QYVU0wz_n14gA07c3PdYm3_&index=3), which gives you some hints about the **code structure**.

If you watch carefully, you will see that in 2015, we were using a web service called [iheartquotes](http://www.iheartquotes.com/) to fetch dynamic text data. This service is now down, so this year we had to look for [a replacement](http://www.icndb.com/api/). We also had to adapt the code of the starter project a bit. You will see why and how when in the commented source code.

We also fixed an issue with previous versions of the lab. In the past, our specification was not correct when talking about **depth-first traversal**. Our unit tests specified a wrong behaviour. We have fixed them. For these reasons, there will be some differences in the console output, but nothing extraordinary.



![image](./diagrams/chuck.png)


### Introduction

The objective of this lab is to get familiar with the Java IO APIs and to interact with the file system. You will implement an application that performs the following tasks:

1. The user invokes the application on the **command line** and provides a numeric argument (*n*).
2. The application **uses a Web Service client** (which is provided to you) to fetch *n* **quotes** from the [Internet Chuck Norris Database](http://www.icndb.com/api/) online service.
3. The application stores the content of each quote in a **text file** (utf-8), on the local filesystem. It uses the *tags* associated to each quote to create a hierarchical structure of directories.
4. The application then **traverses the local file system** (depth-first) and applies a processing to each visited quote file.
5. The processing consists of 1) converting all characters to their **uppercase** value and 2) adding a **line number** (followed by a tab character) at the beginning of each line. This is achieved by combining sub-classes of the `FilterWriter` class.

If your application is fully implemented you should have the following result on your machine:

#### A. When building the application


```
[INFO] Scanning for projects...
[INFO] 
[INFO] --------------------< ch.heigvd.res.io:lab-java-io >--------------------
[INFO] Building RES Lab Java IO 1.0-SNAPSHOT
[INFO] --------------------------------[ jar ]---------------------------------

... (skipping some of the log output)

[INFO] Running ch.heigvd.res.labio.impl.explorers.DFSFileExplorerTest
[INFO] Tests run: 2, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.15 s - in ch.heigvd.res.labio.impl.explorers.DFSFileExplorerTest
[INFO] 
[INFO] Results:
[INFO] 
[INFO] Tests run: 27, Failures: 0, Errors: 0, Skipped: 0

... (skipping some of the log output)

[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 7.497 s
[INFO] Finished at: 2019-03-04T08:12:55+01:00
[INFO] ------------------------------------------------------------------------
```

#### B. When running the application

```
$ java -jar Lab01App-code/target/Lab01App-code-1.0-SNAPSHOT-launcher.jar 10
INFO: Received a new joke with 4 tags.
INFO: > movie
INFO: > internet
INFO: > geek
INFO: > popular
INFO: Received a new joke with 4 tags.
INFO: > movie
INFO: > funny
INFO: > geek
INFO: > joke
INFO: Received a new joke with 2 tags.
INFO: > hilarious
INFO: > movie
INFO: Received a new joke with 1 tags.
INFO: > geek
INFO: Received a new joke with 7 tags.
INFO: > internet
INFO: > geek
INFO: > movie
INFO: > joke
INFO: > funny
INFO: > hilarious
INFO: > popular
INFO: Received a new joke with 0 tags.
INFO: Received a new joke with 1 tags.
INFO: > popular
INFO: Received a new joke with 0 tags.
INFO: Received a new joke with 1 tags.
INFO: > popular
INFO: Received a new joke with 2 tags.
INFO: > hilarious
INFO: > geek
INFO: ./workspace/quotes
./workspace/quotes/geek
./workspace/quotes/geek/quote-4.utf8
./workspace/quotes/hilarious
./workspace/quotes/hilarious/geek
./workspace/quotes/hilarious/geek/quote-10.utf8
./workspace/quotes/hilarious/movie
./workspace/quotes/hilarious/movie/quote-3.utf8
./workspace/quotes/internet
./workspace/quotes/internet/geek
./workspace/quotes/internet/geek/movie
./workspace/quotes/internet/geek/movie/joke
./workspace/quotes/internet/geek/movie/joke/funny
./workspace/quotes/internet/geek/movie/joke/funny/hilarious
./workspace/quotes/internet/geek/movie/joke/funny/hilarious/popular
./workspace/quotes/internet/geek/movie/joke/funny/hilarious/popular/quote-5.utf8
./workspace/quotes/movie
./workspace/quotes/movie/funny
./workspace/quotes/movie/funny/geek
./workspace/quotes/movie/funny/geek/joke
./workspace/quotes/movie/funny/geek/joke/quote-2.utf8
./workspace/quotes/movie/internet
./workspace/quotes/movie/internet/geek
./workspace/quotes/movie/internet/geek/popular
./workspace/quotes/movie/internet/geek/popular/quote-1.utf8
./workspace/quotes/popular
./workspace/quotes/popular/quote-7.utf8
./workspace/quotes/popular/quote-9.utf8
./workspace/quotes/quote-6.utf8
./workspace/quotes/quote-8.utf8
```

#### C. After running the application

```
$ find ./workspace
./workspace
./workspace/quotes
./workspace/quotes/geek
./workspace/quotes/geek/quote-4.utf8
./workspace/quotes/geek/quote-4.utf8.out
./workspace/quotes/hilarious
./workspace/quotes/hilarious/geek
./workspace/quotes/hilarious/geek/quote-10.utf8
./workspace/quotes/hilarious/geek/quote-10.utf8.out
./workspace/quotes/hilarious/movie
./workspace/quotes/hilarious/movie/quote-3.utf8
./workspace/quotes/hilarious/movie/quote-3.utf8.out
./workspace/quotes/internet
./workspace/quotes/internet/geek
./workspace/quotes/internet/geek/movie
./workspace/quotes/internet/geek/movie/joke
./workspace/quotes/internet/geek/movie/joke/funny
./workspace/quotes/internet/geek/movie/joke/funny/hilarious
./workspace/quotes/internet/geek/movie/joke/funny/hilarious/popular
./workspace/quotes/internet/geek/movie/joke/funny/hilarious/popular/quote-5.utf8
./workspace/quotes/internet/geek/movie/joke/funny/hilarious/popular/quote-5.utf8.out
./workspace/quotes/movie
./workspace/quotes/movie/funny
./workspace/quotes/movie/funny/geek
./workspace/quotes/movie/funny/geek/joke
./workspace/quotes/movie/funny/geek/joke/quote-2.utf8
./workspace/quotes/movie/funny/geek/joke/quote-2.utf8.out
./workspace/quotes/movie/internet
./workspace/quotes/movie/internet/geek
./workspace/quotes/movie/internet/geek/popular
./workspace/quotes/movie/internet/geek/popular/quote-1.utf8
./workspace/quotes/movie/internet/geek/popular/quote-1.utf8.out
./workspace/quotes/popular
./workspace/quotes/popular/quote-7.utf8
./workspace/quotes/popular/quote-7.utf8.out
./workspace/quotes/popular/quote-9.utf8
./workspace/quotes/popular/quote-9.utf8.out
./workspace/quotes/quote-6.utf8
./workspace/quotes/quote-6.utf8.out
./workspace/quotes/quote-8.utf8
./workspace/quotes/quote-8.utf8.out
```

You can then compare `quote-8.utf8.out` with `quote-8.utf8` to see the text received as input (from the web service) and the text produced as output (by your program).


### Tasks

Here is the proposed list of tasks to achieve the objectives:

1. Start by forking and cloning this repo (**mandatory!!**).
2. From the main project, do a `mvn clean install` and notice that the tests fail.
3. Spend some time to explore the package structure (and watch [this](https://www.youtube.com/watch?v=v_ZpVgf0lGc&list=PLfKkysTy70Qb_mfkkqa5OUMqsOPNEYZIa&index=5) Youtube video).
4. Examine the automated tests in the test project and do a step-by-step implementation, until all tests are green. Here is a proposed order for fixing the broken tests:
   - `ApplicationTest.java`
   - `UtilsTest.java`
   - `UpperCaseFilterWriterTest.java`
   - `DFSFileExplorerTest.java`
   - `FileTransformerTest.java`
   - `FileNumberingFilterWriterTest.java`
   - `CompleteFileTransformerTest.java`
5. Each time that you fix a failing test, `commit` your work (and use a **meaningful message**)
6. When all the tests are green, invoke the application (`java -jar`) from the top-level directory (the directory that contains the `.git` hidden folder). Inspect the content of the `workspace/quotes` directory and check that the output files are correct.
7. When you are done, make sure that you have committed all your work and push your commits.
8. To make a final validation, move to a fresh directory. Clone your fork. Do a `mvn clean install` and make sure that the tests are still green and that the app still produces the correct output.
9. Submit a pull request.
