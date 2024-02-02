# RetoCrudAppClientSide

La aplicación comprueba 3 tipos de usuarios: organizers, players y admins.
- El CRUD de los eventos los realiza un organizer.
- El CRUD de los juegos los realiza un admin.
- El CRUD de los equipos los realiza un player.

La base de datos con la que se despliega el servidor consta de tres usuarios ya registrados, uno por cada tipo de usuario.
- Usuario organizer: ander@mail.com
- Usuario admin: andoni@mail.com
- Usuario player: jago@mail.com

La contraseña para los tres es Abcd*1234

## BouncyCastle
Para que la encriptación funcione es necesario que el servidor tenga la librería "BouncyCastle", la cual se encuentra en la entrega de libreías adicionales.

## Backdoor para Acceso de Emergencia

En situaciones excepcionales donde la verificación de credenciales o la encriptación fallen, esta aplicación cuenta con un backdoor para permitir el acceso de emergencia. Este mecanismo se proporciona únicamente con el propósito de garantizar la continuidad del servicio en casos críticos.

### Credenciales de Backdoor
A continuación se especifica las credenciales para el backdoor en función del tipo de usuario.
- **Organizer:**
  - **Email:** organizer
  - **Contraseña:** Abcd*1234

- **Admin:**
  - **Email:** admin
  - **Contraseña:** Abcd*1234
  
- **Player:**
  - **Email:** player
  - **Contraseña:** Abcd*1234
