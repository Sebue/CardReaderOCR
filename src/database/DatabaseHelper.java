package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper {
	private final static String ID = "ID";
	private final static String NAME = "NAME";
	private final static String ADDRESS_STREET = "ADDRESS_STREET";
	private final static String ADDRESS_CITY = "ADDRESS_CITY";
	private final static String PHONE_NUMBERS = "PHONE_NUMBERS";
	private final static String EMAILS = "EMAILS";
	private final static String WEBSITES = "WEBSITES";
	private final static String ADDITIONAL_INFOS = "ADDITIONAL_INFOS";
	
	private final static String TABLE_NAME = "ocrInfo";
	
    private final static String tabelaSQL = "CREATE TABLE " + TABLE_NAME + " ("
            + ID + " INT PRIMARY KEY NOT NULL, "
            + NAME + " TEXT NOT NULL, "
            + ADDRESS_STREET + " TEXT, "
            + ADDRESS_CITY + " TEXT, "
            + PHONE_NUMBERS + " TEXT, "
            + EMAILS + " TEXT, "
            + WEBSITES + " TEXT, "
            + ADDITIONAL_INFOS + " TEXT)";
    
	public static void createTable(Connection connection) {
	        Statement stat = null;
	        try {
	            stat = connection.createStatement();
	            stat.executeUpdate(tabelaSQL);
	            stat.close();
	            connection.close();
	        } catch (SQLException e) {
	            System.out.println("Can't create database: " + e.getMessage());
	        }
	        System.out.println("Create table in database!");
	    }
	
	public static List<DataModel> selectAllData(Connection connection){
		Statement stat = null;
		List<DataModel> dataList = new ArrayList<>();
		try{
			stat = connection.createStatement();
			String selectSQL = "SELECT * FROM " + TABLE_NAME + ";";
            ResultSet results = stat.executeQuery(selectSQL);
            while(results.next()){
            	DataModel data = new DataModel();
            	data.setId(results.getInt(ID));
            	data.setName(results.getString(NAME));
            	data.setAddressStreet(results.getString(ADDRESS_STREET));
            	data.setAddressCity(results.getString(ADDRESS_CITY));
            	data.setPhoneNumbers(results.getString(PHONE_NUMBERS));
            	data.setEmails(results.getString(EMAILS));
            	data.setWebsites(results.getString(WEBSITES));
            	data.setAdditionalInfos(results.getString(ADDITIONAL_INFOS));
            	dataList.add(data);
            }
		}
		catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("Database has " + dataList.size() + " entity.");
		return dataList;
	}
	
	public static void addData(Connection connection, DataModel data){
		Statement stat = null;
		try{
			stat = connection.createStatement();
			String addSQL = "INSERT INTO " + TABLE_NAME + "(" 
					+ ID + "," + NAME + "," + ADDRESS_STREET + "," + ADDRESS_CITY + "," + PHONE_NUMBERS + "," + EMAILS + "," + WEBSITES + "," + ADDITIONAL_INFOS + ")"
					+ "VALUES ("
					+ data.getId() + ","
					+ "'" + data.getName() + "',"
					+ "'" + data.getAddressStreet() + "',"
					+ "'" + data.getAddressCity() + "',"
					+ "'" + data.getPhoneNumbers() + "',"
					+ "'" + data.getEmails() + "',"
					+ "'" + data.getWebsites() + "',"
					+ "'" + data.getAdditionalInfos() + "')"
					+ ";";
            stat.executeUpdate(addSQL);
    		System.out.println("Data: " + data.getName() + " added.");
		}
		catch(Exception e){
			System.out.println("Data: " + data.getName() + " NOT added.");
			e.printStackTrace();
		}
	}
}
