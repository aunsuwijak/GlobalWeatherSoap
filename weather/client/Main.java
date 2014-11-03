package weather.client;

import weather.client.ui.WeatherUI;

public class Main {
	
	public static void main(String[] args) {
		WeatherUI wui = new WeatherUI();
		WeatherController wc = new WeatherController();
		wui.setWeatherController(wc);
	}
	
}
