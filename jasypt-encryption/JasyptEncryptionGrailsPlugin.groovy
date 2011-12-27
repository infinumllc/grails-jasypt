class JasyptEncryptionGrailsPlugin {
    def version = "1.0.0"

    def grailsVersion = "2.0.0 > *"

    def dependsOn = [:]

    def pluginExcludes = [
            "grails-app/views/error.gsp"
    ]

    def author = "Ted Naleid"
    def authorEmail = "contact@naleid.com"
    def title = "Plugin summary/headline"
    def description = '''\\
Grails integration with Jasypt, allows easy encryption of information, including Hibernate/GORM integration.
'''

    def developers = [
            [ name: "Ted Naleid" ],
            [ name: "Jon Palmer" ]
    ]

    def documentation = "http://grails.org/plugin/jasypt-encryption"

    def issueManagement = [ system: 'bitbucket', url: 'https://bitbucket.org/tednaleid/grails-jasypt/issues' ]

    def doWithWebDescriptor = { xml ->

    }

    def doWithSpring = {

    }

    def doWithDynamicMethods = { ctx ->

    }

    def doWithApplicationContext = { applicationContext ->

    }

    def onChange = { event ->

    }

    def onConfigChange = { event ->

    }
}
