# StudyBuddy - Plataforma Colaborativa de Estudio

## 📚 Descripción
Aplicación web Spring Boot + Thymeleaf para crear y gestionar grupos y sesiones de estudio, con autenticación, CRUDs, listados con DTOs y formularios con Bootstrap. Integra MySQL y usa un esquema "legacy" (el del script SQL incluido).

## ⚙️ Stack
- Backend: Spring Boot 3, Spring Data JPA, Spring Security
- Frontend: Thymeleaf, Bootstrap 5, FontAwesome, Chart.js
- BD: MySQL (configurable en `application.properties`)
- Java 17

## 🚀 Puesta en marcha
1) Base de datos MySQL
   - Importa `src/database/studybuddy_db.sql` o deja `spring.jpa.hibernate.ddl-auto=update` para que Hibernate cree/ajuste tablas.
2) Configura conexión en `src/main/resources/application.properties`:
   - `spring.datasource.url=jdbc:mysql://localhost:3306/studybuddy_db`
   - `spring.datasource.username=...` / `spring.datasource.password=...`
   - `spring.jpa.hibernate.ddl-auto=update` (o `none` si usas estrictamente el script)
3) Ejecuta: `mvn spring-boot:run` o desde tu IDE.
4) Abre: `http://localhost:8080`

Usuarios demo (en memoria):
- admin@studybuddy.com / 123456
- maria@estudiante.com / 123456
- carlos@estudiante.com / 123456

## 🗂️ Estructura
```
com/
├─ config/           Seguridad, seeds
├─ controller/       MVC + APIs
├─ dto/              DTOs de listados
├─ model/            Entidades JPA
├─ repository/       Repositorios JPA
├─ service/          Lógica de negocio
└─ resources/        Templates, estáticos, configuración
```

## 🧩 Mapeo a tablas legacy (SQL)
- `Usuario` → tabla `Usuario` (`id_usuario`, `email`, `password_hash`, `apellido`, `id_carrera`)
- `Facultad` → `Facultad` (`id_facultad`, `nombre_facultad`)
- `Carrera` → `Carrera` (`id_carrera`, `nombre_carrera`, FK `id_facultad`)
- `Materia` → `Materia` (`id_materia`, `nombre_materia`, FK opcional `id_carrera`)
- `Grupo` → `GrupoEstudio` (`id_grupo`, `nombre_grupo`, `id_materia`, `id_creador`)
- `Usuario_Grupo` (pivote: `id_usuario`, `id_grupo`)
- `Recurso` → `Recurso` (`id_recurso`, `id_grupo`)
- `Sesion` → `Sesion` (`id_sesion`)

## 🔐 Seguridad (estado actual)
- Login en `/login` con usuarios en memoria (3 cuentas de prueba).
- Menú dinámico según sesión (`usuario`).
- APIs de cascada permitidas sin login: `/api/**`.

## 📄 Controladores
- `PaginasController`: landing, login, dashboard, perfil (GET/POST), registrar (GET/POST), gráficos, logout.
- `GrupoController`: `/buscar` (listar DTO), crear, unirse, ver, editar, eliminar.
- `SesionController`: `/sesiones` (listar DTO), crear, editar, eliminar.
- `ApiDatosController`: `/api/facultades`, `/api/carreras/{idFacultad}` (DTO `{id,nombre}`) para combos en cascada.

## 📦 Servicios y CRUDs
- `UsuarioService`: agregar, listar, buscar, actualizar, eliminar; utilidades para materias/horarios/preferencias.
- `GrupoService`: crear, listar, buscar, unirse, eliminar; `listarDTO()`.
- `SesionService`: crear, listar, buscar, actualizar, eliminar; `listarDTO()`.
- `RecursoService`: CRUD de recursos.

## 🧾 DTOs y consultas
- `GrupoListadoDTO` + `GrupoRepository.findAllGruposListado()` (joins a Materia, Usuario y miembros).
- `SesionListadoDTO` + `SesionRepository.findAllSesionesListado()` (join a Grupo y Usuario).

## 🖥️ Vistas (Thymeleaf + Bootstrap)
- `buscar.html`: tarjetas de grupos (DTO); crear grupo con select de Materia; Unirse/Editar/Eliminar (según creador).
- `sesiones.html`: listados con DTO; crear/Editar/Eliminar (según autor).
- `register.html` y `perfil.html`: combos en cascada (Facultad→Carrera) con feedback de carga y errores.
- Fragmentos `header.html` (Bootstrap/FA) y `navbar.html` (menú dinámico).

## 🔌 API Cascada
- GET `/api/facultades` → `[{id,nombre}]`
- GET `/api/carreras/{idFacultad}` → `[{id,nombre}]`

## ✅ Checklist de requisitos (resumen)
- CRUD principal: Usuario, GrupoEstudio, Recurso, Sesion (completo en servicios y controladores dedicados).
- Conexión a BD real (MySQL): configurada en `application.properties`.
- Bootstrap: usado en listados de `buscar.html` y `sesiones.html`.
- Consultas multi-tabla: presentes con JPA y DTOs.
- DTOs en listados: Grupos (sí), Sesiones (sí).
- Cascadas + desplegables: `register.html` y `perfil.html` con `/api/*`.
- Menú: fragmento Thymeleaf reutilizable y dinámico.
- Organización: paquetes `controller/service/repository/model/dto` claros.

## 🧯 Troubleshooting
- Combos vacíos: asegúrate de datos (carga el script SQL o usa `DataInitializer`) y que `/api/**` está permitido.
- Crear grupo falla: usa Materia existente (select `materiaId`).
- No ves registros: consulta tablas legacy (`Usuario`, `GrupoEstudio`, etc.).

---
**© 2025 StudyBuddy**
