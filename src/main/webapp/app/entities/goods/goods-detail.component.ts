import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IGoods } from 'app/shared/model/goods.model';

@Component({
  selector: 'jhi-goods-detail',
  templateUrl: './goods-detail.component.html'
})
export class GoodsDetailComponent implements OnInit {
  goods: IGoods;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ goods }) => {
      this.goods = goods;
    });
  }

  previousState() {
    window.history.back();
  }
}
