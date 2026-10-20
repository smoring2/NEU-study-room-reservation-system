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
    proxy: {}
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
    css: false
  }
})
