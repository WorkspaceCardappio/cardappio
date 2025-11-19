
export default class LoadUtils {

  static getDefault() {
    return {
      first: 0,
      rows: 20,
      sortField: 'createdAt',
      sortOrder: -1,
      filters: {},
    }
  }

  static getDefaultForTicket() {
    return {
      first: 0,
      rows: 20,
      sortField: 'number',
      sortOrder: -1,
      filters: {},
    }
  }

}
