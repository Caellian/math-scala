# Math v2.0.0
[![Download](https://api.bintray.com/packages/caellian/caellian/Math/images/download.svg)](https://bintray.com/caellian/caellian/Math/2.0.0)
[![MIT licensed](https://img.shields.io/badge/license-MIT-blue.svg)](LICENSE.md)
## Old, scala version of [Math](https://github.com/Caellian/Math) library.

### Why did I create this library?
I created this library because I couldn't find a single one I liked written in Java or Scala.
I believe there are currently no libraries out there for Java or languages extending it which support Vectors and Matrices of any dimension and are as simple as this one at the same time.

### License & Documentation
You can find license [here](https://github.com/Caellian/math-scala/blob/master/LICENSE.md).

### Including "Math" in your projects
#### Gradle
Add jCenter to repositories closure in build.gradle:
```Groovy
jcenter()
```
After adding following statement to dependencies closure you are good to go:
```Groovy
compile 'hr.caellian:math:2.0.0'
```

#### Maven
Maven settings:
```xml
<settings xsi:schemaLocation='http://maven.apache.org/SETTINGS/1.0.0 http://maven.apache.org/xsd/settings-1.0.0.xsd'
xmlns='http://maven.apache.org/SETTINGS/1.0.0' xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'>
  <profiles>
  	<profile>
  		<repositories>
  			<repository>
  				<snapshots>
  					<enabled>false</enabled>
  				</snapshots>
  				<id>bintray-caellian-caellian</id>
  				<name>bintray</name>
  				<url>http://dl.bintray.com/caellian/caellian</url>
  			</repository>
  		</repositories>
  		<pluginRepositories>
  			<pluginRepository>
  				<snapshots>
  					<enabled>false</enabled>
  				</snapshots>
  				<id>bintray-caellian-caellian</id>
  				<name>bintray-plugins</name>
  				<url>http://dl.bintray.com/caellian/caellian</url>
  			</pluginRepository>
  		</pluginRepositories>
  		<id>bintray</id>
  	</profile>
  </profiles>
  <activeProfiles>
  	<activeProfile>bintray</activeProfile>
  </activeProfiles>
</settings>
```
Add this dependency:
```xml
<dependency>
	<groupId>hr.caellian</groupId>
	<artifactId>math</artifactId>
	<version>2.0.0</version>
	<type>pom</type>
</dependency>
```

### Contributing
You are free to fork this repository as it's MIT licensed. Contibution to it is welcome and will be pulled if it's useful, but keep in mind that it's probably better for you to spend your time on your own fork instead of updating this dead old repository.
