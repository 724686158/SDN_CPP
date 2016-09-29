package Graph;

public class Link {
	String source;
	String destination;
	String linkLabel;
	String linkNote;
	String linkType;
	//delay
	int weigth;
	
	
	
	public Link(String source, String destination, String linkLabel,
			String linkNote, String linkType, int weigth) {
		super();
		this.source = source;
		this.destination = destination;
		this.linkLabel = linkLabel;
		this.linkNote = linkNote;
		this.linkType = linkType;
		this.weigth = weigth;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public String getLinkLabel() {
		return linkLabel;
	}
	public void setLinkLabel(String linkLabel) {
		this.linkLabel = linkLabel;
	}
	public String getLinkNote() {
		return linkNote;
	}
	public void setLinkNote(String linkNote) {
		this.linkNote = linkNote;
	}
	public String getLinkType() {
		return linkType;
	}
	public void setLinkType(String linkType) {
		this.linkType = linkType;
	}
	public int getWeigth() {
		return weigth;
	}
	public void setWeigth(int weigth) {
		this.weigth = weigth;
	}
	
}
