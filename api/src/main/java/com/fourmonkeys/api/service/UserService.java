package com.fourmonkeys.api.service;

import java.io.IOException;
import java.util.Properties;

import com.fourmonkeys.api.domain.User;
import com.stormpath.sdk.account.*;
import com.stormpath.sdk.application.*;
import com.stormpath.sdk.authc.AuthenticationResult;
import com.stormpath.sdk.authc.UsernamePasswordRequest;
import com.stormpath.sdk.directory.*;
import com.stormpath.sdk.resource.ResourceException;
import com.stormpath.sdk.tenant.*;
import com.stormpath.sdk.client.*;
@SuppressWarnings("deprecation")
public class UserService {

	String path = System.getProperty("user.home") + "/.stormpath/apiKey.properties";
	
	ApiKey apiKey = ApiKeys.builder().setFileLocation(path).build();
	Client client = Clients.builder().setApiKey(apiKey).build();
	
	Application application = client.getResource("https://api.stormpath.com/v1/applications/7Tf8QYxb90U7Bzk3ARJOFm", Application.class);
	
	public UserService() throws IOException{
		Properties properties = new Properties();
		properties.load(UserService.class.getResourceAsStream("/application.properties"));
	}
		
	public UserResponse createUser(User user){
		//Create the account object
		Account account = client.instantiate(Account.class);
		UserResponse response = new UserResponse();
		//Set the account properties
		account.setGivenName(user.getGiveName());
		account.setSurname(user.getSurName());
		account.setUsername(user.getUserName()); //optional, defaults to email if unset
		account.setEmail(user.getEmail());
		account.setPassword(user.getPassword());
		CustomData customData = account.getCustomData();
		customData.put("UserType", "Instructor");

		try{
			
			application.createAccount(account);
		}
		catch(com.stormpath.sdk.resource.ResourceException e){
			response.setResponseCode(e.getStatus());
			response.setMessage(e.getMessage());
		}
		catch(Exception e){
			System.out.println(e.toString());
		}
		
		response.setMessage("Account Created Successfully. Welcome to Torque!");
		response.setResponseCode(200);
		return response;
	}
	
	public void getUser(User user){
		
		
	}
	
	public User authenticateUser(User user){
		UserResponse response = new UserResponse();
		AuthenticationResult result = null;
		Account account = null;
		
		try{
			UsernamePasswordRequest authenticationRequest = new UsernamePasswordRequest(user.getUserName(), user.getPassword());
			result = application.authenticateAccount(authenticationRequest);
			account = result.getAccount();
			
		}catch (ResourceException e) {
			response.setResponseCode(e.getStatus());
			response.setMessage(e.getMessage());
		}
		user.setEmail(account.getEmail());
		
		return user;
	}
	
	public void deleteUser(){
		
	}

	public void updateUser(){
		
	}
	
}
