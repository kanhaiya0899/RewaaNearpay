import { WebPlugin } from '@capacitor/core';

import type { RewaaNearpayPlugin } from './definitions';

export class RewaaNearpayWeb extends WebPlugin implements RewaaNearpayPlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
  async initPayment(options: { token: string }): Promise<{ token: string }> {
    console.log('initPayment', options);
    return options;
  }
  async purchase(options: { amount: string }): Promise<{ amount: string }> {
    console.log('purchase amount: ', options);
    return options;
  }
  async reconcile(options: { enableReceiptUi: boolean }): Promise<{ enableReceiptUi: boolean }> {
    console.log('reconcile: ', options);
    return options;
  }
  async refund(options: { enableReceiptUi: boolean, amount: number, transactionReferenceRetrievalNumber: string, customerReferenceNumber: number }): Promise<{ enableReceiptUi: boolean, amount: number, transactionReferenceRetrievalNumber: string, customerReferenceNumber: number; }> {
    console.log('refund: ', options);
    return options;
  }
  async reverse(options: { enableReceiptUi: boolean; transactionUuid: string; }): Promise<{ enableReceiptUi: boolean; transactionUuid: string; }> {
    return options;
  }
}
