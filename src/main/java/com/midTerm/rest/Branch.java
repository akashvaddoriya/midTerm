package com.midTerm.rest;

public class Branch 
{
		public int BRANCH_ID;
		public String name;
		public String address;
		public String city;
		public String state;
		public String zip;
		public int getId() {
			return BRANCH_ID;
		}
		public void setId(int BRANCH_ID) {
			this.BRANCH_ID = BRANCH_ID;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getAddress() {
			return address;
		}
		public void setAddress(String address) {
			this.address = address;
		}
		public String getCity() {
			return city;
		}
		public void setCity(String city) {
			this.city = city;
		}
		public String getState() {
			return state;
		}
		public void setState(String state) {
			this.state = state;
		}
		public String getZip() {
			return zip;
		}
		public void setZip(String zip) {
			this.zip = zip;
		}

		
}