package com.bloomhealthco.domain

import com.bloomhealthco.jasypt.GormEncryptedStringType
import com.bloomhealthco.jasypt.GormEncryptedDateAsStringType

class Patient {
	String firstName
	String lastName
    Date birthDate
    String correlationId

    static constraints = {
        firstName maxSize: 384
        birthDate nullable: true
    }

	static mapping = {
    	firstName type: GormEncryptedStringType
        lastName type: GormEncryptedStringType
        birthDate type: GormEncryptedDateAsStringType
    }
}
