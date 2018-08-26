package com.prapps.ved;

import java.io.IOException;
import java.util.Collections;
import java.util.Date;

/*import javax.servlet.annotation.WebServlet;*/
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Query;

/*@WebServlet(
    name = "HelloAppEngine",
    urlPatterns = {"/test"}
)*/
public class HelloAppEngine extends HttpServlet {

	private static final String INDEX_KEY = "indexKey";
	
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) 
      throws IOException {

	  doPost(request, response);

  }
  
  @Override
  public void doPut(HttpServletRequest request, HttpServletResponse response) 
      throws IOException {
	  doPost(request, response);
  }
  
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) 
      throws IOException {
	  String action = request.getParameter("action");
	  System.out.println("action: "+action);
	  if ("save".equals(action)) {
		  Key key = saveObject();
		  response.setContentType("text/plain");
		  response.setCharacterEncoding("UTF-8");
		  response.getWriter().print(key+"\r\n");
	  } else {
	    response.setContentType("text/plain");
	    response.setCharacterEncoding("UTF-8");
	    response.getWriter().print("Hello App Engine!\r\n");
	  }
  }

	private Key saveObject() {
		Entity employee = new Entity("Employee", "employees");
		employee.setProperty("firstName", "Antonio");
		employee.setProperty("lastName", "Salieri");
		employee.setProperty("hireDate", new Date());
		employee.setProperty("attendedHrTraining", true);

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Key employeeKey = datastore.put(employee);

		Entity index = new Entity("index", INDEX_KEY);
		try {
			index = datastore.get(index.getKey());
		} catch (EntityNotFoundException e) {
			index.setProperty("employees", Collections.singleton(employeeKey));
			datastore.put(index);
		}
		return employeeKey;
	}
}