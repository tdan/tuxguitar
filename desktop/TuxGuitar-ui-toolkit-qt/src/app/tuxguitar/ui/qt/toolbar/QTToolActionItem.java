package app.tuxguitar.ui.qt.toolbar;

import app.tuxguitar.ui.event.UISelectionListener;
import app.tuxguitar.ui.qt.event.QTSelectionListenerManager;
import app.tuxguitar.ui.toolbar.UIToolActionItem;
import io.qt.widgets.QToolButton;

public class QTToolActionItem extends QTToolAbstractButtonItem<QToolButton> implements UIToolActionItem {

	private QTSelectionListenerManager selectionListener;

	public QTToolActionItem(QTToolBar parent) {
		super(new QToolButton(parent.getControl()), parent);

		this.selectionListener = new QTSelectionListenerManager(this);
	}

	public void addSelectionListener(UISelectionListener listener) {
		if( this.selectionListener.isEmpty() ) {
			this.getControl().clicked.connect(this.selectionListener, QTSelectionListenerManager.SIGNAL_METHOD);
		}
		this.selectionListener.addListener(listener);
	}

	public void removeSelectionListener(UISelectionListener listener) {
		this.selectionListener.removeListener(listener);
		if( this.selectionListener.isEmpty() ) {
			this.getControl().clicked.disconnect();
		}
	}
}
