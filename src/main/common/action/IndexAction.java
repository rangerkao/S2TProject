package main.common.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

public class IndexAction extends BaseAction{ 

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String index() {
        return SUCCESS;
    }
}
