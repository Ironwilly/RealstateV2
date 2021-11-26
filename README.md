# RealstateV2
Proyecto Realstatev2



                          ________________
                          \      __      /         __
                           \_____()_____/         /  )
                           '============`        /  /
                            #---\  /---#        /  /
                           (# @\| |/@  #)      /  /
                            \   (_)   /       /  /
                            |\ '---` /|      /  /
                    _______/ \\_____// \____/ o_|
                   /       \  /     \  /   / o_|
                  / |           o|        / o_| \
                 /  |  _____     |       / /   \ \
                /   |  |===|    o|      / /\    \ \
               |    |   \@/      |     / /  \    \ \
               |    |___________o|__/----)   \    \/
               |    '              ||  --)    \     |
               |___________________||  --)     \    /
                    |           o|   ''''   |   \__/
                    |            |          |

                      "DON'T CROSS ME... !"

## Desarrollada por :
#### - Guillermo Ferrari Ferrari

Esta API REST permite controlar el funcionamiento de la app REAL ESTATE, la cual gestiona el alquiler y venta de viviendas, así como el API para poder gestionar todos los datos.

Esto incluye las siguientes funcionalidades:

- Las peticiones que se detallan más abajo.
- La docuementación generada con OpenApi 3.0 y Swagger.
- Una colección de Postman con las peticiones generadas en JSON para poder realizar pruebas de la app.
- Implementación en Angular

## Entidades

Las entidades que componen esta API son:

- Vivienda
- Inmobiliaria
- Persona (Abstracta) de la que heredan Propietario y Interesado

Para el correcto manejo de cada entidad contamos con las siguientes clases:

### Vivienda

- **ViviendaController**   

- **ViviendaRepository**
  
- **ViviendaService**
 

### Inmobiliaria

- **InmobiliariaController**

- **InmobiliariaRepository**
  
- **InmobiliariaService**


### Propietario

- **PropietarioController**

- **PropietarioRepository**
  
- **PropietarioService**


### Interesado

- **InteresadoController**

- **InteresadoRepository**
  
- **InteresadoService**



Por otro lado, para el correcto manejo de las asociaciones entre entidades, se han creado diferentes DTOs (Data Transfer Object); estos nos permiten crear nuevos objetos con los atributos de las entidades que más nos interesen.

En nuestra API, se han creado los siguientes DTOs:

### Vivienda

- **GetViviendaDto** : Nos permite obtener los datos de esta pseudoentidad.
- **CreateViviendaDto** : Nos permite generar el DTO con los datos necesarios.
- **ViviendaDtoConverter** : Contiene métodos que nos permiten convertir una entidad Song en DTO y viceversa.

### Inmobiliaria

- **GetInmobiliariaDto** : Nos permite obtener los datos de esta pseudoentidad.
- **CreateInmobiliariaDto** : Nos permite generar el DTO con los datos necesarios.
- **InmobiliariaDtoConverter** :Contiene métodos que nos permiten convertir una entidad Song en DTO y viceversa.

### Propietario

- **GetPropietarioDto**  : Nos permite obtener los datos de esta pseudoentidad.
- **CreatePropietarioDto** : Nos permite generar el DTO con los datos necesarios.
- **PropietarioDtoConverter** : Contiene métodos que nos permiten convertir una entidad Song en DTO y viceversa.

### Interesado

- **GetInteresadoDto**  : Nos permite obtener los datos de esta pseudoentidad.
- **CreateInteresadoDto** : Nos permite generar el DTO con los datos necesarios.
- **InteresadoDtoConverter** : Contiene métodos que nos permiten convertir una entidad Song en DTO y viceversa.



Las peticiones que se han definido en nuestra API son:

### Vivienda

- **Añadir vivienda** : Petición tipo POST.
- **Ver todas las viviendas y poder filtrarlas** : Petición tipo GET.
- **Buscar vivienda por ID y ver sus detalles** : Petición tipo GET.
- **Editar vivienda** : Petición tipo PUT.
- **Eliminar vivienda** : Petición tipo DELETE.
- **Eliminar inmobiliaria asociada a una vivienda** : Petición tipo DELETE.

### Propietarios

- **Ver todos los propietarios** : Petición tipo GET.

### Inmobiliaria

- **Añadir inmobiliaria** : Petición tipo POST.
- **Ver todas las inmobiliaras** : Petición tipo GET.
- **Buscar inmobiliaria por ID y ver sus detalles** : Petición tipo GET.
- **Editar inmobiliaria** : Petición tipo PUT.
- **Eliminar inmobiliaria** : Petición tipo DELETE.

## Funcionamiento

Para el correcto funcionamiento de la aplicación, se necesitan seguir los siguientes pasos:

1. Descargar ambos repositorios.
2. Importar el proyecto de backend en IntelliJ IDEA.
3. Ejecutar el proyecto con Maven y Spring Boot.
4. Importar el proyecto de fronted en Visual Studio Code.
5. Abrir un nuevo terminal.
6. Ejecutar el comando npm install. Hay que asegurarse que se encuentra en la ruta del proyecto.
7. Ejecutar el comando ng serve -o.

Una vez seguidos estos pasos, se nos abrirá una ventana del navegador con la aplicación en funcionamiento.

## Seguridad

Se implementan roles de ADMIN,PROPIETARIO Y GESTOR

## JWT

La biblioteca implementa la verificación y firma de JWT utilizando los siguientes algoritmos:

JWS	    Algoritmo	    Descripción
HS256	HMAC256	    HMAC con SHA-256
HS384	HMAC384	    HMAC con SHA-384
HS512	HMAC512 	HMAC con SHA-512
RS256	RSA256	    RSASSA-PKCS1-v1_5 con SHA-256
RS384	RSA384	    RSASSA-PKCS1-v1_5 con SHA-384
RS512	RSA512	    RSASSA-PKCS1-v1_5 con SHA-512
ES256	ECDSA256	ECDSA con curva P-256 y SHA-256
ES256K	ECDSA256	ECDSA con curva secp256k1 y SHA-256
ES384	ECDSA384	ECDSA con curva P-384 y SHA-384
ES512	ECDSA512	ECDSA con curva P-521 y SHA-512


-Se elije el algoritmo
-Creamos y firmamos un token
-Verificamos token
-Validamos el tiempo de validez del token





