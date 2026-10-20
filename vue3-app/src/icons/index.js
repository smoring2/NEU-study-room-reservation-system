import SvgIcon from '@/components/SvgIcon/index.vue' // svg component

// Vite replacement for webpack's require.context + svg-sprite-loader:
// every svg under ./svg is inlined as raw markup and injected into a single
// hidden <svg> sprite so `<use xlink:href="#icon-name">` keeps working.
const modules = import.meta.glob('./svg/*.svg', { query: '?raw', import: 'default', eager: true })

function buildSprite() {
  if (typeof document === 'undefined' || document.getElementById('__svg__icons__dom__')) return
  const symbols = Object.keys(modules).map(key => {
    const name = key.replace('./svg/', '').replace('.svg', '')
    const raw = modules[key]
    const viewBox = (raw.match(/viewBox="([^"]+)"/) || [])[1] || '0 0 1024 1024'
    const inner = raw.replace(/<\?xml[\s\S]*?\?>/, '').replace(/<svg[^>]*>/, '').replace(/<\/svg>\s*$/, '')
    return `<symbol id="icon-${name}" viewBox="${viewBox}">${inner}</symbol>`
  }).join('')
  const sprite = document.createElementNS('http://www.w3.org/2000/svg', 'svg')
  sprite.setAttribute('id', '__svg__icons__dom__')
  sprite.style.position = 'absolute'
  sprite.style.width = '0'
  sprite.style.height = '0'
  sprite.style.overflow = 'hidden'
  sprite.innerHTML = symbols
  document.body.insertBefore(sprite, document.body.firstChild)
}

// register globally
export default function registerSvgIcon(app) {
  buildSprite()
  app.component('svg-icon', SvgIcon)
}
