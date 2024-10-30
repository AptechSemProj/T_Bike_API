package se.pj.tbike.api.util;

public class ResponseConfiguration {

	private ResponseTemplate template = new DefaultResponseTemplate();

	public ResponseConfiguration applyTemplate(ResponseTemplate template) {
		this.template = template;
		return this;
	}

	ResponseTemplate getTemplate() {
		return template;
	}

}
