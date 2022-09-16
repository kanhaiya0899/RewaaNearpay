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
* [`installApp(...)`](#installapp)
* [`auth(...)`](#auth)
* [`purchase(...)`](#purchase)

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
checkStatus(value: string) => Promise<string>
```

| Param       | Type                |
| ----------- | ------------------- |
| **`value`** | <code>string</code> |

**Returns:** <code>Promise&lt;string&gt;</code>

--------------------


### installApp(...)

```typescript
installApp(value: string) => Promise<string>
```

| Param       | Type                |
| ----------- | ------------------- |
| **`value`** | <code>string</code> |

**Returns:** <code>Promise&lt;string&gt;</code>

--------------------


### auth(...)

```typescript
auth(options: { token: string; }) => Promise<{ token: string; }>
```

| Param         | Type                            |
| ------------- | ------------------------------- |
| **`options`** | <code>{ token: string; }</code> |

**Returns:** <code>Promise&lt;{ token: string; }&gt;</code>

--------------------


### purchase(...)

```typescript
purchase(options: { amount: string; }) => Promise<{ amount: string; }>
```

| Param         | Type                             |
| ------------- | -------------------------------- |
| **`options`** | <code>{ amount: string; }</code> |

**Returns:** <code>Promise&lt;{ amount: string; }&gt;</code>

--------------------

</docgen-api>
