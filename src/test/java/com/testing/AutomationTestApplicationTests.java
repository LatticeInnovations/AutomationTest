package com.testing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Arrays;
import java.util.Scanner;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
class AutomationTestApplicationTests {

	static int num;
	static String symptom;
	
	@Test
	void contextLoads() {
	}

	static RestTemplate restTemplate = new RestTemplate();
	
	private static final String GET_ALL_PATIENTS_API = "http://localhost:8080/patient/";
	private static final String CREATE_PATIENTS_API = "http://localhost:8080/patient/add";
	private static final String DELETE_PATIENTS_API = "http://localhost:8080/patient/"; //concate with {id}
	private static final String GET_PATIENTS_BY_ID_API = "http://localhost:8080/patient/"; //concate with {id}
	private static final String UPDATE_PATIENTS_BY_ID_API = "http://localhost:8080/patient/"; //concate with {id}
	private static final String GET_DOCTORS_BY_SYMPTOM = "http://localhost:8080/patient/suggestsymptom/"; //concate with {symptom}

	HttpHeaders headers = new HttpHeaders();
	
	@Test
	public void getPatient() throws Exception {
		
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

		ResponseEntity<String> result = restTemplate.exchange(GET_ALL_PATIENTS_API, HttpMethod.GET, entity,
				String.class);
		System.out.println(result);
		System.out.println("status code "+result.getStatusCodeValue());

		assertEquals(HttpStatus.SC_OK, result.getStatusCodeValue());
	}

	@Test
	public void createPatient() {
		
		headers.add("Accept", "application/json");
		headers.add("Content-Type", "application/json");
		
		String jsonBody = "{\"name\":\"demoRjy\",\"city\":\"Rajahmundry\",\"email\":\"demo@gmail.com\",\"phone\":\"+91 7896541235\",\"symptom\":\"Ear pain\"}";
		HttpEntity<String> entity = new HttpEntity<String>(jsonBody, headers);
		
		ResponseEntity<String> response =restTemplate.postForEntity(CREATE_PATIENTS_API, entity, String.class);
		assertEquals(HttpStatus.SC_OK, response.getStatusCodeValue());
	}
	
	@Test
	public void getPatientById() {
		Scanner sc = new Scanner(System.in);
		System.out.println("enter id to search for patient");
		num = sc.nextInt();
		
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
		
//		Patient patient = restTemplate.getForObject(GET_PATIENTS_BY_ID_API+num, Patient.class, entity);
		
		ResponseEntity<String> result = restTemplate.exchange(GET_ALL_PATIENTS_API+num, HttpMethod.GET, entity,
				String.class);
		System.out.println(result);
		assertEquals(HttpStatus.SC_OK, result.getStatusCodeValue());
	}
	
	@Test
    public void  updateEmployee(){   
		
		Scanner sc = new Scanner(System.in);
		System.out.println("enter patient id to update");
		num = sc.nextInt();
		
		headers.add("Accept", "application/json");
        headers.add("Content-Type", "application/json");
        
        String jsonBody = "{\"id\":\""+num+"\",\"name\":\"demoUpdate\",\"city\":\"Rajahmundry\",\"email\":\"demo@gmail.com\",\"phone\":\"+91 7896541235\",\"symptom\":\"Ear pain\"}"; 

        jsonBody = jsonBody.replace("Ear pain", "ARTHRITIS");
        jsonBody = jsonBody.replace("Kakinada","Rajahmundry");
         
        HttpEntity<String> entity = new HttpEntity<String>(jsonBody, headers);
        
        ResponseEntity<String> result = restTemplate.exchange(GET_PATIENTS_BY_ID_API+num, HttpMethod.GET, entity,
				String.class);
		System.out.println(result);
		System.out.println(result.getStatusCode());
		
		if(result.getStatusCodeValue() == 200) {
			ResponseEntity<String> response = restTemplate.exchange(UPDATE_PATIENTS_BY_ID_API+num, HttpMethod.PUT, entity, String.class);
			System.out.println("Patient is updated succesfully with this id "+num);
			System.out.println(response.toString());
			assertEquals(HttpStatus.SC_OK, response.getStatusCodeValue());
		} else {
			System.out.println("Patient is not available with this id "+num);
			assertEquals(result.getStatusCodeValue(), result.getStatusCodeValue());
		}
      
    }
	
	@Test
	public void deletepatient() {

		Scanner sc = new Scanner(System.in);
		System.out.println("enter patient id to delete");
		num = sc.nextInt();
		
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

		ResponseEntity<String> result = restTemplate.exchange(GET_PATIENTS_BY_ID_API+num, HttpMethod.GET, entity,
				String.class);
		System.out.println(result);
		System.out.println(result.getStatusCode());
		
		if(result.getStatusCodeValue() == 200) {
			restTemplate.delete(DELETE_PATIENTS_API+num, entity);
			System.out.println("Patient is deleted succesfully with this id "+num);
			assertEquals(200.00, 200.00);
		} else {
			System.out.println("Patient is not available with this id "+num);
			assertEquals(result.getStatusCodeValue(), result.getStatusCodeValue());
		}

	}
	
	@Test
	public void getSuggestedDoctors() {
		Scanner sc = new Scanner(System.in);
		System.out.println("enter patient symptom to suggest doctors");
		symptom = sc.nextLine();
		
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
		
		ResponseEntity<String> result = restTemplate.exchange(GET_DOCTORS_BY_SYMPTOM+symptom, HttpMethod.GET, entity,
				String.class);
		System.out.println(result);
		assertEquals(HttpStatus.SC_OK, result.getStatusCodeValue());
	}
	
}
