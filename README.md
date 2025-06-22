# Tarifit User Service

A Spring Boot Kotlin microservice for managing user profiles, progress tracking, statistics, and achievements in the Tarifit language learning platform.

## Architecture

This service follows strict anemic domain principles with clear separation of concerns:

### Domain Layer (Pure Anemic Data)
- **Entities**: Completely anemic data classes with no business logic
- **Ports**: Repository interfaces defining data access contracts

### Application Layer (Business Logic)
- **Services**: All business logic and domain rules
- **Use Cases**: Complex user workflows and operations

### Infrastructure Layer (Persistence & External)
- **MongoDB Entities**: Separate persistence models with annotations
- **Repository Implementations**: Data access layer
- **Feign Clients**: External service communication

### Adapter Layer (Web Interface)
- **Controllers**: REST API endpoints
- **DTOs**: Request/response objects

## Features

### User Profile Management
- Profile creation with sensible defaults
- Profile updates and preferences
- Learning goals tracking
- Notification settings

### Progress Tracking
- Skill progress monitoring
- Exercise completion tracking
- Real-time progress calculations
- Skill unlock prerequisites

### Statistics & Gamification
- XP tracking and level calculations
- Study streak management
- Session tracking
- Dashboard statistics

### Achievement System
- Achievement progress calculation
- Automatic achievement unlocking
- XP rewards for achievements
- Progress tracking

## API Endpoints

### User Profile
- `GET /api/users/profile` - Get user profile
- `PUT /api/users/profile` - Update user profile
- `GET /api/users/learning-goals` - Get learning goals
- `PUT /api/users/learning-goals` - Update learning goals
- `GET /api/users/notification-settings` - Get notification settings
- `PUT /api/users/notification-settings` - Update notification settings

### Progress Tracking
- `GET /api/users/progress` - Get progress summary
- `GET /api/users/progress/skills/{skillId}` - Get skill progress
- `POST /api/users/progress/complete-exercise` - Complete exercise

### Statistics
- `GET /api/users/stats` - Get user statistics
- `GET /api/users/stats/dashboard` - Get dashboard stats
- `POST /api/users/stats/add-xp` - Add XP points
- `POST /api/users/stats/start-session` - Start study session
- `POST /api/users/stats/end-session` - End study session

### Achievements
- `GET /api/users/achievements` - Get user achievements
- `POST /api/users/achievements/{achievementId}/check` - Check achievement

## Configuration

### Database
- MongoDB on port 27017
- Database: `tarifit_users`
- Collections: Auto-created with proper indexes

### Security
- No authentication logic (handled by BFF gateway)
- Receives `X-User-Id` header from gateway
- CORS enabled for all origins

### External Services
- Learning Service: `http://localhost:8082`
- Content Service: `http://localhost:8081`
- Authorization headers forwarded automatically

## Running the Service

```bash
./gradlew bootRun
```

The service will start on port 8083.

## Business Rules

### XP Calculation
- Base XP varies by exercise type (vocabulary: 10, grammar: 15, etc.)
- First attempt bonus: 50% of base XP
- Time bonus: Faster completion gives additional XP
- Maximum 100 XP per operation

### Level Calculation
- Level = sqrt(totalXP / 100) + 1
- Minimum level is 1

### Streak Management
- Streak continues if studied yesterday or today
- Resets to 1 if gap > 1 day
- Updates on session completion

### Achievement System
- Real-time progress calculation
- Automatic unlocking when criteria met
- XP rewards vary by achievement difficulty
- Progress persisted for partial completion

## Data Models

### Domain Entities (Anemic)
- UserProfile
- LearningGoals
- NotificationSettings
- UserProgress
- ExerciseProgress
- UserStats
- UserAchievement
- StudySession

### MongoDB Collections
- user_profiles
- learning_goals
- notification_settings
- user_progress
- exercise_progress
- user_stats
- user_achievements
- study_sessions

## Dependencies

- Spring Boot 3.2.0
- Spring Data MongoDB
- Spring Cloud OpenFeign
- Kotlin 1.9.20
- MongoDB (runtime dependency)