// Vitest global setup: stub the browser APIs Element Plus and the layout rely on.
import { vi } from 'vitest'

global.ResizeObserver = global.ResizeObserver || class {
  observe() {}
  unobserve() {}
  disconnect() {}
}

if (!window.matchMedia) {
  window.matchMedia = () => ({ matches: false, addEventListener() {}, removeEventListener() {} })
}
