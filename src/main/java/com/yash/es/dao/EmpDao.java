package com.yash.es.dao;

import java.util.List;
import java.util.Map;

import org.elasticsearch.search.SearchHit;
import org.springframework.stereotype.Repository;

import com.yash.es.model.Employee;

@Repository
public interface EmpDao {

	public Map<String, Object> insertQuery(Employee employee, Map<String, Object> dataMap);

	public Map<String, Object> fetchQuery(String empId, boolean flag);


	public Map<String, Object> updateQuery(String empId, String employeeJson);

	public String deleteQuery(String empId);

	public List<Map<String, Object>> matchQuery(Map<String, String> headerMap);

	public Map<String, Object> partialUpdatematchQuery(Map<String, String> headerMap,String empID);
	

	
}
