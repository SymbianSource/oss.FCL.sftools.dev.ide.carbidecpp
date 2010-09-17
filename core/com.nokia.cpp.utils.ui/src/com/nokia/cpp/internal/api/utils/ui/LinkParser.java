package com.nokia.cpp.internal.api.utils.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.jface.resource.JFaceColors;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.StyledString.Styler;
import org.eclipse.swt.graphics.TextStyle;
import org.eclipse.swt.widgets.Display;

public class LinkParser {

	public static class Element {
		private String text;

		public Element(String text) {
			this.text = text;
		}
		
		public String getText() {
			return text;
		}
	}
	
	public static class LinkElement extends Element {
		private String href;
		
		public LinkElement(String href, String text) {
			super(text);
			this.href = href;
		}
		
		public String getHref() {
			return href;
		}
	}
	
	private static final Pattern HREF_PATTERN = Pattern.compile("<a href=\"([^\"]*)\">(.*?)</a>", Pattern.CASE_INSENSITIVE);
	private static Styler hyperLinkStyler = new Styler() {
		@Override
		public void applyStyles(TextStyle textStyle) {
			textStyle.foreground = JFaceColors.getHyperlinkText(Display.getDefault());
			textStyle.underline = true;
		}
	};

	public static List<Element> parseText(String text) {
		List<Element> elements = new ArrayList<Element>();
		if (text != null) {
			Matcher m = HREF_PATTERN.matcher(text);
			int start = 0;
			int end;
			while (m.find()) {
				end = m.start();
				if (start <= end)
					elements.add(new Element(text.substring(start, end)));
				elements.add(new LinkElement(m.group(1), m.group(2)));
				start = m.end();
			}
			end = text.length();
			if (start <= end)
				elements.add(new Element(text.substring(start, end)));
		}
		return elements;
	}

	public static StyledString getStyledString(List<Element> elements) {
		StyledString styledString = new StyledString();
		for (Element element : elements) {
			styledString.append(element.getText(), element instanceof LinkElement ? hyperLinkStyler : null);
		}
		return styledString;
	}

}