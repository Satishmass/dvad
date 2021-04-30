# OCI Extension

To use this connector

1. Clone/Import to your local repository

2. Go the Cloned folder and run
    mvn install -DskipTests
    
3. Create a new Anypoint Project/Application
4. Open its pom.xml and add this dependency to . For example 
   <dependency>
			<groupId>org.oracle.oci</groupId>
			<artifactId>oci-connector</artifactId>
			<version>0.0.x</version>
			<classifier>mule-plugin</classifier>
		</dependency>
    
5. You should be able to see connector in the pallatte as 'OCI'

6. Please take a look at the PDF (available in the repository) for more details.
