# Study Buddy - Plataforma Colaborativa de Estudio

## 📚 Descripción del Proyecto

**Study Buddy** es una plataforma web desarrollada en Spring Boot 3 que permite a estudiantes universitarios encontrar compañeros de estudio compatibles según sus materias, horarios y preferencias de estudio (música, gamificación, silencio, etc.).

### 🎯 Objetivos

- Conectar estudiantes con intereses académicos similares
- Facilitar la formación de grupos de estudio efectivos
- Mejorar el rendimiento académico mediante el estudio colaborativo
- Proporcionar herramientas para organizar sesiones de estudio

### 🔧 Tecnologías Utilizadas

- **Backend**: Spring Boot 3.2.4
- **Frontend**: Thymeleaf + Bootstrap 5.3.2
- **Seguridad**: Spring Security
- **Gráficos**: Chart.js
- **Base de Datos**: En memoria (Listas Java)
- **Java**: JDK 17

## 🏗️ Arquitectura del Sistema

### Paquetes del Proyecto

```
com.studybuddy/
├── config/          # Configuración de seguridad
├── controller/      # Controladores REST
├── model/          # Entidades del dominio
├── service/        # Lógica de negocio
└── resources/      # Templates y archivos estáticos
```

### Modelos de Datos

#### Usuario
- `id`: Identificador único
- `nombre`: Nombre completo del estudiante
- `email`: Correo electrónico
- `password`: Contraseña
- `materias`: Lista de materias que estudia
- `horarios`: Horarios preferidos de estudio
- `preferenciasEstudio`: Preferencias de ambiente de estudio

#### GrupoEstudio
- `id`: Identificador único
- `nombre`: Nombre del grupo
- `materia`: Materia principal del grupo
- `horario`: Horario de reunión
- `descripcion`: Descripción del grupo
- `listaUsuarios`: Lista de miembros del grupo

#### Recurso
- `id`: Identificador único
- `titulo`: Título del recurso
- `descripcion`: Descripción del recurso
- `tipo`: Tipo de recurso (DOCUMENTO, ENLACE, VIDEO, IMAGEN, AUDIO)
- `autor`: Autor del recurso
- `grupoId`: ID del grupo al que pertenece

## 🚀 Funcionalidades Implementadas

### ✅ Avance 1 - Completado
- [x] Descripción clara del proyecto
- [x] Marco teórico y justificación
- [x] Casos de uso con Usuario, GrupoEstudio y Recurso
- [x] Plan de trabajo con prototipos Bootstrap
- [x] Diseño de BD en memoria

### ✅ Avance 2 - Completado
- [x] Clases modelo completas
- [x] Servicios CRUD en memoria
- [x] Controladores con vistas Thymeleaf
- [x] Vistas Thymeleaf (7 templates)
- [x] Seguridad con Spring Security
- [x] Menú de navegación Bootstrap
- [x] Gráficos con Chart.js
- [x] Código comentado y sin errores

## 📱 Vistas Disponibles

1. **login.html** - Página de inicio de sesión
2. **register.html** - Registro de nuevos usuarios
3. **landing.html** - Página principal (home)
4. **buscar.html** - Búsqueda de grupos
5. **dashboard.html** - Panel de control del usuario
6. **perfil.html** - Gestión del perfil de usuario
7. **graficos.html** - Estadísticas y gráficos
8. **grupo-detalles.html** - Detalles de un grupo específico
9. **sesiones.html** - Gestión de sesiones de estudio

## 🔐 Seguridad

- **Autenticación**: Spring Security con usuarios en memoria
- **Usuarios predefinidos**:
  - Admin: `admin@studybuddy.com` / `admin123`
  - Estudiante: `maria@estudiante.com` / `estudiante123`
- **Autorización**: Rutas protegidas y públicas
- **Logout**: Cierre de sesión seguro

## 📊 Gráficos y Estadísticas

- **Gráfico de Barras**: Cantidad de estudiantes por materia
- **Gráfico Circular**: Distribución de preferencias de estudio
- **Gráfico Lineal**: Crecimiento de miembros en grupos

## 🛠️ Instalación y Ejecución

### Prerrequisitos
- Java 17 o superior
- Maven 3.6+

### Pasos para ejecutar

1. **Clonar el repositorio**
   ```bash
   git clone <repository-url>
   cd StudyBuddy-main
   ```

2. **Compilar el proyecto**
   ```bash
   mvn clean compile
   ```

3. **Ejecutar la aplicación**
   ```bash
   mvn spring-boot:run
   ```

4. **Acceder a la aplicación**
   - URL: http://localhost:8080
   - Puerto: 8080

## 👥 Usuarios de Prueba

| Usuario | Email | Contraseña | Rol |
|---------|-------|------------|-----|
| 🔑 Admin | admin@studybuddy.com | **123456** | ADMIN |
| 👩 María García | maria@estudiante.com | **123456** | USER |
| 👨 Carlos López | carlos@estudiante.com | **123456** | USER |

> **💡 Tip**: Todos los usuarios de prueba usan la misma contraseña simple: `123456`

## 🔍 Funcionalidades Principales

### Para Usuarios
- Registro e inicio de sesión
- Creación y edición de perfil
- Búsqueda de grupos de estudio
- Unirse a grupos existentes
- Gestión de recursos de estudio
- Visualización de estadísticas

### Para Administradores
- Gestión completa de usuarios
- Supervisión de grupos
- Acceso a todas las funcionalidades

## 📈 Características Técnicas

- **Arquitectura**: MVC (Model-View-Controller)
- **Patrón**: Service Layer
- **Persistencia**: En memoria con sincronización
- **UI**: Responsive design con Bootstrap
- **Validación**: Spring Validation
- **Testing**: Spring Boot Test

## 🎨 Diseño UI/UX

- **Framework CSS**: Bootstrap 5.3.2
- **Iconos**: Font Awesome 6.0
- **Colores**: Paleta azul profesional
- **Responsive**: Adaptable a móviles y tablets
- **Accesibilidad**: Cumple estándares web

## 📝 Notas de Desarrollo

- El proyecto funciona completamente en memoria
- No requiere base de datos externa
- Ideal para demos y pruebas universitarias
- Código bien documentado y comentado
- Estructura escalable para futuras mejoras

## 🚀 Próximas Mejoras

- [ ] Integración con base de datos real
- [ ] Sistema de notificaciones
- [ ] Chat en tiempo real
- [ ] Calendario de eventos
- [ ] Sistema de calificaciones
- [ ] API REST completa

## 📞 Contacto

Desarrollado como proyecto universitario para demostrar competencias en:
- Desarrollo web con Spring Boot
- Diseño de interfaces con Bootstrap
- Implementación de seguridad
- Visualización de datos con Chart.js

---

**© 2025 Study Buddy - Plataforma Colaborativa de Estudio**
