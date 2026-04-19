# University Service

Service backend développé en **Java avec Spring Boot**.

## Description

Ce service contient la gestion de plusieurs entités liées à une structure universitaire :

* Faculty
* Department
* Class
* Cycle
* Establishment
* Genie

Il expose des contrôleurs REST et utilise une architecture classique en couches :
controller → service → repository → entity.

---

## Structure du projet

```text
src/main/java/.../university

├── controller
│   ├── UniversityController
│   ├── FacultyController
│   └── PublicViewController
│
├── service
│   ├── interfaces (ClassService, EstablishmentService, etc.)
│   └── impl
│
├── repository
│   ├── DepartmentRepository
│   ├── FacultyRepository
│   ├── GenieRepository
│   ├── EstablishmentRepository
│   ├── ClassRepository
│   └── CycleRepository
│
├── entity
│   ├── Department
│   ├── Faculty
│   ├── Genie
│   ├── Establishment
│   ├── Class
│   └── Cycle
│
├── dto
│   └── *UpdateDto
│
├── exception
│   ├── NotFoundException
│   └── ConflictException
│
└── event
    └── DepartmentEvent
```

---

## Prérequis

Avant de lancer le projet, if faut s'assurer d'avoir:

* Java (version compatible avec Spring Boot)
* Maven
* Un SGBD configuré (voir `application.yml`)

---

## Configuration

Le fichier de configuration principal est :

```text
src/main/resources/application.yml
```

---

## Lancer le projet

### Avec Maven

```bash
mvn clean install
mvn spring-boot:run
```

### Ou avec le JAR

```bash
mvn clean package
java -jar target/*.jar
```

---

## Accès

Par défaut, l’application démarre sur :

```text
http://localhost:8080
```

Les endpoints sont exposés via les contrôleurs :

* `UniversityController`
* `FacultyController`
* `PublicViewController`

---

## Gestion des erreurs

Le projet définit des exceptions personnalisées :

* `NotFoundException`
* `ConflictException`
