import { WebPlugin } from '@capacitor/core';
export class RewaaNearpayWeb extends WebPlugin {
    async echo(options) {
        console.log('ECHO', options);
        return options;
    }
    async initPayment(options) {
        console.log('initPayment', options);
        return options;
    }
    async purchase(options) {
        console.log('purchase amount: ', options);
        return options;
    }
}
//# sourceMappingURL=web.js.map