package de.verbund.sv.netz;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Befehl implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String befehl;
	private Map<String, Object> parameter = new HashMap<>();
	
	public String getBefehl() {
		return befehl;
	}
	public void setBefehl(String befehl) {
		this.befehl = befehl;
	}
	public Map<String, Object> getParameter() {
		return parameter;
	}
	public void setParameter(Map<String, Object> parameter) {
		this.parameter = parameter;
	}

}
