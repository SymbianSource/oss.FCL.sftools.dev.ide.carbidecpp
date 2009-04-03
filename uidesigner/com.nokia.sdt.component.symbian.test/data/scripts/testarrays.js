function Test() {
}

Test.prototype.getStuff = function(idx) {
	if (idx == 0) {
		return ["foo", "bar"];
	} else if (idx == 1) {
		return [23, 45];
	} else if (idx == 2) {
		var lst = new java.util.ArrayList();
		lst.add(23);
		lst.add(45);
		return lst;
	} else if (idx == 3) {
		return "foo";
	}
}

