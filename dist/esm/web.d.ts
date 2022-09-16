import { WebPlugin } from '@capacitor/core';
import type { RewaaNearpayPlugin } from './definitions';
export declare class RewaaNearpayWeb extends WebPlugin implements RewaaNearpayPlugin {
    echo(options: {
        value: string;
    }): Promise<{
        value: string;
    }>;
    checkStatus(value: string): Promise<string>;
    installApp(value: string): Promise<string>;
    purchase(options: {
        amount: string;
    }): Promise<{
        amount: string;
    }>;
    auth(options: {
        token: string;
    }): Promise<{
        token: string;
    }>;
}
