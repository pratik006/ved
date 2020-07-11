import { Controller } from "./controller.js";
import { Book } from "./model.js";
import { Router } from './router.js';

const router = new Router({
  mode: 'hash',
  root: '/'
});
const controller = new Controller(router);

router
  .add('home', () => controller.showView('home'))
  .add('settings', () => controller.showSettingsView('settings'))
  .add('books/(.*)', (code, params) => controller.showChaptersView(code, params))
  .add('(.*)/(.*)', (bookCode, ch, sutra) => controller.showBookView(bookCode, ch))
  .add('', () => {
    // general controller
    console.log('welcome in catch all controller');
    router.navigate('home');
  });