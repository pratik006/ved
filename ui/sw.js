importScripts('./node_modules/workbox-sw/build/importScripts/workbox-sw.dev.v2.1.2.js');

const staticAssets = [
  './',
  './styles.css',
  './app.js',
  './manifest.json',
  './fallback.json'
];

const wb = new WorkboxSW();
wb.precache(staticAssets);

wb.router.registerRoute('http://localhost:8080/rest/(.*)', wb.strategies.networkFirst());
wb.router.registerRoute('http://localhost:4200/images/(.*)', wb.strategies.networkFirst());