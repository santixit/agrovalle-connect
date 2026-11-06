const { spawnSync } = require('node:child_process');
const path = require('node:path');

const wrapper = process.platform === 'win32' ? 'mvnw.cmd' : './mvnw';
const args = process.argv.slice(2);
const result = spawnSync(wrapper, args, {
  cwd: path.resolve(__dirname, '..'),
  env: process.env,
  shell: process.platform === 'win32',
  stdio: 'inherit'
});

if (result.error) {
  console.error(result.error.message);
  process.exit(1);
}
process.exit(result.status ?? 1);
