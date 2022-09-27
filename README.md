### technology stack

- java 17
- spring (boot, mvc, mail)
- assertj - to test in more declarative way
- docker


### design overview
**type**: layered architecture
![layered.png](./architecture/layered.png)

**type**: slicing by component or package by component
![modular_architecture.png](./architecture/modular_architecture.png)

**_`updated to`_**
**type**: microservice architecture
![microservice.png](./architecture/microservice.png)


##### endpoints:

| Endpoint                                  | Method  | URL                                 |
|-------------------------------------------|---------|-------------------------------------|
| Get BTC UAH rate                          | `GET`   | `/api/rate`                         |
| Add user subscription email               | `POST`  | `/api/subscription`                 |
| Send emails to subscribed users           | `POST`  | `/api/sendEmails`                   |


### how to run
#### local run
run from docker local image

- clone git repository
- run `./mvnw clean install`
- run `docker build -t btc-subscriber .` to create an image
- run `docker run btc-subscriber`

example:
```shell
./mvnw clean install && docker build -t btc-subscriber .
docker run -e MAIL_USERNAME=username -e MAIL_PASSWORD=password -p 8080:8080 btc-subscriber

```

### furthest improvements
- add security on web apis
- create persistent user mail subscriptions with using database
- think about async monitoring of btc rate changing and automatic user notification
- extend retrieving btc rate for other currencies
