package app.tuxguitar.app.system.variables;

import app.tuxguitar.app.document.TGDocumentFileManager;
import app.tuxguitar.util.TGContext;

public class TGVarFilePath {

	public static final String NAME = "filepath";

	public static final String DEFAULT_VALUE = new String();

	private TGContext context;

	public TGVarFilePath(TGContext context) {
		this.context = context;
	}

	public String getValue() {
		return TGDocumentFileManager.getInstance(this.context).getCurrentFilePath();
	}

	public String toString() {
		String value = this.getValue();

		return (value != null ? value : DEFAULT_VALUE);
	}
}
