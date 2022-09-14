import { registerPlugin } from '@capacitor/core';

import type { RewaaNearpayPlugin } from './definitions';

const RewaaNearpay = registerPlugin<RewaaNearpayPlugin>('RewaaNearpay', {
  web: () => import('./web').then(m => new m.RewaaNearpayWeb()),
});

export * from './definitions';
export { RewaaNearpay };
