package cho.carbon.hc.web.dto.xml;

import cho.carbon.bond.utils.xml.XmlNode;

public class XMLRequest {
	private final XmlNode root;
	public XMLRequest(XmlNode root) {
		this.root = root;
	}
	public XmlNode getRoot() {
		return root;
	}
	
	public String getElementValue(String tagName){
		XmlNode node = root.getFirstElement(tagName);
		return node.getText();
	}
	
	@Override
	public String toString() {
		return root.asXML();
	}
	
}
