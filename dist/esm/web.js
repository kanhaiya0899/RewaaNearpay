import { WebPlugin } from '@capacitor/core';
export class RewaaNearpayWeb extends WebPlugin {
    async echo(options) {
        console.log('ECHO', options);
        return options;
    }
    async initNearpay(options) {
        console.log('initNearpay', options);
        return options;
    }
    async setupNearpay(options) {
        console.log('setupNearpay', options);
        return options;
    }
    async initPayment(options) {
        console.log('initPayment', options);
        return options;
    }
    async logoutNearpay(options) {
        return options;
    }
    async purchase(options) {
        console.log('purchase amount: ', options);
        return options;
    }
    async reconcile(options) {
        console.log('reconcile: ', options);
        return options;
    }
    async refund(options) {
        console.log('refund: ', options);
        return options;
    }
    async reverse(options) {
        return options;
    }
}
//# sourceMappingURL=web.js.map