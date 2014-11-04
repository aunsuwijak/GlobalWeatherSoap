package weather.client.view;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.xml.ws.WebServiceException;

import weather.client.controller.WeatherController;
import weather.entity.CurrentWeather;


/**
 * WeatherUI - user interface for global weather service.
 * 
 * @author Suwijak Chaipipat 5510545046
 * @version 4.11.2014
 */
public class WeatherUI extends JFrame implements Runnable {

	private WeatherController wc;
	private JTextField cityField;
	private JTextField countryField;
	private JLabel cityLabel;
	private JLabel countryLabel;
	private JButton button;
	private JTextArea textArea;
	private JProgressBar progressBar;
	private LoadWeather lw;
	
	public WeatherUI() {
		super("Global Weather");
		setSize(600,400);
		setPreferredSize(new Dimension(600,400));
		setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		setResizable(false);
		
		initComponent();
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
				if ( lw != null )
					lw.cancel(true);
				
				lw = new LoadWeather();
				lw.execute();
			}
		});
		
		progressBar = new JProgressBar();
		progressBar.setValue(0);
		
		Container progressContainer = new Container();
		progressContainer.setLayout(new FlowLayout());
		progressContainer.add(progressBar);
		progressContainer.add(button);
		
		textArea = new JTextArea();
		
		Container container = new Container();
		
		container.setLayout( new BoxLayout(container, BoxLayout.Y_AXIS) );
		
		container.add(cityContainer);
		container.add(countryContainer);
		container.add(progressContainer);
		container.add(textArea);
		
		this.add(container);
	}
	
	public void setWeatherController(WeatherController wc) {
		this.wc = wc;
	}
	
	public void setTextArea(String text) {
		this.textArea.setText(text);
	}

	@Override
	public void run() {
		this.pack();
		this.setVisible(true);
	}
	
	class LoadWeather extends SwingWorker<CurrentWeather, Object> {

		private String weather;
		
		@Override
		protected CurrentWeather doInBackground() throws Exception {
			String cityName = cityField.getText().trim();
			
			String countryName = countryField.getText().trim();
			
			progressBar.setValue(15);
			
			if ( cityName.length() != 0 && countryName.length() != 0 ) 
				weather = wc.getGlobalWeather(cityName, countryName);
			else if ( cityName.length() == 0 && countryName.length() != 0 )
				weather = "City Field is Empty";
			else if ( countryName.length() == 0 && cityName.length() != 0 )
				weather = "Country Field is Empty";
			else if ( countryName.length() == 0 && cityName.length() == 0 )
				weather = "Field is Empty";
				
			progressBar.setValue(75);
			
			return null;
		}

		@Override
		protected void done() {
			textArea.setText(weather);
			progressBar.setValue(0);
			super.done();
		}
		
	}
	
	public static void main(String[] args) {
		WeatherUI wui = new WeatherUI();
		WeatherController wc = null;
		
		try {
			wc = new WeatherController();
		} catch ( WebServiceException wse) {
			wui.setTextArea("No internet connection");
			wui.setEnabled(false);
		}
		
		wui.setWeatherController(wc);
		wui.run();
	}

}
