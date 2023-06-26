package application;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.Set;

import model.entities.Sale;

public class Program {
	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);
		List<Sale> sales = new ArrayList<>();

		System.out.print("Entre o caminho do arquivo: ");
		String path = sc.nextLine();
		Set<String> sellers = new HashSet<>();
		int maxLength = 0;
		
		try (BufferedReader in = new BufferedReader(new FileReader(path))) {
			while (in.ready()) {
				String line = in.readLine();
				String[] vect = line.split(",");
				int month = Integer.parseInt(vect[0]);
				int year = Integer.parseInt(vect[1]);
				String seller = vect[2];
				int items = Integer.parseInt(vect[3]);
				double total = Double.parseDouble(vect[4]);
				sales.add(new Sale(month, year, seller, items, total));
				sellers.add(seller);
				
				if (Integer.compare(maxLength, seller.length()) < 0) 
					maxLength = seller.length();
			}
			
			System.out.println();
			
			System.out.println("Total de vendas por vendedor:");
			sellers.stream().forEach(seller -> {
				double sum = 0;
				sum = sales.stream().filter(sale -> sale.getSeller().equals(seller)).map(s -> s.getTotal()).reduce(0.0, (x, y) -> x + y);
				System.out.printf("%s - R$ %.2f%n", seller, sum);
			});
		} catch (FileNotFoundException e) {
			System.out.printf("Erro: %s", e.getMessage());
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
			sc.close();
		}
	}
}
