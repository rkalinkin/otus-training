import org.gradle.api.tasks.testing.logging.TestLogEvent

dependencies {
    implementation ("ch.qos.logback:logback-classic")

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation ("org.junit.jupiter:junit-jupiter")
    testImplementation ("org.assertj:assertj-core")
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events = setOf(TestLogEvent.PASSED, TestLogEvent.SKIPPED, TestLogEvent.FAILED)
    }
}
