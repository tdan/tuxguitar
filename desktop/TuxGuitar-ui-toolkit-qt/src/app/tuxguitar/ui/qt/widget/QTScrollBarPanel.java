package app.tuxguitar.ui.qt.widget;

import app.tuxguitar.ui.resource.UIRectangle;
import app.tuxguitar.ui.widget.UIScrollBar;
import app.tuxguitar.ui.widget.UIScrollBarPanel;
import io.qt.core.QMargins;
import io.qt.core.Qt.ScrollBarPolicy;
import io.qt.widgets.QAbstractScrollArea;
import io.qt.widgets.QWidget;

public class QTScrollBarPanel extends QTAbstractPanel<QAbstractScrollArea> implements UIScrollBarPanel {

	private QTScrollBar vScrollBar;
	private QTScrollBar hScrollBar;

	public QTScrollBarPanel(QTContainer parent, boolean vScroll, boolean hScroll, boolean bordered) {
		super(new QAbstractScrollArea(parent.getContainerControl()), parent, bordered);

		this.getControl().setViewport(new QWidget());

		if( vScroll ) {
			this.getControl().setVerticalScrollBarPolicy(ScrollBarPolicy.ScrollBarAlwaysOn);
			this.vScrollBar = new QTScrollBar(this.getControl().verticalScrollBar(), null);
		}
		if( hScroll ) {
			this.getControl().setHorizontalScrollBarPolicy(ScrollBarPolicy.ScrollBarAlwaysOn);
			this.hScrollBar = new QTScrollBar(this.getControl().horizontalScrollBar(), null);
		}
	}

	@Override
	public QWidget getContainerControl() {
		return this.getControl().viewport();
	}

	@Override
	public UIScrollBar getHScroll() {
		return this.hScrollBar;
	}

	@Override
	public UIScrollBar getVScroll() {
		return this.vScrollBar;
	}

	public void computeMargins() {
		super.computeMargins();

		QMargins margins = this.getControl().contentsMargins();
		QMargins containerMargins = new QMargins(margins.left(), margins.top(), margins.right(), margins.bottom());

		if( this.vScrollBar != null ) {
			containerMargins.setRight(containerMargins.right() + this.vScrollBar.getControl().sizeHint().width());
		}
		if( this.hScrollBar != null ) {
			containerMargins.setBottom(containerMargins.bottom() + this.hScrollBar.getControl().sizeHint().height());
		}
		this.setContainerMargins(containerMargins);
	}

	public void setBounds(UIRectangle bounds) {
		QMargins margins = this.getContainerMargins();

		int viewPortWidth = (Math.round(bounds.getWidth()) - (margins.left() + margins.right()));
		int viewPortHeight = (Math.round(bounds.getHeight()) - (margins.top() + margins.bottom()));

		this.getControl().viewport().resize(viewPortWidth, viewPortHeight);

		super.setBounds(bounds);
	}
}
