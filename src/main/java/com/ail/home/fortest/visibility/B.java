package com.ail.home.fortest.visibility;

import com.ail.home.fortest.visibility.pac.A;

class B extends A {

	void b() {
		aMethod();
		A a = new A();
//		a.aMethod();
	}

}