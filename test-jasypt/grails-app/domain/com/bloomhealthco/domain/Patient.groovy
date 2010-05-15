package com.bloomhealthco.domain

import com.bloomhealthco.jayspt.GormEncryptedStringType

class Patient {
	String firstName
	String lastName
    String correlationId

	static mapping = {
    	firstName type: GormEncryptedStringType
        lastName type: GormEncryptedStringType
    }
}
