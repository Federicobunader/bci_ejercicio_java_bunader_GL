# bci_ejercicio_java_bunader_GL

Pasos para ejecutar la aplicacion:

1- Navegar hasta el directorio raiz del proyecto

2 - Compilar y ejecutar la aplicacion

3 - Ejecutar la aplicacion

Comandos:

cd ruta/a/tu/proyecto

mvn clean package

java -jar target/nombre-de-tu-archivo.jar (Que se creo en el paso anterior)

Endpoint: http://localhost:8080/api/user

body de prueba: 

{
    "name": "Nombre del usuario",
    "email": "email@dominio.cl",
    "password": "Contraseña123",
    "phones": [
        {
            "number": "1234567",
            "citycode": "1",
            "contrycode": "57"
        }
    ]
}

Pasos para ejecutar los tests: 

1- Navegar hasta el directorio raiz del proyecto

2 - Ejecuta los tests utilizando Maven.

Comandos:

cd ruta/a/tu/proyecto

mvn test



