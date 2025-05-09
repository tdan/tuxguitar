package app.tuxguitar.android.view.dialog.track;

import android.view.Menu;
import android.view.MenuInflater;

import app.tuxguitar.android.R;
import app.tuxguitar.android.menu.controller.TGMenuBase;

public class TGTrackTuningListItemMenu extends TGMenuBase {

	private TGTrackTuningDialog dialog;
	private TGTrackTuningModel model;

	public TGTrackTuningListItemMenu(TGTrackTuningDialog dialog, TGTrackTuningModel model) {
		super(dialog.findActivity());

		this.dialog = dialog;
		this.model = model;
	}

	public void inflate(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.menu_track_tuning_list_item, menu);
		initializeItems(menu);
	}

	public void initializeItems(Menu menu) {
		this.initializeItem(menu, R.id.action_track_tuning_list_item_edit, this.dialog.getActionHandler().createEditTuningModelAction(this.model), true);
		this.initializeItem(menu, R.id.action_track_tuning_list_item_remove, this.dialog.getActionHandler().createRemoveTuningModelAction(this.model), true);
	}
}