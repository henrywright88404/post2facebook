package com.post2facebook.facebook;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

import javax.imageio.ImageIO;

import org.springframework.context.annotation.ImportResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.types.FacebookType;
import com.restfb.types.Group;
import com.restfb.BinaryAttachment;


public class FacebookPost {
	private String accessToken;
	private String java101id;
	

	public FacebookPost() {
		setAttributes();
	}
	private void setAttributes(){
		try {
			
			Resource resource = new ClassPathResource("applicationAccess.properties");
			
//			File file = new File("applicationAccess.properties");
//			FileInputStream fileInput = new FileInputStream(file);
			Properties properties = PropertiesLoaderUtils.loadProperties(resource);

			accessToken = properties.getProperty("facebook.accesstoken");
			java101id = properties.getProperty("facebook.java101groupid");
			
			/*
			 * For Debugging props file .
			 */ 
			  
	     	Enumeration enuKeys = properties.keys();
	     	while (enuKeys.hasMoreElements()) {
			 		String key = (String) enuKeys.nextElement();
			 		String value = properties.getProperty(key);
			 		System.out.println(key + ": " + value);
			} 
			  
			  
			 
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getJava101id() {
		return java101id;
	}

	public void createPostInGroup() {
		FacebookClient fbClient = new DefaultFacebookClient(accessToken, Version.LATEST);

		Scanner s = new Scanner(System.in);
		Connection<Group> result = fbClient.fetchConnection("me/groups", Group.class);

		for (List<Group> groupPage : result) {
			for (Group aGroup : groupPage) {
				System.out.println("Do you want to post in this " + aGroup.getName() + " group? ");
				String ans = s.nextLine();
				if (ans.equalsIgnoreCase("y")) {
					System.out.println("What do you want to post?");
					String msg = s.nextLine();
					FacebookType response = fbClient.publish(aGroup.getId() + "/feed", FacebookType.class,
							Parameter.with("message", msg));
					System.out.println("fb.com/" + response.getId());
				}
			}
		}
		s.close();
	}

	public void createTextPostInGroup(String message, String groupid) {
		FacebookClient fbClient = new DefaultFacebookClient(accessToken, Version.LATEST);
		
		/*
		 * For Debugging 
		 */
		
		System.out.println("In FacebookPost.java createTestPostInGroup: Group Id = "+ groupid);
		System.out.println("In FacebookPost.java createTestPostInGroup: access token = "+ accessToken);


		fbClient.publish(groupid + "/feed", FacebookType.class, Parameter.with("message",
				"***This is an Automatic post by Marc's Gmail to Facebook application***  \n" + 
				message ));

	}

	public void createImagePostInGroup(String message, String groupId, String imageFilePath) {

		byte[] imageAsBytes = fetchBytesFromImage(imageFilePath);

		DefaultFacebookClient client = new DefaultFacebookClient(accessToken, Version.LATEST);

		client.publish(groupId + "/photos", FacebookType.class,
				BinaryAttachment.with(imageFilePath.substring(imageFilePath.length() - 34), imageAsBytes, "image/png"),
				Parameter.with("message", message));
	}
	
	public void createImagePostInGroup(String message, String groupId, byte[] imageAsBytes ) {


		DefaultFacebookClient client = new DefaultFacebookClient(accessToken, Version.LATEST);

		client.publish(groupId + "/photos", FacebookType.class,
				BinaryAttachment.with("image/png", imageAsBytes),
				Parameter.with("message", message));
	}
	

	private byte[] fetchBytesFromImage(String imageFile) {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte fileContent[] = null;

		try {

			BufferedImage image = ImageIO.read(new File(imageFile));

			ImageIO.write(image, "png", baos);
			baos.flush();
			fileContent = baos.toByteArray();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return fileContent;

	}

}
