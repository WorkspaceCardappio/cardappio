import { EnumCodigoDescricao } from './enum-codigo-descricao.type';

export interface TableRestaurant {
  id?: string,
  number?: string,
  status?: EnumCodigoDescricao,
  places?: number
}
