MSA CRUD WITH JWT - COMPLETE PRACTICAL STEPS
================================================

Purpose
-------
Build two microservices:
1. MSAResource - REST CRUD service, EJB, JPA, MySQL, JWT security
2. MSAClient   - REST client and servlet that sends JWT

Architecture
------------
Browser
   |
   v
MSAClient : 8086
   |
   | REST Client + JWT
   v
MSAResource : 8085
   |
   v
MySQL : 3306


PART 1 - CREATE PROJECTS
========================

Create two Maven projects:

MSAResource
MSAClient

Use the jakarta-9-microprofile archetype from the practical instructions.

Expected structure:

MSAResource
  src/main/java
  src/main/resources
  artifact/MSAResource.war
  pom.xml

MSAClient
  src/main/java
  src/main/resources
  artifact/MSAClient.war
  pom.xml


PART 2 - POM.XML
================

Use Jakarta EE 10 and MicroProfile 6 for the project described in the practical:

<dependencies>

    <dependency>
        <groupId>jakarta.platform</groupId>
        <artifactId>jakarta.jakartaee-api</artifactId>
        <version>10.0.0</version>
        <scope>provided</scope>
    </dependency>

    <dependency>
        <groupId>org.eclipse.microprofile</groupId>
        <artifactId>microprofile</artifactId>
        <version>6.0</version>
        <type>pom</type>
        <scope>provided</scope>
    </dependency>

</dependencies>

Then replace old namespaces throughout the project:

Find:
javax

Replace with:
jakarta

IMPORTANT:
Do not blindly mix Jakarta EE 10, Jakarta EE 11, Payara 5, Payara 6, and Payara 7.
The project dependencies must match the Payara version you actually run.


PART 3 - MSARESOURCE
====================

MSAResource is the server/resource microservice.

It provides:
- Create User
- Read User
- Update User
- Delete User


PART 4 - USER ENTITY
====================

Create:

User.java

Example:

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String phone;

    // getters and setters
}

Use jakarta.persistence.* imports.


PART 5 - MYSQL DATABASE
=======================

Start MySQL.

Create database:

CREATE DATABASE msa_db;

Check MySQL:

netstat -ano | findstr :3306

Port 3306 should be listening.


PART 6 - PERSISTENCE.XML
========================

Location:

MSAResource/src/main/resources/META-INF/persistence.xml

Example:

<persistence xmlns="https://jakarta.ee/xml/ns/persistence"
             version="3.0">

    <persistence-unit name="MSAPU" transaction-type="JTA">

        <jta-data-source>jdbc/mysql</jta-data-source>

        <properties>
            <property name="jakarta.persistence.schema-generation.database.action"
                      value="create"/>
        </properties>

    </persistence-unit>

</persistence>

IMPORTANT:
The JDBC resource name jdbc/mysql must exactly match the Payara JDBC resource.


PART 7 - USERBEAN
=================

Create:

UserBean.java

Example:

@Stateless
public class UserBean {

    @PersistenceContext(unitName = "MSAPU")
    private EntityManager em;

    public void create(User user) {
        em.persist(user);
    }

    public User find(Long id) {
        return em.find(User.class, id);
    }

    public List<User> findAll() {
        return em.createQuery(
            "SELECT u FROM User u",
            User.class
        ).getResultList();
    }

    public void update(User user) {
        em.merge(user);
    }

    public void delete(Long id) {
        User user = em.find(User.class, id);

        if (user != null) {
            em.remove(user);
        }
    }
}


PART 8 - USERSERVICE
====================

Create:

UserService.java

