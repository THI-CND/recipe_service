# Recipe Service
Der Recipe Service verwaltet Rezepte, bestehend aus einzelnen Zubereitungsschritten und Zutaten mit Mengenangaben.

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=THI-CND_recipe_service&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=THI-CND_recipe_service)

## Getting Started
### Abhängigkeiten
Um das Projekt lokal zu starten, müssen die folgenden Abhängigkeiten installiert werden:
- Java 23 Temurin JDK
- Maven 3+
- Docker
- Docker Compose

### Starten
Um das Projekt lokal zu bauen, führen Sie den folgenden Befehl aus:
```bash
mvn clean install
```
Das gebaute Projekt kann mit dem folgenden Befehl gestartet werden:
```bash
java -jar target/thicnd-<version>.jar
```

### Entwicklungsumgebung
Als Entwicklungsumgebung wird IntelliJ IDEA verwendet und empfohlen.

### Docker
Das Projekt kann auch lokal per Docker gestartet werden.\
Dazu muss im Projektordner der folgende Befehl ausgeführt werden:
```bash
docker compose up
```
Die Konfiguration startet im Standard eine Postgres Datenbank und eine RabbitMQ Message Queue.
Der Recipe Service wird automatisch gestartet und kann unter Port 8080 (REST API) und Port 9090 (gRPC) erreicht werden.

### Profile
#### Development
Standardmäßig wird das Profil `development` verwendet.\
Das Profil beinhaltet das Leeren der Datenbank und die Initialisierung der Datenbank mit Testdaten zum Start der Applikation.

#### Production
Das Profil `production` beinhaltet keine Initialisierung der Datenbank.\
Aktiviert werden kann das Profil mit dieser Umgebungsvariable:
```bash
SPRING_PROFILES_ACTIVE=production
```

#### Test
Zum Ausführen der Tests ist das Profil `test` aktiviert.\
Es startet eine lokale InMemory Datenbank die zum Standalone Test verwendet werden kann.

### Umgebungsvariablen
Es stehen die folgenden Umgebungsvariablen zur Konfiguration zur Verfügung:
- `SPRING_PROFILES_ACTIVE`: Das aktive Profil.\
Mögliche Werte: `development`, `production`, `test`
- `DB_URL`: Die URL der Datenbank.
- `DB_USER`: Der Benutzername der Datenbank.
- `DB_PASSWORD`: Das Passwort der Datenbank.
- `RABBIT_HOST`: Der Hostname der RabbitMQ Message Queue.
- `RABBIT_PORT`: Der Port der RabbitMQ Message Queue.
- `RABBIT_USER`: Der Benutzername der RabbitMQ Message Queue.
- `RABBIT_PASSWORD`: Das Passwort der RabbitMQ Message Queue.
- `INGREDIENT_SERVICE_ADDRESS`: Die Adresse der gRPC Schnittstelle des Ingredient Service.
- `RABBIT_EXCHANGE`: Der RabbitMQ Exchange Name für das Veröffentlichen der Events.

## Schnittstellen
### REST
#### V1
##### GET `/api/v1/recipe`
Gibt alle Rezepte zurück.\
Response:
- Status: 200 OK
- Body: `List<RecipeResponse>`

##### GET `/api/v1/recipe/{id}`
Gibt das Rezept mit der ID `id` zurück.\
Response:
- Status: 200 OK
- Body: `RecipeResponse`

##### GET `/api/v1/recipe/{id}/total-ingredients`
Gibt die Gesamtmenge aller Zutaten des Rezepts mit der ID `id` zurück.\
Response:
- Status: 200 OK
- Body: `List<TotalIngredientResponse>`

##### GET `/api/v1/recipe/{id}/associated-tags`
Gibt alle Tags zurück, die mit dem Rezept mit der ID `id` assoziiert sind.\
`Intersection` umfasst die Tags, die in jederem Rezept vorkommen.\
`Union` umfasst die Tags, die in mindestens einem Rezept vorkommen.\
Response:
- Status: 200 OK
- Body: `List<AssociatedTagsResponse>`

##### POST `/api/v1/recipe`
Erstellt ein neues Rezept.\
Request:
- Body: `RecipeRequest`
Response:
- Status: 200 OK
- Body: `RecipeResponse`

##### PUT `/api/v1/recipe/{id}`
Aktualisiert das Rezept mit der ID `id`.\
Request:
- Body: `RecipeRequest`
Response:
- Status: 200 OK
- Body: `RecipeResponse`

##### DELETE `/api/v1/recipe/{id}`
Löscht das Rezept mit der ID `id`.\
Response:
- Status: 200 OK

##### GET `/api/v1/step/{id}`
Gibt den Zubereitungsschritt mit der ID `id` zurück.\
Response:
- Status: 200 OK
- Body: `StepResponse`

##### PUT `/api/v1/step/{id}`
Aktualisiert den Zubereitungsschritt mit der ID `id`.\
Request:
- Body: `StepRequest`
Response:
- Status: 200 OK
- Body: `StepResponse`

##### GET `/api/v1/quantified-ingredient/{id}`
Gibt die Zutat mit der ID `id` zurück.\
Response:
- Status: 200 OK
- Body: `QuantifiedIngredientResponse`

##### PUT `/api/v1/quantified-ingredient/{id}`
Aktualisiert die Zutat mit der ID `id`.\
Request:
- Body: `QuantifiedIngredientRequest`
Response:
- Status: 200 OK
- Body: `QuantifiedIngredientResponse`

