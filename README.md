# Weather App using Multi-Module Jetpack Compose

This Weather App is a multi-module Android application built using Jetpack Compose. It provides weather forecasts for different locations.

## Features

- View current weather information for a selected location.
- See hourly, daily and next 5 days forecasts.
- Switch between locations for weather details.

## Project Structure

The project is structured into several modules:

- `app`: Main entry point module containing the app initialization and UI setup.
- `core-data`: Manages data sources, repositories, and interactions with external APIs or databases.
- `core-design`: Manages UI themeing and image assets.
- `core-database`: Manages database operations for caching.
- `core-navigation`: Manages common navigation for the entire app.
- `core-network`: Handles REST Api for weather forecasts.
- `core-ui`: Gather common ui composable for reuse.
- `feature-location`: Manages cities and locations for weather forecasts.
- `feature-weather`: Handles weather forecasts details.

## Tech Stack

- Jetpack Compose: UI toolkit for building native Android UI.
- Kotlin Coroutines: For managing asynchronous tasks.
- Dagger Hilt: For dependency injection.
- Coil : For Image loading.
- Retrofit: Networking library for making API calls.
- Room: Local database for caching weather data.

## Setup Instructions

1. **Clone the repository:**
   ```bash
   git clone https://github.com/Kyawkk/Weather_App.git
