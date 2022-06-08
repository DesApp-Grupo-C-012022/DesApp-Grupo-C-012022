import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.6.6"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	id("org.sonarqube") version "3.3"
	id("jacoco")
	war
	kotlin("jvm") version "1.6.10"
	kotlin("plugin.spring") version "1.6.10"
	kotlin("plugin.jpa") version "1.6.10"
}

group = "ar.edu.unq.desapp.grupo-C-012022"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.springdoc:springdoc-openapi-ui:1.6.7")
	implementation("org.postgresql:postgresql:42.3.4")
	implementation("org.apache.tomcat:tomcat-jdbc:10.1.0-M14")
	implementation("joda-time:joda-time:2.10.14")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	runtimeOnly("com.h2database:h2")
	runtimeOnly("org.springdoc:springdoc-openapi-kotlin:1.6.7")
	providedRuntime("org.springframework.boot:spring-boot-starter-tomcat")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.security:spring-security-test")
	testImplementation("com.google.code.gson:gson:2.9.0")
}

sonarqube {
	properties {
		property("sonar.projectKey", "jcvincenti_DesApp-Grupo-C-012022")
		property("sonar.organization", "ggoffredo-jcvincenti")
		property("sonar.host.url", "https://sonarcloud.io")
		property("sonar.coverage.jacoco.xmlReportPaths", "build/reports/jacoco/test/jacocoTestReport.xml")
		property("sonar.exclusions", "**/apis/**")
//		property("sonar.exclusions", "**/apis/**, **/jobs/**")
	}
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.jacocoTestReport {
	dependsOn(tasks.test)
	reports {
		xml.required.set(true)
	}
	classDirectories.setFrom(files(classDirectories.files.map {
		fileTree(it) {
			exclude("**/apis/**", "**/jobs/**")
		}
	}))
}

springBoot {
	mainClass.set("ar.edu.unq.desapp.grupoC012022.backenddesappapi.CriptoP2PApplicationKt")
}
