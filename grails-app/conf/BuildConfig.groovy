grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
grails.project.work.dir = "target/work"
grails.project.target.level = 1.7
grails.project.source.level = 1.7
//grails.project.war.file = "target/${appName}-${appVersion}.war"
grails.project.dependency.resolution = {
    // inherit Grails' default dependencies
    inherits("global") {
        // specify dependency exclusions here; for example, uncomment this to disable ehcache:
        // excludes 'ehcache'
    }
    log "warn" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
    checksums true // Whether to verify checksums on resolve
    legacyResolve false // whether to do a secondary resolve on plugin installation, not advised and here for backwards compatibility

    repositories {
        grailsPlugins()
        grailsHome()
        mavenLocal()
        grailsCentral()
        mavenCentral()
        // uncomment these (or add new ones) to enable remote dependency resolution from public Maven repositories
        //mavenRepo "http://repository.codehaus.org"
        //mavenRepo "http://download.java.net/maven/2/"
        //mavenRepo "http://repository.jboss.com/maven2/"
    }

    dependencies {
        compile 'org.jasypt:jasypt:1.9.2',
                'org.jasypt:jasypt-spring31:1.9.2',
                'org.jasypt:jasypt-hibernate3:1.9.2',
                'org.bouncycastle:bcprov-jdk15on:1.51'  // the bcprov-jdk15on package should work for jdk versions 1.5 and up
    }

    plugins {
        build(':release:3.0.1', ':rest-client-builder:2.0.1') {
            export = false
        }

        runtime(":hibernate:3.6.10.16"){ // or ":hibernate4:4.3.5.4"
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
