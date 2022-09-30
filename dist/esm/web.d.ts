import { WebPlugin } from '@capacitor/core';
import type { RewaaNearpayPlugin } from './definitions';
export declare class RewaaNearpayWeb extends WebPlugin implements RewaaNearpayPlugin {
    echo(options: {
        value: string;
    }): Promise<{
        value: string;
    }>;
    initPayment(value: string): Promise<string>;
    purchase(options: {
        amount: string;
    }): Promise<{
        amount: string;
    }>;
}
