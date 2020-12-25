package com.midTerm.rest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.json.JSONArray;
import org.json.JSONObject;








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
	public Response deleteEmployee(@PathParam("id") int id)
	{
		Sqlconnector connection = new Sqlconnector();	
		con = connection.getConnection();
		int status = 200;
		try {
			String query = "delete from acc_transaction where TXN_ID = "+id;
			
			stmt = con.createStatement();
			stmt.execute(query);
			mainobj.accumulate("Message", "Success");
			mainobj.accumulate("Status", "200");
			
		}catch(SQLException e){
			mainobj.accumulate("Message", "Error!"+e.getMessage());
			mainobj.accumulate("Status", "500");
			status = 500;
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
		return Response.status(status).entity(mainobj.toString()).build();
	}
	
	
	@GET
	@Path("/getAccountsByProductType/{type}")
	@Produces(MediaType.APPLICATION_JSON)
	
	public Response getAccountsByProductType(@PathParam("type") String type) {
		
		Sqlconnector connection = new Sqlconnector();	
		con = connection.getConnection();
		
		try {
			stmt = con.createStatement();
			String q = "select t.NAME,t.PRODUCT_TYPE_CD,p.PRODUCT_CD,p.DATE_OFFERED,a.ACCOUNT_ID,a.AVAIL_BALANCE,a.LAST_ACTIVITY_DATE,a.OPEN_DATE,a.PENDING_BALANCE,a.STATUS from product_type t,product p,account a where t.PRODUCT_TYPE_CD = p.PRODUCT_TYPE_CD and p.PRODUCT_CD = a.PRODUCT_CD and t.PRODUCT_TYPE_CD =\'"+type+"\'";
			rs = stmt.executeQuery(q);
			
			
				while(rs.next()) 
				{
					
					childObj = new JSONObject();
					
					
					//childObject.accumulate("Account ID", rs.getString("ACCOUN_ID"));
					childObj.accumulate("Product Name", rs.getString("NAME"));
					childObj.accumulate("Product Type Code", rs.getString("PRODUCT_TYPE_CD"));
					childObj.accumulate("Product Code", rs.getString("PRODUCT_CD"));
					childObj.accumulate("Date Offered", rs.getString("DATE_OFFERED"));
					childObj.accumulate("Account ID", rs.getString("ACCOUNT_ID"));
					childObj.accumulate("Available Balance", rs.getString("AVAIL_BALANCE"));
					childObj.accumulate("Last Activity Date", rs.getString("LAST_ACTIVITY_DATE"));
					childObj.accumulate("Open Date", rs.getString("OPEN_DATE"));
					childObj.accumulate("Panding Balance", rs.getString("PENDING_BALANCE"));
					childObj.accumulate("Status", rs.getString("STATUS"));
					
					jsonArray.put(childObj);
					
				}
				mainobj.put("Customers",jsonArray);
				
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
			e.printStackTrace();
			mainobj.accumulate("Status", 204);
			mainobj.accumulate("Message", e.getMessage());
		}
		
		return Response.noContent().entity(mainobj.toString()).build();

	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/updateDepartmentByEmployee")
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateDepartmentofemployee(Employee employees)
	{
		Sqlconnector connection = new Sqlconnector();	
		con = connection.getConnection();
		Status status = Status.OK;
		try {
			String query = "UPDATE `midterm`.`employee` SET `DEPT_ID` =? WHERE `EMP_ID` =?";
			
			preparedStatement = con.prepareStatement(query);
			
			preparedStatement.setInt(1, employees.getDepartmentId());
			preparedStatement.setInt(2, employees.getEmployeeId());
			
			
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
	
	@POST
	@Path("/updateEmp")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateEmployee(Customer customer) {
		Sqlconnector connection = new Sqlconnector();

		con = connection.getConnection();

		int status = 200;
		try {

			String query = "update customer set ADDRESS = ? where CUST_ID = ?;";

			preparedStatement = con.prepareStatement(query);

			preparedStatement.setInt(2, customer.getId());
			preparedStatement.setString(1, customer.getAddress());

			int rowCount = preparedStatement.executeUpdate();

			if (rowCount > 0) {
				System.out.println("Record Update Successfully");

				mainobj.accumulate("status", 200);
				mainobj.accumulate("message", "Successfully Updated");
			} else {
				status = 500;
				mainobj.accumulate("status", 500);
				mainobj.accumulate("message", "Something Wrong");
			}

		} catch (SQLException e) {
			status = 500;
			mainobj.accumulate("status", 500);
			mainobj.accumulate("message", e.getMessage());
		} finally {
			try {
				con.close();
				preparedStatement.close();
			} catch (SQLException e) {
				System.out.println("Finally Block SQL Exception : " + e.getMessage());
			}
		}
		return Response.status(status).entity(mainobj.toString()).build();
	}
	
	@GET
	@Path("/getTransactions/{from}/{to}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTransactions(@PathParam("from") String from,@PathParam("to") String to) 
	{	
		Sqlconnector connection = new Sqlconnector();	 
		con = connection.getConnection();
		
		try 
		{
			stmt = con.createStatement();
			String query = "select x.TXN_ID,x.AMOUNT,x.FUNDS_AVAIL_DATE,x.TXN_DATE,x.TXN_TYPE_CD,a.CUST_ID,i.FIRST_NAME,i.LAST_NAME from acc_transaction x,account a ,customer c,individual i where x.ACCOUNT_ID = a.ACCOUNT_ID and a.CUST_ID = c.CUST_ID and i.CUST_ID = c.CUST_ID and TXN_DATE between \""+from+"\" and \""+to+"\";";
			rs = stmt.executeQuery(query);
			System.out.println(query);
			while(rs.next()) 
			{
				childObj = new JSONObject();
				
				childObj.accumulate("Trasaction ID", rs.getString("x.TXN_ID"));
				childObj.accumulate("Amount", rs.getString("x.AMOUNT"));
				childObj.accumulate("Funds Available Date", rs.getString("x.FUNDS_AVAIL_DATE"));
				childObj.accumulate("Transaction Date", rs.getString("x.TXN_DATE"));
				childObj.accumulate("Transaction Type Code", rs.getString("x.TXN_TYPE_CD"));
				childObj.accumulate("Customer ID", rs.getString("a.CUST_ID"));
				childObj.accumulate("First Name", rs.getString("i.FIRST_NAME"));
				childObj.accumulate("Last Name", rs.getString("i.LAST_NAME"));
				
				
				jsonArray.put(childObj);
	
			}
			mainobj.put("Transactions",jsonArray);
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
	
	@POST
	@Path("/createEmp")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createCustomer(Customer customer) {
		Sqlconnector connection = new Sqlconnector();

		con = connection.getConnection();

		int status = 200;
		try {

			String query = "INSERT INTO customer(CUST_ID,ADDRESS,CITY,CUST_TYPE_CD,FED_ID,POSTAL_CODE,STATE) VALUES (?,?,?,?,?,?,?);";

			preparedStatement = con.prepareStatement(query);

			preparedStatement.setInt(1, customer.getId());
			preparedStatement.setString(2, customer.getAddress());
			preparedStatement.setString(3, customer.getCity());
			preparedStatement.setString(4, customer.getCustType());
			preparedStatement.setString(5, customer.getFedId());
			preparedStatement.setString(6, customer.getPostalCode());
			preparedStatement.setString(7, customer.getState());

			int rowCount = preparedStatement.executeUpdate();

			if (rowCount > 0) {
				System.out.println("Record Inserted Successfully");

				mainobj.accumulate("status", 200);
				mainobj.accumulate("message", "Successfully Updated");
			} else {
				status = 500;
				mainobj.accumulate("status", 500);
				mainobj.accumulate("message", "Something Wrong");
			}

		} catch (SQLException e) {
			status = 500;
			mainobj.accumulate("status", 500);
			mainobj.accumulate("message", e.getMessage());
		} finally {
			try {
				con.close();
				preparedStatement.close();
			} catch (SQLException e) {
				System.out.println("Finally Block SQL Exception : " + e.getMessage());
			}
		}
		return Response.status(status).entity(mainobj.toString()).build();
	}
	
}
