export interface RewaaNearpayPlugin {
    echo(options: {
        value: string;
    }): Promise<{
        value: string;
    }>;
    initPayment(options: {
        token: string;
    }): Promise<{
        token: string;
    }>;
    purchase(options: {
        amount: string;
    }): Promise<{
        amount: string;
    }>;
    reconcile(options: {
        enableReceiptUi: boolean;
    }): Promise<{
        enableReceiptUi: boolean;
    }>;
    refund(options: {
        enableReceiptUi: boolean;
        amount: number;
        transactionReferenceRetrievalNumber: string;
        customerReferenceNumber: number;
    }): Promise<{
        enableReceiptUi: boolean;
        amount: number;
        transactionReferenceRetrievalNumber: string;
        customerReferenceNumber: number;
    }>;
    reverse(options: {
        enableReceiptUi: boolean;
        transactionUuid: string;
    }): Promise<{
        enableReceiptUi: boolean;
        transactionUuid: string;
    }>;
}
