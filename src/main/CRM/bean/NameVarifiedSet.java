package main.CRM.bean;

import java.util.List;

public class NameVarifiedSet {

	
	NameVarified current;
	
	List<NameVarified> history;

	public NameVarified getCurrent() {
		return current;
	}

	public void setCurrent(NameVarified current) {
		this.current = current;
	}

	public List<NameVarified> getHistory() {
		return history;
	}

	public void setHistory(List<NameVarified> history) {
		this.history = history;
	}
	
	
}
