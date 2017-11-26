/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.csu.fullerton.cpsc531.database;

import com.csu.fullerton.cpsc531.ui.InsertContact;

import com.csu.fullerton.cpsc531.obj.Contact;
import com.csu.fullerton.cpsc531.obj.Department;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.Metadata;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.Statement;
import com.datastax.driver.core.policies.DefaultRetryPolicy;
import com.datastax.driver.core.querybuilder.QueryBuilder;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

//import com.datastax.driver.core.BatchStatement;


import java.nio.ByteBuffer;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;

/**
 *
 * @author Asit
 */
public class Cassandra {

    private static Cluster cluster;
    private static Session session;
	ResultSet results;
	Row rows;

	public void connect(String node) {
		cluster = Cluster.builder().addContactPoint(node).withRetryPolicy(DefaultRetryPolicy.INSTANCE).build();
		
		Metadata metadata = cluster.getMetadata();
		System.out.println("Connected to cluster:" + metadata.getClusterName());
		for (Host host : metadata.getAllHosts()) {
			System.out.println("Datatacenter: " + host.getDatacenter() + "; Host: " + host.getAddress() + "; Rack: "
					+ host.getRack());
		}
		System.out.println("\n");
	}

	public void getSession() {
		session = cluster.connect();
	}

	public void closeSession() {
		session.close();
		cluster.close();
	}
	
	public void createSchema() {
				
		String cqlkeyspace = "CREATE KEYSPACE IF NOT EXISTS contact_mgmt WITH REPLICATION "
				+ "= {'class':'SimpleStrategy', 'replication_factor':3};";
		
		session.execute(cqlkeyspace);
		
		session.execute("USE contact_mgmt");

		String cqlcreatestmt = "CREATE TABLE IF NOT EXISTS contacts (" 
										+ "contact_id uuid PRIMARY KEY," 
										+ "first_name text," 
										+ "last_name text," 
										+ "company text," 
										+ "address1 text,"
										+ "address2 text,"
										+ "email text," 
										+ "telephone text," 
										+ "cellphone text,"
										+ "role text,"
										+ "department_code text,"
										+ "report_to text,"
										+ "photo blob" + ");";
		
		session.execute(cqlcreatestmt);
		System.out.println("creation complete in Cassandra DB");
	}	
	
//    public UUID insertContact(Contact contact) {
//    public void insertContact(Contact contact) throws IOException {	
    public void insertContact(Contact insContact){    	
/* 
    	BufferedImage originalImage = ImageIO.read(new File("D:/CSU Fullerton/ISa_Bowling.JPG"));
    	ByteArrayOutputStream imageStream = new ByteArrayOutputStream();
    	ImageIO.write(originalImage, "JPG", imageStream );
    	imageStream.flush();
    	byte[] imageInByte = imageStream.toByteArray();
    	ByteBuffer buffer = ByteBuffer.wrap(imageInByte);
*/    	
    	connect("localhost");
    	getSession();
    	createSchema();
    	
    	PreparedStatement statement = session.prepare("INSERT INTO contacts" 
    				+ "(contact_id,first_name,last_name,company,address1,address2,email,"
    				+ "telephone,cellphone,role,department_code,report_to,photo)"
    				+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?);");
    	
    	
//    	ByteBuffer.wrap(image.getBytes()
//    	BoundStatement cqlinsertstmt = statement.bind(UUID.randomUUID(), "cool", "guy", "facebook", "600 langsdorf", "",
//    			"lol@gmail.com", "", "6572588989", "Manager", "B1", UUID.randomUUID(),buffer);
    	
    	BoundStatement cqlinsertstmt = statement.bind(
    			insContact.getContactId(),
    			insContact.getFirstname(),
    			insContact.getLastname(),
    			insContact.getCompany(),
    			insContact.getAddress1(),
    			insContact.getAddress2(),
    			insContact.getEmail(),
    			insContact.getTelephone(),
    			insContact.getCellphone(),
    			insContact.getRole(),
    			insContact.getDepartment_code(),
    			insContact.getReport_to(),
    			insContact.getPhoto());
    	
    	session.execute(cqlinsertstmt);
    	System.out.println("Insertion complete in Cassandra DB");
    }

    public boolean updateContact(Contact contact) {
        return false;
    }

    public boolean deleteContact(UUID contactId) {
        return false;
    }

    public List<Contact> selectAllContact() {
        return new ArrayList<Contact>();
    }

    public List<Contact> selectTheManagement() { // all has roles: all but NOT 'employee' or 'other'
        return new ArrayList<Contact>();
    }

    public Contact selectContactById(UUID contactId) {
        return null;
    }

    public List<Department> selectAllDepartments() {
        return new ArrayList<Department>();
    }

    // search by 'LIKE' firstname, lastname, or companyName
    public List<Contact> search(String keyword) {
        return new ArrayList<Contact>();
    }
    
	public static void main(String[] args) {
//		Cassandra cass = new Cassandra();
//		try {
//			cass.connect("localhost");
//			cass.getSession();
//			cass.createSchema();
			
//			cass.insertContact();
//			cass.updateContact();
//			cass.deleteContact();
//			cass.selectAllContact();
//			cass.selectTheManagement();
//			cass.selectContactById();
//			cass.selectAllDepartments();
//			cass.search();
			
//		} catch (Exception e) {
//			e.printStackTrace();
///		} finally {
			cass.closeSession();
//		}
	}
}
