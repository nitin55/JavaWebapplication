/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cruddemo.util;

import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nitin
 */
//class A {
//
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public String getFirstname() {
//        return firstname;
//    }
//
//    public void setFirstname(String firstname) {
//        this.firstname = firstname;
//    }
//
//    public String getLastname() {
//        return lastname;
//    }
//
//    public void setLastname(String lastname) {
//        this.lastname = lastname;
//    }
//
//    public int getSalary() {
//        return salary;
//    }
//
//    public void setSalary(int salary) {
//        this.salary = salary;
//    }
//
//    private int id;
//    private String firstname;
//    private String lastname;
//    private int salary;
//
//    public A(int id, String firstname, String lastname, int salary) {
//        this.id = id;
//        this.firstname = firstname;
//        this.lastname = lastname;
//        this.salary = salary;
//    }
//
//}

public class CsvUtil<T> {

    //Delimiters which has to be in the CSV file
    private static final String COMMA_DELIMITER = ",";
    private static final String LINE_SEPARATOR = "\n";
    private static final String ENCODING = "UTF-8";
    //File header
    private static String header = "";
    private static String filename = "";

    private static final String CSV_COLUMN_SEPARATOR = ";";

    private static final String CSV_LINE_SEPARATOR = "\n";

    public static String getHeader() {
        return header;
    }

    public static void setHeader(String header) {
        CsvUtil.header = header;
    }

    public static String getFilename() {
        return filename;
    }

    public static void setFilename(String filename) {
        CsvUtil.filename = filename;
    }

    private void write(Object value, OutputStream out) throws IOException {

        System.out.println("write method: "+value.toString());
        out.write(value == null ? "".getBytes(ENCODING) : value.toString().getBytes(ENCODING));
    }

    private HashMap<Integer, Field> getFields(Class<? extends Object> dataClass) {
        StringBuffer headerlist = new StringBuffer();
        HashMap<Integer, Field> fields = new HashMap<>();
        int i = 0;
        for (Field field : dataClass.getDeclaredFields()) {
            headerlist.append(field.getName());
            headerlist.append(CSV_COLUMN_SEPARATOR);
             System.out.println("name:"+field.getName());
            fields.put(i, field);
            i++;
        }
        System.out.println("header is: "+headerlist.toString());
        setHeader(headerlist.toString());
        return fields;
    }

    public void generateCSVFile(byte[] csvdata) {
        FileWriter fw = null;
        try
        {
             fw = new FileWriter(getFilename());
            fw.write(new String(csvdata));
        }
        catch(IOException io)
        {
            io.printStackTrace();
        }
        finally
        {
            try {
                fw.flush();
                fw.close();
            } catch (IOException ex) {
                //Logger.getLogger(CsvUtil.class.getName()).log(Level.SEVERE, null, ex);
                ex.printStackTrace();
            }
            
        }
    }
    public byte[] exportCSV(List<T> dataList) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        boolean isSetHeader = false;
        for (T data : dataList) {
            HashMap<Integer, Field> fields = getFields(data.getClass());
             
            System.out.println(fields.toString());
            System.out.println(".....................");
            for (int i = 0; i <= fields.size(); ++i) {

                Field field = fields.get(i);

                if (field == null) {
                    continue;
                }

                field.setAccessible(true);
                try {
                    Object fieldValue = field.get(data);
                    System.out.println("fieldname 2: "+field.getName());
                    System.out.println("fieldvalue: "+fieldValue.toString());
                    if(!isSetHeader)
                    {
                        isSetHeader = true;
                        System.out.println("ii"+i);

                        bos.write(getHeader().getBytes(ENCODING));
                        bos.write("\n".getBytes(ENCODING));
                       
                    }
                    this.write(fieldValue, bos);
                    this.write(CSV_COLUMN_SEPARATOR, bos);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            this.write(CSV_LINE_SEPARATOR, bos);
        }
        return bos.toByteArray();
 
    }
//    public static void main(String[] args) throws IOException {
//        A a1 = new A(1, "FirstName1", "LastName1", 10000);
//        A a2 = new A(2, "FirstName2", "LastName2", 20000);
//        
//        List<A> beans = new ArrayList<>();
//        beans.add(a1);
//        beans.add(a2);
//        CsvUtil<A> csv = new CsvUtil<>();
//        csv.setFilename("/home/nitin/tmp/csv.csv");
//        byte[] csvdata = csv.exportCSV(beans);
//        csv.generateCSVFile(csvdata);
//        //for(int i=0; i< csvdata.length ; i++) {
//            System.out.println(new String(csvdata));
//        //}
//        
//    }

//    public static void main(String[] args) {
//        A a = new A(1, "FirstName1", "LastName1", 10000);
//        CsvUtil d = new CsvUtil(a);
//    }
//    public static void main(String args[])
//    {
//    	//Creating Employee objects
//    	Employee emp1 = new Employee(1,"FirstName1","LastName1",10000);
//    	Employee emp2 = new Employee(2,"FirstName2","LastName2",20000);
//    	Employee emp3 = new Employee(3,"FirstName3","LastName3",30000);
//    	Employee emp4 = new Employee(4,"FirstName4","LastName4",40000);
//    	Employee emp5 = new Employee(5,"FirstName5","LastName5",50000);
//    	
//    	//Add Employee objects to a list
//    	List empList = new ArrayList();
//    	empList.add(emp1);
//    	empList.add(emp2);
//    	empList.add(emp3);
//    	empList.add(emp4);
//    	empList.add(emp5);
//    	
//    	FileWriter fileWriter = null;
//    	
//    	try
//    	{
//    		fileWriter = new FileWriter("Employee.csv");
//    		
//    		//Adding the header
//    		fileWriter.append(HEADER);
//    		//New Line after the header
//    		fileWriter.append(LINE_SEPARATOR);
//    		
//    		//Iterate the empList
//    		Iterator it = empList.iterator();
//    		while(it.hasNext())
//    		{
//    			Employee e = (Employee)it.next();
//    			fileWriter.append(String.valueOf(e.getEmpId()));
//    			fileWriter.append(COMMA_DELIMITER);
//    			fileWriter.append(e.getFirstName());
//    			fileWriter.append(COMMA_DELIMITER);
//    			fileWriter.append(e.getLastName());
//    			fileWriter.append(COMMA_DELIMITER);
//    			fileWriter.append(String.valueOf(e.getSalary()));
//    			fileWriter.append(LINE_SEPARATOR);
//    		}
//    		System.out.println("Write to CSV file Succeeded!!!");
//    	}
//    	catch(Exception ee)
//    	{
//    		ee.printStackTrace();
//    	}
//    	finally
//    	{
//    		try
//    		{
//    			fileWriter.close();
//    		}
//    		catch(IOException ie)
//    		{
//    			System.out.println("Error occured while closing the fileWriter");
//    			ie.printStackTrace();
//    		}
//    	}
//    }
//}
}
