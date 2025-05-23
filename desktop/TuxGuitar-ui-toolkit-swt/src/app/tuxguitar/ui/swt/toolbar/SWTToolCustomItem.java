package app.tuxguitar.ui.swt.toolbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import app.tuxguitar.ui.event.UIDisposeListener;
import app.tuxguitar.ui.event.UIFocusGainedListener;
import app.tuxguitar.ui.event.UIFocusLostListener;
import app.tuxguitar.ui.event.UIKeyPressedListener;
import app.tuxguitar.ui.event.UIKeyReleasedListener;
import app.tuxguitar.ui.event.UIMouseDoubleClickListener;
import app.tuxguitar.ui.event.UIMouseDownListener;
import app.tuxguitar.ui.event.UIMouseDragListener;
import app.tuxguitar.ui.event.UIMouseEnterListener;
import app.tuxguitar.ui.event.UIMouseExitListener;
import app.tuxguitar.ui.event.UIMouseMoveListener;
import app.tuxguitar.ui.event.UIMouseUpListener;
import app.tuxguitar.ui.event.UIMouseWheelListener;
import app.tuxguitar.ui.event.UIResizeListener;
import app.tuxguitar.ui.event.UIZoomListener;
import app.tuxguitar.ui.menu.UIPopupMenu;
import app.tuxguitar.ui.resource.UIColor;
import app.tuxguitar.ui.resource.UICursor;
import app.tuxguitar.ui.resource.UIFont;
import app.tuxguitar.ui.resource.UIRectangle;
import app.tuxguitar.ui.resource.UISize;
import app.tuxguitar.ui.swt.SWTEnvironment;
import app.tuxguitar.ui.swt.widget.SWTContainer;
import app.tuxguitar.ui.swt.widget.SWTControl;
import app.tuxguitar.ui.toolbar.UIToolCustomItem;
import app.tuxguitar.ui.widget.UIControl;

public class SWTToolCustomItem extends SWTToolControl<ToolBar> implements SWTContainer<ToolBar>, UIToolCustomItem {

	private Map<String, Object> attributes;
	private ToolItem item;
	private UIControl control;

	public SWTToolCustomItem(ToolItem item, SWTToolBar parent) {
		super(parent.getControl(), parent);

		this.item = item;
		this.attributes = new HashMap<String, Object>();
	}

	public <T extends Object> void setLayoutAttribute(String key, T value){
		if( value != null ) {
			this.attributes.put(key, value);
		} else {
			this.attributes.remove(key);
		}
	}

