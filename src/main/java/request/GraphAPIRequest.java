package request;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublisher;
import java.net.http.HttpRequest.BodyPublishers;

public class GraphAPIRequest extends Request{
	
	private String ACCESS_TOKEN;// = "eyJ0eXAiOiJKV1QiLCJub25jZSI6InJWYkRlUVhoXzJ0Y2xmR3hPY1ZJaGdlbjBhNGtCMjd4VUUzZkJNVzJZRVEiLCJhbGciOiJSUzI1NiIsIng1dCI6IkwxS2ZLRklfam5YYndXYzIyeFp4dzFzVUhIMCIsImtpZCI6IkwxS2ZLRklfam5YYndXYzIyeFp4dzFzVUhIMCJ9.eyJhdWQiOiIwMDAwMDAwMy0wMDAwLTAwMDAtYzAwMC0wMDAwMDAwMDAwMDAiLCJpc3MiOiJodHRwczovL3N0cy53aW5kb3dzLm5ldC85ZDYwMjNiNC1mNGNlLTRkNjYtOTY5MC0xYThjMWY0ZjdkYTYvIiwiaWF0IjoxNzE3NTIwNTI3LCJuYmYiOjE3MTc1MjA1MjcsImV4cCI6MTcxNzYwNzIyNywiYWNjdCI6MCwiYWNyIjoiMSIsImFpbyI6IkFWUUFxLzhXQUFBQUNtaDViWDE5RnFnOXNQL2FmalB2ai9CbHc0M25uais1WE5kcHVDemx6RElmaG9VTEF1K2k0eFlsNDMydk8ydmRsL3k1MVpHVmV0dmt4b1cvdERvdThkNEN1NkhjZk9iMWlkeWl2Z2g0dCtnPSIsImFtciI6WyJwd2QiLCJtZmEiXSwiYXBwX2Rpc3BsYXluYW1lIjoiR3JhcGggRXhwbG9yZXIiLCJhcHBpZCI6ImRlOGJjOGI1LWQ5ZjktNDhiMS1hOGFkLWI3NDhkYTcyNTA2NCIsImFwcGlkYWNyIjoiMCIsImZhbWlseV9uYW1lIjoiVHJ1b25nIiwiZ2l2ZW5fbmFtZSI6IlRoYW8gTmd1eWVuIiwiaWR0eXAiOiJ1c2VyIiwiaXBhZGRyIjoiMjQwNTo0ODAzOmZlMzE6NjVhMDpkZTE6MWRhMzpiNmY6YjhlYSIsIm5hbWUiOiJUaGFvIE5ndXllbiBUcnVvbmciLCJvaWQiOiJmYWM0MmRmYi0zNjY2LTQwNDktOTc3Yy1iNmMwZDM5MmIzN2YiLCJwbGF0ZiI6IjMiLCJwdWlkIjoiMTAwMzIwMDM4NkRFODVFRiIsInJoIjoiMC5BY1lBdENOZ25jNzBaazJXa0JxTUgwOTlwZ01BQUFBQUFBQUF3QUFBQUFBQUFBREdBQkUuIiwic2NwIjoiQ2FsZW5kYXJzLlJlYWRXcml0ZSBDaGFubmVsLlJlYWRCYXNpYy5BbGwgQ2hhdC5SZWFkIENoYXQuUmVhZEJhc2ljIENvbnRhY3RzLlJlYWRXcml0ZSBEZXZpY2VNYW5hZ2VtZW50UkJBQy5SZWFkLkFsbCBEZXZpY2VNYW5hZ2VtZW50U2VydmljZUNvbmZpZy5SZWFkLkFsbCBEaXJlY3RvcnkuUmVhZFdyaXRlLkFsbCBGaWxlcy5SZWFkV3JpdGUuQWxsIEdyb3VwLlJlYWRXcml0ZS5BbGwgR3JvdXBNZW1iZXIuUmVhZFdyaXRlLkFsbCBJZGVudGl0eVJpc2tFdmVudC5SZWFkLkFsbCBNYWlsLlJlYWQgTWFpbC5SZWFkV3JpdGUgTWFpbGJveFNldHRpbmdzLlJlYWRXcml0ZSBOb3Rlcy5SZWFkV3JpdGUuQWxsIG9wZW5pZCBQZW9wbGUuUmVhZCBQbGFjZS5SZWFkIFByZXNlbmNlLlJlYWQgUHJlc2VuY2UuUmVhZC5BbGwgUHJpbnRlclNoYXJlLlJlYWRCYXNpYy5BbGwgUHJpbnRKb2IuQ3JlYXRlIFByaW50Sm9iLlJlYWRCYXNpYyBwcm9maWxlIFJlcG9ydHMuUmVhZC5BbGwgU2l0ZXMuUmVhZFdyaXRlLkFsbCBUYXNrcy5SZWFkV3JpdGUgVGVhbU1lbWJlci5SZWFkLkFsbCBUZWFtTWVtYmVyLlJlYWRXcml0ZS5BbGwgVGVhbXdvcmtUYWcuUmVhZCBUZWFtd29ya1RhZy5SZWFkV3JpdGUgVXNlci5SZWFkIFVzZXIuUmVhZEJhc2ljLkFsbCBVc2VyLlJlYWRXcml0ZSBVc2VyLlJlYWRXcml0ZS5BbGwgZW1haWwiLCJzdWIiOiJSdlJfOXJ1ME1EYm5US3Q2VnhqRkg1Rk5wYjJ3b2FHVHd6c2ROUG9aaVF3IiwidGVuYW50X3JlZ2lvbl9zY29wZSI6IkFTIiwidGlkIjoiOWQ2MDIzYjQtZjRjZS00ZDY2LTk2OTAtMWE4YzFmNGY3ZGE2IiwidW5pcXVlX25hbWUiOiJhZG1pbnRlYW1AbWVhZG93aWxsYS5vbm1pY3Jvc29mdC5jb20iLCJ1cG4iOiJhZG1pbnRlYW1AbWVhZG93aWxsYS5vbm1pY3Jvc29mdC5jb20iLCJ1dGkiOiJtN1JzeGkzNDMwLUJORUNYeUV0bkFBIiwidmVyIjoiMS4wIiwid2lkcyI6WyI2MmU5MDM5NC02OWY1LTQyMzctOTE5MC0wMTIxNzcxNDVlMTAiLCJiNzlmYmY0ZC0zZWY5LTQ2ODktODE0My03NmIxOTRlODU1MDkiXSwieG1zX2NjIjpbIkNQMSJdLCJ4bXNfc3NtIjoiMSIsInhtc19zdCI6eyJzdWIiOiI1WkdvZHQ4YWdNWmNRcFlmeGpXaEdTSFUwTUc5Q1RGdzJZSUhqQ2h3OWNNIn0sInhtc190Y2R0IjoxNzE2NzM3MzA2fQ.oYcTkqdSkeiB18AgbvMNcY2N38ngc18THtXd5FkJbT3_crG7UTw08Y2cSbTFUuB7_58isLxbF4BW1R2GyYnt7RqxGGD1Oz7S5irFFsccjqqmHmrakLLyiP-VibSFsutHhEhk2xdMLV5TZ4z-RZZXSNMJr7uzAYimNc_nMpJeYnKP5h0ldw4dGfx7HPf-oPIvdnqpYWhr-f9--zk9Jqrr-iVh_fYmeon3AfCkIvkHSOXbdg4NUaMRJpzGWgbloiXpoJMr_Kt037CFEXqIBHXFPkC8VuZiXVqkRYjYvQhNKYWzyeUED20e8-FFApokTy0Bsy9TKQL4BZXdwbyeh5iQCQ";
	private String teamId; // = "3ea72d5f-4eb1-49d7-9695-f3b8e6f17bb1";
	private int option; // there are 2 options: get Members and get Channels
	private String URL;
	
	@Override
	public HttpRequest postRequest(String bodyString) {
		
		BodyPublisher requestBody = BodyPublishers.ofString(bodyString);
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(URL)) // to be modified
				.POST(requestBody)
				.header("Authorization", "Bearer " + ACCESS_TOKEN)
				.header("Content-Type", "application/json")
				.build();
		return request;
	}

	@Override
	public HttpRequest getRequest() {
		switch (option) {
			case 1: // get members
				URL = "https://graph.microsoft.com/v1.0/teams/" + teamId + "/members";
			case 2: // get channels
				URL = "https://graph.microsoft.com/v1.0/teams/" + teamId + "/channels";
		}
		
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(URL)) // to be modified
				.GET()
				.header("Authorization", "Bearer " + ACCESS_TOKEN)
				.header("Content-Type", "application/json")
				.build();
		return request;
	}

	public void setACCESS_TOKEN(String aCCESS_TOKEN) {
		ACCESS_TOKEN = aCCESS_TOKEN;
	}

	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}
	
	public void setOption(int option) {
		this.option = option;
	}
}
