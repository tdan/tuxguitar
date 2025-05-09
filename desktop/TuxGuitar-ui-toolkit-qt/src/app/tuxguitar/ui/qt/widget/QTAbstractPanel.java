package app.tuxguitar.ui.qt.widget;

import app.tuxguitar.ui.widget.UIPanel;
import io.qt.widgets.QFrame;
import io.qt.widgets.QFrame.Shape;

public abstract class QTAbstractPanel<T extends QFrame> extends QTLayoutContainer<T> implements UIPanel {

	public QTAbstractPanel(T control, QTContainer parent, boolean bordered) {
		super(control, parent);

		this.getControl().setAutoFillBackground(true);
		this.getControl().setFrameShape(bordered ? Shape.StyledPanel : Shape.NoFrame);
	}
}
