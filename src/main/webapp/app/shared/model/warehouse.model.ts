import { Moment } from 'moment';

export interface IWarehouse {
  id?: number;
  name?: string;
  address?: string;
  stock?: number;
  owner?: string;
  gmtCreate?: Moment;
  gmtModified?: Moment;
  deleted?: number;
}

export class Warehouse implements IWarehouse {
  constructor(
    public id?: number,
    public name?: string,
    public address?: string,
    public stock?: number,
    public owner?: string,
    public gmtCreate?: Moment,
    public gmtModified?: Moment,
    public deleted?: number
  ) {}
}
