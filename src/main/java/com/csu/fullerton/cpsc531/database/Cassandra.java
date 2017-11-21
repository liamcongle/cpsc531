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
//import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author Liam
 */
public class Cassandra {

    private static Cluster cluster;
    private static Session session;

    public static Cluster connect(String node) {
        //returns cluster instance using contactpoint 
        //which uses the address of the node that cassandra uses to connect
        return Cluster.builder().addContactPoint(node).build();
    }

    // TEST FUNCTION
    public ResultSet selectAllEmployees() {
        cluster = connect("localhost");
        //connects to the cluster 
        session = cluster.connect();
        session.execute("USE contact_mgmt");
        ResultSet results = session.execute("SELECT * FROM employee");
        session.close();
        cluster.close();
        return results;
    }

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

}
