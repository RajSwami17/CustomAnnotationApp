	package com.custom.model;
	
	import com.custom.annotation.Encrypt;
	
	import lombok.AllArgsConstructor;
	import lombok.Data;
	import lombok.NoArgsConstructor;
	
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public class StudentInfo 
	{
		
		private String id;
		
		@Encrypt
		private String firstName;
		
		@Encrypt
		private String lastName;
		
		
		private String dob;
		
		
		private String address;
		
		@Encrypt
		private String phoneNumber;
		
		@Encrypt
		private String email;
		
	}
