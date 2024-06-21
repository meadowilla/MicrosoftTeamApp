package request;

import java.net.http.HttpRequest;

public abstract class Request {
		
	public abstract HttpRequest getRequest ();
	public abstract HttpRequest postRequest(String bodyString);

}
