package cho.carbon.hc.web.dto.xml;

import cho.carbon.bond.utils.xml.Dom4jNode;
import cho.carbon.bond.utils.xml.XmlNode;


public class XMLResponse {
	private XmlNode root;
	
	public XMLResponse() {
		this(new Dom4jNode());
	}
	public XMLResponse(XmlNode root) {
		this.root = root;
	}
	public XmlNode getRoot() {
		return root;
	}
	
	public String getElementValue(String tagName){
		XmlNode node = root.getFirstElement(tagName);
		return node.getText();
	}
	
	public void putElement(String tagName, String cdata){
		XmlNode ele = root.getFirstElement(tagName);
		if(ele == null){
			ele = root.addNode(tagName);
		}
		ele.setCDATA(cdata);
	}
	
	@Override
	public String toString() {
		return root.asXML();
	}
	
	
}
