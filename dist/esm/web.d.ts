import { WebPlugin } from '@capacitor/core';
import type { RewaaNearpayPlugin } from './definitions';
export declare class RewaaNearpayWeb extends WebPlugin implements RewaaNearpayPlugin {
    echo(options: {
        value: string;
    }): Promise<{
        value: string;
    }>;
    initPayment(options: {
        token: string;
    }): Promise<{
        token: string;
    }>;
    purchase(options: {
        amount: string;
    }): Promise<{
        amount: string;
    }>;
    reconcile(options: {
        enableReceiptUi: boolean;
    }): Promise<{
        enableReceiptUi: boolean;
    }>;
    refund(options: {
        enableReceiptUi: boolean;
        amount: number;
        transactionReferenceRetrievalNumber: string;
        customerReferenceNumber: number;
    }): Promise<{
        enableReceiptUi: boolean;
        amount: number;
        transactionReferenceRetrievalNumber: string;
        customerReferenceNumber: number;
    }>;
}
