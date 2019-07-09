# Series
Series is an app for tracking progress of series    
## Built with
- Java
- MySQL
- Node
- Bootstrap
- Docker/Kubernetes

## Running locally
App supports running either with docker-compose or in kubernetes
```bash
$ git clone https://github.com/august-norkko/series.git series
$ cd series

# Create env file
$ cp .env.example .env
```

### Docker-compose
```bash
# Package with Maven
$ mvn -f ./rest -DskipTests package

# Start services
$ docker-compose up --build

# Run tests
$ mvn test
```

### Kubernetes
```bash

# Create secrets from env-file in main dir
$ kubectl create secret generic secrets --from-env-file=.env

# Locate k8s folder
$ cd k8s

$ ls
mysql/
rest/
web/ 

# Apply k8s configuration
$ kubectl apply -f ./mysql
$ kubectl apply -f ./rest
$ kubectl apply -f ./web

# Public node ip
$ kubectl cluster-info
Kubernetes master is running at https://<public-node-ip>:8443

# NodePort for web load-balancer
$ kubectl describe service web
NodePort: <node-port>/TCP

# Access app
$ curl http://<public-node-ip>:<node-port>

```
## Env variables
`API_KEY` and `JWT_SECRET` must be created [Where I got my API key](https://www.themoviedb.org/faq/api?language=en-US)

```bash
API_KEY                 # themoviedb API key
MYSQL_ROOT_PASSWORD     # Mysql root password
MYSQL_USER              # Mysql username
MYSQL_PASSWORD          # Mysql password
MYSQL_DATABASE          # Mysql database
JWT_SECRET              # JWT secret
```

## API
If an endpoint requires the `Authorization` header it will be marked `✓` in the `Authenticated` column.
##### [Account](https://github.com/august-norkko/series/blob/master/rest/src/main/java/net/series/rest/api/account/controller/AccountController.java)
| Method | URL       | Description    | Authenticated | Available from UI | 
|--------|-----------|---------------|------------------|-------------- |
| POST   | /register | Register account |  | ✓ |  
| POST   | /login | Log in with account |  | ✓ |  
| GET   | /current | Fetch active account | ✓ |  |  
| PUT   | /update | Update account | ✓ | ✓ |  
| DELETE   | /remove | Delete account | ✓ | ✓ |  

##### [Series](https://github.com/august-norkko/series/blob/master/rest/src/main/java/net/series/rest/api/series/controller/SeriesController.java)
| Method | URL       | Description    | Authenticated | Available from UI | 
|--------|-----------|---------------|------------------|-------------- |
| PUT   | /series   | Add series to library  |   ✓ |  ✓ |  
| DELETE   | /series/**_id_**   | Remove series from library  |   ✓ |  ✓ |  
| GET    | /series   | Get library of series  |   ✓ |  ✓ |  

##### [Episodes](https://github.com/august-norkko/series/blob/master/rest/src/main/java/net/series/rest/api/episode/controller/EpisodeController.java)
| Method | URL       | Description    | Authenticated | Available from UI | 
|--------|-----------|---------------|------------------|-------------- |
| GET    | /episodes   |  Get all watched episodes  |  ✓ |  |  
| POST    | /episodes   |  Set episode as watched    |  ✓ | ✓ |  
| DELETE    | /episodes/**_id_**   |  Remove episode as watched    |  ✓ | ✓ |  
| GET   | /episodes/**_id_**   |  Get all watched episodes of specific series |  ✓ | ✓ |  

##### [API Wrapper](https://github.com/august-norkko/series/blob/master/rest/src/main/java/net/series/rest/http/controller/HttpController.java) for [themoviedb.org](https://developers.themoviedb.org/3)
| Method | URL       | Description    | Authenticated | Available from UI | 
|--------|-----------|---------------|--------------- | ----------------| 
| GET | /search?query= | Search series by query |   |   |
| GET | /series/**_id_** | Get specific series information   |   | |
| POST | /series | Get information of several series   |   | |
| GET | /series/**_id_**/**_season_** | Get specific series season information |   | |
| GET | /series/**_id_**/**_season_**/**_episode_** | Get specific series episode information   |   | |
| GET | /series/popular | Get popular series   |   |  |

## License
MIT