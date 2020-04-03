### Elastic search using spring boot

- Sample project showing how to integrate elastic search using spring data elastic search






### Setup
- Download and setup Elastic server on your machine and run the elasticsearch.bat(for windows)
- Create elastic search index using post man.
   The index can be created by a put request :
     **URL**: localhost:9200/employee_index?include_type_name=true
     **Method**: PUT
     **Body** : [paste contents of  employee_index.json]
	 
- Build with tests
    On building the application with tests, the tests will create necessary tables in the in-memory H2 db and add documents to the elastic search index. The tests also have an example to search the document from elastic search.

- Persisting data in actual db
   IF you want to persist the data in a real database, then you need to have an Oracle instance (could also setup local oracle dev edition db) and create the following user on the database:
   
		create user EMPLOYEE_ES identified by test1234; 

		grant CREATE SESSION, ALTER SESSION, CREATE DATABASE LINK, 
		CREATE MATERIALIZED VIEW, CREATE PROCEDURE, 
		CREATE PUBLIC SYNONYM,
		CREATE ROLE, CREATE SEQUENCE, CREATE SYNONYM, CREATE TABLE,  
		CREATE TRIGGER, CREATE TYPE, CREATE VIEW, UNLIMITED TABLESPACE 
		to EMPLOYEE_ES; 

- Sample Search Json

{
		  "conditions" : [ {
			"fieldName" : "employeeType",
			"operation" : "EQ",
			"value1" : "PROJECT_LEADER",
			"value2" : null
		  }, {
			"fieldName" : "firstName",
			"operation" : "EQ",
			"value1" : "Cristiano",
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
		
