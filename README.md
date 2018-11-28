Project Manager Case Study Build Notes:

Application Structure: Parent maven project(project-manager-aggregator) with two dependent modules - For building and packaging both service and ui into a jar

project-manager-service - Maven Spring boot project for exposing rest endpoints + MySql
project-manager-ui - Angular CLI for building UI and connects with rest endpoints exposed

Git Repository: https://github.com/smadhankumar/project-manager-aggregator.git

Maven Build Commands for the final artifacts:

clean install -e	[run the command for project-manager-service project which will build UI and service project and create the final jar with required resources]
package docker:build	[run the command for project-manager-service project which will copy the jar from target and create image in the remote docker container]

Commands to run the created image and validate the image:

Connect to the remote docker machine
To check whether the image is created in docker container dockerx image ls
To run the created image in docker dockerx run -p 8085:8089 463657-project-manager:latest
To check whether the image is running in docker[open new cmd prompt and run the cmd] dockerx ps
To validate whether the application is working fine using curl command 
i) connect to bash shell in the container. [take container id of the image created from dockers ps] 
     dockerx exec -it [CONTAINER_ID] bash 
ii) check whether application is working [it will return custom techincal error from the service exposed since mysql db is not available in docker] 
    curl  http://localhost:8085/project-manager/getTaskInfo

Commands for local development:

Make sure mysql is running in local machine
Run maven command spring-boot:run for the project project-manager-service
Check whether the endpoint is working fine in postman Endpoint : http://localhost:8089/project-manager/getTaskInfo
Open the folder src/main/web of project project-manager-ui in visual studio and then run below commands npm install npm start
Hit the url http://loalhost:4200 and see whether the page is getting loaded

Jenkins command:
Make sure Jenkins is installed and running
Configure Maven and JDK in jenkins with name maven3 and jdk1.8
Create Jenkins project with Pipeline option and configure the below information 
i)   Github repository with credentials 
ii)  Branch to build: */master 
iii) Path to Jenkinsfile

Documents for Reference:
1) Screenshots Folder
    i)   Application screenshots - AppScreenshots.docx
	ii)  Code Coverage screenshots - CodeCoverageScreenshot.docx
	iii) Docker screenshots - Docker.docx
	iv)  Jenkins screenshots - Jenkins.docx
	v)   jMeter Load Test screenshots - JMeterLoadTestReport.docx
2) TableScript Folder - project_manager_table_script.sql
3) jMeterLoadTest Folder - Images of load test reports
4) JenkinsBuild Folder - Jenkins Build reports
5) FinalBuild Folder - final build of service code base
6) EmmaCodeCoverage Folder - Code coverage reports
7) CodeBase Folder - UI and Service code base
    