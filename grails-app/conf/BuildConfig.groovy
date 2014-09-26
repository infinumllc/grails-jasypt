grails.project.work.dir = 'target'
/*grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"*/
//grails.project.war.file = "target/${appName}-${appVersion}.war"
grails.project.dependency.resolution = {
    // inherit Grails' default dependencies
    inherits("global") {
        // uncomment to disable ehcache
        // excludes 'ehcache'
    }
    log "warn" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
    repositories {
        grailsPlugins()
        grailsHome()
        grailsCentral()

        // uncomment the below to enable remote dependency resolution
        // from public Maven repositories
        mavenLocal()
        mavenCentral()
        //mavenRepo "http://snapshots.repository.codehaus.org"
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
        build(':release:2.2.1', ':rest-client-builder:1.0.2') {
            export = false
        }

        runtime(":hibernate:$grailsVersion"){
            export = false
        }
        
        provided(":codenarc:0.20"){
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
