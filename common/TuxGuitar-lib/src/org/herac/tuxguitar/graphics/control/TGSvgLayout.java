package org.herac.tuxguitar.graphics.control;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.herac.tuxguitar.song.models.TGTrack;
import org.herac.tuxguitar.ui.resource.UIFont;
import org.herac.tuxguitar.ui.resource.UIPainter;
import org.herac.tuxguitar.ui.resource.UIRectangle;
import org.herac.tuxguitar.ui.resource.UIResourceFactory;

public class TGSvgLayout extends TGLayout {
	private float maxWidth;
	private float marginLeft;
	private float marginRight;

	private UIFont songNameFont;
	private UIFont songAuthorFont;
	private UIFont artistFont;
	private UIFont albumNameYearFont;

	public TGSvgLayout(TGController controller, int style) {
		super(controller, style);
	}

	@Override
	public int getMode() {
		return MODE_VERTICAL;
	}

	@Override
	public void paintSong(
		UIPainter painter,
		UIRectangle clientArea,
		float fromX, float fromY
	) {
		this.marginLeft = this.getFirstMeasureSpacing();
		this.marginRight = 10;
		this.maxWidth = clientArea.getWidth() - (marginLeft + marginRight);
		this.setWidth(0);
		this.setHeight(0);
		this.clearTrackPositions();
		
		int style = this.getStyle();
		int trackNumber = getComponent().getTrackSelection();

		// Print header
		float headerOffset = this.paintHeader(painter, fromX, fromY);

		float posY = Math.round(fromY + headerOffset + getFirstTrackSpacing());
		float height = getFirstTrackSpacing();
		float lineHeight = 0;
		
		int measureCount = this.getSong().countMeasureHeaders();
		int nextMeasureIndex = 0;
		
		
		while (measureCount > nextMeasureIndex) {
			TempLine line = null;
			
			Iterator<TGTrack> tracks = this.getSong().getTracks();
			while (tracks.hasNext()) {
				TGTrackImpl track = (TGTrackImpl) tracks.next();

				if (trackNumber < 0 || track.getNumber() == trackNumber) {
					TGTrackSpacing trackSpacing = new TGTrackSpacing(this);
					trackSpacing.setSize(
						TGTrackSpacing.POSITION_SCORE_MIDDLE_LINES,
						(style & DISPLAY_SCORE) != 0 ? (getScoreLineSpacing() * 5):0
					);

					if (nextMeasureIndex == 0) 
						((TGLyricImpl)track.getLyrics()).start();

					line = getTempLines(track, nextMeasureIndex, trackSpacing);
					if ((style & DISPLAY_SCORE) != 0) {

						trackSpacing.setSize(
							TGTrackSpacing.POSITION_SCORE_UP_LINES,
							Math.abs(line.minY)
						);
						if (line.maxY > track.getScoreHeight()) 
							trackSpacing.setSize(
								TGTrackSpacing.POSITION_SCORE_DOWN_LINES,
								line.maxY - track.getScoreHeight()
							);
					}
					
					if ((style & DISPLAY_TABLATURE) != 0) {
						trackSpacing.setSize(
							TGTrackSpacing.POSITION_TABLATURE_TOP_SEPARATOR,
							(style & DISPLAY_SCORE) != 0 ? 
								getMinScoreTabSpacing() : 
								Math.max(Math.abs(line.minY), getStringSpacing())
						);
						trackSpacing.setSize(
							TGTrackSpacing.POSITION_TABLATURE,
							(style & DISPLAY_SCORE) != 0 ? 
								track.getTabHeight() + getStringSpacing() + 1 : 
								Math.max(line.maxY, track.getTabHeight() + getStringSpacing() + 1)
						);
					}
					trackSpacing.setSize(TGTrackSpacing.POSITION_LYRIC, 10);
					checkDefaultSpacing(trackSpacing);
					
					paintLine(track, line, painter, fromX, posY, trackSpacing, clientArea);
					
					lineHeight = trackSpacing.getSize();
					addTrackPosition(track.getNumber(), posY, lineHeight);
					
					float emptyX = this.marginLeft + fromX + line.tempWith + 2;
					float emptyWidth = this.maxWidth - emptyX;

					if ((emptyWidth - 20) > 0 &&
						(line.lastIndex + 1) >= measureCount) {
						
						if (emptyX < (clientArea.getX() + clientArea.getWidth())) {
							emptyX = emptyX < clientArea.getX() ? 
										clientArea.getX() :
										emptyX;
							emptyWidth = emptyWidth > clientArea.getWidth() ?
											clientArea.getWidth() :
											emptyWidth;
							paintLines(track, trackSpacing, painter, emptyX, posY, emptyWidth);
						}
					}
					
					float lineHeightWithSpacing = Math.round(lineHeight + getTrackSpacing());
					
					posY += lineHeightWithSpacing;
					height += lineHeightWithSpacing;
				}
			}
			
			if (line != null)
				nextMeasureIndex = line.lastIndex + 1;
		}
		
		this.setHeight(height);
		this.setWidth(getWidth() + this.marginRight);
	}

