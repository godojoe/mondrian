//import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
plugins {
    id("java")
    id("java-library")
    //id("com.github.johnrengelman.shadow") version "8.1.1"
}


java.sourceCompatibility = JavaVersion.VERSION_17
java.targetCompatibility = JavaVersion.VERSION_17


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
        into(layout.buildDirectory.dir("classes/java/main"))
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
/*
tasks.named<ShadowJar>("shadowJar") {
    relocate("com.github.jhoenicke.javacup", "java_cup")
}
*/

tasks.getByName("build") {
    //dependsOn("shadowJar")
}

tasks.withType<Test>() {
    dependsOn(tasks.getByName("javacup-test"))
}

tasks.getByName("clean") {
    dependsOn(tasks.getByName("javacup-clean"))
    delete("sym.java")
    delete("parser.java")
    delete("java/")
}