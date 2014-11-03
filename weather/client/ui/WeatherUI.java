package weather.client.ui;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import weather.client.WeatherController;
import weather.entity.CurrentWeather;

public class WeatherUI extends JFrame {

	private WeatherController wc;
	private JTextField cityField;
	private JTextField countryField;
	private JLabel cityLabel;
	private JLabel countryLabel;
	private JButton button;
	private JTextArea textArea;
	
	public WeatherUI() {
		super("Global Weather");
		setSize(600,400);
		setPreferredSize(new Dimension(600,400));
		setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		setResizable(false);
		
		initComponent();
		
		setVisible(true);
	}

	private void initComponent() {
		cityLabel = new JLabel("City :");
		cityField = new JTextField(40);
		
		Container cityContainer = new Container();
		cityContainer.setLayout(new FlowLayout());
		cityContainer.add(cityLabel);
		cityContainer.add(cityField);
		
		countryLabel = new JLabel("Country :");
		countryField = new JTextField(40);
		
		Container countryContainer = new Container();
		countryContainer.setLayout(new FlowLayout());
		countryContainer.add(countryLabel);
		countryContainer.add(countryField);
		
		button = new JButton("Get Global Weather");
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String cityName = cityField.getText().trim();
				
				String countryName = countryField.getText().trim();
				
				String weather = wc.getGlobalWeather(cityName, countryName);

				textArea.setText(weather);
			}
		});
		
		textArea = new JTextArea();
		
		Container container = new Container();
		
		container.setLayout( new BoxLayout(container, BoxLayout.Y_AXIS) );
		
		container.add(cityContainer);
		container.add(countryContainer);
		container.add(button);
		container.add(textArea);
		
		this.add(container);
	}
	
	public void setWeatherController(WeatherController wc) {
		this.wc = wc;
	}
}
