package test

class MyClass extends wrap.BaseClass {

	def unwrapped(String stringParam) {
		handleWrapped({ print stringParam })
	}

	@wrap.Wrap
	def wrapped(String stringParam) {
		print stringParam
	}
}