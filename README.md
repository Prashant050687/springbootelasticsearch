### Elastic search using spring boot

- Sample project showing how to integrate elastic search using spring data elastic search






### Setup
- Download and setup Elastic server on your machine and run the : <b> elasticsearch.bat </b>(for windows)
- Install phonectic search plugin : <b> elasticsearch-plugin install analysis-phonetic</b>(for windows)
- Create elastic search index using post man.
   The index can be created by a put request :
   <br/>
     **URL**: localhost:9200/employee_index?include_type_name=true
     <br/>
     **Method**: PUT
     <br/>
     **Body** : [paste contents of  employee_index.json]
	 
- Build with tests<br/>
    On building the application with tests, the tests will create necessary tables in the in-memory H2 db and add documents to the elastic search index. The tests also have an example to search the document from elastic search.


- Persisting data in actual db<br/>
   IF you want to persist the data in a real database, then you need to have an Oracle instance (could also setup local oracle dev edition db) and create the following user on the database:
   
		create user EMPLOYEE_ES identified by test1234; 

		grant CREATE SESSION, ALTER SESSION, CREATE DATABASE LINK, 
		CREATE MATERIALIZED VIEW, CREATE PROCEDURE, 
		CREATE PUBLIC SYNONYM,
		CREATE ROLE, CREATE SEQUENCE, CREATE SYNONYM, CREATE TABLE,  
		CREATE TRIGGER, CREATE TYPE, CREATE VIEW, UNLIMITED TABLESPACE 
		to EMPLOYEE_ES; 

- Swagger Endpoint<br/>
   http://localhost:7001/swagger-ui.html


- Sample Create Employee Json
```json
{
  "version" : 0,
  "firstName" : "Test",
  "lastName" : "Employee",
  "salary" : 2000.00,
  "contractType" : {
    "id" : 1,
    "type" : "Permanent"
  },
  "employeeType" : "STANDARD_EMPLOYEE"

}


```		
		
- Sample Create Project Leader Json
```json
{
  "version" : 0,
  "firstName" : "Project",
  "lastName" : "Leader",
  "salary" : 3000.11,
  "contractType" : {
    "id" : 1,
    "type" : "Permanent"
  },
  "employeeType" : "PROJECT_LEADER",
  "projectName" : "Test_1234",
  "reportingEmployees" : 100
}


```

- Sample Create Contractual Project Leader Json
```json
{
  "version" : 0,
  "firstName" : "Christian",
  "lastName" : "Pearson",
  "salary" : 3000.11,
  "contractType" : {
    "id" : 2,
    "type" : "Contractual"
  },
  "employeeType" : "PROJECT_LEADER",
  "projectName" : "Test_1234",
  "reportingEmployees" : 1
}


```


- Sample Search Json
```json
{
  "conditions" : [ {
	"fieldName" : "employeeType",
	"operation" : "EQ",
	"value1" : "PROJECT_LEADER",
	"value2" : null
  }, {
	"fieldName" : "firstName",
	"operation" : "EQ",
	"value1" : "Christian",
	"value2" : null
  }, {
	"fieldName" : "firstName",
	"operation" : "EQ",
	"value1" : "Project",
	"value2" : null
  }, {
	"fieldName" : "salary",
	"operation" : "GT",
	"value1" : "2500",
	"value2" : null
  } ]
}


```

- Sample search json wih nested fields and phonetic
```json
{
   "conditions":[
      {
         "fieldName":"employeeType",
         "operation":"EQ",
         "value1":"PROJECT_LEADER",
         "value2":null
      },
      {
         "fieldName":"lastName",
         "operation":"EQ",
         "value1":"Pierson",
         "value2":null
      },
      {
         "fieldName":"contractType.type",
         "operation":"EQ",
         "value1":"Contractual",
         "value2":null
      }
   ]
}
```