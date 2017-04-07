package com.ail.home.ejb;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

@Stateless
@LocalBean
public class FacadeBean {

	@EJB
	private ServiceBean sb;

	@TransactionAttribute(TransactionAttributeType.NEVER)
	public void callSeviceBean() {
		// Do Something...
		// A: what is provided by CMT at this point?
		// B: what is different when using TransactionAttributeType.NOT_SUPPORTED?
		sb.method1();
	}

}

//////////////
