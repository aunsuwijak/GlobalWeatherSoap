package weather.client;

import java.io.ByteArrayInputStream;

import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import weather.entity.CurrentWeather;

import net.webservicex.GlobalWeather;
import net.webservicex.GlobalWeatherSoap;

public class WeatherController {
	
	private GlobalWeather gw;
	private GlobalWeatherSoap gwp;
	private JAXBContext ctx;
	private Unmarshaller um;
	
	public WeatherController() {
		gw = new GlobalWeather();
		gwp = gw.getGlobalWeatherSoap();
		try {
			ctx = JAXBContext.newInstance( CurrentWeather.class );
			um = ctx.createUnmarshaller();
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}
	
	public String getGlobalWeather(String cityName, String countryName) {
		String weather = null;
		
		try {
			weather = gwp.getWeather(cityName, countryName);
		} catch ( javax.xml.ws.soap.SOAPFaultException ioe ) {
			ioe.printStackTrace();
		}
		
		if ( weather.equals("Data Not Found") || weather == null )
			return "Data Not Found";
		
		weather = weather.replace("utf-16", "utf-8");
		
		InputStream instream = new ByteArrayInputStream( weather.getBytes() );
		
		CurrentWeather cw = null;
		
		try {
			cw = (CurrentWeather)um.unmarshal( instream );
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		
		String out = "Location : " + cw.getLocation()
			    + "\nTime : " + cw.getWind()
			    + "\nVisibility : " + cw.getVisibility()
			    + "\nTempature : " + cw.getTempature()
			    + "\nDew Point : " + cw.getDewPoint()
				+ "\nRelative Humidity : " + cw.getRelativeHumidity()
				+ "\nPressure : " + cw.getPressure();
		
		return out;
	}
}
