# GeoConcert

GeoConcert is a geo location based concert app that finds concerts relevant to your position and even your musical preferences. It makes use of Last.fm's API to fetch events and find out what kind of music you like. It makes use of easily available APIs and software to make a powerful and useful application.

## Code style and commit message:

To make the code as tidy and readable as possible all developers should use the agreed upon set of code style and commit rules.

### Code styles (Java):

#### Method signatures

Methods with <= 2 parameters

```java
public void methodName(Class param1, Class param2) {
	// Contents
}
``

Methods with >= 3 parameters

```java
public void methodName(
		Class param1,
		Class param2,
		Class param3,
		Class param4) {
	// Contents
	}
```

#### Javadoc &amp method comments

All public methods should have a Javadoc. The return value and parameter values can have descriptions if necessary.

```java
/**
  * A description of the method and it's
  * use area should be first.

  * @param user
  * @return name - the full name of the user
  */
public String getName(User user) {
	// Contents
}
```

All private methods should have a multiline comment in the same style as a javadoc.

```java
/*
 * A brief description of the method and what
 * it does should come first.
 *
 * @param title
 * @return title in lowercase
 */
public String toLowerCase(String title) {
	// Contents
}
```

### Commits and commit messages

Only commit working code. Your commit does not have to complete new functionality, but the code should compile, run and not affect other parts of the codebase.

Commit messages should have the following format:

Use case: As a user I want to......
Status: Finished/In Progress/Updated

Filename: FetchEventsAsyncTask.java
Changes: I changed this and that. I fixed this and that method and functionality. Dup di du and more stuff here.

Filename: GeoApplication
Changes: I did this and that and this and that. Please explain nicely here.
