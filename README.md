# Series
Series is a web-application for tracking progress of series easily.   

### Prerequisites
Things you need to run the application locally.
- Java 8
- Docker
- Maven

## Running locally
If the application is to be run locally, the environment variables (`.env` file) has to be updated with an actual API key from [themoviedb.org]()    

Creating `.env` file:   
```bash
cp .env.example .env
```
Package backend:
```bash
mvn -f ./rest package
```
Start with compose:
```bash
docker-compose up
```

## Environment variables
When running the application with docker-compose, the `.env` file provides all environment variables, these environment variables can be set on your local machine, or using the `.env` file. 
## API
If an endpoint requires the `Authorization` header (JWT), authenticated will be marked in the table.

##### Account
| Method | URL       | Description    | Authenticated | Available from UI | 
|--------|-----------|---------------|------------------|-------------- |
| POST   | /login | Log in with account |  | ✓ |  
| POST   | /register | Register account |  | ✓ |  

##### Series
| Method | URL       | Description    | Authenticated | Available from UI | 
|--------|-----------|---------------|------------------|-------------- |
| POST   | /series   | Add series to library  |   ✓ |  ✓ |  
| DELETE   | /series/*id*   | Remove series from library  |   ✓ |  ✓ |  
| GET    | /series   | Get library of series  |   ✓ |  ✓ |  

##### Episodes
| Method | URL       | Description    | Authenticated | Available from UI | 
|--------|-----------|---------------|------------------|-------------- |
| GET    | /episodes   |  Get all watched episodes  |  ✓ |  |  
| POST    | /episodes   |  Set episode as watched    |  ✓ |  |  
| POST    | /episodes/season   |  Set season of episodes as watched  |  ✓ |  |  
| DELETE   | /episodes/*id*   |  Remove season as watched |  ✓ |  ✓ |  
| GET   | /episodes/*id*   |  Get all watched episodes of specific series |  ✓ |✓ |  

##### API Wrapper for [themoviedb.org](https://developers.themoviedb.org/3)
| Method | URL       | Description    | Authenticated | Available from UI | 
|--------|-----------|---------------|--------------- | ----------------| 
| GET | /search?*query* | Search series by query |   |  ✓ |
| GET | /series/*id* | Get specific series information   | ✓  | |
| GET | /series/*id*/*season* | Get specific series season information | ✓  | |
| GET | /series/*id*/*season*/*episode*| Get specific series episode information   |  ✓ | |


## License
MIT