package app.tuxguitar.cocoa.modifiedmarker;

import org.eclipse.swt.internal.cocoa.NSWindow;
import org.eclipse.swt.widgets.Shell;
import app.tuxguitar.app.TuxGuitar;
import app.tuxguitar.app.document.TGDocument;
import app.tuxguitar.app.document.TGDocumentListManager;
import app.tuxguitar.app.view.main.TGWindow;
import app.tuxguitar.editor.event.TGUpdateEvent;
import app.tuxguitar.event.TGEvent;
import app.tuxguitar.event.TGEventListener;
import app.tuxguitar.ui.swt.widget.SWTWindow;
import app.tuxguitar.util.TGContext;

public class ModifiedMarker implements TGEventListener {

	private TGContext context;
	private boolean enabled;

	public ModifiedMarker(TGContext context){
		this.context = context;
	}

	public void init() throws Throwable{
	    TuxGuitar.getInstance().getEditorManager().addUpdateListener(this);
	}

	public boolean isEnabled() {
		return this.enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;

		if (!enabled) setFrameState(false);
	}

	private void setFrameState(boolean modified) {
	    Shell shell = ((SWTWindow) TGWindow.getInstance(this.context).getWindow()).getControl();
   	    NSWindow nsWindow = shell.view.window();
        nsWindow.setDocumentEdited(modified);
	}

	private boolean isUnsavedFile() {
		TGDocument document = TGDocumentListManager.getInstance(this.context).findCurrentDocument();
		if( document != null ) {
			return document.isUnsaved();
		}
		return false;
	}

	/** From 'TGEventListener' */
	public void processEvent(TGEvent event) {
		if( this.isEnabled() && TGUpdateEvent.EVENT_TYPE.equals(event.getEventType()) ) {
		    this.setFrameState(this.isUnsavedFile());
		}
	}
}
