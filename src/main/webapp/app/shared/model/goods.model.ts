import { Moment } from 'moment';

export const enum GoodsType {
  FINE = 'FINE',
  BAD = 'BAD'
}

export interface IGoods {
  id?: number;
  name?: string;
  sku?: number;
  type?: GoodsType;
  gmtCreate?: Moment;
  gmtModified?: Moment;
  deleted?: number;
  warehouseId?: number;
}

export class Goods implements IGoods {
  constructor(
    public id?: number,
    public name?: string,
    public sku?: number,
    public type?: GoodsType,
    public gmtCreate?: Moment,
    public gmtModified?: Moment,
    public deleted?: number,
    public warehouseId?: number
  ) {}
}
