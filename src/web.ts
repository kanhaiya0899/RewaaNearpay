import { WebPlugin } from '@capacitor/core';

import type { RewaaNearpayPlugin } from './definitions';

export class RewaaNearpayWeb extends WebPlugin implements RewaaNearpayPlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
  async checkStatus(value: string): Promise<string> {
    console.log('checkStatus', value);
    return value;
  }
  async installApp(value: string): Promise<string> {
    console.log('installApp', value);
    return value;
  }
  async purchase(options: { amount: string }): Promise<{ amount: string }> {
    console.log('purchase amount: ', options);
    return options;
  }
  async auth(options: { token: string }): Promise<{ token: string }> {
    console.log('token: ', options);
    return options;
  }
}
