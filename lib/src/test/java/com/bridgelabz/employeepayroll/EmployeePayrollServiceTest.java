package com.bridgelabz.employeepayroll;	

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import static com.bridgelabz.employeepayroll.EmployeePayrollService.IOService.*;
public class EmployeePayrollServiceTest {

	@Test
	public void given3EmployeesWhenWrittenToFileShouldMatchEmployeeEntries() {
		EmployeePayrollData[] arrayOfEmps = {
				new EmployeePayrollData(1, "Jeff Bezos", 10000),
				new EmployeePayrollData(2, "Bill Gates", 20000),
				new EmployeePayrollData(3, "Mark Zuckerberg", 30000)
		};

		EmployeePayrollService employeePayrollService;
		employeePayrollService = new EmployeePayrollService(Arrays.asList(arrayOfEmps));
		employeePayrollService.writeEmployeePayrollData(FILE_IO);
		employeePayrollService.printData(FILE_IO);
		long entries = employeePayrollService.countEntries(FILE_IO);
		Assert.assertEquals(3,entries);
	}

	@Test
	public void givenFileOnReadingFromMatchEmployeeCount() {
		EmployeePayrollService employeePayrollService = new EmployeePayrollService();
		long entries = employeePayrollService.readEmployeePayrollData(FILE_IO);
		Assert.assertEquals(3,entries);
	}

	@Test
	public void givenEmployeePayrollInDB_WhenRetrived_ShouldMatchEmployeeCount()
	{
		EmployeePayrollService employeePayrollService = new EmployeePayrollService();
		List<EmployeePayrollData> employeePayrollData = employeePayrollService.readEmployeePayrollDataDB(DB_IO);
		Assert.assertEquals(3, employeePayrollData.size());
	}
	@Test
	public void givenNewSalaryForEmpoyee_WhenUpdated_ShouldSyncWithDB()
	{
		EmployeePayrollService employeePayrollService = new EmployeePayrollService();
		List<EmployeePayrollData> employeePayrollData = employeePayrollService.readEmployeePayrollDataDB(DB_IO);
		employeePayrollService.updateEmployeeSalary("Terisa",3000.00);
		boolean result = employeePayrollService.checkEmployeePayrollInsyncWithDB("Terisa");
		Assert.assertTrue(result);
	}
	
	
	@Test
	public void givenEmployee_database_ShouldRetrieve_All_the_Entries()
	{
		EmployeePayrollService employeePayrollService = new EmployeePayrollService();
		List<EmployeePayrollData> employeePayrollData = employeePayrollService.readEmployeePayrollDataDB(DB_IO);
		for(int size=0;size<employeePayrollData.size();size++)
		System.out.println(employeePayrollData.get(size));
	}
	@Test
	public void givenDateRange_WhenQueried_ShouldReturnEmployeeList()
	{
		EmployeePayrollDBService employeePayrollService = new EmployeePayrollDBService();
		List<EmployeePayrollData> empList = employeePayrollService.getEmployeesInDateRange("2019-01-01","2021-01-01");
		System.out.println(empList);
		Assert.assertEquals(4, empList.size());
	}
	
	@Test
	public void givenEmployeePayrollInDB_ShouldReturnSumOfSalaryBasedOnGender() {
		
		EmployeePayrollDBService employeePayrollService = new EmployeePayrollDBService();
		Map<Character, Double> salaryMap = employeePayrollService.getGenderWiseTotalSalary();
		Assert.assertEquals((double)salaryMap.get('F'),3000,0.0);
		Assert.assertEquals((double)salaryMap.get('M'),900000,0.0);
	}
	
	@Test
	public void givenEmployeePayrollInDB_ShouldReturnMinSalaryBasedOnGender() {
		EmployeePayrollDBService employeePayrollService = new EmployeePayrollDBService();
		Map<Character, Double> salaryMap = employeePayrollService.getGenderWiseMinSalary();
		Assert.assertEquals((double)salaryMap.get('F'),3000,0.0);
		Assert.assertEquals((double)salaryMap.get('M'),100000,0.0);
		
	}
	
	@Test
	public void givenEmployeePayrollInDB_ShouldReturnAverageSalaryBasedOnGender() {
		EmployeePayrollDBService employeePayrollService = new EmployeePayrollDBService();
		Map<Character, Double> salaryMap = employeePayrollService.getGenderWiseAvgSalary();
		Assert.assertEquals((double)salaryMap.get('F'),3000,0.0);
		Assert.assertEquals((double)salaryMap.get('M'),250000,0.0);
		
	}
	
	@Test
	public void givenEmployeePayrollInDB_ShouldReturnMaximumSalaryBasedOnGender() {
		EmployeePayrollDBService employeePayrollService = new EmployeePayrollDBService();
		Map<Character, Double> salaryMap = employeePayrollService.getGenderWiseMaxSalary();
		Assert.assertEquals((double)salaryMap.get('F'),3000,0.0);
		Assert.assertEquals((double)salaryMap.get('M'),500000,0.0);
		
	}
	@Test
	public void givenDepartment_WhenInserted_ShouldReturnOne(){
		EmployeePayrollDBService employeePayrollService = new EmployeePayrollDBService();
		int result = employeePayrollService.insertDepartment(new Department( "D004","Engineering" ,"Virat"));
		Assert.assertEquals(1,result);
	}
	@Test
	public void givenCompany_WhenInserted_ShouldReturnOne(){
		EmployeePayrollDBService employeePayrollService = new EmployeePayrollDBService();
		int result = employeePayrollService.insertCompany(new Company( "Uber","3"));
		Assert.assertEquals(1,result);
	}
	@Test
	public void givenEmployeePayrollInDB_ShouldReturnCountOfBasedOnGender() {
		EmployeePayrollDBService employeePayrollService = new EmployeePayrollDBService();
		Map<Character, Integer> countMap = employeePayrollService.getGenderWiseCount();
		Assert.assertEquals((int)countMap.get('F'),1);
		Assert.assertEquals((int)countMap.get('M'),4);
		
	}

	@Test
	@AfterAll
	public void givenListOfEmployees_WhenInserted_ShouldMatchEmployeeEntries() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
		String date = "16/08/2019";
		LocalDate startDate = LocalDate.parse(date, formatter);
		EmployeePayrollService employeePayrollService = new EmployeePayrollService(new ArrayList<>()); 
		employeePayrollService.addEmployeeToPayroll("arun",100000.00,startDate,'M',"1","1" );
		boolean result = employeePayrollService.checkEmployeePayrollInsyncWithDB("arun");
		Assert.assertTrue(result);
	}
}