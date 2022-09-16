export interface RewaaNearpayPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
  checkStatus(value: string): Promise<string>;
  installApp(value: string): Promise<string>;
  auth(options: {token: string;}): Promise<{token: string;}>;
  purchase(options: { amount: string }): Promise<{ amount: string }>;
}
