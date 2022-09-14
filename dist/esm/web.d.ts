import { WebPlugin } from '@capacitor/core';
import type { RewaaNearpayPlugin } from './definitions';
export declare class RewaaNearpayWeb extends WebPlugin implements RewaaNearpayPlugin {
    echo(options: {
        value: string;
    }): Promise<{
        value: string;
    }>;
    checkStatus(value: JSON): Promise<JSON>;
}
