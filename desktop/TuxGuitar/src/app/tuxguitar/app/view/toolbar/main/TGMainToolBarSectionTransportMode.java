package app.tuxguitar.app.view.toolbar.main;

import app.tuxguitar.app.action.TGActionProcessorListener;
import app.tuxguitar.app.action.impl.transport.TGOpenTransportModeDialogAction;
import app.tuxguitar.app.action.impl.transport.TGTransportCountDownAction;
import app.tuxguitar.app.action.impl.transport.TGTransportMetronomeAction;
import app.tuxguitar.app.ui.TGApplication;
import app.tuxguitar.player.base.MidiPlayer;
import app.tuxguitar.ui.UIFactory;
import app.tuxguitar.ui.event.UISelectionEvent;
import app.tuxguitar.ui.event.UISelectionListener;
import app.tuxguitar.ui.layout.UITableLayout;
import app.tuxguitar.ui.toolbar.UIToolActionItem;
import app.tuxguitar.ui.toolbar.UIToolBar;
import app.tuxguitar.ui.toolbar.UIToolCheckableItem;
import app.tuxguitar.ui.widget.UIContainer;
import app.tuxguitar.ui.widget.UILabel;
import app.tuxguitar.ui.widget.UISpinner;
import app.tuxguitar.util.TGContext;

public class TGMainToolBarSectionTransportMode extends TGMainToolBarSection {

	private UIToolCheckableItem metronome;
	private UIToolCheckableItem countDown;
	private UIToolActionItem mode;
	private UISpinner countDownTicks;
	private UILabel countDowntTicksLabel;
	
	private UIFactory uiFactory;
	private UIContainer parent;
	private UITableLayout layout;

	public TGMainToolBarSectionTransportMode(TGContext context, UIToolBar toolBar, UITableLayout layout, UIContainer parent) {
		super(context, toolBar);
		this.uiFactory = TGApplication.getInstance(context).getFactory();
		this.parent = parent;
		this.layout = layout;
	}

	public void createSection() {
		MidiPlayer player =  MidiPlayer.getInstance(this.getContext());
		
		this.metronome = this.getToolBar().createCheckItem();
		this.metronome.addSelectionListener(new TGActionProcessorListener(this.getContext(), TGTransportMetronomeAction.NAME));
		this.countDown = this.getToolBar().createCheckItem();
		this.countDown.addSelectionListener(new TGActionProcessorListener(this.getContext(), TGTransportCountDownAction.NAME));
		
		this.countDownTicks = this.uiFactory.createSpinner(this.parent);
		this.countDownTicks.setMinimum(0);
		this.countDownTicks.setEnabled(player.getCountDown().isEnabled());
		if ( player.getCountDown().getTickCount() == 0 ) // set default value for count down ticks
			countDownTicks.setValue(player.getSong().getMeasureHeader(0).getTimeSignature().getNumerator());
		else
			countDownTicks.setValue(player.getCountDown().getTickCount());
		
		this.countDownTicks.addSelectionListener(new UISelectionListener() {
			@Override
			public void onSelect(UISelectionEvent event) {
				player.getCountDown().setTickCount(countDownTicks.getValue());
			}
		});
		layout.set(countDownTicks, 1, 3, UITableLayout.ALIGN_ENDING, UITableLayout.ALIGN_CENTER, true, true);
		
		this.mode = this.getToolBar().createActionItem();
		this.mode.addSelectionListener(new TGActionProcessorListener(this.getContext(), TGOpenTransportModeDialogAction.NAME));

		this.loadIcons();
		this.loadProperties();
	}

	public void updateItems(){
		MidiPlayer player = MidiPlayer.getInstance(this.getContext());
		this.metronome.setChecked(player.isMetronomeEnabled());
		this.countDown.setChecked(player.getCountDown().isEnabled());
	}

	public void loadProperties(){
		this.metronome.setToolTipText(this.getText("transport.metronome"));
		this.countDown.setToolTipText(this.getText("transport.count-down"));
		this.mode.setToolTipText(this.getText("transport.mode"));
	}

	public void loadIcons(){
		this.loadIcons(true);
	}

	public void loadIcons(boolean force){
		this.metronome.setImage(this.getIconManager().getTransportMetronome());
		this.countDown.setImage(this.getIconManager().getTransportCountIn());
		this.mode.setImage(this.getIconManager().getTransportMode());
	}
	
	private void createCountDownTicks() {
		
	}
}
