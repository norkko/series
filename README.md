# Series
Series is a web-application for tracking progress of series easily.   

### Prerequisites
Things you need to run the application locally.
- Java 8
- Docker

## Running locally
Package backend:
```bash
mvn -f ./rest package
```
And start with compose:
```bash
docker-compose up
```

## Tests
😂

## API

| Method | URL       | Description    | Authenticated | Available from UI | 
|--------|-----------|---------------|------------------|-------------- |
| POST   | /register | Register account |  | ✓ |  
| POST   | /series   | Add series to library  |   ✓ |  ✓ |  
| GET    | /series   | Get library of series  |   ✓ |  ✓ |  
| GET    | /episodes   |  Get all watched episodes  |  ✓ |  |  
| GET    | /episodes/*id*   |  Get watched episodes of specific series  |  ✓ |  |  
| POST   | /episodes   |  Set episode as watched |  ✓ |  ✓ |  
| POST   | /seasons   |  Set season as watched |  ✓ |✓ |  


##### Movie API Wrapper
| Method | URL       | Description    | Authenticated | Available from UI | 
|--------|-----------|---------------|--------------- | ----------------| 
| GET | /search?*query* | Search series by query |   |  ✓ |
| GET | /series/*id* | Get specific series information   | ✓  | |
| GET | /series/*id*/*season* | Get specific series season information | ✓  | |
| GET | /series/*id*/*season*/*episode*| Get specific series episode information   |  ✓ | |


## License
MIT