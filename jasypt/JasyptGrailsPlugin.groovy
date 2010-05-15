import java.security.Security
import org.bouncycastle.jce.provider.BouncyCastleProvider

class JasyptGrailsPlugin {

    static {
        // adds the BouncyCastle provider to Java so we don't need to manually modify our java install
        // be sure that you've installed the Java Cryptography Extension (JCE) on the Sun website
        // so that you have "unlimited" (rather than "strong", which isn't really strong) encryption
        // if you're on OSX, this should be there by default.  On other platforms, you'll need to
        // update the jars in your $JAVA_HOME/lib/security with the updated JCE jars
        Security.addProvider(new BouncyCastleProvider());
    }
    
    def version = "0.1"

    def grailsVersion = "1.2.1 > *"

    def dependsOn = [core: "1.2.1 > *", converters: "1.2.1 > *"]

    def pluginExcludes = [
		'grails-app/controllers/**',
		'grails-app/domain/**',
		'grails-app/views/**',
		'grails-app/i18n/**',
		'web-app/**'
	]

    def author = "Ted Naleid"

    def authorEmail = "tnaleid@bloomhealthco.com"

    def title = "Grails Jasypt Plugin - easy encryption"

    def description = '''\\
Grails integration with Jasypt, allows easy encryption of information, including Hibernate/GORM integration.
'''

    def documentation = "http://grails.org/plugin/jasypt"
}
