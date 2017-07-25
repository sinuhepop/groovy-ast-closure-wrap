package wrap;

import groovy.lang.Closure;

public class BaseClass {

	public Object handleWrapped(Closure<?> closure) {
		return closure.call();
	}

}
