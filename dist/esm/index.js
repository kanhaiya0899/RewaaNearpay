import { registerPlugin } from '@capacitor/core';
const RewaaNearpay = registerPlugin('RewaaNearpay', {
    web: () => import('./web').then(m => new m.RewaaNearpayWeb()),
});
export * from './definitions';
export { RewaaNearpay };
//# sourceMappingURL=index.js.map