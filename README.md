# ecommerce
Prueba técnica Equipo de Blockchain

Java Version 11
Database: H2

**Endpoints:** 

- API Swagger definition:  http://localhost:8080/v2/api-docs
- Actuator Health: http://localhost:8080/actuator/health
- User: http://localhost:8080/api/users
- Produucts:  http://localhost:8080/api/products
- Orders: http://localhost:8080/api/orders
- Favorites: http://localhost:8080/api/favorites

**Pasos creación ambiente**

1. Package Jar
	- Ubicarse en la raiz del proyecto, en donde se encuentra el archivo POM
	- mvn clean package

2. Docker
	- Create Image: docker build -t marketplace:v1 .
	- Run Countainer: docker run  -d --name marketplace -p 8080:8080 marketplace:v1
	
También si se desea despues de generar el package y si se quiere ejecutar el jar sin levantar un contenedor, se pueden dirigir a la carpeta target y ejecutar: java -jar marketplace-1.0.0.jar 