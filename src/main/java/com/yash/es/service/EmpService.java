package com.yash.es.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yash.es.model.Employee;

public interface EmpService {

	public Map<String, Object> insertEmployeeRecord(Employee Employee);
	
	public Map<String, Object> fetchEmployeeRecord(String empId, boolean flag);
	
	public Map<String, Object> updateEmployeeRecord(Employee Employee, String empId);
	
	public String deleteEmployeeRecord(String empId);

	public List<Map<String, Object>> fetchQueryBuilder(Map<String, String> headerMap);

	public Map<String, Object> partialUpdateEmployeeRecord(Map<String, String> headerMap, String empID);
		
	
}
