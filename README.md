To start the frontend (**prerequisites:** **node.js** and **angular v18**) from 
the _/titanic-app/_ directory, run the following commands in sequence:

<code> npm install</code>
<p>
<code>ng serve</code>

To start the backend (**prerequisite:** **PostgreSQL v12**(with created a 
database with the name **titanic** and username **admin**, password **admin** 
or specified your values in _/src/main/resources/application.properties_ file)) 
for the first time, run the following command from the root directory:

<code> mvnw verify spring-boot:run -Dspring-boot.run.arguments="-l"</code>

For the next runs:

<code> mvnw clean verify spring-boot:run</code>