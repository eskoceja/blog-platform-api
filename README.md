# blog-platform-api

## Review

A RESTful API is an application programming interface that conforms to the constraints of REST architectural type. 
REST (Representational State Transfer) is an architectural style for providing standards between computer systems on 
the web, making it easier to communicate with one another. In REST architecture, clients send requests to retrieve or 
modify resources and servers send responses to these requests. The requests can retrieve or modify the data on the server.
These consist of HTTP verbs, a header, path to source, and an optional body message. The 4 basic HTTP verbs are: **GET**,
**POST**, **PUT**, and **DELETE**.


| REQUEST | FUNCTIONALITY |
| --- | --- | 
| GET | Retrieves a specific resource (ex: id) or collection of resources |
| POST | Creates a new resource |
| PUT | Updates a specific resource (ex: id) |
| DELETE | Removes a specific resource by id |

HTTP, REST APIs provide the ability to build backwards compatible APIs, evolvable APIs, scaleable services, securable 
services, and a spectrum of stateless to stateful services.

## What are all these packages and classes?
I have created 5 packages for this project, a model, controller, repository, service, and a utils. Let's go over what 
each of these packages contain.
- Model Package
  - BlogPost class
    - is an @Entity and contains field names of a table
    - contains setters and getters
    - has an empty constructor because it is an entity
    - auto-generates id with proper annotations
- Repository Package
  - BlogPostRepository interface
    - extends JpaRepository<BlogPost, Long> as it is an extension of the CRUD repository, providing more specific functionality to CRUD
- Service Package
  - BlogPostService class
    - contains an @Service annotation to let the project know it is a service from where the @RestController will be supported from
    - this is where all the CRUD functionality takes place with the support of the BlogPostRepository and its functionality
- Controller Package
  - BlogPostController class
    - contains a @RestController annotation, meaning it won't render to a .html file, thus returning exactly how it is formatted in typical APIs or databases online we have worked with before
    - functions with the support of the services AS WELL AS: model assembler and the blog post not found exception (details in utils)
    - where the @GetMapping, @PostMapping, and @PutMapping functionality is provided along with the corresponding links to each functionality
- Utils Package (this is where it gets interesting)
  - BlogPostModelAssembler class
    - this is where we enhance the REST features by calling in HATEOS
    - it is a @Component which means it will assemble the data when the app starts
    - there is one method, toModel() and that is converting a non-model object into a model-based object with EntityModel<> 
    - this is how we get the links to render on the localhost!
  - BlogPostNotFoundAdvice class
    - this contains an @ControllerAdvice annotation, and it is an extra tidbit of Spring MVC configuration when a 'NotFoundException' is thrown, it supports the next class
    - the @ResponseBody allows advice to render directly to the body
    - the @ExceptionHandler configures the advice to respond ONLY when a 'NotFoundException' is thrown
    - the @ResponseStatus issues an HttpStatus.NOT_FOUND
  - BlogPostNotFoundException class
    - extends from RunTimeException 
    - used to indicate when an employee is looked up, it is the "advice" that renders to the page
  - LoadDatabase class
    - contains @Configuration to have hard-coded data in it
    - the @Bean will allow the CommandLineRunner to run when the application starts, and it will display the data already inside
- BlogPlatformApiApplication class
  - main method with the @SpringBootApplication annotation that makes this project SPRING to life!


## How it works

I have created a simple blog post service. The data is stored in an H2 in-memory database. There are several ways to 
view the data and manipulate it 1) visit http://localhost:8080/h2-console/ (Here you can view it and call queries, view 
application.properties for the JDBC URL), 2) http://localhost:8080/posts or /posts/{id} (you can do up to 3 as I have
hard-coded those in for testing purposes), and 3) My personal favorite at the moment, once you run the application go 
to the terminal and type in the command: curl -v localhost:8080/posts
<br>

Commands to try with my code on CLI (example ids):

| ACTION      | COMMANDS                                                                                                                                     |
|-------------|----------------------------------------------------------------------------------------------------------------------------------------------|
| View All    | curl -v localhost:8080/posts                                                                                                                 |
| View By Id  | curl -v localhost:8080/posts/1                                                                                                               |
| Create | curl -X POST localhost:8080/posts -H 'Content-type:application/json' -d '{"title": "title 4", "content": "content 4", "author": "author 4"}' |
| Update | curl -X PUT localhost:8080/posts/4 -H 'Content-type:application/json' -d '{"title": "title 4", "content": "content 4", "author": "Emily"}'   |
| Delete | curl -X DELETE localhost:8080/posts/4                                                                                                        |

I have learned that merely using pretty URLs, using GET, POST, and DELETE, or using CRUD is not fully using RESTful APIs.
That is why I have also implemented Spring HATEOS into this project to enhance RESTful services! You will notice that it
is implemented in the BlogPostCollection CollectionModels and EntityModels, they are generic containers that contain data 
and a collection of links as well. These, you will notice will render to the localhost:8080/posts but only the link to
view all entries and an individual one for each entry with their corresponding id. Those are clickable!
<br><br>
What's cool is that you can manipulate data on the h2-console or command line and see the manipulation of data real-time!

### For the client -  a visual appealing page to make requests

To see the action in the form of a page, visit http://localhost:8080/view where the client or user will be able to create, read, update, and delete posts. You should also be able to see the data in the h2 in-memory database. *Note:* There are some hard coded posts you will see when you pull up the pages, those too can be manipulated but will automatically re-populate when you rerun the program.

---------------------------------------------------------------------------
[Helpful Link](https://spring.io/guides/tutorials/rest/), Thanks Hector!

