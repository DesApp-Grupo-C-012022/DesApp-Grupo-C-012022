package ar.edu.unq.desapp.grupoC012022.backenddesappapi.architecture

import com.tngtech.archunit.core.domain.JavaClasses
import com.tngtech.archunit.core.importer.ClassFileImporter
import com.tngtech.archunit.core.importer.ImportOption
import com.tngtech.archunit.junit.AnalyzeClasses
import com.tngtech.archunit.junit.ArchTest
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses
import org.junit.jupiter.api.Test

@AnalyzeClasses(
    packages = ["ar.edu.unq.desapp.grupoC012022.backenddesappapi"],
    importOptions = [ImportOption.DoNotIncludeJars::class, ImportOption.DoNotIncludeTests::class]
)
class ArchitectureTest {

    @ArchTest
    fun servicesShouldOnlyBeReferencedByControllersTest(javaClasses: JavaClasses) {
        classes()
            .that().resideInAPackage("..services..")
            .should().dependOnClassesThat().resideInAPackage("..controllers..")
            .check(javaClasses)
    }
}