package com.bloomhealthco.domain

import grails.test.*
import groovy.sql.Sql

class JasyptDomainEncryptionTests extends GrailsUnitTestCase {
    def dataSource

    def CORRELATION_ID = "ABC123"

    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testTwoWayStringEncryption() {
		def originalPatient = new Patient(firstName: "foo", lastName: "bar", correlationId: CORRELATION_ID)
		originalPatient.save(failOnError: "true")
		
        withPatientForCorrelationId(CORRELATION_ID) { patient, rawPatient ->
            assertEquals "foo", patient.firstName
            assertEquals "bar", patient.lastName
            assertTrue "foo" != rawPatient.FIRST_NAME
            assertTrue "bar" != rawPatient.LAST_NAME
            assertTrue rawPatient.FIRST_NAME.endsWith("=")
            assertTrue rawPatient.LAST_NAME.endsWith("=")
        }
    }

    void testSaltingEncryptsSameValueDifferentlyEachTime() {
        def originalPatient = new Patient(firstName: "foo", lastName: "foo", correlationId: CORRELATION_ID)
		originalPatient.save(failOnError: "true")

        withPatientForCorrelationId(CORRELATION_ID) { patient, rawPatient ->
            assertEquals "foo", patient.firstName
            assertEquals "foo", patient.lastName
            assertTrue "foo" != rawPatient.FIRST_NAME
            assertTrue "foo" != rawPatient.LAST_NAME
            assertTrue rawPatient.FIRST_NAME != rawPatient.LAST_NAME
        }
    }

    void testEncryptionWithLongNamesFit() {
        def LONG_NAME_256 = "ABCDEFGHIJKLMNOPABCDEFGHIJKLMNOPABCDEFGHIJKLMNOPABCDEFGHIJKLMNOPABCDEFGHIJKLMNOPABCDEFGHIJKLMNOPABCDEFGHIJKLMNOPABCDEFGHIJKLMNOPABCDEFGHIJKLMNOPABCDEFGHIJKLMNOPABCDEFGHIJKLMNOPABCDEFGHIJKLMNOPABCDEFGHIJKLMNOPABCDEFGHIJKLMNOPABCDEFGHIJKLMNOPABCDEFGHIJKLMNOP"

        (1..256).each { val ->
            def firstName = LONG_NAME_256.substring(0, val)
            Patient.build(firstName: firstName, correlationId: val)
            
            withPatientForCorrelationId(val) { patient, rawPatient ->
                assertNotNull patient
                assertEquals firstName, patient.firstName
                // Bouncy Castle AES block encryption encrypts 256 character string in 384 characters
                assertTrue rawPatient.FIRST_NAME.size() <= 384
            }
        }
    }

    def withPatientForCorrelationId(correlationId, closure) {
        def patient = Patient.findByCorrelationId(correlationId)
        assertNotNull patient
        retrieveRawPatientFromDatabase(correlationId) { rawPatient ->
            assertNotNull rawPatient
            closure(patient, rawPatient)
        }
    }

    def retrieveRawPatientFromDatabase(correlationId, closure) {
        new Sql(dataSource).with { db ->
            try {
                def result = db.firstRow("SELECT * FROM patient where correlation_id = $correlationId")
                closure(result)
            } finally {
                db.close()
            }
        }
    }
}
