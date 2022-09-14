# rewaanearpay

This plugin gives you the ability to let your app accept payments and that includes many features such as purchase, refund and reconcile.

## Install

```bash
npm install rewaanearpay
npx cap sync
```

## API

<docgen-index>

* [`echo(...)`](#echo)
* [`checkStatus(...)`](#checkstatus)
* [Interfaces](#interfaces)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### echo(...)

```typescript
echo(options: { value: string; }) => Promise<{ value: string; }>
```

| Param         | Type                            |
| ------------- | ------------------------------- |
| **`options`** | <code>{ value: string; }</code> |

**Returns:** <code>Promise&lt;{ value: string; }&gt;</code>

--------------------


### checkStatus(...)

```typescript
checkStatus(value: JSON) => Promise<JSON>
```

| Param       | Type                                  |
| ----------- | ------------------------------------- |
| **`value`** | <code><a href="#json">JSON</a></code> |

**Returns:** <code>Promise&lt;<a href="#json">JSON</a>&gt;</code>

--------------------


### Interfaces


#### JSON

An intrinsic object that provides functions to convert JavaScript values to and from the JavaScript Object Notation (<a href="#json">JSON</a>) format.

| Method        | Signature                                                                                                                                  | Description                                                                                    |
| ------------- | ------------------------------------------------------------------------------------------------------------------------------------------ | ---------------------------------------------------------------------------------------------- |
| **parse**     | (text: string, reviver?: ((this: any, key: string, value: any) =&gt; any) \| undefined) =&gt; any                                          | Converts a JavaScript Object Notation (<a href="#json">JSON</a>) string into an object.        |
| **stringify** | (value: any, replacer?: ((this: any, key: string, value: any) =&gt; any) \| undefined, space?: string \| number \| undefined) =&gt; string | Converts a JavaScript value to a JavaScript Object Notation (<a href="#json">JSON</a>) string. |
| **stringify** | (value: any, replacer?: (string \| number)[] \| null \| undefined, space?: string \| number \| undefined) =&gt; string                     | Converts a JavaScript value to a JavaScript Object Notation (<a href="#json">JSON</a>) string. |

</docgen-api>
