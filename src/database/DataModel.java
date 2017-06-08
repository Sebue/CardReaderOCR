package database;

public class DataModel {
	int id;
	String name;
	String addressStreet;
	String addressCity;
	String phoneNumbers;
	String emails;
	String websites;
	String additionalInfos;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddressStreet() {
		return addressStreet;
	}
	public void setAddressStreet(String addressStreet) {
		this.addressStreet = addressStreet;
	}
	public String getAddressCity() {
		return addressCity;
	}
	public void setAddressCity(String addressCity) {
		this.addressCity = addressCity;
	}
	public String getPhoneNumbers() {
		return phoneNumbers;
	}
	public void setPhoneNumbers(String phoneNumbers) {
		this.phoneNumbers = phoneNumbers;
	}
	public String getEmails() {
		return emails;
	}
	public void setEmails(String emails) {
		this.emails = emails;
	}
	public String getWebsites() {
		return websites;
	}
	public void setWebsites(String websites) {
		this.websites = websites;
	}
	public String getAdditionalInfos() {
		return additionalInfos;
	}
	public void setAdditionalInfos(String additionalInfos) {
		this.additionalInfos = additionalInfos;
	}
}
