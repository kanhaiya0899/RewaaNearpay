'use strict';

Object.defineProperty(exports, '__esModule', { value: true });

var core = require('@capacitor/core');

const RewaaNearpay = core.registerPlugin('RewaaNearpay', {
    web: () => Promise.resolve().then(function () { return web; }).then(m => new m.RewaaNearpayWeb()),
});

class RewaaNearpayWeb extends core.WebPlugin {
    async echo(options) {
        console.log('ECHO', options);
        return options;
    }
    async checkStatus(value) {
        console.log('checkStatus', value);
        return value;
    }
}

var web = /*#__PURE__*/Object.freeze({
    __proto__: null,
    RewaaNearpayWeb: RewaaNearpayWeb
});

exports.RewaaNearpay = RewaaNearpay;
//# sourceMappingURL=plugin.cjs.js.map
