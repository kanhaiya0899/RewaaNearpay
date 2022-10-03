export interface RewaaNearpayPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
  initPayment(options: { token: string; }): Promise<{ token: string; }>;
  purchase(options: { amount: string }): Promise<{ amount: string }>;
}
