package com.bloomhealthco.domain

import com.bloomhealthco.jasypt.GormEncryptedStringType

class Patient {
	String firstName
	String lastName
    String correlationId

    static constraints = {
        firstName maxSize: 384
    }

	static mapping = {
    	firstName type: GormEncryptedStringType
        lastName type: GormEncryptedStringType
    }
}
