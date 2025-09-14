# Environment Variables Setup

## Database Configuration

This application uses environment variables to securely store database credentials. The sensitive information has been removed from `application.properties` to improve security.

### Required Environment Variables

Set the following environment variables before running the application:

```bash
# Option 1: Use full DATABASE_URL (recommended for cloud deployment)
export DATABASE_URL="jdbc:postgresql://ep-tiny-base-adqjr4ea-pooler.c-2.us-east-1.aws.neon.tech/neondb?user=neondb_owner&password=YOUR_ACTUAL_PASSWORD&sslmode=require&channelBinding=require"

# Option 2: Use separate credentials (recommended for local development)
export DATABASE_USERNAME="neondb_owner"
export DATABASE_PASSWORD="YOUR_ACTUAL_PASSWORD"
export DATABASE_URL="jdbc:postgresql://ep-tiny-base-adqjr4ea-pooler.c-2.us-east-1.aws.neon.tech/neondb?sslmode=require&channelBinding=require"
```

### Local Development Setup

For local development with PostgreSQL:

```bash
export DATABASE_URL="jdbc:postgresql://localhost:5432/biblioteca"
export DATABASE_USERNAME="postgres"
export DATABASE_PASSWORD="your_local_password"
```

### Setting Environment Variables

#### Option 1: Using export commands (Linux/macOS)
```bash
export DATABASE_USERNAME="your_username"
export DATABASE_PASSWORD="your_password"
export DATABASE_URL="your_database_url"
```

#### Option 2: Using .env file with Spring Boot
1. Add the following dependency to your `pom.xml` (if you want automatic .env file loading):
```xml
<dependency>
    <groupId>me.paulschwarz</groupId>
    <artifactId>spring-dotenv</artifactId>
    <version>4.0.0</version>
</dependency>
```

2. Create a `.env` file in the project root:
```
DATABASE_USERNAME=your_username
DATABASE_PASSWORD=your_password
DATABASE_URL=your_database_url
```

3. Add `.env` to your `.gitignore` file to prevent committing sensitive data.

#### Option 3: IDE Configuration
- **IntelliJ IDEA**: Run Configuration → Environment Variables
- **VS Code**: Launch configuration in `.vscode/launch.json`
- **Eclipse**: Run Configuration → Environment tab

#### Option 4: Application Arguments
```bash
java -jar target/biblioteca-0.0.1-SNAPSHOT.jar --spring.datasource.username=your_username --spring.datasource.password=your_password
```

### Production Deployment

For production deployments, set environment variables through your hosting platform:

- **Heroku**: `heroku config:set DATABASE_PASSWORD=your_password`
- **AWS**: Use Parameter Store or Secrets Manager
- **Docker**: Use `-e` flag or docker-compose environment section
- **Kubernetes**: Use ConfigMaps and Secrets

### Security Best Practices

1. Never commit actual passwords to version control
2. Use different credentials for different environments (dev, staging, prod)
3. Rotate passwords regularly
4. Use managed database services when possible
5. Consider using secrets management tools for production
