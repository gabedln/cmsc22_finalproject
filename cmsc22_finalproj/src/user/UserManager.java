/*
 * This package has an array list of all users
 * Its methods are used for validating existing profiles through logins, and
 * adding new users to the program using sign up.
 */


package user;

import java.util.ArrayList;
import java.util.Scanner;


public class UserManager {
	
	private static Scanner sc = new Scanner(System.in);
	private ArrayList <User> userList = new ArrayList<>();

	//LOGIN
	public void login() {
		System.out.println("Enter USERNAME: ");
		String username = sc.nextLine();
		
		System.out.println("Enter PASSWORD: ");
		String password = sc.nextLine();
		
		validate(username, password);
	}

	
	//USE TO VALIDATE IF USER EXISTS
	public void validate(String username, String password) {
		for(User user : userList) {
			if(user.getName().equals(username) && user.getPassword().equals(password)) {
				System.out.println("Welcome back " + user.getUsername());	
				user.displayDashboard();
				return;
			}			
		}
		System.out.println("Account not found!");
		return;
	}
	
	public void signUp() {		
		System.out.println("Are you signing up as a user?");
		System.out.println("[1] Buyer");
		System.out.println("[2] Seller");
		int choiceType = sc.nextInt();
		sc.nextLine();
		
		
		System.out.println("Enter FIRST NAME: ");
		String firstName = sc.nextLine();
		
		System.out.println("Enter LAST NAME: ");
		String lastName = sc.nextLine();
		
		System.out.println("Enter USERNAME: ");
		String username = sc.nextLine();
		
		System.out.println("Enter PASSWORD: ");
		String password = sc.nextLine();
		
		System.out.println("Confirm PASSWORD");
		String confirmedPassword = sc.nextLine();
		
		if(!password.equals(confirmedPassword)) {
			System.out.println("Entered password not matched.");
			return;
			
		}
		
		System.out.println("Enter BALANCE ");
		float balance = sc.nextFloat();
		sc.nextLine();
		
		System.out.println("Enter LOCATION: ");
		String location = sc.nextLine();
		
		
		if (choiceType == 1) {
			User newUser = new Buyer(firstName, lastName, username, password, balance, location);
			userList.add(newUser);
			newUser.displayDashboard();
		} else {
			User newUser = new Seller(firstName, lastName, username, password, balance, location);
			userList.add(newUser);
			newUser.displayDashboard();

		}
		
		System.out.println("Welcome, " + username + "!");
		
		
		
	}

}