	public float paintHeader(
			UIPainter svgPainter, 
			float startX, float startY
	) {
		float paintableWidth = this.maxWidth - (this.marginLeft + this.marginRight); 
		float fmTopLine = Math.round(svgPainter.getFMTopLine());

		float headerOffset = 0f;

		String songName = getSong().getName();
		String songAuthor = getSong().getAuthor();
		String artistName = getSong().getArtist();
		String albumName = getSong().getAlbum();
		String releaseYear = getSong().getDate();
		String copyright = getSong().getCopyright();
		String transcriber = getSong().getTranscriber();
		String tabCreator = getSong().getWriter();
		
		if( songName != null && songName.length() > 0 ){

			svgPainter.setFont(getSongNameFont());
			svgPainter.drawString(
				songName, 
				startX + alignCenter(svgPainter, paintableWidth, songName),
				fmTopLine + startY + Math.round(headerOffset)
			);
			headerOffset += 20.0f * getScale();
		}

		if( artistName != null && artistName.length() > 0 ){

			svgPainter.setFont(getArtistFont());
			svgPainter.drawString(
				artistName, 
				startX + alignCenter(svgPainter, paintableWidth, artistName),
				fmTopLine + startY + Math.round(headerOffset)
			);
			headerOffset += 10.0f * getScale();
		}
		
		if( (albumName != null && albumName.length() > 0) ||
			(releaseYear != null && releaseYear.length() > 0) ){
			
			String albumNameReleaseYear = "";
			if( albumName != null && albumName.length() > 0 )
				albumNameReleaseYear += "Recorded on " + albumName + " ";
			if( releaseYear != null && releaseYear.length() > 0 )
				albumNameReleaseYear += "(" + releaseYear + ")";
		
			svgPainter.setFont(getAlbumNameYearFont());
			svgPainter.drawString(
				albumNameReleaseYear,
				startX + alignCenter(svgPainter, paintableWidth, albumNameReleaseYear),
				startY + fmTopLine + Math.round(headerOffset)
			);
			headerOffset += 20.0f * this.getScale();
		}
		
		if( songAuthor != null && songAuthor.length() > 0 ){

			svgPainter.setFont(getSongAuthorFont());
			svgPainter.drawString(
				songAuthor,
				startX + alignRight(svgPainter, paintableWidth, songAuthor),
				fmTopLine + startY + Math.round(headerOffset)
			);
			headerOffset += 10.0f * this.getScale();
		}

		if( copyright != null && copyright.length() > 0 ){

			copyright = "Copyrighted by " + copyright;
			svgPainter.setFont(getSongAuthorFont());
			svgPainter.drawString(
				copyright,
				startX + alignRight(svgPainter, paintableWidth, copyright),
				fmTopLine + startY + Math.round(headerOffset)
			);
			headerOffset += 10.0f * getScale();
		}
		
		if( transcriber != null && transcriber.length() > 0 ){

			transcriber = "Transcribed by " + transcriber;
			svgPainter.setFont(getSongAuthorFont());
			svgPainter.drawString(
				transcriber,
				startX + alignRight(svgPainter, paintableWidth, transcriber),
				fmTopLine + startY + Math.round(headerOffset)
			);
			headerOffset += 10.0f * getScale();
		}

		if( tabCreator != null && tabCreator.length() > 0 ){

			tabCreator = "Created by " + tabCreator;
			svgPainter.setFont(getSongAuthorFont());
			svgPainter.drawString(
				tabCreator,
				startX + alignRight(svgPainter, paintableWidth, tabCreator),
				fmTopLine + startY + Math.round(headerOffset)
			);
			headerOffset += 20.0f * getScale();
		}

		return headerOffset;
	}

	public void paintLine(
			TGTrackImpl track, TempLine line, UIPainter painter, 
			float fromX, float fromY,
			TGTrackSpacing trackSpacing, UIRectangle clientArea
	) {
		float posX = Math.round(this.marginLeft + fromX);
		float posY = Math.round(fromY);
		float width = this.marginLeft;
		
		boolean isAtY = (posY + trackSpacing.getSize() > clientArea.getY()) && 
				(posY < (clientArea.getY() + clientArea.getHeight() + getScale() * 80f));
		
		float defaultMeasureSpacing = 0;
		if (line.fullLine) {
			float diff = this.maxWidth - line.tempWith;
			if (diff != 0 && line.measures.size() > 0) 
				defaultMeasureSpacing = diff / line.measures.size();
		}
		
		float measureSpacing = defaultMeasureSpacing;
		
		for (int i = 0; i < line.measures.size(); i++) {
			int index = line.measures.get(i).intValue();
			TGMeasureImpl currMeasure = (TGMeasureImpl)track.getMeasure(index);
			
			currMeasure.setPosX(posX);
			currMeasure.setPosY(posY);
			currMeasure.setTs(trackSpacing);
			
			((TGLyricImpl)track.getLyrics()).setCurrentMeasure(currMeasure);
			
			currMeasure.setFirstOfLine(i == 0);
			
			float measureWidth = currMeasure.getWidth(this);
			float measureWidthWithSpacing = this.isBufferEnabled() ?
												Math.round(measureWidth + measureSpacing) :
												measureWidth + measureSpacing;
			float measureSpacingAfterRound = measureWidthWithSpacing - measureWidth;
			
			boolean isAtX = posX + measureWidthWithSpacing > clientArea.getX() && 
							posX < clientArea.getX() + clientArea.getWidth();
			
			if (isAtX && isAtY)
				paintMeasure(currMeasure, painter, measureSpacingAfterRound);
			else
				currMeasure.setOutOfBounds(true);
			
			posX += measureWidthWithSpacing;
			width += measureWidthWithSpacing;
			measureSpacing = defaultMeasureSpacing + (measureSpacing - measureSpacingAfterRound);
		}
		
		this.setWidth(Math.max(getWidth(), width));
	}

