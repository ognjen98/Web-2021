package main;

import java.util.Date;

import service.OrderService;

public class Main {

	public static void main(String[] args) {
		System.out.println("Caoooooo");
		OrderService os = new OrderService();
		
		String str = os.generateRandomString();
		System.out.println(str);
		System.out.println(new Date());
	}

}
