import { WebPlugin } from '@capacitor/core';
export class RewaaNearpayWeb extends WebPlugin {
    async echo(options) {
        console.log('ECHO', options);
        return options;
    }
    async checkStatus(value) {
        console.log('checkStatus', value);
        return value;
    }
    async installApp(value) {
        console.log('installApp', value);
        return value;
    }
    async purchase(options) {
        console.log('purchase amount: ', options);
        return options;
    }
    async auth(options) {
        console.log('token: ', options);
        return options;
    }
}
//# sourceMappingURL=web.js.map