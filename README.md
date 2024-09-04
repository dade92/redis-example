# SPRING EXAMPLE WITH REDIS

POC with Redis as a cache and Spring Boot. Application contains two APIs, one to store and the other to read objects
from the cache.

## Run the entire application

Run the script `run-local-environment.sh`: it will download and run on port 6379 a local Redis instance using Docker.
Then run the application. You can stop the image running `stop-local-environment.sh` script.