# Resolver
- PickGraph resolver
    + set up resolver to scan files for @PickGraphObject annotations
    + Make resolver user can call in their rest endpoint to get the data they need
    + when handling a request
        - each endpoint should except a Map<String, Object> as requested fields
        - check each field in specified PickGraphObject in requested fields to see if it's a PickGraphObject
        -

# Annotations
- @PickGraphObject
    + set up schema from field names, or let user specify schema
- @PickGraphMapping
    + Set up PickGraph resolver to scan files for @PickGraphMapping annotations
    + Make resolver user can call in their rest endpoint to get the data they need
    + get back PickGraphObject (implementation of Map?)
    + allow any field in @PickGraphMapping method, auto populated by resolver
- @PickEndpoint
    + set up annotation to expose rest endpoint and call resolver
    + specify, snake case vs camel case

# Browser tool
+ set up endpoint in spring to get schema automatically
+ field to edit in with autocomplete, headers and variables?
+ display of returned data

# Spring properties
- snake case vs camel case


# Bonus
- set up batch calls to db

# Graphql replacement
+ use rest endpoints
+ just use objects as schema
+ better support for camel vs snake
+ same browser tool
+ use annotations to make @SchemaMapping type things
+ Just make a class to launch graphql things like Graphql.query(MyClass.class, param1, param2, ...)
+ maybe @GraphqlClass annotation, so return type of endpoint can be a graphql class, it just get's parsed differently by automatic object mappers
+ support maps haha
+ send empty desired object or query string
