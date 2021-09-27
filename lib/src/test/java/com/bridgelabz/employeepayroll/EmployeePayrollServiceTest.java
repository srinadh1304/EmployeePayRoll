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
		Assert.assertEquals(4, employeePayrollData.size());
	}
	@Test
	public void givenNewSalaryForEmpoyee_WhenUpdated_ShouldSyncWithDB()
	{
		EmployeePayrollService employeePayrollService = new EmployeePayrollService();
		List<EmployeePayrollData> employeePayrollData = employeePayrollService.readEmployeePayrollDataDB(DB_IO);
		employeePayrollService.updateEmployeeSalary("Terisa",300000000.00);
		employeePayrollData = employeePayrollService.readEmployeePayrollDataDB(DB_IO);
		System.out.println(employeePayrollData);
	}
	@Test
	@AfterAll
	public void givenListOfEmployees_WhenInsertedToList_ShouldMatchEmployeeEntries() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String date = "16/08/2019";
		LocalDate startDate1 = LocalDate.parse(date, formatter);
		date = "01/08/2020";
		LocalDate startDate2 = LocalDate.parse(date, formatter);

		EmployeePayrollData[] arrayOfEmps = {
				new EmployeePayrollData(5, "Jeff Bezos",'M', 10000,startDate1),
				new EmployeePayrollData(6, "Bill Gates",'M', 20000,startDate2)
		};
		
		EmployeePayrollService employeePayrollService;
		employeePayrollService = new EmployeePayrollService(Arrays.asList(arrayOfEmps));
		employeePayrollService.writeEmployeePayrollData(DB_IO);
		long entries = employeePayrollService.countEntries(DB_IO);
		Assert.assertEquals(6,entries);
	}
}