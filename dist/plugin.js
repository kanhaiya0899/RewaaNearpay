var capacitorRewaaNearpay = (function (exports, core) {
    'use strict';

    const RewaaNearpay = core.registerPlugin('RewaaNearpay', {
        web: () => Promise.resolve().then(function () { return web; }).then(m => new m.RewaaNearpayWeb()),
    });

    class RewaaNearpayWeb extends core.WebPlugin {
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

    var web = /*#__PURE__*/Object.freeze({
        __proto__: null,
        RewaaNearpayWeb: RewaaNearpayWeb
    });

    exports.RewaaNearpay = RewaaNearpay;

    Object.defineProperty(exports, '__esModule', { value: true });

    return exports;

})({}, capacitorExports);
//# sourceMappingURL=plugin.js.map
