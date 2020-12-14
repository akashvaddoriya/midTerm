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






@Path("aws")

public class Awsmysqlpostexample 
{
	
	Connection con = null;
	Statement stmt = null;
	ResultSet rs = null;
	PreparedStatement preparedStatement = null;
	
	JSONObject mainobj = new JSONObject();
	JSONArray jsonArray = new JSONArray();
	JSONObject childObj = new JSONObject();
	
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
			rs = stmt.executeQuery("select * from department d,employee e where d.DEPT_ID = e.DEPT_ID and d.NAME = 'Operations';");
			
			while(rs.next()) 
			{
				childObj = new JSONObject();
				
				childObj.accumulate("Department_id", rs.getString("DEPT_ID"));
				childObj.accumulate("Department_name", rs.getString("NAME"));
				childObj.accumulate("Employee_id", rs.getString("EMP_ID"));
				childObj.accumulate("FirstName", rs.getString("FIRST_NAME"));
				childObj.accumulate("LastName", rs.getString("LAST_NAME"));
				childObj.accumulate("StartDate", rs.getString("START_DATE"));
				childObj.accumulate("Title", rs.getString("TITLE"));
				childObj.accumulate("BranchId", rs.getString("ASSIGNED_BRANCH_ID"));
				childObj.accumulate("SuperiorEmpId", rs.getString("SUPERIOR_EMP_ID"));
				
				
				jsonArray.put(childObj);
	
			}
			mainobj.put("Employees",jsonArray);
		}
		catch(SQLException e) 
		{
			System.out.println("SQL Exception : "+e.getMessage());
		}
		finally 
		{
			try 
			{
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
	
	
	
	@GET
	@Path("/getCustomer")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getEmployee2() 
	{	
		Sqlconnector connection = new Sqlconnector();	 
		con = connection.getConnection();
		
		try 
		{
			stmt = con.createStatement();
			rs = stmt.executeQuery("select i.CUST_ID,i.BIRTH_DATE,i.FIRST_NAME,i.LAST_NAME,c.ADDRESS,c.CITY,c.POSTAL_CODE,c.STATE from customer c,individual i where c.CUST_ID = i.CUST_ID and i.FIRST_NAME like '%fra%';;");
			
			while(rs.next()) 
			{
				childObj = new JSONObject();
				
				childObj.accumulate("customer ID", rs.getString("CUST_ID"));
				childObj.accumulate("customer BD", rs.getString("BIRTH_DATE"));
				childObj.accumulate("Lastname", rs.getString("LAST_NAME"));
				childObj.accumulate("Firstname", rs.getString("FIRST_NAME"));
				childObj.accumulate("Address", rs.getString("ADDRESS"));
				childObj.accumulate("city", rs.getString("CITY"));
				childObj.accumulate("Postalcode", rs.getString("POSTAL_CODE"));
				childObj.accumulate("state", rs.getString("STATE"));
				
				
				jsonArray.put(childObj);
	
			}
			mainobj.put("customers",jsonArray);
			//mainobj.put("individual", jsonArray);
		}
		catch(SQLException e) 
		{
			System.out.println("SQL Exception : "+e.getMessage());
		}
		finally 
		{
			try 
			{
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
	
	
	
	@GET
	@Path("/deleteTransaction/{id}")
	@DELETE
	public Response deleteEmployee(@PathParam("id") int id)
	{
		Sqlconnector connection = new Sqlconnector();	
		con = connection.getConnection();
		
		try {
			String query = "delete from acc_transaction where TXN_ID = "+id;
			
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