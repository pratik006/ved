importScripts('workbox-sw.prod.v2.1.2.js');

const staticAssets = [
  './',
  './styles.css',
  './app.js',
  './manifest.json',
  './fallback.json',
  'https://vedsangraha-187514.firebaseio.com/ved/books.json',
  'https://vedsangraha-187514.firebaseio.com/ved/ref/scripts.json'
];

const wb = new WorkboxSW();
wb.precache(staticAssets);

wb.router.registerRoute('https://vedsangraha-187514.firebaseio.com/ved/(.*)', wb.strategies.staleWhileRevalidate());
//wb.router.registerRoute('http://localhost:4200/images/(.*)', wb.strategies.networkFirst());

workbox.routing.registerRoute(
  /\.(?:png|gif|jpg|jpeg|svg)$/,
  workbox.strategies.cacheFirst({
    cacheName: 'images',
    plugins: [
      new workbox.expiration.Plugin({
        maxEntries: 60,
        maxAgeSeconds: 30 * 24 * 60 * 60, // 30 Days
      }),
    ],
  }),
); 