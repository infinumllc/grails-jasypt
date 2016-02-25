grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
grails.project.work.dir = "target/work"
grails.project.target.level = 1.7
grails.project.source.level = 1.7

grails.project.fork = [
    // configure settings for compilation JVM, note that if you alter the Groovy version forked compilation is required
    //  compile: [maxMemory: 256, minMemory: 64, debug: false, maxPerm: 256, daemon:true],

    // configure settings for the test-app JVM, uses the daemon by default
    test: [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256, daemon:true],
    // configure settings for the run-app JVM
    run: [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256, forkReserve:false],
    // configure settings for the run-war JVM
    war: [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256, forkReserve:false],
    // configure settings for the Console UI JVM
    console: [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256]
]

grails.project.dependency.resolver = "maven" // or ivy
grails.project.dependency.resolution = {
    // inherit Grails' default dependencies
    inherits("global") {
        // specify dependency exclusions here; for example, uncomment this to disable ehcache:
        // excludes 'ehcache'
    }
    log "warn" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
    //checksums true // Whether to verify checksums on resolve
    //legacyResolve false // whether to do a secondary resolve on plugin installation, not advised and here for backwards compatibility

    repositories {
        mavenLocal()
        mavenRepo(id:"avisoapp", url:"https://build.avisoapp.com/artifactory/libs-release")
    }

    dependencies {
        compile 'org.jasypt:jasypt:1.9.2',
                'org.jasypt:jasypt-spring31:1.9.2',
                'org.jasypt:jasypt-hibernate4:1.9.2',
                'org.bouncycastle:bcprov-jdk15on:1.51'  // the bcprov-jdk15on package should work for jdk versions 1.5 and up
        
    }

    plugins {
        build(':release:3.1.1', ':rest-client-builder:2.1.1') {
            export = false
        }

        runtime(":hibernate4:4.3.10"){
            export = false
        }
        
        build(":codenarc:0.22"){
            exclude "junit"
        }
    }
}

codenarc.ruleSetFiles="file:grails-app/conf/GrailsJasyptEncryptionCodeNarcRules.groovy"
codenarc.processTestUnit=false
codenarc.processTestIntegration=false
codenarc.reports = {
    xmlReport('xml') {
        outputFile = 'target/CodeNarc-Report.xml'
    }
    htmlReport('html') {
        outputFile = 'target/CodeNarc-Report.html'
    }
}
