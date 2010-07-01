package com.nokia.carbide.cpp.internal.api.sdk.sbsv2;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.helpers.DefaultHandler;

import com.nokia.carbide.cpp.sdk.core.ISBSv2ConfigQueryData;
import com.nokia.carbide.cpp.sdk.core.SDKCorePlugin;
import com.nokia.cpp.internal.api.utils.core.Logging;

public class SBSv2ConfigQueryData implements ISBSv2ConfigQueryData {

	// Raptor config query data
	private Map<String, String> buildMacros = new HashMap<String, String>(); // cpp preprocessor macros
	private Map<String, String> metaDataMacros = new HashMap<String, String>();  // macros to parse the INF/MMPs files (these do not contain values)
	private List<String> metaDataIncludes = new ArrayList<String>();
	private String buildPrefix;
	private String metaDataVariantHRH;
	private String meaning;
	private String outputPathString;
	private String configurationErrorMessage = null;

	public SBSv2ConfigQueryData(String meaning, String queryResult) {
		this.meaning = meaning;
		parseQueryConfigResults(queryResult);
	}

	public String getBuildPrefix() {
		return buildPrefix;
	}

	public Map<String, String> getBuildMacros() {
		return buildMacros;
	}

	public String getConfigurationErrorMessage() {
		return configurationErrorMessage;
	}

	public Map<String, String> getMetaDataMacros() {
		return metaDataMacros;
	}

	public List<String> getMetaDataIncludes() {
		return metaDataIncludes;
	}

	public String getMetaDataVariantHRH() {
		return metaDataVariantHRH;
	}

	public String getOutputPathString() {
		return outputPathString;
	}

	private void parseQueryConfigResults(String queryResult) {
		try {
    		Element root = null;
    		DocumentBuilder parser = DocumentBuilderFactory.newInstance().newDocumentBuilder();
    		parser.setErrorHandler(new DefaultHandler());
    		
    		StringReader reader = new StringReader( queryResult );
    		InputSource inputSource = new InputSource( reader );
    		root = parser.parse(inputSource).getDocumentElement();
    		
    		NodeList children = root.getChildNodes();
    		for (int i=0; i< children.getLength(); i++) {
    			Node configNode = children.item(i);
    			if (configNode.getNodeName().equals("config")){
    				NamedNodeMap aliasAttribs = configNode.getAttributes();
    				String dottedName = aliasAttribs.getNamedItem("meaning").getNodeValue();
    				if (!dottedName.equalsIgnoreCase(meaning)){
    					continue;
    				}
    				if (configNode.getTextContent() != null&& configNode.getTextContent().length() > 0){
    					// The config failed, likely due to envrionment set up issue.
    					// Save the error message
    					configurationErrorMessage = configNode.getTextContent();
    					break;
    				}
    				
    				String outputpath = aliasAttribs.getNamedItem("outputpath").getNodeValue();
    				if (outputpath != null){
    					outputPathString = outputpath;
    				}
    				
    				NodeList configChillens = configNode.getChildNodes();
    				for (int ii = 0; ii < configChillens.getLength(); ii++){
    					Node dataNode = configChillens.item(ii);
    					if (dataNode.getNodeName().equals("metadata")){
    	    				// get <metadata>
    						NodeList metaDataChillens = dataNode.getChildNodes();
    						for (int iii = 0; iii < metaDataChillens.getLength(); iii++){
    							Node metaChild = metaDataChillens.item(iii);
    							NamedNodeMap attribs = metaChild.getAttributes();
    							try {
									if (metaChild.getNodeName().equals("macro")){
										String name = attribs.getNamedItem("name").getNodeValue();
										String value = "";
										Node valueNode = attribs.getNamedItem("value");
										if (valueNode != null) {
											value = valueNode.getNodeValue();
										}
										metaDataMacros.put(name, value);
									} else if (metaChild.getNodeName().equals("include")){
										String path = attribs.getNamedItem("path").getNodeValue();
										metaDataIncludes.add(path);
									} else if (metaChild.getNodeName().equals("preinclude")){
										metaDataVariantHRH = attribs.getNamedItem("file").getNodeValue();
									}
								} catch (Exception e) {
									// skip it
									e.printStackTrace();
								}
    						}
    					} else if (dataNode.getNodeName().equals("build")){
    	    				// get <build>
    						NodeList buildChillens = dataNode.getChildNodes();
    						for (int iii = 0; iii < buildChillens.getLength(); iii++){
    							Node buildChild = buildChillens.item(iii);
    							NamedNodeMap attribs = buildChild.getAttributes();
    							try {
									if (buildChild.getNodeName().equals("macro")){
										String name = attribs.getNamedItem("name").getNodeValue();
										String value = "";
										Node valueNode = attribs.getNamedItem("value");
										if (valueNode != null) {
											value = valueNode.getNodeValue();
										}
										buildMacros.put(name, value);
									} else if (buildChild.getNodeName().equals("preinclude")){
										buildPrefix = attribs.getNamedItem("file").getNodeValue();
									}
								} catch (Exception e) {
									// skip it
									e.printStackTrace();
								}
    						}
    					}
    				}
    				
    				break;
    			}
    		}
    		
    	} catch (Exception e) {
    		e.printStackTrace();
    		Logging.log(SDKCorePlugin.getDefault(), Logging.newStatus(SDKCorePlugin.getDefault(), e));
    	}
	}

}
