package com.nokia.carbide.internal.discovery.ui.view;

import java.net.MalformedURLException;
import java.net.URL;

import com.nokia.carbide.internal.discovery.ui.extension.AbstractRSSPortalPageLayer;

public class CreatingCarbidePage extends AbstractRSSPortalPageLayer {

	@Override
	protected URL getURL() {
		try {
			return new URL("http://creatingcarbide.blogspot.com/feeds/posts/default?alt=rss"); //$NON-NLS-1$
		} catch (MalformedURLException e) {
		}
		return null;
	}
}
