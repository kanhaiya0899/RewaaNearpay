import { WebPlugin } from '@capacitor/core';

import type { RewaaNearpayPlugin } from './definitions';

export class RewaaNearpayWeb extends WebPlugin implements RewaaNearpayPlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
  async checkStatus(value: JSON): Promise<JSON> {
    console.log('checkStatus', value);
    return value;
  }
}
