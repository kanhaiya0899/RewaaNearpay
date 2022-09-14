export interface RewaaNearpayPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
}
