package app.tuxguitar.io.gpx.v6;

import app.tuxguitar.io.base.TGFileFormat;
import app.tuxguitar.io.base.TGFileFormatException;
import app.tuxguitar.io.base.TGSongReader;
import app.tuxguitar.io.base.TGSongReaderHandle;
import app.tuxguitar.io.gpx.GPXDocumentParser;
import app.tuxguitar.io.gpx.GPXDocumentReader;

public class GPXInputStream implements TGSongReader{

	public static final TGFileFormat FILE_FORMAT = new TGFileFormat("Guitar Pro 6", "application/x-gtp", new String[]{"gpx"});

	public TGFileFormat getFileFormat() {
		return FILE_FORMAT;
	}

	public void read(TGSongReaderHandle handle) throws TGFileFormatException {
		try {
			GPXFileSystem gpxFileSystem = new GPXFileSystem();
			gpxFileSystem.load(handle.getInputStream());

			GPXDocumentReader gpxReader = new GPXDocumentReader(gpxFileSystem.getFileContentsAsStream("score.gpif"), GPXDocumentReader.GP6);
			GPXDocumentParser gpxParser = new GPXDocumentParser(handle.getFactory(), gpxReader.read());

			handle.setSong(gpxParser.parse());
		} catch (Throwable throwable) {
			throw new TGFileFormatException( throwable );
		}
	}
}
