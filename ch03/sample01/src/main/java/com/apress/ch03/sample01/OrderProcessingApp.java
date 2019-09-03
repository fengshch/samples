package com.apress.ch03.sample01;

import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.cert.TrustAnchor;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

import java.security.cert.Certificate;
import java.security.cert.X509Certificate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OrderProcessingApp {

	static {
		
		try {
		String path = System.getProperty("user.dir");
		String keystorePath = path + File.separator + "keystore.jks";
		File file = new File(keystorePath);

		KeyStore keystore = KeyStore.getInstance("jks");
		char[] pwdArray = "springboot".toCharArray();
		keystore.load(new FileInputStream(keystorePath), pwdArray);
		
		Set<TrustAnchor> hashSet = new HashSet<>();
		Enumeration<String> aliases = keystore.aliases();
		while (aliases.hasMoreElements()) {
			String alias = aliases.nextElement();
			if (keystore.isCertificateEntry(alias)) {
				Certificate cert = keystore.getCertificate(alias);
				if (cert instanceof X509Certificate) {
					System.out.println("TEST");
					hashSet.add(new TrustAnchor((X509Certificate)cert, null));
				}
					
			}
		}

		if (file.exists()) {
			System.setProperty("javax.net.ssl.trustStore", keystorePath);
			System.setProperty("javax.net.ssl.trustStorePassword", "springboot");
		}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public static void main(String[] args) {
		SpringApplication.run(OrderProcessingApp.class, args);
	}
}
