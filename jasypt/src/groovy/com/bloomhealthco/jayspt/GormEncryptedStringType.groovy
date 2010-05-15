package com.bloomhealthco.jayspt

import org.jasypt.hibernate.type.AbstractEncryptedAsStringType
import org.jasypt.hibernate.type.ParameterNaming
import org.codehaus.groovy.grails.commons.ConfigurationHolder

class GormEncryptedStringType extends AbstractEncryptedAsStringType {
    void setParameterValues(Properties parameters) {
        def params = config + (parameters ?: [:]) as Properties
        super.setParameterValues(params)
    }

    /**
     *  you can define an encryptor in your grails-app/conf/spring/resources.groovy file that
     *  contains an encryptor with a default name of 'gormEncryptor'
     *  ex:
     *  beans = {
     *     hibernateStringEncryptor(org.jasypt.hibernate.encryptor.HibernatePBEStringEncryptor) {
     *         registeredName = "gormEncryptor"
     *         algorithm = "PBEWithMD5AndTripleDES"
     *         password = "s3kr1t"
     *         keyObtentionIterations = 1000
     *     }
     *  }
     *
     *  or bouncy castle AES:
     *
     *  beans = {
     *     hibernateStringEncryptor(org.jasypt.hibernate.encryptor.HibernatePBEStringEncryptor) {
     *         registeredName = "gormEncryptor"
     *         providerName = "BC"
     *         algorithm = "PBEWITHSHA256AND128BITAES-CBC-BC"
     *         password = "s3kr1t"
     *         keyObtentionIterations = 1000
     *     }
     *  }
     *
     * @return a default config that expects an encryptor name of gormEncryptor
     */
    def getConfig() {
        def config = [:] + jaysptConfig
        if (
            !config[ParameterNaming.ALGORITHM] &&
            !config[ParameterNaming.PASSWORD] &&
            !config[ParameterNaming.KEY_OBTENTION_ITERATIONS] &&
            !config[ParameterNaming.ENCRYPTOR_NAME]
        ) {
           config[ParameterNaming.ENCRYPTOR_NAME] = 'gormEncryptor'
        }

        return config
    }

    /**
     * You can create a jasypt stanza in your grails-app/conf/Config.groovy (or another config file that
     * Config.groovy pulls in).  This stanza can either override the default encryptor name and
     * set a new encryptorRegisteredName that you define in your Spring resources.groovy file, ex:
     *
     * jayspt {
     *     encryptorRegisteredName = "fooBar"
     * }
     *
     * otherwise, you can actually configure the encryptor right there using the other properties
     * available in org.jasypt.hibernate.type.ParameterNaming, ex with triple-DES:
     *
     * jayspt {
     *     algorithm = "PBEWithMD5AndTripleDES"
     *     password = "s3kr1t"
     *     keyObtentionIterations = 1000
     * }
     *
     * or Bouncy Castle AES:
     * 
     * jayspt {
     *     algorithm = "PBEWITHSHA256AND128BITAES-CBC-BC"
     *     providerName = "BC"
     *     password = "s3kr1t"
     *     keyObtentionIterations = 1000
     * }
     *
     * @return the jayspt config specified in Config.groovy
     */
    def getJaysptConfig() {
        return ConfigurationHolder.config?.jayspt ?: [:] 
    }

    protected Object convertToObject(String stringValue) { stringValue }

    Class returnedClass() { String }
}