package app.tuxguitar.io.synth;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;

public class TGSynthAudioSettings {

	public static final AudioFormat DEFAULT_FORMAT = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100, 16, 2, 4, 44100, false);
	public static final AudioFileFormat.Type DEFAULT_TYPE = AudioFileFormat.Type.WAVE;

	private AudioFileFormat.Type type;
	private AudioFormat format;

	public TGSynthAudioSettings() {
		this.setType(DEFAULT_TYPE);
		this.setFormat(DEFAULT_FORMAT);
	}

	public AudioFileFormat.Type getType() {
		return this.type;
	}

	public void setType(AudioFileFormat.Type type) {
		this.type = type;
	}

	public AudioFormat getFormat() {
		return this.format;
	}

	public void setFormat(AudioFormat format) {
		this.format = format;
	}
}
