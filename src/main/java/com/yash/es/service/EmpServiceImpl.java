package com.yash.es.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.google.gson.Gson;
import com.yash.es.dao.EmpDao;
import com.yash.es.model.Employee;

@Service
public class EmpServiceImpl implements EmpService {

	@Autowired
	Gson gson;

	@Autowired
	EmpDao empDao;

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> insertEmployeeRecord(Employee employee) {

		// empDao.creteQuery(employee);
		Map<String, Object> response = null;
		//employee.setEmpId(employee.getEmpId().toString());
		Map<String, Object> dataMap = new HashMap<String, Object>();
		String jsonInString = gson.toJson(employee);

		dataMap = (Map<String, Object>) gson.fromJson(jsonInString, dataMap.getClass());
		response = empDao.insertQuery(employee, dataMap);
		return response;
	}

	@Override
	public Map<String, Object> fetchEmployeeRecord(String empId, boolean flag) {
		// TODO Auto-generated method stub
		Map<String, Object> response = null;
		
		response = empDao.fetchQuery(empId,flag);
		return response;
		
	}

	@Override
	public Map<String, Object> updateEmployeeRecord(Employee employee, String empId) {
		// TODO Auto-generated method stub
		Map<String, Object> response = null;
		String employeeJson = gson.toJson(employee);
		response = empDao.updateQuery(empId, employeeJson);
		return response;
	}
	public Map<String, Object> partialUpdateEmployeeRecord(Map<String, String> headerMap,String empID){
		Map<String, Object> response = null;
		response=empDao.partialUpdatematchQuery(headerMap,empID);
		//String employeeJson = gson.toJson(employee);
		//response = empDao.updateQuery(empId, employeeJson);
		return response;
	}

	@Override
	public String deleteEmployeeRecord(String empId) {
		// TODO Auto-generated method stub
		String response = null;
		response = empDao.deleteQuery(empId);

		return response;
	}
	
	public List<Map<String, Object>> fetchQueryBuilder(Map<String, String> headerMap) {
		 
		 List<Map<String, Object>> response=empDao.matchQuery(headerMap);
		
		
		return response;
	}

}
