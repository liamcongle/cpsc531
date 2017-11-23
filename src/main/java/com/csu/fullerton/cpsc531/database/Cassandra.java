/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.csu.fullerton.cpsc531.database;

import com.csu.fullerton.cpsc531.obj.Contact;
import com.csu.fullerton.cpsc531.obj.Department;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.policies.DefaultRetryPolicy;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.Metadata;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Statement;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
		session = cluster.connect("contact_mgmt");
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
		
		String cqlkeyspace = "CREATE KEYSPACE IF NOT EXISTS contact_mgmt WITH replication "
				+ "= {'class':'SimpleStrategy', 'replication_factor':3};";
		
		session.execute(cqlkeyspace);
		
		session.execute("USE contact_mgmt");

		String cqldroptable = "DROP TABLE contacts";
		
		session.execute(cqldroptable);

		String cqlcreatestmt = "CREATE TABLE IF NOT EXISTS contacts (" 
										+ "contact_id int PRIMARY KEY," 
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
										+ "report_to int,"
										+ "photo blob" + ");";
		
		session.execute(cqlcreatestmt);
	}	
	
	
    // TEST FUNCTION
//    public ResultSet selectAllEmployees() {
/*        cluster = connect("localhost");
        //connects to the cluster 
        session = cluster.connect();
        session.execute("USE contact_mgmt");
        ResultSet results = session.execute("SELECT * FROM employee");
        session.close();
        cluster.close();
        return results;
*/
//    }

    //PLEASE IMPLEMENT ALL THE FUNCTIONS BELOW:
    public UUID insertContact(Contact contact) {
        return null;
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
		Cassandra cass = new Cassandra();
		try {
			cass.connect("localhost");
			cass.createSchema();
			cass.insertContact();
			cass.updateContact();
			cass.deleteContact();
			cass.selectAllContact();
			cass.selectTheManagement();
			cass.selectContactById();
			cass.selectAllDepartments();
			cass.search();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cass.closeSession();
		}
	}
}
