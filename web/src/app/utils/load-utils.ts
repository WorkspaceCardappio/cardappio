
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

}
