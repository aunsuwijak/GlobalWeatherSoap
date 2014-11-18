package ku.weather.controller;

import java.io.ByteArrayInputStream;

import java.io.InputStream;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.ws.WebServiceException;

import ku.weather.model.CurrentWeather;


import net.webservicex.GlobalWeather;
import net.webservicex.GlobalWeatherSoap;

/**
 * WeatherController - controller class for retrieving global weather data.
 * 
 * @author Suwijak Chaipipat 5510545046
 * @version 4.11.2014
 */
public class WeatherController {
	
	private GlobalWeather gw;
	private GlobalWeatherSoap gwp;
	private JAXBContext ctx;
	private Unmarshaller um;
	
	public WeatherController() {
		try {
			gw = new GlobalWeather();
		} catch (WebServiceException wse) {
			throw new WebServiceException();
		}
		
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
		
		weather = gwp.getWeather(cityName, countryName);
		
		if ( weather.equals("Data Not Found") || weather == null )
			return "Data Not Found";
		
		weather = weather.replace("utf-16", "utf-8");
		
		InputStream instream = new ByteArrayInputStream( weather.getBytes() );
		
		CurrentWeather cw = null;
		
		try {
			cw = (CurrentWeather)um.unmarshal( instream );
		} catch (JAXBException e) {
			return "Data Not Found";
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
