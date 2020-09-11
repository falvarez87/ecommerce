# ecommerce
Prueba técnica Equipo de Blockchain

**Introducción**

Plataforma e-Commerce (marketplace), donde se puede:

- Registrarse e iniciar sesión de forma segura
- Comprar productos
- Agregarlos al carrito de compras
- Agregarlos a favoritos para comprarlos más tarde
- Ver productos favoritos
- Ver el historial de compras con su respectivo producto, precio y fecha de compra

Uso API

1. Registrarse e iniciar sesión
	- Singin (POST: http://localhost:8080/api/users/signin)
	- Singup (POST: http://localhost:8080/api/users/signup)
2. Productos
	- Lista Productos (GET: http://localhost:8080/api/products/)
	- Agregar a favoritos por usuario (POST: http://localhost:8080/api/favorites)
	- Lista Favoritos por usuario (GET: http://localhost:8080/api/favorites)
3. Ordenes (Carrito de compra)
	- Crear orden (POST: http://localhost:8080/api/orders/)
	- Pagar orden (POST: http://localhost:8080/api/orders/pay)
	- Lista ordenes por usuario (GET: http://localhost:8080/api/orders/)
	- Buscar orden de usuario por id (GET http://localhost:8080/api/orders/{id})


-Java Version 11
-Database: H2
	- jdb url: jdbc:h2:mem:test_db
	- username: root
	- password: root

**Endpoints:** 

- H2 console: http://localhost:8080/h2-console
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