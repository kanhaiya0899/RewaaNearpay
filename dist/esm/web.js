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
}
//# sourceMappingURL=web.js.map