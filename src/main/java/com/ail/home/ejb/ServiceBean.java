package com.ail.home.ejb;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

@Stateless
@LocalBean
public class ServiceBean {

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void method1() {
		// Do Something...
		// C: what is provided by CMT at this point?
		method2();
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void method2() {
		// Do Something...
		// D: what is provided by CMT at this point?
	}
}