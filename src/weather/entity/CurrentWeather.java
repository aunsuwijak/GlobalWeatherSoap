package weather.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * CurrentWeather - use for extracting xml file into object.
 * 
 * @author Suwijak Chaipipat 5510545046
 * @version 4.11.2014
 */
@XmlRootElement(name="CurrentWeather")
@XmlAccessorType(XmlAccessType.FIELD)
public class CurrentWeather {

	@XmlElement
	private String Location;
	private String Time;
	private String Wind;
	private String Visibility;
	private String Tempature;
	private String DewPoint;
	private String RelativeHumidity;
	private String Pressure;
	private String Status;
	
	public CurrentWeather() {
		
	}
	
	public void setLocation(String Location) {
		this.Location = Location;
	}
	
	public String getLocation() {
		return this.Location;
	}

	public String getTime() {
		return this.Time;
	}

	public void setTime(String time) {
		this.Time = time;
	}

	public String getWind() {
		return this.Wind;
	}

	public void setWind(String wind) {
		this.Wind = wind;
	}

	public String getVisibility() {
		return this.Visibility;
	}

	public void setVisibility(String visibility) {
		this.Visibility = visibility;
	}

	public String getTempature() {
		return this.Tempature;
	}

	public void setTempature(String tempature) {
		this.Tempature = tempature;
	}

	public String getDewPoint() {
		return this.DewPoint;
	}

	public void setDewPoint(String dewPoint) {
		this.DewPoint = dewPoint;
	}

	public String getRelativeHumidity() {
		return this.RelativeHumidity;
	}

	public void setRelativeHumidity(String relativeHumidity) {
		this.RelativeHumidity = relativeHumidity;
	}

	public String getPressure() {
		return this.Pressure;
	}

	public void setPressure(String pressure) {
		this.Pressure = pressure;
	}

	public String getStatus() {
		return this.Status;
	}

	public void setStatus(String status) {
		this.Status = status;
	}
	
}
