mahjong
=======

Simple java project for using in tutorials.

Project shows the interactive mahjong field where the player can remove matching tiles.

## Requirements

The project was initially build with Java 7. 
At the time of writing it is still the case, but the releases after the 0.2.1 won't work with old java versions. A target version for the project is a current LTS - java 17.

## Running

Build the project using maven:

```shell
mvnw clean package
```

Run the desktop client of the game:

```shell
java -jar desktop-client/target/desktop-client-*-with-dependencies.jar
```

## Contributing

You should probably find something more interesting than a project abandonned for 10 years :)

## Commit convention

Use conventional commits and ideas expressed in the [article](https://medium.com/neudesic-innovation/conventional-commits-a-better-way-78d6785c2e08).

### Notable commit templates

#### Change dependency

```
build(deps): change the dependency
```

#### Release activities

```
chore(release): prepare release versions
```

## Changelog

Make sure you prepared a changelog before merging into the main branch.
Use the included maven plugin to do this.

```shell
mvn git-changelog-maven-plugin:git-changelog
```
 
## Release

The project should follow semver for the api module.

Tag your releases with the version prefixed via `v`:

```
v0.2.1
```

To start a new release:

- Create a release branch from the main branch (`release/0.2.1`);
- Run maven release plugin to kick off the release process (`mvn release:prepare`);
- Generate and commit a changelog (`mvn git-changelog-maven-plugin:git-changelog`);
- Push and merge your changes;
- Push the release tag;
- Create a GitHub release;
