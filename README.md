# NIT3213 Final Assignment — Music Streaming App

Android application for **NIT3213** demonstrating API integration, Clean Architecture, Hilt dependency injection, RecyclerView dashboard, and unit tests. The UI shell is a music-streaming design; assignment flows (Login → Dashboard → Details) are wired to the [vu-nit3213-api](https://nit3213api.onrender.com/).

## Features

- **Login** — POST to Sydney campus auth endpoint; shows loading and error states
- **Dashboard** — GET `/dashboard/{keypass}`; RecyclerView grid of entities (summary excludes description)
- **Details** — Full entity fields including description
- **Hilt** — Network, repositories, use cases, ViewModels
- **Unit tests** — ViewModels and `LoginUseCase`

## Prerequisites

- Android Studio Ladybug or newer
- JDK 11+
- Android SDK 34

## Build and run

```bash
./gradlew assembleDebug
```

Install the debug APK on an emulator or device, or run from Android Studio.

### Test credentials (Sydney)

Use the values that work with the live API:

| Field    | Value      |
|----------|------------|
| Username | `s8170807` |
| Password | `Usman`    |

Successful login returns `keypass: "languages"`, which loads the languages dashboard.

> The assignment spec lists first name + student ID (`s12345678`). This project uses the tested credential format above.

## API

| Step       | Method | Endpoint                    |
|------------|--------|-----------------------------|
| Login      | POST   | `/sydney/auth`              |
| Dashboard  | GET    | `/dashboard/{keypass}`      |

Base URL: `https://nit3213api.onrender.com/`

The Render-hosted API may take up to ~60 seconds on cold start; a loading indicator is shown during requests.

## Architecture

```
presentation/   → Activities, Fragments, ViewModels, Adapters
domain/         → Models, repository interfaces, use cases
data/           → Retrofit API, DTOs, repository implementations
di/             → Hilt modules (Network, Repository)
```

## Run tests

```bash
./gradlew test
```

## Project structure (high level)

```
app/src/main/java/com/example/musicstreamingapp/
├── di/
├── data/remote/, data/repository/, data/mapper/
├── domain/model/, domain/repository/, domain/usecase/
├── presentation/login/, presentation/dashboard/, presentation/details/
├── MainActivity.kt
├── HomeActivity.kt
└── HomeFragment.kt
```

## Git

Use meaningful commits, for example:

- `feat: add Hilt and Retrofit network layer`
- `feat: implement login and dashboard API flow`
- `test: add ViewModel unit tests`
