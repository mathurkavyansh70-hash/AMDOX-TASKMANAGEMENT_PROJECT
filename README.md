# AMDOX-TASKMANAGEMENT_PROJECT

## Completion of My Java project of taskmanagementAPI as an intern in AMDOX

# Tech Stack
Java  
Spring Boot  
Spring Data JPA  
Spring Security  
PostgreSQL  
Maven Buld Tool    


   
# application.properties  
 
spring.application.name=TaskManagement3 
## Database configuration  
spring.datasource.url=jdbc:postgresql://localhost:5432/Your_DB  
spring.datasource.username=yout_userName  
spring.datasource.password=your_pass  
spring.datasource.driver-class-name=org.postgresql.Driver  
## server configuration  
server.port=8080  
## JPA configuration  
spring.jpa.hibernate.ddl-auto=update  
spring.jpa.show-sql=true  
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect  
#Email configuration  
spring.mail.host=smtp.example.com  
spring.mail.port=587  
spring.mail.username=your-email@example.com  
spring.mail.password=your-email-password  
spring.mail.properties.mail.smtp.auth=true  
spring.mail.properties.mail.smtp.starttls.enable=true   
## Cloudinary configuration  
Cloudinary:  
            cloud-name=YOUR_CLOUD_NAME  
            api-key=YOUR_API_KEY  
            api-secret=YOUR_API_SECRET  
              
#FileUpload   
spring.servlet.multipart.max-file-size=5MB  
spring.servlet.multipart.max-request-size=5MB  
