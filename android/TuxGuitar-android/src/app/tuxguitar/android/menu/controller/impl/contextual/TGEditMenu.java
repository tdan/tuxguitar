package app.tuxguitar.android.menu.controller.impl.contextual;

import android.view.Menu;
import android.view.MenuInflater;

import app.tuxguitar.android.R;
import app.tuxguitar.android.action.impl.edit.TGSetVoice1Action;
import app.tuxguitar.android.action.impl.edit.TGSetVoice2Action;
import app.tuxguitar.android.activity.TGActivity;
import app.tuxguitar.android.menu.controller.TGMenuBase;
import app.tuxguitar.android.view.dialog.measure.TGMeasureCopyDialogController;
import app.tuxguitar.android.view.dialog.measure.TGMeasurePasteDialogController;
import app.tuxguitar.editor.action.edit.TGRedoAction;
import app.tuxguitar.editor.action.edit.TGUndoAction;
import app.tuxguitar.editor.clipboard.TGClipboard;
import app.tuxguitar.player.base.MidiPlayer;

public class TGEditMenu extends TGMenuBase {

	public TGEditMenu(TGActivity activity) {
		super(activity);
	}

	public void inflate(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.menu_edit, menu);
		initializeItems(menu);
	}

	public void initializeItems(Menu menu) {
		boolean running = MidiPlayer.getInstance(this.findContext()).isRunning();

		this.initializeItem(menu, R.id.action_undo, this.createActionProcessor(TGUndoAction.NAME), !running);
		this.initializeItem(menu, R.id.action_redo, this.createActionProcessor(TGRedoAction.NAME), !running);
		this.initializeItem(menu, R.id.action_voice_1, this.createActionProcessor(TGSetVoice1Action.NAME), true);
		this.initializeItem(menu, R.id.action_voice_2, this.createActionProcessor(TGSetVoice2Action.NAME), true);
		this.initializeItem(menu, R.id.action_copy, new TGMeasureCopyDialogController(), !running);
		this.initializeItem(menu, R.id.action_paste, new TGMeasurePasteDialogController(), !running && TGClipboard.getInstance(findContext()).getSegment() != null);
	}
}
