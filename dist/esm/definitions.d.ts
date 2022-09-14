export interface RewaaNearpayPlugin {
    echo(options: {
        value: string;
    }): Promise<{
        value: string;
    }>;
    checkStatus(value: JSON): Promise<JSON>;
}
