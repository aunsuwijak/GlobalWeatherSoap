package ku.weather.view;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.Timer;
import javax.xml.ws.WebServiceException;

import ku.weather.controller.WeatherController;
import ku.weather.model.CurrentWeather;



/**
 * WeatherUI - user interface for global weather service.
 * 
 * @author Suwijak Chaipipat 5510545046
 * @version 4.11.2014
 */
public class WeatherUI extends JFrame implements Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1970626964837422475L;
	private WeatherController wc;
	private Timer timer;
	private JTextField cityField;
	private JTextField countryField;
	private JLabel cityLabel;
	private JLabel countryLabel;
	private JButton button;
	private JTextArea textArea;
	private JProgressBar progressBar;
	private LoadWeather lw;
	private JButton retryButton;
	
	public WeatherUI() {
		super("Global Weather");
		setSize(600,400);
		setPreferredSize(new Dimension(600,400));
		setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		setResizable(false);
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		
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
		
		retryButton = new JButton("Retry");
		retryButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if ( wc == null ) {
					setWeatherService();
				}
				else {
					setTextArea("Connected to service");
				}
			}
			
		});
		
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
		progressContainer.add(retryButton);
		
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
	
	public void setWeatherService() {
		this.setTextArea("Connecting to service.");
		progressBar.setValue(50);
		try {
			wc = new WeatherController();
			this.setWeatherController(wc);
			this.setTextArea("Connected to service.");
			progressBar.setValue(0);
		} catch ( WebServiceException wse) {
			String[] buttonText = {"Retry","Cancel"};
			int input = JOptionPane.showOptionDialog(null, 
			        "Can't connect to service", 
			        "WARNING", 
			        JOptionPane.OK_CANCEL_OPTION, 
			        JOptionPane.WARNING_MESSAGE, 
			        null, 
			        buttonText, // this is the array
			        "Retry");
			if ( input == 0 ) 
				this.setWeatherService();
			else if ( input == 1 || input == -1 ) 
				System.exit(0);
			progressBar.setValue(0);
		}
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
			setTimeout(10000);
			
			progressBar.setValue(0);
			
			String cityName = cityField.getText().trim();
			
			String countryName = countryField.getText().trim();
			
			progressBar.setValue(15);
			
			if ( cityName.length() != 0 && countryName.length() != 0 ) {
				try {
					weather = wc.getGlobalWeather(cityName, countryName);
				} catch ( WebServiceException wse ) {
					wc = null;
					weather = "No Internet Connection";
					progressBar.setValue(0);
					return null;
				}
			}
			else if ( cityName.length() == 0 && countryName.length() != 0 )
				weather = "City Field is Empty";
			else if ( countryName.length() == 0 && cityName.length() != 0 )
				weather = "Country Field is Empty";
			else if ( countryName.length() == 0 && cityName.length() == 0 )
				weather = "Field is Empty";
				
			progressBar.setValue(75);
			
			progressBar.setValue(100);
			
			return null;
		}

		@Override
		protected void done() {
			timer.stop();
			textArea.setText(weather);
			progressBar.setValue(0);
			super.done();
		}
		
		private void setTimeout(int timeout) {
			final LoadWeather load = this;
			 timer = new Timer(timeout, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					load.cancel(true);
					JOptionPane.showMessageDialog(null, "System connection time out, please try again.");
				}
			});
			timer.start();
		}
		
	}
	
	public static void main(String[] args) {
		WeatherUI wui = new WeatherUI();
		
		wui.run();
		
		wui.setWeatherService();
	}

}
