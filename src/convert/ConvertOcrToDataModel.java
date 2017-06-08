package convert;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import database.DataModel;

public enum ConvertOcrToDataModel {
	INSTANCE;
	private static final String POLISH_CHAR = "[A-Za-z¿Ÿæñó³ê¹œ¯Æ¥ŒÊ£ÓÑ]";
	private static final Pattern NUMBER_PATTERN = Pattern.compile("(\\d+\\s*)+");
	private static final Pattern CITY_PATTERN = Pattern.compile("\\d\\d-\\d\\d\\d\\s*" + POLISH_CHAR + "+");
	private static final Pattern STREET_PATTERN = Pattern.compile("(ul|al)\\.\\s*" + POLISH_CHAR + "+\\s*\\d+(/\\d+)?");
	private static final Pattern WEBSITE_PATTERN = Pattern.compile("(www\\.)?.*(pl|com)");
	private static final Pattern EMAIL_PATTERN = Pattern.compile("(\\w*\\d*)+@.+(pl|com)");
	
	private static final String SEPARATOR = "; ";
	
	public DataModel convert(String ocr){
		DataModel data = new DataModel();
		
		String ocrInLines[] = ocr.split("\\r?\\n");
		data.setName(ocrInLines[0]);
		ocrInLines[0] = "";
		for(String line : ocrInLines){
			String temp = trySetNumber(data, line);
			temp = trySetCity(data, temp);
			temp = trySetStreet(data, temp);
			temp = trySetEmail(data, temp);
			temp = trySetWebsite(data, temp);
			data.setAdditionalInfos(data.getAdditionalInfos() + temp + "\n");
		}
		
		
		return data;
	}
	
	private String trySetEmail(DataModel data, String line){
		Matcher email = EMAIL_PATTERN.matcher(line);
		String temp = "";
		while(email.find()){
			temp += email.group();
		}
		
		if(temp.length() >= 6){
			if(data.getEmails() != null){
				data.setEmails(data.getEmails() + SEPARATOR + temp);
			}
			else{
				data.setEmails(temp);
			}
			
			return line.replace(temp, "");
		}
		
		return line;
	}
	
	private String trySetWebsite(DataModel data, String line){
		Matcher website = WEBSITE_PATTERN.matcher(line);
		String temp = "";
		while(website.find()){
			temp += website.group();
		}
		
		if(temp.length() >= 3){
			if(data.getWebsites() != null){
				data.setWebsites(data.getWebsites() + SEPARATOR + temp);
			}
			else{
				data.setWebsites(temp);
			}
			
			return line.replace(temp, "");
		}
		
		return line;
	}
	
	private String trySetStreet(DataModel data, String line){
		Matcher street = STREET_PATTERN.matcher(line);
		String temp = "";
		while(street.find()){
			temp += street.group();
		}
		
		if(temp.length() > 0){
			data.setAddressStreet(temp);
			return line.replace(temp, "");
		}
		
		return line;
	}
	
	private String trySetCity(DataModel data, String line){
		Matcher city = CITY_PATTERN.matcher(line);
		String temp = "";
		while(city.find()){
			temp += city.group();
		}
		
		if(temp.length() > 0){
			data.setAddressCity(temp);
			return line.replace(temp, "");
		}
		
		return line;
	}
	
	private String trySetNumber(DataModel data, String line){
		Matcher number = NUMBER_PATTERN.matcher(line);
		String temp = "";
		while(number.find()){
			String moreTemp = number.group();
			if(moreTemp.length() >= 9){
				temp += moreTemp;
			}
		}
		
		if(temp.length() >= 9){
			if(data.getPhoneNumbers() != null){
				data.setPhoneNumbers(data.getPhoneNumbers() + SEPARATOR + temp);
			}
			else{
				data.setPhoneNumbers(temp);
			}
			
			return line.replace(temp, "");
		}
		
		return line;
	}

}
