package com.yash.es.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.google.gson.Gson;
import com.yash.es.config.EsConfig;
import com.yash.es.model.Employee;

@Repository
//@PropertySource("classpath:application.properties")
public class EmpDaoImpl implements EmpDao {

	//@Autowired
	//Environment env;
		 
	@Autowired
	RestHighLevelClient client;
	
	@Autowired
	Gson gson;
	
	@Autowired
	EsConfig esconfig;

	 
	public Map<String, Object> insertQuery(Employee employee, Map<String, Object> dataMap) {
		GetResponse response = null;
		Map<String, Object> error = null;
		try {
			IndexRequest indexRequest = new IndexRequest(esconfig.getIndexname(), esconfig.getIndextype(),
					employee.getEmpId()).source(dataMap);
			IndexResponse indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
			if (indexResponse == null)
				error.put("error", "Record does not exist");
			GetRequest getRequest = new GetRequest(esconfig.getIndexname(), esconfig.getIndextype(),
					employee.getEmpId());
			response = client.get(getRequest);
			Map<String, Object> sourceAsMap = response.getSourceAsMap();
			System.out.println(sourceAsMap);
			return sourceAsMap;
		} catch (Exception e) {
			e.getStackTrace();

		}

		return error;
	}

	@Override
	public Map<String, Object> fetchQuery(String empId, boolean flag) {
		GetResponse response = null;
		Map<String, Object> error = null;
		try {
			GetRequest getRequest = new GetRequest(esconfig.getIndexname(), esconfig.getIndextype(), empId);
			response = client.get(getRequest);
			if (response == null)
				error.put("error", "Record does not exist");
			Map<String, Object> sourceAsMap = response.getSourceAsMap();
			System.out.println(sourceAsMap);
			return sourceAsMap;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return error;
	}

	@Override
	public Map<String, Object> updateQuery(String empId, String employeeJson) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String deleteQuery(String empId) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<Map<String, Object>> matchQuery(Map<String, String> headerMap) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Map<String, Object> partialUpdatematchQuery(Map<String, String> headerMap, String empID) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/*public Map<String, Object> fetchQuery(String empId,boolean flag) {
		Map<String, Object> sourceAsMap=null;
		if(flag==true) {
		GetResponse response = null;
		//GetRequest getRequest = new GetRequest(env.getProperty("indexname"),env.getProperty("indextype"), empId);
		GetRequest getRequest = new GetRequest(esconfig.getIndexname(),esconfig.getIndextype(), empId);
		ActionFuture<GetResponse> getResponse = null;
		try {
			getResponse = client.get(getRequest);
			response = getResponse.actionGet();
		} catch (Exception e) {
			e.getLocalizedMessage();
		}
		sourceAsMap = response.getSourceAsMap();
		}else {
			QueryBuilder qb = QueryBuilders.matchQuery("EmpId",empId );
			//QueryBuilder qb = QueryBuilders.matchAllQuery();
			//QueryBuilder qb = QueryBuilders.queryStringQuery("EmpId:" + empId);
			SearchResponse sr = client.prepareSearch(env.getProperty("indexname"))
	                .setQuery(qb).execute()
	                .actionGet();
			SearchResponse sr = client.prepareSearch(esconfig.getIndexname())
	                .setQuery(qb).execute()
	                .actionGet();
			System.out.println(sr);
			SearchHits hits = sr.getHits();
			//List<Map<String, Object>> lm=new LinkedList<>();
			for (SearchHit hit : hits) {
				
				// always returns fine
				
				System.out.println(hit.getId());
				sourceAsMap=hit.getSourceAsMap();
				//lm.add(sourceAsMap);
				
				// will be null if any fields specified
				//System.out.println(hit.getSourceAsString());
			}
			//System.out.println(lm);
			
		}

		return sourceAsMap;
	}
	
	public Map<String, Object> updateQuery(String empId, String employeeJson){
		UpdateResponse response = null;
		//UpdateRequest updateRequest = new UpdateRequest(env.getProperty("indexname"),env.getProperty("indextype"), empId).fetchSource(true); // Fetch Object after its
		UpdateRequest updateRequest = new UpdateRequest(esconfig.getIndexname(),esconfig.getIndextype(), empId).fetchSource(true); // Fetch Object after its																						// update
		Map<String, Object> error = new HashMap<>();
		error.put("Error", "Unable to update employee");
		try {
			
			updateRequest.doc(employeeJson, XContentType.JSON);
			ActionFuture<UpdateResponse> updateResponse = client.update(updateRequest);
			response = updateResponse.actionGet();
			Map<String, Object> sourceAsMap = response.getGetResult().sourceAsMap();
			return sourceAsMap;
		} catch (Exception e) {
			e.getMessage();
		}
		
		return error;
	}
	
	public String deleteQuery(String empId) {
		DeleteResponse response = null;
		//DeleteRequest deleteRequest = new DeleteRequest(env.getProperty("indexname"),env.getProperty("indextype"), empId);
		DeleteRequest deleteRequest = new DeleteRequest(esconfig.getIndexname(),esconfig.getIndextype(), empId);
		ActionFuture<DeleteResponse> deleteResponse = client.delete(deleteRequest);
		response = deleteResponse.actionGet();
		if (!response.getResult().toString().equals("NOT_FOUND"))
			return response.getResult().toString() + " sucessfully";
		else
			return "Pleasae input existed Employee ID";

	}
	
	public List<Map<String, Object>> matchQuery(Map<String, String> headerMap) {
		 Map<String, Object> sourceAsMap=new HashMap<>();
		 ArrayList<Map<String, Object>> employeeList=new ArrayList<>();
		 BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
		 Iterator<String> iterator = headerMap.keySet().iterator();
		

		    while (iterator.hasNext()) {
		        String propertyName = iterator.next();
		        String propertValue = headerMap.get(propertyName);
		        MatchQueryBuilder matchQuery = QueryBuilders.matchQuery(propertyName, propertValue);
		        boolQueryBuilder.must(matchQuery);
		    }
		    SearchResponse sr = client.prepareSearch(env.getProperty("indexname"))
	                .setQuery(boolQueryBuilder).execute()
	                .actionGet();
		    SearchResponse sr = client.prepareSearch(esconfig.getIndexname())
	                .setQuery(boolQueryBuilder).execute()
	                .actionGet();
		     SearchHits hits = sr.getHits();
		   
			
			for (SearchHit hit : hits) {
				sourceAsMap= hit.getSourceAsMap();
				employeeList.add(sourceAsMap);
				
			}

           return employeeList;
	}
	
	public Map<String, Object> partialUpdatematchQuery(Map<String, String> headerMap,String empId){
		
		UpdateResponse response = null;
		
		UpdateRequest updateRequest = new UpdateRequest(env.getProperty("indexname"),env.getProperty("indextype"), empId)
		        .doc(headerMap);
		UpdateRequest updateRequest = new UpdateRequest(esconfig.getIndexname(),esconfig.getIndextype(), empId)
		        .doc(headerMap);
		ActionFuture<UpdateResponse> updateResponse = client.update(updateRequest);
		Map<String, Object> sourceAsMap=fetchQuery(empId,false);
		
		return sourceAsMap;
		
	}*/
}
