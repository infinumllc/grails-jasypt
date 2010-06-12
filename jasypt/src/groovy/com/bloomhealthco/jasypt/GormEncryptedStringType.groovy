package com.bloomhealthco.jasypt

public class GormEncryptedStringType extends AbstractGormEncryptedStringType {
    
    protected Object convertToObject(String stringValue) { stringValue }

    Class returnedClass() { String }
}