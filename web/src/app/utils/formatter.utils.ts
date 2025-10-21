
export default class FormatterUtils {

  private static currencyFormatter = new Intl.NumberFormat('pt-BR', {
    style: 'currency',
    currency: 'BRL',
  });

  static price(price: number): string {
    if (typeof price !== 'number') {
      return '';
    }

    return this.currencyFormatter.format(price);
  }

}
