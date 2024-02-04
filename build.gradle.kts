tasks.wrapper {
    gradleVersion = "8.5"
}

subprojects {
    repositories {
        mavenCentral()
        maven {
            url = uri("https://jaspersoft.jfrog.io/artifactory/jrs-ce-addons-releases")
            url = uri("https://repo.maven.apache.org/maven2/")
        }
    }
}