Example:

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserService {

    @EJB
    private UserBean userBean;

    @POST
    @RolesAllowed("chief")
    public void create(User user) {
        userBean.create(user);
    }

    @GET
    @RolesAllowed("chief")
    public List<User> getAll() {
        return userBean.findAll();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed("chief")
    public User get(@PathParam("id") Long id) {
        return userBean.find(id);
    }

    @PUT
    @RolesAllowed("chief")
    public void update(User user) {
        userBean.update(user);
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("chief")
    public void delete(@PathParam("id") Long id) {
        userBean.delete(id);
    }
}


PART 9 - BOOTSTRAP
==================

Configure the JAX-RS application.

Conceptually:

@ApplicationPath("rest")
@DeclareRoles({"chief", "admin"})
public class Bootstrap extends Application {
}

The practical document also uses:

@LoginConfig(authMethod = "MP-JWT")

Use the Jakarta/Payara-compatible annotation and import for the Payara version you are actually using.

Do NOT use old javax.* imports.


PART 10 - JWTENIZER
===================

For the easiest practical setup, use JWTenizer.

Keep jwtenizr.jar in your working folder.

Run:

java -jar jwtenizr.jar

It generates JWT-related configuration/files.

You need:
- issuer
- public key
- JWT token

Copy the generated MicroProfile JWT configuration into:

MSAResource/src/main/resources/META-INF/microprofile-config.properties

Example:

mp.jwt.verify.issuer=YOUR_ISSUER
mp.jwt.verify.publickey=YOUR_PUBLIC_KEY

IMPORTANT:
The issuer must match the issuer inside the JWT.


PART 11 - USERCLIENT
====================

In MSAClient create:

UserClient.java

Example:

@RegisterRestClient(configKey = "userclient")
@ClientHeaderParam(
    name = "Authorization",
    value = "{getToken}"
)
public interface UserClient {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    List<User> getAll();

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    User get(@PathParam("id") Long id);

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    void create(User user);

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    void update(User user);

    @DELETE
    @Path("/{id}")
    void delete(@PathParam("id") Long id);

    default String getToken() {

        Config config = ConfigProvider.getConfig();

        String token =
            config.getValue("jwt", String.class);

        return "Bearer " + token;
    }
}

The important flow is:

UserClient
   |
   v
getToken()
   |
   v
jwt property
   |
   v
Bearer <JWT>
   |
   v
Authorization header
   |
   v
MSAResource


PART 12 - CLIENT CONFIGURATION
==============================

Create:

MSAClient/src/main/resources/META-INF/microprofile-config.properties

Add:

userclient/mp-rest/url=http://localhost:8085/MSAResource/rest/user

jwt=YOUR_JWT_TOKEN

Example:

userclient/mp-rest/url=http://localhost:8085/MSAResource/rest/user
jwt=eyJhbGciOiJSUzI1NiJ9....

DO NOT write:

jwt=Bearer eyJ...

The Java code already adds:

Bearer + token


PART 13 - SERVLET
=================

Inject the REST client:

@Inject
@RestClient
UserClient userClient;

Examples:

List<User> users = userClient.getAll();

userClient.create(user);

userClient.update(user);

userClient.delete(id);


PART 14 - PAYARA JDBC
=====================

Required flow:

MySQL Driver
     |
     v
JDBC Connection Pool
     |
     v
JDBC Resource
     |
     v
persistence.xml

Example names:

Pool:
mysqlPool

Resource:
jdbc/mysql

persistence.xml:
<jta-data-source>jdbc/mysql</jta-data-source>

Make sure the names match exactly.


PART 15 - MYSQL DRIVER
======================

Make sure your MySQL Connector/J file actually exists.

Example:

mysql-connector-java-8.0.30.jar

Do not use:

mysql-connector-java-8.0.20.jar

unless that exact file exists.

Check:

dir mysql-connector-java-8.0.20.jar

or:

dir mysql-connector-java-8.0.30.jar


PART 16 - BUILD MSARESOURCE
===========================

Open terminal in MSAResource.

Run:

mvn clean package

Expected WAR:

MSAResource.war


PART 17 - BUILD MSACLIENT
=========================

Open terminal in MSAClient.

Run:

mvn clean package

Expected WAR:

MSAClient.war


PART 18 - RUN MSARESOURCE
=========================

The old practical command is:

java -jar payara.jar --deploy MSAResource/artifact/MSAResource.war --port 8085 --addlibs mysql-connector-java-8.0.20.jar --domainconfig domain.xml

Only use it if these files really exist:

payara.jar
mysql-connector-java-8.0.20.jar
domain.xml

Check:

dir payara.jar
dir mysql-connector-java-8.0.20.jar
dir domain.xml

If your actual driver is 8.0.30, use:

java -jar payara.jar --deploy MSAResource/artifact/MSAResource.war --port 8085 --addlibs mysql-connector-java-8.0.30.jar --domainconfig domain.xml


PART 19 - RUN MSACLIENT
=======================

Open a second terminal.

Run:

java -jar payara.jar --deploy MSAClient/artifact/MSAClient.war --port 8086

Ports:

MSAResource = 8085
MSAClient   = 8086


PART 20 - TEST
==============

Resource endpoint:

http://localhost:8085/MSAResource/rest/user

Client application:

http://localhost:8086/MSAClient/

Flow:

Browser
   |
   v
MSAClient
   |
   v
UserClient
   |
   v
JWT
   |
   v
MSAResource
   |
   v
@RolesAllowed("chief")
   |
   v
UserBean
   |
   v
MySQL


CUSTOM JWT APPROACH (OPTIONAL)
==============================

The last section of the original README is a different JWT implementation.

It uses:

OpenSSL
  |
  v
privateKey.pem + publicKey.pem
  |
  v
GenerateToken.java
  |
  v
MPJWTToken.java
  |
  v
JWT
  |
  v
MSAClient
  |
  v
MSAResource

Create a base key:

openssl genrsa -out baseKey.pem

Create PKCS#8 private key:

openssl pkcs8 -topk8 -inform PEM -in baseKey.pem -out privateKey.pem -nocrypt

Create public key:

openssl rsa -in baseKey.pem -pubout -outform PEM -out publicKey.pem

Files:

keys/
  baseKey.pem
  privateKey.pem
  publicKey.pem

This custom method is more complicated.

For the practical, use JWTenizer first unless your teacher specifically asks for custom JWT generation.


JWT METHOD COMPARISON
=====================

JWTenizer:
- jwtenizr.jar
- Easy
- Recommended for basic practical

Custom JWT:
- OpenSSL
- GenerateToken.java
- MPJWTToken.java
- More complex


FINAL PRACTICAL CHECKLIST
========================

PROJECTS
[ ] MSAResource created
[ ] MSAClient created
[ ] jakarta-9-microprofile archetype used

DEPENDENCIES
[ ] Jakarta EE dependency added
[ ] MicroProfile 6 added
[ ] javax changed to jakarta
[ ] Payara version matches project dependencies

RESOURCE APPLICATION
[ ] User.java
[ ] UserBean.java
[ ] UserService.java
[ ] Bootstrap.java
[ ] @RolesAllowed("chief")
[ ] persistence.xml
[ ] microprofile-config.properties

DATABASE
[ ] MySQL running
[ ] Database msa_db created
[ ] JDBC pool created
[ ] JDBC resource jdbc/mysql created
[ ] MySQL connector JAR available
[ ] Port 3306 working

JWT
[ ] jwtenizr.jar available
[ ] java -jar jwtenizr.jar executed
[ ] JWT token obtained
[ ] issuer configured
[ ] public key configured
[ ] MSAResource JWT config completed

CLIENT
[ ] UserClient.java created
[ ] @RegisterRestClient added
[ ] @ClientHeaderParam added
[ ] getToken() added
[ ] JWT added to client config
[ ] REST URL configured
[ ] Servlet injects UserClient

BUILD
[ ] mvn clean package in MSAResource
[ ] mvn clean package in MSAClient
[ ] MSAResource.war generated
[ ] MSAClient.war generated

RUN
[ ] MSAResource running on 8085
[ ] MSAClient running on 8086

TEST
[ ] http://localhost:8085/MSAResource/rest/user
[ ] http://localhost:8086/MSAClient/


MOST IMPORTANT VALUES
=====================

Resource:

mp.jwt.verify.issuer=YOUR_ISSUER
mp.jwt.verify.publickey=YOUR_PUBLIC_KEY

Client:

userclient/mp-rest/url=http://localhost:8085/MSAResource/rest/user
jwt=YOUR_JWT_TOKEN

Security:

@RolesAllowed("chief")

Header:

Authorization: Bearer <JWT>

Ports:

MSAResource = 8085
MSAClient   = 8086
MySQL       = 3306


IMPORTANT FOR YOUR CURRENT SETUP
================================

Do not blindly use the old:

payara-micro-5.194.jar

from the README.

Use the Payara JAR that actually exists on your computer and is compatible with your project.

Before running, check:

dir payara.jar
dir payara-micro-*.jar
dir *.jar

Also check your MySQL connector:

dir mysql-connector*.jar

If a command says:

Unable to access jarfile

the JAR path/name is wrong.

If Payara says:

--addlibs File ... does not exist

the MySQL connector filename/path is wrong.

If MySQL connection fails, check:

netstat -ano | findstr :3306

and verify the MySQL service is running.
