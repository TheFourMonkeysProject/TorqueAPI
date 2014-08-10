package com.fourmonkeys.api.service;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.fourmonkeys.api.domain.*;
import com.stormpath.sdk.account.Account;

public class UserServiceTests {
	
	private User user;
	
	@Before
	public  void setUp(  ) {
		this.user = new User();
		this.user.setEmail("matt.livingston@matt.com");
		this.user.setGiveName("Billy");
		this.user.setSurName("Balls");
		this.user.setUserName("BillsBalls");
		this.user.setPassword("J#ssified1");   
    }
	
	@Test 
	public void testUserCreate() throws IOException{
		UserResponse response = new UserResponse();	
		UserService service = new UserService();
		response = service.createUser(this.user);
		Assert.assertEquals(response.getResponseCode(),200);
		
	}
	
	@Test
	public void testUserAuthenticate() throws IOException{
		
		UserService service = new UserService();
		this.user = service.authenticateUser(user);
			
		Assert.assertEquals(this.user.getEmail(),"matt.livingston@matt.com");
	}

}
