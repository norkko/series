# Series
Series is a web-application for tracking progress of series easily.    

## Built with
- Java
- Docker
- MySQL
- Python
- Redis

## Running locally
To run locally Java 8 and Maven has to be installed for packaging.   

Create the `.env` file:    
```bash
cp .env.example .env
```
Package backend:
```bash
mvn -f ./rest -DskipTests package
```
Start with compose:
```bash
docker-compose up --build
```
Running tests
```bash
mvn test
```

## Environment variables
When creating the `.env` file, only `API_KEY` and `JWT_SECRET` has to be changed. [Getting an API key](https://www.themoviedb.org/faq/api?language=en-US)

```bash
API_KEY                 # themoviedb API key, has to be changed
MYSQL_ROOT_PASSWORD     # Mysql root password
MYSQL_USER              # Mysql username
MYSQL_PASSWORD          # Mysql password
MYSQL_DATABASE          # Mysql database
JWT_SECRET              # JWT secret
APP_SECRET              # Python secret
```

## API
If an endpoint requires the `Authorization` header it will be marked `✓` in the `Authenticated` column.
##### [Account](https://github.com/august-norkko/series/blob/master/rest/src/main/java/net/series/rest/api/account/controller/AccountController.java)
| Method | URL       | Description    | Authenticated | Available from UI | 
|--------|-----------|---------------|------------------|-------------- |
| POST   | /register | Register account |  | ✓ |  
| POST   | /login | Log in with account |  | ✓ |  
| PUT   | /update | Update account | ✓ | ✓ |  
| DELETE   | /remove | Delete account | ✓ | ✓ |  

##### [Series](https://github.com/august-norkko/series/blob/master/rest/src/main/java/net/series/rest/api/series/controller/SeriesController.java)
| Method | URL       | Description    | Authenticated | Available from UI | 
|--------|-----------|---------------|------------------|-------------- |
| POST   | /series   | Add series to library  |   ✓ |  ✓ |  
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
| GET | /search?**_query_** | Search series by query |  ✓ |   |
| GET | /series/**_id_** | Get specific series information   | ✓  | |
| GET | /series/**_id_**/**_season_** | Get specific series season information | ✓  | |
| GET | /series/**_id_**/**_season_**/**_episode_**| Get specific series episode information   |  ✓ | |


## License
MIT