package com.esb.user;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerTest {

	 @Autowired
     private WebApplicationContext webApplicationContext;

     private MockMvc mockMvc;

     @Before
     public void setUp() throws Exception {
         mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
     } 
     
     /** 
      * 
      * Method: pageList() 
      * 
      */ 
      @Test
      public void testPageList() throws Exception {
    	  MvcResult mvcResult =  mockMvc.perform(MockMvcRequestBuilders.get("/user/pageList").param("page","1").param("page-size","2")
        		  .accept(MediaType.APPLICATION_JSON_UTF8))
    			  .andExpect(MockMvcResultMatchers.jsonPath("$.total").value("2"))
    			  .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
          		  .andDo(MockMvcResultHandlers.print())
          		  .andReturn();  
    	  int status=mvcResult.getResponse().getStatus();                 //得到返回程式碼
    	  String content= mvcResult.getResponse().getContentAsString();    //得到返回結果
    	  Assert.assertEquals(200,status);                        //斷言，判斷返回程式碼是否正確 	 
    	   
    	  
      }
	
}