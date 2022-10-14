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
* [`initPayment(...)`](#initpayment)
* [`purchase(...)`](#purchase)
* [`reconcile(...)`](#reconcile)
* [`refund(...)`](#refund)

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


### initPayment(...)

```typescript
initPayment(options: { token: string; }) => Promise<{ token: string; }>
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


### reconcile(...)

```typescript
reconcile(options: { enableReceiptUi: boolean; }) => Promise<{ enableReceiptUi: boolean; }>
```

| Param         | Type                                       |
| ------------- | ------------------------------------------ |
| **`options`** | <code>{ enableReceiptUi: boolean; }</code> |

**Returns:** <code>Promise&lt;{ enableReceiptUi: boolean; }&gt;</code>

--------------------


### refund(...)

```typescript
refund(options: { enableReceiptUi: boolean; amount: number; transactionReferenceRetrievalNumber: string; customerReferenceNumber: number; }) => Promise<{ enableReceiptUi: boolean; amount: number; transactionReferenceRetrievalNumber: string; customerReferenceNumber: number; }>
```

| Param         | Type                                                                                                                                     |
| ------------- | ---------------------------------------------------------------------------------------------------------------------------------------- |
| **`options`** | <code>{ enableReceiptUi: boolean; amount: number; transactionReferenceRetrievalNumber: string; customerReferenceNumber: number; }</code> |

**Returns:** <code>Promise&lt;{ enableReceiptUi: boolean; amount: number; transactionReferenceRetrievalNumber: string; customerReferenceNumber: number; }&gt;</code>

--------------------

</docgen-api>
