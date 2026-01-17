import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.jlleitschuh.gradle.ktlint.KtlintExtension

/**
 * Convention plugin for configuring ktlint with Twitter Compose Rules
 */
class KtlintConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            val libs = extensions
                .getByType<VersionCatalogsExtension>()
                .named("libs")

            // Apply ktlint plugin
            pluginManager.apply("org.jlleitschuh.gradle.ktlint")

            dependencies {
                add("ktlintRuleset", libs.findLibrary("twitter-compose-rules").get())
            }

            // Configure ktlint
            extensions.configure<KtlintExtension> {
                version.set("1.4.1") // ktlint version compatible with compose-rules 0.4.25
                android.set(true)
                ignoreFailures.set(false)

                // Optionally set reporters
                reporters {
                    reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.PLAIN)
                    reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.CHECKSTYLE)
                    reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.HTML)
                }

                // Use .editorconfig for configuration
                filter {
                    exclude("**/generated/**")
                    exclude("**/build/**")
                }
            }
        }
    }
}
