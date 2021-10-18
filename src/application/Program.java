package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Collectors;

import entities.Employee;

public class Program {

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);
		
		System.out.print("Enter full file path: ");
		String path = sc.nextLine();
		
		System.out.print("Enter Salary: ");
		double salaryFilter = sc.nextDouble();
		
		try(BufferedReader br = new BufferedReader(new FileReader(path))){
			
			List<Employee> emp = new ArrayList<>();
			
			//lendo arquivo e adicionando linha por linha na lista
			String line = br.readLine();
			
			while(line != null) {
				String[] fields = line.split(",");
				emp.add(new Employee(fields[0], fields[1], Double.parseDouble(fields[2])));
				line = br.readLine();
			}
			
			Comparator<String> comp = (s1, s2) -> s1.toUpperCase().compareTo(s2.toUpperCase());
		
			List<String> listEmail = emp.stream()
					.filter(p -> p.getSalary() > salaryFilter)
					.map(p -> p.getEmail()).sorted(comp)
					.collect(Collectors.toList());
			
			listEmail.forEach(System.out::println);
					
			double sum = emp.stream()
					.filter(p -> p.getName().charAt(0) == 'M')
					.map(p -> p.getSalary())
					.reduce(0.0, (x,y) -> x + y);
			
			System.out.printf("Sum of salary of people whose name starts with 'M': f " +  String.format("%.2f", sum));
			
		}catch(IOException e) {
			System.out.println("Error "+ e.getMessage());
		}

		sc.close();
	}
}