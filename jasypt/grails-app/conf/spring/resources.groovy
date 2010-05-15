beans = {
	hibernateStringEncryptor(org.jasypt.hibernate.encryptor.HibernatePBEStringEncryptor) {
		registeredName = "strongHibernateStringEncryptor"
		algorithm = "PBEWithMD5AndTripleDES"
		password = "jasypt"
	}
}