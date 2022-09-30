export interface RewaaNearpayPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
  initPayment(value: string): Promise<string>;
  purchase(options: { amount: string }): Promise<{ amount: string }>;
}
