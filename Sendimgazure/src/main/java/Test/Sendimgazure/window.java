package Test.Sendimgazure;

import java.awt.EventQueue;
import java.io.*;

import javax.swing.JFrame;
import javax.swing.JTextField;
import java.io.File;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.net.URI;
import java.awt.event.ActionEvent;

public class window {
	public static String a ;
	private JFrame frame;
	private JTextField textField;
	 private static final String subscriptionKey = "12879c5d28044970b54f4b4f8010227b";


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window window = new window();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public window() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		textField = new JTextField();
		textField.setText("Enter Image name");
		textField.setBounds(145, 69, 96, 19);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("Submit");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final String uriBase =
		                "https://westcentralus.api.cognitive.microsoft.com/vision/v2.0/analyze";
				final String imageToAnalyze = textField.getText();
		        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

		        try {
		            URIBuilder builder = new URIBuilder(uriBase);

		            // Request parameters. All of them are optional.
		            builder.setParameter("visualFeatures", "Categories,Description,Color");
		            builder.setParameter("language", "en");

		            // Prepare the URI for the REST API method.
		            URI uri = builder.build();
		            HttpPost request = new HttpPost(uri);

		            // Request headers.
		            request.setHeader("Content-Type", "application/json");
		            request.setHeader("Ocp-Apim-Subscription-Key", subscriptionKey);

		            // Request body.
		            StringEntity requestEntity =
		                    new StringEntity("{\"url\":\"" + imageToAnalyze + "\"}");
		            request.setEntity(requestEntity);

		            // Call the REST API method and get the response entity.
		            HttpResponse response = httpClient.execute(request);
		            HttpEntity entity = response.getEntity();

		            if (entity != null) {
		                // Format and display the JSON response.
		                String jsonString = EntityUtils.toString(entity);
		                JSONObject json = new JSONObject(jsonString);
		                System.out.println("REST Response:\n");
		                System.out.println(json.query("/categories/0/name"));
		                String s=json.query("/categories/0/name").toString();
		                boolean c = new File(s).mkdir();
		            }
		        } catch (Exception b) {
		            // Display error message.
		            System.out.println(b.getMessage());
		        }
			}
		});
		btnNewButton.setBounds(145, 127, 85, 21);
		frame.getContentPane().add(btnNewButton);
	}
}
