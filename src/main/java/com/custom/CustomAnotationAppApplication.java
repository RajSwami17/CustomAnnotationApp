package com.custom;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.custom.helper.EncryptionDecryptionHelper;
import com.custom.model.StudentInfo;

@SpringBootApplication
public class CustomAnotationAppApplication implements CommandLineRunner
{
	
	public static void main(String[] args) {
		SpringApplication.run(CustomAnotationAppApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception 
	{
		StudentInfo studentInfo = new StudentInfo();

        studentInfo.setId("1");
        studentInfo.setFirstName("Rajesh");
        studentInfo.setLastName("Swami");
        studentInfo.setDob("17-08-1997");
        studentInfo.setPhoneNumber("9763474387");
        studentInfo.setEmail("swamirajesh43@gmail.com");
        studentInfo.setAddress("KarveNagar Pune-411052");

        // Encrypted Data
		EncryptionDecryptionHelper.encrypt(studentInfo);
		System.out.println("Encrypted Data: " + studentInfo.toString());

		// Decrypted Data
		EncryptionDecryptionHelper.decrypt(studentInfo);
		System.out.println("Decrypted Data: " + studentInfo.toString());
	}

}
