import { fileURLToPath, URL } from 'node:url'
import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import vueJsx from '@vitejs/plugin-vue-jsx'

export default defineConfig({
  plugins: [vue(), vueJsx()],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url)),
      src: fileURLToPath(new URL('./src', import.meta.url)),
      path: 'path-browserify'
    }
  },
  server: {
    port: 5173,
    proxy: {
      // Spring Cloud gateway (service_gateway, server.port=80) routes /*/campus/**,
      // /*/cmn/**, /*/user/** and /*/order/** to the backend services.
      '/admin': { target: 'http://localhost:80', changeOrigin: true },
      '/user': { target: 'http://localhost:80', changeOrigin: true }
    }
  },
  build: {
    outDir: 'dist',
    assetsDir: 'static',
    sourcemap: true
  },
  test: {
    environment: 'jsdom',
    globals: true,
    include: ['src/**/*.spec.js'],
    setupFiles: ['./src/__tests__/setup.js'],
    css: false,
    coverage: {
      provider: 'v8',
      reporter: ['text', 'json-summary'],
      reportsDirectory: './coverage',
      include: [
        'src/stores/**/*.js',
        'src/utils/**/*.js',
        'src/api/**/*.js',
        'src/router/**/*.js',
        'src/composables/**/*.js'
      ],
      thresholds: { lines: 80, functions: 80, statements: 80, branches: 70 }
    }
  }
})
