package app.tuxguitar.ui.jfx.widget;

import app.tuxguitar.ui.widget.UIScrollBar;

import javafx.geometry.Orientation;
import javafx.scene.layout.Region;

public class JFXScrollBar extends JFXAbstractScrollBar implements UIScrollBar {

	public JFXScrollBar(JFXContainer<? extends Region> parent, Orientation orientation) {
		super(parent, orientation);
	}
}
