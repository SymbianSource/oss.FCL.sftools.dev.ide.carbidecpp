function Layout() {

}

Layout.prototype.layout = function(instance, laf) {
	// do nothing
}

Layout.prototype.getPreferredSize = function(instance, laf, wHint, hHint) {
	return new Point(wHint, hHint);
}

