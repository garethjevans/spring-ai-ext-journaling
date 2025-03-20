# spring-ai-ext-journaling

A test project for the journaling component.

## Prerequisites

You'll need to have the following:

* A `~/.m2/settings.xml` server entry for `tanzu-ai-server-maven-dev-local` or `tanzu-ai-server-maven-prod-local` depending on the version in use
* Network connectivity to artifactory
* An env variable `OPENAI_KEY` set to an openai key (this could be openai.com or tanzu ai server)
* A local postgres database to query, running on 15432

## Run the application

`./mvnw clean test`
