package request;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublisher;
import java.net.http.HttpRequest.BodyPublishers;

public class AirtableAPIRequest extends Request{
	
	private String BASE_ID; //"appXDA4TnCSLcxrFd";
	private String TABLE_ID;
	private String memberTableId;
	private String channelTableId;
	private String URL; 
	private int option;
	private String API_KEY; 
	//= "pata7gpoK9I7TewmW.49814cc55ff1155354cb30fb6725f677d5e2a3ff841f1b90ea122b751df195e0";

	public AirtableAPIRequest() {}

	@Override
	public HttpRequest postRequest(String bodyString) {
		this.setTABLE_ID();//"tblUZT1lNTDEcDqGi"; // Member table
							//"tbl009fldxGxT9Ubz"; // Channel table
		URL = "https://api.airtable.com/v0/" + BASE_ID + "/" + TABLE_ID;
		
		BodyPublisher requestBody = BodyPublishers.ofString(bodyString);
		
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(URL)) // to be modified
				.POST(requestBody)
				.header("Authorization", "Bearer " + API_KEY)
				.header("Content-Type", "application/json")
				.build();
		return request;
	}

	@Override
	public HttpRequest getRequest() {		
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(URL))
				.GET()
				.header("Authorization", "Bearer " + API_KEY)
				.header("Content-Type", "application/json")
				.build();
		return request;
	}

	public void setBASE_ID(String bASE_ID) throws IllegalArgumentException{
		if (bASE_ID.substring(0, 3).equals("app") ) {
			BASE_ID = bASE_ID;
		} else {
			throw new IllegalArgumentException("Error");
		}
		
	}

	public void setTABLE_ID(){
		switch(option) {
			case 1: // members
				this.TABLE_ID = this.memberTableId;
			case 2: // channels
				this.TABLE_ID = this.channelTableId;			
		}
	}
	
	public void setMemberTableId(String memberTableId) throws IllegalArgumentException {
		if (memberTableId.substring(0, 3).equals("tbl")) {
			this.memberTableId = memberTableId;
		} else {
				throw new IllegalArgumentException("Error");			
		}
	}
	
	public void setChannelTableId(String channelTableId) throws IllegalArgumentException {
		if (channelTableId.substring(0, 3).equals("tbl")) {
			this.channelTableId = channelTableId;
		} else {
				throw new IllegalArgumentException("Error");			
		}
	}

	public void setAPI_KEY(String aPI_KEY) {
		API_KEY = aPI_KEY;
	}
	
	public void setOption(int option) {
		this.option = option;
	}

}
