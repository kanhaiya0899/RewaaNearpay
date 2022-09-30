import { WebPlugin } from '@capacitor/core';

import type { RewaaNearpayPlugin } from './definitions';

export class RewaaNearpayWeb extends WebPlugin implements RewaaNearpayPlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
  async initPayment(value: string): Promise<string> {
    console.log('initPayment', value);
    return value;
  }
  async purchase(options: { amount: string }): Promise<{ amount: string }> {
    console.log('purchase amount: ', options);
    return options;
  }
}
