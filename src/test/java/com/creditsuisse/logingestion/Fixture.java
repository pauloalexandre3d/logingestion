package com.creditsuisse.logingestion;

import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class Fixture {
	
	private static Fixture fixture;
	
	private Fixture(){ }
	
	static Fixture getInstance() {
		System.setProperty("java.io.tmpdir", "/tmp");
		if (fixture == null) {
			fixture = new Fixture();
		}
		return fixture;
	}

    void copyFile() throws IOException {
		InputStream file = new ClassPathResource("logfile.txt").getInputStream();
        Files.copy(file,
				Paths.get(System.getProperty("java.io.tmpdir").concat("/logfile.txt")),
				StandardCopyOption.REPLACE_EXISTING);
    }

}