#### V2
##### PATCH `/api/v2/recipe/{id}/step`
Fügt einen neuen Zubereitungsschritt zum Rezept mit der ID `id` hinzu.\
Request:
- Body: `StepRequest`
Response:
- Status: 200 OK
- Body: `RecipeResponse`

##### DELETE `/api/v2/recipe/{id}/step/{stepId}`
Löscht den Zubereitungsschritt mit der ID `stepId` aus dem Rezept mit der ID `id`.\
Response:
- Status: 200 OK
- Body: `RecipeResponse`

##### PATCH `/api/v2/step/{id}/quantified-ingredient`
Fügt eine neue Zutat zum Zubereitungsschritt mit der ID `id` hinzu.\
Request:
- Body: `QuantifiedIngredientRequest`
Response:
- Status: 200 OK
- Body: `StepResponse`

##### DELETE `/api/v2/step/{id}/quantified-ingredient/{quantifiedIngredientId}`
Löscht die Zutat mit der ID `quantifiedIngredientId` aus dem Zubereitungsschritt mit der ID `id`.\
Response:
- Status: 200 OK
- Body: `StepResponse`

### gRPC
```protobuf
syntax = "proto3";

package de.benedikt_schwering.thicnd.stubs;

option java_multiple_files = true;
option java_package = "de.benedikt_schwering.thicnd.stubs";

service RecipeService {
  rpc GetRecipes (Null) returns (RecipesResponse);
  rpc GetRecipe (RecipeIdRequest) returns (RecipeResponse);
  rpc GetTotalIngredients (RecipeIdRequest) returns (TotalIngredientsResponse);
  rpc GetAssociatedTags (RecipeIdRequest) returns (AssociatedTagsResponse);
  rpc CreateRecipe (CreateRecipeRequest) returns (RecipeResponse);
  rpc UpdateRecipe (UpdateRecipeRequest) returns (RecipeResponse);
  rpc DeleteRecipe (RecipeIdRequest) returns (Null);
}

message Null {}

message CreateRecipeRequest {
  RecipeRequest recipe = 1;
}

message UpdateRecipeRequest {
  string id = 1;
  RecipeRequest recipe = 2;
}

message RecipeIdRequest {
  string id = 1;
}

message RecipeRequest {
  string name = 1;
  string author = 2;
  string description = 3;
  repeated StepRequest steps = 4;
}

message StepRequest {
  repeated QuantifiedIngredientRequest quantifiedIngredients = 1;
  string description = 2;
}

message QuantifiedIngredientRequest {
  int64 ingredient = 1;
  double quantity = 2;
}

message RecipesResponse {
  repeated RecipeResponse recipes = 1;
}

message RecipeResponse {
  string id = 1;
  string name = 2;
  string author = 3;
  string description = 4;
  repeated StepResponse steps = 5;
}

message StepResponse {
  string id = 1;
  repeated QuantifiedIngredientResponse quantifiedIngredients = 2;
  string description = 3;
}

message QuantifiedIngredientResponse {
  string id = 1;
  int64 ingredient = 2;
  double quantity = 3;
}

message TotalIngredientsResponse {
  repeated TotalIngredientResponse totalIngredients = 1;
}

message TotalIngredientResponse {
  int64 ingredient = 1;
  double quantity = 2;
}

message AssociatedTagsResponse {
  repeated string intersection = 1;
  repeated string union = 2;
}
```

## Events
### `recipe.created`
Wird gesendet, wenn ein neues Rezept erstellt wurde.\
Payload: `RecipeEvent`

### `recipe.updated`
Wird gesendet, wenn ein Rezept aktualisiert wurde.\
Payload: `RecipeEvent`

### `recipe.deleted`
Wird gesendet, wenn ein Rezept gelöscht wurde.\
Payload: `RecipeDeletedEvent`

## Datenmodell
### `RecipeRequest`
```json
{
  "name": "string",
  "description": "string",
  "steps": [
    {
      "quantifiedIngredients": [
        {
          "ingredient": 0,
          "quantity": 0
        }
      ],
      "description": "string"
    }
  ]
}
```

### `RecipeResponse`
```json
{
  "id": "string",
  "name": "string",
  "description": "string",
  "steps": [
    {
      "id": "string",
      "quantifiedIngredients": [
        {
          "id": "string",
          "ingredient": 0,
          "quantity": 0
        }
      ],
      "description": "string"
    }
  ]
}
```

### `TotalIngredientResponse`
```json
{
  "ingredient": "string",
  "quantity": 0
}
```

### `StepRequest`
```json
{
  "quantifiedIngredients": [
    {
      "ingredient": 0,
      "quantity": 0
    }
  ],
  "description": "string"
}
```

### `StepResponse`
```json
{
  "id": "string",
  "quantifiedIngredients": [
    {
      "id": "string",
      "ingredient": 0,
      "quantity": 0
    }
  ],
  "description": "string"
}
```

### `QuantifiedIngredientRequest`
```json
{
  "ingredient": 0,
  "quantity": 0
}
```

### `QuantifiedIngredientResponse`
```json
{
  "id": "string",
  "ingredient": 0,
  "quantity": 0
}
```

### `RecipeEvent`
```json
{
  "id": "string",
  "name": "string",
  "description": "string",
  "steps": [
    {
      "id": "string",
      "quantifiedIngredients": [
        {
          "id": "string",
          "ingredient": 0,
          "quantity": 0
        }
      ],
      "description": "string"
    }
  ]
}
```

### `RecipeDeletedEvent`
```json
{
  "id": "string"
}
```
