# Insurance Product


Rest API that will receive, calculate, save/uptade (persist) and list insurance products.


## Solution Proposal

This is a Java project using:
- SpringBoot.
- PostgreSQL for persistence.
- H2 for persistence in Test environment.
- Jaeger for monitoring.

### Tests
The service layer is tested with integration tests, covering the service(including the response and request objects), repository and entity.

The Component and Validators are tested with unit tests, to cover the calculation and validation capabilities respectively.

The Controller layer is tested with unity tests mocking the services, to validate the Rest calls/responses and exception handling.

## Running App

Run `docker-compose up` to create PostgreSQL databases and start Jaeger monitoring

Run `mvn clean install`

Run `java -jar target/itausegdev-0.0.1-SNAPSHOT.jar`

Tomcat will start on port *8081*

App base URL `http://localhost:8081/api/insurance`

### Monitoring

With the app up and running, access `http://localhost:16686/`
The Jaeger UI will open with a search dialog form box on the left.
On the search box combo, select the option `price-fare` (our app name), 
and follow the API call trace.


### API-docs

Access `target/snippets`

### JaCoCo coverage

Access `target/site/index.html`