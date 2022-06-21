package ar.edu.unq.desapp.grupoC012022.backenddesappapi.architecture

import com.tngtech.archunit.core.domain.JavaClasses
import com.tngtech.archunit.core.importer.ImportOption
import com.tngtech.archunit.junit.AnalyzeClasses
import com.tngtech.archunit.junit.ArchTest
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition.*
import com.tngtech.archunit.library.Architectures.layeredArchitecture
import org.springframework.transaction.annotation.Transactional

@AnalyzeClasses(
    packages = ["ar.edu.unq.desapp.grupoC012022.backenddesappapi"],
    importOptions = [ImportOption.DoNotIncludeJars::class, ImportOption.DoNotIncludeTests::class]
)
class ArchitectureTest {

    @ArchTest
    fun servicesShouldOnlyBeReferencedByControllersTest(javaClasses: JavaClasses) {
        layeredArchitecture()
            .layer("Controller").definedBy("..controllers..")
            .layer("Service").definedBy("..services..")
            .layer("Job").definedBy("..jobs..")
            .layer("Persistence").definedBy("..repositories..")
            .layer("API").definedBy("..apis..")
            .whereLayer("Controller").mayNotBeAccessedByAnyLayer()
            .whereLayer("Service").mayOnlyBeAccessedByLayers("Controller", "Job")
            .whereLayer("API").mayOnlyBeAccessedByLayers("Service")
            .whereLayer("Persistence").mayOnlyBeAccessedByLayers("Service")
            .check(javaClasses)
    }

    @ArchTest
    fun onlyServicesShouldBeAnnotatedWithTransactional(javaClasses: JavaClasses) {
        methods()
            .that().areDeclaredInClassesThat().resideOutsideOfPackage("..services..")
            .should().notBeAnnotatedWith(Transactional::class.java)
            .check(javaClasses)
    }
}