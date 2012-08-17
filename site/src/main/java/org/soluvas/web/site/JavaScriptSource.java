package org.soluvas.web.site;

import java.io.Serializable;

public interface JavaScriptSource extends Serializable, Comparable<JavaScriptSource> {

	String getBody();

	void setBody(String body);
	
	String getScript();

}