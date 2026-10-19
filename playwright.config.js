const { defineConfig, devices } = require('@playwright/test');

const mavenWrapper = process.platform === 'win32' ? '.\\mvnw.cmd' : 'bash ./mvnw';

module.exports = defineConfig({
  testDir: './e2e',
  fullyParallel: false,
  workers: process.env.CI ? 1 : undefined,
  retries: process.env.CI ? 1 : 0,
  reporter: process.env.CI ? 'list' : 'html',
  use: {
    ...devices['Desktop Chrome'],
    baseURL: 'http://127.0.0.1:8080',
    trace: 'retain-on-failure',
    screenshot: 'only-on-failure'
  },
  webServer: {
    command: `${mavenWrapper} -q -DskipTests "-Dspring-boot.run.useTestClasspath=true" spring-boot:run`,
    url: 'http://127.0.0.1:8080/api/v1/health',
    env: {
      ...process.env,
      DB_URL: 'jdbc:h2:mem:agrovalle-e2e;MODE=PostgreSQL;DB_CLOSE_DELAY=-1',
      DB_USERNAME: 'sa',
      DB_PASSWORD: '',
      JWT_SECRET: 'YWdyb3ZhbGxlLWNvbm5lY3QtdGVzdC1rZXktMzItYnl0ZXMtbG9uZw=='
    },
    reuseExistingServer: false,
    timeout: 180_000
  }
});