	@SuppressWarnings("unchecked")
	public <T extends Object> T getLayoutAttribute(String key){
		if( this.attributes.containsKey(key) ) {
			return (T) this.attributes.get(key);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public void addChild(UIControl control) {
		this.control = control;
		this.item.setControl(((SWTControl<? extends Control>)this.control).getControl());
	}

	public void removeChild(UIControl uiControl) {
		this.control = null;
		this.item.setControl(null);
	}

	public List<UIControl> getChildren() {
		List<UIControl> children = new ArrayList<UIControl>();
		if( this.control != null ) {
			children.add(this.control);
		}
		return children;
	}

	public void computePackedSize() {
		Float packedWidth = this.getLayoutAttribute(PACKED_WIDTH);

		this.computePackedSize(packedWidth, null);
	}

	public void computePackedSize(Float fixedWidth, Float fixedHeight) {
		UISize packedSize = new UISize();
		if( this.control != null ) {
			this.control.computePackedSize(fixedWidth, fixedHeight);

			packedSize.setWidth(this.control.getPackedSize().getWidth());
			packedSize.setHeight(this.control.getPackedSize().getHeight());
		}
		if( fixedWidth != null ) {
			packedSize.setWidth(fixedWidth);
		}
		if( fixedHeight != null ) {
			packedSize.setHeight(fixedHeight);
		}

		Float minimumPackedWidth = this.getLayoutAttribute(MINIMUM_PACKED_WIDTH);
		Float maximumPackedWidth = this.getLayoutAttribute(MAXIMUM_PACKED_WIDTH);

		if( minimumPackedWidth != null && minimumPackedWidth > packedSize.getWidth()) {
			packedSize.setWidth(minimumPackedWidth);
		}
		if( maximumPackedWidth != null && maximumPackedWidth < packedSize.getWidth()) {
			packedSize.setWidth(maximumPackedWidth);
		}

		if( this.control != null ) {
			UISize controlPackedSize = this.control.getPackedSize();
			if( packedSize.getWidth() != controlPackedSize.getWidth() || packedSize.getHeight() != controlPackedSize.getHeight() ) {
				this.control.computePackedSize(packedSize.getWidth(), packedSize.getHeight());
			}
		}
	}

	public void setSize(int width, int height) {
		if( this.control != null ) {
			if( SWTEnvironment.getInstance().isToolItemResizeAvailable() ) {
				this.getItem().setWidth(width);
			}
			this.getItemControl().setSize(width, height);
		}
	}

	public boolean isEnabled() {
		if( this.control != null ) {
			return this.control.isEnabled();
		}
		return false;
	}

	public void setEnabled(boolean enabled) {
		if( this.control != null ) {
			this.control.setEnabled(enabled);
		}
	}

	public boolean isVisible() {
		if( this.control != null ) {
			return this.control.isVisible();
		}
		return false;
	}

	public void setVisible(boolean visible) {
		if( this.control != null ) {
			this.control.setVisible(visible);
		}
	}

	public UISize getPackedSize() {
		if( this.control != null ) {
			return this.control.getPackedSize();
		}
		return new UISize();
	}

	public UIRectangle getBounds() {
		if( this.control != null ) {
			return this.control.getBounds();
		}
		return new UIRectangle();
	}

	public void setBounds(UIRectangle bounds) {
		if( this.control != null ) {
			this.control.setBounds(bounds);
		}
	}

	public UIColor getBgColor() {
		if( this.control != null ) {
			return this.control.getBgColor();
		}
		return null;
	}

	public void setBgColor(UIColor color) {
		if( this.control != null ) {
			this.control.setBgColor(color);
		}
	}

	public UIColor getFgColor() {
		if( this.control != null ) {
			return this.control.getFgColor();
		}
		return null;
	}

	public void setFgColor(UIColor color) {
		if( this.control != null ) {
			this.control.setFgColor(color);
		}
	}

	public UIFont getFont() {
		if( this.control != null ) {
			return this.control.getFont();
		}
		return null;
	}

	public void setFont(UIFont font) {
		if( this.control != null ) {
			this.control.setFont(font);
		}
	}

	public UICursor getCursor() {
		if( this.control != null ) {
			return this.control.getCursor();
		}
		return null;
	}

	public void setCursor(UICursor cursor) {
		if( this.control != null ) {
			this.control.setCursor(cursor);
		}
	}

	public void setFocus() {
		if( this.control != null ) {
			this.control.setFocus();
		}
	}

	public void redraw() {
		if( this.control != null ) {
			this.control.redraw();
		}
	}

	public UIPopupMenu getPopupMenu() {
		if( this.control != null ) {
			return this.control.getPopupMenu();
		}
		return null;
	}

	public void setPopupMenu(UIPopupMenu menu) {
		if( this.control != null ) {
			this.control.setPopupMenu(menu);
		}
	}

	public <Data> Data getData(String key) {
		if( this.control != null ) {
			return this.control.getData(key);
		}
		return null;
	}

	public <Data> void setData(String key, Data data) {
		if( this.control != null ) {
			this.control.setData(key, data);
		}
	}

	public void setIgnoreEvents(boolean ignoreEvents) {
		if( this.control != null ) {
			this.control.setIgnoreEvents(ignoreEvents);
		}
	}

	public boolean isIgnoreEvents() {
		if( this.control != null ) {
			return this.control.isIgnoreEvents();
		}
		return false;
	}

	public void addDisposeListener(UIDisposeListener listener) {
		if( this.control != null ) {
			this.control.addDisposeListener(listener);
		}
	}

	public void removeDisposeListener(UIDisposeListener listener) {
		if( this.control != null ) {
			this.control.removeDisposeListener(listener);
		}
	}

	public void addResizeListener(UIResizeListener listener) {
		if( this.control != null ) {
			this.control.addResizeListener(listener);
		}
	}

	public void removeResizeListener(UIResizeListener listener) {
		if( this.control != null ) {
			this.control.removeResizeListener(listener);
		}
	}

	public void addMouseUpListener(UIMouseUpListener listener) {
		if( this.control != null ) {
			this.control.addMouseUpListener(listener);
		}
	}

	public void removeMouseUpListener(UIMouseUpListener listener) {
		if( this.control != null ) {
			this.control.removeMouseUpListener(listener);
		}
	}

	public void addMouseDownListener(UIMouseDownListener listener) {
		if( this.control != null ) {
			this.control.addMouseDownListener(listener);
		}
	}

	public void removeMouseDownListener(UIMouseDownListener listener) {
		if( this.control != null ) {
			this.control.removeMouseDownListener(listener);
		}
	}

	public void addMouseMoveListener(UIMouseMoveListener listener) {
		if( this.control != null ) {
			this.control.addMouseMoveListener(listener);
		}
	}

	public void removeMouseMoveListener(UIMouseMoveListener listener) {
		if( this.control != null ) {
			this.control.removeMouseMoveListener(listener);
		}
	}

	public void addMouseDoubleClickListener(UIMouseDoubleClickListener listener) {
		if( this.control != null ) {
			this.control.addMouseDoubleClickListener(listener);
		}
	}

	public void removeMouseDoubleClickListener(UIMouseDoubleClickListener listener) {
		if( this.control != null ) {
			this.control.removeMouseDoubleClickListener(listener);
		}
	}

	public void addMouseDragListener(UIMouseDragListener listener) {
		if( this.control != null ) {
			this.control.addMouseDragListener(listener);
		}
	}

	public void removeMouseDragListener(UIMouseDragListener listener) {
		if( this.control != null ) {
			this.control.removeMouseDragListener(listener);
		}
	}

	public void addMouseWheelListener(UIMouseWheelListener listener) {
		if( this.control != null ) {
			this.control.addMouseWheelListener(listener);
		}
	}

	public void removeMouseWheelListener(UIMouseWheelListener listener) {
		if( this.control != null ) {
			this.control.removeMouseWheelListener(listener);
		}
	}

	public void addMouseEnterListener(UIMouseEnterListener listener) {
		if( this.control != null ) {
			this.control.addMouseEnterListener(listener);
		}
	}

	public void removeMouseEnterListener(UIMouseEnterListener listener) {
		if( this.control != null ) {
			this.control.removeMouseEnterListener(listener);
		}
	}

	public void addMouseExitListener(UIMouseExitListener listener) {
		if( this.control != null ) {
			this.control.addMouseExitListener(listener);
		}
	}

	public void removeMouseExitListener(UIMouseExitListener listener) {
		if( this.control != null ) {
			this.control.removeMouseExitListener(listener);
		}
	}

	public void addKeyPressedListener(UIKeyPressedListener listener) {
		if( this.control != null ) {
			this.control.addKeyPressedListener(listener);
		}
	}

	public void removeKeyPressedListener(UIKeyPressedListener listener) {
		if( this.control != null ) {
			this.control.removeKeyPressedListener(listener);
		}
	}

	public void addKeyReleasedListener(UIKeyReleasedListener listener) {
		if( this.control != null ) {
			this.control.addKeyReleasedListener(listener);
		}
	}

	public void removeKeyReleasedListener(UIKeyReleasedListener listener) {
		if( this.control != null ) {
			this.control.removeKeyReleasedListener(listener);
		}
	}

	public void addFocusGainedListener(UIFocusGainedListener listener) {
		if( this.control != null ) {
			this.control.addFocusGainedListener(listener);
		}
	}

	public void removeFocusGainedListener(UIFocusGainedListener listener) {
		if( this.control != null ) {
			this.control.removeFocusGainedListener(listener);
		}
	}

	public void addFocusLostListener(UIFocusLostListener listener) {
		if( this.control != null ) {
			this.control.addFocusLostListener(listener);
		}
	}

	public void removeFocusLostListener(UIFocusLostListener listener) {
		if( this.control != null ) {
			this.control.removeFocusLostListener(listener);
		}
	}

	public void addZoomListener(UIZoomListener listener) {
		if( this.control != null ) {
			this.control.addZoomListener(listener);
		}
	}

	public void removeZoomListener(UIZoomListener listener) {
		if( this.control != null ) {
			this.control.removeZoomListener(listener);
		}
	}

	public void dispose() {
		this.getParent().dispose(this);
	}

	public void disposeControl() {
		if( this.control != null ) {
			this.control.dispose();
		}
	}

	public boolean isControlDisposed() {
		if( this.control != null ) {
			return this.control.isDisposed();
		}
		return true;
	}

	public ToolItem getItem() {
		return item;
	}

	@SuppressWarnings("unchecked")
	public Control getItemControl() {
		if( this.control != null ) {
			return ((SWTControl<? extends Control>)this.control).getControl();
		}
		return null;
	}
}
