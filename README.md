# Series
Series is a web-application for tracking progress of series    

## Built with
- Java
- Docker
- MySQL
- Node
- Bootstrap

## Running locally
To run locally Java 8 and Maven has to be installed for packaging.   

Create `.env` file:    
```bash
cp .env.example .env
```
Package with Maven:
```bash
mvn -f ./rest -DskipTests package
```
Start services:
```bash
docker-compose up --build
```

Run tests
```bash
mvn test
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