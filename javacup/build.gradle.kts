plugins {
    id("java")
    id("java-library")
}


java.sourceCompatibility = JavaVersion.VERSION_17
java.targetCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

dependencies {
    compileOnly("org.apache.ant:ant:1.10.11")
}

ant.importBuild("build.xml")
tasks {
    register("copyGradleDependenciesInAntFolder", Copy::class) {
        from(sourceSets["main"].compileClasspath)
        into(layout.projectDirectory.dir("lib"))
    }

    register("copyAntBuildOutput", Copy::class) {
        dependsOn(getByName("compile"))
        from(layout.projectDirectory.dir("classes"))
        into(layout.buildDirectory.dir("classes"))
    }

}

tasks.getByName("compile") {
    dependsOn(tasks.getByName("copyGradleDependenciesInAntFolder"))
}

tasks.withType<JavaCompile>() {
    dependsOn(tasks.getByName("compile"))
}

tasks.withType<Jar> {
    dependsOn(tasks.getByName("copyAntBuildOutput"))
}

tasks.withType<Test>() {
    dependsOn(tasks.getByName("javacup-test"))
}

tasks.getByName("clean") {
    dependsOn(tasks.getByName("javacup-clean"))
    delete("sym.java")
    delete("parser.java")
}