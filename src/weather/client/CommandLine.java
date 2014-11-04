package weather.client;

import java.util.Scanner;

import weather.client.controller.WeatherController;


/**
 * CommandLine - start the program in command line.
 * 
 * @author Suwijak Chaipipat 5510545046
 * @version 4.11.2014
 */
public class CommandLine {
	
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		WeatherController wc = new WeatherController();
		
		System.out.println("Start Global Weather Server\nType exit to quit , anything else to continue ");
		
		String command = "";
		
		command = scan.nextLine().trim();
		
		while ( !command.toLowerCase().equals("exit") ) {
			
			System.out.print("Input City : ");
			
			String cityName = scan.nextLine().trim();
			
			System.out.print("Input Country : ");
			
			String countryName = scan.nextLine().trim();
			
			String weather = "";
			
			if ( cityName.length() != 0 && countryName.length() != 0 )
				weather = wc.getGlobalWeather(cityName, countryName);
			else if ( cityName.length() == 0 && countryName.length() != 0 )
				weather = "City Field is Empty";
			else if ( countryName.length() == 0 && cityName.length() != 0 )
				weather = "Country Field is Empty";
			else if ( countryName.length() == 0 && cityName.length() == 0 )
				weather = "Field is Empty";
			
			System.out.println("\n"+ weather);
			
			System.out.println("Type exit to quit , anything else to continue ");
			
			command = scan.nextLine().trim();
		}
		
		System.out.println("\nExit Global Weather Service");
		scan.close();
	}
	
}
