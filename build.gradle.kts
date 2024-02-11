tasks.wrapper {
    gradleVersion = "8.5"
}

subprojects {
    repositories {
        mavenLocal()
        mavenCentral()
        maven {
            url = uri("https://repo.maven.apache.org/maven2/")
        }
    }
}
