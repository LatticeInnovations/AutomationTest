package com.testing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Arrays;
import java.util.Scanner;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestClassOrder;
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
	
	/*Patient API end points*/
	
	private static final String GET_ALL_PATIENTS_API = "http://localhost:8080/patient/";
	private static final String CREATE_PATIENTS_API = "http://localhost:8080/patient/add";
	private static final String DELETE_PATIENTS_API = "http://localhost:8080/patient/"; // concate with {id}
	private static final String GET_PATIENTS_BY_ID_API = "http://localhost:8080/patient/"; // concate with {id}
	private static final String UPDATE_PATIENTS_BY_ID_API = "http://localhost:8080/patient/"; // concate with {id}
	private static final String GET_DOCTORS_BY_SYMPTOM = "http://localhost:8080/patient/suggestsymptom/"; // concate																								// with
																											// {symptom}
	/*Doctor API end points*/
	private static final String GET_ALL_DOCTORS_API = "http://localhost:8080/doctor/";
	private static final String CREATE_DOCTORS_API = "http://localhost:8080/doctor/add";
	private static final String DELETE_DOCTORS_API = "http://localhost:8080/doctor/"; // concate with {id}
	private static final String GET_DOCTORS_BY_ID_API = "http://localhost:8080/doctor/"; // concate with {id}
	private static final String UPDATE_DOCTORS_BY_ID_API = "http://localhost:8080/doctor/"; // concate with {id}

	HttpHeaders headers = new HttpHeaders();
	Scanner sc = new Scanner(System.in);
	
	/*Test cases for patient API end points*/

	@Test
	public void getPatient() throws Exception {

		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

		ResponseEntity<String> result = restTemplate.exchange(GET_ALL_PATIENTS_API, HttpMethod.GET, entity,
				String.class);
		System.out.println(result);
		System.out.println("status code " + result.getStatusCodeValue());

		assertEquals(HttpStatus.SC_OK, result.getStatusCodeValue());
	}

	@Test
	public void createPatient() {

		headers.add("Accept", "application/json");
		headers.add("Content-Type", "application/json");

		String jsonBody = "{\"name\":\"Sample\",\"city\":\"Rajahmundry\",\"email\":\"demo@gmail.com\",\"phone\":\"+91 7896541235\",\"symptom\":\"Arthritis\"}";
		HttpEntity<String> entity = new HttpEntity<String>(jsonBody, headers);

		ResponseEntity<String> response = restTemplate.postForEntity(CREATE_PATIENTS_API, entity, String.class);
		assertEquals(HttpStatus.SC_OK, response.getStatusCodeValue());
	}

	@Test
	public void getPatientById() {
		
		System.out.println("enter id to search for patient");
		num = sc.nextInt();

		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

		ResponseEntity<String> result = restTemplate.exchange(GET_ALL_PATIENTS_API + num, HttpMethod.GET, entity,
				String.class);
		System.out.println(result);
		assertEquals(HttpStatus.SC_OK, result.getStatusCodeValue());
	}

	@Test
	public void updatePatient() {

		System.out.println("enter patient id to update");
		num = sc.nextInt();

		headers.add("Accept", "application/json");
		headers.add("Content-Type", "application/json");

		String jsonBody = "{\"id\":\"" + num
				+ "\",\"name\":\"demoUpdate\",\"city\":\"KAKINADA\",\"email\":\"demo@gmail.com\",\"phone\":\"+91 7896541235\",\"symptom\":\"EAR PAIN\"}";

		jsonBody = jsonBody.replace("EAR PAIN", "DYSMENORRHEA");

		HttpEntity<String> entity = new HttpEntity<String>(jsonBody, headers);

		ResponseEntity<String> result = restTemplate.exchange(GET_PATIENTS_BY_ID_API + num, HttpMethod.GET, entity,
				String.class);
		System.out.println(result);
		System.out.println(result.getStatusCode());

		if (result.getStatusCodeValue() == 200) {
			ResponseEntity<String> response = restTemplate.exchange(UPDATE_PATIENTS_BY_ID_API + num, HttpMethod.PUT,
					entity, String.class);
			System.out.println("Patient is updated succesfully with this id " + num);
			System.out.println(response.toString());
			assertEquals(HttpStatus.SC_OK, response.getStatusCodeValue());
		} else {
			System.out.println("Patient is not available with this id " + num);
			assertEquals(result.getStatusCodeValue(), result.getStatusCodeValue());
		}

	}

	@Test
	public void deletePatient() {

		System.out.println("enter patient id to delete");
		num = sc.nextInt();

		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

		ResponseEntity<String> result = restTemplate.exchange(GET_PATIENTS_BY_ID_API + num, HttpMethod.GET, entity,
				String.class);
		System.out.println(result);
		System.out.println(result.getStatusCode());

		if (result.getStatusCodeValue() == 200) {
			restTemplate.delete(DELETE_PATIENTS_API + num, entity);
			System.out.println("Patient is deleted succesfully with this id " + num);
			assertEquals(200.00, 200.00);
		} else {
			System.out.println("Patient is not available with this id " + num);
			assertEquals(result.getStatusCodeValue(), result.getStatusCodeValue());
		}

	}

	@Test
	public void getSuggestedDoctors() {

		System.out.println("enter patient symptom to suggest doctors");
		symptom = sc.nextLine();

		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

		ResponseEntity<String> result = restTemplate.exchange(GET_DOCTORS_BY_SYMPTOM + symptom, HttpMethod.GET, entity,
				String.class);
		System.out.println(result);
		assertEquals(HttpStatus.SC_OK, result.getStatusCodeValue());
	}

	/*Test cases for doctor API end points*/
	
	@Test
	public void getDoctor() throws Exception {

		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

		ResponseEntity<String> result = restTemplate.exchange(GET_ALL_DOCTORS_API, HttpMethod.GET, entity,
				String.class);
		System.out.println(result);
		System.out.println("status code " + result.getStatusCodeValue());

		assertEquals(HttpStatus.SC_OK, result.getStatusCodeValue());
	}

	@Test
	public void createDoctor() {

		headers.add("Accept", "application/json");
		headers.add("Content-Type", "application/json");

		String jsonBody = "{\"name\":\"demoRjy\",\"city\":\"Faridabad\",\"email\":\"demo@gmail.com\",\"phone\":\"+91 7896541235\",\"speciality\":\"Orthopedic\"}";
		HttpEntity<String> entity = new HttpEntity<String>(jsonBody, headers);

		ResponseEntity<String> response = restTemplate.postForEntity(CREATE_DOCTORS_API, entity, String.class);
		assertEquals(HttpStatus.SC_OK, response.getStatusCodeValue());
	}

	@Test
	public void getDoctorById() {
		
		System.out.println("enter doctor id to fetch details");
		num = sc.nextInt();

		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

		ResponseEntity<String> result = restTemplate.exchange(GET_DOCTORS_BY_ID_API + num, HttpMethod.GET, entity,
				String.class);
		System.out.println(result);
		assertEquals(HttpStatus.SC_OK, result.getStatusCodeValue());
	}

	@Test
	public void updateDoctor() {

		System.out.println("enter doctor id to update");
		num = sc.nextInt();

		headers.add("Accept", "application/json");
		headers.add("Content-Type", "application/json");

		String jsonBody = "{\"id\":\"" + num
				+ "\",\"name\":\"demoRjy\",\"city\":\"Faridabad\",\"email\":\"demo@gmail.com\",\"phone\":\"+91 7896541235\",\"speciality\":\"ORTHOPEDIC\"}";

//		jsonBody = jsonBody.replace("demoRjy", "demoUpdate");
		jsonBody = jsonBody.replace("DERMATOLOGY", "ORTHOPEDIC");

		HttpEntity<String> entity = new HttpEntity<String>(jsonBody, headers);

		ResponseEntity<String> result = restTemplate.exchange(GET_DOCTORS_BY_ID_API + num, HttpMethod.GET, entity,
				String.class);
		System.out.println(result);
		System.out.println(result.getStatusCode());

		if (result.getStatusCodeValue() == 200) {
			ResponseEntity<String> response = restTemplate.exchange(UPDATE_DOCTORS_BY_ID_API + num, HttpMethod.PUT, entity, String.class);

			System.out.println("Doctor is updated succesfully with this id " + num);
			System.out.println(response.toString());
			assertEquals(HttpStatus.SC_OK, response.getStatusCodeValue());
			
		} else {
			System.out.println("Patient is not available with this id " + num);
			assertEquals(result.getStatusCodeValue(), result.getStatusCodeValue());
		}

	}

	@Test
	public void deleteDoctor() {

		System.out.println("enter doctor id to delete");
		num = sc.nextInt();

		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

		ResponseEntity<String> result = restTemplate.exchange(GET_DOCTORS_BY_ID_API + num, HttpMethod.GET, entity,
				String.class);
		System.out.println(result);
		System.out.println(result.getStatusCode());

		if (result.getStatusCodeValue() == 200) {
			restTemplate.delete(DELETE_DOCTORS_API + num, entity);
			System.out.println("Patient is deleted succesfully with this id " + num);
			assertEquals(200.00, 200.00);
		} else {
			System.out.println("Patient is not available with this id " + num);
			assertEquals(result.getStatusCodeValue(), result.getStatusCodeValue());
		}

	}
}
