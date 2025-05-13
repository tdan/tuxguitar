package app.tuxguitar.app.action.impl.transport;

import app.tuxguitar.action.TGActionContext;
import app.tuxguitar.editor.action.TGActionBase;
import app.tuxguitar.player.base.MidiPlayer;
import app.tuxguitar.util.TGContext;

public class TGTransportCountDownTicksAction extends TGActionBase {
	
	public static final String NAME = "action.transport.count-down-ticks";

	public TGTransportCountDownTicksAction(TGContext context) {
		super(context, NAME);
	}

	@Override
	protected void processAction(TGActionContext context) {
		MidiPlayer midiPlayer = MidiPlayer.getInstance(getContext());
		midiPlayer.getCountDown().setTickCount(10);
	}
	
}