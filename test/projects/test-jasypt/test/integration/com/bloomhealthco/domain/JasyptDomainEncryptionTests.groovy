package com.bloomhealthco.domain

import groovy.sql.Sql

import org.junit.Test

class JasyptDomainEncryptionTests {
    def dataSource
    def sessionFactory
    def grailsApplication

    def CORRELATION_ID = "ABC123"

	@Test
    void testStringStringEncryption() {
        testPropertyAsStringEncryption('firstName', 'FIRST_NAME', 'foo')
    }

    void testDateStringEncryption() {
        testPropertyAsStringEncryption('birthDate', 'BIRTH_DATE', new Date(1970, 2, 3))
    }

    void testDoubleStringEncryption() {
        testPropertyAsStringEncryption('latitude', 'LATITUDE', 85.0d)
    }

    void testBooleanStringEncryption() {
        testPropertyAsStringEncryption('hasInsurance', 'HAS_INSURANCE', true)
    }

    void testFloatStringEncryption() {
        testPropertyAsStringEncryption('cashBalance', 'CASH_BALANCE', 123.45f)
    }

    void testShortStringEncryption() {
        testPropertyAsStringEncryption('weight', 'WEIGHT', 160)
    }

    void testIntegerStringEncryption() {
        testPropertyAsStringEncryption('height', 'HEIGHT', 74)
    }

    void testLongStringEncryption() {
        testPropertyAsStringEncryption('patientId', 'PATIENT_ID', 1234567890)
    }

    void testByteStringEncryption() {
        testPropertyAsStringEncryption('biteMe', 'BITE_ME', 2)
    }

    void testSaltingEncryptsSameValueDifferentlyEachTime() {
        def originalPatient = new Patient(firstName: "foo", lastName: "foo", correlationId: CORRELATION_ID)
		originalPatient.save(failOnError: true)

        withPatientForCorrelationId(CORRELATION_ID) { patient, rawPatient ->
            assert "foo" == patient.firstName
            assert "foo" == patient.lastName
            assert "foo" != rawPatient.FIRST_NAME
            assert "foo" != rawPatient.LAST_NAME
            assert rawPatient.FIRST_NAME != rawPatient.LAST_NAME
        }
    }

    void testEncryptionWithLongNamesFit() {
        def LONG_NAME_256 = "ABCDEFGHIJKLMNOPABCDEFGHIJKLMNOPABCDEFGHIJKLMNOPABCDEFGHIJKLMNOPABCDEFGHIJKLMNOPABCDEFGHIJKLMNOPABCDEFGHIJKLMNOPABCDEFGHIJKLMNOPABCDEFGHIJKLMNOPABCDEFGHIJKLMNOPABCDEFGHIJKLMNOPABCDEFGHIJKLMNOPABCDEFGHIJKLMNOPABCDEFGHIJKLMNOPABCDEFGHIJKLMNOPABCDEFGHIJKLMNOP"

        (1..256).each { val ->
            def firstName = LONG_NAME_256.substring(0, val)
            Patient.build(firstName: firstName, correlationId: val)
            
            withPatientForCorrelationId(val.toString()) { patient, rawPatient ->
                assert patient
                assert firstName == patient.firstName
                // Bouncy Castle AES block encryption encrypts 256 character string in 384 characters
                assert rawPatient.FIRST_NAME.size() <= 384
            }
        }
    }

    void testPropertyAsStringEncryption(property, rawProperty, value) {
        def originalPatient = new Patient(correlationId: CORRELATION_ID)
        originalPatient."$property" = value
        originalPatient.save([failOnError: true])

        withPatientForCorrelationId(CORRELATION_ID) { patient, rawPatient ->
            assert value == patient."$property"
            def rawPropertyValue = rawPatient."$rawProperty"
            assert value.toString() != rawPropertyValue
            assert rawPropertyValue.endsWith("=")
        }
    }

    def withPatientForCorrelationId(correlationId, closure) {
        def patient = Patient.findByCorrelationId(correlationId)
        assert patient
        retrieveRawPatientFromDatabase(correlationId) { rawPatient ->
            assert rawPatient
            closure(patient, rawPatient)
        }
    }

    def retrieveRawPatientFromDatabase(correlationId, closure) {
        new Sql(dataSource).with { db ->
            try {
                def result = db.firstRow("SELECT * FROM patient where correlation_id = ?", correlationId)
                closure(result)
            } finally {
                db.close()
            }
        }
    }
}
