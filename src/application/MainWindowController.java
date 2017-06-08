package application;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ResourceBundle;

import convert.ConvertOcrToDataModel;
import database.DataModel;
import database.DatabaseHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import net.sourceforge.tess4j.util.LoadLibs;

public class MainWindowController implements Initializable {
	private static String DATABASE_NAME = "ocr";
	File wynik;
	File selectedFile;
	DataModel dataModel;
	@FXML
	Button save;
	@FXML
	ImageView inputImage;
	@FXML
	TextField name;
	@FXML
	TextField street;
	@FXML
	TextField city;
	@FXML
	TextField phone;
	@FXML
	TextField emails;
	@FXML
	TextField websites;
	@FXML
	TextArea additional;
	
    @FXML
    private void handleGenerate(ActionEvent event) {
        ITesseract instance = new Tesseract();
        File tessDataFolder = LoadLibs.extractTessResources("plTessData");
        System.out.println(tessDataFolder.getAbsolutePath());
        instance.setDatapath(tessDataFolder.getParent());
        instance.setLanguage("pol");

        try {
        	String result = instance.doOCR(selectedFile);
            System.out.println(result);
            dataModel = ConvertOcrToDataModel.INSTANCE.convert(result);
            setDateOnWindow();
        } catch (TesseractException e) {
            System.err.println(e.getMessage());
        }
        //CO Z DB?
    }
    
    @FXML
    private void handlePath(ActionEvent event) {
    	FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Graphics files (*.gif, *.jpg, *.png)", "*.png", "*.jpg", "*.gif");
        fileChooser.getExtensionFilters().add(extFilter);
        fileChooser.setTitle("Open Graphic File");
        selectedFile = fileChooser.showOpenDialog(Main.getStage());

        if (selectedFile != null) {
        	Image img = new Image(selectedFile.toURI().toString());
        	inputImage.setImage(img);
        	setDisable(true);
        }
    }
    
    @FXML
    private void handleSave(ActionEvent event) {
    	dataModel.setName(name.getText());
    	dataModel.setAddressStreet(street.getText());
    	dataModel.setAddressCity(city.getText());
    	dataModel.setPhoneNumbers(phone.getText());
    	dataModel.setEmails(emails.getText());
    	dataModel.setWebsites(websites.getText());
    	dataModel.setAdditionalInfos(additional.getText());
    	dataModel.setId(DatabaseHelper.selectAllData(getConnection()).size());
    	DatabaseHelper.addData(getConnection(), dataModel);
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Done");
		alert.setHeaderText("Data saved successfully saved in database.");
		alert.showAndWait();
        setDisable(true);
    }
    
    protected void setDateOnWindow(){
    	name.setText(dataModel.getName());
    	street.setText(dataModel.getAddressStreet());
    	city.setText(dataModel.getAddressCity());
    	phone.setText(dataModel.getPhoneNumbers());
    	emails.setText(dataModel.getEmails());
    	websites.setText(dataModel.getWebsites());
    	additional.setText(dataModel.getAdditionalInfos());
        setDisable(false);
    }
	
	public static Connection getConnection(){
		Connection connection = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + DATABASE_NAME + ".db");
		} catch (Exception e) {
			e.printStackTrace();
		}
        return connection;
	}
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	setDisable(true);
    }    
    
    
    protected void setDisable(boolean isDisable){
    	name.setDisable(isDisable);
    	street.setDisable(isDisable);
    	city.setDisable(isDisable);
    	phone.setDisable(isDisable);
    	emails.setDisable(isDisable);
    	websites.setDisable(isDisable);
    	additional.setDisable(isDisable);
    	save.setDisable(isDisable);
    }
}
