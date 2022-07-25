RESTful Service
**********************

This project is the RESTful Service, which provides the opportunity to create new users, update, delete or read them.
---

This project contains 2 solutions: 
Common Solution
Solution with Spring Data Elasticsearch
---

Common Solution solves the task without any external databases and stores all data in RAM.
Solution with Spring Data Elasticsearch solves the task using external database Elasticsearch and stores all data there.
---

This project contains docker-compose.yaml and Dockerfile, therefore the project is ready to be deployed to Docker.
Command to deploy the project to Docker: docker-compose up
**********************

**********************
Possible REST requests:
---------
---GET---
url: /user
---
url: /user/{id} ##In case if any data store by this id
---
url: /elasticsearch/user/{id} ##In case if any data store by this id
---------

---POST--
url: /user
expected body:
{
    "email": "someone@gmail.com",
    "firstName": "First name",
    "lastName": "Last Name"
}
---
url: /elasticsearch/user/{id}
expected body:
{
    "email": "someone@gmail.com",
    "firstName": "First name",
    "lastName": "Last Name"
}

notes:
- email must have email-body (***@**.**), otherwise the request will be rejected
- if the required attributes are missing, the request will be rejected
- if the number of attributes is greater or less than 3, the request will be rejected
- the id in common solution calculated automatically
- the id in elasticsearch solution is necessary to specify in url
---------

--PATCH--
url: /user/{id}
expected body:
{
    "email": "someone@gmail.com",
    "firstName": "First name",
    "lastName": "Last Name"
}
---
url: /elasticsearch/user/{id}
expected body:
{
    "email": "someone@gmail.com",
    "firstName": "First name",
    "lastName": "Last Name"
}

note:
- if the user doesn't exist, the request will be rejected
---------

-DELETE--
url: /user/{id}
---
url: /elasticsearch/user/{id}

note:
- if user doesn't exist, the request will be rejected
**********************

**********************
To execute the project on the local machine without docker:
- execute Elasticsearch from Docker or on the local machine
- change url in application.properties from "elasticsearchUrl=es762:9200" to "elasticsearchUrl=localhost:9200"

**********************
To check the work of the project:
- open in any browser the url: localhost:8080/user
or
- send the GET request to url: localhost:8080/user
