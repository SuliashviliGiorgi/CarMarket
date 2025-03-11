plugins {
	java
	id("org.springframework.boot") version "3.4.1"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "ge.giorgi.example"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	compileOnly("org.projectlombok:lombok:1.18.36")
	annotationProcessor("org.projectlombok:lombok:1.18.36")

	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("com.nimbusds:nimbus-jose-jwt:10.0.1")
	implementation("org.postgresql:postgresql:42.7.4")
	implementation("org.liquibase:liquibase-core:4.30.0")

	// Keep this but remove the exclusion - it might be causing conflicts
	testImplementation("org.springframework.boot:spring-boot-starter-test")

	// Keep Mockito dependencies
	testImplementation("org.mockito:mockito-core:5.11.0")
	testImplementation("org.mockito:mockito-junit-jupiter:5.11.0")

	// Remove these JUnit dependencies - let Spring Boot manage the versions
	// testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.2")
	// testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.10.2")
	// testRuntimeOnly("org.junit.platform:junit-platform-launcher")

	// Remove the force configuration - it's likely causing the version conflict
	// configurations.all {
	//    resolutionStrategy.force("org.junit.jupiter:junit-jupiter-api:5.10.2")
	//    resolutionStrategy.force("org.junit.jupiter:junit-jupiter-engine:5.10.2")
	// }
}

tasks.withType<Test> {
	useJUnitPlatform()
}