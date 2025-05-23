package app.tuxguitar.player.base;

import java.util.List;

public interface MidiSequencerProvider {

	public List<MidiSequencer> listSequencers() throws MidiPlayerException;

	public void closeAll() throws MidiPlayerException;

}
