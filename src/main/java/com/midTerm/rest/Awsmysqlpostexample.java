package com.midTerm.rest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.json.JSONArray;
import org.json.JSONObject;


import java.sql.Statement;


@Path("/aws")
public class Awsmysqlpostexample {
	
	Connection con = null;
	Statement stmt = null;
	ResultSet rs = null;
	PreparedStatement preparedStatement = null;
	
	JSONObject mainobj = new JSONObject();
	JSONArray jsonArray = new JSONArray();
	JSONObject childObj = new JSONObject();
	
	@GET
	@Path("/demo")
	@Produces(MediaType.APPLICATION_JSON)
	public Response demo()
	{
		mainobj.accumulate("name", "Akash");
		
		return Response.status(200).entity(mainobj.toString()).build();
	}
	
	
	
	@GET
	@Path("/getEmp")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getEmployee()
	{
		Sqlconnector connection = new Sqlconnector();
		
		con = connection.getConnection();
		
		try 
		{
			stmt = con.createStatement();
			
			rs = stmt.executeQuery("Select * from employees");
			
			while(rs.next())
			{
				childObj = new JSONObject();
				childObj.accumulate("Employee Number", rs.getString("employeeNumber"));
				childObj.accumulate("LastName", rs.getString(2));
				childObj.accumulate("FirstName", rs.getString("firstname"));
				
				jsonArray.put(childObj);
	
			}
			mainobj.put("Employee",jsonArray);
		}
		catch(SQLException e) {  
			System.out.println("SQL Exception : "+e.getMessage());
		}
		finally {
			try {
				con.close();
				stmt.close();
				rs.close();
			}
			catch(SQLException e)
			{
				System.out.println("Finally Block SQL Exception : "+e.getMessage());
			}
		}
		return Response.status(200).entity(mainobj.toString()).build();
	}
	
	
	@POST
	@Path("/createEmp")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createEmployee(Employees employees)
	{
		Sqlconnector connection = new Sqlconnector();
		
		con = connection.getConnection();
		
		try 
		{
			
			String query = "INSERT INTO `classicmodels`.`employees`(`employeeNumber`,`lastName`, `firstName`, `extension`, `email`, `officeCode`, `reportsTo`, `jobTitle`) VALUES(?,?,?,?,?,?,?,?)";
			
			preparedStatement = con.prepareStatement(query);
				
			preparedStatement.setInt(1,employees.getEmployeeNumber());
			preparedStatement.setString(2,employees.getLastName());
			preparedStatement.setString(3,employees.getFirstName());
			preparedStatement.setString(4,employees.getExtension());
			preparedStatement.setString(5,employees.getEmail());
			preparedStatement.setString(6, employees.getOfficeCode());
			preparedStatement.setInt(7,employees.getReportsTo());
			preparedStatement.setString(8,employees.getJobTitle()); 
			
			int rowCount = preparedStatement.executeUpdate();
			
			if(rowCount>0)
			{
				System.out.println("Record inserted Successfully");
				
				mainobj.accumulate("status", 201);
				mainobj.accumulate("message", "Successfully Inserted");
			}
			else
			{
							
				mainobj.accumulate("status", 500);
				mainobj.accumulate("message", "Something Wrong");
			}
			
		}
		catch(SQLException e) {  
			mainobj.accumulate("status", 500);
			mainobj.accumulate("message", e.getMessage());
		}
		finally {
			try {
				con.close();
				preparedStatement.close();
			}
			catch(SQLException e)
			{
				System.out.println("Finally Block SQL Exception : "+e.getMessage());
			}
		}
		return Response.status(201).entity(mainobj.toString()).build();
	}
	@GET
	@Path("/office/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	
	public Response getoffice(@PathParam("id") String id) {
		
		Sqlconnector connection = new Sqlconnector();	
		con = connection.getConnection();
		
		try {
			stmt = con.createStatement();
			String query = "Select * from offices where officeCode = "+id;
			rs = stmt.executeQuery(query);
			
				while(rs.next()) 
				{
					mainobj.accumulate("ID", rs.getString("officeCode"));
					mainobj.accumulate("City", rs.getString("city"));
					mainobj.accumulate("Phone", rs.getString("phone"));
					mainobj.accumulate("addressLine1", rs.getString("addressLine1"));
					mainobj.accumulate("addressLine2", rs.getString("addressLine2"));
					mainobj.accumulate("state", rs.getString("state"));
					mainobj.accumulate("country", rs.getString("country"));
					mainobj.accumulate("postalCode", rs.getString("postalCode"));
					mainobj.accumulate("territory", rs.getString("territory"));
		
				}
				if (!mainobj.isEmpty())
				{
					return Response.ok().entity(mainobj.toString()).build();
				}
				else
				{
					mainobj.accumulate("Status", 404);
					mainobj.accumulate("Message", "Content Not Found!");
					return Response.status(Response.Status.NOT_FOUND).entity(mainobj.toString()).build();
				}
			}
			catch(SQLException e) {
			
				mainobj.accumulate("Status", 204);
				mainobj.accumulate("Message", e.getMessage());
		}
		
		return Response.noContent().entity(mainobj.toString()).build();

	}
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/updateEmp/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateEmployee(@PathParam("id") int id,Employees employees)
	{
		Sqlconnector connection = new Sqlconnector();	
		con = connection.getConnection();
		Status status = Status.OK;
		try {
			String query = "UPDATE `classicmodels`.`employees` SET `employeeNumber` = ?,`lastName` = ?,`firstName` = ?,`extension` = ?,`email` = ?,`officeCode` = ?,`reportsTo` = ?,`jobTitle` = ? WHERE `employeeNumber` ="+id;
			
			preparedStatement = con.prepareStatement(query);
			
			preparedStatement.setInt(1, employees.getEmployeeNumber());
			preparedStatement.setString(2, employees.getLastName());
			preparedStatement.setString(3, employees.getFirstName());
			preparedStatement.setString(4, employees.getExtension());
			preparedStatement.setString(5, employees.getEmail());
			preparedStatement.setString(6, employees.getOfficeCode());
			preparedStatement.setInt(7, employees.getReportsTo());
			preparedStatement.setString(8, employees.getJobTitle());
			
			
			
			int rawCount = preparedStatement.executeUpdate();
			
			if(rawCount>0)
			{
				status = Status.OK;
				mainobj.accumulate("Status", Status.OK);
				mainobj.accumulate("Message", "Data Successfully Updated!!");
			}
			else {
				status = Status.NOT_MODIFIED;
				mainobj.accumulate("Status", status);
				mainobj.accumulate("Message", "Something went wrong!!!");
				
			}
			
			} catch(SQLException e) {
			e.printStackTrace();
			status = Status.NOT_MODIFIED;
			mainobj.accumulate("Status", status);
			mainobj.accumulate("Message", "Something went wrong!!!");
			}
			
		return Response.status(status).entity(mainobj.toString()).build();
	}
	
	@Path("/deleteEmp/{id}")
	@DELETE
	public Response deleteEmployee(@PathParam("id") int id)
	{
		Sqlconnector connection = new Sqlconnector();	
		con = connection.getConnection();
		
		try {
			String query = "Delete from Employee where employeeNumber = "+id;
			
			stmt = con.createStatement();
			stmt.execute(query);
			
		}catch(SQLException e){
			System.out.println("SQL Exception : " + e.getMessage());
		}
		finally {
			try {
				con.close();
				stmt.close();
			} catch (SQLException e) {
				System.out.println("Finally Block SQL Exception : " + e.getMessage());
			}
		}
		return Response.status(200).entity(mainobj.toString()).build();
	}

}
