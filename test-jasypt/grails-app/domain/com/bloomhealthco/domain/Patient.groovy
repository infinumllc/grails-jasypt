package com.bloomhealthco.domain

import com.bloomhealthco.jasypt.GormEncryptedStringType

class Patient {
	String firstName
	String lastName
    String correlationId

	static mapping = {
    	firstName type: GormEncryptedStringType
        lastName type: GormEncryptedStringType
    }
}