	public TempLine getTempLines(
			TGTrackImpl track, int fromIndex, 
			TGTrackSpacing trackSpacing
	) {
		int style = getStyle();
		
		TempLine line = new TempLine();
		line.maxY = 0;
		line.minY = 0;
		
		// Need to score extra-lines in edition mode
		if( (style & DISPLAY_TABLATURE) == 0 && (style & DISPLAY_SCORE) != 0 ){
			line.maxY = ((getScoreLineSpacing() * 4) + (getScoreLineSpacing() * 4));
			line.minY = -(getScoreLineSpacing() * 3);
		}
		
		int measureCount = track.countMeasures();
		for (int measureIdx = fromIndex; measureIdx < measureCount; measureIdx++) {
			TGMeasureImpl measure = (TGMeasureImpl)track.getMeasure(measureIdx);
			
			//verifico si tengo que bajar de linea
			if((line.tempWith + measure.getWidth(this)) >= this.maxWidth ){
				if( line.measures.isEmpty() ) {
					this.addToTempLine(line, trackSpacing, measure, measureIdx);
				}
				line.fullLine = true;
				return line;
			}
			
			this.addToTempLine(line, trackSpacing, measure, measureIdx);
		}
		
		return line;
	}
	
	public void addToTempLine(
			TempLine line, TGTrackSpacing trackSpacing,
			TGMeasureImpl measure, int measureIdx
	) {
		line.tempWith +=  measure.getWidth(this);
		line.maxY = (measure.getMaxY() > line.maxY)?measure.getMaxY():line.maxY;
		line.minY = (measure.getMinY() < line.minY)?measure.getMinY():line.minY;
		
		line.addMeasure(measureIdx);
		measure.registerSpacing(this, trackSpacing);	
	}

	private UIFont getSongNameFont() {
		UIResourceFactory factory = this.getComponent().getResourceFactory();
		
		if (factory != null &&
			(this.songNameFont == null || this.songNameFont.isDisposed())) 
			
			this.songNameFont = factory.createFont(
				this.getResources().getDefaultFont().getName(),
				16.0f * getFontScale(),
				true, false
			);
			
		return this.songNameFont;
	}

	private UIFont getArtistFont() {
		UIResourceFactory factory = this.getComponent().getResourceFactory();
		
		if (factory != null &&
			(this.artistFont == null || this.artistFont.isDisposed())) 
			
			this.artistFont = factory.createFont(
				this.getResources().getDefaultFont().getName(),
				12.0f * getFontScale(),
				true, false
			);
			
		return this.artistFont;
	}

	private UIFont getAlbumNameYearFont() {
		UIResourceFactory factory = this.getComponent().getResourceFactory();
		
		if (factory != null &&
			(this.albumNameYearFont == null || this.albumNameYearFont.isDisposed())) 
			
			this.albumNameYearFont = factory.createFont(
				this.getResources().getDefaultFont().getName(),
				10.0f * getFontScale(),
				true, false
			);
			
		return this.albumNameYearFont;
	}

	private UIFont getSongAuthorFont() {
		UIResourceFactory factory = this.getComponent().getResourceFactory();
		
		if (factory != null &&
			(this.songAuthorFont == null || this.songAuthorFont.isDisposed())) 
			
			this.songAuthorFont = factory.createFont(
				this.getResources().getDefaultFont().getName(),
				8.0f * getFontScale(),
				true, false
			);
			
		return this.songAuthorFont;
	}

	private float alignRight(UIPainter svgPainter, float width, String text) {
		return (width - 0.65f * svgPainter.getFMWidth(text)) / 2;
	}

	private float alignCenter(UIPainter svgPainter, float width, String text) {
		// For some reason, getFMWidth(text) outputs a value that has 1.5 times
		// more pixels than the actual SVG rendering.
		// 0.65f is a temporary fix to calibrate it back to the correct value.
		return (width - 0.65f * svgPainter.getFMWidth(text)) / 2;
	}

	private class TempLine {
		protected float tempWith;
		protected int lastIndex;
		protected boolean fullLine;
		protected float maxY = 0;
		protected float minY = 0;
		protected List<Integer> measures;
		
		public TempLine(){
			this.measures = new ArrayList<Integer>();
		}
		
		protected void addMeasure(int index){
			this.measures.add(Integer.valueOf(index));
			this.lastIndex = index;
		}
	}
}