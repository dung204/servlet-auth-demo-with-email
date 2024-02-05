# Authentication Demo Website

using Jakarta Servlet, JSP, JSTL, JDBC, Microsoft SQL Server, and Jakarta Mail API

## Tables of contents

- [1. Prerequisites](#1-prerequisites)
- [2. Libraries](#2-libraries)
- [3. How to run](#3-how-to-run)
  - [3.1. Clone the source code](#31-clone-the-source-code)
  - [3.2. Configure the database connection](#32-configure-the-database-connection)
    - [3.2.1. Run SQL setup script](#321-run-sql-setup-script)
    - [3.2.2. Configure the database connection in the project](#322-configure-the-database-connection-in-the-project)
  - [3.3. Configure the email service](#33-configure-the-email-service)
  - [3.4. Build the project](#34-build-the-project)
    - [3.4.1. Using a Java preferred IDE (Netbeans, Eclipse, IntelliJ IDEA, etc.)](#341-using-a-java-preferred-ide-netbeans-eclipse-intellij-idea-etc)
    - [3.4.2. Using Maven](#342-using-maven)
  - [3.5 Deploy the WAR file to Tomcat](#35-deploy-the-war-file-to-tomcat)
    - [3.5.1 Using Tomcat Web Application Manager](#351-using-tomcat-web-application-manager)
    - [3.5.2. Using manual deployment](#352-using-manual-deployment)
  - [3.6 Context Path](#36-context-path)
    - [3.6.1. For Netbeans IDE](#361-for-netbeans-ide)
    - [3.6.2. For manual WAR file deployment](#362-for-manual-war-file-deployment)
- [4. Available routes](#4-available-routes)

## 1. Prerequisites

- [Java 17+](https://www.oracle.com/java/technologies/downloads/)
- [Maven 3.8+](https://maven.apache.org/download.cgi)
- [Tomcat 10+](https://tomcat.apache.org/download-10.cgi)
- [Microsoft SQL Server (2019+ is recommended)](https://www.microsoft.com/en-us/sql-server/sql-server-downloads)

## 2. Libraries

All below libraries are listed in the [`pom.xml`](pom.xml#L24) file, and they are automatically downloaded by Maven.

- [jakarta.servlet-api 6.1.0-M1](https://central.sonatype.com/artifact/jakarta.servlet/jakarta.servlet-api): The Jakarta Servlet API
- [jakarta.servlet.jsp.jstl-api 3.0.0](https://central.sonatype.com/artifact/jakarta.servlet.jsp.jstl/jakarta.servlet.jsp.jstl-api): The Jakarta Standard Tag Library API
- [jakarta.servlet.jsp.jstl 3.0.1](https://central.sonatype.com/artifact/org.glassfish.web/jakarta.servlet.jsp.jstl): The Jakarta Standard Tag Library Implementation
- [mssql-jdbc 12.6.0.jre11](https://central.sonatype.com/artifact/com.microsoft.sqlserver/mssql-jdbc): The Microsoft SQL Server JDBC Driver
- [jakarta.mail-api 2.1.2](https://central.sonatype.com/artifact/jakarta.mail/jakarta.mail-api): The Jakarta Mail API
- [jakarta.mail 2.0.2](https://central.sonatype.com/artifact/com.sun.mail/jakarta.mail): The Jakarta Mail Implementation

## 3. How to run

### 3.1. Clone the source code

Run the following command in your terminal:

```bash
git clone https://github.com/dung204/servlet-auth-demo-with-email.git
```

### 3.2. Configure the database connection

#### 3.2.1. Run SQL setup script

- Run the SQL setup script [`setup.sql`](db/setup.sql) in your SQL Server Management Studio (SSMS).

#### 3.2.2. Configure the database connection in the project

- Open the file [`DBContext.java`](src/main/java/dal/DBContext.java#L16)
- Modify the the following variables to match your SQL Server connection: `user`, `password`, `dbName`, `serverName`, `portNumber`.

### 3.3. Configure the email service

- Create your own SMTP server, or use a public SMTP server (like Gmail, Yahoo, etc.). Some of these articles may help you:
  - [Làm thế nào để sử dụng Google Free SMTP Server](https://www.hostinger.vn/huong-dan/lam-the-nao-de-su-dung-google-smtp-server-mien-phi)
  - [Set Up Your Own SMTP Server in 2024 [Windows, Linux, Mac OS]](https://mailtrap.io/blog/setup-smtp-server/)
- Open the file [`MailService.java`](src/main/java/services/MailService.java#L16)
- Modify the the following variables to match your SMTP server: `EMAIL_FROM`, `PORT`, `SMTP_HOST`, `PASSWORD`.

### 3.4. Build the project

#### 3.4.1. Using a Java preferred IDE (Netbeans, Eclipse, IntelliJ IDEA, etc.)

- Open the project in your preferred IDE
- Choose the server instance to run the project
- Simply click the "Run" or "Build" button

#### 3.4.2. Using Maven

```bash
cd servlet-auth-demo-with-email
mvn clean package
```

### 3.5 Deploy the WAR file to Tomcat

> **⚠️ Note**: If you are using a Java preferred IDE (Netbeans, Eclipse, IntelliJ IDEA), you can skip this step and go to [2.4. Context Path](#24-context-path).

#### 3.5.1 Using Tomcat Web Application Manager

- Start Tomcat server (Run mode or Debug mode)
- Open Tomcat Manager at [http://localhost:8080/manager/html](http://localhost:8080/manager/html)
- Login with your Tomcat account
- In the "Deploy" section, you can either fill in the form of **Deploy directory or WAR file located on server** or **WAR file to deploy** to deploy the WAR file, then click "Deploy" button.
- The application will be deployed to the context path `/servlet-auth-demo-with-email` (or the context path you [defined in the `pom.xml` file](#22-context-path)).

#### 3.5.2. Using manual deployment

- Copy the WAR file to the `webapps` directory of Tomcat server
- Start Tomcat server (Run mode or Debug mode)
- The application will be deployed to the context path `/servlet-auth-demo-with-email` (or the context path you [defined in the `pom.xml` file](#22-context-path)).

### 3.6 Context Path

### 3.6.1. For Netbeans IDE

Netbeans IDE uses [`META-INF/context.xml`](src/main/webapp/META-INF/context.xml) file to define the context path of the web application. You can change the context path by modifying the `path` attribute of the `Context` element.

```xml
<?xml version="1.0" encoding="UTF-8"?>
<Context path="/servlet-auth-demo-with-email" />
```

### 3.6.2. For manual WAR file deployment

If you manually deploy WAR file to Tomcat server, the context path is the name of the WAR file, which can be modified in [the `warName` configuration of `maven-war-plugin`, in `pom.xml`](pom.xml#L104) file. By default, the context path is `/servlet-auth-demo-with-email`. You can change it like the following:

```xml
<plugin>
    <artifactId>maven-war-plugin</artifactId>
    <version>3.2.2</version>
    <configuration>
        <warName>my-awesome-context</warName>
    </configuration>
</plugin>
```

> **⚠️ Note**: Slashes (`/`) are not allowed in this configuration, if you want to include slashes, you must replace them with `#`. For example, `my/awesome/context` should be `my#awesome#context`.

## 4. Available routes

Assuming you successfully deployed, and the context path is `/servlet-auth-demo-with-email`, the available routes are:

- [http://localhost:8080/servlet-auth-demo-with-email/](http://localhost:8080/servlet-auth-demo-with-email/sign-in): Sign in page
- [http://localhost:8080/servlet-auth-demo-with-email/sign-up](http://localhost:8080/servlet-auth-demo-with-email/sign-up): Sign up page
- [http://localhost:8080/servlet-auth-demo-with-email/reset-password](http://localhost:8080/servlet-auth-demo-with-email/sign-up): Reset password page (specifying an email, then an OTP code sent to that email, then a new password)
