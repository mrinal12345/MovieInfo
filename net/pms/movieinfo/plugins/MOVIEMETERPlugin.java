package net.pms.movieinfo.plugins;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;

public class MOVIEMETERPlugin implements Plugin
{
	private int fs;
	private StringBuffer sb;
	private String newURL;
	private ArrayList<String> castlist = new ArrayList<String>();

	public void importFile(BufferedReader in)
	{
		try {
			BufferedReader br = in;
			sb = new StringBuffer();
			String eachLine = null;
			if(br != null)
			eachLine = br.readLine();

			while (eachLine != null) {
				sb.append(eachLine);
				eachLine = br.readLine();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public String getTitle() 
	{
		if(sb != null)
		fs = sb.indexOf("<title>");
		String title = null;
		if (fs > -1) {
			title = sb.substring(fs + 7, sb.indexOf("</title>", fs));
			title = title.replace(" - MovieMeter.nl", "");
			if (title.contains("filmsite voor liefhebbers"))
				title = null;
		}
		return title;
	}
	public String getPlot()
	{
		fs = sb.indexOf("class=\"film_info\"");
		if (fs > -1)
		fs = sb.indexOf("</a><br",fs);
		if (fs > -1)
			fs = sb.indexOf("<br /><br />",fs);
		String plot = null;
		if (fs > -1) {
			plot = sb.substring(fs + 12,sb.indexOf("<",fs+12));
			plot = plot.trim();
		}
		return plot;
	}
	public String getDirector()
	{
		
		return null;
	}
	public String getGenre()
	{
		return null;
	}
	public String getTagline()
	{
		return null;
	}
	public String getRating()
	{
		String rating = null;
		fs = sb.indexOf("gemiddelde <b>");
		if (fs > -1){
			rating = sb.substring(fs + 14, sb.indexOf("</", fs));
			rating = rating.replace(",", ".");
			if (rating.matches("1{0,1}[0-9]\\.[0-9][0-9]{0,1}"))
				rating = Double.parseDouble(rating.trim())*2 + "";
				rating += "/10";
		}
		return rating;
	}
	public String getVideoThumbnail()
	{
		String thumb = null;
		fs = sb.indexOf("http://www.moviemeter.nl/images/covers/");
		if (fs > -1) 
			thumb = sb.substring(fs, sb.indexOf("\"", fs));
		return thumb;
	}
	public ArrayList<String> getCast()
	{
		return castlist;
	}
	public String getTvShow() {return "";}
	public String getCharSet() {return "8859_1";}
	public String getGoogleSearchSite()
	{
		return "moviemeter.nl/film";
	}
	public String getVideoURL()
	{
		return "http://www.moviemeter.nl/###MOVIEID###";
	}
	public String lookForMovieID(BufferedReader in) {
		try {
			String inputLine, temp;
			StringWriter content = new StringWriter();

			while ((inputLine = in.readLine()) != null)
				content.write(inputLine);

			in.close();
			content.close();
			temp = content.toString();
			int fs = temp.indexOf("http://www.moviemeter.nl/");
			int end = temp.indexOf("\"", fs+25);
			newURL = null;
			if (fs > -1) {
				newURL = temp.substring(fs + 25, end);
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//System.out.println("lookForImdbID Exception: " + e);
			// e.printStackTrace();
		}
		//System.out.println(this.getClass().getName() + "lookForMovieID Returns " + newURL);
		return newURL; //To use as ###MOVIEID### in getVideoURL()
	}
	@Override
	public String getAgeRating() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getTrailerURL() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
